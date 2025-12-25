INSERT INTO carts (user_id, total_amount) VALUES (1, 149.97);

INSERT INTO cart_items (cart_id, product_id, product_name, price, quantity, subtotal, image_url) VALUES
(1, 1, 'Hyaluronic Acid Serum', 29.99, 2, 59.98, 'https://images.unsplash.com/photo-1620916566398-39f1143ab7be?w=400'),
(1, 5, 'Vitamin C Brightening Serum', 44.99, 2, 89.98, 'https://images.unsplash.com/photo-1620916566398-39f1143ab7be?w=400');

-- Cart 2: Michael Chen (User ID: 2) - 2 items
INSERT INTO carts (user_id, total_amount) VALUES (2, 74.98);

INSERT INTO cart_items (cart_id, product_id, product_name, price, quantity, subtotal, image_url) VALUES
(2, 2, 'Niacinamide 10% + Zinc 1%', 24.99, 1, 24.99, 'https://images.unsplash.com/photo-1620916566398-39f1143ab7be?w=400'),
(2, 3, 'Retinol Night Cream', 49.99, 1, 49.99, 'https://images.unsplash.com/photo-1556228578-8c89e6adf883?w=400');

-- Cart 3: Emily Davis (User ID: 3) - 4 items
INSERT INTO carts (user_id, total_amount) VALUES (3, 164.95);

INSERT INTO cart_items (cart_id, product_id, product_name, price, quantity, subtotal, image_url) VALUES
(3, 4, 'Gentle Foaming Cleanser', 19.99, 1, 19.99, 'https://images.unsplash.com/photo-1556228578-8c89e6adf883?w=400'),
(3, 6, 'Ceramide Moisturizer', 34.99, 1, 34.99, 'https://images.unsplash.com/photo-1556228720-195a672e8a03?w=400'),
(3, 8, 'Rose Water Toner', 18.99, 2, 37.98, 'https://images.unsplash.com/photo-1556228578-8c89e6adf883?w=400'),
(3, 10, 'Aloe Vera Gel', 14.99, 5, 74.95, 'https://images.unsplash.com/photo-1556228720-195a672e8a03?w=400');

-- Cart 4: James Wilson (User ID: 4) - 1 item
INSERT INTO carts (user_id, total_amount) VALUES (4, 59.99);

INSERT INTO cart_items (cart_id, product_id, product_name, price, quantity, subtotal, image_url) VALUES
(4, 7, 'Sunscreen SPF 50+', 59.99, 1, 59.99, 'https://images.unsplash.com/photo-1556228578-8c89e6adf883?w=400');

-- Cart 5: Olivia Martinez (User ID: 5) - 3 items
INSERT INTO carts (user_id, total_amount) VALUES (5, 109.97);

INSERT INTO cart_items (cart_id, product_id, product_name, price, quantity, subtotal, image_url) VALUES
(5, 1, 'Hyaluronic Acid Serum', 29.99, 1, 29.99, 'https://images.unsplash.com/photo-1620916566398-39f1143ab7be?w=400'),
(5, 3, 'Retinol Night Cream', 49.99, 1, 49.99, 'https://images.unsplash.com/photo-1556228578-8c89e6adf883?w=400'),
(5, 2, 'Niacinamide 10% + Zinc 1%', 24.99, 1, 24.99, 'https://images.unsplash.com/photo-1620916566398-39f1143ab7be?w=400');

-- Cart 6: David Anderson (User ID: 6) - Empty cart (just created, no items)
INSERT INTO carts (user_id, total_amount) VALUES (6, 0.00);

-- Cart 7: Sophia Taylor (User ID: 7) - 5 items (large cart)
INSERT INTO carts (user_id, total_amount) VALUES (7, 234.91);

INSERT INTO cart_items (cart_id, product_id, product_name, price, quantity, subtotal, image_url) VALUES
(7, 5, 'Vitamin C Brightening Serum', 44.99, 1, 44.99, 'https://images.unsplash.com/photo-1620916566398-39f1143ab7be?w=400'),
(7, 6, 'Ceramide Moisturizer', 34.99, 2, 69.98, 'https://images.unsplash.com/photo-1556228720-195a672e8a03?w=400'),
(7, 7, 'Sunscreen SPF 50+', 59.99, 1, 59.99, 'https://images.unsplash.com/photo-1556228578-8c89e6adf883?w=400'),
(7, 8, 'Rose Water Toner', 18.99, 1, 18.99, 'https://images.unsplash.com/photo-1556228578-8c89e6adf883?w=400'),
(7, 9, 'Clay Face Mask', 27.99, 1, 27.99, 'https://images.unsplash.com/photo-1556228720-195a672e8a03?w=400'),
(7, 10, 'Aloe Vera Gel', 14.99, 1, 12.97, 'https://images.unsplash.com/photo-1556228720-195a672e8a03?w=400');

-- Cart 8: William Thomas (User ID: 8) - 2 items
INSERT INTO carts (user_id, total_amount) VALUES (8, 64.98);

INSERT INTO cart_items (cart_id, product_id, product_name, price, quantity, subtotal, image_url) VALUES
(8, 2, 'Niacinamide 10% + Zinc 1%', 24.99, 2, 49.98, 'https://images.unsplash.com/photo-1620916566398-39f1143ab7be?w=400'),
(8, 10, 'Aloe Vera Gel', 14.99, 1, 14.99, 'https://images.unsplash.com/photo-1556228720-195a672e8a03?w=400');

-- Cart 9: Ava Jackson (User ID: 9) - 1 item (recent add)
INSERT INTO carts (user_id, total_amount) VALUES (9, 34.99);

INSERT INTO cart_items (cart_id, product_id, product_name, price, quantity, subtotal, image_url) VALUES
(9, 6, 'Ceramide Moisturizer', 34.99, 1, 34.99, 'https://images.unsplash.com/photo-1556228720-195a672e8a03?w=400');

-- Cart 10: Alexander White (User ID: 10) - 3 items
INSERT INTO carts (user_id, total_amount) VALUES (10, 119.97);

INSERT INTO cart_items (cart_id, product_id, product_name, price, quantity, subtotal, image_url) VALUES
(10, 1, 'Hyaluronic Acid Serum', 29.99, 1, 29.99, 'https://images.unsplash.com/photo-1620916566398-39f1143ab7be?w=400'),
(10, 5, 'Vitamin C Brightening Serum', 44.99, 1, 44.99, 'https://images.unsplash.com/photo-1620916566398-39f1143ab7be?w=400'),
(10, 7, 'Sunscreen SPF 50+', 59.99, 1, 44.99, 'https://images.unsplash.com/photo-1556228578-8c89e6adf883?w=400');




-- Count total carts and items
SELECT 
    COUNT(DISTINCT c.id) as total_carts,
    COUNT(ci.id) as total_items,
    SUM(c.total_amount) as total_value
FROM carts c
LEFT JOIN cart_items ci ON c.id = ci.cart_id;

-- Carts with item count
SELECT 
    c.id,
    c.user_id,
    COUNT(ci.id) as item_count,
    c.total_amount,
    c.created_at
FROM carts c
LEFT JOIN cart_items ci ON c.id = ci.cart_id
GROUP BY c.id, c.user_id, c.total_amount, c.created_at
ORDER BY c.created_at DESC;

-- Most popular products in carts
SELECT 
    product_id,
    product_name,
    COUNT(*) as times_added,
    SUM(quantity) as total_quantity,
    SUM(subtotal) as total_value
FROM cart_items
GROUP BY product_id, product_name
ORDER BY times_added DESC;

-- Average cart value and items
SELECT 
    AVG(total_amount) as avg_cart_value,
    AVG(item_count) as avg_items_per_cart
FROM (
    SELECT c.id, c.total_amount, COUNT(ci.id) as item_count
    FROM carts c
    LEFT JOIN cart_items ci ON c.id = ci.cart_id
    GROUP BY c.id, c.total_amount
) as cart_stats;

-- View all carts with details
SELECT 
    c.id as cart_id,
    c.user_id,
    ci.product_name,
    ci.quantity,
    ci.price,
    ci.subtotal,
    ci.added_at,
    c.total_amount as cart_total
FROM carts c
LEFT JOIN cart_items ci ON c.id = ci.cart_id
ORDER BY c.id, ci.added_at DESC;

-- Empty carts (abandoned carts)
SELECT 
    c.id,
    c.user_id,
    c.created_at,
    TIMESTAMPDIFF(DAY, c.updated_at, NOW()) as days_inactive
FROM carts c
LEFT JOIN cart_items ci ON c.id = ci.cart_id
WHERE ci.id IS NULL
ORDER BY c.created_at DESC;