package be.entreprise.entreprise1.controller;

import be.entreprise.entreprise1.model.CartItem;
import be.entreprise.entreprise1.model.Product;
import be.entreprise.entreprise1.model.User;
import be.entreprise.entreprise1.repository.ProductRepository;
import be.entreprise.entreprise1.service.CartService;
import be.entreprise.entreprise1.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CartController {

    private final CartService cartService;
    private final ProductRepository productRepository;
    private final UserService userService;

    public CartController(
            CartService cartService,
            ProductRepository productRepository,
            UserService userService
    ) {
        this.cartService = cartService;
        this.productRepository = productRepository;
        this.userService = userService;
    }

    /**
     * Helper: bepaalt automatisch juiste user
     */
    private User resolveUser(Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            User user = userService.findByEmail(auth.getName());
            if (user != null) {
                return user;
            }
        }
        return userService.getDefaultUser();
    }

    @GetMapping("/cart")
    public String showCart(Authentication auth, Model model) {

        User user = resolveUser(auth);
        List<CartItem> items = cartService.getCart(user);

        model.addAttribute("items", items);
        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(
            Authentication auth,
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") int quantity
    ) {
        User user = resolveUser(auth);
        Product product = productRepository.findById(productId).orElseThrow();

        cartService.addToCart(user, product, quantity);

        return "redirect:/cart";
    }

    @PostMapping("/cart/clear")
    public String clearCart(Authentication auth) {

        User user = resolveUser(auth);
        cartService.clearCart(user);

        return "redirect:/cart";
    }
}
