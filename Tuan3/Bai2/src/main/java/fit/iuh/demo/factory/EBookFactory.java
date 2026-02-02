package fit.iuh.demo.factory;

import fit.iuh.demo.model.Book;
import fit.iuh.demo.model.EBook;
import org.springframework.stereotype.Component;

@Component
public class EBookFactory extends BookFactory {
    @Override
    public Book createBook() {
        return new EBook();
    }
}