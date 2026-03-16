-- Tạo database
CREATE DATABASE school_db;

-- Kết nối vào database vừa tạo
\c school_db;

-- Tạo bảng students
CREATE TABLE students (
    id        SERIAL PRIMARY KEY,
    name      VARCHAR(100) NOT NULL,
    email     VARCHAR(100) UNIQUE NOT NULL,
    age       INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Thêm dữ liệu mẫu
INSERT INTO students (name, email, age) VALUES
    ('Nguyen Van A', 'vana@example.com', 20),
    ('Tran Thi B',   'thib@example.com', 22),
    ('Le Van C',     'vanc@example.com', 21);

-- Tạo bảng courses
CREATE TABLE courses (
    id          SERIAL PRIMARY KEY,
    course_name VARCHAR(100) NOT NULL,
    credits     INT
);

INSERT INTO courses (course_name, credits) VALUES
    ('Ky thuat phan mem', 3),
    ('Co so du lieu',     3),
    ('Lap trinh mang',    2);

-- Thông báo hoàn thành
SELECT 'Database initialized successfully!' AS status;