package be.entreprise.entreprise1.repository;

import be.entreprise.entreprise1.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String name,
            String description
    );

    List<Product> findByCategoryIdAndNameContainingIgnoreCaseOrCategoryIdAndDescriptionContainingIgnoreCase(
            Long categoryId1,
            String name,
            Long categoryId2,
            String description
    );
}
