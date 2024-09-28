package tm;

import lombok.Data;

/**
 * 
 * @author Chamod Abeywickrama
 */
@Data
public class BookTM {
    private int id; 
    private String name; 
    private String isbn; 
    private double price; 
    private int AuthorId; 
    private int mainCategory;
}
