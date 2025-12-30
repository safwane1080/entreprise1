package be.entreprise.entreprise1.controller;

import be.entreprise.entreprise1.model.Order;
import be.entreprise.entreprise1.repository.OrderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final OrderRepository orderRepository;

    public AdminController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public String dashboard(Model model) {

        model.addAttribute(
                "orders",
                orderRepository.findAllByOrderByCreatedAtDesc()
        );

        return "admin-dashboard";
    }

    @PostMapping("/order/status")
    public String updateStatus(
            @RequestParam Long orderId,
            @RequestParam String status
    ) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus(status);
        orderRepository.save(order);

        return "redirect:/admin";
    }
}
