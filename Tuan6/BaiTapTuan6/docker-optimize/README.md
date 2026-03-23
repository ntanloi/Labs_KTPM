# Docker Image Optimization - Multi-stage Build

## Cấu trúc

```
docker-optimize/
├── single-stage/Dockerfile   # Không tối ưu
├── multi-stage/Dockerfile    # Tối ưu với multi-stage
├── init.sql                  # Data mẫu
└── compare.sh                # Script so sánh
```

## Khái niệm chính

### Single-stage (KHÔNG tối ưu)
- Tất cả tools (curl, wget, vim, build-essential) đều nằm trong image cuối
- Image to, chứa nhiều thứ không cần thiết lúc runtime

### Multi-stage (Tối ưu)
- **Stage 1 (builder)**: cài tools, xử lý file, chuẩn bị data
- **Stage 2 (runtime)**: chỉ lấy output cần thiết từ stage 1
- Image nhỏ hơn, an toàn hơn, không có build tools thừa

## Chạy demo

```bash
cd docker-optimize
chmod +x compare.sh
./compare.sh
```

## Chạy thủ công

```bash
# Build
docker build -t postgres-single:demo -f single-stage/Dockerfile .
docker build -t postgres-multi:demo  -f multi-stage/Dockerfile .

# So sánh size
docker images | grep postgres

# Chạy container (data đã có sẵn)
docker run -d --name pg-demo -p 5433:5432 postgres-multi:demo

# Kiểm tra data
docker exec pg-demo psql -U admin -d shopdb -c "SELECT * FROM products;"
```
