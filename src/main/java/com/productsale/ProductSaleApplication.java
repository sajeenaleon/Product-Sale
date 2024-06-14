package com.productsale;

import com.productsale.entity.Product;
import com.productsale.entity.Sale;
import com.productsale.service.ProductService;
import com.productsale.service.SaleService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootApplication
@AllArgsConstructor
public class ProductSaleApplication implements CommandLineRunner {

    private final ProductService productService;
    private final SaleService saleService;

    public static void main(String[] args) {
        SpringApplication.run(ProductSaleApplication.class, args);
    }

    @Override
    public void run(final String... args) {

        // Add product
        final Product product1 = new Product(0, "Product1", "Description1", 100.0, 100, false);
        final Product product2 = new Product(0, "Product2", "Description2", 200.0, 50, false);

        productService.addProduct(product1);
        productService.addProduct(product2);

        // Add Sale
        final Sale sale1 = saleService.addSale(product1.getId(), 2);
        final Sale sale2 = saleService.addSale(product2.getId(), 3);

        saleService.addSale(sale1.getProduct().getId(), sale1.getQuantity());
        saleService.addSale(sale2.getProduct().getId(), sale2.getQuantity());

        // Update Product
        product1.setDescription("description update for product 1");
        product2.setQuantity(50);

        productService.updateProduct(product1.getId(), product1);
        productService.updateProduct(product2.getId(), product2);

        // Update Sale
        sale1.setSaleDate(LocalDateTime.now());
        sale2.setQuantity(20);

        saleService.updateSale(sale1.getId(), sale1);
        saleService.updateSale(sale2.getId(), sale2);

        // Delete product
        productService.deleteProduct(product2.getId());

        // Delete Sale
        saleService.deleteSale(sale2.getId());

        // Get Product by ID
        Optional<Product> product = productService.getProductById(product1.getId());
        System.out.println(product.map(Product::getName).orElse("No Product"));

        // Get Sale by ID
        Optional<Sale> sale = saleService.getSaleById(sale1.getId());
        System.out.println(sale.map(Sale::getQuantity).orElse(0));

        System.out.println("Total Revenue: " + productService.getTotalRevenue());
        System.out.println("Revenue for Product 1: " + productService.getRevenueByProduct(product1.getId()));
        System.out.println("Revenue for Product 2: " + productService.getRevenueByProduct(product2.getId()));
    }
}
