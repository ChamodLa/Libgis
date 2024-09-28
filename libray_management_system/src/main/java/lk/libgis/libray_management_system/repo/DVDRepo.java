package lk.libgis.libray_management_system.repo;

import lk.libgis.libray_management_system.entity.DVD;
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
public class DVDRepo {

    public boolean saveDVD(DVD dvd) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement(
            "INSERT INTO dvd(id, name, price, director_id, dvd_category, duration) VALUES(?, ?, ?, ?, ?, ?)"
        );
        ps.setInt(1, dvd.getId());
        ps.setString(2, dvd.getName());
        ps.setDouble(3, dvd.getPrice());
        ps.setInt(4, dvd.getDirectorId());
        ps.setInt(5, dvd.getDvdCategory());
        ps.setInt(6, dvd.getDuration());

        System.out.println("Debug: Prepared Statement for saveDVD: " + ps);
        try {
            int affectedRows = ps.executeUpdate();
            System.out.println("Debug: Affected rows: " + affectedRows);
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Debug: SQL Exception in saveDVD - " + e.getMessage());
            throw e;
        }
    }

    public boolean delete(int id) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("DELETE FROM dvd WHERE id = ?");
        ps.setInt(1, id);
        int affectedRows = ps.executeUpdate();
        return affectedRows > 0;
    }

    public boolean update(DVD dvd) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement(
            "UPDATE dvd SET name = ?, price = ?, director_id = ?, dvd_category = ?, duration = ? WHERE id = ?"
        );

        ps.setString(1, dvd.getName());
        ps.setDouble(2, dvd.getPrice());
        ps.setInt(3, dvd.getDirectorId());
        ps.setInt(4, dvd.getDvdCategory());
        ps.setInt(5, dvd.getDuration());
        ps.setInt(6, dvd.getId());

        int affectedRows = ps.executeUpdate();

        return affectedRows > 0;
    }

    public Optional<DVD> searchDVD(int dvdId) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM dvd WHERE id = ?");
        ps.setInt(1, dvdId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            double price = rs.getDouble(3);
            int directorId = rs.getInt(4);
            int dvdCategory = rs.getInt(5);
            int duration = rs.getInt(6);
            DVD dvd = new DVD(id, name, price, directorId, dvdCategory, duration);
            return Optional.of(dvd);
        }
        return Optional.empty();
    }

    public List<DVD> getAll() throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM dvd");
        ResultSet rs = ps.executeQuery();
        List<DVD> list = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            double price = rs.getDouble(3);
            int directorId = rs.getInt(4);
            int dvdCategory = rs.getInt(5);
            int duration = rs.getInt(6);
            DVD dvd = new DVD(id, name, price, directorId, dvdCategory, duration);
            list.add(dvd);
        }
        return list;
    }
}
