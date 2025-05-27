package com.productService.productService.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequestDTO {

    @NotNull(message = "Name cannot be null")
    private String name;
    @NotBlank(message = "Description cannot be blank")
    private String description;
    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be positive")
    @Digits(integer = 6,fraction = 2, message = "Price must be a valid number with up to 6 digits and 2 decimal places")
    private double price;
    @NotNull(message = "Quantity cannot be null")
    @Positive(message = "Quantity must be positive")
    private int quantity;
    @NotNull(message = "TypeProduct cannot be blank")
    private String typeProduct;
}
