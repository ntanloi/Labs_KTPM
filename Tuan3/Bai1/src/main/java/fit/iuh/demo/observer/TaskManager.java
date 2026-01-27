package fit.iuh.demo.observer;

/**
 * Concrete Subject - Quản lý công việc
 * Khi trạng thái task thay đổi sẽ thông báo cho team members
 */
public class TaskManager extends Subject {
    private String taskName;
    private String status;
    private String assignee;
    
    public TaskManager(String taskName, String status, String assignee) {
        this.taskName = taskName;
        this.status = status;
        this.assignee = assignee;
    }
    
    public String getTaskName() {
        return taskName;
    }
    
    public String getStatus() {
        return status;
    }
    
    public String getAssignee() {
        return assignee;
    }
    
    public void setStatus(String newStatus) {
        String oldStatus = this.status;
        this.status = newStatus;
        
        String message = String.format("Task '%s' (Assignee: %s): Trạng thái thay đổi từ '%s' -> '%s'", 
            taskName, assignee, oldStatus, newStatus);
        
        notifyObservers(message);
    }
    
    public void setAssignee(String newAssignee) {
        String oldAssignee = this.assignee;
        this.assignee = newAssignee;
        
        String message = String.format("Task '%s': Người được giao thay đổi từ '%s' -> '%s'", 
            taskName, oldAssignee, newAssignee);
        
        notifyObservers(message);
    }
}