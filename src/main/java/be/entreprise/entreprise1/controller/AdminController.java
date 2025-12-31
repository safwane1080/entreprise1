package be.entreprise.entreprise1.controller;

import be.entreprise.entreprise1.model.OrderStatus;
import be.entreprise.entreprise1.repository.OrderRepository;
import be.entreprise.entreprise1.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final OrderRepository orderRepository;
    private final OrderService orderService;
    public AdminController(
            OrderRepository orderRepository,
            OrderService orderService
    ) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
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

    @PostMapping("/orders/{id}/status")
    public String changeStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status
    ) {
        orderService.updateStatus(id, status);
        return "redirect:/admin/orders";
    }
}
