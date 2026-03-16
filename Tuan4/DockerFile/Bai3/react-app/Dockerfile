# 1. Build stage - dùng node:18-alpine để build React
FROM node:18-alpine AS build

# 2. Thư mục làm việc
WORKDIR /app

# 3. Copy package.json trước
COPY package.json .

# 4. Cài dependencies
RUN npm install

# 5. Copy toàn bộ source code
COPY . .

# 6. Build ứng dụng React thành file tĩnh
RUN npm run build

# -------------------------------------------
# 7. Production stage - dùng nginx để serve
FROM nginx:alpine

# 8. Copy file build sang nginx
COPY --from=build /app/build /usr/share/nginx/html

# 9. Mở cổng 80
EXPOSE 80

# 10. Chạy nginx
CMD ["nginx", "-g", "daemon off;"]