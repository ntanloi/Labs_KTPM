package fit.iuh.demo.observer;

/**
 * Concrete Observer - Thành viên nhóm
 * Nhận thông báo khi trạng thái task thay đổi
 */
public class TeamMember implements Observer {
    private String name;
    private String role;
    
    public TeamMember(String name, String role) {
        this.name = name;
        this.role = role;
    }
    
    public String getName() {
        return name;
    }
    
    public String getRole() {
        return role;
    }
    
    @Override
    public void update(String message) {
        System.out.println("👥 " + role + " " + name + " nhận thông báo: " + message);
    }
}