import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        String regEx;
        String path;
        if (args.length == 1) {
            regEx = args[0];
            Scanner scanner = new Scanner(System.in);
            System.out.print("  >> Please enter a file path: ");
            path = scanner.next();
            scanner.close();
        } else if (args.length == 2) {
            regEx = args[0];
            path = args[1];
        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.print("  >> Please enter a regEx: ");
            regEx = scanner.next();
            System.out.print("  >> Please enter a file path: ");
            path = scanner.next();
            scanner.close();
        }
        DFAutomaton automaton = DFAutomaton.constructDFAutomaton(regEx);
        if (automaton == null) {
            System.out.println("An error occured");
        } else {
            automaton.matchTextFromFile(path);
        }
    }
}