import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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
        String regex = "x*a";
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

        DFAutomaton testAutomaton = DFAutomaton.constructDFAutomaton(regex);
        if (testAutomaton == null) {
            fail();
        } else {
            assertEquals(egrepLinesNumbers, testAutomaton.matchTextFromFile(path));
        }
    }
}
