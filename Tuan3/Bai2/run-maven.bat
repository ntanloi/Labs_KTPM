@echo off
echo ========================================
echo   He thong Quan ly Thu vien (Maven)
echo   Design Patterns Demo
echo ========================================
echo.

echo Kiem tra Java version...
java -version
echo.

echo Chay ung dung bang Maven...
echo Maven khong can JDK, chi can JRE!
echo.

mvn spring-boot:run

if %errorlevel% neq 0 (
    echo.
    echo LOI: Khong the chay ung dung!
    echo Vui long kiem tra:
    echo 1. Da cai dat Maven
    echo 2. Port 8080 chua bi su dung
    echo 3. Ket noi internet de download dependencies
    echo.
    echo Neu chua co Maven, download tai: https://maven.apache.org/download.cgi
    echo.
    pause
    exit /b 1
)

pause