package be.entreprise.entreprise1.config;

import be.entreprise.entreprise1.model.Category;
import be.entreprise.entreprise1.model.Product;
import be.entreprise.entreprise1.repository.CategoryRepository;
import be.entreprise.entreprise1.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public DataLoader(CategoryRepository categoryRepository,
                      ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {

        if (categoryRepository.count() > 0) return;

        Category kabels = new Category();
        kabels.setName("Kabels");

        Category belichting = new Category();
        belichting.setName("Belichting");

        categoryRepository.save(kabels);
        categoryRepository.save(belichting);

        Product p1 = new Product();
        p1.setName("XLR Kabel 5m");
        p1.setDescription("Audio kabel voor microfoons en mixers.");
        p1.setStock(20);
        p1.setCategory(kabels);

        Product p2 = new Product();
        p2.setName("LED Paneel");
        p2.setDescription("Dimbaar LED paneel voor podium.");
        p2.setStock(6);
        p2.setCategory(belichting);

        productRepository.save(p1);
        productRepository.save(p2);
    }
}
