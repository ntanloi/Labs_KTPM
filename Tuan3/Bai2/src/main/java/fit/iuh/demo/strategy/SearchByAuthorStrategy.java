package fit.iuh.demo.strategy;

import fit.iuh.demo.model.Book;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SearchByAuthorStrategy implements SearchStrategy {
    @Override
    public List<Book> search(List<Book> books, String keyword) {
        return books.stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    @Override
    public String getStrategyName() {
        return "Tìm theo tác giả";
    }
}