


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
import java.awt.event.*;

public class FullScreenFrame extends JFrame {
    public FullScreenFrame() {
        setUndecorated(true);
       
        setLayout(new BorderLayout());
        setVisible(true);
         ImageIcon icon = new ImageIcon("/home/hab/Videos/a.png"); 
        setIconImage(icon.getImage());
        setSize(1200, 680); // Set a specific size
        setLocationRelativeTo(null); // Center the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        ImageIcon imageIcon = new ImageIcon("/home/hab/Videos/w.png");
        JLabel label = new JLabel(imageIcon);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);

        add(label);

        Timer timer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Slogin ff = new Slogin();
                ff.setVisible(true);
                
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public static void main(String[] args) {
        FullScreenFrame frame = new FullScreenFrame();
        frame.setVisible(true);
    }
}
