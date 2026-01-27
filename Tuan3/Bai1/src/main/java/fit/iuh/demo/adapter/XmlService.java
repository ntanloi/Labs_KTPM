package fit.iuh.demo.adapter;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * Adaptee class - Service xử lý XML
 * Có interface khác với DataProcessor
 */
public class XmlService {
    private XmlMapper xmlMapper = new XmlMapper();
    
    public String processXml(String xmlData) {
        try {
            // Validate và format XML
            Object xmlObject = xmlMapper.readValue(xmlData, Object.class);
            String formattedXml = xmlMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(xmlObject);
            
            System.out.println("✅ XML được xử lý thành công");
            return formattedXml;
        } catch (Exception e) {
            System.err.println("❌ Lỗi xử lý XML: " + e.getMessage());
            return null;
        }
    }
    
    public boolean isValidXml(String xmlData) {
        try {
            xmlMapper.readValue(xmlData, Object.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}