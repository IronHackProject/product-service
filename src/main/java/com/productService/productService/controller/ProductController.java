package com.productService.productService.controller;

import com.productService.productService.dto.CreatedProductRequestDTO;
import com.productService.productService.dto.DeleteProductRequestDTO;
import com.productService.productService.dto.UpdateProductRequestDTO;
import com.productService.productService.exceptions.customExceptions.ProductExceptions;
import com.productService.productService.model.Product;
import com.productService.productService.repository.ProductRepository;
import com.productService.productService.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;
    private final ProductRepository productRepository;

    public ProductController(ProductService productService, ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.productService = productService;
    }


    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody @Valid CreatedProductRequestDTO dto) {
        return productService.createdProduct(dto);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/find/{typeProduct}")
    public ResponseEntity<?> getProductById(@PathVariable String typeProduct) {
        return productService.getProductByType(typeProduct);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable long id,@RequestBody @Valid UpdateProductRequestDTO dto) {
        return productService.updateProduct(id,dto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id) {
        return productService.deleteProduct(id);
    }

    // this method retrieves a product by its ID
    // use by feingClient.
    @GetMapping("/type/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    // used by FeingClient
    @GetMapping("/{id}/{quantity}")
    public boolean isProductAvailable(@PathVariable Long id, @PathVariable int quantity) {
        logger.info("Comprobando disponibilidad del producto con id {} y cantidad {}", id, quantity);
        return productService.isProductAvailable(id, quantity);
    }

    // thismethod used FeingCleint
    // sub the quantity and update the product quantity
    @PostMapping("/subQuantity/{id}/{quantity}")
    public ResponseEntity<?> subQuantity(@PathVariable Long id, @PathVariable int quantity) {
        return productService.subQuantity(id, quantity);
    }





}
