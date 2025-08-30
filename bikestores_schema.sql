CREATE database bikestores;

use bikestores;

CREATE TABLE brands (
    brand_id INT AUTO_INCREMENT PRIMARY KEY,
    brand_name VARCHAR(255) NOT NULL
);

-- CATEGORIES TABLE
CREATE TABLE categories (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL
);

-- STORES TABLE
CREATE TABLE stores (
    store_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(20)
);

-- STAFFS TABLE
CREATE TABLE staffs (
    staff_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    role VARCHAR(50),
    email VARCHAR(100),
    phone VARCHAR(20),
    store_id INT,
    FOREIGN KEY (store_id) REFERENCES stores(store_id)
);

-- DISCOUNTS TABLE
CREATE TABLE discounts (
    discount_id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    percentage INT,
    expires_at DATE
);

-- CUSTOMERS TABLE
CREATE TABLE customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    gender ENUM('Male', 'Female', 'Other'),
    phone VARCHAR(20),
    email VARCHAR(100)
);

-- PRODUCTS TABLE
CREATE TABLE products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category_id INT,
    brand_id INT,
    price DECIMAL(10,2),
    description TEXT,
    FOREIGN KEY (category_id) REFERENCES categories(category_id),
    FOREIGN KEY (brand_id) REFERENCES brands(brand_id)
);

-- ORDERS TABLE
CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    order_date DATE,
    discount_id INT,
    status VARCHAR(50),
    total_amount DECIMAL(10,2),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    FOREIGN KEY (discount_id) REFERENCES discounts(discount_id)
);

-- ORDER_ITEMS TABLE
CREATE TABLE order_items (
    order_item_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    product_id INT,
    quantity INT,
    price DECIMAL(10,2),
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- STOCKS TABLE
CREATE TABLE stocks (
    stock_id INT AUTO_INCREMENT PRIMARY KEY,
    store_id INT,
    product_id INT,
    quantity INT,
    FOREIGN KEY (store_id) REFERENCES stores(store_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- USERS TABLE
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20)
);

-- CARTS TABLE
CREATE TABLE carts (
    cart_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- CART_ITEMS TABLE
CREATE TABLE cart_items (
    cart_item_id INT AUTO_INCREMENT PRIMARY KEY,
    cart_id INT,
    product_id INT,
    quantity INT,
    FOREIGN KEY (cart_id) REFERENCES carts(cart_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- PAYMENTS TABLE
CREATE TABLE payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    amount DECIMAL(10,2),
    method VARCHAR(50),
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(order_id)
);

-- SHIPMENTS TABLE
CREATE TABLE shipments (
    shipment_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    carrier VARCHAR(100),
    tracking_number VARCHAR(100),
    shipped_date DATE,
    FOREIGN KEY (order_id) REFERENCES orders(order_id)
);

-- REVIEWS TABLE
CREATE TABLE reviews (
    review_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    product_id INT,
    rating INT,
    comment TEXT,
    review_date DATE,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- WISHLISTS TABLE
CREATE TABLE wishlists (
    wishlist_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    product_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

select * from carts;

-- 1. Thêm brand giày sneaker
INSERT INTO brands(brand_name) VALUES
('Nike'),
('Adidas'),
('Puma');

-- 2. Thêm category cho sneaker
INSERT INTO categories(category_name) VALUES
('Sneakers'),
('Running Shoes');

-- 3. Thêm products giày sneaker
INSERT INTO products(name, category_id, brand_id, price, description) VALUES
('Nike Air Max 90', 4, 4, 150.00, 'Classic Nike Air Max sneakers'),
('Adidas Ultraboost', 4, 5, 180.00, 'Comfortable Adidas running shoes'),
('Puma RS-X', 4, 6, 130.00, 'Stylish Puma RS-X sneakers'),
('Nike Air Force 1', 4, 4, 140.00, 'Iconic Nike Air Force 1 shoes'),
('Adidas NMD', 4, 5, 160.00, 'Modern Adidas NMD sneakers');

-- 4. Tạo cart cho Bob (user_id = 2)
INSERT INTO carts(user_id) VALUES (2);

-- 5. Thêm cart_items cho Bob
INSERT INTO cart_items(cart_id, product_id, quantity) VALUES
(2, 4, 1),  -- Nike Air Max 90
(2, 5, 2);  -- Adidas Ultraboost

-- 6. Tạo cart cho Charlie (user_id = 3)
INSERT INTO carts(user_id) VALUES (3);

-- 7. Thêm cart_items cho Charlie
INSERT INTO cart_items(cart_id, product_id, quantity) VALUES
(3, 6, 1),  -- Puma RS-X
(3, 7, 1);  -- Nike Air Force 1

