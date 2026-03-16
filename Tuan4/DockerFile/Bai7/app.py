import os
import time

# Đọc biến môi trường APP_ENV
app_env = os.environ.get('APP_ENV', 'unknown')

print("=" * 40)
print(f"  Ứng dụng Python đang chạy!")
print(f"  Môi trường: {app_env}")
print("=" * 40)

# Giữ container chạy để xem log
while True:
    print(f"[{app_env}] Server đang hoạt động...")
    time.sleep(3)
```

### 📄 `requirements.txt`
```
# Bài này không cần thư viện ngoài
# File này để minh họa cấu trúc chuẩn