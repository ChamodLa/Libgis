package lk.libgis.libray_management_system.repo;

import lk.libgis.libray_management_system.entity.BookRecordReturn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRecordReturnRepo {

    private final Connection connection;

    public BookRecordReturnRepo(Connection connection) {
        this.connection = connection;
    }

    public List<BookRecordReturn> findAll() throws SQLException {
        List<BookRecordReturn> list = new ArrayList<>();
        String sql = "SELECT br.id, br.book_id, br.member_id, br.borrowed_date, br.isReturned, br.returnDate, "
                   + "b.name AS bookName, m.name AS memberName "
                   + "FROM book_record br "
                   + "JOIN book b ON br.book_id = b.id "
                   + "JOIN member m ON br.member_id = m.id";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                BookRecordReturn record = new BookRecordReturn(
                    rs.getString("id"),
                    rs.getString("book_id"),
                    rs.getString("member_id"),
                    rs.getString("borrowed_date"),
                    rs.getBoolean("isReturned"),
                    rs.getString("returnDate"),
                    rs.getString("bookName"),
                    rs.getString("memberName")
                );
                list.add(record);
            }
        }
        return list;
    }

    public boolean markAsReturned(String id, String returnDate) throws SQLException {
        String sql = "UPDATE book_record SET isReturned = TRUE, returnDate = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, returnDate);
            stmt.setString(2, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public Optional<BookRecordReturn> findById(String id) throws SQLException {
        String sql = "SELECT br.id, br.book_id, br.member_id, br.borrowed_date, br.isReturned, br.returnDate, "
                   + "b.name AS bookName, m.name AS memberName "
                   + "FROM book_record br "
                   + "JOIN book b ON br.book_id = b.id "
                   + "JOIN member m ON br.member_id = m.id "
                   + "WHERE br.id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    BookRecordReturn record = new BookRecordReturn(
                        rs.getString("id"),
                        rs.getString("book_id"),
                        rs.getString("member_id"),
                        rs.getString("borrowed_date"),
                        rs.getBoolean("isReturned"),
                        rs.getString("returnDate"),
                        rs.getString("bookName"),
                        rs.getString("memberName")
                    );
                    return Optional.of(record);
                }
            }
        }
        return Optional.empty();
    }
    
    public boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM book_record WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }
}
