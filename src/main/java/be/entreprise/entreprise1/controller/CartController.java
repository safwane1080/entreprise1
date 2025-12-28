package be.entreprise.entreprise1.controller;

import be.entreprise.entreprise1.model.CartItem;
import be.entreprise.entreprise1.model.Product;
import be.entreprise.entreprise1.model.User;
import be.entreprise.entreprise1.repository.ProductRepository;
import be.entreprise.entreprise1.repository.UserRepository;
import be.entreprise.entreprise1.service.CartService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CartController {

    private final CartService cartService;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartController(CartService cartService,
                          ProductRepository productRepository,
                          UserRepository userRepository) {
        this.cartService = cartService;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long productId,
                            @RequestParam int quantity,
                            Authentication auth) {

        String email = auth.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User niet gevonden"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product niet gevonden"));

        cartService.addToCart(user, product, quantity);

        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String viewCart(Authentication auth, Model model) {

        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User niet gevonden"));

        model.addAttribute("items", cartService.getCart(user));
        return "cart";
    }
}
