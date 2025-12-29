package be.entreprise.entreprise1.service;

import be.entreprise.entreprise1.model.CartItem;
import be.entreprise.entreprise1.model.Order;
import be.entreprise.entreprise1.model.User;
import be.entreprise.entreprise1.repository.CartItemRepository;
import be.entreprise.entreprise1.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CheckoutService {

    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;

    public CheckoutService(CartItemRepository cartItemRepository, OrderRepository orderRepository) {
        this.cartItemRepository = cartItemRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Order checkout(User user) {
        List<CartItem> items = cartItemRepository.findByUser(user);

        if (items.isEmpty()) {
            throw new RuntimeException("Winkelmand is leeg");
        }

        Order order = new Order();
        order.setUser(user);
        order = orderRepository.save(order);

        for (CartItem item : items) {
            item.setOrder(order);
        }
        cartItemRepository.saveAll(items);

        cartItemRepository.deleteAll(items);

        return order;
    }
}
