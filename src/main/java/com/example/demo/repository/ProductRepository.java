package com.example.demo.repository;

import com.example.demo.domain.entity.Product;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {

    @Query("SELECT DISTINCT product.* from Product product WHERE product.code=:code")
    Optional<Product> findByCode(@Param("code") String code);
}
