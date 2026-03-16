# MicroCMS — Kiến trúc CMS với Microkernel Plugin System

## 🏗️ Kiến trúc tổng quan

```
cms-project/
├── backend/                        # Spring Boot (Layer Architecture)
│   └── src/main/java/com/cms/
│       ├── controller/             # Layer 1: Presentation (REST API)
│       │   ├── PostController.java
│       │   └── PluginController.java
│       ├── service/                # Layer 2: Business Logic
│       │   ├── PostService.java
│       │   └── PluginService.java
│       ├── repository/             # Layer 3: Data Access
│       │   └── PostRepository.java
│       ├── model/                  # Entities
│       │   └── Post.java
│       └── plugin/                 # Microkernel Plugin System
│           ├── CmsPlugin.java      # Plugin interface
│           ├── PluginRegistry.java # Plugin manager (core kernel)
│           └── AuditLogPlugin.java # Sample plugin
├── frontend/                       # React JS
│   └── src/
│       ├── components/             # PostList, PostForm, PluginPanel
│       ├── hooks/                  # usePosts custom hook
│       └── services/               # API calls (axios)
└── docker-compose.yml              # Full stack setup
```

## 🚀 Chạy toàn bộ hệ thống

```bash
# Clone / vào thư mục project
cd cms-project

# Build & run tất cả services
docker-compose up --build

# Chạy nền (background)
docker-compose up --build -d
```

Sau khi chạy xong:
| Service    | URL                          |
|------------|------------------------------|
| Frontend   | http://localhost:3000        |
| Backend API| http://localhost:8080/api    |
| Database   | localhost:5432               |

## 🧪 Chạy Tests (Backend)

```bash
cd backend
mvn test
```

Các test cases:
- `PostServiceTest` — Unit test service layer (mock repository + plugin)
- `PostControllerTest` — Integration test REST API (MockMvc)

## 🔌 Thêm Plugin mới (Microkernel)

Tạo class mới implement `CmsPlugin`:

```java
@Component
public class MyPlugin implements CmsPlugin {
    private final PluginRegistry registry;

    public MyPlugin(PluginRegistry registry) { this.registry = registry; }

    @PostConstruct
    public void register() { registry.register(this); }

    @Override public String getName() { return "my-plugin"; }
    @Override public String getVersion() { return "1.0.0"; }
    @Override public void initialize() { /* setup */ }
    @Override public void onPostCreate(Object post) { /* handle */ }
    @Override public void onPostUpdate(Object post) { /* handle */ }
    @Override public void onPostDelete(Long id) { /* handle */ }
}
```

Spring Boot tự động load — không cần sửa code cũ. ✅

## 📡 API Endpoints

| Method | Endpoint                  | Mô tả             |
|--------|---------------------------|-------------------|
| GET    | /api/posts                | Lấy tất cả posts  |
| GET    | /api/posts/published      | Posts đã publish  |
| GET    | /api/posts/{id}           | Lấy post theo id  |
| GET    | /api/posts/search?keyword | Tìm kiếm          |
| POST   | /api/posts                | Tạo post mới      |
| PUT    | /api/posts/{id}           | Cập nhật post     |
| PATCH  | /api/posts/{id}/publish   | Publish post      |
| DELETE | /api/posts/{id}           | Xóa post          |
| GET    | /api/plugins              | Danh sách plugins |

## 🛑 Dừng hệ thống

```bash
docker-compose down

# Xóa cả data
docker-compose down -v
```
