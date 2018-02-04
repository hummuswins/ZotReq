public class ClassNode extends BooleanTreeNode {
    Course course;

    ClassNode(Course course) {
        this.course = course;
    }

    @Override
    boolean evaluate() {
        return course.isTaken();
    }
}
