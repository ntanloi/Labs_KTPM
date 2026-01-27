package fit.iuh.demo.demo;

import fit.iuh.demo.observer.*;

/**
 * Demo class cho Observer Pattern
 * Minh họa hệ thống thông báo cho cổ phiếu và quản lý task
 */
public class ObserverPatternDemo {
    
    public static void runDemo() {
        System.out.println("=== OBSERVER PATTERN DEMO ===");
        
        // Demo 1: Hệ thống thông báo cổ phiếu
        System.out.println("\n--- Demo 1: Hệ thống thông báo cổ phiếu ---");
        
        // Tạo cổ phiếu
        Stock vn30 = new Stock("VN30", 1250.5);
        Stock fpt = new Stock("FPT", 85.2);
        
        // Tạo các nhà đầu tư
        Investor investor1 = new Investor("Nguyễn Văn A");
        Investor investor2 = new Investor("Trần Thị B");
        Investor investor3 = new Investor("Lê Văn C");
        
        // Đăng ký theo dõi cổ phiếu
        System.out.println("\n📋 Đăng ký theo dõi cổ phiếu VN30:");
        vn30.attach(investor1);
        vn30.attach(investor2);
        
        System.out.println("\n📋 Đăng ký theo dõi cổ phiếu FPT:");
        fpt.attach(investor2);
        fpt.attach(investor3);
        
        // Thay đổi giá cổ phiếu
        System.out.println("\n💰 Thay đổi giá cổ phiếu:");
        vn30.setPrice(1275.8);
        
        System.out.println();
        fpt.setPrice(88.5);
        
        System.out.println();
        vn30.setPrice(1240.2);
        
        // Demo 2: Hệ thống quản lý task
        System.out.println("\n--- Demo 2: Hệ thống quản lý task ---");
        
        // Tạo task manager
        TaskManager task1 = new TaskManager("Phát triển tính năng đăng nhập", "TODO", "Nguyễn Văn D");
        TaskManager task2 = new TaskManager("Thiết kế database", "IN_PROGRESS", "Trần Thị E");
        
        // Tạo team members
        TeamMember dev1 = new TeamMember("Nguyễn Văn D", "Developer");
        TeamMember dev2 = new TeamMember("Trần Thị E", "Database Designer");
        TeamMember pm = new TeamMember("Lê Văn F", "Project Manager");
        TeamMember tester = new TeamMember("Phạm Thị G", "Tester");
        
        // Đăng ký theo dõi tasks
        System.out.println("\n📋 Đăng ký theo dõi task 1:");
        task1.attach(dev1);
        task1.attach(pm);
        task1.attach(tester);
        
        System.out.println("\n📋 Đăng ký theo dõi task 2:");
        task2.attach(dev2);
        task2.attach(pm);
        
        // Thay đổi trạng thái tasks
        System.out.println("\n🔄 Thay đổi trạng thái tasks:");
        task1.setStatus("IN_PROGRESS");
        
        System.out.println();
        task2.setStatus("DONE");
        
        System.out.println();
        task1.setAssignee("Trần Thị H");
        
        System.out.println();
        task1.setStatus("TESTING");
        
        // Hủy đăng ký
        System.out.println("\n📋 Hủy đăng ký theo dõi:");
        task1.detach(tester);
        task1.setStatus("DONE");
        
        System.out.println("\n=== KẾT THÚC OBSERVER PATTERN DEMO ===\n");
    }
}