import java.util.ArrayList;

public class OrNode {
    ArrayList<Course> leaves;

    OrNode() {
        leaves = new ArrayList<>();
    }

    boolean evaluate() {
        for (int i = 0; i < leaves.size(); i++) {
            if (leaves.get(i).isTaken()) {
                //System.out.println("Or Node returning true " + leaves.get(i).getCourseName() + " was taken.");
                return true;
            }

        }
        //System.out.println("Or node returning false. Cannot take course");
        return false;
    }

    void insertCourse(Course course) {
        leaves.add(course);
    }
}
