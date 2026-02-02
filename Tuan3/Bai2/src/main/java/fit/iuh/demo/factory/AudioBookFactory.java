package fit.iuh.demo.factory;

import fit.iuh.demo.model.Book;
import fit.iuh.demo.model.AudioBook;
import org.springframework.stereotype.Component;

@Component
public class AudioBookFactory extends BookFactory {
    @Override
    public Book createBook() {
        return new AudioBook();
    }
}