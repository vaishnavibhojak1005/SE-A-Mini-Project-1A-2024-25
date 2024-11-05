/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import StreamlinePPMS.MyConnection;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Aayush
 */
public class RegistrationDao {

    Connection con = MyConnection.getConnection();
    PreparedStatement ps;
    Statement st;
    ResultSet rs;

    public void insert(String username, String id, String user, String pass, String ssc, String hsc, String uni, String email, String dd, String mm, String yy, byte[] image) {
        {
            String sql = "insert into registration (studentname, studentid, username, upassword, percentagessc, percentagehsc, percentageuniversity, emailaddress, dobdd, dobmm, dobyyyy, image) values(?,?,?,?,?,?,?,?,?,?,?,?)";
            try {
                ps = con.prepareStatement(sql);
                ps.setString(1, username);
                ps.setString(2, id);
                ps.setString(3, user);
                ps.setString(4, pass);
                ps.setString(5, ssc);
                ps.setString(6, hsc);
                ps.setString(7, uni);
                ps.setString(8, email);
                ps.setString(9, dd);
                ps.setString(10, mm);
                ps.setString(11, yy);
                ps.setBytes(12, image);

                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "User registered successfully");
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void insert2(String Gender, String state, String address, String contact, String Year, String skills, String studentid) {
        {
            String sql = "update registration set gender = ?, state = ?, address = ?, phoneno = ?, yearofstudy = ?, skills = ? where studentid = ?";
          //String sql = "insert into registration (gender, state, address, phoneno, yearofstudy, skills) values(?,?,?,?,?)";
            try {
                ps = con.prepareStatement(sql);
                ps.setString(1, Gender);
                ps.setString(2, state);
                ps.setString(3, address);
                ps.setString(4, contact);
                ps.setString(5, Year);
                ps.setString(6, skills);
                ps.setString(7, studentid);
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "User registered successfully");
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
