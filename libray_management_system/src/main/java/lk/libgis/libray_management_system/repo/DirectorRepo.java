/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.libgis.libray_management_system.repo;

import lk.libgis.libray_management_system.entity.Director;
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

public class DirectorRepo {

    public boolean saveDirector(Director director) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement(
            "INSERT INTO director(id, name) VALUES(?, ?)"
        );
        ps.setInt(1, director.getId());
        ps.setString(2, director.getName());

        int affectedRows = ps.executeUpdate();

        return affectedRows > 0;
    }

    public boolean delete(String id) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("DELETE FROM director WHERE id = ?");
        ps.setInt(1, Integer.parseInt(id));
        int affectedRows = ps.executeUpdate();
        return affectedRows > 0;
    }

    public boolean update(Director director) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement(
            "UPDATE director SET name = ? WHERE id = ?"
        );
        ps.setString(1, director.getName());
        ps.setInt(2, director.getId());

        int affectedRows = ps.executeUpdate();

        return affectedRows > 0;
    }

    public Optional<Director> searchDirector(String directorId) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM director WHERE id = ?");
        ps.setInt(1, Integer.parseInt(directorId));
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            Director director = new Director(id, name);
            return Optional.of(director);
        }
        return Optional.empty();
    }

    public List<Director> getAll() throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM director");
        ResultSet rs = ps.executeQuery();
        List<Director> list = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            Director director = new Director(id, name);
            list.add(director);
        }
        return list;
    }
}
