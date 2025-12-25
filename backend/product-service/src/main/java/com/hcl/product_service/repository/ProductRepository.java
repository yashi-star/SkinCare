package com.hcl.product_service.repository;

import com.hcl.product_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Custom query methods (Spring generates SQL automatically!)
    List<Product> findByCategory(String category);
    List<Product> findBySkinType(String skinType);
    List<Product> findByBrand(String brand);
    List<Product> findByActiveTrue();
    List<Product> findByNameContainingIgnoreCase(String name);
}

