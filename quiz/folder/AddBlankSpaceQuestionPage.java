package folder;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author hab
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AddBlankSpaceQuestionPage extends JPanel {
    private Connection connection;
    private JComboBox<String> courseComboBox;
    private JTextArea questionTextArea;
    private JTextField correctAnswerTextField;

    public AddBlankSpaceQuestionPage() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/QUIZ", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error connecting to the database");
            System.exit(1);
        }

        setLayout(null);
        setBackground(Color.decode("#ecf0f1"));

        courseComboBox = new JComboBox<>();
        populateCourseComboBox();
        courseComboBox.setBounds(10, 20, 100, 25);
        add(courseComboBox);

        JLabel questionLabel = new JLabel("Question:");
        questionLabel.setBounds(10, 50, 200, 25);
        questionLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        add(questionLabel);

        questionTextArea = new JTextArea();
        questionTextArea.setBounds(10, 80, 400, 80);
        questionTextArea.setFont(new Font("Tahoma", Font.PLAIN, 12));
        add(questionTextArea);

        JLabel correctAnswerLabel = new JLabel("Correct Answer:");
        correctAnswerLabel.setBounds(10, 180, 200, 25);
        correctAnswerLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        add(correctAnswerLabel);

        correctAnswerTextField = new JTextField();
        correctAnswerTextField.setBounds(10, 210, 400, 25);
        correctAnswerTextField.setFont(new Font("Tahoma", Font.PLAIN, 12));
        add(correctAnswerTextField);

        JButton addButton = new JButton("Add Blank Space Question");
        addButton.setBounds(10, 250, 200, 25);
        addButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        add(addButton);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addBlankSpaceQuestionToDatabase();
            }
        });
    }

    private void populateCourseComboBox() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT cname FROM course");

            while (rs.next()) {
                String courseName = rs.getString("cname");
                courseComboBox.addItem(courseName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addBlankSpaceQuestionToDatabase() {
        String selectedCourse = (String) courseComboBox.getSelectedItem();
        int courseId = getCourseId(selectedCourse);

        String question = questionTextArea.getText();
        String correctAnswerBS = correctAnswerTextField.getText();

        try {
            // Add Blank Space Question
            if (!question.isEmpty() && !correctAnswerBS.isEmpty()) {
                PreparedStatement pstmtBS = connection.prepareStatement("INSERT INTO blank_space (courseid, question_text, correct_answer) VALUES (?, ?, ?)");
                pstmtBS.setInt(1, courseId);
                pstmtBS.setString(2, question);
                pstmtBS.setString(3, correctAnswerBS);
                int rowsAffectedBS = pstmtBS.executeUpdate();

                if (rowsAffectedBS > 0) {
                    JOptionPane.showMessageDialog(this, "Blank Space question added successfully.");
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Error adding Blank Space question. Please try again.");
                }
            } else {
                // Show error message if question or correct answer is not filled
                JOptionPane.showMessageDialog(this, "Please enter the question and the correct answer for Blank Space question.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private int getCourseId(String courseName) {
        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT courseid FROM course WHERE cname = ?");
            pstmt.setString(1, courseName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("courseid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    private void clearFields() {
        questionTextArea.setText("");
        correctAnswerTextField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Add Blank Space Question Page");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 400);
            frame.setContentPane(new AddBlankSpaceQuestionPage());
            frame.setVisible(true);
        });
    }
}

