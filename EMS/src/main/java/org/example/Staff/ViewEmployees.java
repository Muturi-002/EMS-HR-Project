package org.example.Staff;

import org.example.Standard;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class ViewEmployees extends Standard {

    private JTable employeeTable;
    private JButton tempStaffButton;

    public ViewEmployees() {
        setTitle("View Employee Records");


        // Table Column Names - replace/add as per your Employees table
        String[] columnNames = {"ID", "Name", "Position", "Department", "Type"};

        // Sample Data - replace with actual DB data
        Object[][] data = {
            {1, "Alice", "Analyst", "IT", "Full-Time"},
            {2, "Bob", "Intern", "HR", "Temporary"},
            {3, "Charlie", "Engineer", "R&D", "Full-Time"},
            {4, "David", "Attache", "Finance", "Temporary"}
        };

        // Table Model
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        employeeTable = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(employeeTable);

        // Button to filter temporary staff
        tempStaffButton = new JButton("Check Temporary Staff");
        tempStaffButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                filterTemporaryStaff(model, columnNames, data);
            }
        });

        // Layout
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(tempStaffButton);

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void filterTemporaryStaff(DefaultTableModel model, String[] columnNames, Object[][] allData) {
        model.setRowCount(0); // Clear table
        for (Object[] row : allData) {
            String type = row[4].toString();
            if (type.equalsIgnoreCase("Temporary")) {
                model.addRow(row);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ViewEmployees());
    }
}

