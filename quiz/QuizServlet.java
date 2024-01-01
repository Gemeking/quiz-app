/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author hab
 */
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class QuizServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        String courseName = request.getParameter("course");

        try {
            Registry registry = LocateRegistry.getRegistry("server_ip_address", 1099);
            QuizService quizService = (QuizService) registry.lookup("QuizService");

            List<String> questions = quizService.getQuestionsForCourse(courseName);

            // Prepare the HTML response with questions
            StringBuilder htmlResponse = new StringBuilder("<html><body><h1>Questions for " + courseName + ":</h1><ul>");
            for (String question : questions) {
                htmlResponse.append("<li>").append(question).append("</li>");
            }
            htmlResponse.append("</ul></body></html>");

            response.getWriter().println(htmlResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error accessing server");
        }
    }
}

