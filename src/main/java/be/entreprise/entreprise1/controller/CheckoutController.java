package be.entreprise.entreprise1.controller;

import be.entreprise.entreprise1.model.Order;
import be.entreprise.entreprise1.model.User;
import be.entreprise.entreprise1.repository.UserRepository;
import be.entreprise.entreprise1.service.CheckoutService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;

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
    public String doCheckout(
            Principal principal,
            @RequestParam("startDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate
    ) {

        // 🔐 extra safety (front-end doet dit ook)
        if (startDate.isBefore(LocalDate.now())) {
            return "redirect:/cart?error=invalidDate";
        }

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User niet gevonden"));

        // ✅ JUISTE methode-aanroep
        Order order = checkoutService.checkout(user, startDate);

        return "redirect:/orders/" + order.getId();
    }
}
