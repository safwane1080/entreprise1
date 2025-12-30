package be.entreprise.entreprise1.repository;

import  be.entreprise.entreprise1.model.Order;
import be.entreprise.entreprise1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserOrderByCreatedAtDesc(User user);
    List<Order> findAllByOrderByCreatedAtDesc();


}
