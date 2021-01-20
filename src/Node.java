public class Node {

    private int value;
    private Node parent;
    private boolean isMarked;

    public Node(int value, Node parent, boolean isMarked) {
        this.value = value;
        this.parent = parent;
        this.isMarked = isMarked;
    }

    public Node(int value) {
        this(value, null, false);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }
}
