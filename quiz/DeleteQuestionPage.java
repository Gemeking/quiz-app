



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class DeleteQuestionPage extends JPanel {
    private Connection connection;
    private JComboBox<String> courseComboBox;
    private JPanel radioPanel;
    private ButtonGroup buttonGroup;
    private JTextArea questionTextArea;


    public DeleteQuestionPage() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/QUIZ", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error connecting to the database");
            System.exit(1);
        }

        setLayout(null);
        setBackground(Color.decode("#ecf0f1")); 

        JLabel courseLabel = new JLabel("Select Course:");
        courseLabel.setBounds(100, 100, 400, 55);
        courseLabel.setFont(new Font("Tahoma", Font.BOLD, 48));
        add(courseLabel);

        courseComboBox = new JComboBox<>();
        populateCourseComboBox();
        courseComboBox.setBounds(500, 95, 400, 55);
        add(courseComboBox);

        JButton viewQuestionsButton = new JButton("View Questions");
        viewQuestionsButton.setBounds(500, 150, 400, 55);
        viewQuestionsButton.setFont(new Font("Tahoma", Font.BOLD, 24));
        viewQuestionsButton.setBackground(Color.decode("#3498db"));
        viewQuestionsButton.setForeground(Color.WHITE);
        add(viewQuestionsButton);

        JButton deleteButton = new JButton("Delete Selected Question");
        deleteButton.setBounds(500, 200, 400, 55);
        deleteButton.setFont(new Font("Tahoma", Font.BOLD, 24));
        deleteButton.setBackground(Color.decode("#e74c3c"));
        deleteButton.setForeground(Color.WHITE);
        add(deleteButton);

        radioPanel = new JPanel();
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS));
        radioPanel.setBackground(Color.WHITE); // Setting background color for the question list panel

        JScrollPane scrollPane = new JScrollPane(radioPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(50, 300, 900, 400);
        add(scrollPane);
        questionTextArea = new JTextArea();
questionTextArea.setBounds(100, 300, 120, 130);
questionTextArea.setFont(new Font("Tahoma", Font.PLAIN, 24));
//add(questionTextArea);


        buttonGroup = new ButtonGroup();

        viewQuestionsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedCourse = (String) courseComboBox.getSelectedItem();
                int courseId = getCourseId(selectedCourse);

                if (courseId != -1) {
                    displayQuestions(courseId);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteSelectedQuestions();
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

    private void deleteSelectedQuestions() {
        Component[] components = radioPanel.getComponents();

        for (Component component : components) {
            if (component instanceof JRadioButton) {
                JRadioButton radioButton = (JRadioButton) component;
                if (radioButton.isSelected()) {
                    String selectedQuestion = radioButton.getText();
                    String questionType = getQuestionType(selectedQuestion);
                    int courseId = getCourseId((String) courseComboBox.getSelectedItem());

                    int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this question?", "Confirmation", JOptionPane.YES_NO_OPTION);

                    if (dialogResult == JOptionPane.YES_OPTION) {
                        try {
                            switch (questionType) {
                                case "True/False":
                                    deleteFromTrueFalse(courseId, selectedQuestion.substring(12).trim());
                                    break;
                                case "Multiple Choice":
                                    deleteFromMultipleChoice(courseId, selectedQuestion.substring(16).trim());
                                    break;
                                case "Blank Space":
                                    deleteFromBlankSpace(courseId, selectedQuestion.substring(12).trim());
                                    break;
                            }

                            displayQuestions(courseId);
                            questionTextArea.setText("Question deleted.");
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                    break;
                }
            }
        }
    }
private String getQuestionType(String selectedQuestion) {
    if (selectedQuestion.startsWith("True/False")) {
        return "True/False";
    } else if (selectedQuestion.startsWith("Multiple Choice")) {
        return "Multiple Choice";
    } else if (selectedQuestion.startsWith("Blank Space")) {
        return "Blank Space";
    }
    return "";
}

private void deleteFromTrueFalse(int courseId, String question) throws SQLException {
    String query = "DELETE FROM true_false WHERE courseid = ? AND question_text = ?";
    
    try (PreparedStatement pstmtTrueFalse = connection.prepareStatement(query)) {
        pstmtTrueFalse.setInt(1, courseId);
        pstmtTrueFalse.setString(2, question);
        
        int rowsAffected = pstmtTrueFalse.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println(rowsAffected + " row(s) deleted from True/False.");
        } else {
            System.out.println("No rows deleted from True/False.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Error deleting from True/False: " + e.getMessage());
    }
}



private void deleteFromMultipleChoice(int courseId, String question) throws SQLException {
    PreparedStatement pstmtMultipleChoice = connection.prepareStatement("DELETE FROM multiplechoice WHERE courseid = ? AND question_text = ?");
    pstmtMultipleChoice.setInt(1, courseId);
    pstmtMultipleChoice.setString(2, question);
    int rowsAffected = pstmtMultipleChoice.executeUpdate();

    if (rowsAffected > 0) {
        System.out.println("Multiple Choice question deleted successfully.");
    } else {
        System.out.println("No Multiple Choice question deleted.");
    }
}

private void deleteFromBlankSpace(int courseId, String question) throws SQLException {
    PreparedStatement pstmtBlankSpace = connection.prepareStatement("DELETE FROM blank_space WHERE courseid = ? AND question_text = ?");
    pstmtBlankSpace.setInt(1, courseId);
    pstmtBlankSpace.setString(2, question);
    int rowsAffected = pstmtBlankSpace.executeUpdate();

    if (rowsAffected > 0) {
        System.out.println("Blank Space question deleted successfully.");
    } else {
        System.out.println("No Blank Space question deleted.");
    }
}

    private void displayQuestions(int courseId) {
        try {
            radioPanel.removeAll();

            // Fetch questions from true_false
            PreparedStatement pstmtTrueFalse = connection.prepareStatement("SELECT question_text, correct_answer FROM true_false WHERE courseid = ?");
            pstmtTrueFalse.setInt(1, courseId);
            ResultSet rsTrueFalse = pstmtTrueFalse.executeQuery();
            
            while (rsTrueFalse.next()) {
                String question = rsTrueFalse.getString("question_text");
                String correctAnswer = rsTrueFalse.getString("correct_answer");

                JRadioButton radioButton = new JRadioButton("T/F:  " + question);
                radioButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
                buttonGroup.add(radioButton);
                radioPanel.add(radioButton);

                JLabel answerLabel = new JLabel("Answer: " + correctAnswer);
                answerLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
                radioPanel.add(answerLabel);
            }

            // Fetch questions from multiplechoice
            PreparedStatement pstmtMultipleChoice = connection.prepareStatement("SELECT question_text, option_a, option_b, option_c, option_d, correct_answer FROM multiplechoice WHERE courseid = ?");
            pstmtMultipleChoice.setInt(1, courseId);
            ResultSet rsMultipleChoice = pstmtMultipleChoice.executeQuery();

            while (rsMultipleChoice.next()) {
                String question = rsMultipleChoice.getString("question_text");
                String optionA = rsMultipleChoice.getString("option_a");
                String optionB = rsMultipleChoice.getString("option_b");
                String optionC = rsMultipleChoice.getString("option_c");
                String optionD = rsMultipleChoice.getString("option_d");
                String correctAnswer = rsMultipleChoice.getString("correct_answer");

                JRadioButton radioButton = new JRadioButton("Multiple Choice: " + question);
                radioButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
                buttonGroup.add(radioButton);
                radioPanel.add(radioButton);

                JLabel optionALabel = new JLabel("A. " + optionA);
                optionALabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
                radioPanel.add(optionALabel);

                JLabel optionBLabel = new JLabel("B. " + optionB);

                optionBLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
                radioPanel.add(optionBLabel);

                JLabel optionCLabel = new JLabel("C. " + optionC);
                optionCLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
                radioPanel.add(optionCLabel);

                JLabel optionDLabel = new JLabel("D. " + optionD);
                optionDLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
                radioPanel.add(optionDLabel);

                JLabel answerLabel = new JLabel("Correct Answer: " + correctAnswer);
                answerLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
                radioPanel.add(answerLabel);
            }

            // Fetch questions from blank_space
            PreparedStatement pstmtBlankSpace = connection.prepareStatement("SELECT question_text FROM blank_space WHERE courseid = ?");
            pstmtBlankSpace.setInt(1, courseId);
            ResultSet rsBlankSpace = pstmtBlankSpace.executeQuery();

            while (rsBlankSpace.next()) {
                String question = rsBlankSpace.getString("question_text");

                JRadioButton radioButton = new JRadioButton("Blank Space: " + question);
                radioButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
                buttonGroup.add(radioButton);
                radioPanel.add(radioButton);
            }

            radioPanel.revalidate();
            radioPanel.repaint();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Delete Question Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 900);
        frame.setContentPane(new DeleteQuestionPage());
        frame.setVisible(true);
    }
}
