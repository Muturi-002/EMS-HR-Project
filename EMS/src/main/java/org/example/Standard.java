package org.example;
import java.awt.*;
import javax.swing.*;

public abstract class Standard extends JFrame{
    protected JPanel leftPanel, rightPanel, upperPanel,navPanel;
    public Standard(){
        this.setSize(1500,1200);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(20,20));
        initializePanels();
    }
    public void initializePanels() {
        rightPanel = new JPanel();
        upperPanel = new JPanel();
        navPanel = new JPanel();
        leftPanel= new JPanel();

        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;

        leftPanel.setPreferredSize(new Dimension(100, height));
        rightPanel.setPreferredSize(new Dimension(100,height));
        navPanel.setPreferredSize(new Dimension(width,100));
        upperPanel.setPreferredSize(new Dimension(width,100));
        upperPanel.setBackground(Color.GRAY.darker());
        upperPanel.setOpaque(true);
        rightPanel.setBackground(Color.GRAY.darker());
        rightPanel.setOpaque(true);
        leftPanel.setBackground(Color.GRAY.darker());
        leftPanel.setOpaque(true);
        navPanel.setBackground(Color.GRAY.darker());
        navPanel.setOpaque(true);

        this.add(upperPanel, BorderLayout.NORTH);
        this.add(navPanel, BorderLayout.SOUTH);
    }

    public JPanel getLeftPanel() {
        return leftPanel;
    }
    public JPanel getRightPanel() {
        return rightPanel;
    }
    public JPanel getUpperPanel() {
        return upperPanel;
    }
    public JPanel getNavPanel(){
        return navPanel;
    }
}