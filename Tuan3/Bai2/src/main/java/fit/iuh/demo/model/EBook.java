package fit.iuh.demo.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("EBOOK")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class EBook extends Book {
    private String fileFormat;
    private double fileSizeMB;
    
    @Override
    public String getBookType() {
        return "Sách điện tử";
    }
    
    @Override
    public String getDisplayInfo() {
        return String.format("%s - %s (%s, %.1f MB)", getTitle(), getAuthor(), fileFormat, fileSizeMB);
    }
}