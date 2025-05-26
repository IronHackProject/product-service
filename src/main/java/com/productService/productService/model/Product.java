package com.productService.productService.model;

import com.productService.productService.enums.TypeProductEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private int quantity;
    @Enumerated(EnumType.STRING)
    private TypeProductEnum typeProduct;
}
