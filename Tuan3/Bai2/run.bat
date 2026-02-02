@echo off
echo ========================================
echo   He thong Quan ly Thu vien
echo   Design Patterns Demo
echo ========================================
echo.

echo Kiem tra Java version...
java -version
echo.

echo Kiem tra JAVA_HOME...
if "%JAVA_HOME%"=="" (
    echo CANH BAO: JAVA_HOME chua duoc thiet lap!
    echo Ban can cai dat JDK va thiet lap JAVA_HOME
    echo.
    echo Huong dan:
    echo 1. Tai va cai dat JDK 8 hoac cao hon tu Oracle hoac OpenJDK
    echo 2. Thiet lap JAVA_HOME tro den thu muc JDK
    echo 3. Them %%JAVA_HOME%%\bin vao PATH
    echo.
    echo Vi du:
    echo set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_XXX
    echo set PATH=%%JAVA_HOME%%\bin;%%PATH%%
    echo.
    pause
    exit /b 1
) else (
    echo JAVA_HOME: %JAVA_HOME%
)

echo.
echo Kiem tra javac (Java Compiler)...
javac -version
if %errorlevel% neq 0 (
    echo LOI: Khong tim thay javac. Ban can cai dat JDK (khong phai JRE)
    pause
    exit /b 1
)

echo.
echo Bat dau build va chay ung dung...
echo.

gradlew.bat clean bootRun

if %errorlevel% neq 0 (
    echo.
    echo LOI: Khong the chay ung dung!
    echo Vui long kiem tra:
    echo 1. Da cai dat JDK (khong phai JRE)
    echo 2. JAVA_HOME da duoc thiet lap dung
    echo 3. Port 8080 chua bi su dung
    echo.
    pause
    exit /b 1
)

pause