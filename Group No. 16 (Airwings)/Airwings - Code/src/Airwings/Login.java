package Airwings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {

    JButton submit, reset, close;
    JTextField tfusername;
    JPasswordField tfpassword;

    public Login() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
       
        JLabel heading = new JLabel("Airwings");
        heading.setBounds(130, 40, 500, 35);
        heading.setFont(new Font("ALGERIAN", Font.BOLD, 30));
        heading.setForeground(Color.RED);
        add(heading);
        
        
        
        JLabel lblusername = new JLabel("Username");
        lblusername.setBounds(20, 140, 100, 20);
        add(lblusername);

        tfusername = new JTextField();
        tfusername.setBounds(130, 140, 200, 20);
        add(tfusername);

        JLabel lblpassword = new JLabel("Password");
        lblpassword.setBounds(20, 180, 100, 20);
        add(lblpassword);

        tfpassword = new JPasswordField();
        tfpassword.setBounds(130, 180, 200, 20);
        add(tfpassword);

        reset = new JButton("Reset");
        reset.setBounds(40, 250, 120, 20);
        reset.addActionListener(this);
        add(reset);

        submit = new JButton("Submit");
        submit.setBounds(190, 250, 120, 20);
        submit.addActionListener(this);
        add(submit);

        close = new JButton("Close");
        close.setBounds(120, 300, 120, 20);
        close.addActionListener(this);
        add(close);

        setSize(450, 420);
        setLocation(600, 250);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            String username = tfusername.getText();
            String password = tfpassword.getText();

            try {
                Conn c = new Conn();

                String query = "select * from login where username = '" + username + "' and password = '" + password + "'";

                ResultSet rs = c.s.executeQuery(query);

                if (rs.next()) {
                    new Home();
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Username or Password");
                    setVisible(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == close) {
            setVisible(false);
        } else if (ae.getSource() == reset) {
            tfusername.setText("");
            tfpassword.setText("");
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
