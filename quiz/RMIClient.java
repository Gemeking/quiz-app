
import java.rmi.Naming;

public class RMIClient {
    public static void main(String[] args) {
        try {
            MyService myService = (MyService) Naming.lookup("rmi://localhost:1099/MyService");

            myService.executeServlet();
            myService.doSomething();
        } catch (Exception e) {
            System.out.println("Client exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

