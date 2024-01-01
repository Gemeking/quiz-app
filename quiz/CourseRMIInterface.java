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

public interface CourseRMIInterface extends Remote {

    List<String[]> getAllCourses() throws RemoteException;

    List<String[]> getAllBlankSpaceQuestions() throws RemoteException;

    List<String[]> getAllMultipleChoiceQuestions() throws RemoteException;

    List<String[]> getAllTrueFalseQuestions() throws RemoteException;

    List<String[]> getAllUserDetails() throws RemoteException;

    List<String[]> getCourseTrueFalseQuestions(String courseId) throws RemoteException;

    List<String[]> getCourseMultipleChoiceQuestions(String courseId) throws RemoteException;

    List<String[]> getCourseBlankSpaceQuestions(String courseId) throws RemoteException;

    void insertUserScore(int scoreId, int userId, int courseId, int scoreValue) throws RemoteException;
}




