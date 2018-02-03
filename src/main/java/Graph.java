import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
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

    private void takeCourse(int courseID) {
        Course course = courses.get(courseID);
        course.setTaken(true);
        ArrayList<Integer> requiredFor = course.getReqs();
        for (Integer req : requiredFor) {
            courses.get(req).decrementPreqs();
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
        try {
            Reader in = new FileReader(fileName);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
            for (CSVRecord record : records) {
                String lastName = record.get("Last Name");
                String firstName = record.get("First Name");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
