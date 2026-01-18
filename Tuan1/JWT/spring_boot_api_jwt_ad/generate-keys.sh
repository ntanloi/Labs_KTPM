#!/bin/bash

# Tạo thư mục certs nếu chưa tồn tại
mkdir -p src/main/resources/certs

# Tạo RSA private key (2048 bits)
openssl genrsa -out src/main/resources/certs/private.pem 2048

# Tạo RSA public key từ private key
openssl rsa -in src/main/resources/certs/private.pem -pubout -out src/main/resources/certs/public.pem

echo "RSA key pair generated successfully!"
echo "Private key: src/main/resources/certs/private.pem"
echo "Public key: src/main/resources/certs/public.pem"