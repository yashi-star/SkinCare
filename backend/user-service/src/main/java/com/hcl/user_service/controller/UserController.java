package com.hcl.user_service.controller;

import com.hcl.user_service.dto.*;
import com.hcl.user_service.service.UserService;
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
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class UserController {
    
    private final UserService userService;
    
    /**
     * Register a new user
     * POST /users/register
     */
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody RegisterDTO registerDTO) {
        log.info("REST request to register user with email: {}", registerDTO.getEmail());
        UserDTO createdUser = userService.registerUser(registerDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
    
    /**
     * User login
     * POST /users/login
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        log.info("REST request to login user with email: {}", loginDTO.getEmail());
        LoginResponseDTO response = userService.login(loginDTO);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Get user by ID
     * GET /users/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        log.info("REST request to get user by ID: {}", id);
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
    
    /**
     * Get all users
     * GET /users
     * GET /users?active=true
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(
            @RequestParam(required = false) Boolean active) {
        
        log.info("REST request to get all users. Active filter: {}", active);
        
        List<UserDTO> users;
        if (active != null && active) {
            users = userService.getActiveUsers();
        } else {
            users = userService.getAllUsers();
        }
        
        return ResponseEntity.ok(users);
    }
    
    /**
     * Update user
     * PUT /users/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserDTO updateDTO) {
        
        log.info("REST request to update user with ID: {}", id);
        UserDTO updatedUser = userService.updateUser(id, updateDTO);
        return ResponseEntity.ok(updatedUser);
    }
    
    /**
     * Delete user (soft delete - marks as inactive)
     * DELETE /users/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id) {
        log.info("REST request to delete user with ID: {}", id);
        userService.deleteUser(id);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "User marked as inactive successfully");
        response.put("userId", id.toString());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Permanently delete user (hard delete - removes from database)
     * DELETE /users/{id}/permanent
     */
    @DeleteMapping("/{id}/permanent")
    public ResponseEntity<Map<String, String>> permanentlyDeleteUser(@PathVariable Long id) {
        log.info("REST request to permanently delete user with ID: {}", id);
        userService.permanentlyDeleteUser(id);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "User permanently deleted successfully");
        response.put("userId", id.toString());
        
        return ResponseEntity.ok(response);
    }
}
