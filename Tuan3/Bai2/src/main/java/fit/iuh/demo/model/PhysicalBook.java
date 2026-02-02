package fit.iuh.demo.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("PHYSICAL")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class PhysicalBook extends Book {
    private String isbn;
    private int pageCount;
    
    @Override
    public String getBookType() {
        return "Sách giấy";
    }
    
    @Override
    public String getDisplayInfo() {
        return String.format("%s - %s (%s trang)", getTitle(), getAuthor(), pageCount);
    }
}