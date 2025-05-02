package org.example;
import java.awt.*;
import javax.swing.*;

public abstract class Standard extends JFrame{
    protected JPanel leftPanel, rightPanel, upperPanel,navPanel;
    public Standard(){
        this.setSize(1500,1200);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        initializePanels();
    }
    public void initializePanels() {
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        upperPanel = new JPanel();
        navPanel = new JPanel();

        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;

        leftPanel.setPreferredSize(new Dimension(100, height));
        rightPanel.setPreferredSize(new Dimension(100,height));
        navPanel.setPreferredSize(new Dimension(width,100));
        upperPanel.setPreferredSize(new Dimension(width,100));
        upperPanel.setBackground(Color.GRAY.darker());
        upperPanel.setOpaque(true);
        leftPanel.setBackground(Color.GRAY.darker());
        leftPanel.setOpaque(true);
        rightPanel.setBackground(Color.GRAY.darker());
        rightPanel.setOpaque(true);
        navPanel.setBackground(Color.GRAY.darker());
        navPanel.setOpaque(true);

        this.setLayout(new BorderLayout());
        this.add(leftPanel, BorderLayout.WEST);
        this.add(rightPanel, BorderLayout.EAST);
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