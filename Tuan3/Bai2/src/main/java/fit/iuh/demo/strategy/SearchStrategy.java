package fit.iuh.demo.strategy;

import fit.iuh.demo.model.Book;
import java.util.List;

public interface SearchStrategy {
    List<Book> search(List<Book> books, String keyword);
    String getStrategyName();
}