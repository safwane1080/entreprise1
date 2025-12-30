package be.entreprise.entreprise1.service;

import be.entreprise.entreprise1.model.CartItem;
import be.entreprise.entreprise1.model.Order;
import be.entreprise.entreprise1.model.Product;
import be.entreprise.entreprise1.repository.OrderRepository;
import be.entreprise.entreprise1.repository.ProductRepository;
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

    // =========================
    // OPHALEN (ADMIN)
    // =========================
    public void markAsPickedUp(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order niet gevonden"));

        // Status-check
        if (!"BEVESTIGD".equals(order.getStatus())) {
            throw new IllegalStateException("Status moet BEVESTIGD zijn");
        }

        // Extra veiligheid
        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new IllegalStateException("Order bevat geen items");
        }

        // 🔒 Stock controle
        for (CartItem item : order.getItems()) {
            Product product = item.getProduct();

            if (product.getStock() < item.getQuantity()) {
                throw new IllegalStateException(
                        "Onvoldoende stock voor " + product.getName()
                );
            }
        }

        // ✅ Stock verminderen
        for (CartItem item : order.getItems()) {
            Product product = item.getProduct();
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);
        }

        order.setStatus("OPGEHAALD");
        orderRepository.save(order);
    }

    // =========================
    // TERUGBRENGEN (ADMIN)
    // =========================
    public void markAsReturned(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order niet gevonden"));

        // Status-check
        if (!"OPGEHAALD".equals(order.getStatus())) {
            throw new IllegalStateException("Status moet OPGEHAALD zijn");
        }

        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new IllegalStateException("Order bevat geen items");
        }

        // Stock terug verhogen
        for (CartItem item : order.getItems()) {
            Product product = item.getProduct();
            product.setStock(product.getStock() + item.getQuantity());
            productRepository.save(product);
        }

        order.setStatus("TERUGGEBRACHT");
        orderRepository.save(order);
    }
}
