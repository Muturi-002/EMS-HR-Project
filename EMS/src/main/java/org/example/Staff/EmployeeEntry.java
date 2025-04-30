package org.example.Staff;

import org.example.Standard;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeEntry extends Standard{
    private JPanel panel;
    private JLabel lblFirstName, lblMiddleName, lblLastName, lblNationalId, lblAddress, lblKraPin, lblDepartmentDivision, lblYearOfBirth, lblDisabilities;
    private JTextField firstNameField, middleNameField, lastNameField, nationalIdField, addressField, kraPinField, departmentDivisionField, yearOfBirthField;
    private JComboBox<String> disabilitiesCombo;
    private JButton btnSave, btnClear;

    public EmployeeEntry() {
        // Create the frame
        setTitle("Add a New/Existing Employee");

        // Create the panel
        panel = new JPanel();
        panel.setLayout(new GridLayout(11, 2, 10, 10));

        // Initialize components
        lblFirstName = new JLabel("First Name:");
        lblLastName = new JLabel("Last Name:");
        lblMiddleName = new JLabel("Middle Name:");
        lblYearOfBirth = new JLabel("Year of Birth:");
        lblNationalId = new JLabel("National ID No:");
        lblAddress = new JLabel("Physical Address:");
        lblKraPin = new JLabel("KRA PIN:");
        lblDepartmentDivision = new JLabel("Department-Division:");
        lblDisabilities = new JLabel("Disabilities (if any):");

        firstNameField= new JTextField();
        lastNameField = new JTextField();
        middleNameField = new JTextField();
        yearOfBirthField = new JTextField();
        nationalIdField = new JTextField();
        addressField = new JTextField();
        kraPinField = new JTextField();
        departmentDivisionField = new JTextField();
        String[] disab = {"Yes", "No"};
        disabilitiesCombo = new JComboBox<>(disab);
        disabilitiesCombo.setSelectedIndex(1); // Default to "No"



        btnSave = new JButton("Save");
        btnClear = new JButton("Clear");

        // Add components to the panel
        panel.add(lblFirstName);
        panel.add(firstNameField);
        panel.add(lblMiddleName);
        panel.add(middleNameField);
        panel.add(lblLastName);
        panel.add(lastNameField);
        panel.add(lblYearOfBirth);
        panel.add(yearOfBirthField);
        panel.add(lblNationalId);
        panel.add(nationalIdField);
        panel.add(lblAddress);
        panel.add(addressField);
        panel.add(lblKraPin);
        panel.add(kraPinField);
        panel.add(lblDepartmentDivision);
        panel.add(departmentDivisionField);
        panel.add(lblDisabilities);
        panel.add(disabilitiesCombo);

        panel.add(btnSave);
        panel.add(btnClear);

        // Add panel to the frame
        add(panel);

        // Add action listener for the Save button
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveEmployee();
            }
        });

        // Add action listener for the Clear button
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

    }

    private void saveEmployee() {
        String fName = firstNameField.getText();
        String mName = middleNameField.getText();
        String lName = lastNameField.getText();
        String yearBirth = yearOfBirthField.getText();
        String nationalId = nationalIdField.getText();
        String address = addressField.getText();
        String kraPin = kraPinField.getText();
        String departmentDivision = departmentDivisionField.getText();


        if (fName.isEmpty() || mName.isEmpty() || lName.isEmpty() || yearBirth.isEmpty() || nationalId.isEmpty() || address.isEmpty() || kraPin.isEmpty() || departmentDivision.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "New Employee record saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            // Here you can add code to save the employee info to a database or a file
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
        disabilitiesCombo.setSelectedIndex(1); // Reset to "No"
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
