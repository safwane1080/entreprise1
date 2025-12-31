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
        p1.setPricePerDay(15.00);
        p1.setImageUrl("https://www.kamera-express.be/_ipx/b_%23ffffff00,f_webp,fit_contain,s_484x484/https://www.kamera-express.nl/media/92211cef-cb07-49a1-8372-7942fc304316/amaran-200x.jpg");


        Product p2 = new Product();
        p2.setName("Godox SL60W");
        p2.setDescription("Krachtige LED lamp voor videoproducties.");
        p2.setStock(4);
        p2.setCategory(belichting);
        p2.setPricePerDay(12.00);
        p2.setImageUrl("https://www.kamera-express.be/media/06abba71-0421-4a98-bc2d-5ad3e982d25f/godox-sl60w.jpg");


        Product p3 = new Product();
        p3.setName("RGB LED Tube");
        p3.setDescription("RGB tube voor creatieve belichting.");
        p3.setStock(8);
        p3.setCategory(belichting);
        p3.setPricePerDay(8.00);
        p3.setImageUrl("https://lumecube.com/cdn/shop/files/230920_Shopify_TubeLightXL2Pack_01_1160x1450_8a46d5e0-2cfd-4f58-be1c-1d34b31ef745_1160x.jpg?v=1695226764");


        Product p4 = new Product();
        p4.setName("XLR Kabel 5m");
        p4.setDescription("Audio kabel voor microfoons en mixers.");
        p4.setStock(20);
        p4.setCategory(kabels);
        p4.setPricePerDay(2.00);
        p4.setImageUrl("https://static.bax-shop.es/image/product/56144/4759099/d90aa44a/1700657000Devine%20MIC100%20XLR%2010m.jpg");


        Product p5 = new Product();
        p5.setName("XLR Kabel 10m");
        p5.setDescription("Lange XLR kabel voor podiumgebruik.");
        p5.setStock(12);
        p5.setCategory(kabels);
        p5.setPricePerDay(3.00);
        p5.setImageUrl("https://static.bax-shop.es/image/product/56144/4759099/d90aa44a/1700657000Devine%20MIC100%20XLR%2010m.jpg");


        Product p6 = new Product();
        p6.setName("HDMI Kabel 10m");
        p6.setDescription("HDMI kabel voor projectoren en schermen.");
        p6.setStock(8);
        p6.setCategory(kabels);
        p6.setPricePerDay(3.50);
        p6.setImageUrl("https://www.interactivetouch.be/wp-content/uploads/2021/09/HDMI-Kabel-High-Speed-3m-4K-UHD.jpeg");


        Product p7 = new Product();
        p7.setName("Shure SM58");
        p7.setDescription("Dynamische microfoon voor zang en spraak.");
        p7.setStock(5);
        p7.setCategory(audio);
        p7.setPricePerDay(7.00);
        p7.setImageUrl("https://media.s-bol.com/BvqBqN2VDwr2/njWnpE/550x442.jpg");


        Product p8 = new Product();
        p8.setName("Focusrite Scarlett 2i2");
        p8.setDescription("USB audio interface voor opnames.");
        p8.setStock(4);
        p8.setCategory(audio);
        p8.setPricePerDay(10.00);
        p8.setImageUrl("https://m.media-amazon.com/images/I/61d27qcYtvL.jpg");

        Product p9 = new Product();
        p9.setName("Behringer Xenyx Mengpaneel");
        p9.setDescription("Compact mengpaneel voor live audio.");
        p9.setStock(2);
        p9.setCategory(audio);
        p9.setPricePerDay(18.00);
        p9.setImageUrl("https://static.bax-shop.es/image/product/50091/3525359/853ec89e/450x450/1650612728QX1222USB_P0AKX_Right_XL_JPG.jpg");



        Product p10 = new Product();
        p10.setName("Podiumelement 2x1m");
        p10.setDescription("Modulair podiumelement.");
        p10.setStock(12);
        p10.setCategory(podium);
        p10.setPricePerDay(20.00);
        p10.setImageUrl("https://cdn.webshopapp.com/shops/348837/files/451048133/showgear-70620-mammouth-podium-2x1m-500-kg-m2.jpg");

        Product p11 = new Product();
        p11.setName("Rookmachine Chauvet");
        p11.setDescription("Rookmachine voor theater en shows.");
        p11.setStock(2);
        p11.setCategory(podium);
        p11.setPricePerDay(25.00);
        p11.setImageUrl("https://axse.be/wp-content/uploads/2022/11/Rookmachine-huren-hqsm10001.png");

        Product p12 = new Product();
        p12.setName("Safety kabel");
        p12.setDescription("Beveiligingskabel voor lichtinstallaties.");
        p12.setStock(20);
        p12.setCategory(podium);
        p12.setPricePerDay(1.00);
        p12.setImageUrl("https://assets.highlite.com/media/catalog/product/cache/e5fd06d2b22125e4d45c42fcb78267f3/7/1/71700_65.png");


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
