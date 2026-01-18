@echo off
REM Tạo thư mục certs nếu chưa tồn tại
if not exist "src\main\resources\certs" mkdir "src\main\resources\certs"

REM Tạo RSA private key (2048 bits)
openssl genrsa -out src\main\resources\certs\private.pem 2048

REM Tạo RSA public key từ private key
openssl rsa -in src\main\resources\certs\private.pem -pubout -out src\main\resources\certs\public.pem

echo RSA key pair generated successfully!
echo Private key: src\main\resources\certs\private.pem
echo Public key: src\main\resources\certs\public.pem
pause