import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.server.UnicastRemoteObject;

public class ServerControlPanel extends JPanel {
    private CourseRMIServer courseServer;

    public ServerControlPanel(CourseRMIServer server) {
        this.courseServer = server;

        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Stop");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startServer();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopServer();
            }
        });

        add(startButton);
        add(stopButton);
    }

    public void startServer() {
        try {
            courseServer = new CourseRMIServer();
            // Start RMI server code
            // ...
            System.out.println("Server started...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        try {
            if (courseServer != null) {
                UnicastRemoteObject.unexportObject(courseServer, true);
                System.out.println("Server stopped...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Terminate the program
            System.exit(0);
        }
    }
}
