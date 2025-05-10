package org.example.Staff;

import org.example.Home;
import org.example.Standard;
import org.example.LoadEnv;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class ViewTempStaff extends Standard {
    String databaseUser = LoadEnv.getDatabaseUser();
    String databasePassword = LoadEnv.getDatabasePassword();
    String url = LoadEnv.getURL();
    String tnsAdmin = "/home/muturiiii/Desktop/Y3S2 Project/EMS-HR-Project/EMS/src/main/java/org/example/Wallet_EMS2";

    private JTable tempTable;

    public ViewTempStaff() {
        System.setProperty("oracle.net.tns_admin", tnsAdmin);
        setTitle("View Temporary Staff's Records");

        String[] columnNames = {"ID", "First Name", "Middle Name", "Last Name", "Work Level", "Year of Birth", "National ID", "Address", "Disabilities", "KRA PIN", "Department Division"};

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        tempTable = new JTable(model);
        JPanel buttonPanel= getNavPanel();
        JButton btnExit = new JButton("Back to Homepage");
        JButton btnSingleRecord= new JButton("View Each Record");
        JButton btnNew;
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
        });
        btnNew= new JButton("Add new Intern/Attache");navPanel.add(btnNew);
        btnNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new TempStaffEntry();
            }
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
                ResultSet rs = stmt.executeQuery("SELECT * FROM Temporary ORDER BY TempID")) {

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("TempID"),
                        rs.getString("FirstName"),
                        rs.getString("MiddleName"),
                        rs.getString("LastName"),
                        rs.getString("WorkLevel"),
                        rs.getDate("YearOfBirth"),
                        rs.getString("NationalIDNo"),
                        rs.getString("EmailAddress"),
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
