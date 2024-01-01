import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class ServerControlFrame extends JFrame {
    private CourseRMIServer server;
    private JLabel statusLabel;

    public ServerControlFrame() {
        setTitle("Server Control");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton startButton = new JButton("Start");
        JButton endButton = new JButton("End");
        statusLabel = new JLabel("Status: Stopped");

        startButton.addActionListener(e -> startServer());
        endButton.addActionListener(e -> stopServer());

        JPanel panel = new JPanel();
        panel.add(startButton);
        panel.add(endButton);
        panel.add(statusLabel);

        add(panel);
    }

    private void startServer() {
        try {
            server = new CourseRMIServer();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("CourseServer", server);
            statusLabel.setText("Status: Running");
            System.out.println("Server started...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopServer() {
    if (server != null) {
        try {
            UnicastRemoteObject.unexportObject(server, true);
            statusLabel.setText("Status: Stopped");
            System.out.println("Server stopped...");
            server = null; // Clear the reference to the server object
        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Status: Error stopping server");
        }
    } else {
        statusLabel.setText("Status: Not running");
    }
}

    public static void main(String[] args) {
        ServerControlFrame frame = new ServerControlFrame();
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                frame.stopServer();
            }
        });
    }
}
