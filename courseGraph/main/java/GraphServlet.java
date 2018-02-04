import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GraphServlet", urlPatterns = {"/courseGraph"})
public class GraphServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        Graph graph = new Graph();
        graph.insertCourse("cs122b", "Web Development", "cs122a", "ics45j");
        graph.insertCourse("cs122a", "Database", "ics33");
        graph.insertCourse("ics45j", "Java", "ics33");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Graph graph = new Graph();
        graph.insertCourse("cs122b", "Web Development", "cs122a", "ics45j");
        graph.insertCourse("cs122a", "Database", "ics33");
        graph.insertCourse("ics45j", "Java", "ics33");
        response.getWriter().append(graph.toString());
    }
}
