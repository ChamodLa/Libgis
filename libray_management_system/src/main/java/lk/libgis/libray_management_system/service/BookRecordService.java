package lk.libgis.libray_management_system.service;

import lk.libgis.libray_management_system.dto.BookRecordDTO;
import lk.libgis.libray_management_system.entity.BookRecord;
import lk.libgis.libray_management_system.repo.BookRecordRepo;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Chamod Abeywickrama
 */

public class BookRecordService {

    private final BookRecordRepo bookRecordRepo = new BookRecordRepo();

    public boolean addToCart(int bookId, String memberId) {
        try {
            return bookRecordRepo.addToCart(bookId, memberId);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean confirmTransaction(int bookId, String memberId, LocalDate borrowedDate, LocalDate returnDate) {
        try {
            return bookRecordRepo.confirmTransaction(bookId, memberId, borrowedDate, returnDate);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Optional<BookRecordDTO> search(int bookId, String memberId) {
        try {
            Optional<BookRecord> bookRecord = bookRecordRepo.search(bookId, memberId);
            if (bookRecord.isPresent()) {
                BookRecordDTO bookRecordDTO = convertEntityToDTO(bookRecord.get());
                return Optional.of(bookRecordDTO);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private BookRecordDTO convertEntityToDTO(BookRecord bookRecord) {
        return new BookRecordDTO(
                bookRecord.getBookId(),
                bookRecord.getMemberId(),
                bookRecord.getBorrowedDate(),
                bookRecord.isReturned(),
                bookRecord.getReturnDate()
        );
    }

    public boolean saveAll(List<BookRecordDTO> recordDTOList) {
        boolean allSaved = true;
        for (BookRecordDTO recordDTO : recordDTOList) {
            try {
                BookRecord bookRecord = new BookRecord(
                    recordDTO.getBookId(),
                    recordDTO.getMemberId(),
                    recordDTO.getBorrowedDate(),
                    recordDTO.isReturned(),
                    recordDTO.getReturnDate()
                );

                if (!bookRecordRepo.save(bookRecord)) {
                    allSaved = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                allSaved = false;
            }
        }
        return allSaved;
    }

}