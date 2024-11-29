package code;

import java.util.ArrayList;

public class Node {
    State state;
    ArrayList<Node> children;
    int level;

    public Node(State state, boolean isFirst, int level) {
        if (isFirst) {
            this.state = state;
        }
        else {
            this.state = new State(state);
        }
        this.level = level;
        this.children = new ArrayList<Node>();
    }
}
