package be.entreprise.entreprise1.controller;

import be.entreprise.entreprise1.model.Order;
import be.entreprise.entreprise1.model.User;
import be.entreprise.entreprise1.repository.OrderRepository;
import be.entreprise.entreprise1.repository.UserRepository;
import be.entreprise.entreprise1.service.OrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderService orderService;

    public OrderController(
            OrderRepository orderRepository,
            UserRepository userRepository,
            OrderService orderService
    ) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderService = orderService;
    }

    @GetMapping
    public String orders(Principal principal, Model model) {

        if (principal == null) {
            return "redirect:/login";
        }

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User niet gevonden"));

        model.addAttribute(
                "orders",
                orderRepository.findByUserOrderByCreatedAtDesc(user)
        );

        return "orders";
    }
    @GetMapping("/{id}")
    public String orderDetail(
            @PathVariable Long id,
            Principal principal,
            Model model
    ) {

        if (principal == null) {
            return "redirect:/login";
        }

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User niet gevonden"));

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order niet gevonden"));

        if (!order.getUser().getId().equals(user.getId()) && !isAdmin()) {
            return "redirect:/orders";
        }

        model.addAttribute("order", order);
        return "order-detail";
    }

    @PostMapping("/{id}/pickup")
    @PreAuthorize("hasRole('ADMIN')")
    public String pickup(@PathVariable Long id) {
        orderService.markAsPickedUp(id);
        return "redirect:/orders/" + id;
    }

    @PostMapping("/{id}/return")
    @PreAuthorize("hasRole('ADMIN')")
    public String returnOrder(@PathVariable Long id) {
        orderService.markAsReturned(id);
        return "redirect:/orders/" + id;
    }

    private boolean isAdmin() {
        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}
