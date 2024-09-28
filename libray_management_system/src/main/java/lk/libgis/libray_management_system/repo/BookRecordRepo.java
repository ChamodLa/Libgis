package lk.libgis.libray_management_system.repo;

import lk.libgis.libray_management_system.entity.Book;
import lk.libgis.libray_management_system.entity.Member;
import lk.libgis.libray_management_system.entity.BookRecord;
import lk.libgis.libray_management_system.util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

/**
 *
 * @author Chamod Abeywickrama
 */

public class BookRecordRepo {

    public boolean addToCart(int bookId, String memberId) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement(
            "INSERT INTO book_member(book_id, member_id) VALUES(?, ?)"
        );
        ps.setInt(1, bookId);
        ps.setString(2, memberId);

        int affectedRows = ps.executeUpdate();
        return affectedRows > 0;
    }

    public boolean confirmTransaction(int bookId, String memberId, LocalDate borrowedDate, LocalDate returnDate) throws ClassNotFoundException, SQLException {
        BookRecord bookRecord = new BookRecord(
            bookId,
            memberId,
            Date.valueOf(borrowedDate),
            false,
            Date.valueOf(returnDate)
        );
        return save(bookRecord);
    }

    public boolean save(BookRecord bookRecord) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement(
            "INSERT INTO book_record (book_id, member_id, borrowed_date, isReturned, returnDate) VALUES (?, ?, ?, ?, ?)"
        );
        ps.setInt(1, bookRecord.getBookId());
        ps.setString(2, bookRecord.getMemberId());
        ps.setDate(3, (Date) bookRecord.getBorrowedDate());
        ps.setBoolean(4, bookRecord.isReturned());
        ps.setDate(5, (Date) bookRecord.getReturnDate());

        int affectedRows = ps.executeUpdate();
        return affectedRows > 0;
    }

    public Optional<Book> searchBookDetails(int bookId) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM book WHERE id = ?");
        ps.setInt(1, bookId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            String isbn = rs.getString(3);
            double price = rs.getDouble(4);
            int authorId = rs.getInt(5);
            int mainCategory = rs.getInt(6);
            Book book = new Book(id, name, isbn, price, authorId, mainCategory);
            return Optional.of(book);
        }
        return Optional.empty();
    }

    public Optional<Member> searchMemberDetails(String memberId) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM member WHERE id = ?");
        ps.setString(1, memberId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String id = rs.getString(1);
            String name = rs.getString(2);
            String address = rs.getString(3);
            String email = rs.getString(4);
            String contact = rs.getString(5);
            Member member = new Member(id, name, address, email, contact);
            return Optional.of(member);
        }
        return Optional.empty();
    }

    public Optional<BookRecord> search(int bookId, String memberId) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement(
            "SELECT * FROM book_record WHERE book_id = ? AND member_id = ?"
        );
        ps.setInt(1, bookId);
        ps.setString(2, memberId);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int bookIdResult = rs.getInt("book_id");
            String memberIdResult = rs.getString("member_id");
            Date borrowedDate = rs.getDate("borrowed_date");
            boolean isReturned = rs.getBoolean("isReturned");
            Date returnDate = rs.getDate("return_date");

            BookRecord bookRecord = new BookRecord(
                bookIdResult,
                memberIdResult,
                borrowedDate,
                isReturned,
                returnDate
            );
            return Optional.of(bookRecord);
        }
        return Optional.empty();
    }
}