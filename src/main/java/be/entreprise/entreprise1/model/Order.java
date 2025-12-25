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

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<CartItem> items;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String status;

    // ===== GETTERS =====

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getStatus() {
        return status;
    }

    // ===== SETTERS =====

    public void setUser(User user) {
        this.user = user;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
