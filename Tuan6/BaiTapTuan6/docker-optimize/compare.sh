#!/bin/bash
# ============================================
# Script so sánh 2 cách build Docker image
# ============================================

echo "================================================"
echo "  DEMO: So sánh Single-stage vs Multi-stage"
echo "================================================"

# ---------- BUILD IMAGES ----------
echo ""
echo "[1/4] Building SINGLE-STAGE image..."
docker build -t postgres-single:demo -f single-stage/Dockerfile .
echo "Done: postgres-single:demo"

echo ""
echo "[2/4] Building MULTI-STAGE image..."
docker build -t postgres-multi:demo -f multi-stage/Dockerfile .
echo "Done: postgres-multi:demo"

# ---------- SO SÁNH SIZE ----------
echo ""
echo "[3/4] So sánh kích thước image:"
echo "------------------------------------------------"
docker images | grep -E "REPOSITORY|postgres-single|postgres-multi"
echo "------------------------------------------------"

# ---------- CHẠY CONTAINER & KIỂM TRA DATA ----------
echo ""
echo "[4/4] Chạy container từ multi-stage image và kiểm tra data..."

# Dừng container cũ nếu có
docker rm -f pg-demo 2>/dev/null

# Chạy container
docker run -d \
    --name pg-demo \
    -p 5433:5432 \
    postgres-multi:demo

echo "Đợi PostgreSQL khởi động..."
sleep 5

# Kiểm tra data đã có sẵn trong image
echo ""
echo ">>> Dữ liệu trong bảng PRODUCTS:"
docker exec pg-demo psql -U admin -d shopdb -c "SELECT * FROM products;"

echo ""
echo ">>> Dữ liệu trong bảng USERS:"
docker exec pg-demo psql -U admin -d shopdb -c "SELECT * FROM users;"

echo ""
echo "================================================"
echo "  KẾT QUẢ: Data đã có sẵn trong image!"
echo "  Không cần insert thêm khi chạy container."
echo "================================================"

# Dọn dẹp
echo ""
read -p "Xóa container demo? (y/n): " choice
if [ "$choice" = "y" ]; then
    docker rm -f pg-demo
    echo "Đã xóa container pg-demo"
fi
