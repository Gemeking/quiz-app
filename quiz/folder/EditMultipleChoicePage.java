package folder;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.sql.*;

public class EditMultipleChoicePage extends JPanel {
    private Connection connection;
    private JComboBox<String> courseComboBox;
    private JComboBox<String> questionComboBox;
    private JPanel formPanel;
    private JTextArea questionTextArea;
    private JTextField optionATextField;
    private JTextField optionBTextField;
    private JTextField optionCTextField;
    private JTextField optionDTextField;
    private JTextField correctAnswerTextField;
    private JButton editButton;

    public EditMultipleChoicePage() {
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
        populateQuestionComboBox((String) courseComboBox.getSelectedItem());
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
        formPanel.setBackground(Color.WHITE);
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
                    "SELECT multipleId FROM multiplechoice " +
                            "WHERE courseid = (SELECT courseid FROM course WHERE cname = ?)"
            );

            statement.setString(1, selectedCourse);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String questionID = resultSet.getString("multipleId");
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
            displaySelectedMultipleChoiceQuestion(selectedCourse, selectedQuestionID);
        }
        revalidate();
        repaint();
    }

    private void displaySelectedMultipleChoiceQuestion(String selectedCourse, String selectedQuestionID) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT question_text, option_a, option_b, option_c, option_d, correct_answer " +
                            "FROM multiplechoice WHERE courseid = (SELECT courseid FROM course WHERE cname = ?) " +
                            "AND multipleId = ?"
            );
            statement.setString(1, selectedCourse);
            statement.setString(2, selectedQuestionID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String questionText = resultSet.getString("question_text");
                String optionA = resultSet.getString("option_a");
                String optionB = resultSet.getString("option_b");
                String optionC = resultSet.getString("option_c");
                String optionD = resultSet.getString("option_d");
                String correctAnswer = resultSet.getString("correct_answer");

                questionTextArea = new JTextArea(questionText);
                optionATextField = new JTextField(optionA);
                optionBTextField = new JTextField(optionB);
                optionCTextField = new JTextField(optionC);
                optionDTextField = new JTextField(optionD);
                correctAnswerTextField = new JTextField(correctAnswer);
                editButton = new JButton("Edit Multiple Choice Question");

                editButton.setBackground(Color.GREEN);
                editButton.setForeground(Color.WHITE);

                editButton.addActionListener(e ->
                        saveEditedMultipleChoiceQuestion(selectedCourse, questionTextArea.getText(),
                                optionATextField.getText(), optionBTextField.getText(),
                                optionCTextField.getText(), optionDTextField.getText(),
                                correctAnswerTextField.getText(), selectedQuestionID)
                );

                formPanel.add(new JLabel("Multiple Choice Question:"));
                formPanel.add(questionTextArea);
                formPanel.add(new JLabel("Option A:"));
                formPanel.add(optionATextField);
                formPanel.add(new JLabel("Option B:"));
                formPanel.add(optionBTextField);
                formPanel.add(new JLabel("Option C:"));
                formPanel.add(optionCTextField);
                formPanel.add(new JLabel("Option D:"));
                formPanel.add(optionDTextField);
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

    private void saveEditedMultipleChoiceQuestion(String selectedCourse, String editedQuestion,
                                                  String editedOptionA, String editedOptionB,
                                                  String editedOptionC, String editedOptionD,
                                                  String editedCorrectAnswer, String selectedQuestionID) {
        try {
            PreparedStatement updateStatement = connection.prepareStatement(
                    "UPDATE multiplechoice SET question_text = ?, option_a = ?, option_b = ?, option_c = ?, option_d = ?, correct_answer = ? " +
                            "WHERE courseid = (SELECT courseid FROM course WHERE cname = ?) " +
                            "AND multipleId = ?"
            );
            updateStatement.setString(1, editedQuestion);
            updateStatement.setString(2, editedOptionA);
            updateStatement.setString(3, editedOptionB);
            updateStatement.setString(4, editedOptionC);
            updateStatement.setString(5, editedOptionD);
            updateStatement.setString(6, editedCorrectAnswer);
            updateStatement.setString(7, selectedCourse);
            updateStatement.setString(8, selectedQuestionID);

            int rowsAffected = updateStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Multiple Choice question updated successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update Multiple Choice question.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating Multiple Choice question");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Edit Multiple Choice Question Page");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setContentPane(new EditMultipleChoicePage());
            frame.setVisible(true);
        });
    }
}
