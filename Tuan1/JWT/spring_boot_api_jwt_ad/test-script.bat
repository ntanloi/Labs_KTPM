@echo off
setlocal enabledelayedexpansion

REM JWT Authentication Test Script for Windows
set BASE_URL=http://localhost:8080
echo 🚀 Starting JWT Authentication Tests...
echo Base URL: %BASE_URL%
echo ==================================

set TESTS_PASSED=0
set TESTS_FAILED=0

echo ⏳ Waiting for server to be ready...
timeout /t 5 /nobreak > nul

REM Test 1: Public endpoint
echo.
echo Testing: Public Endpoint (No Auth Required)
curl -s -o response.tmp -w "%%{http_code}" %BASE_URL%/api/test/public > status.tmp
set /p STATUS=<status.tmp
if "!STATUS!"=="200" (
    echo ✅ PASS - Status: !STATUS!
    set /a TESTS_PASSED+=1
) else (
    echo ❌ FAIL - Expected: 200, Got: !STATUS!
    set /a TESTS_FAILED+=1
)

REM Test 2: Register user
echo.
echo Testing: User Registration
curl -s -o response.tmp -w "%%{http_code}" -X POST %BASE_URL%/auth/register -H "Content-Type: application/json" -d "{\"username\":\"testuser\",\"password\":\"password123\"}" > status.tmp
set /p STATUS=<status.tmp
if "!STATUS!"=="200" (
    echo ✅ PASS - Status: !STATUS!
    set /a TESTS_PASSED+=1
) else (
    echo ❌ FAIL - Expected: 200, Got: !STATUS!
    set /a TESTS_FAILED+=1
)

REM Test 3: Login
echo.
echo Testing: User Login
curl -s -o login_response.tmp %BASE_URL%/auth/login -H "Content-Type: application/json" -d "{\"username\":\"testuser\",\"password\":\"password123\"}"

REM Extract access token (simplified - you may need to use a JSON parser for production)
for /f "tokens=2 delims=:" %%a in ('findstr "accessToken" login_response.tmp') do (
    set TOKEN_PART=%%a
    set ACCESS_TOKEN=!TOKEN_PART:"=!
    set ACCESS_TOKEN=!ACCESS_TOKEN:,=!
)

if defined ACCESS_TOKEN (
    echo ✅ Login successful
    set /a TESTS_PASSED+=1
    echo Access Token: !ACCESS_TOKEN:~0,50!...
) else (
    echo ❌ Login failed
    set /a TESTS_FAILED+=1
    goto :summary
)

REM Test 4: Protected endpoint with token
echo.
echo Testing: Protected Endpoint With Valid Token
curl -s -o response.tmp -w "%%{http_code}" %BASE_URL%/api/test/protected -H "Authorization: Bearer !ACCESS_TOKEN!" > status.tmp
set /p STATUS=<status.tmp
if "!STATUS!"=="200" (
    echo ✅ PASS - Status: !STATUS!
    set /a TESTS_PASSED+=1
) else (
    echo ❌ FAIL - Expected: 200, Got: !STATUS!
    set /a TESTS_FAILED+=1
)

REM Test 5: Invalid token
echo.
echo Testing: Invalid Token
curl -s -o response.tmp -w "%%{http_code}" %BASE_URL%/api/test/protected -H "Authorization: Bearer invalid_token" > status.tmp
set /p STATUS=<status.tmp
if "!STATUS!"=="401" (
    echo ✅ PASS - Status: !STATUS!
    set /a TESTS_PASSED+=1
) else (
    echo ❌ FAIL - Expected: 401, Got: !STATUS!
    set /a TESTS_FAILED+=1
)

:summary
echo.
echo ==================================
echo TEST SUMMARY
echo ==================================
echo ✅ Tests Passed: %TESTS_PASSED%
echo ❌ Tests Failed: %TESTS_FAILED%
set /a TOTAL_TESTS=%TESTS_PASSED%+%TESTS_FAILED%
echo 📊 Total Tests: %TOTAL_TESTS%

if %TESTS_FAILED%==0 (
    echo.
    echo 🎉 All tests passed!
) else (
    echo.
    echo ⚠️ Some tests failed!
)

REM Cleanup
del response.tmp status.tmp login_response.tmp 2>nul

pause