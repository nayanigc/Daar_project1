import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Automaton {
    private String regEx;
    private State startingState;

    public Automaton() {

    }

    public Automaton(String regEx) {
        this.regEx = regEx;
        this.construct();
    }

    public State getStartingState() {
        return startingState;
    }

    public void setStartingState(State startingState) {
        this.startingState = startingState;
    }

    public String getRegEx() {
        return regEx;
    }

    public void setRegEx(String regEx) {
        this.regEx = regEx;
    }

    // Nayani's function here
    // This function is called from the automaton constructor
    // The starting state will be stored in the attribute named "startingState"
    public void construct() {
    }

    public boolean matchOneLine(String line) {
        if (this.startingState.isItEnd())
            return true;
        int index = 0, tempIndex;
        State currentState, previousState;
        while (index < line.length()) {
            tempIndex = index - 1;
            currentState = this.startingState;
            do {
                previousState = currentState;
                tempIndex++;
            } while (tempIndex < line.length() && (currentState = currentState.getChildren().get(Character.valueOf(line.charAt(tempIndex)))) != null);
            if (previousState.isItEnd())
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
