package be.entreprise.entreprise1.controller;

import be.entreprise.entreprise1.model.User;
import be.entreprise.entreprise1.service.CartService;
import be.entreprise.entreprise1.service.OrderService;
import be.entreprise.entreprise1.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CheckoutController {

    private final CartService cartService;
    private final OrderService orderService;
    private final UserService userService;

    public CheckoutController(
            CartService cartService,
            OrderService orderService,
            UserService userService
    ) {
        this.cartService = cartService;
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping("/checkout")
    public String checkout(Authentication auth) {

        User user = userService.findByEmail(auth.getName());

        orderService.placeOrder(user);   // ✅ bestaat nu
        cartService.clearCart(user);

        return "redirect:/checkout/success";
    }
}
