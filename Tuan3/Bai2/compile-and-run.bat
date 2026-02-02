@echo off
echo ========================================
echo   He thong Quan ly Thu vien
echo   Compile va chay truc tiep
echo ========================================
echo.

echo Kiem tra Java...
java -version
echo.

echo Tao thu muc build...
if not exist "build" mkdir build
if not exist "build\classes" mkdir build\classes

echo.
echo Download Spring Boot JAR files...
echo Ban can download cac file JAR sau va dat vao thu muc lib\:
echo.
echo 1. spring-boot-starter-web-2.7.18.jar
echo 2. spring-boot-starter-data-jpa-2.7.18.jar  
echo 3. spring-boot-starter-thymeleaf-2.7.18.jar
echo 4. h2-2.1.214.jar
echo 5. lombok-1.18.30.jar
echo.
echo Hoac su dung IDE nhu IntelliJ IDEA, Eclipse de import project
echo.

pause