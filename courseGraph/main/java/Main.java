public class Main {
    /**
     * Class Main for testing
     */
    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.insertCourse("cs122b", "Web Development", "cs122a","ics45j");
        graph.insertCourse("cs122a", "Database", "ics33");
        graph.insertCourse("ics45j", "Java", "ics33");
        System.out.println(graph);
    }
}
