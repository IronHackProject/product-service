package com.productService.productService.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequestDTO {
    @NotBlank(message = "ID cannot be blank")
    private long id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String typeProduct;
}
