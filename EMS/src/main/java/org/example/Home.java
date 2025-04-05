package org.example;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Home{
    JButton btnview, btncreate, btnexit,btnemployee,btntemp,btntempstaff;
    JFrame frame= new JFrame("Home Page");
    Home(){
       btncreate= new JButton("Create Record");
       btnview= new JButton("View Record");
       btnexit= new JButton("Exit");
       btnemployee = new JButton("Employee");
       btntemp= new JButton("Temporary stuff");
       btntempstaff= new JButton("Tempstaff");

       frame.setVisible (true);
       frame.setSize(300,200);
       frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
       frame.setLayout(new FlowLayout());

       frame.add(btncreate);
       frame.add(btnview);
       frame.add(btnemployee);
       frame.add(btntemp);
       frame.add(btntempstaff);
       frame.add(btnexit);

       btnview.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e){
            new View();
            frame.dispose();
        }
       });
       btnemployee.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e){
            new Employee();
            frame.dispose();
        }
       });
       btntemp.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e){
            new Temp();
            frame.dispose();
        }
       });
       btntempstaff.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e){
            new TempStaff();
            frame.dispose();
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