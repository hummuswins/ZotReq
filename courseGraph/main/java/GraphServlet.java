import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// This is the mapping of the our servlet API, so if ran on local machine
// go to localhost:8080/courseGraph
// This is implemented using Tomcat
// Don't mess with this - James
@WebServlet(name = "GraphServlet", urlPatterns = {"/courseGraph"})
public class GraphServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        Graph graph = new Graph();
        graph.readCSV("scrape/courses.csv");
        System.out.println(graph);

        graph.takeCourse(5);

        System.out.println(graph);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        Graph graph = new Graph();
        graph.readCSV("scrape/courses.csv");
        System.out.println(graph);

        graph.takeCourse(5);

        System.out.println(graph);
    }
}
