package tm;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Chamod Abeywickrama
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookRecordTM {
    private int bookId;
    private String bookName;
    private String memberName;
    private LocalDate borrowedDate;
    private LocalDate returnDate;
}

