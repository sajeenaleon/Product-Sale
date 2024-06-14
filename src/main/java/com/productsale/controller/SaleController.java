package com.productsale.controller;

import com.productsale.entity.Sale;
import com.productsale.service.SaleService;
import lombok.AllArgsConstructor;
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

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/sale")
@AllArgsConstructor
public class SaleController {

    private SaleService saleService;

    @PostMapping("/add")
    public ResponseEntity<Sale> addSale(@RequestParam final int productId, @RequestParam final int quantity) {
        return ResponseEntity.ok(saleService.addSale(productId, quantity));
    }

    @GetMapping
    public ResponseEntity<List<Sale>> getAllSales() {
        return ResponseEntity.ok(saleService.getAllSales());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sale> getSaleById(@PathVariable final int id) {
        final Optional<Sale> sale = saleService.getSaleById(id);
        return sale.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sale> updateSale(@PathVariable int id, @RequestBody Sale updatedSale) {
        try {
            return ResponseEntity.ok(saleService.updateSale(id, updatedSale));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable final int id) {
        saleService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }
}
