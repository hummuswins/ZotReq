import java.util.ArrayList;

public class AndNode {
    ArrayList<OrNode> children;

    AndNode() {
        children = new ArrayList<>();
        children.add(new OrNode());
    }

    boolean evaluate() {
        for (int i = 0; i < children.size(); i++) {
            if (!children.get(i).evaluate())
                return false;
        }
        return true;
    }

    void insertCourse(Course course, boolean newOr) {
        if (!newOr) {
            int orIndex = children.size() - 1;
            children.get(orIndex).insertCourse(course);
        } else {
            children.add(new OrNode());
            int orIndex = children.size() - 1;
            children.get(orIndex).insertCourse(course);
        }
    }
}
