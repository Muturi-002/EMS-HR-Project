import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TempStaffGUI extends JFrame implements ActionListener {

    private JTextField firstNameField, middleNameField, lastNameField, nationalIdField, addressField, disabilitiesField,
            kraPinField, departmentDivisionField, yearOfBirthField;
    private JComboBox<String> workLevelCombo;
    private JLabel kraPinLabel;
    private JButton saveButton;

    // Database credentials (replace with your actual credentials)
    private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String DB_USER = "your_mysql_username";
    private static final String DB_PASSWORD = "your_mysql_password";

    public TempStaffGUI() {
        setTitle("Temporary Staff Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(11, 2, 10, 10));
        setPreferredSize(new Dimension(450, 480));
        setLocationRelativeTo(null);

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
        String[] workLevels = { "Intern", "Attache" };
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
        disabilitiesField = new JTextField();
        add(disabilitiesField);

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
        pack();
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
            String disabilities = disabilitiesField.getText().trim();
            String kraPin = kraPinField.getText().trim();
            String departmentDivision = departmentDivisionField.getText().trim();

            // Basic Validation
            if (firstName.isEmpty() || lastName.isEmpty() || yearOfBirthText.isEmpty() || nationalId.isEmpty()
                    || departmentDivision.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please fill in all required fields (First Name, Last Name, Year of Birth, National ID, Department-Division).",
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int year;
            try {
                year = Integer.parseInt(yearOfBirthText);
                if (year <= 1900 || year > 2099) { // Basic year range check
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Year of Birth. Please enter a valid year.",
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (workLevel.equals("Intern") && kraPin.isEmpty()) {
                JOptionPane.showMessageDialog(this, "KRA PIN is required for Interns.", "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Save the record to the database
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String sql = "INSERT INTO temp_staff (first_name, middle_name, last_name, work_level, year_of_birth, national_id, address, disabilities, kra_pin, department_division) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, firstName);
                pstmt.setString(2, middleName);
                pstmt.setString(3, lastName);
                pstmt.setString(4, workLevel);
                pstmt.setInt(5, year);
                pstmt.setString(6, nationalId);
                pstmt.setString(7, address);
                pstmt.setString(8, disabilities.isEmpty() ? "N/A" : disabilities);
                pstmt.setString(9, workLevel.equals("Intern") ? kraPin : "N/A");
                pstmt.setString(10, departmentDivision);

                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Temporary Staff Record Saved Successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                clearFields();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error saving record to database: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
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
        disabilitiesField.setText("");
        kraPinField.setText("");
        departmentDivisionField.setText("");
        workLevelCombo.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TempStaffGUI());
    }
}
