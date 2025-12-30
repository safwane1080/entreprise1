package be.entreprise.entreprise1.controller;

import be.entreprise.entreprise1.model.User;
import be.entreprise.entreprise1.repository.UserRepository;
import be.entreprise.entreprise1.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;

    public OrderController(OrderService orderService,
                           UserRepository userRepository) {
        this.orderService = orderService;
        this.userRepository = userRepository;
    }

    @GetMapping("/orders")
    public String orderHistory(Principal principal, Model model) {

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User niet gevonden"));

        model.addAttribute("orders", orderService.getOrdersForUser(user));
        return "orders";
    }
}
