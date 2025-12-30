package be.entreprise.entreprise1.controller;

import be.entreprise.entreprise1.model.Order;
import be.entreprise.entreprise1.model.User;
import be.entreprise.entreprise1.repository.OrderRepository;
import be.entreprise.entreprise1.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class OrderController {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderController(OrderRepository orderRepository,
                           UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/orders")
    public String orders(Principal principal, Model model) {

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow();

        model.addAttribute(
                "orders",
                orderRepository.findByUserOrderByCreatedAtDesc(user)
        );

        return "orders";
    }

    @GetMapping("/orders/{id}")
    public String orderDetail(
            @PathVariable Long id,
            Principal principal,
            Model model
    ) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow();

        Order order = orderRepository.findById(id)
                .orElseThrow();

        if (!order.getUser().getId().equals(user.getId())) {
            return "redirect:/orders";
        }

        model.addAttribute("order", order);
        return "order-detail";
    }
}
