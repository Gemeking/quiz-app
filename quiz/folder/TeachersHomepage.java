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


public class TeachersHomepage extends JFrame implements ActionListener {
    public TeachersHomepage() {
        setTitle("Teachers Homepage");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         ImageIcon icon = new ImageIcon("/home/hab/Videos/a.png"); 
        setIconImage(icon.getImage());

        // Creating watermark panel with login and register buttons
        JPanel watermarkPanel = new JPanel();
        watermarkPanel.setLayout(new GridLayout(2, 1));
        watermarkPanel.setBackground(new Color(255, 255, 255, 100));

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 20));
        loginButton.addActionListener(this);

        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.PLAIN, 20));
        registerButton.addActionListener(this);

        watermarkPanel.add(loginButton);
        watermarkPanel.add(registerButton);

        // Setting up header
        JLabel headerLabel = new JLabel("Teachers Homepage", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 36));

        // Adding components to content pane
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(headerLabel, BorderLayout.NORTH);
        contentPane.add(watermarkPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if (actionCommand.equals("Login")) {
            // Open the login page (you can implement this)
           // LoginPage loginPage = new LoginPage();
            // loginPage.setVisible(true);
        } else if (actionCommand.equals("Register")) {
            // Open the registration page (you can implement this)
            //RegisterPage registerPage = new RegisterPage();
            //registerPage.setVisible(true);
        }
    }

    public static void main(String[] args) {
        new TeachersHomepage();
    }
}

