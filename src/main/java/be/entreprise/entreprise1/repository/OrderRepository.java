package be.entreprise.entreprise1.repository;

import be.entreprise.entreprise1.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
