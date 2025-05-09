package org.example.Staff;

import org.example.LoadEnv;
import org.example.Standard;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SingleViewTemp extends Standard{
    String databaseUser = LoadEnv.getDatabaseUser();
    String databasePassword = LoadEnv.getDatabasePassword();
    String url = LoadEnv.getURL();
    String tnsAdmin = "/home/muturiiii/Desktop/Y3S2 Project/EMS-HR-Project/EMS/src/main/java/org/example/Wallet_EMS2";

    JPanel centrePanel = new JPanel();
    JPanel navPanel = getNavPanel();
    JButton btnBack,btnEnableEdit,btnDelete,btnNew,btnSave,btnSearch;

    private JTextField tempIDField,firstNameField, middleNameField, lastNameField, nationalIdField, emailField,addressField, kraPinField, departmentDivisionField, yearOfBirthField;
    private JComboBox<String> workLevelCombo, disabilitiesCombo;
    SingleViewTemp(){
        System.setProperty("oracle.net.tns_admin", tnsAdmin);
        setTitle("Search for Intern/Attache Record");
        setLayout(new BorderLayout(20,20));
        centrePanel.setLayout(new GridLayout(12, 2, 10, 10));

        tempIDField= new JTextField(); tempIDField.setEditable(true);
        firstNameField = new JTextField(); firstNameField.setEditable(false);
        middleNameField = new JTextField(); middleNameField.setEditable(false);
        lastNameField = new JTextField(); lastNameField.setEditable(false);
        String[] workLevels = {"INTERN", "ATTACHE"};
        workLevelCombo = new JComboBox<>(workLevels); workLevelCombo.setEditable(false);
        yearOfBirthField = new JTextField(); yearOfBirthField.setEditable(false);
        nationalIdField = new JTextField(); nationalIdField.setEditable(false);
        addressField= new JTextField(); addressField.setEditable(false);
        emailField=new JTextField(); emailField.setEditable(false);
        String[] disabilitiesOptions = {"NO", "YES"};
        disabilitiesCombo = new JComboBox<>(disabilitiesOptions); disabilitiesCombo.setEditable(false);
        kraPinField = new JTextField(); kraPinField.setEditable(false);
        departmentDivisionField = new JTextField(); departmentDivisionField.setEditable(false);

        centrePanel.add(new JLabel("Employee ID:")); centrePanel.add(tempIDField);
        centrePanel.add(new JLabel("First Name:")); centrePanel.add(firstNameField);
        centrePanel.add(new JLabel("Middle Name:")); centrePanel.add(middleNameField);
        centrePanel.add(new JLabel("Last Name:")); centrePanel.add(lastNameField);
        centrePanel.add(new JLabel("Email Address:"));; centrePanel.add(emailField);
        centrePanel.add(new JLabel("Year of Birth (YYYY-MM-DD):"));
        centrePanel.add(yearOfBirthField);
        centrePanel.add(new JLabel("Physical Address:")); centrePanel.add(addressField);
        centrePanel.add(new JLabel("Work Level (Intern/Attache):")); centrePanel.add(workLevelCombo);
        centrePanel.add(new JLabel("National ID No:")); centrePanel.add(nationalIdField);
        centrePanel.add(new JLabel("KRA PIN :")); centrePanel.add(kraPinField);
        centrePanel.add(new JLabel("Disabilities :")); centrePanel.add(disabilitiesCombo);
        centrePanel.add(new JLabel("Department-Division ID:")); centrePanel.add(departmentDivisionField);

        btnBack = new JButton("Back To Table View"); btnBack.addActionListener(new Nav());
        btnDelete= new JButton("Delete Record"); btnDelete.addActionListener(new Nav());
        btnEnableEdit= new JButton("Enable Edit"); btnEnableEdit.addActionListener(new Nav());
        btnNew= new JButton("New Record"); btnNew.addActionListener(new Nav());
        btnSave = new JButton("Save Changes"); btnSave.addActionListener(new Nav());
        btnSearch= new JButton("Search Record");btnSearch.addActionListener(new Nav());

        navPanel.add(btnSearch);
        navPanel.add(btnEnableEdit);
        navPanel.add(btnSave);
        navPanel.add(btnNew);
        navPanel.add(btnBack);
        navPanel.add(btnDelete);

        add(getUpperPanel(),BorderLayout.NORTH);
        add(centrePanel, BorderLayout.CENTER);
        add(navPanel,BorderLayout.SOUTH);
    }

    private class Nav implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnBack) {
                dispose();
                new ViewTempStaff();
            } else if (e.getSource()==btnSearch){
                getRecords();
            }else if (e.getSource() == btnEnableEdit) {
                editEnabled();
            } else if (e.getSource() == btnNew) {
                dispose();
                new TempStaffEntry();
            } else if (e.getSource() == btnDelete) {
                try (Connection conn = DriverManager.getConnection(url, databaseUser, databasePassword);
                     PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Temporary WHERE EmployeeID=?")) {
                    pstmt.setInt(1, Integer.parseInt(tempIDField.getText()));
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
                     PreparedStatement pstmt = conn.prepareStatement("UPDATE Temporary SET FirstName=?, MiddleName=?, LastName=?, WorkLevel=?, YearOfBirth=?, NationalIDNo=?, EmailAddress=?, PhysicalAddress=?, KRAPIN=?, DepartmentDivision=?, Disabilities=? WHERE EmployeeID=?"))
                {
                    pstmt.setString(1, firstNameField.getText());
                    pstmt.setString(2, middleNameField.getText());
                    pstmt.setString(3, lastNameField.getText());
                    pstmt.setString(4, workLevelCombo.getSelectedItem().toString());
                    pstmt.setDate(5, Date.valueOf(yearOfBirthField.getText()));
                    pstmt.setString(6, nationalIdField.getText());
                    pstmt.setString(7, emailField.getText());
                    pstmt.setString(8, addressField.getText());
                    pstmt.setString(9, kraPinField.getText());
                    pstmt.setInt(10, Integer.parseInt(departmentDivisionField.getText()));
                    pstmt.setString(11, disabilitiesCombo.getSelectedItem().toString());
                    pstmt.setInt(12, Integer.parseInt(tempIDField.getText()));

                    int rowsUpdated = pstmt.executeUpdate();
                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(null, "Record updated successfully.");
                        dispose();
                        new ViewEmployees();
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to update record.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void editEnabled(){
        tempIDField.setEditable(false);
        firstNameField.setEditable(true);
        middleNameField.setEditable(true);
        lastNameField.setEditable(true);
        workLevelCombo.setEnabled(true);
        yearOfBirthField.setEditable(true);
        nationalIdField.setEditable(true);
        emailField.setEditable(true);
        addressField.setEditable(true);
        addressField.setEditable(true);
        kraPinField.setEditable(true);
        departmentDivisionField.setEditable(true);
        disabilitiesCombo.setEnabled(true);
    }

    private void getRecords() {
        try (
                Connection conn = DriverManager.getConnection(url, databaseUser, databasePassword);
                Statement stmt = conn.createStatement();
        ) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM Temporary WHERE TempID = " + tempIDField.getText());
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

                String worklevel = rs.getString("WorkLevel");
                workLevelCombo.setSelectedItem(worklevel);

            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Temp Staff"+tempIDField.getText()+" does not exist.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }


}
