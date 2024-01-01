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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerController extends JFrame {
    private CourseRMIServer serverInstance;

    public ServerController() {
        setTitle("Server Controller");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton startButton = new JButton("Start");
        JButton endButton = new JButton("End");

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startServer();
            }
        });

        endButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stopServer();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(startButton);
        panel.add(endButton);

        add(panel);
        setVisible(true);
    }

    private void startServer() {
        try {
            serverInstance = new CourseRMIServer();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("CourseServer", serverInstance);
            System.out.println("Server started...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopServer() {
        if (serverInstance != null) {
            try {
                UnicastRemoteObject.unexportObject(serverInstance, true);
                System.out.println("Server stopped.");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Server is not running.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ServerController();
            }
        });
    }
}

