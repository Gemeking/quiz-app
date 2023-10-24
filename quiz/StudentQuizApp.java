import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Desktop;
import java.net.URI;

public class StudentQuizApp extends JFrame {
    private JComboBox<String> quizSelector;
    private JButton startQuizButton;

    public StudentQuizApp() {
        setTitle("Student Quiz Application");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] quizzes = {"Quiz 1", "Quiz 2", "Quiz 3"};
        quizSelector = new JComboBox<>(quizzes);
        startQuizButton = new JButton("Start Quiz");

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(new JLabel("Select a quiz:"));
        add(quizSelector);
        add(startQuizButton);

        startQuizButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedQuiz = (String) quizSelector.getSelectedItem();
                openQuizWebPage(selectedQuiz);
            }
        });
    }

    private void openQuizWebPage(String quizName) {
        try {
        String url = "http://localhost/";

        if (quizName.equals("Quiz 1")) {
            url += "jj.html";
        } else if (quizName.equals("Quiz 2")) {
            url += "jj.html";
        } else if (quizName.equals("Quiz 3")) {
            url += "jj.html";
        } else {
            System.out.println("Invalid quiz selection");
            return;
        }

        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                desktop.browse(new URI(url));
            } else {
                System.out.println("Desktop browsing is not supported on this platform");
            }
        } else {
            Runtime.getRuntime().exec("xdg-open " + url); // Try using xdg-open for Linux systems
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StudentQuizApp().setVisible(true);
            }
        });
    }
}

