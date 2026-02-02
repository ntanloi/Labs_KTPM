@echo off
echo ========================================
echo   Cai dat Java cho He thong Thu vien
echo ========================================
echo.

echo Huong dan cai dat JDK:
echo.
echo 1. DOWNLOAD JDK:
echo    - Oracle JDK: https://www.oracle.com/java/technologies/downloads/
echo    - OpenJDK: https://adoptium.net/
echo    - Chon phien ban JDK 8 hoac cao hon
echo.

echo 2. CAI DAT JDK:
echo    - Chay file installer da download
echo    - Chon thu muc cai dat (vi du: C:\Program Files\Java\jdk-8)
echo.

echo 3. THIET LAP BIEN MOI TRUONG:
echo    - Mo Control Panel ^> System ^> Advanced System Settings
echo    - Click "Environment Variables"
echo    - Trong "System Variables", click "New":
echo      * Variable name: JAVA_HOME
echo      * Variable value: C:\Program Files\Java\jdk-8 (duong dan JDK cua ban)
echo    - Tim bien PATH, click "Edit", them: %%JAVA_HOME%%\bin
echo.

echo 4. KIEM TRA CAI DAT:
echo    - Mo Command Prompt moi
echo    - Chay: java -version
echo    - Chay: javac -version
echo    - Neu thanh cong, ban se thay thong tin JDK
echo.

echo 5. CHAY UNG DUNG:
echo    - Quay lai thu muc du an
echo    - Chay: run.bat
echo.

echo ========================================
echo   Cac lenh kiem tra nhanh:
echo ========================================
echo.

echo Kiem tra Java hien tai:
java -version 2>nul
if %errorlevel% neq 0 (
    echo - Java chua duoc cai dat hoac chua co trong PATH
) else (
    echo - Java da duoc cai dat
)

echo.
echo Kiem tra Java Compiler:
javac -version 2>nul
if %errorlevel% neq 0 (
    echo - JDK chua duoc cai dat (chi co JRE)
    echo - CAN CAI DAT JDK!
) else (
    echo - JDK da duoc cai dat
)

echo.
echo Kiem tra JAVA_HOME:
if "%JAVA_HOME%"=="" (
    echo - JAVA_HOME chua duoc thiet lap
    echo - CAN THIET LAP JAVA_HOME!
) else (
    echo - JAVA_HOME: %JAVA_HOME%
    if exist "%JAVA_HOME%\bin\javac.exe" (
        echo - JAVA_HOME hop le
    ) else (
        echo - JAVA_HOME khong hop le (khong tim thay javac.exe)
    )
)

echo.
echo ========================================
echo Nhan phim bat ky de dong...
pause >nul