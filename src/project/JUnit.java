import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
public class JUnit {

    public JUnit() {
    }
    
    // This test compares the matched lines numbers from egrep with our solution
    @Test
    public void testMatchTextFromFile() {
        String regex = "S(a|g|r)*on";
        String path = "/home/azzou/Desktop/56667-0.txt";
        ArrayList<Integer> egrepLinesNumbers = new ArrayList<Integer>();
        
        // Get matched lines numbers using egrep
        Pattern lineNumberPattern = Pattern.compile("(\\d+)");
        try {
            Process process = Runtime.getRuntime().exec("egrep -n " + regex + " " + path);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                // here
                Matcher m = lineNumberPattern.matcher(line);
                m.find();
                egrepLinesNumbers.add(Integer.parseInt(m.group(1)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Mocking an automaton
        Automaton testAutomaton = new Automaton(regex);
        State s1 = new State();
        State s2 = new State();
        State s3 = new State();
        State s4 = new State();
        s1.setItEnd(false);
        s2.setItEnd(false);
        s3.setItEnd(false);
        s4.setItEnd(true);
        s1.addChild(Character.valueOf('S'), s2);
        s2.addChild(Character.valueOf('a'), s2);
        s2.addChild(Character.valueOf('g'), s2);
        s2.addChild(Character.valueOf('r'), s2);
        s2.addChild(Character.valueOf('o'), s3);
        s3.addChild(Character.valueOf('n'), s4);
        testAutomaton.setStartingState(s1);
        
        // Run the test
        assertEquals(egrepLinesNumbers, testAutomaton.matchTextFromFile(path));
    }
}
