


import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class ViewScorePage extends JPanel {
    private int userID; // Changed to int for simplicity
    private JTable scoreTable;

    public ViewScorePage() {
        initializeComponents();
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Your Scores");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        scoreTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(scoreTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void setUserID(int userID) {
        this.userID = userID;
        fetchAndDisplayScores();
    }

    private void fetchAndDisplayScores() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/QUIZ", "root", "");
            PreparedStatement ps = con.prepareStatement("SELECT * FROM user_scores WHERE user_id = ? AND score_id = (SELECT MAX(score_id) FROM user_scores WHERE user_id = ?)");
            ps.setInt(1, userID);
            ps.setInt(2, userID);
            ResultSet rs = ps.executeQuery();

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Score ID");
            model.addColumn("Course ID");
            model.addColumn("Score");

            while (rs.next()) {
                int scoreId = rs.getInt("score_id");
                int courseId = rs.getInt("course_id");
                int score = rs.getInt("score");

                model.addRow(new Object[]{scoreId, courseId, score});
            }

            scoreTable.setModel(model);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
