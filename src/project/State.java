import java.util.HashMap;

public class State {
    private boolean isItEnd;
    private HashMap<Character, State> children = new HashMap<Character, State>();

    public State() {
    }

    public boolean isItEnd() {
        return isItEnd;
    }

    public void setItEnd(boolean isItEnd) {
        this.isItEnd = isItEnd;
    }

    public HashMap<Character, State> getChildren() {
        return children;
    }

    public void setChildren(HashMap<Character, State> children) {
        this.children = children;
    }

    public void addChild(Character character, State state) {
        this.children.put(character, state);
    }
}