/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author hab
 */
// GrantAccessServlet.java
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class GrantAccessServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Assuming you have a method in your RMI client to grant generic access
        // You might have a method like: grantAccess();

        // Call RMI to grant access
        // You need to implement RMI client logic here

        // Redirect back to the Admin Dashboard
        response.sendRedirect("admin-dashboard.jsp");
    }
}

