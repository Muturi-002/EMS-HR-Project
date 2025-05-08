package org.example.Staff;

import org.example.Home;
import org.example.LoadEnv;
import org.example.Standard;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ViewEmployees extends Standard {
    private JTable employeeTable;
    private JButton tempStaffButton,btnExit,btnSingleRecord,btnNew;
    String databaseUser = LoadEnv.getDatabaseUser();
    String databasePassword = LoadEnv.getDatabasePassword();
    String url = LoadEnv.getURL();
    String tnsAdmin = "/home/muturiiii/Desktop/Y3S2 Project/EMS-HR-Project/EMS/src/main/java/org/example/Wallet_EMS2";

    public ViewEmployees() {
        System.setProperty("oracle.net.tns_admin", tnsAdmin);

        setTitle("View Employee Records");

        // Table Column Names - replace/add as per your Employees table
        String[] columnNames = {"Employee ID", "First Name", "Middle Name", "Last Name", "Year of Birth", "National ID",
                "Email Address", "Physical Address", "Disabilities", "KRA PIN", "Department Division", "Status"};

        // Table Model
        DefaultTableModel model = new DefaultTableModel(columnNames, 0); // 0 rows initially
        employeeTable = new JTable(model);

        int totalColumnWidth = 0;
        for (int i = 0; i < employeeTable.getColumnCount(); i++) {
            employeeTable.getColumnModel().getColumn(i).setPreferredWidth(250); // Set a reasonable width
            totalColumnWidth += employeeTable.getColumnModel().getColumn(i).getPreferredWidth();
        }

        // Tell the scroll pane the preferred size to display the table's viewport
        employeeTable.setPreferredScrollableViewportSize(new Dimension(totalColumnWidth, employeeTable.getPreferredSize().height));

        JScrollPane scrollPane = new JScrollPane(employeeTable,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        tempStaffButton = new JButton("Check Temporary Staff");
        tempStaffButton.addActionListener(e -> new ViewTempStaff());
        btnSingleRecord= new JButton("View Each Record");
        btnSingleRecord.addActionListener(e -> new SingleViewEmployees());
        btnExit= new JButton("Back to Homepage");
        btnExit.addActionListener(e -> {
            dispose();
            new Home();
        });
        btnNew= new JButton("Add New Record");navPanel.add(btnNew);
        btnNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EmployeeEntry();
            }
        });

        // Layout
        JPanel buttonPanel = getNavPanel();
        buttonPanel.add(tempStaffButton);
        buttonPanel.add(btnSingleRecord);
        buttonPanel.add(btnExit);

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(getUpperPanel(),BorderLayout.NORTH);

        // Load data from the database
        loadDataFromDatabase(model);

        setVisible(true);
    }

    private void loadDataFromDatabase(DefaultTableModel model) {

        try (
                Connection conn = DriverManager.getConnection(url, databaseUser, databasePassword);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM Employees")) { // Assuming table name is "Employees"

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
