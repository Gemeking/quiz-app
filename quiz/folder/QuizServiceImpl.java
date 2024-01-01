package folder;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author hab
 */
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class QuizServiceImpl extends UnicastRemoteObject implements QuizService {
    private List<Question> quizQuestions; // Placeholder for quiz questions

    public QuizServiceImpl() throws RemoteException {
        super();
        // Initialize quizQuestions with sample questions
        quizQuestions = new ArrayList<>();
        quizQuestions.add(new Question("What is 2+2?", "4", "3", "2", "1", "4"));
        // Add more questions as needed
    }

    @Override
    public void startQuiz() throws RemoteException {
        // Logic to start the quiz
        System.out.println("Quiz started!");
    }

    @Override
    public void endQuiz() throws RemoteException {
        // Logic to end the quiz
        System.out.println("Quiz ended!");
    }

    @Override
    public List<Question> getQuizQuestions() throws RemoteException {
        // Return the list of quiz questions
        return quizQuestions;
    }
}
