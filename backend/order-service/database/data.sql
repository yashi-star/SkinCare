-- ============================================
-- ORDER SERVICE - SAMPLE DATA
-- ============================================
-- Database: order_db
-- Description: Sample orders for testing
-- Prerequisites: 
--   - User Service running with users (IDs 1-5)
--   - Product Service running with products (IDs 1-20)
-- ============================================

USE order_db;

-- Clear existing data (optional)
-- TRUNCATE TABLE order_items;
-- TRUNCATE TABLE orders;

-- ============================================
-- SAMPLE ORDERS
-- ============================================

-- Order 1: John Doe's skincare routine order
INSERT INTO orders (user_id, total_amount, status, shipping_address, payment_method, payment_status, created_at, updated_at) VALUES
(1, 80.97, 'DELIVERED', '123 Main Street, New York, NY 10001, USA', 'Credit Card', 'PAID', '2024-12-01 10:30:00', '2024-12-05 14:20:00');

INSERT INTO order_items (order_id, product_id, product_name, quantity, price, subtotal) VALUES
(1, 1, 'Hydrating Facial Cleanser', 2, 24.99, 49.98),
(1, 5, 'Daily Hydrating Moisturizer', 1, 32.99, 32.99);

-- Order 2: Jane Smith's anti-aging order
INSERT INTO orders (user_id, total_amount, status, shipping_address, payment_method, payment_status, created_at, updated_at) VALUES
(2, 162.00, 'SHIPPED', '456 Oak Avenue, Los Angeles, CA 90001, USA', 'PayPal', 'PAID', '2024-12-10 14:15:00', '2024-12-12 09:30:00');

INSERT INTO order_items (order_id, product_id, product_name, quantity, price, subtotal) VALUES
(2, 12, 'Retinol Anti-Aging Serum', 1, 62.00, 62.00),
(2, 6, 'Rich Night Cream', 1, 45.00, 45.00),
(2, 9, 'Vitamin C Brightening Serum', 1, 55.00, 55.00);

-- Order 3: Mike Johnson's basic routine
INSERT INTO orders (user_id, total_amount, status, shipping_address, payment_method, payment_status, created_at, updated_at) VALUES
(3, 48.98, 'CONFIRMED', '789 Pine Road, Chicago, IL 60601, USA', 'Debit Card', 'PAID', '2024-12-15 11:45:00', '2024-12-15 12:00:00');

INSERT INTO order_items (order_id, product_id, product_name, quantity, price, subtotal) VALUES
(3, 4, 'Balancing Gel Cleanser', 1, 22.50, 22.50),
(3, 14, 'Ultra-Light Sunscreen SPF 50+', 1, 26.99, 26.99);

-- Order 4: Sarah Williams's sensitive skin order
INSERT INTO orders (user_id, total_amount, status, shipping_address, payment_method, payment_status, created_at, updated_at) VALUES
(4, 88.48, 'PROCESSING', '321 Elm Street, Houston, TX 77001, USA', 'Credit Card', 'PAID', '2024-12-18 16:20:00', '2024-12-19 08:15:00');

INSERT INTO order_items (order_id, product_id, product_name, quantity, price, subtotal) VALUES
(4, 3, 'Gentle Micellar Cleansing Water', 2, 15.99, 31.98),
(4, 8, 'Calming Relief Moisturizer', 1, 38.50, 38.50),
(4, 13, 'Mineral Sunscreen SPF 50', 1, 34.99, 34.99);

-- Order 5: David Brown's pending order
INSERT INTO orders (user_id, total_amount, status, shipping_address, payment_method, payment_status, created_at, updated_at) VALUES
(5, 94.98, 'PENDING', '654 Maple Drive, Phoenix, AZ 85001, USA', 'Cash on Delivery', 'PENDING', '2024-12-20 09:10:00', '2024-12-20 09:10:00');

INSERT INTO order_items (order_id, product_id, product_name, quantity, price, subtotal) VALUES
(5, 10, 'Hyaluronic Acid Hydrating Serum', 2, 29.99, 59.98),
(5, 13, 'Mineral Sunscreen SPF 50', 1, 34.99, 34.99);

-- Order 6: Large order with multiple items
INSERT INTO orders (user_id, total_amount, status, shipping_address, payment_method, payment_status, created_at, updated_at) VALUES
(1, 215.94, 'CONFIRMED', '123 Main Street, New York, NY 10001, USA', 'Credit Card', 'PAID', '2024-12-22 13:30:00', '2024-12-22 13:35:00');

INSERT INTO order_items (order_id, product_id, product_name, quantity, price, subtotal) VALUES
(6, 2, 'Oil Control Foaming Cleanser', 1, 19.99, 19.99),
(6, 7, 'Oil-Free Gel Moisturizer', 1, 28.99, 28.99),
(6, 11, 'Niacinamide 10% + Zinc Serum', 3, 18.99, 56.97),
(6, 16, 'Clay Purifying Mask', 2, 21.99, 43.98),
(6, 18, 'Exfoliating AHA/BHA Toner', 2, 27.99, 55.98);

-- Order 7: Cancelled order
INSERT INTO orders (user_id, total_amount, status, shipping_address, payment_method, payment_status, created_at, updated_at) VALUES
(3, 45.00, 'CANCELLED', '789 Pine Road, Chicago, IL 60601, USA', 'Credit Card', 'PENDING', '2024-12-19 10:00:00', '2024-12-19 11:30:00');

INSERT INTO order_items (order_id, product_id, product_name, quantity, price, subtotal) VALUES
(7, 6, 'Rich Night Cream', 1, 45.00, 45.00);

-- ============================================
-- VERIFICATION QUERIES
-- ============================================

-- Count total orders
SELECT COUNT(*) AS total_orders FROM orders;

-- Count orders by status
SELECT status, COUNT(*) AS count FROM orders GROUP BY status;

-- Total revenue (paid orders)
SELECT SUM(total_amount) AS total_revenue FROM orders WHERE payment_status = 'PAID';

-- Orders with items
SELECT 
    o.id AS order_id,
    o.user_id,
    o.status,
    o.total_amount,
    COUNT(oi.id) AS item_count
FROM orders o
LEFT JOIN order_items oi ON o.id = oi.order_id
GROUP BY o.id;

-- Most ordered products
SELECT 
    product_id,
    product_name,
    SUM(quantity) AS total_quantity
FROM order_items
GROUP BY product_id, product_name
ORDER BY total_quantity DESC
LIMIT 5;

-- ============================================
-- SUMMARY
-- ============================================
-- Total Orders: 7
-- Statuses:
--   - PENDING: 1
--   - CONFIRMED: 2
--   - PROCESSING: 1
--   - SHIPPED: 1
--   - DELIVERED: 1
--   - CANCELLED: 1
-- Total Revenue: $472.43 (from paid orders)
-- ============================================