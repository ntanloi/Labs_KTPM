package fit.iuh.demo.adapter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Service xử lý JSON - implement DataProcessor interface
 */
public class JsonService implements DataProcessor {
    private ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public String processData(String jsonData) {
        try {
            // Validate và format JSON
            JsonNode jsonNode = objectMapper.readTree(jsonData);
            String formattedJson = objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(jsonNode);
            
            System.out.println("✅ JSON được xử lý thành công");
            return formattedJson;
        } catch (Exception e) {
            System.err.println("❌ Lỗi xử lý JSON: " + e.getMessage());
            return null;
        }
    }
    
    public boolean isValidJson(String jsonData) {
        try {
            objectMapper.readTree(jsonData);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}