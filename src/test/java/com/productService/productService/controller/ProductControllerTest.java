package com.productService.productService.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.productService.productService.dto.CreatedProductRequestDTO;
import com.productService.productService.dto.UpdateProductRequestDTO;
import com.productService.productService.model.Product;
import com.productService.productService.repository.ProductRepository;
import com.productService.productService.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @MockitoBean
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private CreatedProductRequestDTO productDTO;
    private UpdateProductRequestDTO updateDTO;

    @BeforeEach
    void setup() {
        productDTO = new CreatedProductRequestDTO();
        productDTO.setName("Test Product");
        productDTO.setDescription("Test Desc");
        productDTO.setPrice(99.99);
        productDTO.setQuantity(10);
        productDTO.setTypeProduct("ELECTRONICS");

        updateDTO = new UpdateProductRequestDTO();
        updateDTO.setName("Updated Product");
        updateDTO.setDescription("Updated Desc");
        updateDTO.setPrice(199.99);
        updateDTO.setQuantity(5);
        updateDTO.setTypeProduct("ELECTRONICS");
    }

    @Test
    void testCreateProduct() throws Exception {
        Mockito.when(productService.createdProduct(any())).thenReturn(ResponseEntity.ok("Product created successfully"));

        mockMvc.perform(post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Product created successfully"));
    }
    @Test
    @DisplayName("Debe retornar errores de validación cuando se envía un producto inválido")
    void testCreateProduct_InvalidFields_ReturnsValidationErrors() throws Exception {
        CreatedProductRequestDTO dto = new CreatedProductRequestDTO();
        dto.setName(null);
       // dto.setDescription(""); // Uncomment to test empty description
        dto.setPrice(1234567.891);
        dto.setQuantity(0);
        dto.setTypeProduct(null);

        mockMvc.perform(post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Name cannot be blank"))
                .andExpect(jsonPath("$.description").value("Description cannot be blank"))
                .andExpect(jsonPath("$.price").value("Price must be a valid number with up to 6 digits and 2 decimal places"));

    }


    @Test
    void testGetAllProducts() throws Exception {
        Product product = new Product();
        product.setName("Product1");
        Mockito.when(productService.getAllProducts()).thenReturn(ResponseEntity.ok(List.of(product)));

        mockMvc.perform(get("/api/product/all"))
                .andExpect(status().isOk());

    }

    @Test
    void testGetProductByType() throws Exception {
        Mockito.when(productService.getProductByType("electronics")).thenReturn(ResponseEntity.ok(Collections.emptyList()));

        mockMvc.perform(get("/api/product/find/electronics"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateProduct() throws Exception {
        Mockito.when(productService.updateProduct(eq(1L), any(UpdateProductRequestDTO.class)))
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(put("/api/product/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk());
    }
    @Test
    void updateProduct_whenInvalidFields_shouldReturnValidationErrors() throws Exception {
        UpdateProductRequestDTO dto = new UpdateProductRequestDTO();


        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(put("/api/product/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Name cannot be null"))
                .andExpect(jsonPath("$.description").value("Description cannot be blank"))
                .andExpect(jsonPath("$.price").exists())
                .andExpect(jsonPath("$.quantity").exists())
                .andExpect(jsonPath("$.typeProduct").value("TypeProduct cannot be blank"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        Mockito.when(productService.deleteProduct(1L)).thenReturn(ResponseEntity.ok("Product deleted successfully"));

        mockMvc.perform(delete("/api/product/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Product deleted successfully"));
    }

    @Test
    void testGetProductById() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");

        Mockito.when(productService.getProductById(1L)).thenReturn(ResponseEntity.ok(product));

        mockMvc.perform(get("/api/product/type/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Product"));
    }

    @Test
    void testIsProductAvailable() throws Exception {
        Mockito.when(productService.isProductAvailable(1L, 2)).thenReturn(true);

        mockMvc.perform(get("/api/product/1/2"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void testSubQuantity() throws Exception {
        Mockito.when(productService.subQuantity(1L, 2)).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(post("/api/product/subQuantity/1/2"))
                .andExpect(status().isOk());
    }
}
