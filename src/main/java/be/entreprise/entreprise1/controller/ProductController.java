package be.entreprise.entreprise1.controller;

import be.entreprise.entreprise1.model.Category;
import be.entreprise.entreprise1.model.Product;
import be.entreprise.entreprise1.repository.CategoryRepository;
import be.entreprise.entreprise1.repository.ProductRepository;
import org.springframework.data.domain.Sort;
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
            @RequestParam(required = false) String sort,
            Model model
    ) {

        List<Category> categories = categoryRepository.findAll();
        List<Product> products;

        boolean hasSearch = q != null && !q.trim().isEmpty();

        Sort sortOrder = Sort.unsorted();

        if ("name_asc".equals(sort)) {
            sortOrder = Sort.by("name").ascending();
        } else if ("name_desc".equals(sort)) {
            sortOrder = Sort.by("name").descending();
        } else if ("price_asc".equals(sort)) {
            sortOrder = Sort.by("pricePerDay").ascending();
        } else if ("price_desc".equals(sort)) {
            sortOrder = Sort.by("pricePerDay").descending();
        }

        if (category == null && !hasSearch) {

            products = productRepository.findAll(sortOrder);

        } else if (category != null && !hasSearch) {

            products = productRepository.findByCategoryId(category, sortOrder);

        } else if (category == null) {

            products = productRepository
                    .findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                            q, q, sortOrder
                    );

        } else {

            products = productRepository
                    .findByCategoryIdAndNameContainingIgnoreCaseOrCategoryIdAndDescriptionContainingIgnoreCase(
                            category, q,
                            category, q,
                            sortOrder
                    );
        }

        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("q", q);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("selectedSort", sort);

        return "products";
    }

}
