package daartme1;

public class Transition {
	private String name;
	private State in;
	private State out;
	
	public Transition(String name, State in, State out) {
		this.name = name;
		this.in = in;
		this.out = out;
	}
	
	public String getName() {
		return name;
	}
	
	public State getIn() {
		return in;
	}
	
	public State getOut() {
		return out;
	}
	
	@Override
	public String toString() {
		return "--"+getName()+"-->";
	}
}
