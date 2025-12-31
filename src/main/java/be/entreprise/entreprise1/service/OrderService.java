package be.entreprise.entreprise1.service;

import be.entreprise.entreprise1.model.CartItem;
import be.entreprise.entreprise1.model.Order;
import be.entreprise.entreprise1.model.OrderStatus;
import be.entreprise.entreprise1.model.Product;
import be.entreprise.entreprise1.repository.OrderRepository;
import be.entreprise.entreprise1.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(
            OrderRepository orderRepository,
            ProductRepository productRepository
    ) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }
    @Transactional
    public void markAsPickedUp(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order niet gevonden"));

        if (!"BEVESTIGD".equals(order.getStatus())) {
            throw new IllegalStateException("Status moet BEVESTIGD zijn");
        }

        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new IllegalStateException("Order bevat geen items");
        }

        order.setStatus("OPGEHAALD");
        orderRepository.save(order);
    }

    public void markAsReturned(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order niet gevonden"));

        if (!"OPGEHAALD".equals(order.getStatus())) {
            throw new IllegalStateException("Status moet OPGEHAALD zijn");
        }

        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new IllegalStateException("Order bevat geen items");
        }

        for (CartItem item : order.getItems()) {
            Product product = item.getProduct();
            product.setStock(product.getStock() + item.getQuantity());
            productRepository.save(product);
        }

        order.setStatus("TERUGGEBRACHT");
        orderRepository.save(order);
    }

    @Transactional
    public void updateStatus(Long orderId, OrderStatus newStatus) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order niet gevonden"));

        OrderStatus current = OrderStatus.valueOf(order.getStatus());

        if (current == OrderStatus.BEVESTIGD && newStatus != OrderStatus.OPGEHAALD) {
            throw new IllegalStateException("Ongeldige statusovergang");
        }

        if (current == OrderStatus.OPGEHAALD && newStatus != OrderStatus.TERUGGEBRACHT) {
            throw new IllegalStateException("Ongeldige statusovergang");
        }

        order.setStatus(String.valueOf(newStatus));
        orderRepository.save(order);
    }
}
