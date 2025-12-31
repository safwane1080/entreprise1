package be.entreprise.entreprise1.model;

import jakarta.persistence.*;

@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    @ManyToOne
    private User user;

    @ManyToOne
    private Order order;

    private int quantity;
    private int days;

    public Long getId() { return id; }
    public Product getProduct() { return product; }
    public User getUser() { return user; }
    public Order getOrder() { return order; }
    public int getQuantity() { return quantity; }
    public int getDays() { return days; }

    public double getSubtotal() {
        if (product == null) return 0.0;

        double pricePerDay = product.getPricePerDay();
        return pricePerDay * quantity * days;
    }

    public void setProduct(Product product) { this.product = product; }
    public void setUser(User user) { this.user = user; }
    public void setOrder(Order order) { this.order = order; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setDays(int days) { this.days = days; }
}
