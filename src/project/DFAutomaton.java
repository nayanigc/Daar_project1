import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class DFAutomaton {
    private String regEx;
    private DFAState startingState;
    private HashMap<HashSet<Integer>, DFAState> states = new HashMap<HashSet<Integer>, DFAState>();

    public DFAutomaton() {
    }

    public static DFAutomaton constructDFAutomaton(String regEx) {
        DFAutomaton result;
        try {
            RegExTreeProf tree = RegExTreeProf.parse(regEx);
            NDFAutomatonProf automaton1 = NDFAutomatonProf.step2_AhoUllman(tree);
            NFAutomaton automaton2 = NFAutomaton.EpsilonNFA_To_NFA(automaton1);
            result = DFAutomaton.NFA_To_IDFA(automaton2);
            result.setRegEx(regEx);
            return result;
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public String getRegEx() {
        return regEx;
    }

    public void setRegEx(String regEx) {
        this.regEx = regEx;
    }

    public DFAState getStartingState() {
        return startingState;
    }

    public void setStartingState(DFAState startingState) {
        this.startingState = startingState;
    }

    public HashMap<HashSet<Integer>, DFAState> getStates() {
        return states;
    }

    public void setStates(HashMap<HashSet<Integer>, DFAState> states) {
        this.states = states;
    }

    public static DFAutomaton NFA_To_IDFA(NFAutomaton automaton) {
        DFAutomaton result = new DFAutomaton();
        result.startingState = new DFAState();
        result.startingState.setEnd(automaton.getStartingState().isEnd());
        result.startingState.setName(automaton.getStartingState().getName());
        HashSet<Integer> temp = new HashSet<Integer>();
        temp.add(automaton.getStartingState().getName());
        result.states.put(temp, result.startingState);
        boolean newStateAdded = true;
        // Core
        HashMap<Character, HashSet<Integer>> transitions; 
        while (newStateAdded) {
            newStateAdded = false;
            Iterator it1 = result.states.entrySet().iterator();
            while (it1.hasNext() && !newStateAdded) {
                transitions = new HashMap<Character, HashSet<Integer>>();
                Map.Entry<HashSet<Integer>, DFAState> pair1 = (Map.Entry)it1.next();
                for (int i: pair1.getKey()) {
                    Iterator it2 = automaton.getStates().get(i).getChildren().entrySet().iterator();
                    while (it2.hasNext()) {
                        Map.Entry<Character, HashSet<NFAState>> pair2 = (Map.Entry)it2.next();
                        for (NFAState s: pair2.getValue()) {
                            if (transitions.get(pair2.getKey()) == null) {
                                HashSet<Integer> temp2 = new HashSet<Integer>();
                                temp2.add(s.getName());
                                transitions.put(pair2.getKey(), temp2);
                            } else {
                                transitions.get(pair2.getKey()).add(s.getName());
                            }
                        }
                    }
                }
                // Start here tomorrow
                // Add transitions
                Iterator it3 = transitions.entrySet().iterator();
                while (it3.hasNext()) {
                    Map.Entry<Character, HashSet<Integer>> pair3 = (Map.Entry)it3.next();
                    DFAState tempState = result.states.get(pair3.getValue());
                    if (tempState == null) {
                        // Create new state
                        newStateAdded = true;
                        DFAState newState = new DFAState();
                        newState.setNameArray(pair3.getValue());
                        boolean isEnd = false;
                        for (int k : pair3.getValue()) {
                            if (automaton.getEndingStates().contains(k)) {
                                isEnd = true;
                                break;
                            }
                        }
                        newState.setEnd(isEnd);
                        result.states.put(pair3.getValue(), newState);
                    } else {
                        pair1.getValue().addChild(pair3.getKey(), tempState);
                    }
                }
            }
        }
        return result;
    }

    public boolean matchOneLine(String line) {
        if (this.startingState.isEnd())
            return true;
        int index = 0, tempIndex;
        DFAState currentState;
        while (index < line.length()) {
            tempIndex = index - 1;
            currentState = this.startingState;
            do {
                tempIndex++;
            } while (tempIndex < line.length() && (currentState = currentState.getChildren().get(Character.valueOf(line.charAt(tempIndex)))) != null && !currentState.isEnd());
            if (currentState != null && currentState.isEnd())
                return true;
            index++;
        }
        return false;
    }

    public ArrayList<Integer> matchTextFromFile(String path) {
        ArrayList<Integer> egrepCloneLinesNumbers = new ArrayList<Integer>();
        int lineCount = 1;
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (this.matchOneLine(data)) {
                    System.out.println(data);
                    egrepCloneLinesNumbers.add(lineCount);
                }
                lineCount++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return egrepCloneLinesNumbers;
    }

    public String toString() {
        String result = "";
        String startingStateString = "Starting State is : " + startingState.getName() + "\n";
        String endingStatesString = "Ending States are :";
        result += startingStateString;
        Iterator it0 = states.entrySet().iterator();
        while (it0.hasNext()) {
            Map.Entry<HashSet<Integer>, DFAState> pair0 = (Map.Entry)it0.next();
            if (pair0.getValue().isEnd())
                endingStatesString += "  " + pair0.getValue().getName();
            Iterator it = pair0.getValue().getChildren().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Character, DFAState> pair = (Map.Entry)it.next();
                    result += pair0.getValue().getName() + "---" + pair.getKey() + "--->" + pair.getValue().getName() + "\n";
            }
        }
        result += endingStatesString + "\n";
        return result;
    }
}
