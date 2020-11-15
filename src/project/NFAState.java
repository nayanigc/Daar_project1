import java.util.HashMap;
import java.util.HashSet;

public class NFAState {
    private boolean isEnd;
    private int name;
    private HashMap<Character, HashSet<NFAState>> children = new HashMap<Character, HashSet<NFAState>>();

    public NFAState() {
    }

    public boolean isEnd() {
        return isEnd;
    }
    
    public void setEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    public HashMap<Character, HashSet<NFAState>> getChildren() {
        return children;
    }

    public void setChildren(HashMap<Character, HashSet<NFAState>> children) {
        this.children = children;
    }
    
    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }
    
    public void addChild(Character character, NFAState state) {
        HashSet<NFAState> temp;
        if ((temp = this.children.get(character)) == null) {
            temp = new HashSet<NFAState>();
            temp.add(state);
            this.children.put(character, temp);
        } else {
            temp.add(state);
        }
    }
}