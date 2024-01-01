

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyServer extends UnicastRemoteObject implements MyService {

    public MyServer() throws RemoteException {
        super();
    }

    @Override
    public void executeServlet() throws RemoteException {
        // Your logic for executing servlet from here
        System.out.println("Method not yet supported.");
    }

    @Override
    public void doSomething() throws RemoteException {
        System.out.println("Doing something in MyServer...");
    }

    public void executeServlet(HttpServletRequest request, HttpServletResponse response) throws RemoteException {
        try {
            System.out.println("Executing servlet class on the server...");
            ExamServlet servlet = new ExamServlet();
            servlet.processRequest(request, response);
        } catch (ServletException | IOException ex) {
            ex.printStackTrace();
            throw new RemoteException("Failed to execute servlet on the server", ex);
        }
    }

    public static void main(String[] args) {
        try {
            MyServer server = new MyServer();
            Registry registry = LocateRegistry.createRegistry(1099); // Using default RMI port
            registry.rebind("MyService", server);
            System.out.println("Server started.");

            // Execute ExamServlet here after the server has started
            HttpServletRequest request = null; // Replace with actual request
            HttpServletResponse response = null; // Replace with actual response
            server.executeServlet(request, response);
        } catch (Exception e) {
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
