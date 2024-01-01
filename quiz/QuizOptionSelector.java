


import javax.swing.*;
import java.awt.*;
import static java.awt.Color.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class QuizOptionSelector extends JFrame implements ActionListener {
    private JLabel welcomeLabel;
    private JLabel headerLabel;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private ViewScorePage ViewScorePage;
    private JPanel placeholderPanel;
    private JMenuBar menuBar;
    private JMenu fileMenu,Add,view;
    private QuizPanel control;
    private ViewScorePage viewscorepage;
    private JMenuItem ControlPanelitem,viewsc;
    private User currentUser;
    private JMenuItem logoutitem;
  ImageIcon backgroundImage = new ImageIcon("/home/hab/Videos/best.png");
        JLabel background = new JLabel(backgroundImage);
        
    public void setCurrentUser(User user) {
        this.currentUser = user;
        displayUserProfile();
    }

    public void displayUserProfile() {
        if (currentUser != null) {
            String imagePath = currentUser.getImagePath();
            JLabel imageLabel;

            if (imagePath != null && !imagePath.isEmpty()) {
                ImageIcon userImage = resizeImage(imagePath, 500, 320);
                imageLabel = new JLabel(userImage);
            } else {
                ImageIcon user = resizeImage("/home/hab/Downloads/user.png", 500, 320);
                imageLabel = new JLabel(user);
            }

            imageLabel.setBounds(1470, 50, 500, 320);
            imageLabel.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60), 4));
            background.add(imageLabel);

            JLabel nameLabel = new JLabel("Name:   " + currentUser.getName());
            nameLabel.setBounds(1510, 390, 450, 30);
            nameLabel.setFont(new Font("Tahoma", Font.BOLD, 27));
            nameLabel.setForeground(new Color(200, 200, 255));
            background.add(nameLabel);

            JLabel welCome = new JLabel("Welcome, Teacher " + currentUser.getName());
            welCome.setBounds(10, 2, 750, 70);
            welCome.setFont(new Font("Tahoma", Font.BOLD, 27));
            welCome.setForeground(new Color(200, 200, 255));
            background.add(welCome);

            JLabel genderLabel = new JLabel("Gender: " + currentUser.getGender());
            genderLabel.setBounds(1510, 430, 450, 30);
            genderLabel.setFont(new Font("Tahoma", Font.BOLD, 27));
            genderLabel.setForeground(new Color(200, 200, 255));
            background.add(genderLabel);

            JLabel emailLabel = new JLabel("Email:    " + currentUser.getEmail());
            emailLabel.setBounds(1510, 470, 450, 30);
            emailLabel.setFont(new Font("Tahoma", Font.BOLD, 27));
            emailLabel.setForeground(new Color(200, 200, 255));
            background.add(emailLabel);

            JLabel IDLabel = new JLabel("ID:         " + currentUser.getId());
            IDLabel.setBounds(1510, 510, 450, 30);
            IDLabel.setFont(new Font("Tahoma", Font.BOLD, 27));
            IDLabel.setForeground(new Color(200, 200, 255));
            background.add(IDLabel);

            JLabel nn = new JLabel("");
            background.add(nn);
            background.setBounds(0, 0, backgroundImage.getIconWidth(), backgroundImage.getIconHeight());
        }
    }

    private ImageIcon resizeImage(String imagePath, int width, int height) {
        try {
            BufferedImage originalImage = ImageIO.read(new File(imagePath));
            Image resultingImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(resultingImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public QuizOptionSelector() {
        setTitle("Student's form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //getContentPane().setBackground(new Color(0, 130, 255));
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        
      
        //add(background);
        add(background);
        menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        fileMenu.setFont(new Font("Tahoma", Font.BOLD, 20));
        view = new JMenu("View");
        view.setFont(new Font("Tahoma", Font.BOLD, 20));
        

       
        ControlPanelitem = new JMenuItem("Take Quiz");
        logoutitem =new JMenuItem("logout");
        viewsc=new JMenuItem("view score");

        ControlPanelitem.addActionListener(this);
        ControlPanelitem.setActionCommand("adminControl");
        fileMenu.add(ControlPanelitem);
        menuBar.add(fileMenu);
        menuBar.add(view);
        
        viewsc.addActionListener(this);
        viewsc.setActionCommand("viewsc");
     
    
     logoutitem.addActionListener(this);
     
        ViewScorePage =new ViewScorePage();

        fileMenu.add(logoutitem);
        view.add(viewsc);
        menuBar.setPreferredSize(new Dimension(menuBar.getWidth(), 50));

        Font menuFont = new Font("Tahoma", Font.PLAIN, 20);
        

        
         ControlPanelitem.setFont(menuFont);
         logoutitem.setFont(menuFont);
         viewsc.setFont(menuFont);
         
        

        setJMenuBar(menuBar);

        JButton editProfileButton = new JButton("Edit Profile");
        editProfileButton.setBounds(1510, 550, 200, 30);
        editProfileButton.setBackground(new Color(200, 200, 255));
        editProfileButton.setForeground(Color.BLACK);
        editProfileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EditProfileFrameS editFrame = new EditProfileFrameS(currentUser);
                editFrame.setVisible(true);
            }
        });

        background.add(editProfileButton);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        placeholderPanel = new JPanel();

        cardPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 255), 3));

        cardPanel.add(placeholderPanel, "placeholder");
        

    
        cardPanel.setBounds(3, 60, 1450, 810);
        cardPanel.setBackground(new Color(60, 60, 60));
        cardPanel.setForeground(red);
        
        background.add(cardPanel);

        placeholderPanel.setBackground(Color.decode("#ecf0f1"));
        
        control = new QuizPanel();

        cardPanel.add(control, "adminControl");
        cardPanel.add( ViewScorePage, "viewsc");
        
        viewscorepage =new ViewScorePage();
        
       

        ImageIcon icon = new ImageIcon("/home/hab/Videos/a.png");
        setIconImage(icon.getImage());
    }

    public void actionPerformed(ActionEvent evt) {
        Object source = evt.getSource();

        if (source instanceof JMenuItem) {
            JMenuItem menuItemSource = (JMenuItem) source;
           if ("adminControl".equals(menuItemSource.getActionCommand())) {
                cardLayout.show(cardPanel, "adminControl");
            }else if ("viewsc".equals(menuItemSource.getActionCommand())) {
                cardLayout.show(cardPanel, "viewsc");
            }
            
            else if ("logout".equals(menuItemSource.getActionCommand())) {
        this.dispose();
        Homepage h = new Homepage();
        h.setVisible(true);
    }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QuizOptionSelector adminDashboard = new QuizOptionSelector();
            adminDashboard.pack();
            adminDashboard.setVisible(true);
        });
    }
}
