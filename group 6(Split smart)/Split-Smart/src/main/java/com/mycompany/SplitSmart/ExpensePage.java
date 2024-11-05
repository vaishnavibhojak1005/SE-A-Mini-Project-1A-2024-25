/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.SplitSmart;

import com.mycompany.SplitSmart.utils.DatabaseConnection;
import static com.mycompany.SplitSmart.utils.DatabaseOperations.fetchUsersInGroup;
import com.mycompany.SplitSmart.utils.UserSession;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author sanjananaik
 */
public class ExpensePage extends javax.swing.JFrame {
    private JTextField categoryTextField; // Text field for category
    private JFormattedTextField amountTextField; // Text field for amount
    private JComboBox<String> fromDropdown; // Dropdown for selecting "FROM" user
    private JCheckBox[] userCheckBoxes; // Array of checkboxes for "TO" users
    private ArrayList<String> usersList;
    private JButton saveButton;
    private JButton goBackButton;
    
    public ExpensePage() {
        pack();
        //setResizable(false); // Disable resizing if needed
        setLocationRelativeTo(null); // This will center the window on the screen
        setMinimumSize(new Dimension(600, 500));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        String groupCode = UserSession.getInstance().getGroupCode();
        usersList = (ArrayList<String>) fetchUsersInGroup(groupCode);
//        initComponents();
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(600, 500));

        // Create the left panel (white background)
        JPanel whitePanel = new JPanel();
        whitePanel.setLayout(null); // Set layout to null for custom positioning
        whitePanel.setBackground(Color.WHITE); // Set background to white
        whitePanel.setPreferredSize(new Dimension(400, 500)); // Width is 400px to take most of the screen

        // Header Label
        JLabel headerLabel = new JLabel("EXPENSE");
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        headerLabel.setBounds(150, 20, 100, 30);
        whitePanel.add(headerLabel);

        // Category Label and Text Field
        JLabel categoryLabel = new JLabel("CATEGORY");
        categoryLabel.setBounds(50, 70, 100, 25);
        whitePanel.add(categoryLabel);

        categoryTextField = new JTextField();
        categoryTextField.setBounds(150, 70, 150, 25);
        whitePanel.add(categoryTextField);

        // Amount Label and Text Field
        JLabel amountLabel = new JLabel("AMOUNT");
        amountLabel.setBounds(50, 110, 100, 25);
        whitePanel.add(amountLabel);
        NumberFormat format = NumberFormat.getInstance();
        format.setGroupingUsed(false);
        NumberFormatter numberFormatter = new NumberFormatter(format);
        numberFormatter.setValueClass(Integer.class); // Specify type (Integer or Double)
        numberFormatter.setAllowsInvalid(false); // Prevent invalid input
        numberFormatter.setMinimum(0); // Set minimum value if needed
        numberFormatter.setMaximum(Integer.MAX_VALUE); // Set maximum value if needed

        // Create the JFormattedTextField with the NumberFormatter
        amountTextField = new JFormattedTextField(numberFormatter);
        amountTextField.setBounds(150, 110, 150, 25);
        whitePanel.add(amountTextField);

        // From Label and Dropdown
        JLabel fromLabel = new JLabel("FROM");
        fromLabel.setBounds(50, 150, 100, 25);
        whitePanel.add(fromLabel);

        fromDropdown = new JComboBox<>(usersList.toArray(new String[0]));
        fromDropdown.setBounds(150, 150, 150, 25);
        whitePanel.add(fromDropdown);

        // To Label and Checkboxes for Users (dynamically populated from the database)
        JLabel toLabel = new JLabel("TO");
        toLabel.setBounds(50, 190, 100, 25);
        whitePanel.add(toLabel);

        userCheckBoxes = new JCheckBox[usersList.size()];
        for (int i = 0; i < usersList.size(); i++) {
            userCheckBoxes[i] = new JCheckBox(usersList.get(i));
            userCheckBoxes[i].setBounds(150, 190 + (i * 30), 150, 25); // Position checkboxes dynamically
            whitePanel.add(userCheckBoxes[i]);
        }

        // Go Back Button
        goBackButton = new JButton("GO BACK");
        goBackButton.setBounds(50, 350, 120, 30);
        goBackButton.setSize(100, 40);
        goBackButton.setFont(new java.awt.Font("Helvetica Neue", 0, 18));
        goBackButton.setForeground(new java.awt.Color(204, 0, 0));;
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
        whitePanel.add(goBackButton);

        // Save Button
        saveButton = new JButton("SAVE");
        saveButton.setBounds(180, 350, 120, 30);
        saveButton.setSize(100, 40);
        saveButton.setFont(new java.awt.Font("Helvetica Neue", 0, 18));
        saveButton.setForeground(new Color(0, 102, 102));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSaveButton(); // Handle save button logic
            }
        });
        whitePanel.add(saveButton);

        // Create the right panel (green background)
        JPanel greenPanel = new JPanel();
        greenPanel.setBackground(new Color(0, 102, 102)); // Set background to green
        greenPanel.setPreferredSize(new Dimension(200, 500)); // Width is 200px for the green panel

        // Add white and green panels to the main panel
        mainPanel.add(whitePanel, BorderLayout.CENTER); // White panel in the center
        mainPanel.add(greenPanel, BorderLayout.EAST);   // Green panel on the right

        // Set the main panel as the content pane of the JFrame
        setContentPane(mainPanel);
        pack();
        setResizable(false); // Disable resizing
        setLocationRelativeTo(null); // Center the window on the screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
    
    public void handleSaveButton(){
        String category = categoryTextField.getText();
        String amountText =  amountTextField.getText(); // Get the numeric value
        String fromUser = (String) fromDropdown.getSelectedItem();
        List<String> toUsers = new ArrayList<>();
        
        for (JCheckBox checkBox : userCheckBoxes) {
            if (checkBox.isSelected()) {
                toUsers.add(checkBox.getText());
            }
        }

        // Validate inputs (you can add more validation as needed)
        if (category.isEmpty() || amountText == null || fromUser == null || toUsers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields and select at least one 'TO' user.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        double amount = Double.parseDouble(amountText);
        List<String> selectedUsers = new ArrayList<>();
        toUsers.remove(fromUser);
        
        if (toUsers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "The 'TO' user list must contain at least one user other than the 'FROM' user.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        for (JCheckBox checkBox : userCheckBoxes) {
            if (checkBox.isSelected()) {
                selectedUsers.add(checkBox.getText());
            }
        }
        selectedUsers.remove(fromUser);
        // Calculate share per user
        double sharePerUser = amount / (selectedUsers.size()+1);

        // Save to the database
        saveExpenseToDatabase(category, amount, fromUser, selectedUsers, sharePerUser);
    }
    
    private void saveExpenseToDatabase(String category, double amount, String fromUser, List<String> selectedUsers, double sharePerUser) {
        
        try (Connection connection = DatabaseConnection.getConnection()) {
            String expenseId = UUID.randomUUID().toString();
            String expenseQuery = "INSERT INTO expenses (expense_id, category, amount, from_user) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(expenseQuery)) {
                preparedStatement.setString(1, expenseId);
                preparedStatement.setString(2, category);
                preparedStatement.setDouble(3, amount);
                preparedStatement.setString(4, fromUser);
                preparedStatement.executeUpdate();
            }

            for (String user : selectedUsers) {
                String sqlToUser = "INSERT INTO expense_users (expense_id, user_name, share) VALUES (?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sqlToUser)) {
                    preparedStatement.setString(1, expenseId);
                    preparedStatement.setString(2, user);
                    preparedStatement.setDouble(3, sharePerUser);
                    preparedStatement.executeUpdate();
                }
            }

            JOptionPane.showMessageDialog(this, "Expense saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            MainPage MainPageFrame = new MainPage();
                MainPageFrame.setVisible(true);
                MainPageFrame.pack();
                MainPageFrame.setLocationRelativeTo(null);
                dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving expense: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane1 = new java.awt.ScrollPane();
        jOptionPane1 = new javax.swing.JOptionPane();
        jFrame1 = new javax.swing.JFrame();
        jLabel1 = new javax.swing.JLabel();
        AmountTextBox = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        SaveBtn = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        CategoryTextBox = new javax.swing.JTextField();
        FromDropDownMenu = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        GoBackBtn = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Grantha Sangam MN", 1, 18)); // NOI18N
        jLabel1.setText("AMOUNT");

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 102));
        jLabel2.setText("    EXPENSE");

        jLabel3.setFont(new java.awt.Font("Grantha Sangam MN", 1, 18)); // NOI18N
        jLabel3.setText("FROM");

        jLabel4.setFont(new java.awt.Font("Grantha Sangam MN", 1, 18)); // NOI18N
        jLabel4.setText("TO");

        SaveBtn.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        SaveBtn.setForeground(new java.awt.Color(0, 102, 102));
        SaveBtn.setText("SAVE");

        jLabel5.setFont(new java.awt.Font("Grantha Sangam MN", 1, 18)); // NOI18N
        jLabel5.setText("CATEGORY");

        FromDropDownMenu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sanjana", "Abhishek", "Rushikesh", "Samruddhi" }));

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 481, Short.MAX_VALUE)
        );

        GoBackBtn.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        GoBackBtn.setForeground(new java.awt.Color(204, 0, 0));
        GoBackBtn.setText("GO BACK");
        GoBackBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GoBackBtnActionPerformed(evt);
            }
        });

        jCheckBox1.setText("Abhishek");

        jCheckBox2.setText("Samruddhi");

        jCheckBox3.setText("Rushikesh");

        jCheckBox4.setText("Sanjana");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(AmountTextBox)
                                    .addComponent(FromDropDownMenu, 0, 188, Short.MAX_VALUE)
                                    .addComponent(CategoryTextBox))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCheckBox2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jCheckBox1, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                    .addComponent(jCheckBox3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jCheckBox4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(113, 113, 113))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(205, 205, 205)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(GoBackBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(SaveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel2)
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CategoryTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AmountTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FromDropDownMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jLabel4))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jCheckBox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox2, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 19, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 19, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SaveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(GoBackBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void GoBackBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GoBackBtnActionPerformed
         MainPage MainPageFrame = new MainPage();
         MainPageFrame.setVisible(true);
         MainPageFrame.pack();
         MainPageFrame.setLocationRelativeTo(null);
         this.dispose();
                 // TODO add your handling code here:
    }//GEN-LAST:event_GoBackBtnActionPerformed

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
            java.util.logging.Logger.getLogger(ExpensePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExpensePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExpensePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExpensePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ExpensePage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AmountTextBox;
    private javax.swing.JTextField CategoryTextBox;
    private javax.swing.JComboBox<String> FromDropDownMenu;
    private javax.swing.JButton GoBackBtn;
    private javax.swing.JButton SaveBtn;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JOptionPane jOptionPane1;
    private javax.swing.JPanel jPanel1;
    private java.awt.ScrollPane scrollPane1;
    // End of variables declaration//GEN-END:variables
}
