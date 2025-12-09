package be.entreprise.entreprise1.config;

import be.entreprise.entreprise1.model.Category;
import be.entreprise.entreprise1.model.Product;
import be.entreprise.entreprise1.repository.CategoryRepository;
import be.entreprise.entreprise1.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public void run(String... args) {

        // Als er al data is, doe niets
        if (categoryRepository.count() > 0) {
            return;
        }

        // Categorieën
        Category lighting = new Category();
        lighting.setName("Belichting");
        categoryRepository.save(lighting);

        Category cables = new Category();
        cables.setName("Kabels");
        categoryRepository.save(cables);

        Category control = new Category();
        control.setName("Controlepanelen");
        categoryRepository.save(control);

        // Producten voor Belichting
        Product p1 = new Product();
        p1.setName("LED Paneel");
        p1.setDescription("Krachtig LED-paneel voor studio of podium");
        p1.setCategory(lighting);
        p1.setAvailable(true);
        productRepository.save(p1);

        Product p2 = new Product();
        p2.setName("Spotlight");
        p2.setDescription("Gerichte spotlight voor podium");
        p2.setCategory(lighting);
        p2.setAvailable(true);
        productRepository.save(p2);

        // Producten voor Kabels
        Product p3 = new Product();
        p3.setName("XLR Kabel 5m");
        p3.setDescription("Professionele audiokabel 5 meter");
        p3.setCategory(cables);
        p3.setAvailable(true);
        productRepository.save(p3);

        Product p4 = new Product();
        p4.setName("HDMI Kabel 10m");
        p4.setDescription("High-speed HDMI kabel");
        p4.setCategory(cables);
        p4.setAvailable(true);
        productRepository.save(p4);

        // Producten voor Controlepanelen
        Product p5 = new Product();
        p5.setName("DMX Controller");
        p5.setDescription("Controller voor DMX-licht");
        p5.setCategory(control);
        p5.setAvailable(true);
        productRepository.save(p5);

        Product p6 = new Product();
        p6.setName("Audio Mengpaneel");
        p6.setDescription("Mixing console voor audio");
        p6.setCategory(control);
        p6.setAvailable(true);
        productRepository.save(p6);
    }
}
