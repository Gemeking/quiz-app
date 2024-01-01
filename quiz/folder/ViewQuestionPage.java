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
import java.sql.*;

public class ViewQuestionPage extends JPanel {
    private Connection connection;
    private JComboBox<String> courseComboBox;
    private JPanel questionPanel;
    private ButtonGroup buttonGroup;

    public ViewQuestionPage() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/QUIZ", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error connecting to the database");
            System.exit(1);
        }

        setLayout(null);

        JLabel courseLabel = new JLabel("Select Course:");
        courseLabel.setBounds(100, 100, 400, 55);
        courseLabel.setFont(new Font("Tahoma", Font.BOLD, 48));
        add(courseLabel);

        courseComboBox = new JComboBox<>();
        populateCourseComboBox();
        courseComboBox.setBounds(500, 100, 400, 55);
        add(courseComboBox);

        JButton viewQuestionsButton = new JButton("View Questions");
        viewQuestionsButton.setBounds(500, 200, 400, 55);
        viewQuestionsButton.setFont(new Font("Tahoma", Font.BOLD, 24));
        add(viewQuestionsButton);

        questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(questionPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(50, 300, 900, 400);
        add(scrollPane);

        buttonGroup = new ButtonGroup();

        viewQuestionsButton.addActionListener(e -> {
            String selectedCourse = (String) courseComboBox.getSelectedItem();
            int courseId = getCourseId(selectedCourse);

            if (courseId != -1) {
                displayQuestions(courseId);
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

    private void displayQuestions(int courseId) {
        try {
            questionPanel.removeAll();

            // Fetch questions from true_false
            displayTrueFalseQuestions(courseId);

            // Fetch questions from multiplechoice
            displayMultipleChoiceQuestions(courseId);

            // Fetch questions from blank_space
            displayBlankSpaceQuestions(courseId);

            questionPanel.revalidate();
            questionPanel.repaint();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   private void displayTrueFalseQuestions(int courseId) throws SQLException {
    PreparedStatement pstmtTrueFalse = connection.prepareStatement("SELECT T_FId, question_text, correct_answer FROM true_false WHERE courseid = ?");
    pstmtTrueFalse.setInt(1, courseId);
    ResultSet rsTrueFalse = pstmtTrueFalse.executeQuery();

    while (rsTrueFalse.next()) {
        int trueFalseId = rsTrueFalse.getInt("T_FId");
        String question = rsTrueFalse.getString("question_text");
        String correctAnswer = rsTrueFalse.getString("correct_answer");

        JLabel roleNumberLabel = new JLabel("Role Number: " + trueFalseId + ", Question: " + question + ", Correct Answer: " + correctAnswer);
        roleNumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));

        questionPanel.add(roleNumberLabel);
         JLabel emptySpaceLabel = new JLabel(" ");
        questionPanel.add(emptySpaceLabel);
    }
}

private void displayMultipleChoiceQuestions(int courseId) throws SQLException {
    PreparedStatement pstmtMultipleChoice = connection.prepareStatement("SELECT multipleId, question_text, option_a, option_b, option_c, option_d, correct_answer FROM multiplechoice WHERE courseid = ?");
    pstmtMultipleChoice.setInt(1, courseId);
    ResultSet rsMultipleChoice = pstmtMultipleChoice.executeQuery();

    while (rsMultipleChoice.next()) {
        int multipleChoiceId = rsMultipleChoice.getInt("multipleId");
        String question = rsMultipleChoice.getString("question_text");
        String optionA = rsMultipleChoice.getString("option_a");
        String optionB = rsMultipleChoice.getString("option_b");
        String optionC = rsMultipleChoice.getString("option_c");
        String optionD = rsMultipleChoice.getString("option_d");
        String correctAnswer = rsMultipleChoice.getString("correct_answer");

        JLabel roleNumberLabel = new JLabel("Role Number: " + multipleChoiceId + ", Question: " + question + 
            ", Options: A. " + optionA + ", B. " + optionB + ", C. " + optionC + ", D. " + optionD + 
            ", Correct Answer: " + correctAnswer);
        roleNumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));

        questionPanel.add(roleNumberLabel);
         JLabel emptySpaceLabel = new JLabel(" ");
        questionPanel.add(emptySpaceLabel);
    }
}

private void displayBlankSpaceQuestions(int courseId) throws SQLException {
    PreparedStatement pstmtBlankSpace = connection.prepareStatement("SELECT blankspaceid, question_text FROM blank_space WHERE courseid = ?");
    pstmtBlankSpace.setInt(1, courseId);
    ResultSet rsBlankSpace = pstmtBlankSpace.executeQuery();

    while (rsBlankSpace.next()) {
        int blankSpaceId = rsBlankSpace.getInt("blankspaceid");
        String question = rsBlankSpace.getString("question_text");

        JLabel roleNumberLabel = new JLabel("Role Number: " + blankSpaceId + ", Question: " + question);
        roleNumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));

        questionPanel.add(roleNumberLabel);
         JLabel emptySpaceLabel = new JLabel(" ");
        questionPanel.add(emptySpaceLabel);
    }
}
 public static void main(String[] args) {
        JFrame frame = new JFrame("View Question Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 900);
        frame.setContentPane(new ViewQuestionPage());
        frame.setVisible(true);
    }
}

