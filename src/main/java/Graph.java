import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.ArrayList;

public class Graph {
    private BiMap<String, Integer> courseToID;
    private int numCourses;
    private ArrayList<Course> courses;

    Graph() {
        courseToID = HashBiMap.create();
        numCourses = 0;
        courses = new ArrayList<Course>();
    }

    void takeCourse(String courseName) {
        Integer id = courseToID.get(courseName);
        takeCourse(id);
    }

    void takeCourse(int courseID) {
        Course course = courses.get(courseID);
        course.setTaken(true);
        ArrayList<Integer> requiredFor = course.getReqs();
        for (int i = 0; i < requiredFor.size(); i++) {
            courses.get(requiredFor.get(i)).decrementPreqs();
        }
    }

    void insertCourse(String course, String courseTitle, String... preqs) {
        if (!courseToID.containsKey(course)) {
            courseToID.put(course, numCourses);
            courses.add(new Course(course, courseTitle));
            numCourses++;
        } else {
            courses.get(courseToID.get(course)).setCourseTitle(courseTitle);
        }

        int courseIndex = courseToID.get(course);

        for (int i = 0; i < preqs.length; i++) {
            if (!courseToID.containsKey(preqs[i])) {
                courseToID.put(preqs[i], numCourses);
                courses.add(new Course(preqs[i], null));
                numCourses++;
            }
            int preqIndex = courseToID.get(preqs[i]);
            courses.get(courseIndex).addPreq(preqIndex);
            courses.get(preqIndex).addReq(courseIndex);
        }
    }

    void getDotFile() {

    }

    void readCSV(String fileName) {

    }
}
