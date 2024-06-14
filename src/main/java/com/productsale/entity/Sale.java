package com.productsale.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Sale {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;
    private int quantity;
    private LocalDateTime saleDate;
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}