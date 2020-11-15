class Main {
    public static void main(String[] args) {
        /*String userInput = "regular_expression";
        String path = "path_to_file";
        DFAutomaton automaton = new DFAutomaton(userInput);
        automaton.matchTextFromFile(path);*/
        try {
            RegExTreeProf test = RegExTreeProf.parse("ab|ac");
            System.out.println(test.toString());
            NDFAutomatonProf test2 = NDFAutomatonProf.step2_AhoUllman(test);
            System.out.println(test2.toString());
            NFAutomaton test3 = NFAutomaton.EpsilonNFA_To_NFA(test2);
            System.out.println(test3.toString());
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}