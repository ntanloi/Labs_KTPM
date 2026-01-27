package fit.iuh.demo.adapter;

/**
 * Target interface cho Adapter Pattern
 * Định nghĩa interface mà client mong muốn sử dụng
 */
public interface DataProcessor {
    String processData(String data);
}