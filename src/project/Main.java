class Main {
    public static void main(String[] args) {
        String userInput = "regular_expression";
        String path = "path_to_file";
        Automaton automaton = new Automaton(userInput);
        automaton.matchTextFromFile(path);
    }
}