# 1. Base image
FROM python:3.9

# 2. Thư mục làm việc
WORKDIR /app

# 3. Copy file requirements trước
COPY requirements.txt .

# 4. Cài đặt dependencies
RUN pip install -r requirements.txt

# 5. Copy toàn bộ code
COPY . .

# 6. Mở cổng 5000
EXPOSE 5000

# 7. Chạy ứng dụng
CMD ["python", "app.py"]