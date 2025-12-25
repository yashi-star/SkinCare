-- ============================================
-- ORDER SERVICE - DATABASE SCHEMA
-- ============================================
-- Database: order_db
-- Description: Schema for managing orders and order items
-- ============================================

-- Create database
CREATE DATABASE IF NOT EXISTS order_db;
USE order_db;

-- Drop tables if exist (for clean setup)
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;

-- ============================================
-- ORDERS TABLE
-- ============================================
CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    total_amount DECIMAL(19,2) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    shipping_address VARCHAR(500),
    payment_method VARCHAR(50),
    payment_status VARCHAR(50) DEFAULT 'PENDING',
    created_at DATETIME(6),
    updated_at DATETIME(6),
    
    -- Indexes for faster queries
    INDEX idx_user_id (user_id),
    INDEX idx_status (status),
    INDEX idx_payment_status (payment_status),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- ORDER_ITEMS TABLE
-- ============================================
CREATE TABLE order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    product_name VARCHAR(255),
    quantity INT NOT NULL,
    price DECIMAL(19,2) NOT NULL,
    subtotal DECIMAL(19,2) NOT NULL,
    
    -- Foreign key constraint
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    
    -- Indexes
    INDEX idx_order_id (order_id),
    INDEX idx_product_id (product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- NOTES:
-- ============================================
-- 1. user_id references users table in user-service (no FK due to microservices)
-- 2. product_id references products table in product-service (no FK due to microservices)
-- 3. CASCADE on order_items ensures items are deleted when order is deleted
-- 4. Indexes improve query performance for filtering and joins
-- ============================================