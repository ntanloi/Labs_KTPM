package fit.iuh.demo.repository;

import fit.iuh.demo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAvailableTrue();
    List<Book> findByAvailableFalse();
    
    @Query("SELECT b FROM Book b WHERE b.available = false AND b.returnDate < :currentDate")
    List<Book> findOverdueBooks(@Param("currentDate") LocalDate currentDate);
    
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByAuthorContainingIgnoreCase(String author);
    List<Book> findByGenreContainingIgnoreCase(String genre);
}