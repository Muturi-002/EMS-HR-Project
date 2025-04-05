package org.example.HR;
import org.example.Welcome;//imports the Welcome class from the parent package (org.example)
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Auth extends JFrame implements ActionListener {
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
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("HR Staff Authentication");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLayout(new BorderLayout(20, 20));

        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
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
        user.setHorizontalTextPosition(SwingConstants.CENTER);
        user.setVerticalTextPosition(SwingConstants.CENTER);
        pass.setHorizontalTextPosition(SwingConstants.CENTER);
        pass.setVerticalTextPosition(SwingConstants.CENTER);

        authPanel.setBackground(Color.GRAY);
        authPanel.setPreferredSize(new Dimension(500, 400));
        authPanel.setLayout(new GridLayout(3, 1));
        authPanel.add(userPanel);
        authPanel.add(passPanel);
        confirm.setBounds(0, 0, 70, 50);
        authPanel.add(confirm);

        leftPanel.setPreferredSize(new Dimension(300, height));
        rightPanel.setPreferredSize(new Dimension(300, height));
        bottomPanel.setPreferredSize(new Dimension(width, 300));
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
                System.out.println("Authentication Successful");
                this.dispose();
                Welcome welcome = new Welcome();
            } else {
                System.out.println("Authentication Failed");
                JOptionPane.showMessageDialog(this, "Invalid Username or Password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        new Auth();
    }
}