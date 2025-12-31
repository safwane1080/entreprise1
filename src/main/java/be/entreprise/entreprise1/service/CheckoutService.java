package be.entreprise.entreprise1.service;

import be.entreprise.entreprise1.model.CartItem;
import be.entreprise.entreprise1.model.Order;
import be.entreprise.entreprise1.model.User;
import be.entreprise.entreprise1.repository.CartItemRepository;
import be.entreprise.entreprise1.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public Order checkout(User user, LocalDate startDate) {

        List<CartItem> items = cartItemRepository.findByUser(user);

        if (items == null || items.isEmpty()) {
            throw new IllegalStateException("Je winkelmand is leeg.");
        }

        // 🧠 langste huur bepaalt einddatum
        int maxDays = items.stream()
                .mapToInt(CartItem::getDays)
                .max()
                .orElse(1);

        LocalDate endDate = startDate.plusDays(maxDays);

        Order order = new Order();
        order.setUser(user);
        order.setStartDate(startDate);
        order.setEndDate(endDate);
        order.setStatus("BEVESTIGD");

        Order savedOrder = orderRepository.save(order);

        // items koppelen aan order
        for (CartItem item : items) {
            item.setOrder(savedOrder);
        }

        cartItemRepository.saveAll(items);

        return savedOrder;
    }
}
