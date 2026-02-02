package fit.iuh.demo.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookFactoryProvider {
    
    @Autowired
    private PhysicalBookFactory physicalBookFactory;
    
    @Autowired
    private EBookFactory eBookFactory;
    
    @Autowired
    private AudioBookFactory audioBookFactory;
    
    public BookFactory getFactory(String bookType) {
        switch (bookType.toUpperCase()) {
            case "PHYSICAL":
                return physicalBookFactory;
            case "EBOOK":
                return eBookFactory;
            case "AUDIOBOOK":
                return audioBookFactory;
            default:
                throw new IllegalArgumentException("Loại sách không hợp lệ: " + bookType);
        }
    }
}