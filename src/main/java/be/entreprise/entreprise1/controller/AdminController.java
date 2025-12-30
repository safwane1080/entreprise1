package be.entreprise.entreprise1.controller;

import be.entreprise.entreprise1.repository.OrderRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final OrderRepository orderRepository;

    public AdminController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("orders", orderRepository.findAllByOrderByCreatedAtDesc());
        return "admin-dashboard";
    }

    @GetMapping("/orders")
    public String adminOrders(Model model) {
        model.addAttribute("orders", orderRepository.findAllByOrderByCreatedAtDesc());
        return "admin-orders";
    }
}
