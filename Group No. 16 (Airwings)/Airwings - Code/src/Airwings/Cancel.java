package Airwings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class Cancel extends JFrame implements ActionListener{
    
    JTextField tfpnr;
    JLabel tfname, cancellationno, lblfcode, lblfprice, lblfdeptime, lblfarrtime, lbldateoftravel;
    JButton fetchButton, flight;
    
    public Cancel() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        Random random = new Random();
        
        JLabel heading = new JLabel("CANCELLATION");
        heading.setBounds(180, 20, 250, 35);
        heading.setFont(new Font("Algerian", Font.BOLD, 32));
        add(heading);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Airwings/imageandicons/cancel.png"));
        Image i2 = i1.getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(470, 120, 250, 250);
        add(image);
        
        JLabel lblaadhar = new JLabel("PNR Number");
        lblaadhar.setBounds(60, 80, 150, 25);
        lblaadhar.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblaadhar);
        
        tfpnr = new JTextField();
        tfpnr.setBounds(220, 80, 150, 25);
        add(tfpnr);
        
        fetchButton = new JButton("Show Details");
        fetchButton.setBackground(Color.BLACK);
        fetchButton.setForeground(Color.WHITE);
        fetchButton.setBounds(380, 80, 120, 25);
        fetchButton.addActionListener(this);
        add(fetchButton);
        
        JLabel lblname = new JLabel("Name");
        lblname.setBounds(60, 110, 150, 25);
        lblname.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblname);
        
        tfname = new JLabel();
        tfname.setBounds(220, 110, 150, 25);
        add(tfname);
        
        JLabel lblcancellation = new JLabel("Cancellation No");
        lblcancellation.setBounds(60, 140, 150, 25);
        lblcancellation.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblcancellation);
        
        cancellationno = new JLabel("" + random.nextInt(1000000));
        cancellationno.setBounds(220, 140, 150, 25);
        add(cancellationno);
        
        JLabel lblflightcode = new JLabel("Flight Code");
        lblflightcode.setBounds(60, 170, 150, 25);
        lblflightcode.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblflightcode);
        
        lblfcode = new JLabel();
        lblfcode.setBounds(220, 170, 150, 25);
        add(lblfcode);
        
        JLabel lblflightprice = new JLabel("Flight Price");
        lblflightprice.setBounds(60, 205, 150, 25);
        lblflightprice.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblflightprice);
        
        lblfprice = new JLabel();
        lblfprice.setBounds(220, 205, 150, 25);
        add(lblfprice);
        
        JLabel lblflightdeptime = new JLabel("Flight DepTime");
        lblflightdeptime.setBounds(60, 240, 150, 25);
        lblflightdeptime.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblflightdeptime);
        
        lblfdeptime = new JLabel();
        lblfdeptime.setBounds(220, 240, 150, 25);
        add(lblfdeptime);
        
         JLabel lblflightarrtime = new JLabel("Flight ArrTime");
        lblflightarrtime.setBounds(60, 275, 150, 25);
        lblflightarrtime.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblflightarrtime);
        
        lblfarrtime = new JLabel();
        lblfarrtime.setBounds(220, 275, 150, 25);
        add(lblfarrtime);
        
        JLabel lbldate = new JLabel("Date");
        lbldate.setBounds(60, 310, 150, 25);
        lbldate.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lbldate);
        
        lbldateoftravel = new JLabel();
        lbldateoftravel.setBounds(220, 310, 150, 25);
        add(lbldateoftravel);
        
        flight = new JButton("Cancel");
        flight.setBackground(Color.BLACK);
        flight.setForeground(Color.WHITE);
        flight.setBounds(220, 350, 120, 25);
        flight.addActionListener(this);
        add(flight);
        
        setSize(800, 450);
        setLocation(350, 150);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == fetchButton) {
            String pnr = tfpnr.getText();
            
            try {
                Conn conn = new Conn();

                String query = "select * from reservation where PNR = '"+pnr+"'";

                ResultSet rs = conn.s.executeQuery(query);
                
                if (rs.next()) {
                    tfname.setText(rs.getString("name")); 
                    lblfcode.setText(rs.getString("flightcode")); 
                    lblfprice.setText(rs.getString("flightprice"));
                    lblfdeptime.setText(rs.getString("flightdeptime"));
                    lblfarrtime.setText(rs.getString("flightarrtime"));
                    lbldateoftravel.setText(rs.getString("ddate"));
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter correct PNR");                
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == flight) {
            String name = tfname.getText();
            String pnr = tfpnr.getText();
            String cancelno = cancellationno.getText();
            String fcode = lblfcode.getText();
            String fprice = lblfprice.getText();
            String fdeptime = lblfdeptime.getText();
            String farrtime = lblfarrtime.getText();
            String date = lbldateoftravel.getText();
            
            try {
                Conn conn = new Conn();

                String query = "insert into cancel values('"+pnr+"', '"+name+"', '"+cancelno+"', '"+fcode+"', '"+fprice+"', '"+fdeptime+"', '"+farrtime+"', '"+date+"')";

                conn.s.executeUpdate(query);
                conn.s.executeUpdate("delete from reservation where PNR = '"+pnr+"'");
                
                JOptionPane.showMessageDialog(null, "Ticket Cancelled");
                setVisible(false);
            
            } catch (Exception e) {
                e.printStackTrace();
            }
        } 
    }

    public static void main(String[] args) {
        new Cancel();
    }
}
