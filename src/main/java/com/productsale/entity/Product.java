package com.productsale.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private boolean isDeleted;
}