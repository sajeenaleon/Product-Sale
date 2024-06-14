package com.productsale.repository;

import com.productsale.entity.Sale;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {

    Optional<Sale> findByIdAndIsDeletedFalse(final int saleId);

    List<Sale> findByProductIdAndIsDeletedFalse(final int productId);

    @Transactional
    @Modifying
    @Query("UPDATE Sale s SET s.isDeleted = true WHERE s.id = :id")
    void deleteSale(final int id);

    @Transactional
    @Modifying
    @Query("UPDATE Sale s SET s.isDeleted = true WHERE s.product.id = :id")
    void deleteSalesByProductId(final int id);
}
