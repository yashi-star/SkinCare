package com.hcl.product_service.controller;

import com.hcl.product_service.dto.ProductDTO;
import com.hcl.product_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class ProductController {
    
    private final ProductService productService;
    
    /**
     * Create a new product
     * POST /products
     */
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        log.info("REST request to create product: {}", productDTO.getName());
        ProductDTO createdProduct = productService.createProduct(productDTO);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }
    
    /**
     * Get all products
     * GET /products
     * GET /products?active=true
     */
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(
            @RequestParam(required = false) Boolean active) {
        
        log.info("REST request to get all products. Active filter: {}", active);
        
        List<ProductDTO> products;
        if (active != null && active) {
            products = productService.getActiveProducts();
        } else {
            products = productService.getAllProducts();
        }
        
        return ResponseEntity.ok(products);
    }
    
    /**
     * Get product by ID
     * GET /products/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        log.info("REST request to get product by ID: {}", id);
        ProductDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }
    
    /**
     * Get products by category
     * GET /products/category/{category}
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable String category) {
        log.info("REST request to get products by category: {}", category);
        List<ProductDTO> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }
    
    /**
     * Get products by skin type
     * GET /products/skintype/{skinType}
     */
    @GetMapping("/skintype/{skinType}")
    public ResponseEntity<List<ProductDTO>> getProductsBySkinType(@PathVariable String skinType) {
        log.info("REST request to get products by skin type: {}", skinType);
        List<ProductDTO> products = productService.getProductsBySkinType(skinType);
        return ResponseEntity.ok(products);
    }
    
    /**
     * Get products by brand
     * GET /products/brand/{brand}
     */
    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<ProductDTO>> getProductsByBrand(@PathVariable String brand) {
        log.info("REST request to get products by brand: {}", brand);
        List<ProductDTO> products = productService.getProductsByBrand(brand);
        return ResponseEntity.ok(products);
    }
    
    /**
     * Search products by name
     * GET /products/search?name=cleanser
     */
    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProducts(@RequestParam String name) {
        log.info("REST request to search products with name: {}", name);
        List<ProductDTO> products = productService.searchProductsByName(name);
        return ResponseEntity.ok(products);
    }
    
    /**
     * Update product
     * PUT /products/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductDTO productDTO) {
        
        log.info("REST request to update product with ID: {}", id);
        ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(updatedProduct);
    }
    
    /**
     * Delete product (soft delete - marks as inactive)
     * DELETE /products/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable Long id) {
        log.info("REST request to delete product with ID: {}", id);
        productService.deleteProduct(id);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Product marked as inactive successfully");
        response.put("productId", id.toString());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Permanently delete product (hard delete - removes from database)
     * DELETE /products/{id}/permanent
     */
    @DeleteMapping("/{id}/permanent")
    public ResponseEntity<Map<String, String>> permanentlyDeleteProduct(@PathVariable Long id) {
        log.info("REST request to permanently delete product with ID: {}", id);
        productService.permanentlyDeleteProduct(id);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Product permanently deleted successfully");
        response.put("productId", id.toString());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Update stock (called by Order Service via Feign Client)
     * PUT /products/{id}/stock?quantity=2
     */
    @PutMapping("/{id}/stock")
    public ResponseEntity<Map<String, String>> updateStock(
            @PathVariable Long id,
            @RequestParam Integer quantity) {
        
        log.info("REST request to update stock for product ID: {}, quantity: {}", id, quantity);
        productService.updateStock(id, quantity);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Stock updated successfully");
        response.put("productId", id.toString());
        response.put("quantityDeducted", quantity.toString());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Check stock availability
     * GET /products/{id}/stock/check?quantity=5
     */
    @GetMapping("/{id}/stock/check")
    public ResponseEntity<Map<String, Object>> checkStockAvailability(
            @PathVariable Long id,
            @RequestParam Integer quantity) {
        
        log.info("REST request to check stock for product ID: {}, quantity: {}", id, quantity);
        boolean available = productService.checkStockAvailability(id, quantity);
        
        Map<String, Object> response = new HashMap<>();
        response.put("productId", id);
        response.put("requestedQuantity", quantity);
        response.put("available", available);
        
        return ResponseEntity.ok(response);
    }
}