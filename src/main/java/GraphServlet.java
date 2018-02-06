import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

// This is the mapping of the our servlet API, so if ran on local machine
// go to localhost:8080/courseGraph
// This is implemented using Tomcat
// Don't mess with this - James
@WebServlet(name = "GraphServlet", urlPatterns = {"/courseGraph"})
public class GraphServlet extends HttpServlet {
    Graph graph;

    public GraphServlet() {
        graph = new Graph();
        String path = "/home/hummuswins/apache-tomcat-8.5.24/scraper/courses.csv";
        graph.readCSV(path);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        System.out.println(id);
        ArrayList<Integer> arrayList = graph.takeCourse(Integer.parseInt(id));
        response.getWriter().append(arrayList.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().append(graph.toString());
        graph.takeCourse(5);
    }
}
