



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author hab
 */import java.rmi.Remote;
import java.rmi.RemoteException;

public interface QuizService extends Remote {
    String getQuestion() throws RemoteException;
}


