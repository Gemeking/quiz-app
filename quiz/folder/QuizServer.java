package folder;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author hab
 */
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class QuizServer extends UnicastRemoteObject implements QuizServerInterface {
    public QuizServer() throws RemoteException {
        super();
    }

    public String getQuizPage() throws RemoteException {
        String htmlContent = "";
        try {
            // Read HTML content from the file
            htmlContent = new String(Files.readAllBytes(Paths.get("quiz_questions.html")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return htmlContent;
    }

    public static void main(String[] args) {
        try {
            QuizServer server = new QuizServer();
            java.rmi.registry.LocateRegistry.createRegistry(5000);
            java.rmi.Naming.rebind("QuizService", server);
            System.out.println("QuizServer is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startQuiz() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void endQuiz() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Question> retrieveQuestions() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

