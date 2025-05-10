package org.example.HR;

import org.example.LoadEnv;
import org.example.Standard;
import org.example.DB.Database;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.*;
import java.awt.event.*;

public class Attendance extends Standard implements ActionListener{
    JPanel checkInPanel = new JPanel();
    JPanel empPanel= new JPanel();
    JPanel timePanel= new JPanel();
    JLabel lblIdentification = new JLabel("Record Your Attendance");
    JLabel lblCheckIn = new JLabel("Check-In Time");
    JLabel lblEmployeeId = new JLabel("Employee ID");
    JTextField txtCheckIn = new JTextField(20);
    JTextField txtEmployeeId = new JTextField(4);
    JButton btnCheckIn = new JButton("Check In");
    JButton btnExit = new JButton("Exit");

    private int employeeId;
    private boolean isPresent= false;
    private LocalTime checkInTime = LocalTime.now();

    // Constructor
    public Attendance() {
        setTitle("Attendance System");

        lblIdentification.setHorizontalAlignment(SwingConstants.CENTER);
        lblIdentification.setFont(new Font("Arial", Font.BOLD, 15));

        txtCheckIn.setEditable(false);
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

        checkInPanel.setLayout(new GridLayout(3,1,10,10));
        empPanel.add(lblEmployeeId);
        empPanel.add(txtEmployeeId);
        timePanel.add(lblCheckIn);
        timePanel.add(txtCheckIn);

        checkInPanel.add(lblIdentification);
        checkInPanel.add(empPanel);
        checkInPanel.add(timePanel);
        add(checkInPanel, BorderLayout.CENTER);
        add(getLeftPanel(), BorderLayout.WEST);
        add(getRightPanel(), BorderLayout.EAST);
        getNavPanel().add(btnCheckIn);
        getNavPanel().add(btnExit);
        btnCheckIn.addActionListener(this);
        btnExit.addActionListener(this);
    }

    // Methods to mark check-in and check-out
    public void markAttendance(LocalTime checkIn) {
        if (checkIn.isAfter(LocalTime.of(8, 0))) {
            System.out.println("Warning: Outside standard time range (8 AM - 5 PM)");
        }else {
            isPresent = true;
            employeeId = Integer.parseInt(txtEmployeeId.getText());
            Connection conn;
            PreparedStatement stmt;
            Statement checkEmployee;
            String databaseUser = LoadEnv.getDatabaseUser();
            String databasePassword = LoadEnv.getDatabasePassword();
            String url = LoadEnv.getURL();
            String tnsAdmin = "/home/muturiiii/Desktop/Y3S2 Project/EMS-HR-Project/EMS/src/main/java/org/example/Wallet_EMS2";

            System.setProperty("oracle.net.tns_admin", tnsAdmin);

            try {
                conn = DriverManager.getConnection(url, databaseUser, databasePassword);
                String attend = "INSERT INTO Attendance(EmployeeID,AttendanceDate,Status) VALUES (?,?,?)";
                stmt = conn.prepareStatement(attend);
                checkEmployee=conn.createStatement();
                ResultSet employeeExists= checkEmployee.executeQuery("SELECT * FROM Employees WHERE EmployeeID = "+employeeId);
                if (employeeExists.next()){
                    stmt.setInt(1, employeeId);
                    stmt.setObject(2, LocalDate.now());
                    stmt.setString(3, "Present");
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Attendance recorded successsfully.");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Getters
    public LocalTime getCheckInTime() {
        return checkInTime;
    }
    public static void main (String [] args){
        SwingUtilities.invokeLater(() -> new Attendance());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCheckIn){
            markAttendance(checkInTime);
        }else if (e.getSource()==btnExit){
            dispose();
        }
    }
}

