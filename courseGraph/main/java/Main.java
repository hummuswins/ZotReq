import java.util.ArrayList;

public class Main {
    /**
     * Class Main for testing
     */
    public static void main(String[] args) {
        testTakeCourse();
        //Graph graph = new Graph();
//        graph.insertCourse("cs122b", "Web Development", "cs122a","ics45j");
//        graph.insertCourse("cs122a", "Database", "ics33");
//        graph.insertCourse("ics45j", "Java", "ics33");
        //graph.readCSV("scrape/courses.csv");
        //System.out.println(graph);
    }

    private static void testTakeCourse() {
        Graph graph = new Graph();
        graph.readCSV("scrape/courses.csv");

        for (int i = 0; i < 5; i++) {
            System.out.println("Taking Course: " + i);
            ArrayList<Integer> takeCourseResults = graph.takeCourse(5);
            for (int course : takeCourseResults) {
                System.out.println("Now can take: " + course);
            }
        }

        System.out.println(graph);
    }
}
