


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.io.*;
import java.nio.file.*;
import java.nio.file.StandardCopyOption;

public class studentreg extends JFrame implements ActionListener {

    JLabel header, l2, l3, l4, l5, l6, l7, reg;
    JTextField tf1, tf2, tf3, tf4, tf5;
    JRadioButton male, female;
    ButtonGroup genderGroup;
    JPasswordField passwordField;
    JButton btn1, uploadImageButton;
    private JPanel backgroundPanel;
    private String imagePath;
    private JPanel backgroundPane;
    JComboBox<String> courseComboBox;

    public studentreg() {
        setVisible(true);
        setSize(700, 470);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setTitle("Student Registration Form");
        ImageIcon icon = new ImageIcon("/home/hab/Videos/a.png");
        setIconImage(icon.getImage());

        backgroundPanel = new JPanel(); 
        backgroundPanel.setLayout(null);
        backgroundPanel = new BackgroundPanel(new ImageIcon("/home/hab/Videos/l.jpg").getImage());
        backgroundPanel.setLayout(null);

        getContentPane().add(backgroundPanel);

        header = new JLabel("Registration Form");
        header.setFont(new Font("Serif", Font.BOLD, 50));
        header.setForeground(Color.ORANGE);
        header.setBounds(50, 0, 500, 60);

        l2 = new JLabel("Name:");
        l2.setFont(new Font("Serif", Font.BOLD, 20));
        l2.setForeground(Color.ORANGE);
        l2.setBounds(50, 80, 200, 30);

        tf1 = new JTextField();
        tf1.setBounds(250, 80, 200, 30);

        l3 = new JLabel("Username");
        l3.setBounds(50, 120, 200, 30);
        l3.setForeground(Color.ORANGE);
        l3.setFont(new Font("Serif", Font.BOLD, 20));

        tf2 = new JTextField();
        tf2.setBounds(250, 120, 200, 30);

        l4 = new JLabel("ID:");
        l4.setFont(new Font("Serif", Font.BOLD, 20));
        l4.setForeground(Color.ORANGE);
        l4.setBounds(50, 160, 200, 30);

        tf3 = new JTextField();
        tf3.setBounds(250, 160, 200, 30);

        l5 = new JLabel("Gender:");
        l5.setFont(new Font("Serif", Font.BOLD, 20));
        l5.setForeground(Color.ORANGE);
        l5.setBounds(50, 200, 200, 30);

        male = new JRadioButton("Male");
        male.setBounds(250, 200, 100, 30);

        female = new JRadioButton("Female");
        female.setBounds(350, 200, 100, 30);

        genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);

        l6 = new JLabel("Email-ID:");
        l6.setFont(new Font("Serif", Font.BOLD, 20));
        l6.setForeground(Color.ORANGE);
        l6.setBounds(50, 240, 200, 30);

        l7 = new JLabel("Create Password:");
        l7.setFont(new Font("Serif", Font.BOLD, 20));
        l7.setForeground(Color.ORANGE);
        l7.setBounds(50, 280, 200, 30);

        tf5 = new JTextField();
        tf5.setBounds(250, 240, 200, 30);

        passwordField = new JPasswordField();
        passwordField.setBounds(250, 280, 200, 30);

        btn1 = new JButton("Submit");
        btn1.setBounds(260, 360, 100, 30);
        btn1.addActionListener(this);

        reg = new JLabel("Have an account?");
        reg.setBounds(400, 360, 200, 30);
        reg.setForeground(Color.WHITE);

        reg.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Slogin l = new Slogin();
                l.setVisible(true);
                dispose();
            }
        });

        uploadImageButton = new JButton("Upload Image");
        uploadImageButton.setBounds(250, 320, 200, 30);
        uploadImageButton.addActionListener(this);

        backgroundPanel.add(header);
        backgroundPanel.add(l2);
        backgroundPanel.add(tf1);
        backgroundPanel.add(l3);
        backgroundPanel.add(tf2);
        backgroundPanel.add(l4);
        backgroundPanel.add(tf3);
        backgroundPanel.add(l5);
        backgroundPanel.add(male);
        backgroundPanel.add(female);
        backgroundPanel.add(l6);
        backgroundPanel.add(l7);
        backgroundPanel.add(tf5);
        backgroundPanel.add(passwordField);
        backgroundPanel.add(btn1);
        backgroundPanel.add(reg);
        backgroundPanel.add(uploadImageButton);

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

            if (name.isEmpty() || uname.isEmpty() || idString.isEmpty() || email.isEmpty() || password.isEmpty() || gender.isEmpty() || imagePath == null) {
                JOptionPane.showMessageDialog(btn1, "please don't leave empty all the fields.");
                return;
            }

            if (!idString.matches("\\d+")) {
                JOptionPane.showMessageDialog(btn1, "ID must contain only numbers.");
                return;
            }

            if (password.length() >= 8 && containsCapitalLetter(password) && containsSpecialCharacter(password)) {
                String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                        "[a-zA-Z0-9_+&*-]+)*@" +
                        "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                        "A-Z]{2,7}$";

                if (email.matches(emailRegex)) {
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/QUIZ", "root", "");
                        PreparedStatement checkUsername = con.prepareStatement("SELECT * FROM users WHERE sUname = ?");
                        checkUsername.setString(1, uname);

                        ResultSet rs = checkUsername.executeQuery();

                        if (rs.next()) {
                            JOptionPane.showMessageDialog(btn1, "Username already exists.");
                            return;
                        }

                        PreparedStatement ps = con.prepareStatement("INSERT INTO users (sName, sUname, sID, sGender, sEmail, sPassword, image) VALUES (?,?,?,?,?,?,?)");

                        ps.setString(1, name);
                        ps.setString(2, uname);
                        ps.setInt(3, Integer.parseInt(idString)); 
                        ps.setString(4, gender);
                        ps.setString(5, email);
                        ps.setString(6, password);

                        if (imagePath != null) {
                            File selectedFile = new File(imagePath);
                            String fileName = selectedFile.getName();

                            Path source = Paths.get(imagePath);
                            Path finalDestination = Paths.get("/home/hab/Documents/qqqq/" + fileName);

                            Files.copy(source, finalDestination, StandardCopyOption.REPLACE_EXISTING);

                            imagePath = finalDestination.toString();

                            ps.setString(7, imagePath);
                        } else {
                            ps.setString(7, null);
                        }

                       

                        int rowsAffected = ps.executeUpdate();

                        if (rowsAffected > 0) {
                            Slogin l = new Slogin();
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
        } else if (e.getSource() == uploadImageButton) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                imagePath = selectedFile.getAbsolutePath();
                System.out.println("Selected file: " + imagePath);
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
        studentreg studentreg = new studentreg();
    }
}
