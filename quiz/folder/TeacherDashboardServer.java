package folder;


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
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class TeacherDashboardServer extends JPanel {
    private JLabel statusLabel;

    public TeacherDashboardServer() {
        setLayout(new BorderLayout());

        // Status label to display server status
        statusLabel = new JLabel("Server is not running");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(statusLabel, BorderLayout.NORTH);

        // Panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        add(buttonPanel, BorderLayout.CENTER);

        // Start Exam Button
        JButton startExamButton = new JButton("Start Exam");
        startExamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ExamControl examControl = new ExamControlImpl();

                    // Export the RMI object
                    ExamControl stub = (ExamControl) UnicastRemoteObject.exportObject(examControl, 0);

                    // Bind the remote object's stub in the registry
                    Registry registry = LocateRegistry.createRegistry(1099);
                    registry.bind("ExamControlService", stub);

                    statusLabel.setText("Exam started. Server is running...");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    statusLabel.setText("Error starting the exam");
                }
            }
        });
        buttonPanel.add(startExamButton);

        // End Exam Button
        JButton endExamButton = new JButton("End Exam");
        endExamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Registry registry = LocateRegistry.getRegistry(1099);
                    registry.unbind("ExamControlService");
                    UnicastRemoteObject.unexportObject(new ExamControlImpl(), true);

                    statusLabel.setText("Exam ended. Server stopped.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    statusLabel.setText("Error ending the exam");
                }
            }
        });
        buttonPanel.add(endExamButton);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Teacher Dashboard Server");
        TeacherDashboardServer serverPanel = new TeacherDashboardServer();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.getContentPane().add(serverPanel);
        frame.setVisible(true);
    }
}

