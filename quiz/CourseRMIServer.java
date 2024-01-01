import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List; // Updated import for List
import javax.swing.JOptionPane;



public class CourseRMIServer extends UnicastRemoteObject implements CourseRMIInterface {
    private Connection connection;

    public CourseRMIServer() throws RemoteException {
        super();
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/QUIZ?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error connecting to the database");
            System.exit(1);
        }
    }

     public List<String[]> getAllCourses() throws RemoteException {
        List<String[]> coursesList = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM course");

            while (rs.next()) {
                String[] course = new String[2];
                course[0] = rs.getString("courseid");
                course[1] = rs.getString("cname");
                coursesList.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error retrieving courses: " + e.getMessage());
        }

        return coursesList;
    }


  public List<String[]> getAllBlankSpaceQuestions() throws RemoteException {
    List<String[]> questionsList = new ArrayList<>();

    try {
        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stmt.executeQuery("SELECT * FROM blank_space");

        while (rs.next()) {
            String[] question = new String[5]; // Assuming 5 columns in your blank space table
            question[0] = rs.getString("question_text");
            question[1] = rs.getString("correct_answer");
            question[2] = rs.getString("courseid");
            question[3] = rs.getString("B_SId");
            question[4] = rs.getString("question_type");
            // Adjust column names ("question_text", "correct_answer", etc.) as per your database schema
            questionsList.add(question);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.err.println("Error retrieving blank space questions: " + e.getMessage());
    }

    return questionsList;
}


   public List<String[]> getAllMultipleChoiceQuestions() throws RemoteException {
    List<String[]> questionsList = new ArrayList<>();

    try {
        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stmt.executeQuery("SELECT * FROM multiplechoice");

        while (rs.next()) {
            String[] question = new String[6]; // Assuming 6 columns in your multiple choice table
            question[0] = rs.getString("question_text");
            question[1] = rs.getString("option_a");
            question[2] = rs.getString("option_b");
            question[3] = rs.getString("option_c");
            question[4] = rs.getString("option_d");
            question[5] = rs.getString("correct_answer");
            // Adjust column names ("question_text", "option_a", etc.) as per your database schema
            questionsList.add(question);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.err.println("Error retrieving multiple choice questions: " + e.getMessage());
    }

    return questionsList;
}



    public List<String[]> getAllTrueFalseQuestions() throws RemoteException {
    List<String[]> questionsList = new ArrayList<>();

    try {
        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stmt.executeQuery("SELECT * FROM true_false");

        while (rs.next()) {
            String[] question = new String[5]; // Assuming 5 columns based on your table structure
            question[0] = rs.getString("question_text");
            question[1] = rs.getString("correct_answer");
            question[2] = rs.getString("courseid");
            question[3] = rs.getString("T_FId");
            question[4] = rs.getString("question_type");
            questionsList.add(question);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.err.println("Error retrieving true/false questions: " + e.getMessage());
    }

    return questionsList;
}


   public List<String[]> getAllUserDetails() throws RemoteException {
    List<String[]> userDetailsList = new ArrayList<>();

    try {
        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stmt.executeQuery("SELECT sID, sUname FROM users");

        while (rs.next()) {
            String[] userDetails = new String[2]; // Assuming 2 columns: sID, sUname
            userDetails[0] = rs.getString("sID");
            userDetails[1] = rs.getString("sUname");
            userDetailsList.add(userDetails);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.err.println("Error retrieving user details: " + e.getMessage());
    }

    return userDetailsList;
}

public List<String[]> getCourseTrueFalseQuestions(String courseId) throws RemoteException {
    List<String[]> questionsList = new ArrayList<>();

    try {
        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String query = "SELECT question_text, correct_answer, courseid, T_FId, question_type FROM true_false WHERE courseid = '" + courseId + "'";
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            String[] question = new String[5]; // Assuming 5 columns: question_text, correct_answer, courseid, T_FId, question_type
            question[0] = rs.getString("question_text");
            question[1] = rs.getString("correct_answer");
            question[2] = rs.getString("courseid");
            question[3] = rs.getString("T_FId");
            question[4] = rs.getString("question_type");
            questionsList.add(question);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.err.println("Error retrieving true/false questions for the course: " + e.getMessage());
    }

    return questionsList;
}

public List<String[]> getCourseMultipleChoiceQuestions(String courseId) throws RemoteException {
    List<String[]> questionsList = new ArrayList<>();

    try {
        PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM multiplechoice WHERE courseid = ?");
        pstmt.setString(1, courseId);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            String[] question = new String[6]; // Assuming 6 columns in your multiple choice table
            question[0] = rs.getString("question_text");
            question[1] = rs.getString("option_a");
            question[2] = rs.getString("option_b");
            question[3] = rs.getString("option_c");
            question[4] = rs.getString("option_d");
            question[5] = rs.getString("correct_answer");
            // Adjust column names ("question_text", "option_a", etc.) as per your database schema
            questionsList.add(question);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.err.println("Error retrieving multiple choice questions for the course: " + e.getMessage());
    }

    return questionsList;
}



    
   public List<String[]> getCourseBlankSpaceQuestions(String courseId) throws RemoteException {
    List<String[]> questionsList = new ArrayList<>();

    try {
        PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM blank_space WHERE courseid = ?");
        pstmt.setString(1, courseId);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            String[] question = new String[5]; // Assuming 5 columns in your blank space table
            question[0] = rs.getString("question_text");
            question[1] = rs.getString("correct_answer");
            question[2] = rs.getString("courseid");
            question[3] = rs.getString("B_SId");
            question[4] = rs.getString("question_type");
            // Adjust column names ("question_text", "correct_answer", etc.) as per your database schema
            questionsList.add(question);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.err.println("Error retrieving blank space questions for the course: " + e.getMessage());
    }

    return questionsList;
}


public void insertUserScore(int scoreId, int userId, int courseId, int scoreValue) throws RemoteException {
    try {
        PreparedStatement pstmt = connection.prepareStatement("INSERT INTO user_scores (score_id, sID, courseid, score) VALUES (?, ?, ?, ?)");
        pstmt.setInt(1, scoreId);
        pstmt.setInt(2, userId);
        pstmt.setInt(3, courseId);
        pstmt.setInt(4, scoreValue);
        pstmt.executeUpdate();
        System.out.println("User score inserted successfully!");
    } catch (SQLException e) {
        e.printStackTrace();
        System.err.println("Error inserting user score: " + e.getMessage());
    }
}



    public static void main(String[] args) {
        try {
            CourseRMIServer server = new CourseRMIServer();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("CourseServer", server);
            System.out.println("Server started...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  
}
