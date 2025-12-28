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

        // Voorkomt dubbele data bij herstart
        if (categoryRepository.count() > 0) return;

        Category belichting = new Category();
        belichting.setName("Belichting");

        Category kabels = new Category();
        kabels.setName("Kabels");

        Category audio = new Category();
        audio.setName("Audio");

        Category podium = new Category();
        podium.setName("Podium & Rigging");

        categoryRepository.save(belichting);
        categoryRepository.save(kabels);
        categoryRepository.save(audio);
        categoryRepository.save(podium);

        Product p1 = new Product();
        p1.setName("Aputure Amaran 200x");
        p1.setDescription("Bi-color LED paneel voor film en studio-opnames.");
        p1.setStock(6);
        p1.setCategory(belichting);

        Product p2 = new Product();
        p2.setName("Godox SL60W");
        p2.setDescription("Krachtige LED lamp voor videoproducties.");
        p2.setStock(4);
        p2.setCategory(belichting);

        Product p3 = new Product();
        p3.setName("RGB LED Tube");
        p3.setDescription("RGB tube voor creatieve belichting.");
        p3.setStock(8);
        p3.setCategory(belichting);


        Product p4 = new Product();
        p4.setName("XLR Kabel 5m");
        p4.setDescription("Audio kabel voor microfoons en mixers.");
        p4.setStock(20);
        p4.setCategory(kabels);

        Product p5 = new Product();
        p5.setName("XLR Kabel 10m");
        p5.setDescription("Lange XLR kabel voor podiumgebruik.");
        p5.setStock(12);
        p5.setCategory(kabels);

        Product p6 = new Product();
        p6.setName("HDMI Kabel 10m");
        p6.setDescription("HDMI kabel voor projectoren en schermen.");
        p6.setStock(8);
        p6.setCategory(kabels);

        Product p7 = new Product();
        p7.setName("Shure SM58");
        p7.setDescription("Dynamische microfoon voor zang en spraak.");
        p7.setStock(5);
        p7.setCategory(audio);

        Product p8 = new Product();
        p8.setName("Audio Interface Focusrite 2i2");
        p8.setDescription("USB audio interface voor opnames.");
        p8.setStock(4);
        p8.setCategory(audio);

        Product p9 = new Product();
        p9.setName("Mengpaneel Behringer Xenyx");
        p9.setDescription("Compact mengpaneel voor live audio.");
        p9.setStock(2);
        p9.setCategory(audio);


        Product p10 = new Product();
        p10.setName("Podiumelement 2x1m");
        p10.setDescription("Modulair podiumelement.");
        p10.setStock(12);
        p10.setCategory(podium);

        Product p11 = new Product();
        p11.setName("Rookmachine Chauvet");
        p11.setDescription("Rookmachine voor theater en shows.");
        p11.setStock(2);
        p11.setCategory(podium);

        Product p12 = new Product();
        p12.setName("Safety kabel");
        p12.setDescription("Beveiligingskabel voor lichtinstallaties.");
        p12.setStock(20);
        p12.setCategory(podium);

        productRepository.save(p1);
        productRepository.save(p2);
        productRepository.save(p3);
        productRepository.save(p4);
        productRepository.save(p5);
        productRepository.save(p6);
        productRepository.save(p7);
        productRepository.save(p8);
        productRepository.save(p9);
        productRepository.save(p10);
        productRepository.save(p11);
        productRepository.save(p12);
    }
}
