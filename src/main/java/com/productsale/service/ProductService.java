package com.productsale.service;

import com.productsale.entity.Product;
import com.productsale.repository.ProductRepository;
import com.productsale.repository.SaleRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;

    public Page<Product> getAllProducts(final Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Optional<Product> getProductById(final int id) {
        return productRepository.findByIdAndIsDeletedFalse(id);
    }

    public Product addProduct(final Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(final int id, final Product updatedProduct) {
        return productRepository.findByIdAndIsDeletedFalse(id)
            .map(product -> {
                product.setName(updatedProduct.getName())
                    .setDescription(updatedProduct.getDescription())
                    .setPrice(updatedProduct.getPrice())
                    .setQuantity(updatedProduct.getQuantity());
                return productRepository.save(product);
            }).orElseThrow(() -> new RuntimeException("Product not found with id " + id));
    }

    public void deleteProduct(final int id) {
        productRepository.deleteProduct(id);
        saleRepository.deleteSalesByProductId(id);
    }

    public double getTotalRevenue() {
        return saleRepository.findAll()
            .stream()
            .mapToDouble(sale -> sale.getQuantity() * sale.getProduct().getPrice())
            .sum();
    }

    public double getRevenueByProduct(final int productId) {
        return saleRepository.findByProductIdAndIsDeletedFalse(productId)
            .stream()
            .mapToDouble(sale -> sale.getQuantity() * sale.getProduct().getPrice())
            .sum();
    }
}
