package org.example;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Home{
    private JButton btnview, btncreate, btnexit,btnemployee,btntemp,btntempstaff;
    public Home(){
       btncreate= new JButton("Create Record");
       btnview= new JButton("View Record");
       btnexit= new JButton("Exit");
       btnemployee = new JButton("Employee");
       btntemp= new JButton("Temporary stuff");
       btntempstaff= new JButton("Tempstaff");

       setVisible (true);
       setSize(300,200);
       setDefaultCloseOperation( EXIT_ON_CLOSE);

       setLayout(new FlowLayout());
       add (btncreate);
       add (btnview);
       add (btnemployee);
       add (btntemp);
       add (btntempstaff);
       add (btnexit);

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
            new Temporary();
            dispose();
        }
       });
       btntempstaff.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e){
            new Tempstaff();
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