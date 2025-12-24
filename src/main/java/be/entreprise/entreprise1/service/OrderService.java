package be.entreprise.entreprise1.service;

import be.entreprise.entreprise1.model.CartItem;
import be.entreprise.entreprise1.model.Order;
import be.entreprise.entreprise1.model.User;
import be.entreprise.entreprise1.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    // ✅ EXPLICIETE CONSTRUCTOR (PROBLEEM OPGELOST)
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(User user, List<CartItem> items) {

        Order order = new Order();
        order.setUser(user);
        order.setItems(items);

        for (CartItem item : items) {
            item.setOrder(order);
        }

        return orderRepository.save(order);
    }
}
