package com.productService.productService.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteProductRequestDTO {
    @NotBlank(message = "ID cannot be blank")
    private long id;
}
