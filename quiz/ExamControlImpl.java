


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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExamControlImpl extends UnicastRemoteObject implements ExamControl {
    protected ExamControlImpl() throws RemoteException {
        super();
    }

    public String getQuestionsForCourse(String courseName) throws RemoteException {
        StringBuilder questions = new StringBuilder();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/QUIZ", "root", "")) {
            String query = "SELECT * FROM blank_space WHERE courseid = (SELECT courseid FROM course WHERE cname = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, courseName);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                questions.append(resultSet.getString("question_text")).append("\n");
                // Add other details based on the question table schema
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions.toString();
    }

    @Override
    public String getQuestions(String username) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}


