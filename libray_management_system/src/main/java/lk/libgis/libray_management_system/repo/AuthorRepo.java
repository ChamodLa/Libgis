/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.libgis.libray_management_system.repo;

import lk.libgis.libray_management_system.entity.Author;
import lk.libgis.libray_management_system.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Chamod Abeywickrama
 */
public class AuthorRepo {

    public boolean saveAuthor(Author author) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement(
            "INSERT INTO author(id, name) VALUES(?, ?)"
        );
        ps.setInt(1, author.getId());
        ps.setString(2, author.getName());

        int affectedRows = ps.executeUpdate();

        return affectedRows > 0;
    }

    public boolean delete(String id) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("DELETE FROM author WHERE id = ?");
        ps.setInt(1, Integer.parseInt(id));
        int affectedRows = ps.executeUpdate();
        return affectedRows > 0;
    }

    public boolean update(Author author) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement(
            "UPDATE author SET name = ? WHERE id = ?"
        );
        ps.setString(1, author.getName());
        ps.setInt(2, author.getId());

        int affectedRows = ps.executeUpdate();

        return affectedRows > 0;
    }

    public Optional<Author> searchAuthor(String authorName) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM author WHERE name = ?");
        ps.setString(1, authorName);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            Author author = new Author(id, name);
            return Optional.of(author);
        }
        return Optional.empty();
    }


    public List<Author> getAll() throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM author");
        ResultSet rs = ps.executeQuery();
        List<Author> list = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            Author author = new Author(id, name);
            list.add(author);
        }
        return list;
    }
}
