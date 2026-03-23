-- Tạo bảng và insert data sẵn vào image
CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    price NUMERIC(10,2),
    created_at TIMESTAMP DEFAULT NOW()
);

INSERT INTO products (name, price) VALUES
    ('Laptop Dell XPS', 25000000),
    ('Chuột Logitech', 500000),
    ('Bàn phím cơ', 1200000),
    ('Màn hình 27 inch', 7500000),
    ('Tai nghe Sony', 3000000);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100),
    created_at TIMESTAMP DEFAULT NOW()
);

INSERT INTO users (username, email) VALUES
    ('admin', 'admin@example.com'),
    ('john_doe', 'john@example.com'),
    ('jane_doe', 'jane@example.com');
