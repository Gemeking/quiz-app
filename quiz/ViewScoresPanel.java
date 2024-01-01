import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewScoresPanel extends JPanel {
    private JTable scoreTable;
    private JScrollPane scrollPane;
    private JLabel topScorerLabel;
    private Connection con; // Database connection

    public ViewScoresPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));

        scoreTable = new JTable();
        scoreTable.setFont(new Font("Arial", Font.PLAIN, 14));
        scoreTable.setRowHeight(25);

        scrollPane = new JScrollPane(scoreTable);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("Student Scores");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(titleLabel);

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topScorerLabel = new JLabel();
        topScorerLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        infoPanel.add(topScorerLabel);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(topPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(infoPanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);

        // Database connection
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/QUIZ", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage());
        }

        displayScores();
    }

    public void displayScores() {
        try {
            String query = "SELECT user_scores.sID, users.sName, course.cname, user_scores.score " +
                    "FROM user_scores " +
                    "INNER JOIN users ON user_scores.sID = users.sID " +
                    "INNER JOIN course ON user_scores.courseid = course.courseid";

            PreparedStatement pstmt = con.prepareStatement(query);

            ResultSet resultSet = pstmt.executeQuery();

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            String[] columnNames = {"Student ID", "Name", "Course Name", "Score"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            int topScore = Integer.MIN_VALUE;
            String topScorer = "";

            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    rowData[i] = resultSet.getObject(i + 1);
                }
                model.addRow(rowData);

                int score = resultSet.getInt("score");
                if (score > topScore) {
                    topScore = score;
                    topScorer = resultSet.getString("sName");
                }
            }

            scoreTable.setModel(model);

            resultSet.close();
            pstmt.close();

            if (!topScorer.isEmpty()) {
                topScorerLabel.setText("Top Scorer: " + topScorer + " with a score of " + topScore);
            } else {
                topScorerLabel.setText("No scores found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching scores: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("View Scores");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            ViewScoresPanel viewScoresPanel = new ViewScoresPanel();
            frame.add(viewScoresPanel);

            frame.setVisible(true);
        });
    }
}
