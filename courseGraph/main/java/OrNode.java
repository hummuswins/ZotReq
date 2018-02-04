import java.util.ArrayList;

public class OrNode {
    ArrayList<Course> leaves;

    OrNode() {
        leaves = new ArrayList<>();
    }

    boolean evaluate() {
        for (int i = 0; i < leaves.size(); i++) {
            if (leaves.get(i).isTaken())
                return true;
        }
        return false;
    }

    void insertCourse(Course course) {
        leaves.add(course);
    }
}
