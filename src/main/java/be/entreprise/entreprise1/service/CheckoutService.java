package be.entreprise.entreprise1.service;

import be.entreprise.entreprise1.model.CartItem;
import be.entreprise.entreprise1.model.Order;
import be.entreprise.entreprise1.model.User;
import be.entreprise.entreprise1.repository.CartItemRepository;
import be.entreprise.entreprise1.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CheckoutService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;

    public CheckoutService(
            OrderRepository orderRepository,
            CartItemRepository cartItemRepository
    ) {
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Transactional
    public Order checkout(User user) {

        // 🔹 Enkel cart items die nog NIET aan een order hangen
        List<CartItem> items = cartItemRepository.findByUserAndOrderIsNull(user);

        if (items.isEmpty()) {
            throw new RuntimeException("Winkelmand is leeg");
        }

        Order order = new Order();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus("BEVESTIGD");

        order = orderRepository.save(order);

        // 🔹 Koppel cart items aan order
        for (CartItem item : items) {
            item.setOrder(order);
        }

        cartItemRepository.saveAll(items);

        return order;
    }
}
