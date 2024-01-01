package folder;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author hab
 */

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author hab
 */
import ada.AdminRegistration;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(Image image) {
        this.backgroundImage = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this);
    }
}

public class stud extends JFrame implements ActionListener {

    JLabel header, l2, l3, l4, l5, l6, l7, reg;
    JTextField tf1, tf2, tf3, tf4, tf5;
    JRadioButton male, female;
    ButtonGroup genderGroup;
    JPasswordField passwordField;
    JButton btn1;
    private JPanel backgroundPanel;

    public stud() {
        setVisible(true);
        setSize(700, 450); // Set a specific size
        setLocationRelativeTo(null); // Center the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Admin Registration Form");
         ImageIcon icon = new ImageIcon("/home/hab/Videos/a.png"); 
        setIconImage(icon.getImage());

        backgroundPanel = new BackgroundPanel(new ImageIcon("/home/hab/Videos/l.jpg").getImage());
        backgroundPanel.setLayout(null);

        header = new JLabel("Registraion Form");
        header.setFont(new Font("Serif", Font.BOLD, 50));
        header.setForeground(Color.ORANGE);
        header.setBounds(50, 0, 500, 60);

        l2 = new JLabel("Name:");
        l2.setFont(new Font("Serif", Font.BOLD, 20));
        l2.setForeground(Color.ORANGE);
        l3 = new JLabel("Username:");
        l3.setFont(new Font("Serif", Font.BOLD, 20));
        l3.setForeground(Color.ORANGE);
        l4 = new JLabel("ID:");
        l4.setFont(new Font("Serif", Font.BOLD, 20));
        l4.setForeground(Color.ORANGE);
        l5 = new JLabel("Gender:");
        l5.setFont(new Font("Serif", Font.BOLD, 20));
        l5.setForeground(Color.ORANGE);
        l6 = new JLabel("Email-ID:");
        l6.setFont(new Font("Serif", Font.BOLD, 20));
        l6.setForeground(Color.ORANGE);
        l7 = new JLabel("Create Password:");
        l7.setFont(new Font("Serif", Font.BOLD, 20));
        l7.setForeground(Color.ORANGE);

        tf1 = new JTextField();
        tf2 = new JTextField();
        tf3 = new JTextField();
        tf4 = new JTextField();
        tf5 = new JTextField();

        male = new JRadioButton("Male");
        female = new JRadioButton("Female");

        genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);

        passwordField = new JPasswordField();

        btn1 = new JButton("Submit");
        reg = new JLabel("Have an account?");

        btn1.addActionListener(this);

        reg.setForeground(Color.ORANGE);
        reg.setCursor(new Cursor(Cursor.HAND_CURSOR));
        reg.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LoginFrame l = new LoginFrame();
                l.setVisible(true);
                dispose();
            }
        });

        l2.setBounds(50, 80, 200, 30);
        tf1.setBounds(250, 80, 200, 30);
        l3.setBounds(50, 120, 200, 30);
        tf2.setBounds(250, 120, 200, 30);
        l4.setBounds(50, 160, 200, 30);
        tf3.setBounds(250, 160, 200, 30);
        l5.setBounds(50, 200, 200, 30);
        male.setBounds(250, 200, 100, 30);
        female.setBounds(350, 200, 100, 30);
        l6.setBounds(50, 240, 200, 30);
        //tf4.setBounds(250, 240, 200, 30);
        l7.setBounds(50, 280, 200, 30);
        tf5.setBounds(250, 240, 200, 30);
        passwordField.setBounds(250, 280, 200, 30);
        btn1.setBounds(260, 360, 100, 30);
        reg.setBounds(400, 360, 200, 30);
        
       
        backgroundPanel.add(header);
        backgroundPanel.add(l2);
        backgroundPanel.add(l3);
        backgroundPanel.add(l4);
        backgroundPanel.add(l5);
        backgroundPanel.add(l6);
        backgroundPanel.add(l7);
        backgroundPanel.add(tf1);
        backgroundPanel.add(tf2);
        backgroundPanel.add(tf3);
        backgroundPanel.add(tf5);
        backgroundPanel.add(male);
        backgroundPanel.add(female);
       backgroundPanel.add(passwordField);
       backgroundPanel.add(btn1);
        backgroundPanel.add(reg);

        getContentPane().add(backgroundPanel);
    }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == btn1) {
        String name = tf1.getText();
        String uname = tf2.getText();
        String idString = tf3.getText();
        String gender = male.isSelected() ? "m" : "f";
        String email = tf5.getText();
        String password = new String(passwordField.getPassword());

         if (name.isEmpty() || uname.isEmpty() || idString.isEmpty() || email.isEmpty() || password.isEmpty() || gender.isEmpty()) {
            JOptionPane.showMessageDialog(btn1, "Please fill out all fields, including gender.");
            return;
        }
         
if (!idString.matches("\\d+")) {
    JOptionPane.showMessageDialog(btn1, "ID must contain only numbers.");
    return;
}

        if (password.length() >= 8 && containsCapitalLetter(password) && containsSpecialCharacter(password)) {
            // Email validation using regex
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$";

            if (email.matches(emailRegex)) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/QUIZ", "root", "");
                    PreparedStatement checkUsername = con.prepareStatement("SELECT * FROM users WHERE tUname = ?");
                    checkUsername.setString(1, uname);

                    ResultSet rs = checkUsername.executeQuery();

                    if (rs.next()) {
                        JOptionPane.showMessageDialog(btn1, "Username already in use. Please choose a different one.");
                        return;
                    }

                    PreparedStatement ps = con.prepareStatement("INSERT INTO users VALUES (?,?,?,?,?,?)");

                    ps.setString(1, sName);
                    ps.setString(2, sUname);
                    ps.setString(3, idString);
                    ps.setString(4, gender);
                    ps.setString(5, email);
                    ps.setString(6, password);

                    int rowsAffected = ps.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(btn1, "Data Saved Successfully");
                        LoginFrame l = new LoginFrame();
                        l.setVisible(true);
                        dispose();
                    }

                } catch (Exception ex) {
                    System.out.println(ex);
                }
            } else {
                JOptionPane.showMessageDialog(btn1, "Please enter a valid email address.");
            }
        } else {
            JOptionPane.showMessageDialog(btn1, "Password must have at least 8 characters, a capital letter, and a special character.");
        }
    }
}

    private boolean containsCapitalLetter(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsSpecialCharacter(String password) {
        String specialCharacters = "!@#$%^&*()-_+=<>?";

        for (char c : password.toCharArray()) {
            if (specialCharacters.contains(String.valueOf(c))) {
                return true;
            }
        }
        return false;
    }

    public static void main(String args[]) {
        new AdminRegistration();
    }
}
