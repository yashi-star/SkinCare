

INSERT INTO users (email, password, first_name, last_name, phone, address, city, state, zip_code, country, skin_type, skin_concerns, active) VALUES
-- Active customers with complete profiles
('sarah.johnson@email.com', '$2a$10$xqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6', 'Sarah', 'Johnson', '+1-555-0101', '123 Beauty Lane', 'Los Angeles', 'California', '90001', 'USA', 'Combination', 'Acne, Oily T-zone', TRUE),

('michael.chen@email.com', '$2a$10$xqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6', 'Michael', 'Chen', '+1-555-0102', '456 Glow Street', 'New York', 'New York', '10001', 'USA', 'Oily', 'Acne, Large pores', TRUE),

('emily.davis@email.com', '$2a$10$xqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6', 'Emily', 'Davis', '+1-555-0103', '789 Radiance Ave', 'Chicago', 'Illinois', '60601', 'USA', 'Dry', 'Dryness, Fine lines', TRUE),

('james.wilson@email.com', '$2a$10$xqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6', 'James', 'Wilson', '+1-555-0104', '321 Smooth Blvd', 'Houston', 'Texas', '77001', 'USA', 'Sensitive', 'Redness, Irritation', TRUE),

('olivia.martinez@email.com', '$2a$10$xqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6', 'Olivia', 'Martinez', '+1-555-0105', '654 Clear Skin Road', 'Phoenix', 'Arizona', '85001', 'USA', 'Normal', 'Hyperpigmentation', TRUE),

('david.anderson@email.com', '$2a$10$xqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6', 'David', 'Anderson', '+1-555-0106', '987 Wellness Way', 'Philadelphia', 'Pennsylvania', '19101', 'USA', 'Combination', 'Blackheads, Uneven texture', TRUE),

('sophia.taylor@email.com', '$2a$10$xqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6', 'Sophia', 'Taylor', '+1-555-0107', '147 Hydration St', 'San Antonio', 'Texas', '78201', 'USA', 'Dry', 'Dryness, Flakiness', TRUE),

('william.thomas@email.com', '$2a$10$xqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6', 'William', 'Thomas', '+1-555-0108', '258 Youth Drive', 'San Diego', 'California', '92101', 'USA', 'Oily', 'Acne, Shine', TRUE),

('ava.jackson@email.com', '$2a$10$xqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6', 'Ava', 'Jackson', '+1-555-0109', '369 Serum Circle', 'Dallas', 'Texas', '75201', 'USA', 'Sensitive', 'Redness, Dryness', TRUE),

('alexander.white@email.com', '$2a$10$xqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6', 'Alexander', 'White', '+1-555-0110', '741 Balance Path', 'San Jose', 'California', '95101', 'USA', 'Combination', 'Oily T-zone, Dry cheeks', TRUE),

-- More customers
('isabella.harris@email.com', '$2a$10$xqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6', 'Isabella', 'Harris', '+1-555-0111', '852 Glow Avenue', 'Austin', 'Texas', '78701', 'USA', 'Normal', 'Dark circles', TRUE),

('ethan.martin@email.com', '$2a$10$xqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6', 'Ethan', 'Martin', '+1-555-0112', '963 Fresh Lane', 'Jacksonville', 'Florida', '32099', 'USA', 'Oily', 'Acne, Blackheads', TRUE),

('mia.thompson@email.com', '$2a$10$xqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6', 'Mia', 'Thompson', '+1-555-0113', '159 Nourish Street', 'Fort Worth', 'Texas', '76101', 'USA', 'Dry', 'Aging, Fine lines', TRUE),

('noah.garcia@email.com', '$2a$10$xqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6', 'Noah', 'Garcia', '+1-555-0114', '357 Vitality Road', 'Columbus', 'Ohio', '43004', 'USA', 'Sensitive', 'Irritation, Redness', TRUE),

('charlotte.rodriguez@email.com', '$2a$10$xqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6', 'Charlotte', 'Rodriguez', '+1-555-0115', '486 Clarity Blvd', 'Charlotte', 'North Carolina', '28201', 'USA', 'Combination', 'Hyperpigmentation, Uneven tone', TRUE),

-- Customers with minimal address info
('liam.lopez@email.com', '$2a$10$xqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6', 'Liam', 'Lopez', '+1-555-0116', NULL, 'Indianapolis', 'Indiana', NULL, 'USA', 'Normal', NULL, TRUE),

('amelia.lee@email.com', '$2a$10$xqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6', 'Amelia', 'Lee', NULL, '753 Beauty Plaza', 'San Francisco', 'California', '94101', 'USA', 'Dry', 'Dryness', TRUE),

('benjamin.walker@email.com', '$2a$10$xqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6', 'Benjamin', 'Walker', '+1-555-0118', '864 Spa Street', 'Seattle', 'Washington', '98101', 'USA', 'Oily', 'Shine, Large pores', TRUE),

-- Inactive user
('harper.hall@email.com', '$2a$10$xqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6', 'Harper', 'Hall', '+1-555-0119', '975 Wellness Way', 'Denver', 'Colorado', '80201', 'USA', 'Combination', 'Acne', FALSE),

-- Admin/Test user
('admin@skincare.com', '$2a$10$xqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6Y8Z8Z8Z8Z8OqX5h3Qq5Y6', 'Admin', 'User', '+1-555-9999', '1 Admin Street', 'Boston', 'Massachusetts', '02101', 'USA', 'Normal', NULL, TRUE);

-- ============================================
-- Verify Data
-- ============================================

-- Count total users
SELECT COUNT(*) as total_users FROM users;

-- Count by skin type
SELECT skin_type, COUNT(*) as count 
FROM users 
WHERE active = TRUE 
GROUP BY skin_type;

-- Count by state
SELECT state, COUNT(*) as count 
FROM users 
WHERE active = TRUE 
GROUP BY state 
ORDER BY count DESC;

-- View all users summary
SELECT 
    id,
    CONCAT(first_name, ' ', last_name) as full_name,
    email,
    city,
    state,
    skin_type,
    active,
    created_at
FROM users
ORDER BY created_at DESC;