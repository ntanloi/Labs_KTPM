# Base image PHP 8.2 với Apache
FROM php:8.2-apache

# Khai báo biến môi trường
ENV APP_ENV=development
ENV APP_NAME="My PHP App"

# Cài thêm extensions PHP thường dùng
RUN docker-php-ext-install pdo pdo_mysql

# Bật module rewrite của Apache (cần cho framework)
RUN a2enmod rewrite

# Copy source code vào thư mục web của Apache
COPY src/ /var/www/html/

# Phân quyền cho thư mục web
RUN chown -R www-data:www-data /var/www/html/

# Expose port 80 (Apache mặc định)
EXPOSE 80

# Apache tự khởi động (có sẵn trong base image)