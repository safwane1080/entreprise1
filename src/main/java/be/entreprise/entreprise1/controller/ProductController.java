package be.entreprise.entreprise1.controller;

import be.entreprise.entreprise1.model.Category;
import be.entreprise.entreprise1.model.Product;
import be.entreprise.entreprise1.repository.CategoryRepository;
import be.entreprise.entreprise1.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductController {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductController(ProductRepository productRepository,
                             CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/products")
    public String products(
            @RequestParam(required = false) Long category,
            @RequestParam(required = false) String q,
            Model model
    ) {

        List<Category> categories = categoryRepository.findAll();
        List<Product> products;

        boolean hasSearch = q != null && !q.trim().isEmpty();

        if (category == null && !hasSearch) {
            // Geen filter, geen zoekterm
            products = productRepository.findAll();

        } else if (category != null && !hasSearch) {
            // Alleen categorie
            products = productRepository.findByCategoryId(category);

        } else if (category == null) {
            // Alleen zoekterm
            products = productRepository
                    .findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(q, q);

        } else {
            // Categorie + zoekterm
            products = productRepository
                    .findByCategoryIdAndNameContainingIgnoreCaseOrCategoryIdAndDescriptionContainingIgnoreCase(
                            category, q,
                            category, q
                    );
        }

        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("q", q);
        model.addAttribute("selectedCategory", category);

        return "products";
    }

}
