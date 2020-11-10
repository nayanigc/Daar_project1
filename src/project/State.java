import java.util.HashMap;

public class State {
    private boolean isItEnd;
    private HashMap<String, State> children = new HashMap<String, State>();

    public State() {
    }

    public boolean isItEnd() {
        return isItEnd;
    }

    public void setItEnd(boolean isItEnd) {
        this.isItEnd = isItEnd;
    }

    public HashMap<String, State> getChildren() {
        return children;
    }

    public void setChildren(HashMap<String, State> children) {
        this.children = children;
    }
}