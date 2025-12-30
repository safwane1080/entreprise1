package be.entreprise.entreprise1.controller;

import be.entreprise.entreprise1.model.Order;
import be.entreprise.entreprise1.model.User;
import be.entreprise.entreprise1.repository.UserRepository;
import be.entreprise.entreprise1.service.CheckoutService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class CheckoutController {

    private final CheckoutService checkoutService;
    private final UserRepository userRepository;

    public CheckoutController(
            CheckoutService checkoutService,
            UserRepository userRepository
    ) {
        this.checkoutService = checkoutService;
        this.userRepository = userRepository;
    }

    @PostMapping("/checkout")
    public String doCheckout(Principal principal) {

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User niet gevonden"));

        Order order = checkoutService.checkout(user);

        return "redirect:/checkout/success?orderId=" + order.getId();
    }

    @GetMapping("/checkout/success")
    public String checkoutSuccess(Long orderId, Model model) {
        model.addAttribute("orderId", orderId);
        return "checkout-success";
    }
}
