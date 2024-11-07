package Airwings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import com.toedter.calendar.JDateChooser;
import java.util.*;

public class BookFlight extends JFrame implements ActionListener{
    
    JTextField tfaadhar;
    JLabel tfname, tfnationality, tfaddress, labelgender, labelfname, labelfcode, labelfprice, labelfdept, labelfarrt, labelfjourneyt;
    JButton bookflight, fetchButton, flight;
    Choice source, destination;
    JDateChooser dcdate;
    
    public BookFlight() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        JLabel heading = new JLabel("Book Flight");
        heading.setBounds(420, 10, 500, 35);
        heading.setFont(new Font("Algerian", Font.BOLD, 40));
        heading.setForeground(Color.BLUE);
        add(heading);
        
        JLabel lblaadhar = new JLabel("Aadhar");
        lblaadhar.setBounds(60, 60, 150, 25);
        lblaadhar.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lblaadhar);
        
        tfaadhar = new JTextField();
        tfaadhar.setBounds(220, 60, 150, 25);
        add(tfaadhar);
        
        fetchButton = new JButton("Fetch User");
        fetchButton.setBackground(Color.BLACK);
        fetchButton.setForeground(Color.WHITE);
        fetchButton.setBounds(420, 60, 120, 25);
        fetchButton.addActionListener(this);
        add(fetchButton);
        
        JLabel lblname = new JLabel("Name");
        lblname.setBounds(60, 100, 150, 25);
        lblname.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lblname);
        
        tfname = new JLabel();
        tfname.setBounds(220, 100, 150, 25);
        add(tfname);
        
        JLabel lblnationality = new JLabel("Nationality");
        lblnationality.setBounds(60, 130, 150, 25);
        lblnationality.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lblnationality);
        
        tfnationality = new JLabel();
        tfnationality.setBounds(220, 130, 150, 25);
        add(tfnationality);
        
        JLabel lbladdress = new JLabel("Address");
        lbladdress.setBounds(60, 170, 150, 25);
        lbladdress.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lbladdress);
        
        tfaddress = new JLabel();
        tfaddress.setBounds(220, 170, 150, 25);
        add(tfaddress);
        
        JLabel lblgender = new JLabel("Gender");
        lblgender.setBounds(60, 200, 150, 25);
        lblgender.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lblgender);
        
        labelgender = new JLabel("Gender");
        labelgender.setBounds(220, 200, 150, 25);
        add(labelgender);
        
        JLabel lblsource = new JLabel("Source");
        lblsource.setBounds(60, 240, 150, 25);
        lblsource.setFont(new Font("Tahoma", Font.PLAIN, 22));
        add(lblsource);
        
        source = new Choice();
        source.setBounds(220, 240, 150, 25);       
        add(source);
        
        JLabel lbldest = new JLabel("Destination");
        lbldest.setBounds(60, 280, 150, 25);
        lbldest.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lbldest);
        
        destination = new Choice();
        destination.setBounds(220, 280, 150, 25);       
        add(destination);
        
        try {
            Conn c = new Conn();
            String query = "select * from flight";
            ResultSet rs = c.s.executeQuery(query);
            
            while(rs.next()) {
                source.add(rs.getString("source"));
                destination.add(rs.getString("destination"));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        flight = new JButton("Fetch Flights");
        flight.setBackground(Color.BLACK);
        flight.setForeground(Color.WHITE);
        flight.setBounds(380, 280, 120, 25);
        flight.addActionListener(this);
        add(flight);
        
        JLabel lblfname = new JLabel("Flight Name");
        lblfname.setBounds(60, 320, 150, 25);
        lblfname.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lblfname);
        
        labelfname = new JLabel();
        labelfname.setBounds(220, 320, 150,25 );
        add(labelfname);
        
        JLabel lblfcode = new JLabel("Flight Code");
        lblfcode.setBounds(60, 365, 150, 25);
        lblfcode.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lblfcode);
        
        labelfcode = new JLabel();
        labelfcode.setBounds(220, 365, 150, 25);
        add(labelfcode);
        
        JLabel lblfprice = new JLabel("Flight Price");
        lblfprice.setBounds(60, 405, 150, 25);
        lblfprice.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lblfprice);
        
        labelfprice = new JLabel();
        labelfprice.setBounds(220, 405, 150, 25);
        add(labelfprice);
        
         JLabel lblfdept = new JLabel("Flight DepTime");
        lblfdept.setBounds(60, 440, 150, 25);
        lblfdept.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lblfdept);
        
        labelfdept = new JLabel();
        labelfdept.setBounds(230, 440, 150, 25);
        add(labelfdept);
        
        JLabel lblfarrt = new JLabel("Flight ArrTime");
        lblfarrt.setBounds(60, 480, 150, 25);
        lblfarrt.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lblfarrt);
        
        labelfarrt = new JLabel();
        labelfarrt.setBounds(230, 480, 150, 25);
        add(labelfarrt);
        
        JLabel lblfjourneyt = new JLabel("Flight JourneyTime");
        lblfjourneyt.setBounds(60, 520, 150, 25);
        lblfjourneyt.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lblfjourneyt);
        
        labelfjourneyt = new JLabel();
        labelfjourneyt.setBounds(230, 520, 150, 25);
        add(labelfjourneyt);
        
        JLabel lbldate = new JLabel("Date of Travel");
        lbldate.setBounds(60, 560, 150, 25);
        lbldate.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lbldate);
        
        dcdate = new JDateChooser();
        dcdate.setBounds(220, 560, 150, 25);
        add(dcdate);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Airwings/imageandicons/Book Flight.jpg"));
        Image i2 = i1.getImage().getScaledInstance(450, 320, Image.SCALE_DEFAULT);
        ImageIcon image = new ImageIcon(i2);
        JLabel lblimage = new JLabel(image);
        lblimage.setBounds(550, 80, 500, 410);
        add(lblimage);
        
        bookflight = new JButton("Book Flight");
        bookflight.setBackground(Color.BLACK);
        bookflight.setForeground(Color.WHITE);
        bookflight.setBounds(220, 640, 150, 25);
        bookflight.addActionListener(this);
        add(bookflight);
        
        setSize(1100, 750);
        setLocation(200, 50);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == fetchButton) {
            String aadhar = tfaadhar.getText();
            
            try {
                Conn conn = new Conn();

                String query = "select * from passenger where aadhar = '"+aadhar+"'";

                ResultSet rs = conn.s.executeQuery(query);
                
                if (rs.next()) {
                    tfname.setText(rs.getString("name")); 
                    tfnationality.setText(rs.getString("nationality")); 
                    tfaddress.setText(rs.getString("address"));
                    labelgender.setText(rs.getString("gender"));
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter correct aadhar");                
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == flight) {
            String src = source.getSelectedItem();
            String dest = destination.getSelectedItem();
            try {
                Conn conn = new Conn();

                String query = "select * from flight where source = '"+src+"' and destination = '"+dest+"'";

                ResultSet rs = conn.s.executeQuery(query);
                
                if (rs.next()) {
                    labelfname.setText(rs.getString("f_name")); 
                    labelfcode.setText(rs.getString("f_code"));
                    labelfprice.setText(rs.getString("f_price"));
                    labelfdept.setText(rs.getString("f_deptime"));
                    labelfarrt.setText(rs.getString("f_arrtime"));
                    labelfjourneyt.setText(rs.getString("f_journeytime"));
                    
                } else {
                    JOptionPane.showMessageDialog(null, "No Flights Found");                
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Random random = new Random();
            
            String aadhar = tfaadhar.getText();
            String name = tfname.getText(); 
            String nationality = tfnationality.getText();
            String flightname = labelfname.getText(); 
            String flightcode = labelfcode.getText();
            String flightprice = labelfprice.getText();
            String flightdeptime = labelfdept.getText();
            String flightarrtime = labelfarrt.getText();
            String flightjourneytime = labelfjourneyt.getText();
            String src = source.getSelectedItem(); 
            String des = destination.getSelectedItem();
            String ddate = ((JTextField) dcdate.getDateEditor().getUiComponent()).getText();
            
            try {
                Conn conn = new Conn();

                String query = "insert into reservation values('PNR-"+random.nextInt(1000000)+"', 'TIC-"+random.nextInt(10000)+"', '"+aadhar+"', '"+name+"', '"+nationality+"', '"+flightname+"', '"+flightcode+"', '"+flightprice+"', '"+flightdeptime+"', '"+flightarrtime+"', '"+flightjourneytime+"', '"+src+"', '"+des+"', '"+ddate+"')";

                conn.s.executeUpdate(query);
                
                JOptionPane.showMessageDialog(null, "Ticket Booked Successfully");

                setVisible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new BookFlight();
    }
}
