package folder;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author hab
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TransparentLoginPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public TransparentLoginPage() {
        setTitle("Transparent Login Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel contentPane = new PanelTransparent(); // Use the transparent panel
        contentPane.setLayout(new GridLayout(3, 2));

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");

        contentPane.add(new JLabel("Username:"));
        contentPane.add(usernameField);
        contentPane.add(new JLabel("Password:"));
        contentPane.add(passwordField);
        contentPane.add(new JLabel());
        contentPane.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add login logic here
                JOptionPane.showMessageDialog(null, "Login Successful!");
            }
        });

        setContentPane(contentPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TransparentLoginPage().setVisible(true);
            }
        });
    }
}

