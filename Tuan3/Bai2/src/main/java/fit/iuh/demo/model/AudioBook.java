package fit.iuh.demo.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("AUDIOBOOK")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class AudioBook extends Book {
    private int durationMinutes;
    private String narrator;
    
    @Override
    public String getBookType() {
        return "Sách nói";
    }
    
    @Override
    public String getDisplayInfo() {
        return String.format("%s - %s (Thời lượng: %d phút, Người đọc: %s)", 
                getTitle(), getAuthor(), durationMinutes, narrator);
    }
}