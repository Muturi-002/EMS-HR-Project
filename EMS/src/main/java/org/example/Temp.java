package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class Temp extends JFrame {

    private JTable tempTable;

    public Temp() {
        setTitle("Temporary Staff Records");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);

        // Table Columns
        String[] columnNames = {"ID", "Name", "Position", "Department", "Type"};

        // Updated Sample Data (Only Temporary Staff)
        Object[][] data = {
            {"T001", "Bob", "Intern", "HR", "Temporary"},
            {"T002", "David", "Attache", "Finance", "Temporary"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        tempTable = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(tempTable);

        getContentPane().add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Temp());
    }
}
