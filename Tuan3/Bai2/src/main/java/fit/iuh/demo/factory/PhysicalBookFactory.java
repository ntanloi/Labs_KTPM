package fit.iuh.demo.factory;

import fit.iuh.demo.model.Book;
import fit.iuh.demo.model.PhysicalBook;
import org.springframework.stereotype.Component;

@Component
public class PhysicalBookFactory extends BookFactory {
    @Override
    public Book createBook() {
        return new PhysicalBook();
    }
}