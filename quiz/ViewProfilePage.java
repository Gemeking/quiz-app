



import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewProfilePage {
    public ViewProfilePage() {
        JFrame frame = new JFrame("View Profile");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 200);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setBounds(50, 50, 100, 25);
        panel.add(nameLabel);

        JTextField nameTextField = new JTextField("John Doe");
        nameTextField.setBounds(160, 50, 200, 25);
        panel.add(nameTextField);

        JLabel emailLabel = new JLabel("Email Address:");
        emailLabel.setBounds(50, 80, 100, 25);
        panel.add(emailLabel);

        JTextField emailTextField = new JTextField("johndoe@example.com");
        emailTextField.setBounds(160, 80, 200, 25);
        panel.add(emailTextField);

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setBounds(50, 110, 100, 25);
        panel.add(roleLabel);

        JComboBox<String> roleComboBox = new JComboBox<>(new String[]{"Admin", "User"});
        roleComboBox.setBounds(160, 110, 100, 25);
        panel.add(roleComboBox);

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(160, 150, 80, 30);
        panel.add(saveButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fullName = nameTextField.getText();
                String emailAddress = emailTextField.getText();
                String selectedRole = (String) roleComboBox.getSelectedItem();

                // Perform save operation (e.g., update profile in the database)
                // For now, displaying the updated profile details in a dialog
                String message = "Full Name: " + fullName + "\n"
                        + "Email Address: " + emailAddress + "\n"
                        + "Role: " + selectedRole;
                JOptionPane.showMessageDialog(null, message, "Profile Updated", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        // Run the ViewProfilePage as a standalone application for testing
        new ViewProfilePage();
    }

    public void setVisble(boolean b) {
    }

    void setVisible(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
