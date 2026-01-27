package fit.iuh.demo.controller;

import fit.iuh.demo.adapter.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controller cho Adapter Pattern demo
 */
@Controller
@RequestMapping("/adapter")
public class AdapterController {
    
    private final JsonService jsonService;
    private final XmlService xmlService;
    private final XmlToJsonAdapter xmlToJsonAdapter;
    private final JsonToXmlAdapter jsonToXmlAdapter;
    
    public AdapterController() {
        this.jsonService = new JsonService();
        this.xmlService = new XmlService();
        this.xmlToJsonAdapter = new XmlToJsonAdapter(xmlService);
        this.jsonToXmlAdapter = new JsonToXmlAdapter(jsonService);
    }
    
    @GetMapping
    public String adapterDemo(Model model) {
        model.addAttribute("title", "Adapter Pattern - Chuyển đổi XML/JSON");
        
        // Sample data
        String sampleJson = "{\n  \"user\": {\n    \"id\": 1,\n    \"name\": \"Nguyễn Văn A\",\n    \"email\": \"nguyenvana@example.com\"\n  }\n}";
        String sampleXml = "<user>\n  <id>2</id>\n  <name>Trần Thị B</name>\n  <email>tranthib@example.com</email>\n</user>";
        
        model.addAttribute("sampleJson", sampleJson);
        model.addAttribute("sampleXml", sampleXml);
        
        return "adapter";
    }
    
    @PostMapping("/process-json")
    public String processJson(@RequestParam String jsonData, Model model) {
        try {
            String result = jsonService.processData(jsonData);
            model.addAttribute("jsonResult", result);
            model.addAttribute("jsonSuccess", true);
        } catch (Exception e) {
            model.addAttribute("jsonError", "Lỗi xử lý JSON: " + e.getMessage());
        }
        
        addCommonAttributes(model);
        return "adapter";
    }
    
    @PostMapping("/convert-json-to-xml")
    public String convertJsonToXml(@RequestParam String jsonData, Model model) {
        try {
            String result = jsonToXmlAdapter.processData(jsonData);
            model.addAttribute("xmlResult", result);
            model.addAttribute("conversionSuccess", true);
            model.addAttribute("conversionType", "JSON → XML");
        } catch (Exception e) {
            model.addAttribute("conversionError", "Lỗi chuyển đổi JSON sang XML: " + e.getMessage());
        }
        
        addCommonAttributes(model);
        return "adapter";
    }
    
    @PostMapping("/convert-xml-to-json")
    public String convertXmlToJson(@RequestParam String xmlData, Model model) {
        try {
            String result = xmlToJsonAdapter.processData(xmlData);
            model.addAttribute("jsonResult", result);
            model.addAttribute("conversionSuccess", true);
            model.addAttribute("conversionType", "XML → JSON");
        } catch (Exception e) {
            model.addAttribute("conversionError", "Lỗi chuyển đổi XML sang JSON: " + e.getMessage());
        }
        
        addCommonAttributes(model);
        return "adapter";
    }
    
    @PostMapping("/process-xml")
    public String processXml(@RequestParam String xmlData, Model model) {
        try {
            String result = xmlService.processXml(xmlData);
            model.addAttribute("xmlResult", result);
            model.addAttribute("xmlSuccess", true);
        } catch (Exception e) {
            model.addAttribute("xmlError", "Lỗi xử lý XML: " + e.getMessage());
        }
        
        addCommonAttributes(model);
        return "adapter";
    }
    
    private void addCommonAttributes(Model model) {
        model.addAttribute("title", "Adapter Pattern - Chuyển đổi XML/JSON");
        
        String sampleJson = "{\n  \"user\": {\n    \"id\": 1,\n    \"name\": \"Nguyễn Văn A\",\n    \"email\": \"nguyenvana@example.com\"\n  }\n}";
        String sampleXml = "<user>\n  <id>2</id>\n  <name>Trần Thị B</name>\n  <email>tranthib@example.com</email>\n</user>";
        
        model.addAttribute("sampleJson", sampleJson);
        model.addAttribute("sampleXml", sampleXml);
    }
}