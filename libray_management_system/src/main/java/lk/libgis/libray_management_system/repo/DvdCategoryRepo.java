/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.libgis.libray_management_system.repo;

import lk.libgis.libray_management_system.entity.DvdCategory;
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
public class DvdCategoryRepo {

    public boolean saveDvdCategory(DvdCategory dvdCategory) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement(
            "INSERT INTO dvdcategory(id, name) VALUES(?, ?)"
        );
        ps.setInt(1, dvdCategory.getId());
        ps.setString(2, dvdCategory.getName());

        int affectedRows = ps.executeUpdate();
        return affectedRows > 0;
    }

    public boolean delete(int id) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("DELETE FROM dvdcategory WHERE id = ?");
        ps.setInt(1, id);
        int affectedRows = ps.executeUpdate();
        return affectedRows > 0;
    }

    public boolean update(DvdCategory dvdCategory) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement(
            "UPDATE dvdcategory SET name = ? WHERE id = ?"
        );
        ps.setString(1, dvdCategory.getName());
        ps.setInt(2, dvdCategory.getId());

        int affectedRows = ps.executeUpdate();
        return affectedRows > 0;
    }

    public Optional<DvdCategory> searchDvdCategory(int dvdCategoryId) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM dvdcategory WHERE id = ?");
        ps.setInt(1, dvdCategoryId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            DvdCategory dvdCategory = new DvdCategory(id, name);
            return Optional.of(dvdCategory);
        }
        return Optional.empty();
    }

    public List<DvdCategory> getAll() throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM dvdcategory");
        ResultSet rs = ps.executeQuery();
        List<DvdCategory> list = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            DvdCategory dvdCategory = new DvdCategory(id, name);
            list.add(dvdCategory);
        }
        return list;
    }
}
