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

public class AddTrueFalseQuestionPage extends JPanel {
    private Connection connection;
    private JComboBox<String> courseComboBox;
    private JTextArea trueFalseTextArea;
    private JRadioButton trueRadioButton;
    private JRadioButton falseRadioButton;

    public AddTrueFalseQuestionPage() {
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

        JLabel trueFalseLabel = new JLabel("True or False Question:");
        trueFalseLabel.setBounds(10, 50, 200, 25);
        trueFalseLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        add(trueFalseLabel);

        trueFalseTextArea = new JTextArea();
        trueFalseTextArea.setBounds(10, 80, 400, 80);
        trueFalseTextArea.setFont(new Font("Tahoma", Font.PLAIN, 12));
        add(trueFalseTextArea);

        trueRadioButton = new JRadioButton("True");
        trueRadioButton.setBounds(10, 180, 100, 25);
        trueRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
        add(trueRadioButton);

        falseRadioButton = new JRadioButton("False");
        falseRadioButton.setBounds(120, 180, 100, 25);
        falseRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
        add(falseRadioButton);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(trueRadioButton);
        buttonGroup.add(falseRadioButton);

        JButton addButton = new JButton("Add True/False Question");
        addButton.setBounds(10, 220, 200, 25);
        addButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        add(addButton);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addTrueFalseQuestionToDatabase();
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

    private void addTrueFalseQuestionToDatabase() {
        String selectedCourse = (String) courseComboBox.getSelectedItem();
        int courseId = getCourseId(selectedCourse);

        String trueFalseQuestion = trueFalseTextArea.getText();

        // Check if correct answer is provided for True/False
        if (trueRadioButton.isSelected() || falseRadioButton.isSelected()) {
            try {
                // Add True/False Question
                PreparedStatement pstmtTF = connection.prepareStatement("INSERT INTO true_false (courseid, question_text, correct_answer) VALUES (?, ?, ?)");
                pstmtTF.setInt(1, courseId);
                pstmtTF.setString(2, trueFalseQuestion);

                // Convert boolean to text
                String correctAnswerTF = trueRadioButton.isSelected() ? "True" : "False";
                pstmtTF.setString(3, correctAnswerTF);

                int rowsAffectedTF = pstmtTF.executeUpdate();

                if (rowsAffectedTF > 0) {
                    JOptionPane.showMessageDialog(this, "True/False question added successfully.");
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Error adding True/False question. Please try again.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select the correct answer for the True/False question.");
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
        trueFalseTextArea.setText("");
        trueRadioButton.setSelected(false);
        falseRadioButton.setSelected(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Add True/False Question Page");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 300);
            frame.setContentPane(new AddTrueFalseQuestionPage());
            frame.setVisible(true);
        });
    }
}
