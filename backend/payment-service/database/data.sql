
-- Successful Payments
INSERT INTO payments (transaction_id, order_id, user_id, amount, payment_method, status, payment_gateway_response, created_at) VALUES
('TXN1735201234001', 1, 1, 149.97, 'CREDIT_CARD', 'SUCCESS', 'Payment processed successfully', '2024-12-20 10:30:00'),
('TXN1735201234002', 2, 2, 74.98, 'UPI', 'SUCCESS', 'Payment processed successfully', '2024-12-21 14:15:00'),
('TXN1735201234003', 3, 3, 164.95, 'DEBIT_CARD', 'SUCCESS', 'Payment processed successfully', '2024-12-22 09:45:00'),
('TXN1735201234004', 4, 4, 59.99, 'NET_BANKING', 'SUCCESS', 'Payment processed successfully', '2024-12-22 16:20:00'),
('TXN1735201234005', 5, 5, 109.97, 'WALLET', 'SUCCESS', 'Payment processed successfully', '2024-12-23 11:30:00'),
('TXN1735201234006', 6, 1, 89.98, 'CREDIT_CARD', 'SUCCESS', 'Payment processed successfully', '2024-12-23 15:45:00'),
('TXN1735201234007', 7, 6, 234.91, 'UPI', 'SUCCESS', 'Payment processed successfully', '2024-12-24 08:15:00'),
('TXN1735201234008', 8, 7, 119.97, 'DEBIT_CARD', 'SUCCESS', 'Payment processed successfully', '2024-12-24 13:30:00'),
('TXN1735201234009', 9, 8, 44.99, 'COD', 'SUCCESS', 'COD order placed successfully', '2024-12-25 10:00:00'),
('TXN1735201234010', 10, 9, 199.99, 'CREDIT_CARD', 'SUCCESS', 'Payment processed successfully', '2024-12-25 12:00:00'),
('TXN1735201234011', 11, 10, 79.99, 'UPI', 'SUCCESS', 'Payment processed successfully', '2024-12-25 14:30:00'),
('TXN1735201234012', 12, 2, 149.98, 'DEBIT_CARD', 'SUCCESS', 'Payment processed successfully', '2024-12-26 09:30:00'),

-- Failed Payments
('TXN1735201234013', 13, 3, 89.99, 'CREDIT_CARD', 'FAILED', NULL, '2024-12-25 14:20:00'),
('TXN1735201234014', 14, 4, 129.99, 'UPI', 'FAILED', NULL, '2024-12-25 16:45:00'),

-- Processing Payment
('TXN1735201234015', 15, 5, 59.99, 'NET_BANKING', 'PROCESSING', 'Payment being processed', '2024-12-26 10:00:00'),

-- Refunded Payments
('TXN1735201234016', 16, 6, 89.99, 'CREDIT_CARD', 'REFUNDED', 'Refund processed successfully', '2024-12-24 12:00:00'),
('TXN1735201234017', 17, 7, 44.99, 'DEBIT_CARD', 'REFUNDED', 'Refund processed successfully', '2024-12-25 11:00:00'),

-- Cancelled Payment
('TXN1735201234018', 18, 8, 99.99, 'WALLET', 'CANCELLED', NULL, '2024-12-25 18:00:00'),

-- Pending Payments
('TXN1735201234019', 19, 9, 179.99, 'UPI', 'PENDING', 'Awaiting confirmation', '2024-12-26 11:00:00'),
('TXN1735201234020', 20, 10, 249.99, 'NET_BANKING', 'PENDING', 'Awaiting confirmation', '2024-12-26 11:30:00');

-- Update failure reasons for failed payments
UPDATE payments 
SET failure_reason = 'Insufficient funds' 
WHERE status = 'FAILED' AND transaction_id = 'TXN1735201234013';

UPDATE payments 
SET failure_reason = 'Transaction timeout' 
WHERE status = 'FAILED' AND transaction_id = 'TXN1735201234014';

-- Update refund reasons
UPDATE payments 
SET failure_reason = 'Customer requested refund - Product damaged' 
WHERE status = 'REFUNDED' AND transaction_id = 'TXN1735201234016';

UPDATE payments 
SET failure_reason = 'Customer requested refund - Wrong item' 
WHERE status = 'REFUNDED' AND transaction_id = 'TXN1735201234017';

-- ============================================
-- Verification Queries
-- ============================================

-- Count total payments by status
SELECT 
    status,
    COUNT(*) as count,
    SUM(amount) as total_amount
FROM payments
GROUP BY status
ORDER BY count DESC;

-- Count payments by payment method
SELECT 
    payment_method,
    COUNT(*) as count,
    SUM(amount) as total_amount,
    ROUND(AVG(amount), 2) as avg_amount
FROM payments
GROUP BY payment_method
ORDER BY count DESC;

-- Successful payments in last 7 days
SELECT 
    DATE(created_at) as payment_date,
    COUNT(*) as transactions,
    SUM(amount) as daily_revenue
FROM payments
WHERE status = 'SUCCESS'
  AND created_at >= DATE_SUB(NOW(), INTERVAL 7 DAY)
GROUP BY DATE(created_at)
ORDER BY payment_date DESC;

-- Payment success rate
SELECT 
    ROUND(
        (SUM(CASE WHEN status = 'SUCCESS' THEN 1 ELSE 0 END) * 100.0 / COUNT(*)), 
        2
    ) as success_rate_percentage,
    SUM(CASE WHEN status = 'SUCCESS' THEN 1 ELSE 0 END) as successful_payments,
    SUM(CASE WHEN status = 'FAILED' THEN 1 ELSE 0 END) as failed_payments,
    COUNT(*) as total_payments
FROM payments;

-- User payment history with statistics
SELECT 
    user_id,
    COUNT(*) as total_payments,
    SUM(CASE WHEN status = 'SUCCESS' THEN 1 ELSE 0 END) as successful_payments,
    SUM(CASE WHEN status = 'FAILED' THEN 1 ELSE 0 END) as failed_payments,
    SUM(CASE WHEN status = 'SUCCESS' THEN amount ELSE 0 END) as total_spent,
    ROUND(AVG(CASE WHEN status = 'SUCCESS' THEN amount END), 2) as avg_order_value
FROM payments
GROUP BY user_id
ORDER BY total_spent DESC;

-- Recent payments (all details)
SELECT 
    id,
    transaction_id,
    order_id,
    user_id,
    amount,
    payment_method,
    status,
    failure_reason,
    created_at
FROM payments
ORDER BY created_at DESC
LIMIT 15;

-- Failed payments analysis
SELECT 
    payment_method,
    COUNT(*) as failed_count,
    failure_reason,
    SUM(amount) as failed_amount
FROM payments
WHERE status = 'FAILED'
GROUP BY payment_method, failure_reason;

-- Average payment amount by method (successful only)
SELECT 
    payment_method,
    COUNT(*) as transaction_count,
    MIN(amount) as min_amount,
    ROUND(AVG(amount), 2) as avg_amount,
    MAX(amount) as max_amount,
    SUM(amount) as total_amount
FROM payments
WHERE status = 'SUCCESS'
GROUP BY payment_method
ORDER BY avg_amount DESC;

-- Monthly revenue (successful payments)
SELECT 
    DATE_FORMAT(created_at, '%Y-%m') as month,
    COUNT(*) as transactions,
    SUM(amount) as revenue
FROM payments
WHERE status = 'SUCCESS'
GROUP BY DATE_FORMAT(created_at, '%Y-%m')
ORDER BY month DESC;

-- Payment method preference by user
SELECT 
    user_id,
    payment_method,
    COUNT(*) as usage_count
FROM payments
GROUP BY user_id, payment_method
ORDER BY user_id, usage_count DESC;

-- Hourly payment distribution (for today)
SELECT 
    HOUR(created_at) as hour,
    COUNT(*) as payment_count,
    SUM(amount) as hourly_revenue
FROM payments
WHERE DATE(created_at) = CURDATE()
GROUP BY HOUR(created_at)
ORDER BY hour;