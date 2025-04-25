package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TempStaff extends Standard implements ActionListener {

    private JTextField firstNameField, middleNameField, lastNameField, nationalIdField, addressField, kraPinField, departmentDivisionField, yearOfBirthField;
    private JComboBox<String> workLevelCombo, disabilitiesCombo;
    private JLabel kraPinLabel;
    private JButton saveButton,btnexit;

    // Database credentials (replace with your actual credentials)
    private static final String DB_URL = "jdbc:mysql://localhost:3306/EMS"; // Assuming database name is EMS
    private static final String DB_USER = "your_mysql_username";
    private static final String DB_PASSWORD = "your_mysql_password";

    public TempStaff() {
        setTitle("Temporary Staff Registration");
        setLayout(new GridLayout(12, 2, 10, 10)); // Increased rows for disabilities

        // Labels and Input Fields
        add(new JLabel("First Name:"));
        firstNameField = new JTextField();
        add(firstNameField);

        add(new JLabel("Middle Name:"));
        middleNameField = new JTextField();
        add(middleNameField);

        add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        add(lastNameField);

        add(new JLabel("Work Level (Intern/Attache):"));
        String[] workLevels = {"Intern", "Attache"};
        workLevelCombo = new JComboBox<>(workLevels);
        workLevelCombo.addActionListener(this);
        add(workLevelCombo);

        add(new JLabel("Year of Birth (YYYY-MM-DD):")); // Updated format
        yearOfBirthField = new JTextField();
        add(yearOfBirthField);

        add(new JLabel("National ID No:"));
        nationalIdField = new JTextField();
        add(nationalIdField);

        add(new JLabel("Physical Address:"));
        addressField = new JTextField();
        add(addressField);

        add(new JLabel("Disabilities (YES/NO):")); // Updated to ENUM values
        String[] disabilitiesOptions = {"NO", "YES"};
        disabilitiesCombo = new JComboBox<>(disabilitiesOptions);
        add(disabilitiesCombo);

        kraPinLabel = new JLabel("KRA PIN (Interns only):");
        add(kraPinLabel);
        kraPinField = new JTextField();
        add(kraPinField);

        add(new JLabel("Department-Division ID:")); // Changed to Division ID
        departmentDivisionField = new JTextField();
        add(departmentDivisionField);

        saveButton = new JButton("Save Record");
        saveButton.addActionListener(this);
        add(new JLabel("")); // Empty label for alignment
        add(saveButton);
        add(new JLabel("")); // Empty label for alignment
        add(btnexit = new JButton("Exit"));
        btnexit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                //new Home();
            }
        });

        updateKraPinVisibility();
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
            String firstName = firstNameField.getText().trim();
            String middleName = middleNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String workLevel = (String) workLevelCombo.getSelectedItem();
            String yearOfBirthText = yearOfBirthField.getText().trim();
            String nationalId = nationalIdField.getText().trim();
            String address = addressField.getText().trim();
            String disabilities = (String) disabilitiesCombo.getSelectedItem(); // Get from combo box
            String kraPin = kraPinField.getText().trim();
            String departmentDivisionText = departmentDivisionField.getText().trim();

            // Basic Validation
            if (firstName.isEmpty() || lastName.isEmpty() || yearOfBirthText.isEmpty() || nationalId.isEmpty() || departmentDivisionText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all required fields (First Name, Last Name, Year of Birth, National ID, Department-Division).", "Validation Error", JOptionPane.ERROR_MESSAGE);
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
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                 String sql = "INSERT INTO Temporary (FirstName, MiddleName, LastName, WorkLevel, YearOfBirth, NationalIDNo, PhysicalAddress, Disabilities, KRAPIN, DepartmentDivision) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; // Updated Table name
                 PreparedStatement pstmt = conn.prepareStatement(sql);
                 pstmt.setString(1, firstName);
                 pstmt.setString(2, middleName);
                 pstmt.setString(3, lastName);
                 pstmt.setString(4, workLevel);
                 pstmt.setObject(5, yearOfBirth); // Store as LocalDate
                 pstmt.setString(6, nationalId);
                 pstmt.setString(7, address);
                 pstmt.setString(8, disabilities); // Use value from combo box
                 pstmt.setString(9, workLevel.equals("Intern") ? kraPin : null); // Handle KRAPIN for Attache
                 pstmt.setInt(10, departmentDivision); // Store DepartmentDivision ID

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
        SwingUtilities.invokeLater(() -> new TempStaff());
    }
}
