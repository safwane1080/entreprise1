package be.entreprise.entreprise1.service;

import be.entreprise.entreprise1.model.CartItem;
import be.entreprise.entreprise1.model.Product;
import be.entreprise.entreprise1.model.User;
import be.entreprise.entreprise1.repository.CartItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;

    public CartService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public List<CartItem> getCart(User user) {
        return cartItemRepository.findByUser(user);
    }

    public void addToCart(User user, Product product, int quantity, int days) {

        CartItem existingItem = cartItemRepository
                .findByUserAndProductAndOrderIsNull(user, product)
                .orElse(null);

        int totalQuantity = quantity;

        if (existingItem != null) {
            totalQuantity += existingItem.getQuantity();
        }

        // 🔒 Stock check
        if (totalQuantity > product.getStock()) {
            throw new IllegalArgumentException(
                    "Onvoldoende stock voor " + product.getName()
            );
        }

        // 🔻 STOCK VERLAGEN
        product.setStock(product.getStock() - quantity);

        if (existingItem != null) {
            existingItem.setQuantity(totalQuantity);
            existingItem.setDays(days);
            cartItemRepository.save(existingItem);
        } else {
            CartItem item = new CartItem();
            item.setUser(user);
            item.setProduct(product);
            item.setQuantity(quantity);
            item.setDays(days);
            cartItemRepository.save(item);
        }
    }

    public void clearCart(User user) {

        List<CartItem> items = cartItemRepository.findByUserAndOrderIsNull(user);

        for (CartItem item : items) {
            Product product = item.getProduct();

            // 🔺 STOCK TERUG
            product.setStock(product.getStock() + item.getQuantity());
        }

        cartItemRepository.deleteAll(items);
    }

}
