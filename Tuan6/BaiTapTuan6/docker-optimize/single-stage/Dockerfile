# ============================================
# SINGLE STAGE - KHÔNG tối ưu
# Vấn đề: build tools, cache, temp files
# đều nằm trong image cuối --> image phình to
# ============================================
FROM postgres:15

# Cài thêm tools (chỉ dùng lúc build, nhưng vẫn còn trong image)
RUN apt-get update && apt-get install -y \
    curl \
    wget \
    vim \
    build-essential \
    && rm -rf /var/lib/apt/lists/*

# Biến môi trường
ENV POSTGRES_DB=shopdb
ENV POSTGRES_USER=admin
ENV POSTGRES_PASSWORD=secret123

# Copy script init data
COPY ../init.sql /docker-entrypoint-initdb.d/init.sql

# Expose port
EXPOSE 5432
