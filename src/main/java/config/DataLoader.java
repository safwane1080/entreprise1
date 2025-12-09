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

        if (categoryRepository.count() > 0) return;

        // Categories
        Category lighting = new Category();
        lighting.setName("Belichting");

        Category cables = new Category();
        cables.setName("Kabels");

        Category control = new Category();
        control.setName("Controlepanelen");

        categoryRepository.save(lighting);
        categoryRepository.save(cables);
        categoryRepository.save(control);

        // Products
        productRepository.save(new Product(null, "LED Paneel", "Krachtig LED paneel", lighting, true));
        productRepository.save(new Product(null, "Spotlight", "Podium spotlight", lighting, true));
        productRepository.save(new Product(null, "DMX Lamp", "DMX-gestuurde lamp", lighting, true));

        productRepository.save(new Product(null, "XLR Kabel", "Professionele audiokabel", cables, true));
        productRepository.save(new Product(null, "HDMI Kabel", "High-speed HDMI kabel", cables, true));
        productRepository.save(new Product(null, "Verlengkabel", "10m stroomkabel", cables, true));

        productRepository.save(new Product(null, "DMX Controller", "Lichtcontroller", control, true));
        productRepository.save(new Product(null, "Mixing Panel", "Audio mengpaneel", control, true));
        productRepository.save(new Product(null, "Touch Panel", "Touch-bediening", control, true));
        productRepository.save(new Product(null, "Power Unit", "Stroomregelaar", control, true));
    }
}
