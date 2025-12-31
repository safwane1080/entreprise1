package be.entreprise.entreprise1.repository;

import be.entreprise.entreprise1.model.Order;
import be.entreprise.entreprise1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserOrderByCreatedAtDesc(User user);

    List<Order> findAllByOrderByCreatedAtDesc();

    @Query("""
        select distinct o
        from Order o
        left join fetch o.items i
        left join fetch i.product p
        where o.id = :id and o.user = :user
    """)
    Optional<Order> findDetailByIdAndUser(@Param("id") Long id, @Param("user") User user);
}
