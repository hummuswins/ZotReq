import java.util.ArrayList;

public class AndNode {
    ArrayList<OrNode> children;

    AndNode() {
        children = new ArrayList<>();
        children.add(new OrNode());
    }

    boolean evaluate() {
        for (int i = 0; i < children.size(); i++) {
            if (!children.get(i).evaluate()) {
                //System.out.println("Cannot take course. And evaluating to false");
                return false;
            }
        }
        //System.out.println("Can take course. And evaluating to true.");
        return true;
    }

    void insertCourse(Course course, boolean newOr) {
        if (!newOr) {
            //System.out.println("Inserting course to current or subtree: " + course.getCourseName());
            int orIndex = children.size() - 1;
            children.get(orIndex).insertCourse(course);
        } else {
            //System.out.println("Inserting course to NEW or subtree: " + course.getCourseName());
            children.add(new OrNode());
            int orIndex = children.size() - 1;
            children.get(orIndex).insertCourse(course);

        }
    }
}