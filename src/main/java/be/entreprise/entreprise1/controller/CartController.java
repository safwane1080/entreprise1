package be.entreprise.entreprise1.controller;

import be.entreprise.entreprise1.model.CartItem;
import be.entreprise.entreprise1.model.Product;
import be.entreprise.entreprise1.model.User;
import be.entreprise.entreprise1.repository.ProductRepository;
import be.entreprise.entreprise1.service.CartService;
import be.entreprise.entreprise1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final ProductRepository productRepository;
    private final UserService userService;

    /* ======================
       WINKELMAND TONEN
       ====================== */
    @GetMapping("/cart")
    public String showCart(Authentication auth, Model model) {

        User user = userService.findByEmail(auth.getName());
        List<CartItem> items = cartService.getCart(user);

        model.addAttribute("items", items);
        return "cart";
    }

    /* ======================
       PRODUCT TOEVOEGEN
       ====================== */
    @PostMapping("/cart/add")
    public String addToCart(
            @RequestParam Long productId,
            @RequestParam int quantity,
            Authentication auth
    ) {
        User user = userService.findByEmail(auth.getName());
        Product product = productRepository.findById(productId).orElseThrow();

        cartService.addToCart(user, product, quantity);

        return "redirect:/cart";
    }
}
