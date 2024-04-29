package com.sinugaud.springbootdatajpa.repository;

import com.javapedia.productify.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Custom queries or additional methods if needed
}
