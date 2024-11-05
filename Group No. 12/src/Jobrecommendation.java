import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Jobrecommendation extends JFrame {
    
    private JTable jobTable;
    private JButton applyButton;
    private JButton saveButton;
    
    public Jobrecommendation() {
        setTitle("Job Recommendations");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create the header
        JLabel headerLabel = new JLabel("Recommended Jobs for You", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        // Create the table with job listings
        String[] columnNames = {"Job Title", "Company", "Location", "Skills", "Job Type", "Action"};
        Object[][] data = {
                {"Software Engineer", "Tech Corp", "New York", "Java, C++", "Full-time", "Apply"},
                {"Data Analyst", "Data Co", "San Francisco", "SQL, Python", "Internship", "Apply"},
                {"Mechanical Engineer", "Auto Ltd", "Detroit", "AutoCAD", "Full-time", "Apply"}
        };
        
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        jobTable = new JTable(model) {
            // Making the "Apply" button column non-editable
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }
        };
        
        // Adding a scroll pane for the table
        JScrollPane tableScrollPane = new JScrollPane(jobTable);
        
        // Create "Apply" button functionality
        applyButton = new JButton("Apply");
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = jobTable.getSelectedRow();
                if (selectedRow != -1) {
                    String jobTitle = (String) jobTable.getValueAt(selectedRow, 0);
                    JOptionPane.showMessageDialog(null, "You have applied for: " + jobTitle);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a job to apply.");
                }
            }
        });
        
        // Create "Save for Later" button functionality
        saveButton = new JButton("Save for Later");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = jobTable.getSelectedRow();
                if (selectedRow != -1) {
                    String jobTitle = (String) jobTable.getValueAt(selectedRow, 0);
                    JOptionPane.showMessageDialog(null, jobTitle + " has been saved for later.");
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a job to save.");
                }
            }
        });
        
        // Create a panel to hold buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(applyButton);
        buttonPanel.add(saveButton);
        
        // Add everything to the frame
        setLayout(new BorderLayout());
        add(headerLabel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Jobrecommendation jobPage = new Jobrecommendation();
            jobPage.setVisible(true);
        });
    }
}
