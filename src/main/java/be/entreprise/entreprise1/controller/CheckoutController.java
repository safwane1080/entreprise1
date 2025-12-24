package be.entreprise.entreprise1.controller;

import be.entreprise.entreprise1.model.CartItem;
import be.entreprise.entreprise1.model.User;
import be.entreprise.entreprise1.service.CartService;
import be.entreprise.entreprise1.service.OrderService;
import be.entreprise.entreprise1.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CheckoutController {

    private final CartService cartService;
    private final OrderService orderService;
    private final UserService userService;

    // ✅ EXPLICIETE CONSTRUCTOR (belangrijk!)
    public CheckoutController(
            CartService cartService,
            OrderService orderService,
            UserService userService
    ) {
        this.cartService = cartService;
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/checkout")
    public String checkout(Authentication auth, Model model) {
        User user = userService.findByEmail(auth.getName());
        List<CartItem> items = cartService.getCart(user);
        model.addAttribute("items", items);
        return "checkout";
    }

    @PostMapping("/checkout/confirm")
    public String confirmCheckout(Authentication auth) {
        User user = userService.findByEmail(auth.getName());
        List<CartItem> items = cartService.getCart(user);

        orderService.createOrder(user, items);
        cartService.clearCart(user);

        return "redirect:/checkout/success";
    }

    @GetMapping("/checkout/success")
    public String success() {
        return "checkout-success";
    }
}
