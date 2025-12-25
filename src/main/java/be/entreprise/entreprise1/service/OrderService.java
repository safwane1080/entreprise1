package be.entreprise.entreprise1.service;

import be.entreprise.entreprise1.model.CartItem;
import be.entreprise.entreprise1.model.Order;
import be.entreprise.entreprise1.model.User;
import be.entreprise.entreprise1.repository.CartItemRepository;
import be.entreprise.entreprise1.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;

    public OrderService(
            OrderRepository orderRepository,
            CartItemRepository cartItemRepository
    ) {
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public Order placeOrder(User user) {

        List<CartItem> items = cartItemRepository.findByUser(user);

        Order order = new Order();
        order.setUser(user);
        order.setItems(items);

        for (CartItem item : items) {
            item.setOrder(order);
        }

        return orderRepository.save(order);
    }
}
