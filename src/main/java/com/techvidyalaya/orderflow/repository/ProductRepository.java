package com.techvidyalaya.orderflow.repository;

import com.techvidyalaya.orderflow.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {}
