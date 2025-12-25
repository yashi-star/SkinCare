-- ============================================
-- PRODUCT SERVICE - SAMPLE DATA
-- ============================================
-- Database: product_db
-- Created: December 2025
-- Description: 20 sample skincare products for testing and demo
-- ============================================

USE product_db;

-- Clear existing data (optional - remove if you want to keep existing products)
-- TRUNCATE TABLE products;

-- ============================================
-- CLEANSERS (4 products)
-- ============================================

INSERT INTO products (name, description, price, stock, category, skin_type, brand, image_url, ingredients, active, created_at, updated_at) VALUES
('Hydrating Facial Cleanser', 'Gentle, non-foaming cleanser that removes makeup and impurities while maintaining skin moisture barrier', 24.99, 150, 'Cleanser', 'Dry', 'CeraVe', 'https://images.unsplash.com/photo-1556228720-195a672e8a03?w=400', 'Water, Glycerin, Ceramides, Hyaluronic Acid, Niacinamide', true, NOW(), NOW()),

('Oil Control Foaming Cleanser', 'Deep cleansing foam that removes excess oil and unclogs pores without over-drying', 19.99, 200, 'Cleanser', 'Oily', 'Neutrogena', 'https://images.unsplash.com/photo-1571875257727-256c39da42af?w=400', 'Salicylic Acid, Tea Tree Oil, Zinc PCA, Glycolic Acid', true, NOW(), NOW()),

('Gentle Micellar Cleansing Water', 'No-rinse cleanser that removes makeup and cleanses skin in one step, suitable for sensitive skin', 15.99, 180, 'Cleanser', 'Sensitive', 'Bioderma', 'https://images.unsplash.com/photo-1608571423902-eed4a5ad8108?w=400', 'Micellar Technology, Cucumber Extract, Glycerin', true, NOW(), NOW()),

('Balancing Gel Cleanser', 'pH-balanced gel cleanser perfect for combination skin, removes impurities without stripping', 22.50, 120, 'Cleanser', 'Combination', 'La Roche-Posay', 'https://images.unsplash.com/photo-1556228852-80b6e5fedc91?w=400', 'Niacinamide, Zinc, Thermal Spring Water', true, NOW(), NOW());

-- ============================================
-- MOISTURIZERS (4 products)
-- ============================================

INSERT INTO products (name, description, price, stock, category, skin_type, brand, image_url, ingredients, active, created_at, updated_at) VALUES
('Daily Hydrating Moisturizer', 'Lightweight daily moisturizer with SPF 30 for all-day hydration and sun protection', 32.99, 175, 'Moisturizer', 'All', 'Cetaphil', 'https://images.unsplash.com/photo-1620916566398-39f1143ab7be?w=400', 'Hyaluronic Acid, Vitamin E, SPF 30, Glycerin', true, NOW(), NOW()),

('Rich Night Cream', 'Intensive overnight treatment that deeply nourishes and repairs dry skin while you sleep', 45.00, 90, 'Moisturizer', 'Dry', 'Kiehl\'s', 'https://images.unsplash.com/photo-1611930022073-b7a4ba5fcccd?w=400', 'Squalane, Avocado Oil, Shea Butter, Vitamin B5', true, NOW(), NOW()),

('Oil-Free Gel Moisturizer', 'Ultra-light gel formula that hydrates without clogging pores, perfect for oily skin', 28.99, 160, 'Moisturizer', 'Oily', 'Clinique', 'https://images.unsplash.com/photo-1598440947619-2c35fc9aa908?w=400', 'Hyaluronic Acid, Aloe Vera, Cucumber Extract', true, NOW(), NOW()),

('Calming Relief Moisturizer', 'Fragrance-free moisturizer designed to soothe and protect sensitive, reactive skin', 38.50, 110, 'Moisturizer', 'Sensitive', 'Aveeno', 'https://images.unsplash.com/photo-1571875257727-256c39da42af?w=400', 'Colloidal Oatmeal, Feverfew Extract, Glycerin', true, NOW(), NOW());

-- ============================================
-- SERUMS (4 products)
-- ============================================

INSERT INTO products (name, description, price, stock, category, skin_type, brand, image_url, ingredients, active, created_at, updated_at) VALUES
('Vitamin C Brightening Serum', 'Potent antioxidant serum that brightens skin tone and reduces dark spots', 55.00, 85, 'Serum', 'All', 'SkinCeuticals', 'https://images.unsplash.com/photo-1620916566398-39f1143ab7be?w=400', '15% L-Ascorbic Acid, Vitamin E, Ferulic Acid', true, NOW(), NOW()),

('Hyaluronic Acid Hydrating Serum', 'Multi-molecular weight HA serum that penetrates deep to plump and hydrate skin', 29.99, 140, 'Serum', 'Dry', 'The Ordinary', 'https://images.unsplash.com/photo-1611930022073-b7a4ba5fcccd?w=400', 'Hyaluronic Acid Complex, Vitamin B5, Glycerin', true, NOW(), NOW()),

('Niacinamide 10% + Zinc Serum', 'Oil-controlling serum that minimizes pores and regulates sebum production', 18.99, 200, 'Serum', 'Oily', 'The Ordinary', 'https://images.unsplash.com/photo-1598440947619-2c35fc9aa908?w=400', '10% Niacinamide, 1% Zinc PCA', true, NOW(), NOW()),

('Retinol Anti-Aging Serum', 'Advanced retinol formula that reduces fine lines and improves skin texture', 62.00, 75, 'Serum', 'All', 'Paula\'s Choice', 'https://images.unsplash.com/photo-1556228720-195a672e8a03?w=400', '1% Retinol, Peptides, Vitamin E, Hyaluronic Acid', true, NOW(), NOW());

-- ============================================
-- SUNSCREENS (3 products)
-- ============================================

INSERT INTO products (name, description, price, stock, category, skin_type, brand, image_url, ingredients, active, created_at, updated_at) VALUES
('Mineral Sunscreen SPF 50', 'Broad-spectrum mineral sunscreen with zinc oxide, gentle for sensitive skin', 34.99, 130, 'Sunscreen', 'Sensitive', 'EltaMD', 'https://images.unsplash.com/photo-1571875257727-256c39da42af?w=400', 'Zinc Oxide 20%, Niacinamide, Hyaluronic Acid', true, NOW(), NOW()),

('Ultra-Light Sunscreen SPF 50+', 'Invisible, fast-absorbing sunscreen perfect for daily use under makeup', 26.99, 170, 'Sunscreen', 'All', 'Supergoop!', 'https://images.unsplash.com/photo-1608571423902-eed4a5ad8108?w=400', 'Avobenzone, Octisalate, Blue Light Protection', true, NOW(), NOW()),

('Tinted Mineral Sunscreen SPF 45', 'Lightweight tinted sunscreen that evens skin tone while protecting from UV rays', 42.00, 95, 'Sunscreen', 'All', 'Australian Gold', 'https://images.unsplash.com/photo-1556228852-80b6e5fedc91?w=400', 'Titanium Dioxide, Iron Oxides, Vitamin C', true, NOW(), NOW());

-- ============================================
-- MASKS & TREATMENTS (3 products)
-- ============================================

INSERT INTO products (name, description, price, stock, category, skin_type, brand, image_url, ingredients, active, created_at, updated_at) VALUES
('Clay Purifying Mask', 'Deep cleansing clay mask that draws out impurities and excess oil from pores', 21.99, 105, 'Mask', 'Oily', 'Aztec Secret', 'https://images.unsplash.com/photo-1620916566398-39f1143ab7be?w=400', 'Bentonite Clay, Kaolin Clay, Tea Tree Oil', true, NOW(), NOW()),

('Overnight Sleeping Mask', 'Intensive hydrating mask that works overnight to restore moisture and radiance', 38.00, 88, 'Mask', 'Dry', 'Laneige', 'https://images.unsplash.com/photo-1611930022073-b7a4ba5fcccd?w=400', 'Hyaluronic Acid, Squalane, Probiotics, Beta-Glucan', true, NOW(), NOW()),

('Exfoliating AHA/BHA Toner', 'Chemical exfoliant that unclogs pores and improves skin texture', 27.99, 145, 'Toner', 'Combination', 'COSRX', 'https://images.unsplash.com/photo-1598440947619-2c35fc9aa908?w=400', 'Glycolic Acid, Salicylic Acid, Niacinamide', true, NOW(), NOW());

-- ============================================
-- EYE CARE (2 products)
-- ============================================

INSERT INTO products (name, description, price, stock, category, skin_type, brand, image_url, ingredients, active, created_at, updated_at) VALUES
('Caffeine Eye Serum', 'Reduces puffiness and dark circles with caffeine and peptides', 24.99, 115, 'Eye Care', 'All', 'The Inkey List', 'https://images.unsplash.com/photo-1556228720-195a672e8a03?w=400', 'Caffeine 5%, Peptides, Hyaluronic Acid', true, NOW(), NOW()),

('Retinol Eye Cream', 'Anti-aging eye cream that targets fine lines and wrinkles around delicate eye area', 48.00, 82, 'Eye Care', 'All', 'RoC', 'https://images.unsplash.com/photo-1571875257727-256c39da42af?w=400', 'Retinol, Hyaluronic Acid, Vitamin E, Caffeine', true, NOW(), NOW());

-- ============================================
-- VERIFY DATA
-- ============================================

-- Check total count
SELECT COUNT(*) AS total_products FROM products;

-- View by category
SELECT category, COUNT(*) AS count FROM products GROUP BY category;

-- View by skin type
SELECT skin_type, COUNT(*) AS count FROM products GROUP BY skin_type;

-- View all products
-- SELECT id, name, category, skin_type, price, stock FROM products;

-- ============================================
-- NOTES:
-- ============================================
-- Total: 20 products
-- Categories: Cleanser (4), Moisturizer (4), Serum (4), Sunscreen (3), Mask (2), Toner (1), Eye Care (2)
-- Skin Types: Dry, Oily, Sensitive, Combination, All
-- Price Range: $15.99 - $62.00
-- Stock Range: 75 - 200 units
-- ============================================