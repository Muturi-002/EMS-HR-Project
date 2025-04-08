import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeGUI {
    private JFrame frame;
    private JPanel panel;
    private JLabel lblEmployeeId, lblName, lblJobTitle;
    private JTextField txtEmployeeId, txtName, txtJobTitle;
    private JButton btnSave, btnClear;

    public EmployeeGUI() {
        // Create the frame
        frame = new JFrame("Employee Information");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        
        // Create the panel
        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        // Initialize components
        lblEmployeeId = new JLabel("Employee ID:");
        lblName = new JLabel("Name:");
        lblJobTitle = new JLabel("Job Title:");

        txtEmployeeId = new JTextField();
        txtName = new JTextField();
        txtJobTitle = new JTextField();

        btnSave = new JButton("Save");
        btnClear = new JButton("Clear");

        // Add components to the panel
        panel.add(lblEmployeeId);
        panel.add(txtEmployeeId);
        panel.add(lblName);
        panel.add(txtName);
        panel.add(lblJobTitle);
        panel.add(txtJobTitle);
        panel.add(btnSave);
        panel.add(btnClear);

        // Add panel to the frame
        frame.add(panel);
        
        // Add action listener for the Save button
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveEmployee();
            }
        });

        // Add action listener for the Clear button
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
        
        // Make the frame visible
        frame.setVisible(true);
    }

    private void saveEmployee() {
        String employeeId = txtEmployeeId.getText();
        String name = txtName.getText();
        String jobTitle = txtJobTitle.getText();

        if (employeeId.isEmpty() || name.isEmpty() || jobTitle.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, "Employee saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            // Here you can add code to save the employee info to a database or a file
        }
    }

    private void clearFields() {
        txtEmployeeId.setText("");
        txtName.setText("");
        txtJobTitle.setText("");
    }

    public static void main(String[] args) {
        // Run the GUI on the Event Dispatch Thread for thread safety
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EmployeeGUI();
            }
        });
    }
}
