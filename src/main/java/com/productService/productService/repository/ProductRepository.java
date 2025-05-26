package com.productService.productService.repository;

import com.productService.productService.enums.TypeProductEnum;
import com.productService.productService.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByTypeProduct(TypeProductEnum type);
}
