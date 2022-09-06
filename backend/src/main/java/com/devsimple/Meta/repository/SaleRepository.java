package com.devsimple.Meta.repository;

import com.devsimple.Meta.model.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface SaleRepository extends JpaRepository<Sale, String> {

    @Query("SELECT obj FROM Sale obj WHERE obj.date BETWEEN :min AND :max ")
    Page<Sale> findSales(LocalDate min, LocalDate max, Pageable pageable);
}