package org.example.HR;

import org.example.Staff.EmployeeEntry;
import org.example.Staff.SingleViewEmployees;
import org.example.Staff.ViewEmployees;
import org.example.Staff.ViewTempStaff;
import org.example.Standard;
import org.example.LoadEnv;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.*;
import java.awt.event.*;

public class ViewLeaves extends Standard implements ActionListener{
    private JTable leavesTable;
    private JButton btnExit,btnSingleRecord,btnNew;
    String databaseUser = LoadEnv.getDatabaseUser();
    String databasePassword = LoadEnv.getDatabasePassword();
    String url = LoadEnv.getURL();
    String tnsAdmin = "/home/muturiiii/Desktop/Y3S2 Project/EMS-HR-Project/EMS/src/main/java/org/example/Wallet_EMS2";
    public ViewLeaves(){
        System.setProperty("oracle.net.tns_admin", tnsAdmin);

        setTitle("View Applied Leave Requests");

        String[] columnNames = {"Leave ID", "Employee Number", "Start Date", "End Date", "Reason", "Status"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0); // 0 rows initially
        leavesTable = new JTable(model);
        int totalColumnWidth = 0;
        for (int i = 0; i < leavesTable.getColumnCount(); i++) {
            leavesTable.getColumnModel().getColumn(i).setPreferredWidth(250); // Set a reasonable width
            totalColumnWidth += leavesTable.getColumnModel().getColumn(i).getPreferredWidth();
        }

        // Tell the scroll pane the preferred size to display the table's viewport
        leavesTable.setPreferredScrollableViewportSize(new Dimension(totalColumnWidth, leavesTable.getPreferredSize().height));

        JScrollPane scrollPane = new JScrollPane(leavesTable,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        btnSingleRecord= new JButton("View Each Record");
        btnSingleRecord.addActionListener(this);
        btnExit= new JButton("Back to Homepage");
        btnExit.addActionListener(this);
        btnNew= new JButton("Add New Leave Request");navPanel.add(btnNew);
        btnNew.addActionListener(this);

        JPanel buttonPanel = getNavPanel();
        buttonPanel.add(btnSingleRecord);
        buttonPanel.add(btnExit);

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(getUpperPanel(),BorderLayout.NORTH);

        loadDataFromDatabase(model);
    }
    private void loadDataFromDatabase(DefaultTableModel model) {
        try (
                Connection conn = DriverManager.getConnection(url, databaseUser, databasePassword);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM LeaveRequests ORDER BY LeaveID")) {
            while (rs.next()) {
                Object[] row = {
                        rs.getInt("LeaveID"),
                        rs.getString("EmployeeID"),
                        rs.getObject("StartDate"),
                        rs.getObject("EndDate"),
                        rs.getString("Reason"),
                        rs.getString("Status")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data from database.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==btnExit){
            dispose();
        }
        if (e.getSource()==btnSingleRecord){
            dispose();
            new ModifyLeaves();
        }
        if (e.getSource()==btnNew){
            dispose();
            new Leaves();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ViewLeaves());
    }
}