import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Homepage extends JFrame implements ActionListener {
    private JButton teacherButton;
    private JLabel welcomeLabel;

    public Homepage() {
        setTitle("Teacher Environment");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(null);
        ImageIcon icon = new ImageIcon("/home/hab/Videos/a.png");
        setIconImage(icon.getImage());

        ImageIcon backgroundImage = new ImageIcon("/home/hab/Downloads/oo.jpg");
        JLabel background = new JLabel(backgroundImage);
        background.setBounds(0, 0, backgroundImage.getIconWidth(), backgroundImage.getIconHeight());
        add(background);

        welcomeLabel = new JLabel("<html><center><p style='color:white; font-size:24pt;'>&nbsp;&nbsp;Welcome to the Teacher Environment!</p></center></html>");
        welcomeLabel.setBounds(50, 20, 500, 80);

        teacherButton = new JButton("Teacher Login");
        Font customFont = new Font("Arial", Font.BOLD, 30);
        teacherButton.setFont(customFont);

        teacherButton.addActionListener(this);

        teacherButton.setBounds(150, 150, 300, 80);
        teacherButton.setBackground(new Color(144, 238, 144)); // Light Green

        background.add(welcomeLabel);
        background.add(teacherButton);
        setSize(700, 380);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == teacherButton) {
            LoginFrame f = new LoginFrame();
            f.setVisible(true);
            dispose(); // Close the current frame
        }
    }

    public static void main(String[] args) {
        new Homepage();
    }
}
