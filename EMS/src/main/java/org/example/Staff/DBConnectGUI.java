package org.example.Staff;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnectGUI extends JFrame {

    private JTextField userField;
    private JPasswordField passwordField;
    private JTextField walletPathField; // Or a service name field
    private JButton connectButton;
    private JTextArea outputArea;

    public DBConnectGUI() {
        setTitle("Oracle ADB Connection");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        userField = new JTextField(20);
        passwordField = new JPasswordField(20);
        walletPathField = new JTextField(20); // Or a service name field
        connectButton = new JButton("Connect");
        outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);

        add(new JLabel("Username:"));
        add(userField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(new JLabel("Wallet Path:")); // Or "Service Name:"
        add(walletPathField);
        add(connectButton);
        add(new JScrollPane(outputArea));

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectToDB();
            }
        });

        setSize(500, 300);
        setVisible(true);
    }

    private void connectToDB() {
        String userName = userField.getText();
        String password = new String(passwordField.getPassword());
        String walletPath = walletPathField.getText(); // Or service name

        try {
            System.setProperty("oracle.net.tns_admin", walletPath); // Or, if using service name, remove this line

            String connectionString = "jdbc:oracle:thin:@" + walletPath; // Or, if using service name, use that here
            // Or: String connectionString = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCPS)(HOST=adb.eu-frankfurt-1.oraclecloud.com)(PORT=1522))(CONNECT_DATA=(SERVICE_NAME=g2b5e13550b7348_db20230521_high.adb.oraclecloud.com)))"; // From tnsnames.ora

            Properties props = new Properties();
            props.setProperty("user", userName);
            props.setProperty("password", password);

            Connection connection = DriverManager.getConnection(connectionString, props);

            outputArea.setText("Connected to Oracle Autonomous Database!\n");
            // Perform further database operations here...

            connection.close();

        } catch (SQLException ex) {
            outputArea.setText("Connection failed: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DBConnectGUI());
    }
}