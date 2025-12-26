-- Create database
CREATE DATABASE IF NOT EXISTS payment_db;
USE payment_db;

-- Drop table if exists
DROP TABLE IF EXISTS payments;

-- Create payments table
CREATE TABLE payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    transaction_id VARCHAR(100) NOT NULL UNIQUE,
    order_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    payment_method VARCHAR(50) NOT NULL COMMENT 'CREDIT_CARD, DEBIT_CARD, UPI, NET_BANKING, WALLET, COD',
    status VARCHAR(50) NOT NULL COMMENT 'PENDING, PROCESSING, SUCCESS, FAILED, REFUNDED, CANCELLED',
    payment_gateway_response TEXT,
    failure_reason VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_transaction_id (transaction_id),
    INDEX idx_order_id (order_id),
    INDEX idx_user_id (user_id),
    INDEX idx_status (status),
    INDEX idx_payment_method (payment_method),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =