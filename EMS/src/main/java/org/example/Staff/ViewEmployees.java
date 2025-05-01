package org.example.Staff;

import org.example.LoadEnv;
import org.example.Standard;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.View;
import java.awt.*;
import java.sql.*;

public class ViewEmployees extends Standard {

    private JTable employeeTable;
    private JButton tempStaffButton;
    String ipAddress = LoadEnv.getIP();
    String port = LoadEnv.getPort();
    String databaseUser = LoadEnv.getDatabaseUser();
    String databasePassword = LoadEnv.getDatabasePassword();
    String databaseName = LoadEnv.getDatabaseName();
    String url= "jdbc:mysql://"+ipAddress+":"+port;

    public ViewEmployees() {
        setTitle("View Employee Records");

        // Table Column Names - replace/add as per your Employees table
        String[] columnNames = {"Employee ID", "First Name", "Middle Name", "Last Name", "Year of Birth", "National ID",
                "Email Address", "Physical Address", "Disabilities", "KRA PIN", "Department Division", "Branch", "Status"};

        // Table Model
        DefaultTableModel model = new DefaultTableModel(columnNames, 0); // 0 rows initially
        employeeTable = new JTable(model);

        int totalColumnWidth = 0;
        for (int i = 0; i < employeeTable.getColumnCount(); i++) {
            employeeTable.getColumnModel().getColumn(i).setPreferredWidth(200); // Set a reasonable width
            totalColumnWidth += employeeTable.getColumnModel().getColumn(i).getPreferredWidth();
        }

        // Tell the scroll pane the preferred size to display the table's viewport
        employeeTable.setPreferredScrollableViewportSize(new Dimension(totalColumnWidth, employeeTable.getPreferredSize().height));

        JScrollPane scrollPane = new JScrollPane(employeeTable,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        tempStaffButton = new JButton("Check Temporary Staff");
        tempStaffButton.addActionListener(e -> new ViewTempStaff());

        // Layout
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(tempStaffButton);

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Load data from the database
        loadDataFromDatabase(model);

        setVisible(true);
    }

    private void loadDataFromDatabase(DefaultTableModel model) {

        try (
            Connection conn = DriverManager.getConnection(url, databaseUser, databasePassword);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM "+databaseName+".Employees")) { // Assuming table name is "Employees"

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("EmployeeID"),
                        rs.getString("FirstName"),
                        rs.getString("MiddleName"),
                        rs.getString("LastName"),
                        rs.getDate("YearOfBirth"),
                        rs.getString("NationalIDNo"),
                        rs.getString("EmailAddress"),
                        rs.getString("PhysicalAddress"),
                        rs.getBoolean("Disabilities"),
                        rs.getString("KRAPIN"),
                        rs.getInt("DepartmentDivision"),
                        rs.getString("Branch"),
                        rs.getString("Status")
                };
                model.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data from database.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ViewEmployees());
    }
}