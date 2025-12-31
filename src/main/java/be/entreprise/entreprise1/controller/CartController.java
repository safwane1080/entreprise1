package be.entreprise.entreprise1.controller;

import be.entreprise.entreprise1.model.Product;
import be.entreprise.entreprise1.model.User;
import be.entreprise.entreprise1.repository.ProductRepository;
import be.entreprise.entreprise1.repository.UserRepository;
import be.entreprise.entreprise1.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class CartController {

    private final CartService cartService;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartController(
            CartService cartService,
            ProductRepository productRepository,
            UserRepository userRepository
    ) {
        this.cartService = cartService;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/cart/add")
    public String addToCart(
            Principal principal,
            @RequestParam Long productId,
            @RequestParam int quantity,
            @RequestParam int days
    ) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User niet gevonden"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product niet gevonden"));
        if (quantity > product.getStock()) {
            throw new IllegalArgumentException("Onvoldoende stock beschikbaar");
        }

        cartService.addToCart(user, product, quantity, days);

        return "redirect:/cart";
    }


    @GetMapping("/cart")
    public String viewCart(Principal principal, Model model) {

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User niet gevonden"));

        var items = cartService.getCart(user);

        double total = items.stream()
                .mapToDouble(i -> i.getSubtotal())
                .sum();

        model.addAttribute("items", items);
        model.addAttribute("total", total);

        return "cart";
    }

}
