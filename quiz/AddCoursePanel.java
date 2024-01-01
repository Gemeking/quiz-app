

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AddCoursePanel extends JPanel {
    private Connection connection;
    private JTextField courseNameField;

    public AddCoursePanel() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/QUIZ", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error connecting to the database");
            System.exit(1);
        }

        setLayout(null);
        setBackground(Color.decode("#ecf0f1"));

        JLabel courseNameLabel = new JLabel("Course Name:");
        courseNameLabel.setBounds(10, 20, 100, 25);
        add(courseNameLabel);

        courseNameField = new JTextField();
        courseNameField.setBounds(120, 20, 200, 25);
        add(courseNameField);

        JButton addCourseButton = new JButton("Add Course");
        addCourseButton.setBounds(10, 50, 150, 25);
        addCourseButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        add(addCourseButton);

        addCourseButton.addActionListener(e -> addCourseToDatabase());
    }

    private void addCourseToDatabase() {
        String courseName = courseNameField.getText();

        if (!courseName.isEmpty()) {
            try {
                PreparedStatement pstmt = connection.prepareStatement("INSERT INTO course (cname) VALUES (?)");
                pstmt.setString(1, courseName);

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Course added successfully.");
                    courseNameField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Error adding course. Please try again.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a course name.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Add Course Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(350, 150);
            frame.setContentPane(new AddCoursePanel());
            frame.setVisible(true);
        });
    }
}
