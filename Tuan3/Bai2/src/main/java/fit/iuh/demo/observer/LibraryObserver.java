package fit.iuh.demo.observer;

import fit.iuh.demo.model.Book;

public interface LibraryObserver {
    void onBookAdded(Book book);
    void onBookBorrowed(Book book);
    void onBookReturned(Book book);
    void onBookOverdue(Book book);
    String getObserverName();
}