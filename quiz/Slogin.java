

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;



public class Slogin extends JFrame implements ActionListener {

    JLabel usernameLabel, passwordLabel;
    JTextField tf1;
    JPasswordField pf;
    JButton loginBtn;
    JLabel registerLabel;

    public Slogin() {
        setVisible(true);
        setSize(600, 450); 
        setLocationRelativeTo(null); 
        setResizable(false);
        ImageIcon icon = new ImageIcon("/home/hab/Videos/a.png"); 
        setIconImage(icon.getImage());
        setTitle("Login");
        ImageIcon backgroundImage = new ImageIcon("/home/hab/Videos/l.jpg"); // Replace with your image path
        BackgroundPanel background = new BackgroundPanel(backgroundImage.getImage());
        background.setLayout(new FlowLayout(FlowLayout.CENTER, 160, 40)); // Centered FlowLayout with specific gaps

        usernameLabel = new JLabel("Username:");
        tf1 = new JTextField(15); // Set preferred columns for JTextField

        passwordLabel = new JLabel("Password:");
        pf = new JPasswordField(15); // Set preferred columns for JPasswordField

        Font font = new Font("Arial", Font.PLAIN, 20);

        usernameLabel.setFont(font);
        usernameLabel.setForeground(Color.ORANGE);
        tf1.setFont(font);
        passwordLabel.setFont(font);
        passwordLabel.setForeground(Color.ORANGE);
        pf.setFont(font);

        // Login button
        loginBtn = new JButton("Login");
        loginBtn.addActionListener(this);
        loginBtn.setFont(font);

        // Registration link as a JLabel
        registerLabel = new JLabel("Don't have account?");
        registerLabel.setForeground(Color.WHITE);
        registerLabel.setFont(new Font("Arial", Font.BOLD, 14)); 
        registerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               studentreg  registration = new studentreg();
                registration.setVisible(true);
                dispose();
            }
        });

        background.add(usernameLabel);
        background.add(tf1);
        background.add(passwordLabel);
        background.add(pf);
        background.add(loginBtn);
        background.add(registerLabel);

        setContentPane(background);
    }

 public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Login")) {
            String username = tf1.getText();
            String password = new String(pf.getPassword());

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/QUIZ", "root", ""); 
                PreparedStatement ps = con.prepareStatement("select * from users where sUname=? and sPassword=?");
                ps.setString(1, username);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    User user = new User(
                            rs.getString("sName"),
                            rs.getString("sUname"),
                            rs.getInt("sID"),
                            rs.getString("sGender"),
                            rs.getString("sEmail"),
                            rs.getString("sPassword"),
                            rs.getString("image")
                    );

                    QuizOptionSelector a = new QuizOptionSelector();
                    if (user != null) {
                        a.setCurrentUser(user); // Set the current user in QuizOptionSelector
                        a.setVisible(true);
                        this.dispose();
                    } else {
                        // Handle the case where the user object is null
                        System.out.println("User object is null.");
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Username or Password");
                }
                con.close(); // Close the database connection
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        new Slogin();
    }
}