package com.productsale.repository;

import com.productsale.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByIdAndIsDeletedFalse(int id);

    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.isDeleted = true WHERE p.id = :id")
    void deleteProduct(final int id);
}
