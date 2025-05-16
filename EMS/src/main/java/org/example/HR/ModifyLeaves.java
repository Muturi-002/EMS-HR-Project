package org.example.HR;

import org.example.Staff.SingleViewEmployees;
import org.example.Staff.TempStaffEntry;
import org.example.Staff.ViewEmployees;
import org.example.Standard;
import org.example.LoadEnv;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.*;
import java.awt.event.*;

public class ModifyLeaves extends Standard implements ActionListener{
    String databaseUser = LoadEnv.getDatabaseUser();
    String databasePassword = LoadEnv.getDatabasePassword();
    String url = LoadEnv.getURL();
    String tnsAdmin = "/home/muturiiii/Desktop/Y3S2 Project/EMS-HR-Project/EMS/src/main/java/org/example/Wallet_EMS2";

    JTextField txtDuration,txtEmployeeId,txtStartDate,txtEndDate;
    JTextArea txtReason;
    JComboBox <String> chkStatus;
    JButton btnBack,btnEnableEdit,btnDelete,btnNew,btnSave,btnSearch;
    public ModifyLeaves(){
        System.setProperty("oracle.net.tns_admin", tnsAdmin);
        setTitle("Modify Leave Requests");
        JPanel leavePanel = new JPanel();
        JPanel empPanel = new JPanel();
        JPanel timePanel = new JPanel();
        JPanel reasonPanel = new JPanel();
        JPanel statusPanel = new JPanel();
        JLabel lblEmployee= new JLabel("Employee ID");
        JLabel lblStartDate= new JLabel("Start Date (YYYY-MM-DD format)");
        JLabel lblDuration= new JLabel("Duration in weeks");
        JLabel lblEndDate= new JLabel("End Date (YYYY-MM-DD format)");
        JLabel lblReason= new JLabel("Reason");
        JLabel lblStatus= new JLabel("Request Status");
        txtEmployeeId= new JTextField(20);
        txtDuration= new JTextField(2);
        txtDuration.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char i = e.getKeyChar();
                if (!Character.isDigit(i) && i != KeyEvent.VK_BACK_SPACE){
                    JOptionPane.showMessageDialog(null,"Enter a valid number of weeks!");
                    e.consume();
                }
            }
        });
        txtDuration.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                calculateEndDate();
            }
        });
        txtStartDate= new JTextField(20);
        txtEndDate= new JTextField(20);
        txtReason= new JTextArea(5, 30);

        leavePanel.setLayout(new GridLayout(4,1,20,10));
        timePanel.setLayout(new GridLayout(3,2,20,10));
        reasonPanel.setLayout(new GridLayout(1,2,10,10));
        statusPanel.setLayout(new GridLayout(1,2,10,10));
        empPanel.add(lblEmployee); empPanel.add(txtEmployeeId);
        timePanel.add(lblStartDate); timePanel.add(txtStartDate);
        timePanel.add(lblDuration); timePanel.add(txtDuration);
        timePanel.add(lblEndDate); timePanel.add(txtEndDate);
        reasonPanel.add(lblReason); reasonPanel.add(txtReason);
        statusPanel.add(lblStatus); statusPanel.add(chkStatus= new JComboBox<>(new String[]{"Pending", "Approved", "Rejected"}));
        leavePanel.add(empPanel);
        leavePanel.add(timePanel);
        leavePanel.add(reasonPanel);
        leavePanel.add(statusPanel);
        getNavPanel().add(btnBack=new JButton("Back to Requests View Page"));
        getNavPanel().add(btnSearch= new JButton("Search Leave Request"));
        getNavPanel().add(btnEnableEdit=new JButton("Enable Edit"));
        getNavPanel().add(btnNew=new JButton("Add New Leave Request"));
        getNavPanel().add(btnDelete= new JButton("Delete Leave Request"));
        add(leavePanel, BorderLayout.CENTER);

        txtReason.setEditable(false);
        txtDuration.setEditable(false);
        txtStartDate.setEditable(false);
        txtEndDate.setEditable(false);
        chkStatus.setEnabled(false);

        btnBack.addActionListener(this);
        btnSearch.addActionListener(this);
        btnEnableEdit.addActionListener(this);
        btnDelete.addActionListener(this);
        btnNew.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) {
            dispose();
        } else if (e.getSource()==btnSearch){
            getRecords();
        }else if (e.getSource() == btnEnableEdit) {
            editEnabled();
        } else if (e.getSource() == btnNew) {
            dispose();
            new Leaves();
        } else if (e.getSource() == btnDelete) {
            try (Connection conn = DriverManager.getConnection(url, databaseUser, databasePassword);
                 PreparedStatement pstmt = conn.prepareStatement("DELETE FROM LeaveRequests WHERE EmployeeID=?")) {
                pstmt.setInt(1, Integer.parseInt(txtEmployeeId.getText()));
                int rowsDeleted = pstmt.executeUpdate();
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(null, "Record deleted successfully.");
                    dispose();
                    new ViewEmployees();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to delete record.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }else if (e.getSource()==btnSave) {
            try (Connection conn = DriverManager.getConnection(url, databaseUser, databasePassword);
                 PreparedStatement pstmt = conn.prepareStatement("UPDATE LeaveRequests SET EmployeeID=?, StartDate=?, EndDate=?, Reason=?, Status=? WHERE EmployeeID=?")) {
                pstmt.setString(1, txtEmployeeId.getText());
                pstmt.setDate(2, Date.valueOf(txtStartDate.getText()));
                pstmt.setDate(3, Date.valueOf(txtEndDate.getText()));
                pstmt.setObject(4, txtReason.getText());
                pstmt.setString(11, chkStatus.getSelectedItem().toString());
                pstmt.setInt(12, Integer.parseInt(txtEmployeeId.getText()));

                int rowsUpdated = pstmt.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Record updated successfully.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to update record.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    private void getRecords() {
        try (
                Connection conn = DriverManager.getConnection(url, databaseUser, databasePassword);
                Statement stmt = conn.createStatement();
        ) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM LeaveRequests WHERE EmployeeID = " + txtEmployeeId.getText());
            if (rs.next()) {
                txtStartDate.setText(String.valueOf(rs.getDate("StartDate")));
                txtEndDate.setText(String.valueOf(rs.getDate("EndDate")));
                txtReason.setText(rs.getString("Reason"));
                String status = rs.getString("Status");
                chkStatus.setSelectedItem(status);

            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Leave Request record of Employee-ID "+txtEmployeeId.getText()+" does not exist.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void editEnabled(){
        txtEmployeeId.setEditable(false);
        txtStartDate.setEditable(true);
        txtDuration.setEditable(true);
        txtReason.setEditable(true);
        chkStatus.setEnabled(true);
    }
    private void calculateEndDate() {
        LocalDate startDate;
        LocalDate endDate;
        try {
            int duration = Integer.parseInt(txtDuration.getText());
            startDate = LocalDate.parse(txtStartDate.getText());
            endDate = startDate.plusWeeks(duration);
            txtEndDate.setText(endDate.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid date or duration format.");
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ModifyLeaves());
    }
}