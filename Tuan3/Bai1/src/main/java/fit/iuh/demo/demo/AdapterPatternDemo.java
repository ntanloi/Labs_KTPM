package fit.iuh.demo.demo;

import fit.iuh.demo.adapter.*;

/**
 * Demo class cho Adapter Pattern
 * Minh họa chuyển đổi dữ liệu giữa XML và JSON
 */
public class AdapterPatternDemo {
    
    public static void runDemo() {
        System.out.println("=== ADAPTER PATTERN DEMO ===");
        System.out.println("Chuyển đổi dữ liệu giữa XML và JSON\n");
        
        // Tạo các service
        JsonService jsonService = new JsonService();
        XmlService xmlService = new XmlService();
        
        // Tạo các adapter
        XmlToJsonAdapter xmlToJsonAdapter = new XmlToJsonAdapter(xmlService);
        JsonToXmlAdapter jsonToXmlAdapter = new JsonToXmlAdapter(jsonService);
        
        // Demo data
        String jsonData = """
            {
                "user": {
                    "id": 1,
                    "name": "Nguyễn Văn A",
                    "email": "nguyenvana@example.com",
                    "address": {
                        "street": "123 Đường ABC",
                        "city": "TP.HCM",
                        "country": "Vietnam"
                    },
                    "hobbies": ["đọc sách", "du lịch", "âm nhạc"]
                }
            }
            """;
        
        String xmlData = """
            <user>
                <id>2</id>
                <name>Trần Thị B</name>
                <email>tranthib@example.com</email>
                <address>
                    <street>456 Đường XYZ</street>
                    <city>Hà Nội</city>
                    <country>Vietnam</country>
                </address>
                <hobbies>
                    <hobby>nấu ăn</hobby>
                    <hobby>thể thao</hobby>
                    <hobby>phim ảnh</hobby>
                </hobbies>
            </user>
            """;
        
        // Demo 1: Xử lý JSON trực tiếp
        System.out.println("--- Demo 1: Xử lý JSON trực tiếp ---");
        System.out.println("Input JSON:");
        System.out.println(jsonData);
        
        String processedJson = jsonService.processData(jsonData);
        System.out.println("\nOutput JSON đã được format:");
        System.out.println(processedJson);
        
        // Demo 2: Chuyển đổi JSON sang XML
        System.out.println("\n--- Demo 2: Chuyển đổi JSON sang XML ---");
        System.out.println("Input JSON:");
        System.out.println(jsonData);
        
        String convertedXml = jsonToXmlAdapter.processData(jsonData);
        System.out.println("\nOutput XML:");
        System.out.println(convertedXml);
        
        // Demo 3: Chuyển đổi XML sang JSON
        System.out.println("\n--- Demo 3: Chuyển đổi XML sang JSON ---");
        System.out.println("Input XML:");
        System.out.println(xmlData);
        
        String convertedJson = xmlToJsonAdapter.processData(xmlData);
        System.out.println("\nOutput JSON:");
        System.out.println(convertedJson);
        
        // Demo 4: Xử lý dữ liệu không hợp lệ
        System.out.println("\n--- Demo 4: Xử lý dữ liệu không hợp lệ ---");
        
        String invalidJson = "{ invalid json data }";
        String invalidXml = "<invalid><xml>data</invalid>";
        
        System.out.println("Thử xử lý JSON không hợp lệ:");
        jsonService.processData(invalidJson);
        
        System.out.println("\nThử chuyển đổi XML không hợp lệ sang JSON:");
        xmlToJsonAdapter.processData(invalidXml);
        
        // Demo 5: Sử dụng thống nhất qua DataProcessor interface
        System.out.println("\n--- Demo 5: Sử dụng thống nhất qua interface ---");
        
        DataProcessor[] processors = {
            jsonService,
            xmlToJsonAdapter,
            jsonToXmlAdapter
        };
        
        System.out.println("Xử lý cùng một JSON data với các processor khác nhau:");
        String simpleJson = "{\"message\": \"Hello World\", \"timestamp\": \"2024-01-26\"}";
        
        for (int i = 0; i < processors.length; i++) {
            System.out.println("\nProcessor " + (i + 1) + ":");
            String result = processors[i].processData(simpleJson);
            if (result != null) {
                System.out.println("Kết quả:\n" + result);
            }
        }
        
        System.out.println("\n=== KẾT THÚC ADAPTER PATTERN DEMO ===\n");
    }
}