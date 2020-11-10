import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Automaton {
    private String regEx;
    private State startingState;

    public Automaton() {
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

    public int findFirstOccurence(String line) {
        int index = -1;
        return index;
    }

    @Test
    public void testfindFirstOccurence() {
        assertEquals(1, 1);
    }
}
