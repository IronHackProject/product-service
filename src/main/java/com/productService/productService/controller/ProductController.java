package com.productService.productService.controller;

import com.productService.productService.dto.CreatedProductRequestDTO;
import com.productService.productService.dto.DeleteProductRequestDTO;
import com.productService.productService.dto.UpdateProductRequestDTO;
import com.productService.productService.model.Product;
import com.productService.productService.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
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

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteProduct(@RequestBody @Valid DeleteProductRequestDTO dto) {
        return productService.deleteProduct(dto);
    }

    // this method retrieves a product by its ID
    // use by feingClient.
    @GetMapping("/find/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/find/{id}/{quantity}")
    public boolean isProductAvailable(@PathVariable Long id, @PathVariable int quantity) {
        return productService.isProductAvailable(id, quantity);
    }

    // thismethod used FeingCleint
    // sub the quantity and update the product quantity
    @PatchMapping("/subQuantity/{id}/{quantity}")
    public ResponseEntity<?> subQuantity(@PathVariable Long id, @PathVariable int quantity) {
        return productService.subQuantity(id, quantity);
    }



}
