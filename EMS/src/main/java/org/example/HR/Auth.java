package org.example.HR;

import org.example.DB.Database;
import org.example.Home;
import org.example.Standard;//imports the Standard class from the parent package (org.example)
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Auth extends Standard implements ActionListener {
    JLabel auth = new JLabel("HR Authentication");
    JLabel user = new JLabel("Username");
    JLabel pass = new JLabel("Password");
    JPanel authPanel = new JPanel();
    JPanel leftPanel = new JPanel();
    JPanel rightPanel = new JPanel();
    JPanel bottomPanel = new JPanel();
    JPanel userPanel = new JPanel();
    JPanel passPanel = new JPanel();
    JButton confirm = new JButton("Confirm");
    JTextField username = new JTextField(20);
    JPasswordField password = new JPasswordField(20);

    Auth() {
        this.setTitle("HR Staff Authentication");
        this.setLayout(new BorderLayout(20, 20));

        this.setVisible(true);
        auth.setOpaque(true);
        auth.setBackground(Color.lightGray);
        auth.setFont(new Font("Arial", Font.BOLD, 30));
        user.setOpaque(true);
        user.setHorizontalAlignment(JLabel.LEFT);
        user.setBackground(Color.lightGray);
        pass.setOpaque(true);
        pass.setHorizontalAlignment(JLabel.LEFT);
        pass.setBackground(Color.lightGray);
//        user.setHorizontalTextPosition(SwingConstants.CENTER);
//        user.setVerticalTextPosition(SwingConstants.CENTER);
//        pass.setHorizontalTextPosition(SwingConstants.CENTER);
//        pass.setVerticalTextPosition(SwingConstants.CENTER);

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
                this.dispose();
                new Home();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        new Auth();
    }
}