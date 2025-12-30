package be.entreprise.entreprise1.service;

import be.entreprise.entreprise1.model.CartItem;
import be.entreprise.entreprise1.model.Order;
import be.entreprise.entreprise1.model.User;
import be.entreprise.entreprise1.repository.CartItemRepository;
import be.entreprise.entreprise1.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CheckoutService {

    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;

    public CheckoutService(
            CartService cartService,
            OrderRepository orderRepository,
            CartItemRepository cartItemRepository
    ) {
        this.cartService = cartService;
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public Order checkout(User user) {

        List<CartItem> items = cartService.getCart(user);

        if (items.isEmpty()) {
            throw new RuntimeException("Winkelmand is leeg");
        }

        Order order = new Order();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus("Bevestigd");

        double total = items.stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();

        order.setTotalPrice(total);

        orderRepository.save(order);

        for (CartItem item : items) {
            item.setOrder(order);
            cartItemRepository.save(item);
        }

        cartService.clearCart(user);

        return order;
    }
}
