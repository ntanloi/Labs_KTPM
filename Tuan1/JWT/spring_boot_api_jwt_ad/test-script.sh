#!/bin/bash

# JWT Authentication Test Script
BASE_URL="http://localhost:8080"
echo "🚀 Starting JWT Authentication Tests..."
echo "Base URL: $BASE_URL"
echo "=================================="

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Test counter
TESTS_PASSED=0
TESTS_FAILED=0

# Function to test API endpoint
test_endpoint() {
    local method=$1
    local url=$2
    local headers=$3
    local data=$4
    local expected_status=$5
    local test_name=$6
    
    echo -e "\n${YELLOW}Testing: $test_name${NC}"
    echo "Request: $method $url"
    
    if [ -n "$data" ]; then
        response=$(curl -s -w "\n%{http_code}" -X $method "$url" $headers -d "$data")
    else
        response=$(curl -s -w "\n%{http_code}" -X $method "$url" $headers)
    fi
    
    # Extract status code (last line)
    status_code=$(echo "$response" | tail -n1)
    # Extract response body (all but last line)
    response_body=$(echo "$response" | head -n -1)
    
    if [ "$status_code" -eq "$expected_status" ]; then
        echo -e "${GREEN}✅ PASS${NC} - Status: $status_code"
        TESTS_PASSED=$((TESTS_PASSED + 1))
        if [ -n "$response_body" ]; then
            echo "Response: $response_body"
        fi
    else
        echo -e "${RED}❌ FAIL${NC} - Expected: $expected_status, Got: $status_code"
        echo "Response: $response_body"
        TESTS_FAILED=$((TESTS_FAILED + 1))
    fi
}

# Wait for server to be ready
echo "⏳ Waiting for server to be ready..."
for i in {1..30}; do
    if curl -s "$BASE_URL/api/test/public" > /dev/null; then
        echo "✅ Server is ready!"
        break
    fi
    if [ $i -eq 30 ]; then
        echo "❌ Server not responding after 30 seconds"
        exit 1
    fi
    sleep 1
done

# Test 1: Public endpoint
test_endpoint "GET" "$BASE_URL/api/test/public" "" "" 200 "Public Endpoint (No Auth Required)"

# Test 2: Protected endpoint without token (should fail)
test_endpoint "GET" "$BASE_URL/api/test/protected" "" "" 401 "Protected Endpoint Without Token"

# Test 3: Register new user
USER_DATA='{"username":"testuser","password":"password123"}'
test_endpoint "POST" "$BASE_URL/auth/register" "-H 'Content-Type: application/json'" "$USER_DATA" 200 "User Registration"

# Test 4: Login to get tokens
echo -e "\n${YELLOW}Getting authentication tokens...${NC}"
login_response=$(curl -s -X POST "$BASE_URL/auth/login" \
    -H "Content-Type: application/json" \
    -d "$USER_DATA")

if echo "$login_response" | grep -q "accessToken"; then
    echo -e "${GREEN}✅ Login successful${NC}"
    TESTS_PASSED=$((TESTS_PASSED + 1))
    
    # Extract tokens using basic text processing
    ACCESS_TOKEN=$(echo "$login_response" | grep -o '"accessToken":"[^"]*"' | cut -d'"' -f4)
    REFRESH_TOKEN=$(echo "$login_response" | grep -o '"refreshToken":"[^"]*"' | cut -d'"' -f4)
    
    echo "Access Token: ${ACCESS_TOKEN:0:50}..."
    echo "Refresh Token: ${REFRESH_TOKEN:0:50}..."
else
    echo -e "${RED}❌ Login failed${NC}"
    echo "Response: $login_response"
    TESTS_FAILED=$((TESTS_FAILED + 1))
    exit 1
fi

# Test 5: Protected endpoint with valid token
AUTH_HEADER="-H 'Authorization: Bearer $ACCESS_TOKEN'"
test_endpoint "GET" "$BASE_URL/api/test/protected" "$AUTH_HEADER" "" 200 "Protected Endpoint With Valid Token"

# Test 6: User endpoint (requires USER_READ permission)
test_endpoint "GET" "$BASE_URL/api/test/user" "$AUTH_HEADER" "" 200 "User Endpoint (USER_READ Permission)"

# Test 7: Admin endpoint (should fail - no ADMIN role)
test_endpoint "GET" "$BASE_URL/api/test/admin" "$AUTH_HEADER" "" 403 "Admin Endpoint (Should Fail - No ADMIN Role)"

# Test 8: Refresh token
REFRESH_DATA="{\"refreshToken\":\"$REFRESH_TOKEN\"}"
echo -e "\n${YELLOW}Testing token refresh...${NC}"
refresh_response=$(curl -s -X POST "$BASE_URL/auth/refresh" \
    -H "Content-Type: application/json" \
    -d "$REFRESH_DATA")

if echo "$refresh_response" | grep -q "accessToken"; then
    echo -e "${GREEN}✅ Token refresh successful${NC}"
    TESTS_PASSED=$((TESTS_PASSED + 1))
    
    # Extract new access token
    NEW_ACCESS_TOKEN=$(echo "$refresh_response" | grep -o '"accessToken":"[^"]*"' | cut -d'"' -f4)
    echo "New Access Token: ${NEW_ACCESS_TOKEN:0:50}..."
else
    echo -e "${RED}❌ Token refresh failed${NC}"
    echo "Response: $refresh_response"
    TESTS_FAILED=$((TESTS_FAILED + 1))
fi

# Test 9: Use new access token
NEW_AUTH_HEADER="-H 'Authorization: Bearer $NEW_ACCESS_TOKEN'"
test_endpoint "GET" "$BASE_URL/api/test/protected" "$NEW_AUTH_HEADER" "" 200 "Protected Endpoint With Refreshed Token"

# Test 10: Logout (revoke refresh tokens)
test_endpoint "POST" "$BASE_URL/auth/logout" "$AUTH_HEADER" "" 200 "Logout (Revoke Refresh Tokens)"

# Test 11: Try to use refresh token after logout (should fail)
test_endpoint "POST" "$BASE_URL/auth/refresh" "-H 'Content-Type: application/json'" "$REFRESH_DATA" 401 "Refresh Token After Logout (Should Fail)"

# Test 12: Invalid token
INVALID_AUTH="-H 'Authorization: Bearer invalid_token'"
test_endpoint "GET" "$BASE_URL/api/test/protected" "$INVALID_AUTH" "" 401 "Invalid Token"

# Test 13: Wrong credentials
WRONG_CREDS='{"username":"testuser","password":"wrongpassword"}'
test_endpoint "POST" "$BASE_URL/auth/login" "-H 'Content-Type: application/json'" "$WRONG_CREDS" 401 "Wrong Credentials"

# Summary
echo -e "\n=================================="
echo -e "${YELLOW}TEST SUMMARY${NC}"
echo -e "=================================="
echo -e "✅ Tests Passed: ${GREEN}$TESTS_PASSED${NC}"
echo -e "❌ Tests Failed: ${RED}$TESTS_FAILED${NC}"
echo -e "📊 Total Tests: $((TESTS_PASSED + TESTS_FAILED))"

if [ $TESTS_FAILED -eq 0 ]; then
    echo -e "\n🎉 ${GREEN}All tests passed!${NC}"
    exit 0
else
    echo -e "\n⚠️  ${RED}Some tests failed!${NC}"
    exit 1
fi