public class Node {

    private int value;
    private Node parent;
    private boolean isMarked;
    private int row;
    private int col;

    public Node(int value, Node parent, boolean isMarked, int row, int col) {
        this.value = value;
        this.parent = parent;
        this.isMarked = isMarked;
        this.row = row;
        this.col = col;
    }

    public Node(int value, int row, int col) {
        this(value, null, false, row, col);
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

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
