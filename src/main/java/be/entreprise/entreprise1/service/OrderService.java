package be.entreprise.entreprise1.service;

import be.entreprise.entreprise1.model.CartItem;
import be.entreprise.entreprise1.model.Order;
import be.entreprise.entreprise1.model.Product;
import be.entreprise.entreprise1.model.User;
import be.entreprise.entreprise1.repository.OrderRepository;
import be.entreprise.entreprise1.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final ProductRepository productRepository;

    public OrderService(
            OrderRepository orderRepository,
            CartService cartService,
            ProductRepository productRepository
    ) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.productRepository = productRepository;
    }

    public void placeOrder(User user) {

        List<CartItem> items = cartService.getCart(user);

        Order order = new Order();
        order.setUser(user);
        order.setItems(items);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus("CONFIRMED");

        for (CartItem item : items) {

            Product product = item.getProduct();

            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException(
                        "Niet genoeg stock voor: " + product.getName()
                );
            }

            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);

            item.setOrder(order);
        }

        orderRepository.save(order);
    }
}
