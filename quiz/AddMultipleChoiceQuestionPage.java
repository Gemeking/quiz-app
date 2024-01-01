


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

public class AddMultipleChoiceQuestionPage extends JPanel {
    private Connection connection;
    private JComboBox<String> courseComboBox;
    private JTextArea questionTextArea;
    private JTextField optionATextField;
    private JTextField optionBTextField;
    private JTextField optionCTextField;
    private JTextField optionDTextField;
    private JTextField correctAnswerTextField;

    public AddMultipleChoiceQuestionPage() {
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

        JLabel optionALabel = new JLabel("Option A:");
        optionALabel.setBounds(10, 180, 200, 25);
        optionALabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        add(optionALabel);

        optionATextField = new JTextField();
        optionATextField.setBounds(10, 210, 400, 25);
        optionATextField.setFont(new Font("Tahoma", Font.PLAIN, 12));
        add(optionATextField);

        JLabel optionBLabel = new JLabel("Option B:");
        optionBLabel.setBounds(10, 240, 200, 25);
        optionBLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        add(optionBLabel);

        optionBTextField = new JTextField();
        optionBTextField.setBounds(10, 270, 400, 25);
        optionBTextField.setFont(new Font("Tahoma", Font.PLAIN, 12));
        add(optionBTextField);

        JLabel optionCLabel = new JLabel("Option C:");
        optionCLabel.setBounds(10, 300, 200, 25);
        optionCLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        add(optionCLabel);

        optionCTextField = new JTextField();
        optionCTextField.setBounds(10, 330, 400, 25);
        optionCTextField.setFont(new Font("Tahoma", Font.PLAIN, 12));
        add(optionCTextField);

        JLabel optionDLabel = new JLabel("Option D:");
        optionDLabel.setBounds(10, 360, 200, 25);
        optionDLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        add(optionDLabel);

        optionDTextField = new JTextField();
        optionDTextField.setBounds(10, 390, 400, 25);
        optionDTextField.setFont(new Font("Tahoma", Font.PLAIN, 12));
        add(optionDTextField);

        JLabel correctAnswerLabel = new JLabel("Correct Answer:");
        correctAnswerLabel.setBounds(10, 420, 200, 25);
        correctAnswerLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        add(correctAnswerLabel);

        correctAnswerTextField = new JTextField();
        correctAnswerTextField.setBounds(10, 450, 400, 25);
        correctAnswerTextField.setFont(new Font("Tahoma", Font.PLAIN, 12));
        add(correctAnswerTextField);

        JButton addButton = new JButton("Add Multiple Choice Question");
        addButton.setBounds(10, 490, 200, 25);
        addButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        add(addButton);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addMultipleChoiceQuestionToDatabase();
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

    private void addMultipleChoiceQuestionToDatabase() {
        String selectedCourse = (String) courseComboBox.getSelectedItem();
        int courseId = getCourseId(selectedCourse);

        String question = questionTextArea.getText();
        String optionA = optionATextField.getText();
        String optionB = optionBTextField.getText();
        String optionC = optionCTextField.getText();
        String optionD = optionDTextField.getText();
        String correctAnswerMC = correctAnswerTextField.getText();

        try {
            // Add Multiple Choice Question
            if (!question.isEmpty() && !optionA.isEmpty() && !optionB.isEmpty() && !optionC.isEmpty() && !optionD.isEmpty() && !correctAnswerMC.isEmpty()) {
                PreparedStatement pstmtMC = connection.prepareStatement("INSERT INTO multiplechoice (courseid, question_text, option_a, option_b, option_c, option_d, correct_answer) VALUES (?, ?, ?, ?, ?, ?, ?)");
                pstmtMC.setInt(1, courseId);
                pstmtMC.setString(2, question);
                pstmtMC.setString(3, optionA);
                pstmtMC.setString(4, optionB);
                pstmtMC.setString(5, optionC);
                pstmtMC.setString(6, optionD);
                pstmtMC.setString(7, correctAnswerMC);
                int rowsAffectedMC = pstmtMC.executeUpdate();

                if (rowsAffectedMC > 0) {
                    JOptionPane.showMessageDialog(this, "Multiple Choice question added successfully.");
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Error adding Multiple Choice question. Please try again.");
                }
            } else {
                // Show error message if options or correct answer is not filled
                JOptionPane.showMessageDialog(this, "Please enter the question, all options, and the correct answer for Multiple Choice question.");
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
        optionATextField.setText("");
        optionBTextField.setText("");
        optionCTextField.setText("");
        optionDTextField.setText("");
        correctAnswerTextField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Add Multiple Choice Question Page");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 600);
            frame.setContentPane(new AddMultipleChoiceQuestionPage());
            frame.setVisible(true);
        });
    }
}

