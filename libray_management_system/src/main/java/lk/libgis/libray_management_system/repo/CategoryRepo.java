package lk.libgis.libray_management_system.repo;

import lk.libgis.libray_management_system.entity.Category;
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

public class CategoryRepo {

    public boolean saveCategory(Category category) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement(
            "INSERT INTO category(id, name) VALUES(?, ?)"
        );
        ps.setInt(1, category.getId());
        ps.setString(2, category.getName());

        int affectedRows = ps.executeUpdate();
        return affectedRows > 0;
    }

    public boolean delete(int id) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("DELETE FROM category WHERE id = ?");
        ps.setInt(1, id);
        int affectedRows = ps.executeUpdate();
        return affectedRows > 0;
    }

    public boolean update(Category category) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement(
            "UPDATE category SET name = ? WHERE id = ?"
        );
        ps.setString(1, category.getName());
        ps.setInt(2, category.getId());

        int affectedRows = ps.executeUpdate();
        return affectedRows > 0;
    }

    public Optional<Category> searchCategory(int categoryId) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM category WHERE id = ?");
        ps.setInt(1, categoryId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            Category category = new Category(id, name);
            return Optional.of(category);
        }
        return Optional.empty();
    }

    public List<Category> getAll() throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM category");
        ResultSet rs = ps.executeQuery();
        List<Category> list = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            Category category = new Category(id, name);
            list.add(category);
        }
        return list;
    }
}
