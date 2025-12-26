package com.hcl.user_service.service;

import com.hcl.user_service.dto.*;
import com.hcl.user_service.entity.User;
import com.hcl.user_service.exception.*;
import com.hcl.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    /**
     * Register a new user
     */
    @Transactional
    public UserDTO registerUser(RegisterDTO registerDTO) {
        log.info("Registering new user with email: {}", registerDTO.getEmail());
        
        // Check if email already exists
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new DuplicateResourceException("Email already exists: " + registerDTO.getEmail());
        }
        
        // Create new user
        User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword())); // Hash password
        user.setFirstName(registerDTO.getFirstName());
        user.setLastName(registerDTO.getLastName());
        user.setPhone(registerDTO.getPhone());
        user.setAddress(registerDTO.getAddress());
        user.setCity(registerDTO.getCity());
        user.setState(registerDTO.getState());
        user.setZipCode(registerDTO.getZipCode());
        user.setCountry(registerDTO.getCountry());
        user.setSkinType(registerDTO.getSkinType());
        user.setSkinConcerns(registerDTO.getSkinConcerns());
        user.setActive(true);
        
        User savedUser = userRepository.save(user);
        log.info("User registered successfully with ID: {}", savedUser.getId());
        
        return mapToDTO(savedUser);
    }
    
    /**
     * Login user
     */
    @Transactional(readOnly = true)
    public LoginResponseDTO login(LoginDTO loginDTO) {
        log.info("Login attempt for email: {}", loginDTO.getEmail());
        
        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));
        
        // Check if user is active
        if (!user.getActive()) {
            throw new InvalidCredentialsException("Account is inactive. Please contact support.");
        }
        
        // Verify password
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        
        log.info("User logged in successfully: {}", user.getEmail());
        
        LoginResponseDTO response = new LoginResponseDTO();
        response.setMessage("Login successful");
        response.setUser(mapToDTO(user));
        
        return response;
    }
    
    /**
     * Get user by ID
     */
    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        log.info("Fetching user with ID: {}", id);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        
        return mapToDTO(user);
    }
    
    /**
     * Get all users
     */
    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Get all active users
     */
    @Transactional(readOnly = true)
    public List<UserDTO> getActiveUsers() {
        log.info("Fetching active users");
        return userRepository.findByActiveTrue().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Update user
     */
    @Transactional
    public UserDTO updateUser(Long id, UpdateUserDTO updateDTO) {
        log.info("Updating user with ID: {}", id);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        
        // Update fields
        user.setFirstName(updateDTO.getFirstName());
        user.setLastName(updateDTO.getLastName());
        user.setPhone(updateDTO.getPhone());
        user.setAddress(updateDTO.getAddress());
        user.setCity(updateDTO.getCity());
        user.setState(updateDTO.getState());
        user.setZipCode(updateDTO.getZipCode());
        user.setCountry(updateDTO.getCountry());
        user.setSkinType(updateDTO.getSkinType());
        user.setSkinConcerns(updateDTO.getSkinConcerns());
        
        User updatedUser = userRepository.save(user);
        log.info("User updated successfully with ID: {}", updatedUser.getId());
        
        return mapToDTO(updatedUser);
    }
    
    /**
     * Delete user (soft delete - marks as inactive)
     */
    @Transactional
    public void deleteUser(Long id) {
        log.info("Deleting user with ID: {}", id);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        
        user.setActive(false);
        userRepository.save(user);
        
        log.info("User marked as inactive with ID: {}", id);
    }
    
    /**
     * Permanently delete user (hard delete)
     */
    @Transactional
    public void permanentlyDeleteUser(Long id) {
        log.info("Permanently deleting user with ID: {}", id);
        
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User", "id", id);
        }
        
        userRepository.deleteById(id);
        log.info("User permanently deleted with ID: {}", id);
    }
    
    /**
     * Map User entity to UserDTO
     */
    private UserDTO mapToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        // NOTE: Never include password in DTO!
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPhone(user.getPhone());
        dto.setAddress(user.getAddress());
        dto.setCity(user.getCity());
        dto.setState(user.getState());
        dto.setZipCode(user.getZipCode());
        dto.setCountry(user.getCountry());
        dto.setSkinType(user.getSkinType());
        dto.setSkinConcerns(user.getSkinConcerns());
        dto.setActive(user.getActive());
        return dto;
    }
}
