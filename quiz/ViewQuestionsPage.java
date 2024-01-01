



import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewQuestionsPage {
    public ViewQuestionsPage() {
        JFrame frame = new JFrame("View Questions");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane);

        try {
    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/QUIZ", "root", "");
    String query = "SELECT * FROM multiplechoice question_text " +
                   "JOIN blank_space question_text ON question_text.courseid =question_text.courseid " +
                   "JOIN true_false question_text ON question_text.courseid = question_text.courseid " +
                   "WHERE question_text.courseid = ?";
    PreparedStatement ps = con.prepareStatement(query);
    
    // Set the course ID based on the selected course
    int selectedCourseId = getSelectedCourseId(); // Replace with your logic to get the selected course ID
    ps.setInt(1, selectedCourseId);
    
    ResultSet rs = ps.executeQuery();
    
    while (rs.next()) {
        // Retrieve and process the questions
        // Example: String question = rs.getString("question_column_name");
        // ...
    }
    
    con.close();
} catch (SQLException e) {
    e.printStackTrace();
}


        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Run the ViewQuestionsPage as a standalone application for testing
        new ViewQuestionsPage();
    }

    void setVisible(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private int getSelectedCourseId() {
    return 1;
    }
}

