/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.SplitSmart.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sahilnaik
 */
public class DatabaseOperations {
    public static String fetchUserCode(String username) {
        String code = null;
        String query = "SELECT code FROM users WHERE username = ?"; // Adjust the query according to your database schema
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
             
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                code = rs.getString("code");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return code; // Return the fetched code or null if not found
    }
    
    public static String fetchGroupCode(String username) {
        String code = null;
        String query = "SELECT group_code FROM users WHERE username = ?"; // Adjust the query according to your database schema
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
             
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                code = rs.getString("group_code");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return code; // Return the fetched code or null if not found
    }
    
    public static List<String> fetchUsersInGroup(String groupCode) {
        List<String> users = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT username FROM users WHERE group_code = ?")) {
            
            ps.setString(1, groupCode);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                users.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
}
