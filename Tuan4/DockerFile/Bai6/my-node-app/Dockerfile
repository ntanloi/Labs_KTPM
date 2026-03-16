# ==========================================
# STAGE 1: BUILD
# Dùng node:18 (full image) để build code
# ==========================================
FROM node:18 AS builder

# Thiết lập thư mục làm việc
WORKDIR /app

# Copy package.json trước → tận dụng Docker cache
COPY package.json ./

# Cài đặt dependencies
RUN npm install

# Copy toàn bộ source code
COPY . .

# Chạy build script
RUN npm run build


# ==========================================
# STAGE 2: RUN
# Dùng node:18-alpine (nhỏ gọn) để chạy app
# ==========================================
FROM node:18-alpine

WORKDIR /app

# Copy dependencies và code từ stage builder
COPY --from=builder /app/node_modules ./node_modules
COPY --from=builder /app/src ./src
COPY --from=builder /app/package.json ./

# Expose port ứng dụng
EXPOSE 3000

# Chạy ứng dụng
CMD ["node", "src/index.js"]