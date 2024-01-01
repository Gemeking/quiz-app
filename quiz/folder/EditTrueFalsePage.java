package folder;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.sql.*;

public class EditTrueFalsePage extends JPanel {
    private Connection connection;
    private JComboBox<String> courseComboBox;
    private JComboBox<String> questionComboBox;
    private JPanel formPanel;
    private JTextArea questionTextArea;
    private JTextField correctAnswerTextField;
    private JButton editButton;

    public EditTrueFalsePage() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/QUIZ", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error connecting to the database");
            System.exit(1);
        }

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.setBackground(Color.LIGHT_GRAY);

        courseComboBox = new JComboBox<>();
        populateCourseComboBox();
        courseComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedCourse = (String) courseComboBox.getSelectedItem();
                populateQuestionComboBox(selectedCourse);
            }
        });
        topPanel.add(new JLabel("Select Course:"));
        topPanel.add(courseComboBox);

        questionComboBox = new JComboBox<>();
        topPanel.add(new JLabel("Select Question:"));
        topPanel.add(questionComboBox);

        JButton selectButton = new JButton("Select Question");
        selectButton.setBackground(Color.BLUE);
        selectButton.setForeground(Color.WHITE);
        selectButton.addActionListener(e -> displayQuestionForm());
        topPanel.add(selectButton);

        add(topPanel, BorderLayout.NORTH);

        formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 1));
        add(formPanel, BorderLayout.CENTER);
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
            JOptionPane.showMessageDialog(null, "Error fetching courses");
        }
    }

    private void populateQuestionComboBox(String selectedCourse) {
        try {
            questionComboBox.removeAllItems();

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT T_FId FROM true_false " +
                            "WHERE courseid = (SELECT courseid FROM course WHERE cname = ?)"
            );

            statement.setString(1, selectedCourse);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String questionID = resultSet.getString("T_FId");
                questionComboBox.addItem(questionID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching questions");
        }
    }

    private void displayQuestionForm() {
        formPanel.removeAll();
        String selectedCourse = (String) courseComboBox.getSelectedItem();
        String selectedQuestionID = (String) questionComboBox.getSelectedItem();

        if (selectedCourse != null && selectedQuestionID != null) {
            displaySelectedTrueFalseQuestion(selectedCourse, selectedQuestionID);
        }
        revalidate();
        repaint();
    }

    private void displaySelectedTrueFalseQuestion(String selectedCourse, String selectedQuestionID) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT question_text, correct_answer FROM true_false " +
                            "WHERE courseid = (SELECT courseid FROM course WHERE cname = ?) " +
                            "AND T_FId = ?"
            );
            statement.setString(1, selectedCourse);
            statement.setString(2, selectedQuestionID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String questionText = resultSet.getString("question_text");
                String correctAnswer = resultSet.getString("correct_answer");

                questionTextArea = new JTextArea(questionText);
                correctAnswerTextField = new JTextField(correctAnswer);
                editButton = new JButton("Edit True/False Question");
                editButton.setBackground(Color.GREEN);
                editButton.setForeground(Color.WHITE);

                editButton.addActionListener(e ->
                        saveEditedTrueFalseQuestion(selectedCourse, questionTextArea.getText(), correctAnswerTextField.getText(), selectedQuestionID)
                );

                formPanel.add(new JLabel("True/False Question:"));
                formPanel.add(questionTextArea);
                formPanel.add(new JLabel("Correct Answer:"));
                formPanel.add(correctAnswerTextField);
                formPanel.add(editButton);
            } else {
                formPanel.add(new JLabel("Selected question not found for the course."));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching selected question");
        }

        revalidate();
        repaint();
    }

    private void saveEditedTrueFalseQuestion(String selectedCourse, String editedQuestion, String editedAnswer, String selectedQuestionID) {
        try {
            PreparedStatement updateStatement = connection.prepareStatement(
                    "UPDATE true_false SET question_text = ?, correct_answer = ? " +
                            "WHERE courseid = (SELECT courseid FROM course WHERE cname = ?) " +
                            "AND T_FId = ?"
            );
            updateStatement.setString(1, editedQuestion);
            updateStatement.setString(2, editedAnswer);
            updateStatement.setString(3, selectedCourse);
            updateStatement.setString(4, selectedQuestionID);

            int rowsAffected = updateStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "True/False question updated successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update True/False question.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating True/False question");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Edit True/False Question Page");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setContentPane(new EditTrueFalsePage());
            frame.setVisible(true);
        });
    }
}
