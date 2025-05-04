package org.example.Staff;

import org.example.Home;
import org.example.Standard;
import org.example.DB.Database;
import org.example.LoadEnv;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;


public class ViewTempStaff extends Standard {
    String ipAddress = LoadEnv.getIP();
    String port = LoadEnv.getPort();
    String databaseUser = LoadEnv.getDatabaseUser();
    String databasePassword = LoadEnv.getDatabasePassword();
    String databaseName = LoadEnv.getDatabaseName();
    String url= "jdbc:mysql://"+ipAddress+":"+port+"/"+databaseName;

    private JTable tempTable;

    public ViewTempStaff() {
        setTitle("View Temporary Staff's Records");
        // Table Columns
        String[] columnNames = {"ID", "First Name", "Middle Name", "Last Name", "Work Level", "Year of Birth", "National ID", "Address", "Disabilities", "KRA PIN", "Department Division"};

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        tempTable = new JTable(model);
        JPanel buttonPanel= getNavPanel();
        JButton btnExit = new JButton("Back to Homepage");
        JButton btnSingleRecord= new JButton("View Each Record");
        btnSingleRecord.addActionListener(e -> new SingleViewTemp());

        int totalColumnWidth = 0;
        for (int i = 0; i < tempTable.getColumnCount(); i++) {
            tempTable.getColumnModel().getColumn(i).setPreferredWidth(250); // Set a reasonable width
            totalColumnWidth += tempTable.getColumnModel().getColumn(i).getPreferredWidth();
        }

        // Tell the scroll pane the preferred size to display the table's viewport
        tempTable.setPreferredScrollableViewportSize(new Dimension(totalColumnWidth, tempTable.getPreferredSize().height));

        JScrollPane scrollPane = new JScrollPane(tempTable,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        btnExit.addActionListener(e -> {
            dispose();
            new Home();
        });
        buttonPanel.add(btnExit);
        buttonPanel.add(btnSingleRecord);
        add(buttonPanel, BorderLayout.SOUTH);
        add(getUpperPanel(),BorderLayout.NORTH);

        // Load data from the database
        loadDataFromDatabase(model);

        setVisible(true);
    }

    private void loadDataFromDatabase(DefaultTableModel model) {
        // Database connection details
        try (
            Connection conn = DriverManager.getConnection(url, databaseUser, databasePassword);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Temporary")) {

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("TempID"),
                        rs.getString("FirstName"),
                        rs.getString("MiddleName"),
                        rs.getString("LastName"),
                        rs.getString("WorkLevel"),
                        rs.getDate("YearOfBirth"),
                        rs.getString("NationalIDNo"),
                        rs.getString("PhysicalAddress"),
                        rs.getString("Disabilities"),
                        rs.getString("KRAPIN"),
                        rs.getInt("DepartmentDivision")
                };
                model.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data from database.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ViewTempStaff());
    }
}