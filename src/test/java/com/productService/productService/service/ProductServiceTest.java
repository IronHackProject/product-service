package com.productService.productService.service;

import com.productService.productService.dto.CreatedProductRequestDTO;
import com.productService.productService.dto.UpdateProductRequestDTO;
import com.productService.productService.enums.TypeProductEnum;
import com.productService.productService.exceptions.customExceptions.ProductExceptions;
import com.productService.productService.model.Product;
import com.productService.productService.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private AutoCloseable closeable;

    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }
    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testCreateProduct_success() {
        CreatedProductRequestDTO dto = new CreatedProductRequestDTO(
                "Phone", "Latest smartphone", 999.99, 10, "ELECTRONICS");

        Product savedProduct = new Product();
        savedProduct.setName(dto.getName());

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        ResponseEntity<String> response = productService.createdProduct(dto);
        assertEquals("Product created successfully", response.getBody());
    }

    @Test
    void testCreateProduct_invalidType() {
        CreatedProductRequestDTO dto = new CreatedProductRequestDTO(
                "Phone", "Latest smartphone", 999.99, 10, "INVALID_TYPE");

        assertThrows(ProductExceptions.class, () -> productService.createdProduct(dto));
    }

    @Test
    void testGetAllProducts_success() {
        Product product = new Product();
        product.setName("Test");

        when(productRepository.findAll()).thenReturn(List.of(product));

        ResponseEntity<List<Product>> response = productService.getAllProducts();
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetProductByType_success() {
        Product product = new Product();
        product.setTypeProduct(TypeProductEnum.ELECTRONICS);

        when(productRepository.findByTypeProduct(TypeProductEnum.ELECTRONICS))
                .thenReturn(List.of(product));

        ResponseEntity<?> response = productService.getProductByType("ELECTRONICS");
        assertTrue(response.getBody() instanceof List);
    }

    @Test
    void testGetProductByType_invalid() {
        assertThrows(ProductExceptions.class, () -> productService.getProductByType("INVALID"));
    }

    @Test
    void testGetProductById_found() {
        Product product = new Product();
        product.setId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ResponseEntity<Product> response = productService.getProductById(1L);
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void testGetProductById_notFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<Product> response = productService.getProductById(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testUpdateProduct_success() {
        Product product = new Product();
        product.setId(1L);

        UpdateProductRequestDTO dto = new UpdateProductRequestDTO(
                "Updated", "Updated", 100.0, 5, "ELECTRONICS");

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any())).thenReturn(product);

        ResponseEntity<?> response = productService.updateProduct(1L, dto);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testUpdateProduct_notFound() {
        UpdateProductRequestDTO dto = new UpdateProductRequestDTO(
                "Updated", "Updated", 100.0, 5, "ELECTRONICS");

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductExceptions.class, () -> productService.updateProduct(1L, dto));
    }

    @Test
    void testDeleteProduct_success() {
        Product product = new Product();
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).delete(product);

        ResponseEntity<?> response = productService.deleteProduct(1L);
        assertEquals("Product deleted successfully", response.getBody());
    }

    @Test
    void testDeleteProduct_notFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ProductExceptions.class, () -> productService.deleteProduct(1L));
    }

    @Test
    void testSubQuantity_success() {
        Product product = new Product();
        product.setQuantity(10);

        when(productRepository.findQuantityById(1L)).thenReturn(product);
        when(productRepository.save(any())).thenReturn(product);

        ResponseEntity<?> response = productService.subQuantity(1L, 5);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testIsProductAvailable_success() {
        Product product = new Product();
        product.setQuantity(10);

        when(productRepository.findQuantityById(1L)).thenReturn(product);

        assertTrue(productService.isProductAvailable(1L, 5));
    }

    @Test
    void testIsProductAvailable_insufficient() {
        Product product = new Product();
        product.setQuantity(2);

        when(productRepository.findQuantityById(1L)).thenReturn(product);

        assertThrows(ProductExceptions.class, () -> productService.isProductAvailable(1L, 5));
    }


}
