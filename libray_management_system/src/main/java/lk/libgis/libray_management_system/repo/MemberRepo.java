/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.libgis.libray_management_system.repo;

import lk.libgis.libray_management_system.entity.Member;
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
public class MemberRepo {
    
    public boolean saveMember(Member member) throws ClassNotFoundException, SQLException {
        
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement(
            "INSERT INTO member(id, name, address, email, contact) VALUES(?, ?, ?, ?, ?)"
        );
        ps.setString(1, member.getId());
        ps.setString(2, member.getName());
        ps.setString(3, member.getAddress());
        ps.setString(4, member.getEmail());
        ps.setString(5, member.getContact());
        
        int affectedRows = ps.executeUpdate();
        
        return affectedRows > 0;
    }
    
    public boolean delete(String id) throws ClassNotFoundException, SQLException{
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("DELETE FROM member WHERE id = ?");
        ps.setString(1, id);
        int affectedRows = ps.executeUpdate();
        return affectedRows>0;
    } 
    


    public boolean update(Member member) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("UPDATE member SET name = ?, address = ?, email = ?, contact = ? WHERE id = ?");

        ps.setString(1, member.getName());
        ps.setString(2, member.getAddress());
        ps.setString(3, member.getEmail());
        ps.setString(4, member.getContact());
        ps.setString(5, member.getId()); 

        int affectedRows = ps.executeUpdate();

        return affectedRows > 0;
    }
    
    public Optional<Member> searchCustomer(String customerId) throws ClassNotFoundException, SQLException{
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT * From member WHERE id= ?");
        ps.setString(1,customerId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            String id = rs.getString(1);
            String name = rs.getString(2);
            String address = rs.getString(3);
            String email = rs.getString(4);
            String contact = rs.getString(5);
            Member ob = new Member(id,name,address,email,contact);
            return Optional.of(ob);
        }
        return Optional.empty();
    }
    
    public List<Member> getAll() throws ClassNotFoundException, SQLException{
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM member");
        ResultSet rs = ps.executeQuery();
        List<Member> list = new ArrayList<>();
        while(rs.next()){
            String id = rs.getString(1);
            String name = rs.getString(2);
            String address = rs.getString(3);
            String email = rs.getString(4);
            String contact = rs.getString(5);
            Member member = new Member(id,name,address,email,contact);
            list.add(member);
        }
        return list;
    }
    
}

