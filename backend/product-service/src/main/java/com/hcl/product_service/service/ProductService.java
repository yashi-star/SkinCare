package com.hcl.product_service.service;

import com.hcl.product_service.dto.ProductDTO;
import com.hcl.product_service.entity.Product;
import com.hcl.product_service.exception.BadRequestException;
import com.hcl.product_service.exception.InsufficientStockException;
import com.hcl.product_service.exception.ResourceNotFoundException;
import com.hcl.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    
    private final ProductRepository productRepository;
    
    /**
     * Create a new product
     */
    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        log.info("Creating new product: {}", productDTO.getName());
        
        // Business validation
        if (productDTO.getPrice().doubleValue() <= 0) {
            throw new BadRequestException("Price must be greater than 0");
        }
        
        if (productDTO.getStock() < 0) {
            throw new BadRequestException("Stock cannot be negative");
        }
        
        Product product = mapToEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        
        log.info("Product created successfully with ID: {}", savedProduct.getId());
        return mapToDTO(savedProduct);
    }
    
    /**
     * Get all products
     */
    public List<ProductDTO> getAllProducts() {
        log.info("Fetching all products");
        return productRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Get all active products
     */
    public List<ProductDTO> getActiveProducts() {
        log.info("Fetching active products");
        return productRepository.findByActiveTrue()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Get product by ID
     */
    public ProductDTO getProductById(Long id) {
        log.info("Fetching product with ID: {}", id);
        
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        
        return mapToDTO(product);
    }
    
    /**
     * Get products by category
     */
    public List<ProductDTO> getProductsByCategory(String category) {
        log.info("Fetching products by category: {}", category);
        
        if (category == null || category.trim().isEmpty()) {
            throw new BadRequestException("Category cannot be empty");
        }
        
        return productRepository.findByCategory(category)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Get products by skin type
     */
    public List<ProductDTO> getProductsBySkinType(String skinType) {
        log.info("Fetching products by skin type: {}", skinType);
        
        if (skinType == null || skinType.trim().isEmpty()) {
            throw new BadRequestException("Skin type cannot be empty");
        }
        
        return productRepository.findBySkinType(skinType)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Get products by brand
     */
    public List<ProductDTO> getProductsByBrand(String brand) {
        log.info("Fetching products by brand: {}", brand);
        
        if (brand == null || brand.trim().isEmpty()) {
            throw new BadRequestException("Brand cannot be empty");
        }
        
        return productRepository.findByBrand(brand)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Search products by name
     */
    public List<ProductDTO> searchProductsByName(String name) {
        log.info("Searching products with name containing: {}", name);
        
        if (name == null || name.trim().isEmpty()) {
            throw new BadRequestException("Search term cannot be empty");
        }
        
        return productRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Update product
     */
    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        log.info("Updating product with ID: {}", id);
        
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        
        // Validation
        if (productDTO.getPrice().doubleValue() <= 0) {
            throw new BadRequestException("Price must be greater than 0");
        }
        
        if (productDTO.getStock() < 0) {
            throw new BadRequestException("Stock cannot be negative");
        }
        
        // Update fields
        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setStock(productDTO.getStock());
        existingProduct.setCategory(productDTO.getCategory());
        existingProduct.setSkinType(productDTO.getSkinType());
        existingProduct.setBrand(productDTO.getBrand());
        existingProduct.setImageUrl(productDTO.getImageUrl());
        existingProduct.setIngredients(productDTO.getIngredients());
        
        if (productDTO.getActive() != null) {
            existingProduct.setActive(productDTO.getActive());
        }
        
        Product updatedProduct = productRepository.save(existingProduct);
        
        log.info("Product updated successfully with ID: {}", updatedProduct.getId());
        return mapToDTO(updatedProduct);
    }
    
    /**
     * Delete product (soft delete by marking inactive)
     */
    @Transactional
    public void deleteProduct(Long id) {
        log.info("Deleting product with ID: {}", id);
        
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        
        product.setActive(false);
        productRepository.save(product);
        
        log.info("Product marked as inactive with ID: {}", id);
    }
    
    /**
     * Permanently delete product (hard delete)
     */
    @Transactional
    public void permanentlyDeleteProduct(Long id) {
        log.info("Permanently deleting product with ID: {}", id);
        
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product", "id", id);
        }
        
        productRepository.deleteById(id);
        log.info("Product permanently deleted with ID: {}", id);
    }
    
    /**
     * Update stock (used by Order Service via Feign Client)
     */
    @Transactional
    public void updateStock(Long productId, Integer quantity) {
        log.info("Updating stock for product ID: {}, quantity: {}", productId, quantity);
        
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
        
        if (!product.getActive()) {
            throw new BadRequestException("Cannot update stock for inactive product");
        }
        
        int newStock = product.getStock() - quantity;
        
        if (newStock < 0) {
            throw new InsufficientStockException(productId, product.getStock(), quantity);
        }
        
        product.setStock(newStock);
        productRepository.save(product);
        
        log.info("Stock updated successfully. New stock: {}", newStock);
    }
    
    /**
     * Check stock availability
     */
    public boolean checkStockAvailability(Long productId, Integer quantity) {
        log.info("Checking stock availability for product ID: {}, quantity: {}", productId, quantity);
        
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
        
        return product.getActive() && product.getStock() >= quantity;
    }
    
    // ================ MAPPING METHODS ================
    
    private ProductDTO mapToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setCategory(product.getCategory());
        dto.setSkinType(product.getSkinType());
        dto.setBrand(product.getBrand());
        dto.setImageUrl(product.getImageUrl());
        dto.setIngredients(product.getIngredients());
        dto.setActive(product.getActive());
        return dto;
    }
    
    private Product mapToEntity(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setCategory(dto.getCategory());
        product.setSkinType(dto.getSkinType());
        product.setBrand(dto.getBrand());
        product.setImageUrl(dto.getImageUrl());
        product.setIngredients(dto.getIngredients());
        product.setActive(dto.getActive() != null ? dto.getActive() : true);
        return product;
    }
}