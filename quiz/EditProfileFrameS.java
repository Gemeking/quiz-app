


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author hab
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class EditProfileFrameS extends JFrame {
    private JTextField nameField, emailField, usernameField;
    private JPasswordField passwordField;
    private JRadioButton male, female;
    private JButton saveButton, chooseImageButton;
    private User currentUser;
    private JLabel nlabel, elabel, genderLabel, passwordLabel, usernameLabel, selectedImageLabel;
    private String imagePath;

    public EditProfileFrameS(User user) {
        this.currentUser = user;
        initializeFrame();
        initComponents();
    }

    private void initializeFrame() {
        setTitle("Edit Profile");
        setSize(400, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
    }

    private void initComponents() {
        nameField = new JTextField(currentUser.getName());
        nameField.setBounds(100, 50, 200, 30);
        add(nameField);
        nlabel = new JLabel("Name");
        nlabel.setBounds(10, 50, 130, 30);
        add(nlabel);

        emailField = new JTextField(currentUser.getEmail());
        emailField.setBounds(100, 100, 200, 30);
        add(emailField);
        elabel = new JLabel("Email");
        elabel.setBounds(10, 100, 100, 30);
        add(elabel);

        male = new JRadioButton("Male");
        male.setBounds(100, 150, 80, 30);
        female = new JRadioButton("Female");
        female.setBounds(140, 150, 80, 30);
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);
        JPanel genderPanel = new JPanel();
        genderPanel.setLayout(new FlowLayout());
        genderPanel.add(male);
        genderPanel.add(female);
        genderPanel.setBounds(100, 150, 200, 30);
        add(genderPanel);
        genderLabel = new JLabel("Gender");
        genderLabel.setBounds(10, 150, 100, 30);
        add(genderLabel);

        usernameField = new JTextField(currentUser.getUsername());
        usernameField.setBounds(100, 200, 200, 30);
        add(usernameField);
        usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(10, 200, 100, 30);
        add(usernameLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 250, 200, 30);
        add(passwordField);
        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 250, 100, 30);
        add(passwordLabel);
   

        chooseImageButton = new JButton("Choose Image");
        chooseImageButton.setBounds(50, 300, 150, 30);
        chooseImageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    imagePath = selectedFile.getAbsolutePath();
                    selectedImageLabel.setText(imagePath);
                }
            }
        });
        add(chooseImageButton);

        selectedImageLabel = new JLabel("");
        selectedImageLabel.setBounds(50, 350, 300, 30);
        add(selectedImageLabel);

        saveButton = new JButton("Save Changes");
        saveButton.setBounds(50, 400, 150, 30);
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateProfile();
            }
        });
        add(saveButton);
        
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Color labelColor = Color.BLACK;

        nlabel.setFont(labelFont);
        nlabel.setForeground(labelColor);
        elabel.setFont(labelFont);
        elabel.setForeground(labelColor);
        genderLabel.setFont(labelFont);
        genderLabel.setForeground(labelColor);
        usernameLabel.setFont(labelFont);
        usernameLabel.setForeground(labelColor);
        passwordLabel.setFont(labelFont);
        passwordLabel.setForeground(labelColor);

        // Set font and foreground color for buttons
        Font buttonFont = new Font("Arial", Font.PLAIN, 12);
        Color buttonColor = Color.WHITE;
        Color buttonBgColor = Color.BLUE;

        chooseImageButton.setFont(buttonFont);
        chooseImageButton.setForeground(buttonColor);
        chooseImageButton.setBackground(buttonBgColor);
        saveButton.setFont(buttonFont);
        saveButton.setForeground(buttonColor);
        saveButton.setBackground(buttonBgColor);
    }

    private void updateProfile() {
        String newName = nameField.getText();
        String newEmail = emailField.getText();
        String newUsername = usernameField.getText();
        String newGender = male.isSelected() ? "M" : "F";
        String newPassword = new String(passwordField.getPassword());

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/QUIZ", "root", "")) {
            String query = "UPDATE users SET sName = ?, sEmail = ?, sUname = ?, sGender = ?, sPassword = ?, image = ? WHERE sID = ?";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, newName);
                ps.setString(2, newEmail);
                ps.setString(3, newUsername);
                ps.setString(4, newGender);
                ps.setString(5, newPassword);

                if (imagePath != null) {
                    File selectedFile = new File(imagePath);
                    String fileName = selectedFile.getName();

                    Path source = Paths.get(imagePath);
                    Path finalDestination = Paths.get("/home/hab/Documents/qqqq/" + fileName);

                    Files.copy(source, finalDestination, StandardCopyOption.REPLACE_EXISTING);

                    imagePath = finalDestination.toString();

                    ps.setString(6, imagePath);
                } else {
                    ps.setString(6, null);
                }

                ps.setInt(7, currentUser.getId());

                int updatedRows = ps.executeUpdate();

                if (updatedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Updated Successfully");
                    clearFields();
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed!!!!!");
                }
            }
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating profile: " + ex.getMessage());
        }
    }

    private void clearFields() {
        nameField.setText("");
        emailField.setText("");
        male.setSelected(false);
        female.setSelected(false);
        selectedImageLabel.setText("");
        passwordField.setText("");
    }
}

