/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.libgis.libray_management_system.repo;

import lk.libgis.libray_management_system.entity.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lk.libgis.libray_management_system.util.DBConnection;

/**
 *
 * @author Chamod Abeywickrama
 */
public class BookRepo {

    public boolean saveBook(Book book) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement(
            "INSERT INTO book(id, name, isbn, price, author_id, main_category) VALUES(?, ?, ?, ?, ?, ?)"
        );
        ps.setInt(1, book.getId());
        ps.setString(2, book.getName());
        ps.setString(3, book.getIsbn());
        ps.setDouble(4, book.getPrice());
        ps.setInt(5, book.getAuthorId());
        ps.setInt(6, book.getMainCategory());

        System.out.println("Debug: Prepared Statement for saveBook: " + ps);
        try {
            int affectedRows = ps.executeUpdate();
            System.out.println("Debug: Affected rows: " + affectedRows);
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Debug: SQL Exception in saveBook - " + e.getMessage());
            throw e;
        }
    }



    public boolean delete(int id) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("DELETE FROM book WHERE id = ?");
        ps.setInt(1, id);
        int affectedRows = ps.executeUpdate();
        return affectedRows > 0;
    }

    public boolean update(Book book) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement(
            "UPDATE book SET name = ?, isbn = ?, price = ?, author_id = ?, main_category = ? WHERE id = ?"
        );

        ps.setString(1, book.getName());
        ps.setString(2, book.getIsbn());
        ps.setDouble(3, book.getPrice());
        ps.setInt(4, book.getAuthorId());
        ps.setInt(5, book.getMainCategory());
        ps.setInt(6, book.getId());

        int affectedRows = ps.executeUpdate();

        return affectedRows > 0;
    }

    public Optional<Book> searchBook(int bookId) throws ClassNotFoundException, SQLException {
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

    public List<Book> getAll() throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM book");
        ResultSet rs = ps.executeQuery();
        List<Book> list = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            String isbn = rs.getString(3);
            double price = rs.getDouble(4);
            int authorId = rs.getInt(5);
            int mainCategory = rs.getInt(6);
            Book book = new Book(id, name, isbn, price, authorId, mainCategory);
            list.add(book);
        }
        return list;
    }
}
