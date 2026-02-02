package fit.iuh.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "book_type")
public abstract class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private String author;
    
    @Column(nullable = false)
    private String genre;
    
    @Column(name = "publication_date")
    private LocalDate publicationDate;
    
    @Column(name = "is_available")
    private boolean available = true;
    
    @Column(name = "borrowed_date")
    private LocalDate borrowedDate;
    
    @Column(name = "return_date")
    private LocalDate returnDate;
    
    public abstract String getBookType();
    public abstract String getDisplayInfo();
}