package org.example;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Home extends JFrame{
    JButton btnview, btncreate, btnexit,btnemployee,btntemp,btntempstaff;
    JLabel homeLabel= new JLabel("Welcome, ");
    JPanel nav=new JPanel();
    JPanel buttonPanel=new JPanel();
    public Home(){
       this.setExtendedState(JFrame.MAXIMIZED_BOTH);
       this.setTitle("Home Page");
       this.setLayout(new BorderLayout(20,20));
       int width= Toolkit.getDefaultToolkit().getScreenSize().width;
       homeLabel.setPreferredSize(new Dimension(width,100));
       homeLabel.setOpaque(true);
       homeLabel.setBackground(Color.GRAY);
       homeLabel.setHorizontalAlignment(SwingConstants.CENTER);
       homeLabel.setFont(new Font("Arial", Font.BOLD,20));

       btncreate= new JButton("Create Record");
       btnview= new JButton("View Record");
       btnexit= new JButton("Exit");
       btnemployee = new JButton("Employee");
       btntemp= new JButton("Temporary stuff");
       btntempstaff= new JButton("Tempstaff");

       //nav.setLayout(new GridLayout(2,2,20,50));
       nav.setLayout(new FlowLayout());
       buttonPanel.setLayout(new GridLayout(2,2,20,50));
       buttonPanel.add(btncreate);
       buttonPanel.add(btnview);
       buttonPanel.add(btnemployee);
       buttonPanel.add(btntemp);
       buttonPanel.add(btntempstaff);
       //nav.add(btnexit);
       nav.add(buttonPanel);

       add(homeLabel, BorderLayout.NORTH);
       add(nav, BorderLayout.CENTER);
       add(btnexit, BorderLayout.SOUTH);
       btnview.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e){
            new View();
            dispose();
        }
       });
       btnemployee.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e){
            new Employee();
            dispose();
        }
       });
       btntemp.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e){
            new Temp();
            dispose();
        }
       });
       btntempstaff.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e){
            new TempStaff();
            dispose();
        }
       });
       
       btnexit.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            System.exit(0);
        }
       });
    }
    public static void main (String []args){
        new Home();
    }
}