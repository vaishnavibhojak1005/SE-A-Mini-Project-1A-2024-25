package Airwings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class BoardingPass extends JFrame implements ActionListener{
    
    JTextField tfpnr;
    JLabel tfname, tfnationality, lblsrc, lbldest, labelfname, labelfcode, labelfdeptime, labelfarrtime, labelfjourneytime, labelfprice, labeldate;
    JButton fetchButton;
    
    public BoardingPass() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        JLabel heading = new JLabel("AIRWINGS");
        heading.setBounds(360, 10, 450, 35);
        heading.setFont(new Font("Algerian", Font.PLAIN, 37));
        add(heading);
        
        JLabel subheading = new JLabel("Boarding Pass");
        subheading.setBounds(360, 50, 300, 30);
        subheading.setFont(new Font("Tahoma", Font.PLAIN, 24));
        subheading.setForeground(Color.RED);
        add(subheading);
        
        JLabel lblaadhar = new JLabel("PNR DETAILS");
        lblaadhar.setBounds(60, 100, 150, 25);
        lblaadhar.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblaadhar);
        
        tfpnr = new JTextField();
        tfpnr.setBounds(220, 100, 150, 25);
        add(tfpnr);
        
        fetchButton = new JButton("Enter");
        fetchButton.setBackground(Color.BLACK);
        fetchButton.setForeground(Color.WHITE);
        fetchButton.setBounds(380, 100, 120, 25);
        fetchButton.addActionListener(this);
        add(fetchButton);
        
        JLabel lblname = new JLabel("NAME");
        lblname.setBounds(60, 140, 150, 25);
        lblname.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lblname);
        
        tfname = new JLabel();
        tfname.setBounds(220, 140, 150, 25);
        add(tfname);
        
        JLabel lblnationality = new JLabel("NATIONALITY");
        lblnationality.setBounds(60, 180, 150, 25);
        lblnationality.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lblnationality);
        
        tfnationality = new JLabel();
        tfnationality.setBounds(220, 180, 150, 25);
        add(tfnationality);
        
        JLabel lbladdress = new JLabel("SRC");
        lbladdress.setBounds(60, 220, 150, 25);
        lbladdress.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lbladdress);
        
        lblsrc = new JLabel();
        lblsrc.setBounds(220, 220, 150, 25);
        add(lblsrc);
        
        JLabel lblgender = new JLabel("DEST");
        lblgender.setBounds(380, 220, 150, 25);
        lblgender.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lblgender);
        
        lbldest = new JLabel();
        lbldest.setBounds(540, 220, 150, 25);
        add(lbldest);
        
        JLabel lblfname = new JLabel("Flight Name");
        lblfname.setBounds(60, 260, 150, 25);
        lblfname.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lblfname);
        
        labelfname = new JLabel();
        labelfname.setBounds(220, 260, 150, 25);
        add(labelfname);
        
        JLabel lblfcode = new JLabel("Flight Code");
        lblfcode.setBounds(380, 260, 150, 25);
        lblfcode.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lblfcode);
        
        labelfcode = new JLabel();
        labelfcode.setBounds(540, 260, 150, 25);
        add(labelfcode);
        
         JLabel lblfdeptime = new JLabel("Flight DepTime");
        lblfdeptime.setBounds(60, 300, 150, 25);
        lblfdeptime.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lblfdeptime);
        
        labelfdeptime = new JLabel();
        labelfdeptime.setBounds(220, 300, 150, 25);
        add(labelfdeptime);
        
        JLabel lblfarrtime = new JLabel("Flight ArrTime");
        lblfarrtime.setBounds(380, 300, 150, 25);
        lblfarrtime.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lblfarrtime);
        
        labelfarrtime = new JLabel();
        labelfarrtime.setBounds(540, 300, 150, 25);
        add(labelfarrtime);
        
        JLabel lblfjourneytime = new JLabel("Flight JourneyTime");
        lblfjourneytime.setBounds(60, 340, 150, 25);
        lblfjourneytime.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lblfjourneytime);
        
        labelfjourneytime = new JLabel();
        labelfjourneytime.setBounds(220, 340, 150, 25);
        add(labelfjourneytime);
        
        
        
        JLabel lbldate = new JLabel("Date");
        lbldate.setBounds(60, 380, 150, 25);
        lbldate.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lbldate);
        
        labeldate = new JLabel();
        labeldate.setBounds(220, 380, 150, 25);
        add(labeldate);
        
         JLabel lblfprice = new JLabel("Flight Price");
        lblfprice.setBounds(380, 340, 150, 25);
        lblfprice.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lblfprice);
        
        labelfprice = new JLabel();
        labelfprice.setBounds(540, 340, 150, 25);
        add(labelfprice);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Airwings/imageandicons/BoardingPass.jpg"));
        Image i2 = i1.getImage().getScaledInstance(300, 230, Image.SCALE_DEFAULT);
        ImageIcon image = new ImageIcon(i2);
        JLabel lblimage = new JLabel(image);
        lblimage.setBounds(600, 0, 300, 300);
        add(lblimage);
        
        setSize(900, 460);
        setLocation(300, 150);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        String pnr = tfpnr.getText();

        try {
            Conn conn = new Conn();

            String query = "select * from reservation where PNR = '"+pnr+"'";

            ResultSet rs = conn.s.executeQuery(query);

            if (rs.next()) {
                tfname.setText(rs.getString("name")); 
                tfnationality.setText(rs.getString("nationality")); 
                lblsrc.setText(rs.getString("src")); 
                lbldest.setText(rs.getString("des"));  
                labelfname.setText(rs.getString("flightname"));  
                labelfcode.setText(rs.getString("flightcode")); 
                labelfdeptime.setText(rs.getString("flightdeptime"));
                labelfarrtime.setText(rs.getString("flightarrtime"));
                labelfjourneytime.setText(rs.getString("flightjourneytime"));
                labeldate.setText(rs.getString("ddate"));
                labelfprice.setText(rs.getString("flightprice"));
            } else {
                JOptionPane.showMessageDialog(null, "Please enter correct PNR");                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new BoardingPass();
    }
}
