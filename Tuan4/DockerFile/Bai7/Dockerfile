# Base image Python
FROM python:3.11-slim

# Thiết lập thư mục làm việc
WORKDIR /app

# Khai báo biến môi trường mặc định
ENV APP_ENV=development

# Copy requirements trước → tận dụng cache
COPY requirements.txt .

# Cài dependencies (nếu có)
RUN pip install --no-cache-dir -r requirements.txt

# Copy source code
COPY app.py .

# Chạy ứng dụng
CMD ["python", "app.py"]