
egrep-clone.jar: DFAState.class DFAutomaton.class JUnit.class Main.class NDFAutomatonProf.class NFAState.class NFAutomaton.class RegExTreeProf.class
	jar cvfe egrep-clone.jar Main *

DFAState.class DFAutomaton.class JUnit.class Main.class NDFAutomatonProf.class NFAState.class NFAutomaton.class RegExTreeProf.class: DFAState.java DFAutomaton.java JUnit.java Main.java NDFAutomatonProf.java NFAState.java NFAutomaton.java RegExTreeProf.java
	javac -cp "./junit-platform-console-standalone-1.7.0.jar" ./*.java