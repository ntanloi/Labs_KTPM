package fit.iuh.demo.factory;

import fit.iuh.demo.model.Book;

public abstract class BookFactory {
    public abstract Book createBook();
    
    public Book orderBook() {
        Book book = createBook();
        return book;
    }
}