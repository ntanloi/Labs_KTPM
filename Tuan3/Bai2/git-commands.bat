@echo off
echo ========================================
echo   Git Commands for Library Management
echo ========================================
echo.

echo Initializing Git repository...
git init

echo.
echo Adding all files to staging...
git add .

echo.
echo Checking status...
git status

echo.
echo Creating commit...
git commit -m "feat: Implement Library Management System with 5 Design Patterns

🎯 Complete library management system with web interface implementing:
- Singleton Pattern: LibraryService managed by Spring
- Factory Method Pattern: BookFactory for different book types  
- Strategy Pattern: SearchStrategy for flexible search algorithms
- Observer Pattern: LibraryObserver for automatic notifications
- Decorator Pattern: BorrowService with additional features

🌐 Features:
- Bootstrap 5 responsive web interface
- Book management (add, list, borrow, return)
- Search with strategy selection
- Overdue books tracking
- H2 database with sample data

🏗️ Tech Stack:
- Spring Boot 3.2.2 + Java 17
- Spring Data JPA + Thymeleaf
- Comprehensive documentation and demo guides

✅ Ready for academic presentation and demonstration"

echo.
echo ========================================
echo   Commit completed successfully!
echo ========================================
echo.

echo Optional: Add remote repository
echo git remote add origin https://github.com/username/library-management.git
echo git branch -M main  
echo git push -u origin main

echo.
echo Current Git status:
git log --oneline -1
git status

pause