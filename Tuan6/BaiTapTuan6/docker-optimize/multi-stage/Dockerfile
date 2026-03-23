# ============================================
# MULTI STAGE - Tối ưu hóa
# Stage 1 (builder): môi trường chỉ cần lúc BUILD
# Stage 2 (runtime): chỉ giữ những gì cần lúc CHẠY
# ============================================

# ---------- STAGE 1: Builder ----------
# Môi trường này chỉ tồn tại lúc build image
# KHÔNG được copy vào image cuối
FROM postgres:15 AS builder

# Cài tools chỉ cần lúc build/prepare data
RUN apt-get update && apt-get install -y \
    curl \
    wget \
    vim \
    build-essential \
    && rm -rf /var/lib/apt/lists/*

# Giả lập: download/xử lý file config, transform SQL, v.v.
COPY ../init.sql /tmp/raw_init.sql

# Xử lý file SQL (ví dụ: thêm comment, validate, transform)
RUN echo "-- Generated at build time: $(date)" > /tmp/init_final.sql \
    && cat /tmp/raw_init.sql >> /tmp/init_final.sql


# ---------- STAGE 2: Runtime ----------
# Image cuối - chỉ lấy những gì cần thiết từ stage 1
FROM postgres:15

# Biến môi trường runtime
ENV POSTGRES_DB=shopdb
ENV POSTGRES_USER=admin
ENV POSTGRES_PASSWORD=secret123

# Chỉ COPY file SQL đã xử lý từ stage builder
# KHÔNG copy curl, wget, vim, build-essential...
COPY --from=builder /tmp/init_final.sql /docker-entrypoint-initdb.d/init.sql

EXPOSE 5432
