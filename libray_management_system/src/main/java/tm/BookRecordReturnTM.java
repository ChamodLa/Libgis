/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRecordReturnTM {
    private String recordId;
    private String bookId;
    private String bookName;
    private String memberId;
    private String memberName;
    private String borrowedDate;
    private boolean isReturned;
    private String returnDate;

}
