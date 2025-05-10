package org.example.HR;

import org.example.DB.Database;
import org.example.Home;
import org.example.LoadEnv;
import org.example.Standard;//imports the Standard class from the parent package (org.example)
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Auth extends Standard implements ActionListener{
    JButton btnlogin, btnexit;
    JTextField txtusername;
    JPasswordField txtpassword;
    JLabel lblusername= new JLabel("Username");
    JLabel lblPassword= new JLabel("Enter Password");
    JLabel lblCredentials= new JLabel("Enter your credentials if authorized");
    JLabel lblTitle= new JLabel("HR Authorization");
    JPanel bottomPanel = getNavPanel();
    JPanel leftPanel=getLeftPanel();
    JPanel rightPanel= getRightPanel();
    JPanel upperPanel=getUpperPanel();
    JPanel authPanel= new JPanel();
    JPanel userPanel= new JPanel();
    JPanel passPanel= new JPanel();
    JPanel credTitlePanel= new JPanel();
    Auth() {
        setTitle("Authentication Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        upperPanel.add(lblTitle);
        authPanel.setLayout(new GridLayout(3,1,20,20));
        userPanel.setLayout(new FlowLayout());
        passPanel.setLayout(new FlowLayout());

        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        credTitlePanel.add(lblCredentials);
        lblCredentials.setHorizontalAlignment(SwingConstants.CENTER);
        lblCredentials.setFont(new Font("Arial", Font.BOLD, 15));

        userPanel.add(lblusername); userPanel.add(txtusername= new JTextField(20));
        passPanel.add(lblPassword); passPanel.add(txtpassword= new JPasswordField(20));

        authPanel.add(credTitlePanel);
        authPanel.add(userPanel);
        authPanel.add(passPanel);

        bottomPanel.add(btnlogin= new JButton("Login"));
        bottomPanel.add(btnexit= new JButton("Exit"));

        btnexit.addActionListener(this);
        btnlogin.addActionListener(this);
        add(leftPanel,BorderLayout.WEST);
        add(rightPanel,BorderLayout.EAST);
        add(authPanel, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==btnlogin) {
            checkAuthorization();
        }else if (e.getSource()==btnexit){
            System.exit(0);
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Auth());
    }
    public void checkAuthorization(){
        //check in database
        /*Connection conn ;
        Statement stmt;
        String databaseUser = LoadEnv.getDatabaseUser();
        String databasePassword = LoadEnv.getDatabasePassword();
        String url = LoadEnv.getURL();
        String tnsAdmin = "/home/muturiiii/Desktop/Y3S2 Project/EMS-HR-Project/EMS/src/main/java/org/example/Wallet_EMS2";

        System.setProperty("oracle.net.tns_admin", tnsAdmin);
        try {
            conn = DriverManager.getConnection(url, databaseUser, databasePassword);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM HR_AUTH WHERE USERNAME = '" + txtusername.getText() + "' AND PASSWORD = '" + txtpassword.getText() + "'";
            stmt.executeQuery(sql);
            if (stmt.getResultSet().next()) {
                JOptionPane.showMessageDialog(this, "Login Successful");
                new Home();
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Login Failed. Invalid credentials.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        if (txtusername.getText().equals(LoadEnv.getAppUsername()) && txtpassword.getText().equals(LoadEnv.getAppPassword())) {
            JOptionPane.showMessageDialog(this, "Login Successful");
            new Home();
            this.dispose();
        }else {
            JOptionPane.showMessageDialog(this, "Login Failed. Invalid credentials.");
        }
    }
}