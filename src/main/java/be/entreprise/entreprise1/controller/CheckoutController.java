package be.entreprise.entreprise1.controller;

import be.entreprise.entreprise1.model.User;
import be.entreprise.entreprise1.repository.UserRepository;
import be.entreprise.entreprise1.service.CartService;
import be.entreprise.entreprise1.service.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CheckoutController {

    private final CartService cartService;
    private final OrderService orderService;
    private final UserRepository userRepository;

    public CheckoutController(
            CartService cartService,
            OrderService orderService,
            UserRepository userRepository
    ) {
        this.cartService = cartService;
        this.orderService = orderService;
        this.userRepository = userRepository;
    }

    @PostMapping("/checkout")
    public String checkout(Authentication auth) {

        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User niet gevonden"));

        orderService.placeOrder(user);
        cartService.clearCart(user);

        return "redirect:/checkout/success";
    }

    @GetMapping("/checkout/success")
    public String success() {
        return "checkout-success";
    }
}
