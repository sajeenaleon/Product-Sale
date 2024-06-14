package com.productsale.service;

import com.productsale.entity.Product;
import com.productsale.entity.Sale;
import com.productsale.repository.ProductRepository;
import com.productsale.repository.SaleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private ProductRepository productRepository;

    public Sale addSale(final int productId, final int quantity) {
        final Product product = productRepository.findByIdAndIsDeletedFalse(productId)
            .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (product.getQuantity() < quantity) throw new IllegalArgumentException("Not enough quantity in stock");

        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
        return saleRepository.save(new Sale(product.getId(), quantity, LocalDateTime.now(), false, product));
    }

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public Optional<Sale> getSaleById(final int id) {
        return saleRepository.findByIdAndIsDeletedFalse(id);
    }

    public Sale updateSale(final int saleId, final Sale updatedSale) {
        return saleRepository.findByIdAndIsDeletedFalse(saleId)
            .map(sale -> {
                sale.setQuantity(updatedSale.getQuantity())
                    .setSaleDate(updatedSale.getSaleDate())
                    .setProduct(updatedSale.getProduct());
                return saleRepository.save(sale);
            }).orElseThrow(() -> new RuntimeException("Sale not found with id: " + saleId));
    }

    public void deleteSale(final int id) {
        saleRepository.deleteSale(id);
    }
}
