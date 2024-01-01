package folder;


import javax.swing.*;
import java.awt.*;
import static java.awt.Color.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class AdminDashboard extends JFrame implements ActionListener {
    private JLabel welcomeLabel;
    private JLabel headerLabel;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel placeholderPanel;
    private DeleteQuestionPage DeleteQuestionPage;
    private EditTrueFalsePage EditTrueFalsePage;
    private EditMultipleChoicePage EditMultipleChoicePage;
    private JMenuBar menuBar;
    private JMenu fileMenu,Add,view;
    private AdminControlPanel control;
    private JMenu editMenu, settingMenu;
    private JMenuItem darkThemeMenuItem;
    private JMenuItem lightThemeMenuItem;
    private JMenu Delete;
    private JMenuItem delete,exit;
    private JMenuItem editbs;
    private JMenuItem edittf;
    private JMenuItem multch,addtf,addmc,addbs,viewtf,viewmc,viewbs;
    private AddTrueFalseQuestionPage AddTrueFalseQuestionPage;
    private AddMultipleChoiceQuestionPage AddMultipleChoiceQuestionPage;
    private AddBlankSpaceQuestionPage AddBlankSpaceQuestionPage;
    private viewBlankSpacePage viewBlankSpacePage;
    private viewTrueFalsePage viewTrueFalsePage;
    private viewMultipleChoicePage viewMultipleChoicePage;
    private EditBlankSpacePage EditBlankSpacePage;
    private JMenuItem ControlPanelitem;
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

    public AdminDashboard() {
        setTitle("Teacher's form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //getContentPane().setBackground(new Color(0, 130, 255));
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        
      
        //add(background);
        add(background);
        menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        fileMenu.setFont(new Font("Tahoma", Font.BOLD, 20));
        editMenu = new JMenu("Edit");
        editMenu.setFont(new Font("Tahoma", Font.BOLD, 20));
        Add=new JMenu("Add");
        Add.setFont(new Font("Tahoma", Font.BOLD, 20));
        view=new JMenu("View");
        view.setFont(new Font("Tahoma", Font.BOLD, 20));
        Delete=new JMenu("Remove");
        Delete.setFont(new Font("Tahoma", Font.BOLD, 20));
        settingMenu = new JMenu("Setting");

        darkThemeMenuItem = new JMenuItem("Dark Theme");
        lightThemeMenuItem = new JMenuItem("Light Theme");
        
        edittf = new JMenuItem("True false");
        multch= new JMenuItem("Multiple choise");
        editbs = new JMenuItem("Blank space");
        
        exit=new JMenuItem("Exit");
        
         addtf = new JMenuItem("True false");
        addmc= new JMenuItem("Multiple choise");
       addbs = new JMenuItem("Blank space");
       
       viewtf = new JMenuItem("True false");
        viewmc= new JMenuItem("Multiple choise");
       viewbs = new JMenuItem("Blank space");
       
       delete=new JMenuItem("Delete Questions");
       
        ControlPanelitem = new JMenuItem("Quiz status");
        logoutitem =new JMenuItem("logout");

        darkThemeMenuItem.addActionListener(this);
        darkThemeMenuItem.setActionCommand("darkTheme");

        lightThemeMenuItem.addActionListener(this);
        lightThemeMenuItem.setActionCommand("lightTheme");

        ControlPanelitem.addActionListener(this);
        ControlPanelitem.setActionCommand("adminControl");
        
        exit.setActionCommand("exit");

        editbs.addActionListener(this);
        edittf.addActionListener(this);
        multch.addActionListener(this);
        addbs.addActionListener(this);
        addtf.addActionListener(this);
        addmc.addActionListener(this);
        viewtf.addActionListener(this);
        viewmc.addActionListener(this);
        viewbs.addActionListener(this);
        logoutitem.addActionListener(this);
          delete.addActionListener(this);
          exit.addActionListener(this);

        settingMenu.add(darkThemeMenuItem);
        settingMenu.add(lightThemeMenuItem);
        fileMenu.add(ControlPanelitem);
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(Add);
        menuBar.add(view);
        menuBar.add(Delete);
        //menuBar.add(settingMenu);
        editMenu.add(edittf);
        editMenu.add(multch);
        editMenu.add(editbs);
        
        Add.add(addtf);
        Add.add(addmc);
        Add.add(addbs);
        
        
        
        view.add(viewtf);
        view.add(viewmc);
        view.add(viewbs);
        
        Delete.add(delete);
        
        fileMenu.add(logoutitem);
        fileMenu.add(exit);
        menuBar.setPreferredSize(new Dimension(menuBar.getWidth(), 50));

        Font menuFont = new Font("Tahoma", Font.PLAIN, 20);
        darkThemeMenuItem.setFont(menuFont);
        lightThemeMenuItem.setFont(menuFont);
        editbs.setFont(menuFont);
        edittf.setFont(menuFont);
        multch.setFont(menuFont);
        
        addtf.setFont(menuFont);
        addmc.setFont(menuFont);
        addbs.setFont(menuFont);
        
        viewtf.setFont(menuFont);
        viewmc.setFont(menuFont);
        viewbs.setFont(menuFont);
        
        delete.setFont(menuFont);
        
         ControlPanelitem.setFont(menuFont);
         logoutitem.setFont(menuFont);
         exit.setFont(menuFont);
         
        

        setJMenuBar(menuBar);

        JButton editProfileButton = new JButton("Edit Profile");
        editProfileButton.setBounds(1510, 550, 200, 30);
        editProfileButton.setBackground(new Color(200, 200, 255));
        editProfileButton.setForeground(Color.BLACK);
        editProfileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EditProfileFrame editFrame = new EditProfileFrame(currentUser);
                editFrame.setVisible(true);
            }
        });

       background.add(editProfileButton);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        placeholderPanel = new JPanel();

        cardPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 255), 3));

        cardPanel.add(placeholderPanel, "placeholder");
        

       
       JLabel k=new JLabel("...................................... This is the working enviroment please select any task.................................");
                    k.setFont(new Font("Tahoma", Font.BOLD, 28));
                    k.setForeground(Color.gray);
                    
            placeholderPanel.add(k);
        cardPanel.setBounds(3, 60, 1450, 810);
        cardPanel.setBackground(new Color(60, 60, 60));
        cardPanel.setForeground(red);
        
        background.add(cardPanel);

        placeholderPanel.setBackground(Color.decode("#ecf0f1"));
        DeleteQuestionPage = new DeleteQuestionPage();
        EditTrueFalsePage=new EditTrueFalsePage();
        control = new AdminControlPanel();
        EditMultipleChoicePage =new EditMultipleChoicePage();
        EditBlankSpacePage =new EditBlankSpacePage();
        
        AddTrueFalseQuestionPage =new AddTrueFalseQuestionPage();
    AddMultipleChoiceQuestionPage=new AddMultipleChoiceQuestionPage();
   AddBlankSpaceQuestionPage =new AddBlankSpaceQuestionPage();
   
    viewBlankSpacePage =new viewBlankSpacePage();
   viewTrueFalsePage =new viewTrueFalsePage();
    viewMultipleChoicePage =new viewMultipleChoicePage();

    cardPanel.add(viewBlankSpacePage, "viewBlankSpacePage");
    cardPanel.add(viewTrueFalsePage, "viewTrueFalsePage");
    cardPanel.add(viewMultipleChoicePage, "viewMultipleChoicePage");
       
   cardPanel.add(AddTrueFalseQuestionPage, "AddTrueFalseQuestionPage");
   cardPanel.add(AddMultipleChoiceQuestionPage, "AddMultipleChoiceQuestionPage");
   cardPanel.add(AddBlankSpaceQuestionPage, "AddBlankSpaceQuestionPage");
   
        cardPanel.add(EditTrueFalsePage, "edittf");
        cardPanel.add(EditMultipleChoicePage, "EditMultipleChoicePage");
        cardPanel.add(EditBlankSpacePage, "EditBlankSpacePage");
        cardPanel.add(control, "adminControl");
        cardPanel.add(DeleteQuestionPage, "DeleteQuestionPage");

        ImageIcon icon = new ImageIcon("/home/hab/Videos/a.png");
        setIconImage(icon.getImage());
    }

    public void actionPerformed(ActionEvent evt) {
        Object source = evt.getSource();

        if (source instanceof JMenuItem) {
            JMenuItem menuItemSource = (JMenuItem) source;

            if ("darkTheme".equals(menuItemSource.getActionCommand())) {
                getContentPane().setBackground(new Color(10, 10, 100));

            } else if ("lightTheme".equals(menuItemSource.getActionCommand())) {
                getContentPane().setBackground(new Color(0, 130, 255));

            } else if (menuItemSource == editbs) {
                cardLayout.show(cardPanel, "EditBlankSpacePage");
            } else if (menuItemSource == edittf) {
                cardLayout.show(cardPanel, "edittf");
            } else if (menuItemSource == multch) {
                cardLayout.show(cardPanel, "EditMultipleChoicePage");
            } else if (menuItemSource == addtf) {
                cardLayout.show(cardPanel, "AddTrueFalseQuestionPage");
            } else if (menuItemSource == addmc) {
                cardLayout.show(cardPanel, "AddMultipleChoiceQuestionPage");
            } else if (menuItemSource == addbs) {
                cardLayout.show(cardPanel, "AddBlankSpaceQuestionPage");
            } else if (menuItemSource == viewtf) {
                cardLayout.show(cardPanel, "viewTrueFalsePage");
            } else if (menuItemSource == viewmc) {
                cardLayout.show(cardPanel, "viewMultipleChoicePage");
            } else if (menuItemSource == viewbs) {
                cardLayout.show(cardPanel, "viewBlankSpacePage");
            } else if (menuItemSource == delete) {
                cardLayout.show(cardPanel, "DeleteQuestionPage");
            }
            else if ("adminControl".equals(menuItemSource.getActionCommand())) {
                cardLayout.show(cardPanel, "adminControl");
            }
            
            else if ("logout".equals(menuItemSource.getActionCommand())) {
                int dialogResult =JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if(dialogResult ==JOptionPane.YES_OPTION){
       this.dispose();
        Homepage h = new Homepage();
        h.setVisible(true);
        }
        
    }else if ("exit".equals(menuItemSource.getActionCommand())) {
        int dialogResult =JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if(dialogResult ==JOptionPane.YES_OPTION){
        this.dispose();
        AdminControlPanel j=new AdminControlPanel();
        j.dispose();
        }
        
    }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminDashboard adminDashboard = new AdminDashboard();
            adminDashboard.pack();
             adminDashboard.setSize(1500,800);
            adminDashboard.setVisible(true);
        });
    }
}
