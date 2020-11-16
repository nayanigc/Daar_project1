import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
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
        /*
        // Mocking an automaton
        IDFAutomaton testAutomaton = new IDFAutomaton();
        IDFAState s1 = new IDFAState();
        IDFAState s2 = new IDFAState();
        IDFAState s3 = new IDFAState();
        IDFAState s4 = new IDFAState();
        s1.setEnd(false);
        s2.setEnd(false);
        s3.setEnd(false);
        s4.setEnd(true);
        s1.addChild(Character.valueOf('S'), s2);
        s2.addChild(Character.valueOf('a'), s2);
        s2.addChild(Character.valueOf('g'), s2);
        s2.addChild(Character.valueOf('r'), s2);
        s2.addChild(Character.valueOf('o'), s3);
        s3.addChild(Character.valueOf('n'), s4);
        testAutomaton.setStartingState(s1);
        */
        RegExTreeProf test;
        NDFAutomatonProf test2;
        NFAutomaton test3;
        IDFAutomaton testAutomaton;
        try {
            test = RegExTreeProf.parse(regex);
            System.out.println(test.toString());
            test2 = NDFAutomatonProf.step2_AhoUllman(test);
            System.out.println(test2.toString());
            test3 = NFAutomaton.EpsilonNFA_To_NFA(test2);
            System.out.println(test3.toString());
            testAutomaton = IDFAutomaton.NFA_To_IDFA(test3);
            System.out.println(testAutomaton.toString());
            assertEquals(egrepLinesNumbers, testAutomaton.matchTextFromFile(path));
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
