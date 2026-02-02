package fit.iuh.demo.strategy;

import fit.iuh.demo.model.Book;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchContext {
    private SearchStrategy strategy;
    
    public void setStrategy(SearchStrategy strategy) {
        this.strategy = strategy;
    }
    
    public List<Book> executeSearch(List<Book> books, String keyword) {
        if (strategy == null) {
            throw new IllegalStateException("Chưa thiết lập chiến lược tìm kiếm");
        }
        return strategy.search(books, keyword);
    }
    
    public String getCurrentStrategyName() {
        return strategy != null ? strategy.getStrategyName() : "Chưa chọn";
    }
}