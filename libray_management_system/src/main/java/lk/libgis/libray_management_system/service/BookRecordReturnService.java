package lk.libgis.libray_management_system.service;

import lk.libgis.libray_management_system.dto.BookRecordReturnDTO;
import lk.libgis.libray_management_system.entity.BookRecordReturn;
import lk.libgis.libray_management_system.repo.BookRecordReturnRepo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookRecordReturnService {

    private final BookRecordReturnRepo repo;

    public BookRecordReturnService(Connection connection) {
        this.repo = new BookRecordReturnRepo(connection);
    }

    public List<BookRecordReturnDTO> getAll() throws SQLException {
        List<BookRecordReturn> records = repo.findAll();
        return records.stream()
                      .map(this::convertToDTO)
                      .collect(Collectors.toList());
    }

    public boolean markAsReturned(String id, String returnDate) {
        try {
            return repo.markAsReturned(id, returnDate);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Optional<BookRecordReturnDTO> search(String id) {
        try {
            Optional<BookRecordReturn> record = repo.findById(id);
            return record.map(this::convertToDTO);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private BookRecordReturnDTO convertToDTO(BookRecordReturn entity) {
        return new BookRecordReturnDTO(
            entity.getId(),
            entity.getBookId(),
            entity.getMemberId(),
            entity.getBorrowedDate(),
            entity.isReturned(),
            entity.getReturnDate(),
            entity.getBookName(),
            entity.getMemberName()
        );
    }
    
    public boolean delete(String id) {
        try {
            return repo.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
