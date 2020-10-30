package daartme1;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

class State{
	private static int count = 0;
	private int id;
	private Set<Transition> in;
	private Set<Transition> out;
	private boolean init;
	private boolean fin;
	
	public State(Set<Transition> in, Set<Transition> out, boolean init, boolean fin) {
		id = count++;
		this.in = in;
		this.out = out;
		this.init = init;
		this.fin = fin;
	}
	public State( boolean init, boolean fin) {
		id = count++;
		this.in = new HashSet<>();
		this.out = new HashSet<>();
		this.init = init;
		this.fin = fin;
	}
	
	public int getId() {
		return id;
	}
	
	public Set<Transition> getIn() {
		return in;
	}
	
	public Set<Transition> getOut() {
		return out;
	}
	
	public boolean isInit() {
		return init;
	}
	
	public boolean isFin() {
		return fin;
	}
	
}