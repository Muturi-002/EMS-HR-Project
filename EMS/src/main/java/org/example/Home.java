package org.example;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import org.example.Staff.*;
import org.example.HR.*;

public class Home extends JFrame{
    JButton btnview, btnLeaves, btnexit,btnemployee,btntemp,btntempstaff,btnAttendance;
    JLabel homeLabel= new JLabel("Welcome, "+LoadEnv.getAppUsername());
    JPanel nav=new JPanel();
    JPanel buttonPanel=new JPanel();
    public Home(){
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       this.setExtendedState(JFrame.MAXIMIZED_BOTH);
       this.setTitle("Home Page");
       this.setLayout(new BorderLayout(20,20));
       this.setVisible(true);
       int width= Toolkit.getDefaultToolkit().getScreenSize().width;
       homeLabel.setPreferredSize(new Dimension(width,100));
       homeLabel.setOpaque(true);
       homeLabel.setBackground(Color.GRAY);
       homeLabel.setHorizontalAlignment(SwingConstants.CENTER);
       homeLabel.setFont(new Font("Arial", Font.BOLD,20));

       btnLeaves= new JButton("Check Leave Requests");
       btnview= new JButton("View Employees' Details (Contract)");
       btnexit= new JButton("Exit");
       btnemployee = new JButton("Add Employee");
       btntemp= new JButton("Add Temporary stuff");
       btntempstaff= new JButton("View Temporary Staff Details");
       btnAttendance= new JButton("Record Attendance");

       //nav.setLayout(new GridLayout(2,2,20,50));
       nav.setLayout(new FlowLayout());
       buttonPanel.setLayout(new GridLayout(6,1,20,50));
       buttonPanel.add(btnview);
       buttonPanel.add(btnemployee);
       buttonPanel.add(btntemp);
       buttonPanel.add(btntempstaff);
       buttonPanel.add(btnLeaves);
       buttonPanel.add(btnAttendance);
       //nav.add(btnexit);
       nav.add(buttonPanel);

       add(homeLabel, BorderLayout.NORTH);
       add(nav, BorderLayout.CENTER);
       add(btnexit, BorderLayout.SOUTH);
       btnview.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e){
            new ViewEmployees();

        }
       });
       btnemployee.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e){
            new EmployeeEntry();

        }
       });
       btntemp.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e){
            new TempStaffEntry();

        }
       });
       btntempstaff.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e){
            new ViewTempStaff();

        }
       });
       btnAttendance.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e){
            new Attendance();
        }
        });
       btnexit.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            System.exit(0);
        }
       });
        btnLeaves.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new ViewLeaves();
            }
        });
    }
    public static void main (String []args){
        SwingUtilities.invokeLater( ()-> new Home());
    }
}