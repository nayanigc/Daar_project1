import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;

public class NFAutomaton {
    private String regEx;
    private NFAState startingState;
    private ArrayList<Integer> endingStates = new ArrayList<Integer>();
    private HashMap<Integer, NFAState> states = new HashMap<Integer, NFAState>();

    public NFAutomaton() {

    }

    public String getRegEx() {
        return regEx;
    }

    public void setRegEx(String regEx) {
        this.regEx = regEx;
    }

    public NFAState getStartingState() {
        return startingState;
    }

    public void setStartingState(NFAState startingState) {
        this.startingState = startingState;
    }

    public String toString() {
        String result = "";
        String startingStateString = "Starting State is : " + startingState.getName() + "\n";
        String endingStatesString = "Ending States are :";
        result += startingStateString;
        Iterator it0 = states.entrySet().iterator();
        while (it0.hasNext()) {
            Map.Entry<Integer, NFAState> pair0 = (Map.Entry)it0.next();
            if (pair0.getValue().isEnd())
                endingStatesString += "  " + pair0.getValue().getName();
            Iterator it = pair0.getValue().getChildren().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Character, HashSet<NFAState>> pair = (Map.Entry)it.next();
                HashSet<NFAState> destStates = pair.getValue();
                for (NFAState destState: destStates) {
                    result += pair0.getValue().getName() + "---" + pair.getKey() + "--->" + destState.getName() + "\n";
                }
            }
        }
        result += endingStatesString + "\n";
        return result;
    }

    // HERE
    public static LinkedHashSet<Integer> stateClosure(ArrayList<Integer>[] epsilonTransitionTable, int state) {
        LinkedHashSet<Integer> result = new LinkedHashSet<Integer>();
        result.add(state);
        Integer[] array = result.toArray( new Integer[result.size()] );
        int index = 0;
        do {
            array = result.toArray( new Integer[result.size()] );
            for (; index < array.length; index++) {
                epsilonTransitionTable[array[index]].forEach(elt -> {
                    result.add(elt);
                });
            }
        } while (result.size() != array.length);
        return result;
    }

    public static NFAutomaton EpsilonNFA_To_NFA(NDFAutomatonProf automaton) {
        NFAutomaton result = new NFAutomaton();
        // Grab important states
        result.states.put(0, new NFAState());
        result.states.get(0).setEnd(false);
        result.states.get(0).setName(0);
        result.setStartingState(result.states.get(0));
        for (int i = 0; i < automaton.transitionTable.length-1; i++) {
            for (int j = 0; j < 256; j++) {
                if (automaton.transitionTable[i][j] != -1) {
                    if (result.states.put(automaton.transitionTable[i][j], new NFAState()) == null) {
                        result.states.get(automaton.transitionTable[i][j]).setEnd(false);
                        result.states.get(automaton.transitionTable[i][j]).setName(automaton.transitionTable[i][j]);
                    }
                }
            }
        }
        
        result.states.forEach((key, value) -> {
            LinkedHashSet<Integer> closure;
            closure = NFAutomaton.stateClosure(automaton.epsilonTransitionTable, key);
            if (closure.contains(automaton.transitionTable.length-1)) {
                result.states.get(key).setEnd(true);
                result.endingStates.add(key);
            }
            for (Integer state: closure) {
                for (int j = 0; j < 256; j++) {
                    if (automaton.transitionTable[state][j] != -1) {
                        result.states.get(key)
                            .addChild(
                                Character.valueOf((char) j),
                                result.states.get(automaton.transitionTable[state][j]));
                    }
                }
            }
        });
        return result;
    }

    public HashMap<Integer, NFAState> getStates() {
        return states;
    }

    public void setStates(HashMap<Integer, NFAState> states) {
        this.states = states;
    }

    public ArrayList<Integer> getEndingStates() {
        return endingStates;
    }

    public void setEndingStates(ArrayList<Integer> endingStates) {
        this.endingStates = endingStates;
    }

    
}
