package com.hcl.user_service.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.user_service.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Find user by email (for login)
    Optional<User> findByEmail(String email);
    
    // Check if email already exists (for registration)
    boolean existsByEmail(String email);
    
    // Find active users
    List<User> findByActiveTrue();
    
    // Find users by skin type (for analytics/recommendations)
    List<User> findBySkinType(String skinType);
    
    // Find users by city (for location-based features)
    List<User> findByCity(String city);
}