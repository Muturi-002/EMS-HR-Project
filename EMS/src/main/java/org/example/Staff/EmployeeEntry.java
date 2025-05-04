package org.example.Staff;

import org.example.LoadEnv;
import org.example.Standard;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EmployeeEntry extends Standard{

    private JLabel lblFirstName, lblMiddleName, lblLastName, lblNationalId, lblEmail,lblAddress, lblKraPin, lblDepartmentDivision, lblYearOfBirth, lblDisabilities, lblStatus;
    private JTextField firstNameField, middleNameField, lastNameField, nationalIdField, emailField,addressField, kraPinField, departmentDivisionField, yearOfBirthField;
    private JComboBox<String> disabilitiesCombo, statusCombo;
    private JButton btnSave, btnClear,btnExit;
    //Date formatter
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    //Database connection
    static Connection conn ;
    Statement stmt;
    String ipAddress = LoadEnv.getIP();
    String port = LoadEnv.getPort();
    String databaseUser = LoadEnv.getDatabaseUser();
    String databasePassword = LoadEnv.getDatabasePassword();
    String databaseName = LoadEnv.getDatabaseName();
    String url= "jdbc:mysql://"+ipAddress+":"+port+"/"+databaseName;

    public EmployeeEntry() {
        setTitle("Add a New/Existing Employee");

        JPanel panel = new JPanel();
        JPanel navPanel = getNavPanel();
        panel.setLayout(new GridLayout(11, 2, 10, 10));

        lblFirstName = new JLabel("First Name:");
        lblLastName = new JLabel("Last Name:");
        lblMiddleName = new JLabel("Middle Name:");
        lblYearOfBirth = new JLabel("Year of Birth (YYYY-MM-DD format):");
        lblNationalId = new JLabel("National ID No:");
        lblEmail = new JLabel("Email Address:");
        lblAddress = new JLabel("Physical Address:");
        lblKraPin = new JLabel("KRA PIN:");
        lblDepartmentDivision = new JLabel("Department-Division:");
        lblDisabilities = new JLabel("Disabilities (if any):");
        lblStatus= new JLabel("Employment Status");

        firstNameField= new JTextField();
        lastNameField = new JTextField();
        middleNameField = new JTextField();
        yearOfBirthField = new JTextField();
        nationalIdField = new JTextField();
        emailField = new JTextField();
        addressField = new JTextField();
        kraPinField = new JTextField();
        departmentDivisionField = new JTextField();
        String[] disab = {"Yes", "No"};
        disabilitiesCombo = new JComboBox<>(disab);
        String[] status = {"Active", "Inactive"};
        statusCombo = new JComboBox<>(status);

        btnSave = new JButton("Save");
        btnClear = new JButton("Clear");
        btnExit = new JButton("Exit");

        panel.add(lblFirstName); panel.add(firstNameField);
        panel.add(lblMiddleName); panel.add(middleNameField);
        panel.add(lblLastName); panel.add(lastNameField);
        panel.add(lblYearOfBirth); panel.add(yearOfBirthField);
        panel.add(lblNationalId); panel.add(nationalIdField);
        panel.add(lblEmail); panel.add(emailField);
        panel.add(lblAddress); panel.add(addressField);
        panel.add(lblKraPin); panel.add(kraPinField);
        panel.add(lblDepartmentDivision); panel.add(departmentDivisionField);
        panel.add(lblDisabilities); panel.add(disabilitiesCombo);
        panel.add(lblStatus); panel.add(statusCombo);

        navPanel.add(btnSave);
        navPanel.add(btnClear);
        navPanel.add(btnExit);

        // Add panel to the frame
        add(getUpperPanel(),BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(navPanel,BorderLayout.SOUTH);

        // Add action listener for the Save button
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveEmployee();

            }
        });

        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void saveEmployee() {
        String fName = firstNameField.getText().toUpperCase();
        String mName = middleNameField.getText().toUpperCase();
        String lName = lastNameField.getText().toUpperCase();
        String yearBirth = yearOfBirthField.getText();
        String formattedDate = LocalDate.parse(yearBirth, formatter).toString();
        String nationalId = nationalIdField.getText();
        String emailAddress = emailField.getText().toLowerCase();
        String address = addressField.getText().toUpperCase();
        String kraPin = kraPinField.getText().toUpperCase();
        String departmentDivision = departmentDivisionField.getText();
        String disabilities = (String) disabilitiesCombo.getSelectedItem();
        String status = (String) statusCombo.getSelectedItem();


        if (fName.isEmpty() || mName.isEmpty() || lName.isEmpty() || yearBirth.isEmpty() || nationalId.isEmpty() || address.isEmpty() || kraPin.isEmpty() || departmentDivision.isEmpty() || disabilities.isEmpty() || emailAddress.isEmpty() || status.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Saving info to the database
            try {
                conn = DriverManager.getConnection(url, databaseUser, databasePassword);
                String sql = "INSERT INTO Employees (FirstName, MiddleName, LastName, YearOfBirth, NationalIDNo, EmailAddress, PhysicalAddress, KRAPIN, DepartmentDivision, Disabilities, Status) " +
                        "VALUES (?,?,?,?,?,?,?,?,?,?,?);";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, fName);
                pstmt.setString(2, mName);
                pstmt.setString(3, lName);
                pstmt.setString(4, formattedDate);
                pstmt.setString(5, nationalId);
                pstmt.setString(6, emailAddress);
                pstmt.setString(7, address);
                pstmt.setString(8, kraPin);
                pstmt.setString(9, departmentDivision);
                pstmt.setString(10, disabilities);
                pstmt.setString(11, status);

                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Employee record saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    private void clearFields() {
        firstNameField.setText("");
        middleNameField.setText("");
        lastNameField.setText("");
        yearOfBirthField.setText("");
        nationalIdField.setText("");
        addressField.setText("");
        kraPinField.setText("");
        departmentDivisionField.setText("");
        disabilitiesCombo.setSelectedIndex(0); // Reset to "No"
    }

    public static void main(String[] args) {
        // Run the GUI on the Event Dispatch Thread for thread safety
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EmployeeEntry();
            }
        });
    }

}
