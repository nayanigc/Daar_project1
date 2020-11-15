import java.util.HashMap;

public class DFAState {
    private boolean isEnd;
    private int name;
    private HashMap<Character, DFAState> children = new HashMap<Character, DFAState>();

    public DFAState() {
    }
    
    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    public HashMap<Character, DFAState> getChildren() {
        return children;
    }

    public void setChildren(HashMap<Character, DFAState> children) {
        this.children = children;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public void addChild(Character character, DFAState state) {
        this.children.put(character, state);
    }
}