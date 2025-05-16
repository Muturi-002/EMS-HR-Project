package org.example.HR;

import org.example.Standard;
import org.example.LoadEnv;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.*;
import java.awt.event.*;
//import java.time.temporal.ChronoUnit;

public class Leaves extends Standard implements ActionListener{
    private int employeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;

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
    JLabel lblLeave= new JLabel("Leave Request Record");
    JTextField txtEmployeeId= new JTextField(20);
    JTextField txtDuration= new JTextField(2);
    JTextField txtStartDate= new JTextField(20);
    JTextField txtEndDate= new JTextField(20);
    JTextArea txtReason= new JTextArea(5, 30);
    JComboBox <String> chkStatus;
    JButton btnSubmitRequest = new JButton("Save Request");
    JButton btnExit = new JButton("Exit");
    JButton btnView= new JButton("View Available Requests");


    public Leaves() {
        setTitle("Leave Requests");

        leavePanel.setLayout(new GridLayout(4,1,20,10));
        timePanel.setLayout(new GridLayout(3,2,10,10));
        reasonPanel.setLayout(new GridLayout(1,2,10,10));
        statusPanel.setLayout(new GridLayout(1,2,10,10));

        lblLeave.setHorizontalAlignment(SwingConstants.CENTER);
        lblLeave.setFont(new Font("Arial", Font.BOLD, 15));

        txtReason.setLineWrap(true);
        txtReason.setWrapStyleWord(true);
        txtEmployeeId.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char i = e.getKeyChar();
                if (!Character.isDigit(i) && i != KeyEvent.VK_BACK_SPACE){
                    JOptionPane.showMessageDialog(null,"Enter a number only!");
                    e.consume();
                }
            }
        });
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
        txtEndDate.setEditable(false);
        txtDuration.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                calculateEndDate();
            }
        });

        btnExit.addActionListener(this);
        btnSubmitRequest.addActionListener(this);
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

        getUpperPanel().add(lblLeave);
        add(leavePanel, BorderLayout.CENTER);
        add(getLeftPanel(), BorderLayout.WEST);
        add(getRightPanel(), BorderLayout.EAST);
        getNavPanel().add(btnSubmitRequest);
        getNavPanel().add(btnView);
        getNavPanel().add(btnExit);
    }

    private void calculateEndDate() {
        try {
            int duration = Integer.parseInt(txtDuration.getText());
            startDate = LocalDate.parse(txtStartDate.getText());
            endDate = startDate.plusWeeks(duration);
            txtEndDate.setText(endDate.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid date or duration format.");
        }
    }
    private void submitRequest (){
        if (txtEmployeeId.getText().isEmpty() || txtStartDate.getText().isEmpty() || txtDuration.getText().isEmpty() || txtReason.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (txtStartDate.getText().length() != 10) {
            JOptionPane.showMessageDialog(null, "Invalid date format. Use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (txtDuration.getText().length() > 2 ) {
            if (Integer.parseInt(txtDuration.getText()) > 8) {
                JOptionPane.showMessageDialog(null, "Duration cannot exceed 8 weeks.", "Error", JOptionPane.ERROR_MESSAGE);
            }else {
                JOptionPane.showMessageDialog(null, "Invalid duration format. Use weeks only.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }else {
            employeeId = Integer.parseInt(txtEmployeeId.getText());
            reason= txtReason.getText();
            String status= (String) chkStatus.getSelectedItem();
            LocalDate endDate = LocalDate.parse(txtEndDate.getText());
            LocalDate startDate = LocalDate.parse(txtStartDate.getText());
            Connection conn;
            PreparedStatement stmt;
            Statement submit;
            String databaseUser = LoadEnv.getDatabaseUser();
            String databasePassword = LoadEnv.getDatabasePassword();
            String url = LoadEnv.getURL();
            String tnsAdmin = "/home/muturiiii/Desktop/Y3S2 Project/EMS-HR-Project/EMS/src/main/java/org/example/Wallet_EMS2";

            System.setProperty("oracle.net.tns_admin", tnsAdmin);

            try {
                conn = DriverManager.getConnection(url, databaseUser, databasePassword);
                String attend = "INSERT INTO LeaveRequests(EmployeeID,StartDate,EndDate,Reason,Status) VALUES (?,?,?,?,?)";
                stmt = conn.prepareStatement(attend);
                submit=conn.createStatement();
                ResultSet employeeExists= submit.executeQuery("SELECT * FROM Employees WHERE EmployeeID = "+employeeId);
                if (employeeExists.next()){
                    stmt.setInt(1, employeeId);
                    stmt.setObject(2, startDate);
                    stmt.setObject(3, endDate);
                    stmt.setString(4, reason);
                    stmt.setString(5, status);
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Request recorded successsfully.");
                }
                txtEmployeeId.setText("");
                txtStartDate.setText("");
                txtDuration.setText("");
                txtEndDate.setText("");
                txtReason.setText("");
                chkStatus.setSelectedIndex(0);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main (String [] args){
        SwingUtilities.invokeLater(() -> new Leaves());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==btnExit){
            dispose();
        }
        if (e.getSource()==btnSubmitRequest){
            submitRequest();
        }
        if (e.getSource()== btnView){
            dispose();
            new ViewLeaves();
        }
    }
}
