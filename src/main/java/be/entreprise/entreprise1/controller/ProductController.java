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
    public String products(@RequestParam(required = false) Long category,
                           Model model) {

        List<Category> categories = categoryRepository.findAll();
        List<Product> products;

        if (category == null) {
            products = productRepository.findAll();
        } else {
            products = productRepository.findByCategoryId(category);
        }

        model.addAttribute("categories", categories);
        model.addAttribute("products", products);

        return "products";
    }
}
