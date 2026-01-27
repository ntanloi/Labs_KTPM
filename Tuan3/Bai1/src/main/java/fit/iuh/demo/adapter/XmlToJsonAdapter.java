package fit.iuh.demo.adapter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * Adapter class - Chuyển đổi XML sang JSON
 * Implement DataProcessor interface và sử dụng XmlService
 */
public class XmlToJsonAdapter implements DataProcessor {
    private XmlService xmlService;
    private XmlMapper xmlMapper = new XmlMapper();
    private ObjectMapper jsonMapper = new ObjectMapper();
    
    public XmlToJsonAdapter(XmlService xmlService) {
        this.xmlService = xmlService;
    }
    
    @Override
    public String processData(String xmlData) {
        try {
            System.out.println("🔄 Đang chuyển đổi XML sang JSON...");
            
            // Sử dụng XmlService để validate XML
            if (!xmlService.isValidXml(xmlData)) {
                System.err.println("❌ XML không hợp lệ");
                return null;
            }
            
            // Chuyển đổi XML sang Object
            Object xmlObject = xmlMapper.readValue(xmlData, Object.class);
            
            // Chuyển đổi Object sang JSON
            String jsonResult = jsonMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(xmlObject);
            
            System.out.println("✅ Chuyển đổi XML sang JSON thành công");
            return jsonResult;
            
        } catch (Exception e) {
            System.err.println("❌ Lỗi chuyển đổi XML sang JSON: " + e.getMessage());
            return null;
        }
    }
}