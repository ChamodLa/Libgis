package tm;

import lombok.Data;

/**
 * 
 * @author Chamod Abeywickrama
 */
@Data
public class DVDTM {
    private int id;
    private String name;
    private double price;
    private int duration;
    private int directorId;
    private int categoryId;
    private String directorName;
}
