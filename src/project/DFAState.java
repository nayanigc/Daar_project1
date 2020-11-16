import java.util.HashMap;
import java.util.HashSet;

public class DFAState {
    private boolean isEnd;
    private HashSet<Integer> name = new HashSet<Integer>();
    private HashMap<Character, DFAState> children = new HashMap<Character, DFAState>();
    
    // DFAState stands for Deterministic Finite Automaton State
    // children : used to map a DFAState into a character (ex: using 'a' we go to state 1); cotrary to NFAState we need only one state holder because it's a DFA
    // We used hashSet in name instead of array to avoid the process of verifying duplicats and because the name used to execute the subset construction algorithm
    // which convets a NFA into a DFA (that's why is not one integer as in NFAState).

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

    public HashSet<Integer> getName() {
        return name;
    }

    public void setName(int name) {
        this.name.add(name);
    }

    public void setNameArray(HashSet<Integer> name) {
        this.name = (HashSet<Integer>) name.clone();
    }

    public void addChild(Character character, DFAState state) {
        this.children.put(character, state);
    }
}
