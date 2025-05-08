package org.example.HR;

import org.example.DB.Database;
import org.example.Home;
import org.example.Standard;//imports the Standard class from the parent package (org.example)
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Auth extends Standard implements ActionListener{
    JLabel auth = new JLabel("HR Authentication");
    JLabel user = new JLabel("Username");
    JLabel pass = new JLabel("Password");
    JPanel authPanel = getUpperPanel();
    JPanel leftPanel = getLeftPanel();
    JPanel rightPanel = getRightPanel();
    JPanel bottomPanel = getNavPanel();
    JPanel userPanel = new JPanel();
    JPanel passPanel = new JPanel();
    JButton confirm = new JButton("Confirm");
    JButton exit= new JButton("Exit");
    JTextField username = new JTextField(20);
    JPasswordField password = new JPasswordField(20);

    Auth() {
        this.setTitle("HR Staff Authentication");
        this.setLayout(new BorderLayout(20, 20));

        this.setVisible(true);
        auth.setOpaque(true);
        auth.setBackground(Color.lightGray);
        auth.setFont(new Font("Arial", Font.BOLD, 30));

        authPanel.setBackground(Color.GRAY);
        authPanel.setPreferredSize(new Dimension(500, 400));
        authPanel.setLayout(new GridLayout(3, 1));
        authPanel.add(userPanel);
        authPanel.add(passPanel);
        confirm.setBounds(300,200 , 70, 50);
        authPanel.add(confirm);

        userPanel.add(user);
        userPanel.add(username);
        passPanel.add(pass);
        passPanel.add(password);

        confirm.addActionListener(this);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        bottomPanel.add(exit);
        this.add(auth, BorderLayout.NORTH);
        this.add(authPanel, BorderLayout.CENTER);
        this.add(leftPanel, BorderLayout.WEST);
        this.add(rightPanel, BorderLayout.EAST);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirm) {
            String userText = username.getText();
            String passText = password.getText();
            System.out.println("Username: " + userText);
            System.out.println("Password: " + passText);
            if (userText.equals("admin") && passText.equals("admin")) {
                //System.out.println("Authentication Successful");
                JOptionPane.showMessageDialog(null, "Authentication Successful");
                dispose();
                new Home();
            } else {
                JOptionPane.showMessageDialog(auth, "Invalid Username or Password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    public static void main(String[] args) {
        new Auth();
    }
}