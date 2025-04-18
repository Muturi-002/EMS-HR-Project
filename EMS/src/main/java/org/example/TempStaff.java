package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

public class TempStaff extends Standard implements ActionListener {

    private JTextField firstNameField, middleNameField, lastNameField, nationalIdField, addressField, kraPinField, departmentDivisionField, yearOfBirthField;
    private JComboBox<String> workLevelCombo, disabilitiesCombo;
    private JLabel kraPinLabel;
    private JButton saveButton;

    private static final String DATA_FILE = "temp_staff_records.txt"; 

    public TempStaff() {
        setTitle("Temporary Staff Registration");
        setLayout(new GridLayout(11, 2, 10, 10));

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

        add(new JLabel("Year of Birth:"));
        yearOfBirthField = new JTextField();
        add(yearOfBirthField);

        add(new JLabel("National ID No:"));
        nationalIdField = new JTextField();
        add(nationalIdField);

        add(new JLabel("Physical Address:"));
        addressField = new JTextField();
        add(addressField);

        add(new JLabel("Disabilities (if any):"));
        String[] disab = {"Yes", "No"};
        disabilitiesCombo = new JComboBox<>(disab);
        add(disabilitiesCombo);

        kraPinLabel = new JLabel("KRA PIN (Interns only):");
        add(kraPinLabel);
        kraPinField = new JTextField();
        add(kraPinField);

        add(new JLabel("Department-Division:"));
        departmentDivisionField = new JTextField();
        add(departmentDivisionField);

        saveButton = new JButton("Save Record");
        saveButton.addActionListener(this);
        add(new JLabel("")); // Empty label for alignment
        add(saveButton);

        updateKraPinVisibility();
        setVisible(true);
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
            String disabilities = (String) disabilitiesCombo.getSelectedItem();
            String kraPin = kraPinField.getText().trim();
            String departmentDivision = departmentDivisionField.getText().trim();

            // Basic Validation
            if (firstName.isEmpty() || lastName.isEmpty() || yearOfBirthText.isEmpty() || nationalId.isEmpty() || departmentDivision.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all required fields (First Name, Last Name, Year of Birth, National ID, Department-Division).", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int year;
            try {
                year = Integer.parseInt(yearOfBirthText);
                if (year <= 1900 || year > 2099) { // Basic year range check
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Year of Birth. Please enter a valid year.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (workLevel.equals("Intern") && kraPin.isEmpty()) {
                JOptionPane.showMessageDialog(this, "KRA PIN is required for Interns.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create the record string
            String record = String.format("%s,%s,%s,%s,%d,%s,%s,%s,%s,%s%n",
                    firstName, middleName, lastName, workLevel, year, nationalId, address, disabilities.isEmpty() ? "N/A" : disabilities,
                    workLevel.equals("Intern") ? kraPin : "N/A", departmentDivision);

            // Save the record to a file
            try (FileWriter fw = new FileWriter(DATA_FILE, true)) {
                fw.write(record);
                JOptionPane.showMessageDialog(this, "Temporary Staff Record Saved Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving record to file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
