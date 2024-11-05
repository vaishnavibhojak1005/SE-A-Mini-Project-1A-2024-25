/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connection.MyConnection;
import static connection.MyConnection.con;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author arjun
 */
public class AdDao {

    Connection con = MyConnection.getConnection();
    PreparedStatement ps;
    Statement st;
    ResultSet rs;

    public int getMaxRow() {
        int row = 0;
        try {
            st = con.createStatement();
            rs = st.executeQuery("select max(id) from advertisements");
            while (rs.next()) {
                row = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row + 1;
    }
    
    public boolean isIDExist(int id) {
        try {
            ps = con.prepareStatement("select * from advertisements where id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public void insert(int id, byte[] aimage, String adesc) {
        String sql = "insert into advertisements values(?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setBytes(2, aimage);
            ps.setString(3, adesc);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AdDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void update(int id, String adesc) {
        String sql = "Update advertisements set adesc = ? where id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, adesc);
            ps.setInt(2, id);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Ad successfully updated");
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void delete(int id) {
        int x = JOptionPane.showConfirmDialog(null, "Are you sure to delete this Ad?", "Ad Deleted", JOptionPane.OK_CANCEL_OPTION,0);
        if (x == JOptionPane.OK_OPTION) {
            try {
                ps = con.prepareStatement("delete from advertisements where id =?");
                ps.setInt(1, id);
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Ad deleted");

                }
            } catch (SQLException ex) {
                Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
    
    public ImageIcon getAdImage(int AdId) {
    ImageIcon imageIcon = null;
    String sql = "select image from advertisements where id = ?"; // Modify according to your table structure
    try {
        ps = con.prepareStatement(sql);
        ps.setInt(1, AdId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            byte[] imgBytes = rs.getBytes("image");
            if (imgBytes != null) {
                imageIcon = new ImageIcon(imgBytes);
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return imageIcon;
}
    
    public void getAdValue(JTable table, String search) {
        String sql = "select * from advertisements where concat (id,image,adesc) like? order by id desc";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + search + "%");
            rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[3];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(3);
                model.addRow(row);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
