package com.productsale.controller;

import com.productsale.entity.Product;
import com.productsale.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public Page<Product> getAllProducts(
        @RequestParam(defaultValue = "0") final int page,
        @RequestParam(defaultValue = "10") final int size
    ) {
        return productService.getAllProducts(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable final int id) {
        final Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody final Product product) {
        return new ResponseEntity<>(productService.addProduct(product), CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
        @PathVariable final int id,
        @RequestBody final Product product
    ) {
        try {
            return new ResponseEntity<>(productService.updateProduct(id, product), OK);
        } catch (final RuntimeException e) {
            return new ResponseEntity<>(null, NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable final int id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @GetMapping("/revenue")
    public double getTotalRevenue() {
        return productService.getTotalRevenue();
    }

    @GetMapping("/revenue/{productId}")
    public double getRevenueByProduct(@PathVariable final int productId) {
        return productService.getRevenueByProduct(productId);
    }
}