package com.productService.productService.dto;

import com.productService.productService.enums.TypeProductEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatedProductRequestDTO {
    @NotNull(message = "Name cannot be blank")
    private String name;
    @NotNull(message = "Description cannot be blank")
    private String description;
    @NotNull(message = "Price cannot be blank")
    @Digits(integer = 6,fraction = 2, message = "Price must be a valid number with up to 6 digits and 2 decimal places")
    private double price;
    @NotNull(message = "Quantity cannot be blank")
    private int quantity;
    private String typeProduct;

}
