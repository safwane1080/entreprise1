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

import java.util.List;

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

    @GetMapping("/cart")
    public String showCart(Authentication authentication, Model model) {

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User niet gevonden"));

        List<CartItem> items = cartService.getCart(user);
        model.addAttribute("items", items);

        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(
            Authentication authentication,
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") int quantity
    ) {

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User niet gevonden"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product niet gevonden"));

        cartService.addToCart(user, product, quantity);

        return "redirect:/cart";
    }

    @PostMapping("/cart/clear")
    public String clearCart(Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User niet gevonden"));

        cartService.clearCart(user);

        return "redirect:/cart";
    }
}
