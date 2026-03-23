-- ============================================================
-- DATABASE PARTITIONING DEMO - MySQL
-- ============================================================

CREATE DATABASE IF NOT EXISTS PartitionDemo
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE PartitionDemo;

-- ============================================================
-- 1. HORIZONTAL PARTITIONING (Phân vùng ngang)
--    Chia theo HÀNG dựa trên điều kiện (gender)
--    Nam -> table_user_01 | Nữ -> table_user_02
-- ============================================================

CREATE TABLE IF NOT EXISTS table_user_01 (
    id     INT PRIMARY KEY AUTO_INCREMENT,
    name   VARCHAR(100),
    email  VARCHAR(100),
    gender VARCHAR(10) DEFAULT 'male',
    age    INT,
    city   VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS table_user_02 (
    id     INT PRIMARY KEY AUTO_INCREMENT,
    name   VARCHAR(100),
    email  VARCHAR(100),
    gender VARCHAR(10) DEFAULT 'female',
    age    INT,
    city   VARCHAR(100)
);

-- View hợp nhất 2 bảng
CREATE OR REPLACE VIEW vw_all_users AS
    SELECT id, name, email, gender, age, city FROM table_user_01
    UNION ALL
    SELECT id, name, email, gender, age, city FROM table_user_02;

-- ============================================================
-- 2. VERTICAL PARTITIONING (Phân vùng dọc)
--    Chia theo CỘT: tách thông tin cơ bản và thông tin chi tiết
-- ============================================================

CREATE TABLE IF NOT EXISTS user_basic (
    id     INT PRIMARY KEY AUTO_INCREMENT,
    name   VARCHAR(100),
    email  VARCHAR(100),
    gender VARCHAR(10)
);

CREATE TABLE IF NOT EXISTS user_detail (
    id         INT PRIMARY KEY,
    address    VARCHAR(255),
    phone      VARCHAR(20),
    bio        TEXT,
    avatar_url VARCHAR(500),
    created_at DATETIME DEFAULT NOW(),
    FOREIGN KEY (id) REFERENCES user_basic(id)
);

-- ============================================================
-- 3. FUNCTIONAL PARTITIONING (Phân vùng theo chức năng)
--    Chia theo NGHIỆP VỤ: orders, products, logs riêng biệt
-- ============================================================

CREATE TABLE IF NOT EXISTS func_orders (
    id           INT PRIMARY KEY AUTO_INCREMENT,
    user_id      INT,
    product_name VARCHAR(200),
    amount       DECIMAL(18,2),
    order_date   DATETIME DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS func_products (
    id           INT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(200),
    price        DECIMAL(18,2),
    stock        INT
);

CREATE TABLE IF NOT EXISTS func_audit_logs (
    id           INT PRIMARY KEY AUTO_INCREMENT,
    action       VARCHAR(50),
    table_name   VARCHAR(100),
    performed_by VARCHAR(100),
    log_time     DATETIME DEFAULT NOW()
);

-- ============================================================
-- SEED DATA
-- ============================================================

-- Horizontal: Nam (table_user_01)
INSERT INTO table_user_01 (name, email, gender, age, city) VALUES
('Nguyen Van An',   'an@mail.com',   'male', 25, 'Ha Noi'),
('Tran Van Binh',   'binh@mail.com', 'male', 30, 'TP.HCM'),
('Le Van Cuong',    'cuong@mail.com','male', 22, 'Da Nang'),
('Pham Van Dung',   'dung@mail.com', 'male', 28, 'Hai Phong'),
('Hoang Van Em',    'em@mail.com',   'male', 35, 'Can Tho');

-- Horizontal: Nữ (table_user_02)
INSERT INTO table_user_02 (name, email, gender, age, city) VALUES
('Nguyen Thi Hoa',  'hoa@mail.com',  'female', 24, 'Ha Noi'),
('Tran Thi Lan',    'lan@mail.com',  'female', 29, 'TP.HCM'),
('Le Thi Mai',      'mai@mail.com',  'female', 21, 'Da Nang'),
('Pham Thi Nga',    'nga@mail.com',  'female', 27, 'Hai Phong'),
('Hoang Thi Oanh',  'oanh@mail.com', 'female', 33, 'Can Tho');

-- Vertical: basic
INSERT INTO user_basic (name, email, gender) VALUES
('Nguyen Van An',  'an@mail.com',  'male'),
('Tran Thi Lan',   'lan@mail.com', 'female');

-- Vertical: detail
INSERT INTO user_detail (id, address, phone, bio, avatar_url) VALUES
(1, '123 Pho Hue, Ha Noi', '0901234567', 'Lap trinh vien backend', 'https://cdn.example.com/an.jpg'),
(2, '456 Le Loi, TP.HCM',  '0912345678', 'Designer UI/UX',         'https://cdn.example.com/lan.jpg');

-- Functional
INSERT INTO func_products (product_name, price, stock) VALUES
('Laptop Dell', 25000000, 10),
('Chuot Logitech', 500000, 50);

INSERT INTO func_orders (user_id, product_name, amount) VALUES
(1, 'Laptop Dell', 25000000),
(2, 'Chuot Logitech', 500000);

INSERT INTO func_audit_logs (action, table_name, performed_by) VALUES
('INSERT', 'func_orders', 'user_service'),
('SELECT', 'func_products', 'product_service');
