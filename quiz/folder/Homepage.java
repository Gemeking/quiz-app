package folder;


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

public class Homepage extends JFrame implements ActionListener {
    private JButton adminButton;
    private JButton studentButton;
    private JLabel welcomeLabel;

    
    public Homepage() {
        setTitle("Student Quiz App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(null);
        ImageIcon icon = new ImageIcon("/home/hab/Videos/a.png"); 
        setIconImage(icon.getImage());

        ImageIcon backgroundImage = new ImageIcon("/home/hab/Downloads/oo.jpg");
        JLabel background = new JLabel(backgroundImage);
        background.setBounds(0, 0, backgroundImage.getIconWidth(), backgroundImage.getIconHeight());
        add(background);
        
        JLabel welcomeLabel = new JLabel("<html><center><p style='color:white; '>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Please choose your Role!!!!!</p></center></html>");
         welcomeLabel.setBounds(100, 20, 400, 80);

        adminButton = new JButton("Teacher");
        studentButton = new JButton("Student");

        Font customFont = new Font("Arial", Font.BOLD, 30);
        adminButton.setFont(customFont);
        studentButton.setFont(customFont);
        
        adminButton.addActionListener(this);
        studentButton.addActionListener(this);

        adminButton.setBounds(100, 200, 200, 80);
        studentButton.setBounds(100, 100, 200, 80);

        adminButton.setBackground(new Color(144, 238, 144)); // Light Green
        studentButton.setBackground(Color.blue); // Blue
        //background.setBackground(Color.black); // Black

        background.add(welcomeLabel);
        background.add(adminButton);
        background.add(studentButton);
        setSize(700, 380);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    @Override
public void actionPerformed(ActionEvent e) {
    if (e.getSource() == adminButton) {
        LoginFrame f= new LoginFrame();
        f.setVisible(true);
        dispose(); // Close the current frame
    } else if (e.getSource() == studentButton) {
        FullScreenFrame a = new FullScreenFrame();
        a.setVisible(true);
        dispose(); // Close the current frame
    }
}


    public static void main(String[] args) {
        new Homepage();
    }
}

