package fit.iuh.demo.controller;

import fit.iuh.demo.observer.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller cho Observer Pattern demo
 */
@Controller
@RequestMapping("/observer")
public class ObserverController {
    
    private Stock vn30Stock;
    private Stock fptStock;
    private TaskManager task1;
    private TaskManager task2;
    
    private List<Investor> investors;
    private List<TeamMember> teamMembers;
    private List<String> notifications;
    
    public ObserverController() {
        initializeObserverSystem();
    }
    
    @GetMapping
    public String observerDemo(Model model) {
        model.addAttribute("title", "Observer Pattern - Hệ thống Thông báo");
        model.addAttribute("vn30Stock", vn30Stock);
        model.addAttribute("fptStock", fptStock);
        model.addAttribute("task1", task1);
        model.addAttribute("task2", task2);
        model.addAttribute("investors", investors);
        model.addAttribute("teamMembers", teamMembers);
        model.addAttribute("notifications", notifications);
        return "observer";
    }
    
    @PostMapping("/update-stock-price")
    public String updateStockPrice(@RequestParam String stockSymbol, 
                                  @RequestParam double newPrice) {
        if ("VN30".equals(stockSymbol)) {
            vn30Stock.setPrice(newPrice);
        } else if ("FPT".equals(stockSymbol)) {
            fptStock.setPrice(newPrice);
        }
        return "redirect:/observer";
    }
    
    @PostMapping("/update-task-status")
    public String updateTaskStatus(@RequestParam String taskName,
                                  @RequestParam String newStatus) {
        if (task1.getTaskName().equals(taskName)) {
            task1.setStatus(newStatus);
        } else if (task2.getTaskName().equals(taskName)) {
            task2.setStatus(newStatus);
        }
        return "redirect:/observer";
    }
    
    @PostMapping("/update-task-assignee")
    public String updateTaskAssignee(@RequestParam String taskName,
                                    @RequestParam String newAssignee) {
        if (task1.getTaskName().equals(taskName)) {
            task1.setAssignee(newAssignee);
        } else if (task2.getTaskName().equals(taskName)) {
            task2.setAssignee(newAssignee);
        }
        return "redirect:/observer";
    }
    
    @PostMapping("/clear-notifications")
    public String clearNotifications() {
        notifications.clear();
        return "redirect:/observer";
    }
    
    private void initializeObserverSystem() {
        notifications = new ArrayList<>();
        
        // Tạo stocks
        vn30Stock = new Stock("VN30", 1250.5) {
            @Override
            protected void notifyObservers(String message) {
                super.notifyObservers(message);
                notifications.add("📈 " + message);
            }
        };
        
        fptStock = new Stock("FPT", 85.2) {
            @Override
            protected void notifyObservers(String message) {
                super.notifyObservers(message);
                notifications.add("📈 " + message);
            }
        };
        
        // Tạo tasks
        task1 = new TaskManager("Phát triển tính năng đăng nhập", "TODO", "Nguyễn Văn D") {
            @Override
            protected void notifyObservers(String message) {
                super.notifyObservers(message);
                notifications.add("👥 " + message);
            }
        };
        
        task2 = new TaskManager("Thiết kế database", "IN_PROGRESS", "Trần Thị E") {
            @Override
            protected void notifyObservers(String message) {
                super.notifyObservers(message);
                notifications.add("👥 " + message);
            }
        };
        
        // Tạo observers
        investors = new ArrayList<>();
        investors.add(new Investor("Nguyễn Văn A"));
        investors.add(new Investor("Trần Thị B"));
        investors.add(new Investor("Lê Văn C"));
        
        teamMembers = new ArrayList<>();
        teamMembers.add(new TeamMember("Nguyễn Văn D", "Developer"));
        teamMembers.add(new TeamMember("Trần Thị E", "Database Designer"));
        teamMembers.add(new TeamMember("Lê Văn F", "Project Manager"));
        teamMembers.add(new TeamMember("Phạm Thị G", "Tester"));
        
        // Đăng ký observers
        vn30Stock.attach(investors.get(0)); // Nguyễn Văn A
        vn30Stock.attach(investors.get(1)); // Trần Thị B
        
        fptStock.attach(investors.get(1)); // Trần Thị B
        fptStock.attach(investors.get(2)); // Lê Văn C
        
        task1.attach(teamMembers.get(0)); // Developer
        task1.attach(teamMembers.get(2)); // PM
        task1.attach(teamMembers.get(3)); // Tester
        
        task2.attach(teamMembers.get(1)); // Database Designer
        task2.attach(teamMembers.get(2)); // PM
    }
}