package fit.iuh.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Bai1Application {

	public static void main(String[] args) {
		System.out.println("🚀 KHỞI ĐỘNG WEB APPLICATION - DESIGN PATTERNS DEMO");
		System.out.println("==========================================");
		System.out.println("🌐 Spring Boot sẽ tự động chọn port available");
		System.out.println("🔔 Observer Pattern: /observer");
		System.out.println("🔄 Adapter Pattern: /adapter");
		System.out.println("📋 Xem console log để biết port được sử dụng");
		System.out.println("📋 Tìm dòng: 'Tomcat started on port XXXX'");
		System.out.println("==========================================");
		
		// Khởi động Spring Boot application
		SpringApplication.run(Bai1Application.class, args);
	}

}
