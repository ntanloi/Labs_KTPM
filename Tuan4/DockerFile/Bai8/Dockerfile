# Base image PostgreSQL phiên bản 15
FROM postgres:15

# Khai báo biến môi trường cho PostgreSQL
ENV POSTGRES_USER=admin
ENV POSTGRES_PASSWORD=admin123
ENV POSTGRES_DB=school_db

# Copy file SQL vào thư mục đặc biệt của PostgreSQL
# Mọi file .sql trong thư mục này sẽ tự động chạy
# khi container khởi động lần đầu tiên
COPY init.sql /docker-entrypoint-initdb.d/

# Expose port mặc định của PostgreSQL
EXPOSE 5432