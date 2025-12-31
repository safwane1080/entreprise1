package be.entreprise.entreprise1.service;

import be.entreprise.entreprise1.model.CartItem;
import be.entreprise.entreprise1.model.Order;
import be.entreprise.entreprise1.model.User;
import be.entreprise.entreprise1.repository.CartItemRepository;
import be.entreprise.entreprise1.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckoutService {

    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;

    public CheckoutService(
            CartItemRepository cartItemRepository,
            OrderRepository orderRepository
    ) {
        this.cartItemRepository = cartItemRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Order checkout(User user) {

        // ✅ Neem ALLE items van de user
        List<CartItem> items = cartItemRepository.findByUser(user);

        if (items == null || items.isEmpty()) {
            throw new IllegalStateException("Je winkelmand is leeg.");
        }

        // ✅ Nieuwe order maken
        Order order = new Order();
        order.setUser(user);
        order.setStatus("BEVESTIGD");

        Order savedOrder = orderRepository.save(order);

        // ✅ Items koppelen aan order
        for (CartItem item : items) {
            item.setOrder(savedOrder);
        }

        cartItemRepository.saveAll(items);

        return savedOrder;
    }
}
