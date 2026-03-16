# Bài 4: Tạo Dockerfile chạy trang web tĩnh bằng Nginx
# Sử dụng image nginx:latest làm base image
FROM nginx:latest

# Xóa file index.html mặc định của Nginx
RUN rm /usr/share/nginx/html/index.html

# Copy file index.html của chúng ta vào thư mục phục vụ của Nginx
COPY index.html /usr/share/nginx/html/index.html

# Nginx mặc định lắng nghe trên cổng 80
EXPOSE 80

# Lệnh khởi động Nginx ở foreground (daemon off để container không thoát)
CMD ["nginx", "-g", "daemon off;"]
