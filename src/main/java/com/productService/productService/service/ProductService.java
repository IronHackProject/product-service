package com.productService.productService.service;

import com.productService.productService.dto.CreatedProductRequestDTO;
import com.productService.productService.dto.DeleteProductRequestDTO;
import com.productService.productService.dto.UpdateProductRequestDTO;
import com.productService.productService.enums.TypeProductEnum;
import com.productService.productService.exceptions.customExceptions.ProductExceptions;
import com.productService.productService.model.Product;
import com.productService.productService.repository.ProductRepository;

import jakarta.persistence.EnumType;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public ResponseEntity<?> createdProduct(CreatedProductRequestDTO dto) {
        if (isValidEnumValue(dto.getTypeProduct())) {
            throw new ProductExceptions("Invalid product type: " + dto.getTypeProduct());
        }

        TypeProductEnum typeProduct = TypeProductEnum.valueOf(dto.getTypeProduct().toUpperCase());

        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setTypeProduct(typeProduct);

        productRepository.save(product);

        return ResponseEntity.ok("Product created successfully");
    }


    // This method retrieves all products from the repository
    public ResponseEntity<?> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            return ResponseEntity.ok("No products found");
        }
        return ResponseEntity.ok(products);
    }

    // This method retrieves a product by its type from the repository
    public ResponseEntity<?> getProductByType(String typeProduct) {
        if (isValidEnumValue(typeProduct)) {
            throw new ProductExceptions("Invalid product type: " + typeProduct);
        }

        TypeProductEnum type = TypeProductEnum.valueOf(typeProduct.toUpperCase());
        List<Product> products = productRepository.findByTypeProduct(type);

        if (products.isEmpty()) {
            return ResponseEntity.ok("No products found for type: " + typeProduct);
        }
        return ResponseEntity.ok(products);
    }


    // this method use FeingClient
    public ResponseEntity<?> getProductById(Long id) {
        return productRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    public ResponseEntity<?> updateProduct(UpdateProductRequestDTO dto) {
        if (isValidEnumValue(dto.getTypeProduct())) {
            throw new ProductExceptions("Invalid product type: " + dto.getTypeProduct());
        }

        TypeProductEnum typeProduct = TypeProductEnum.valueOf(dto.getTypeProduct().toUpperCase());
        Optional<Product> productid = productRepository.findById(dto.getId());
        if (productid.isEmpty()) {
            throw new ProductExceptions("Product not found with id: " + dto.getId());
        }
        return productid
                .map(product -> {
                    product.setName(dto.getName());
                    product.setDescription(dto.getDescription());
                    product.setPrice(dto.getPrice());
                    product.setQuantity(dto.getQuantity());
                    product.setTypeProduct(typeProduct);
                    productRepository.save(product);
                    return ResponseEntity.ok("Product updated successfully");
                })
                .orElseThrow(() -> new ProductExceptions("Product not found with id: " + dto.getId()));
    }

    public ResponseEntity<?> deleteProduct(@Valid DeleteProductRequestDTO dto) {
        Optional<Product> product = productRepository.findById(dto.getId());
        if (product.isEmpty()) {
            throw new ProductExceptions("Product not found with id: " + dto.getId());
        }
        productRepository.delete(product.get());
        return ResponseEntity.ok("Product deleted successfully");
    }


    // This method checks if the provided value is a valid enum value for TypeProductEnum
    public boolean isValidEnumValue(String value) {
        return Arrays.stream(EnumType.values())
                .noneMatch(enumValue -> enumValue.name().equalsIgnoreCase(value));
    }


    // this methos used FeingClient
    public ResponseEntity<?> subQuantity(Long id, int quantity) {
        Product productQuantity = productRepository.findQuantityById(id);
        if ((productQuantity.getQuantity() - quantity) >= 0) {
            productQuantity.setQuantity(productQuantity.getQuantity() - quantity);
            productRepository.save(productQuantity);
            return ResponseEntity.ok().build();
        }
        throw new ProductExceptions("Insufficient quantity for product with id: " + id);
    }
}
