package org.example.Staff;

import org.example.Standard;
import org.example.LoadEnv;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SingleViewEmployees extends Standard {
    String databaseUser = LoadEnv.getDatabaseUser();
    String databasePassword = LoadEnv.getDatabasePassword();
    String url = LoadEnv.getURL();
    String tnsAdmin = "/home/muturiiii/Desktop/Y3S2 Project/EMS-HR-Project/EMS/src/main/java/org/example/Wallet_EMS2";
    String databaseName = LoadEnv.getDatabaseName();

    JPanel centrePanel = new JPanel();
    JPanel navPanel = getNavPanel();
    JButton btnBack,btnEnableEdit,btnDelete,btnNew,btnSave,btnSearch;

    private JComboBox<String> disabilitiesCombo, statusCombo;
    private JLabel lblEmpID,lblFirstName, lblMiddleName, lblLastName, lblNationalId, lblEmail,lblAddress, lblKraPin, lblDepartmentDivision, lblYearOfBirth, lblDisabilities, lblStatus;
    private JTextField empIDField,firstNameField, middleNameField, lastNameField, nationalIdField, emailField,addressField, kraPinField, departmentDivisionField, yearOfBirthField;
    SingleViewEmployees(){
        System.setProperty("oracle.net.tns_admin", tnsAdmin);
        setTitle("View Each Employee Record");
        setLayout(new BorderLayout(20,20));
        centrePanel.setLayout(new GridLayout(12, 2, 10, 10));

        lblEmpID=new JLabel("Employee ID:");
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

        empIDField= new JTextField(); empIDField.setEditable(true);
        firstNameField= new JTextField(); firstNameField.setEditable(false);
        lastNameField = new JTextField(); lastNameField.setEditable(false);
        middleNameField = new JTextField(); middleNameField.setEditable(false);
        yearOfBirthField = new JTextField(); yearOfBirthField.setEditable(false);
        nationalIdField = new JTextField(); nationalIdField.setEditable(false);
        emailField = new JTextField(); emailField.setEditable(false);
        addressField = new JTextField(); addressField.setEditable(false);
        kraPinField = new JTextField(); kraPinField.setEditable(false);
        departmentDivisionField = new JTextField(); departmentDivisionField.setEditable(false);
        String[] disab = {"Yes", "No"};
        disabilitiesCombo = new JComboBox<>(disab); disabilitiesCombo.setEnabled(false);
        String[] status = {"Active", "Inactive"};
        statusCombo = new JComboBox<>(status); statusCombo.setEnabled(false);

        centrePanel.add(lblEmpID); centrePanel.add(empIDField);
        centrePanel.add(lblFirstName); centrePanel.add(firstNameField);
        centrePanel.add(lblMiddleName); centrePanel.add(middleNameField);
        centrePanel.add(lblLastName); centrePanel.add(lastNameField);
        centrePanel.add(lblYearOfBirth); centrePanel.add(yearOfBirthField);
        centrePanel.add(lblNationalId); centrePanel.add(nationalIdField);
        centrePanel.add(lblEmail); centrePanel.add(emailField);
        centrePanel.add(lblAddress); centrePanel.add(addressField);
        centrePanel.add(lblKraPin); centrePanel.add(kraPinField);
        centrePanel.add(lblDepartmentDivision); centrePanel.add(departmentDivisionField);
        centrePanel.add(lblDisabilities); centrePanel.add(disabilitiesCombo);
        centrePanel.add(lblStatus); centrePanel.add(statusCombo);

        btnBack = new JButton("Back To Table View"); btnBack.addActionListener(new Nav());
        btnDelete= new JButton("Delete Record"); btnDelete.addActionListener(new Nav());
        btnEnableEdit= new JButton("Enable Edit"); btnEnableEdit.addActionListener(new Nav());
        btnNew= new JButton("New Record"); btnNew.addActionListener(new Nav());
        btnSave = new JButton("Save Changes"); btnSave.addActionListener(new Nav());
        btnSearch= new JButton("Search Record"); btnSearch.addActionListener(new Nav());

        navPanel.add(btnEnableEdit);
        navPanel.add(btnNew);
        navPanel.add(btnBack);
        navPanel.add(btnDelete);
        navPanel.add(btnSearch);
        add(getUpperPanel(),BorderLayout.NORTH);
        add(centrePanel, BorderLayout.CENTER);
        add(navPanel,BorderLayout.SOUTH);
    }

    private class Nav implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnBack) {
                dispose();
                new ViewEmployees();
            } else if (e.getSource()==btnSearch){
                getRecords();
            }else if (e.getSource() == btnEnableEdit) {
                editEnabled();
            } else if (e.getSource() == btnNew) {
                dispose();
                new EmployeeEntry();
            } else if (e.getSource() == btnDelete) {
                try (Connection conn = DriverManager.getConnection(url, databaseUser, databasePassword);
                     PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Employees WHERE EmployeeID=?")) {
                    pstmt.setInt(1, Integer.parseInt(empIDField.getText()));
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
            }else if (e.getSource()==btnSave){
                //Saving changes
                try (Connection conn = DriverManager.getConnection(url, databaseUser, databasePassword);
                     PreparedStatement pstmt = conn.prepareStatement("UPDATE Employees SET FirstName=?, MiddleName=?, LastName=?, YearOfBirth=?, NationalIDNo=?, EmailAddress=?, PhysicalAddress=?, KRAPIN=?, DepartmentDivision=?, Disabilities=?, Status=? WHERE EmployeeID=?"))
                {
                    pstmt.setString(1, firstNameField.getText());
                    pstmt.setString(2, middleNameField.getText());
                    pstmt.setString(3, lastNameField.getText());
                    pstmt.setDate(4, Date.valueOf(yearOfBirthField.getText()));
                    pstmt.setString(5, nationalIdField.getText());
                    pstmt.setString(6, emailField.getText());
                    pstmt.setString(7, addressField.getText());
                    pstmt.setString(8, kraPinField.getText());
                    pstmt.setInt(9, Integer.parseInt(departmentDivisionField.getText()));
                    pstmt.setString(10, disabilitiesCombo.getSelectedItem().toString());
                    pstmt.setString(11, statusCombo.getSelectedItem().toString());
                    pstmt.setInt(12, Integer.parseInt(empIDField.getText()));

                    int rowsUpdated = pstmt.executeUpdate();
                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(SingleViewEmployees.this, "Record updated successfully.");
                        dispose();
                        new ViewEmployees();
                    } else {
                        JOptionPane.showMessageDialog(SingleViewEmployees.this, "Failed to update record.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void editEnabled(){
        empIDField.setEditable(false);
        firstNameField.setEditable(true);
        middleNameField.setEditable(true);
        lastNameField.setEditable(true);
        yearOfBirthField.setEditable(true);
        nationalIdField.setEditable(true);
        emailField.setEditable(true);
        addressField.setEditable(true);
        kraPinField.setEditable(true);
        departmentDivisionField.setEditable(true);
        disabilitiesCombo.setEnabled(true);
        statusCombo.setEnabled(true);
    }

    private void getRecords() {
        String id=empIDField.getText();
        try (
                Connection conn = DriverManager.getConnection(url, databaseUser, databasePassword);
                Statement stmt = conn.createStatement();
        ) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM Employees WHERE EmployeeID = " + id);
            if (rs.next()) {
                firstNameField.setText(rs.getString("FirstName"));
                middleNameField.setText(rs.getString("MiddleName"));
                lastNameField.setText(rs.getString("LastName"));
                yearOfBirthField.setText(String.valueOf(rs.getDate("YearOfBirth")));
                nationalIdField.setText(rs.getString("NationalIDNo"));
                emailField.setText(rs.getString("EmailAddress"));
                addressField.setText(rs.getString("PhysicalAddress"));
                kraPinField.setText(rs.getString("KRAPIN"));
                departmentDivisionField.setText(String.valueOf(rs.getInt("DepartmentDivision")));

                boolean hasDisability = rs.getBoolean("Disabilities");
                disabilitiesCombo.setSelectedItem(hasDisability ? "Yes" : "No");

                String status = rs.getString("Status");
                statusCombo.setSelectedItem(status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Employee "+id+" does not exist.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
