package org.example.Staff;

import org.example.LoadEnv;
import org.example.Standard;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.*;

public class TempStaffEntry extends Standard implements ActionListener {

    private JTextField firstNameField, middleNameField, lastNameField, nationalIdField, emailField,addressField, kraPinField, departmentDivisionField, yearOfBirthField;
    private JComboBox<String> workLevelCombo, disabilitiesCombo;
    private JLabel kraPinLabel;
    private JButton saveButton,btnexit;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Database credentials (replace with your actual credentials)
    static Connection conn ;
    Statement stmt;
    String ipAddress = LoadEnv.getIP();
    String port = LoadEnv.getPort();
    String databaseUser = LoadEnv.getDatabaseUser();
    String databasePassword = LoadEnv.getDatabasePassword();
    String databaseName = LoadEnv.getDatabaseName();
    String url= "jdbc:mysql://"+ipAddress+":"+port+"/"+databaseName;

    public TempStaffEntry() {
        setTitle("Temporary Staff Registration");
        setLayout(new BorderLayout(20,20));
        JPanel panel = new JPanel();
        JPanel navPanel = getNavPanel();
        panel.setLayout(new GridLayout(12, 2, 10, 10)); // Increased rows for disabilities

        firstNameField = new JTextField();
        middleNameField = new JTextField();
        lastNameField = new JTextField();
        String[] workLevels = {"INTERN", "ATTACHE"};
        workLevelCombo = new JComboBox<>(workLevels);
        yearOfBirthField = new JTextField();
        nationalIdField = new JTextField();
        emailField=new JTextField();
        String[] disabilitiesOptions = {"NO", "YES"};
        disabilitiesCombo = new JComboBox<>(disabilitiesOptions);
        kraPinField = new JTextField();
        departmentDivisionField = new JTextField();

        panel.add(new JLabel("First Name:"));
        panel.add(firstNameField);
        panel.add(new JLabel("Middle Name:"));
        panel.add(middleNameField);
        panel.add(new JLabel("Last Name:"));
        panel.add(lastNameField);
        panel.add(new JLabel("Work Level (Intern/Attache):"));
        workLevelCombo.addActionListener(this);
        panel.add(workLevelCombo);
        panel.add(new JLabel("Year of Birth (YYYY-MM-DD):")); // Updated format
        panel.add(yearOfBirthField);
        panel.add(new JLabel("National ID No:"));
        panel.add(nationalIdField);
        panel.add(new JLabel("Email Address:"));;
        panel.add(emailField);
        panel.add(new JLabel("Physical Address:"));
        addressField = new JTextField();
        panel.add(addressField);
        panel.add(new JLabel("Disabilities :"));
        panel.add(disabilitiesCombo);
        kraPinLabel = new JLabel("KRA PIN (Interns only):");
        panel.add(kraPinLabel);
        panel.add(kraPinField);
        panel.add(new JLabel("Department-Division ID:"));
        panel.add(departmentDivisionField);
        saveButton = new JButton("Save Record");
        saveButton.addActionListener(this);
        navPanel.add(saveButton);
        navPanel.add(btnexit = new JButton("Exit"));
        btnexit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        updateKraPinVisibility();
        add(getUpperPanel(),BorderLayout.NORTH);
        add(panel,BorderLayout.CENTER);
        add(getLeftPanel(),BorderLayout.WEST);
        add(getRightPanel(),BorderLayout.EAST);
        add(navPanel,BorderLayout.SOUTH);
    }

    private void updateKraPinVisibility() {
        boolean isIntern = workLevelCombo.getSelectedItem().equals("Intern");
        kraPinLabel.setVisible(isIntern);
        kraPinField.setVisible(isIntern);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == workLevelCombo) {
            updateKraPinVisibility();
        } else if (e.getSource() == saveButton) {
            String firstName = firstNameField.getText().trim().toUpperCase();
            String middleName = middleNameField.getText().trim().toUpperCase();
            String lastName = lastNameField.getText().trim().toUpperCase();
            String workLevel = (String) workLevelCombo.getSelectedItem();
            String yearOfBirthText = yearOfBirthField.getText().trim();
            String formattedDate = LocalDate.parse(yearOfBirthText, formatter).toString();
            String nationalId = nationalIdField.getText().trim();
            String emailAddress= emailField.getText().trim().toLowerCase();
            String address = addressField.getText().trim().toUpperCase();
            String disabilities = (String) disabilitiesCombo.getSelectedItem(); // Get from combo box
            String kraPin = kraPinField.getText().trim().toLowerCase();
            String departmentDivisionText = departmentDivisionField.getText().trim();

            // Basic Validation
            if (firstName.isEmpty() || lastName.isEmpty() || formattedDate.isEmpty() || nationalId.isEmpty() || departmentDivisionText.isEmpty() || emailAddress.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all required fields (First Name, Last Name, Year of Birth, National ID, Email Address, Department-Division).", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            LocalDate yearOfBirth;
            try {
                 yearOfBirth = LocalDate.parse(yearOfBirthText, DateTimeFormatter.ISO_DATE); // Use LocalDate
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Year of Birth. Please enter a valid date in YYYY-MM-DD format.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (workLevel.equals("Intern") && kraPin.isEmpty()) {
                JOptionPane.showMessageDialog(this, "KRA PIN is required for Interns.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int departmentDivision;
            try {
                departmentDivision = Integer.parseInt(departmentDivisionText);
            } catch (NumberFormatException ex) {
                 JOptionPane.showMessageDialog(this, "Invalid Department-Division ID. Please enter a valid integer.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                 return;
            }

            // Save the record to the database
            try (Connection conn = DriverManager.getConnection(url, databaseUser, databasePassword)) {
                 String sql = "INSERT INTO Temporary (FirstName, MiddleName, LastName, WorkLevel, YearOfBirth, NationalIDNo, EmailAddress,PhysicalAddress, Disabilities, KRAPIN, DepartmentDivision) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; // Updated Table name
                 PreparedStatement pstmt = conn.prepareStatement(sql);
                 pstmt.setString(1, firstName);
                 pstmt.setString(2, middleName);
                 pstmt.setString(3, lastName);
                 pstmt.setString(4, workLevel);
                 pstmt.setObject(5, formattedDate); // Store as LocalDate
                 pstmt.setString(6, nationalId);
                 pstmt.setString(7, emailAddress);
                 pstmt.setString(8, address);
                 pstmt.setString(9, disabilities); // Use value from combo box
                 pstmt.setString(10, workLevel.equals("Intern") ? kraPin : null); // Handle KRAPIN for Attache
                 pstmt.setInt(11, departmentDivision); // Store DepartmentDivision ID

                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Temporary Staff Record Saved Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error saving record to database: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace(); // For debugging
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
        disabilitiesCombo.setSelectedIndex(0);
        kraPinField.setText("");
        departmentDivisionField.setText("");
        workLevelCombo.setSelectedIndex(0);
    }
    public static void main(String[] args) {
        // Run the GUI on the Event Dispatch Thread for thread safety
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TempStaffEntry();
            }
        });
    }
}
