import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class IDFAState {
    private boolean isEnd;
    private HashSet<Integer> name = new HashSet<Integer>();
    private HashMap<Character, IDFAState> children = new HashMap<Character, IDFAState>();

    public IDFAState() {
    }
    
    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    public HashMap<Character, IDFAState> getChildren() {
        return children;
    }

    public void setChildren(HashMap<Character, IDFAState> children) {
        this.children = children;
    }

    public HashSet<Integer> getName() {
        return name;
    }

    public void setName(int name) {
        this.name.add(name);
    }

    public void setNameArray(HashSet<Integer> name) {
        this.name = (HashSet<Integer>) name.clone();
    }

    public void addChild(Character character, IDFAState state) {
        this.children.put(character, state);
    }
}