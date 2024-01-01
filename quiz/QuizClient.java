/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author hab
 */
import java.rmi.Naming;

public class QuizClient {
    public static void main(String[] args) {
        try {
            // Lookup the remote QuizService object from the RMI Registry
            QuizService quizService = (QuizService) Naming.lookup("//localhost/QuizService");

            // Invoke remote method to get a question
            String question = quizService.getQuestion();
            System.out.println("Question from server: " + question);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

