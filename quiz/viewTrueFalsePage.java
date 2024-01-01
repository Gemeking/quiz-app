



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

public class viewTrueFalsePage extends JPanel {
    private Connection connection;
    private JComboBox<String> courseComboBox;
    private JPanel radioPanel;
    private ButtonGroup buttonGroup;

    public viewTrueFalsePage() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/QUIZ", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error connecting to the database");
            System.exit(1);
        }

        setLayout(null);
        setBackground(Color.decode("#ecf0f1")); // Setting overall background color

        JLabel courseLabel = new JLabel("Select Course:");
        courseLabel.setBounds(100, 100, 400, 55);
        courseLabel.setFont(new Font("Tahoma", Font.BOLD, 48));
        add(courseLabel);

        courseComboBox = new JComboBox<>();
        populateCourseComboBox();
        courseComboBox.setBounds(500, 95, 440, 55);
        add(courseComboBox);

        JButton viewQuestionsButton = new JButton("View Questions");
        viewQuestionsButton.setBounds(500, 150, 440, 55);
        viewQuestionsButton.setFont(new Font("Tahoma", Font.BOLD, 24));
        viewQuestionsButton.setBackground(Color.decode("#3498db"));
        viewQuestionsButton.setForeground(Color.WHITE);
        add(viewQuestionsButton);

        radioPanel = new JPanel();
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS));
        radioPanel.setBackground(Color.WHITE); // Setting background color for the question list panel

        JScrollPane scrollPane = new JScrollPane(radioPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(50, 300, 900, 400);
        add(scrollPane);
        
         JLabel j=new JLabel("        TRUE FALSE QUESTIONS ");  
                    j.setFont(new Font("Tahoma", Font.BOLD, 48));
                    j.setForeground(Color.green);
            radioPanel.add(j);

        viewQuestionsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedCourse = (String) courseComboBox.getSelectedItem();
                int courseId = getCourseId(selectedCourse);

                if (courseId != -1) {
                    displayTrueFalseQuestions(courseId);
                }
            }
        });
    }

    private void populateCourseComboBox() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT cname FROM course");

            while (rs.next()) {
                String courseName = rs.getString("cname");
                courseComboBox.setFont(new Font("Tahoma", Font.BOLD, 24));
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

    private void displayTrueFalseQuestions(int courseId) {
        try {
            radioPanel.removeAll();

            // Fetch questions from true_false
            PreparedStatement pstmtTrueFalse = connection.prepareStatement("SELECT question_text FROM true_false WHERE courseid = ?");
            pstmtTrueFalse.setInt(1, courseId);
            ResultSet rsTrueFalse = pstmtTrueFalse.executeQuery();

            JLabel j=new JLabel("TRUE FALSE QUESTIONS ");  
                    j.setFont(new Font("Tahoma", Font.BOLD, 48));
                    j.setForeground(Color.red);
            radioPanel.add(j);
            while (rsTrueFalse.next()) {
                String question = rsTrueFalse.getString("question_text");

                JLabel radioButton = new JLabel("True/False:  " + question);
                JLabel k=new JLabel(" ");
                radioButton.setFont(new Font("Tahoma", Font.PLAIN, 28));
            
               radioPanel.add(k);
                radioPanel.add(radioButton);
            }

            radioPanel.revalidate();
            radioPanel.repaint();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Delete True/False Question Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 900);
        frame.setContentPane(new viewTrueFalsePage());
        frame.setVisible(true);
    }
}

