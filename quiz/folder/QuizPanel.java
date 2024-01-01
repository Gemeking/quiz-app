package folder;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class QuizPanel extends JPanel {
    private JTextField usernameField;
    private JTextArea questionArea;
    private JButton startQuizButton;

    public QuizPanel() {
        // Initialize components
        usernameField = new JTextField(0);
        questionArea = new JTextArea(44, 0);
        questionArea.setEditable(false);
        startQuizButton = new JButton("Start Quiz");

        // Set layout
        setLayout(new BorderLayout());
        add(new JLabel("Enter your username: "), BorderLayout.WEST);
        add(usernameField, BorderLayout.CENTER);
        add(startQuizButton, BorderLayout.EAST);
        add(new JScrollPane(questionArea), BorderLayout.SOUTH);

        // Event handling
        startQuizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                if (!username.isEmpty()) {
                    startQuiz(username);
                } else {
                    JOptionPane.showMessageDialog(QuizPanel.this, "Please enter a username.");
                }
            }
        });
    }

    private void startQuiz(String username) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/QUIZ", "root", "")) {
            // Fetch questions from the database
            fetchAndDisplayQuestions(connection);

            // Get user's answers
            int score = getAndEvaluateUserAnswers(connection);

            // Save user's score to the database
            saveUserScore(connection, username, score);

            // Display the result
            questionArea.append("\nYour quiz score: " + score);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void fetchAndDisplayQuestions(Connection connection) throws SQLException {
        // Implement the logic to fetch questions from the database and display them
        // Use PreparedStatement to avoid SQL injection

        // For example:
        String query = "SELECT * FROM true_false WHERE courseid = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, 1); // Replace 1 with the actual course ID
            ResultSet resultSet = preparedStatement.executeQuery();

            // Display questions
            while (resultSet.next()) {
                questionArea.append("\nQuestion: " + resultSet.getString("question_text"));
                // Display other question details based on the table structure
            }
        }
    }

    private int getAndEvaluateUserAnswers(Connection connection) throws SQLException {
        // Implement the logic to get user answers and evaluate them

        // For example:
        int score = 0;
        // Assume there are 3 questions for simplicity
        for (int i = 1; i <= 3; i++) {
            String userAnswer = JOptionPane.showInputDialog(this, "Enter your answer for question " + i + ": ");

            // Compare the user's answer with the correct answer from the database
            String query = "SELECT correct_answer FROM true_false WHERE T_FId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, i); // Replace i with the actual question ID
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String correctAnswer = resultSet.getString("correct_answer");
                    if (userAnswer != null && userAnswer.equalsIgnoreCase(correctAnswer)) {
                        score++;
                    }
                }
            }
        }

        return score;
    }

    private void saveUserScore(Connection connection, String username, int score) throws SQLException {
        // Implement the logic to save the user's score to the database

        // For example:
        String insertQuery = "INSERT INTO user_scores (username, score) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, score);
            preparedStatement.executeUpdate();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Quiz App");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 300);
                frame.setLocationRelativeTo(null);
                frame.add(new QuizPanel());
                frame.setVisible(true);
            }
        });
    }
}
