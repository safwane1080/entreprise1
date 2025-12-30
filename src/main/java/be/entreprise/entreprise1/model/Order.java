package be.entreprise.entreprise1.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "order")
    private List<CartItem> items;

    private LocalDateTime createdAt;

    private String status; // CONFIRMED, IN_PROGRESS, COMPLETED

    private double totalPrice;

    // ===== GETTERS =====
    public Long getId() { return id; }
    public User getUser() { return user; }
    public List<CartItem> getItems() { return items; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getStatus() { return status; }
    public double getTotalPrice() { return totalPrice; }

    // ===== SETTERS =====
    public void setUser(User user) { this.user = user; }
    public void setItems(List<CartItem> items) { this.items = items; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setStatus(String status) { this.status = status; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
}
