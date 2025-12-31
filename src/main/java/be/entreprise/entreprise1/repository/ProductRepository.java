package be.entreprise.entreprise1.repository;

import be.entreprise.entreprise1.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;

import java.util.List;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAll(Sort sort);
    List<Product> findByCategoryId(Long categoryId, Sort sort);

    List<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String name,
            String description,
            Sort sort
    );

    List<Product> findByCategoryIdAndNameContainingIgnoreCaseOrCategoryIdAndDescriptionContainingIgnoreCase(
            Long categoryId1,
            String name,
            Long categoryId2,
            String description,
            Sort sort
    );
}
