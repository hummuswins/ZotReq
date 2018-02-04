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
        courses = new ArrayList<>();
    }


    void takeCourse(String courseName) {
        Integer id = courseToID.get(courseName);
        takeCourse(id);
    }

    ArrayList<Integer> takeCourse(int courseID) {
        ArrayList<Integer> newCanTakeCourses = new ArrayList<>();

        Course course = courses.get(courseID);
        course.setTaken(true);

        System.out.println("Taking Course: " + course.getCourseName());

        ArrayList<Integer> requiredFor = course.getReqs();
        for (Integer req : requiredFor) {
            Course updateCourse = courses.get(req);
            updateCourse.decrementPreqs();
            if (!updateCourse.isTaken() && updateCourse.canTake()) {
                newCanTakeCourses.add(req);
            }
        }

        return newCanTakeCourses;
    }

    ArrayList<Integer> untakeCourse(String courseName) {
        ArrayList<Integer> newCannotTakeCourses = new ArrayList<>();

        Integer courseID = getID(courseName);
        Course course = courses.get(courseID);
        ArrayList<Integer> requiredFor = course.getReqs();
        for (Integer req : requiredFor) {
            Course updateCourse = courses.get(req);
            updateCourse.incremementReqs();
            if (!updateCourse.canTake()) {
                newCannotTakeCourses.add(req);
            }
        }

        return newCannotTakeCourses;
    }

    /**
     * Insert course into the graph
     *
     * @param course      The ID of the course
     * @param courseTitle The title of the course
     * @param preqs       Array of perquisite courses' ID
     */
    void insertCourse(String course, String courseTitle, ArrayList<String> preqs) {
        System.out.println("Inserting: " + course + " " + courseTitle + ": ");
        if (!courseToID.containsKey(course)) {
            courseToID.put(course, numCourses);
            numCourses++;
            courses.add(new Course(course, courseTitle));
        } else {
            courses.get(courseToID.get(course)).setCourseTitle(courseTitle);
        }

        int courseIndex = courseToID.get(course);
        boolean newOr = false;

        System.out.print("Preqs: ");
        for (String preq : preqs) {
            if (preq.contains("AND")) {
                newOr = true;
            } else {
                if (!courseToID.containsKey(preq)) {
                    courseToID.put(preq, numCourses);
                    numCourses++;
                    courses.add(new Course(preq, null));
                }
                int preqIndex = courseToID.get(preq);
                courses.get(courseIndex).addPreq(courses.get(preqIndex), preqIndex, newOr);
                courses.get(preqIndex).addReq(courseIndex);

                newOr = false;
            }
            System.out.print(preq);
        }

        System.out.println();
    }

    // TODO: Remove course based on courseId
    public void removeCourse(int courseId) {

    }

    /**
     * This will be the DOT representation for the graph (doing it manually,
     * if you have any automatic way, I welcome it.
     * @return DOT notation of the graph
     */
    @Override
    public String toString() {
        System.out.println("Took " + numCourses + " courses.");
        StringBuilder builder = new StringBuilder();
        builder.append("course {\n");
        for (Course course : courses) {
            builder.append(course.getCourseName()).append(";\n");

            ArrayList<Integer> reqs = course.getReqs();
            if (!reqs.isEmpty()) {
                for (int courseId : reqs)
                    builder.append(course.getCourseName())
                            .append("->")
                            .append(courseToID.inverse().get(courseId))
                            .append(";\n");
            }
        }
        builder.append("}");
        return builder.toString();
    }

    /**
     * I didn't do anything with this yet (Please halp).
     * @param fileName name of file
     */
    void readCSV(String fileName) {
        try {
            Reader in = new FileReader(fileName);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
            for (CSVRecord record : records) {
                ArrayList<String> preqs = new ArrayList<>();
                preqs.ensureCapacity(record.size() - 2);

                //System.out.print("Reading csv record: " + record.get(0) + ", " + record.get(1) + ", ");
                for (int i = 2; i < record.size(); i++) {
                    System.out.print(record.get(i) + ", ");
                    String column = record.get(i);
                    if (column != "")
                        preqs.add(column);
                }
                //System.out.println();
                insertCourse(record.get(0), record.get(1), preqs);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    int getID(String courseName) {
        return courseToID.get(courseName);
    }

    String getName(int courseID) {
        return courseToID.inverse().get(courseID);
    }

    void printCourseArray() {
        for (Course course : courses) {
            assert (!course.getCourseName().contains("AND"));
            System.out.print(course.getCourseName() + "  " + course.getCourseTitle() + " ");
            for (int courseID : course.getReqs()) {
                System.out.print(getName(courseID) + " ");
            }
            System.out.println();
        }
    }
}
