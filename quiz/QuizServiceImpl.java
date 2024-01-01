import ada.QuizService;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;

public class QuizServiceImpl extends UnicastRemoteObject implements QuizService {
    private Connection connection;

    public QuizServiceImpl() throws RemoteException {
        super();
        // Initialize your database connection here
        try {
            // Load your database driver
            Class.forName("your_database_driver");
            // Establish the connection
            connection = DriverManager.getConnection("your_database_url", "username", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getQuestion() throws RemoteException {
        String question = "";
        try {
            // Retrieve a question from the database
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT question FROM your_question_table ORDER BY RAND() LIMIT 1");

            if (resultSet.next()) {
                question = resultSet.getString("question");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return question;
    }

    public static void main(String[] args) {
        try {
            QuizService quizService = new QuizServiceImpl();
            // Bind the implementation to the RMI registry
            // Naming.bind("QuizService", quizService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
