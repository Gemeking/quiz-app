package folder;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author hab
 */
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface QuizService extends Remote {
    // Method to start the quiz
    void startQuiz() throws RemoteException;

    // Method to end the quiz
    void endQuiz() throws RemoteException;

    // Method to retrieve quiz questions
    List<Question> getQuizQuestions() throws RemoteException;

    // Other quiz-related methods can be added here
}

