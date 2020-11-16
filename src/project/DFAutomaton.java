import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class DFAutomaton {
    private String regEx;
    private DFAState startingState;
    private HashMap<Integer, DFAState> states = new HashMap<Integer, DFAState>();

    public DFAutomaton() {

    }

    public DFAutomaton(String regEx) {
        this.regEx = regEx;
    }

    public DFAState getStartingState() {
        return startingState;
    }

    public void setStartingState(DFAState startingState) {
        this.startingState = startingState;
    }

    public String getRegEx() {
        return regEx;
    }

    public void setRegEx(String regEx) {
        this.regEx = regEx;
    }

    // HERE
    public static DFAutomaton NFA_To_DFA() {
        DFAutomaton result = new DFAutomaton();
        
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
}
