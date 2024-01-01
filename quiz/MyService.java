

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MyService extends Remote {
    void executeServlet() throws RemoteException;

    void doSomething() throws RemoteException;
}
