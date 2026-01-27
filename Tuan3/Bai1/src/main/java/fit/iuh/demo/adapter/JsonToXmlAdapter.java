package fit.iuh.demo.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * Adapter class - Chuyển đổi JSON sang XML
 * Implement DataProcessor interface
 */
public class JsonToXmlAdapter implements DataProcessor {
    private JsonService jsonService;
    private ObjectMapper jsonMapper = new ObjectMapper();
    private XmlMapper xmlMapper = new XmlMapper();
    
    public JsonToXmlAdapter(JsonService jsonService) {
        this.jsonService = jsonService;
    }
    
    @Override
    public String processData(String jsonData) {
        try {
            System.out.println("🔄 Đang chuyển đổi JSON sang XML...");
            
            // Sử dụng JsonService để validate JSON
            if (!jsonService.isValidJson(jsonData)) {
                System.err.println("❌ JSON không hợp lệ");
                return null;
            }
            
            // Chuyển đổi JSON sang Object
            Object jsonObject = jsonMapper.readValue(jsonData, Object.class);
            
            // Chuyển đổi Object sang XML
            String xmlResult = xmlMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(jsonObject);
            
            System.out.println("✅ Chuyển đổi JSON sang XML thành công");
            return xmlResult;
            
        } catch (Exception e) {
            System.err.println("❌ Lỗi chuyển đổi JSON sang XML: " + e.getMessage());
            return null;
        }
    }
}