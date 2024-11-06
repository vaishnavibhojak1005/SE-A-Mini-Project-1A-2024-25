/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.SplitSmart;

import com.mycompany.SplitSmart.utils.DatabaseConnection;
import com.mycompany.SplitSmart.utils.UserSession;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author sanjananaik
 */
public class SettleUp extends javax.swing.JFrame {
    private ArrayList<String> usersList;
    private JCheckBox[] userCheckBoxes;
    private JButton confirmButton;
    private JButton goBackButton;
    private JTable debtsTable;
    private JLabel[] owedUserLabels;
    /**
     * Creates new form SettleUp
     */
    public SettleUp() {
        //initComponents();
        setTitle("Settling Up");
        setSize(500, 600);  // Set the width and height of the frame
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);  // Center the window
        
        String currentUser = UserSession.getInstance().getUsername();
        List<Debt> userOwesDebts = fetchUserDebts(currentUser);
        List<Debt> userIsOwedDebts = fetchDebtsOwedToUser(currentUser);

        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the panel

        // Add header
        JLabel headerLabel = new JLabel("Settle Your Payments");
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        headerLabel.setForeground(new Color(0, 102, 102));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Create a panel for checkboxes and add it to a JScrollPane for scrolling if needed
        JPanel owesPanel = new JPanel();
        owesPanel.setLayout(new BoxLayout(owesPanel, BoxLayout.Y_AXIS));
        owesPanel.setBorder(BorderFactory.createTitledBorder("You Owe"));

        userCheckBoxes = new JCheckBox[userOwesDebts.size()];
        for (int i = 0; i < userOwesDebts.size(); i++) {
            Debt debt = userOwesDebts.get(i);
            JCheckBox checkBox = new JCheckBox(debt.getUserName() + ": " + debt.getAmount() + " INR " + "for " + debt.getCategory());
            checkBox.setFont(new Font("SansSerif", Font.PLAIN, 16));
            owesPanel.add(Box.createVerticalStrut(10));
            owesPanel.add(checkBox);
            userCheckBoxes[i] = checkBox;
        }

        // Panel for debts owed to the user
        JPanel owedPanel = new JPanel();
        owedPanel.setLayout(new BoxLayout(owedPanel, BoxLayout.Y_AXIS));
        owedPanel.setBorder(BorderFactory.createTitledBorder("You Are Owed"));

        owedUserLabels = new JLabel[userIsOwedDebts.size()];
        for (int i = 0; i < userIsOwedDebts.size(); i++) {
            Debt debt = userIsOwedDebts.get(i);
            JLabel label = new JLabel(debt.getUserName() + " owes you: " + debt.getAmount() + " INR "+ "for " + debt.getCategory());
            label.setFont(new Font("SansSerif", Font.PLAIN, 16));
            owedPanel.add(Box.createVerticalStrut(10));
            owedPanel.add(label);
            owedUserLabels[i] = label;
        }
        
 
        // Wrap owesPanel and owedPanel in JScrollPane for scrollability
        JScrollPane owesScrollPane = new JScrollPane(owesPanel);
        JScrollPane owedScrollPane = new JScrollPane(owedPanel);
        owesScrollPane.setPreferredSize(new Dimension(450, 150));
        owedScrollPane.setPreferredSize(new Dimension(450, 150));
        
        
        JPanel debtsPanel = new JPanel();
        debtsPanel.setLayout(new BorderLayout());
        debtsPanel.add(owesScrollPane, BorderLayout.NORTH);
        debtsPanel.add(owedScrollPane, BorderLayout.SOUTH);
        mainPanel.add(debtsPanel, BorderLayout.CENTER);

        // Create and style the confirm button
        confirmButton = new JButton("CONFIRM");
        confirmButton.setFont(new java.awt.Font("Helvetica Neue", 0, 18));
        confirmButton.setForeground(new Color(0, 102, 102));
        confirmButton.setBackground(new Color(0, 102, 102));
        confirmButton.setFocusPainted(false);
        confirmButton.setPreferredSize(new Dimension(200, 40));
        confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add action listener to handle confirmation
        confirmButton.addActionListener(e -> handleConfirmPayments(currentUser));
        
        goBackButton = new JButton("GO BACK");
        goBackButton.setBounds(50, 350, 120, 30);
        goBackButton.setPreferredSize(new Dimension(200, 40));
        goBackButton.setFont(new java.awt.Font("Helvetica Neue", 0, 18));
        goBackButton.setForeground(new java.awt.Color(204, 0, 0));;
        goBackButton.setFocusPainted(false);
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 MainPage MainPageFrame = new MainPage();
                MainPageFrame.setVisible(true);
                MainPageFrame.pack();
                MainPageFrame.setLocationRelativeTo(null);
                dispose(); // Close the window
            }
        });
        // Create a panel to hold the confirm button at the bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(confirmButton);
        buttonPanel.add(goBackButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        
        // Add the main panel to the frame
        add(mainPanel);
        setVisible(true);
    }

    
    private void handleConfirmPayments(String currentUser) {
        for (int i = 0; i < userCheckBoxes.length; i++) {
            if (userCheckBoxes[i].isSelected()) {
                String[] parts = userCheckBoxes[i].getText().split(": "); // Split by colon and space
            String userToPay = parts[0];  // Extract the user name
            String[] amountParts = parts[1].split(" INR");  // Split by ' INR' to get the amount
            double amount = Double.parseDouble(amountParts[0].trim());  // Parse the amount
            processPayment(currentUser, userToPay, amount);
            }
        }
        // Optionally refresh the page or show a confirmation message
        JOptionPane.showMessageDialog(this, "Payments confirmed!");
        this.dispose();
        SettleUp SettleUpFrame = new SettleUp();
         SettleUpFrame.setVisible(true);
         SettleUpFrame.pack();
         SettleUpFrame.setLocationRelativeTo(null);
    }
    
    private void processPayment(String fromUser, String toUser, double amount) {
    String expenseId = null;

    System.out.println("Processing payment:");
    System.out.println("From User: " + fromUser);
    System.out.println("To User: " + toUser);
    System.out.println("Amount: " + amount);

    // Step 1: Find the expense_id where fromUser owes toUser
    String findSql = "SELECT expense_id FROM expense_users " +
                     "WHERE user_name = ? AND share = ? " +
                     "AND expense_id IN (SELECT expense_id FROM expenses WHERE from_user = ?)";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(findSql)) {

        preparedStatement.setString(1, fromUser);    // The user who is owed (toUser)
        preparedStatement.setDouble(2, amount);  // The user making the payment (fromUser)
        preparedStatement.setString(3, toUser);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            expenseId = resultSet.getString("expense_id");
            System.out.println("Found expense_id: " + expenseId);
        } else {
            System.out.println("No matching expense_id found.");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    // Step 2: Delete the entry for the payment
    if (expenseId != null) {
        String deleteSql = "DELETE FROM expense_users WHERE user_name = ? AND expense_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {

            preparedStatement.setString(1, fromUser);  // The user making the payment (fromUser)
            preparedStatement.setString(2, expenseId); // The expense_id found earlier

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Payment processed: " + fromUser + " paid " + toUser + " " + amount + " INR");
            } else {
                System.out.println("No rows deleted.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } else {
        System.out.println("No expense found for this payment.");
    }
}
    
    
    public List<Debt> fetchDebtsOwedToUser(String currentUser) {
        List<Debt> debtsOwedToUser = new ArrayList<>();

        String query = "SELECT eu.user_name, e.category, eu.share " +
                       "FROM expenses e " +
                       "JOIN expense_users eu ON e.expense_id = eu.expense_id " +
                       "WHERE e.from_user = ? AND eu.user_name != ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, currentUser);
            stmt.setString(2, currentUser);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String owedUser = rs.getString("user_name");
                double share = rs.getDouble("share");
                String category = rs.getString("category");
                debtsOwedToUser.add(new Debt(owedUser, share, category));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return debtsOwedToUser;
    }
    
    private List<Debt> fetchUserDebts(String userName) {
    List<Debt> debts = new ArrayList<>();
    
    // Modify the query to get individual expenses and the category
    String sql = "SELECT e.from_user, e.category, eu.share " +
                 "FROM expense_users eu " +
                 "JOIN expenses e ON eu.expense_id = e.expense_id " +
                 "WHERE eu.user_name = ? AND e.from_user != ?";  // user owes to the payer

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

        preparedStatement.setString(1, userName); // User who owes
        preparedStatement.setString(2, userName); // Exclude expenses where the user is the payer

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String owingToUser = resultSet.getString("from_user");
            String category = resultSet.getString("category"); // Get the category
            double share = resultSet.getDouble("share");
            
            // Create a Debt object with the new fields (category included)
            debts.add(new Debt(owingToUser, share, category)); 
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return debts;
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        PaymentCheckBox1 = new javax.swing.JCheckBox();
        PaymentCheckBox2 = new javax.swing.JCheckBox();
        PaymentCheckBox3 = new javax.swing.JCheckBox();
        PaymentAmount1 = new javax.swing.JLabel();
        PaymentAmount2 = new javax.swing.JLabel();
        PaymentAmount3 = new javax.swing.JLabel();
        SaveSettleBtn = new javax.swing.JButton();
        GoBackBtnSettleUpPg = new javax.swing.JButton();

        jCheckBox3.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jCheckBox3.setText("FROM Sanjana TO Abhishek");

        jCheckBox5.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jCheckBox5.setText("FROM Samruddhi TO Rushikesh");

        jMenu3.setText("File");
        jMenuBar2.add(jMenu3);

        jMenu4.setText("Edit");
        jMenuBar2.add(jMenu4);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Muna", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("PAYMENTS");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(285, 285, 285)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                .addGap(14, 14, 14))
        );

        PaymentCheckBox1.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        PaymentCheckBox1.setText(" Sanjana ----------> Abhishek");

        PaymentCheckBox2.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        PaymentCheckBox2.setText("Samruddhi  ----------> Rushikesh");

        PaymentCheckBox3.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        PaymentCheckBox3.setText(" Abhishek  ----------> Rushikesh");

        PaymentAmount1.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        PaymentAmount1.setText("0 INR");

        PaymentAmount2.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        PaymentAmount2.setText("0 INR");

        PaymentAmount3.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        PaymentAmount3.setText("0 INR");

        SaveSettleBtn.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        SaveSettleBtn.setForeground(new java.awt.Color(0, 102, 102));
        SaveSettleBtn.setText("SAVE");

        GoBackBtnSettleUpPg.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        GoBackBtnSettleUpPg.setForeground(new java.awt.Color(255, 0, 0));
        GoBackBtnSettleUpPg.setText("GO BACK");
        GoBackBtnSettleUpPg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GoBackBtnSettleUpPgActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(PaymentCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(65, 65, 65)
                        .addComponent(PaymentAmount1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(PaymentCheckBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(PaymentCheckBox2)
                                .addGap(66, 66, 66)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(PaymentAmount2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PaymentAmount3, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(GoBackBtnSettleUpPg, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(228, 228, 228)
                        .addComponent(SaveSettleBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(156, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(PaymentCheckBox1)
                        .addGap(24, 24, 24)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(PaymentAmount2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PaymentCheckBox2)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(PaymentAmount1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)))
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(PaymentCheckBox3)
                    .addComponent(PaymentAmount3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(96, 96, 96)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SaveSettleBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(GoBackBtnSettleUpPg, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(136, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void GoBackBtnSettleUpPgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GoBackBtnSettleUpPgActionPerformed
MainPage MainPageFrame = new MainPage();
         MainPageFrame.setVisible(true);
         MainPageFrame.pack();
         MainPageFrame.setLocationRelativeTo(null);
         this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_GoBackBtnSettleUpPgActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SettleUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SettleUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SettleUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SettleUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SettleUp().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton GoBackBtnSettleUpPg;
    private javax.swing.JLabel PaymentAmount1;
    private javax.swing.JLabel PaymentAmount2;
    private javax.swing.JLabel PaymentAmount3;
    private javax.swing.JCheckBox PaymentCheckBox1;
    private javax.swing.JCheckBox PaymentCheckBox2;
    private javax.swing.JCheckBox PaymentCheckBox3;
    private javax.swing.JButton SaveSettleBtn;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables

    private static class Debt {

        private String userName;
    private double amount;
    private String category;

    public Debt(String userName, double amount, String category) {
        this.userName = userName;
        this.amount = amount;
        this.category = category;
    }

    public String getUserName() {
        return userName;
    }

    public double getAmount() {
        return amount;
    }
    public String getCategory() {
        return category;
    }
    }
}
