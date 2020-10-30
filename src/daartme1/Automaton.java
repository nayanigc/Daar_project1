package daartme1;

import java.util.HashSet;
import java.util.Set;

public class Automaton {
	private Set<Character> alphabet = new HashSet<>();
	private Set<State> states = new HashSet<>();
	private Set<Transition> transitions = new HashSet<>();
	private Set<State> init = new HashSet<>();
	private Set<State> finals = new HashSet<>();
	
	private String name;
	
	public Automaton(String name) {
		this.name = name;
	}

	public Set<Character> getAlphabet() {
		return alphabet;
	}

	public void setAlphabet(Set<Character> alphabet) {
		this.alphabet = alphabet;
	}

	public Set<State> getStates() {
		return states;
	}

	public void setStates(Set<State> states) {
		this.states = states;
	}

	public Set<Transition> getTransitions() {
		return transitions;
	}

	public void setTransitions(Set<Transition> transitions) {
		this.transitions = transitions;
	}

	public Set<State> getInit() {
		return init;
	}

	public void setInit(Set<State> init) {
		this.init = init;
	}

	public Set<State> getFinals() {
		return finals;
	}

	public void setFinals(Set<State> finals) {
		this.finals = finals;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder(); 
		str.append("Automate " + name + " :\n") ;
		for(State s : states) {
			
		}
		
		for(Transition t : transitions) {
			str.append(t.getIn().toString());
			str.append(t.toString());
			str.append(t.getOut().toString());
		}
		return str.toString();
	}
}
