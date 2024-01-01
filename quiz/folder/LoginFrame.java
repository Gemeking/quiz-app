package folder;


import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;



public class LoginFrame extends JFrame implements ActionListener {

    JLabel usernameLabel, passwordLabel;
    JTextField tf1;
    JPasswordField pf;
    JButton loginBtn,Home ;
    JLabel registerLabel;
 
    public LoginFrame() {
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
       loginBtn.setBackground(Color.GREEN);
        loginBtn.setForeground(Color.BLACK);
        loginBtn.setFont(font);
        
      Home =new JButton("Homepage");
      Home.setForeground(Color.WHITE);
      Home.setBackground(Color.BLUE);
     Home.addActionListener(this);
        Home.setFont(font);
      

       

        background.add(usernameLabel);
        background.add(tf1);
        background.add(passwordLabel);
        background.add(pf);
        background.add(loginBtn);
        background.add(Home);
       

        setContentPane(background);
    }

public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("Login")) {
        String username = tf1.getText();
        String password = new String(pf.getPassword());

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/QUIZ", "root", "");
            PreparedStatement ps = con.prepareStatement("select * from admin where tUname=? and tPassword=?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User(
                        rs.getString("tName"),
                        rs.getString("tUname"),
                        rs.getInt("tID"),
                        rs.getString("tGender"),
                        rs.getString("tEmail"),
                        rs.getString("tPassword"),
                        rs.getString("image")       
                );

                AdminDashboard a = new AdminDashboard();
                a.setCurrentUser(user);
                a.displayUserProfile();
                a.setVisible(true);
                this.dispose();
            }
            
            else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    else if(e.getActionCommand().equals("Homepage")){
              Homepage h=new Homepage();
              h.setVisible(true);
              this.dispose();
            }
}


    public static void main(String args[]) {
        new LoginFrame();
    }
}
