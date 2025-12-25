package be.entreprise.entreprise1.service;

import be.entreprise.entreprise1.model.CartItem;
import be.entreprise.entreprise1.model.Order;
import be.entreprise.entreprise1.model.User;
import be.entreprise.entreprise1.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;

    public OrderService(OrderRepository orderRepository, CartService cartService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
    }

    public void placeOrder(User user) {

        List<CartItem> items = cartService.getCart(user);

        Order order = new Order();
        order.setUser(user);
        order.setItems(items);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus("CONFIRMED");

        for (CartItem item : items) {
            item.setOrder(order);
        }

        orderRepository.save(order);
    }
}
