import javax.swing.*;
import java.awt.*;

public class AttendanceUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("HR Attendance System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        JLabel label = new JLabel("Mark Attendance (8:00 AM - 5:00 PM):");
        JCheckBox checkbox = new JCheckBox("Present");
        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(e -> {
            if (checkbox.isSelected()) {
                System.out.println("Attendance marked for employee.");
                // You could call attendance.markAttendance(LocalTime.now(), LocalTime.of(17, 0)) here
            } else {
                System.out.println("Attendance not marked.");
            }
        });

        panel.add(label);
        panel.add(checkbox);
        panel.add(submitButton);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
}
