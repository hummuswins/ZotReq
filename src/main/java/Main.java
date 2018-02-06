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
        graph.readCSV("/home/hummuswins/apache-tomcat-8.5.24/scraper/courses.csv");


        //System.out.println("Printing contents of course array.");
        //graph.printCourseArray();

        ArrayList<Integer> takeCourseResults = graph.takeCourse(5);
        for (int i = 0; i < 25; i++) {
            System.out.println("Taking Course: " + i);
            takeCourseResults = graph.takeCourse(i);
            for (int course : takeCourseResults) {
                System.out.println("Now can take: " + course);
            }
        }

        System.out.println("Printing Graph");
        System.out.println(graph);
    }
}