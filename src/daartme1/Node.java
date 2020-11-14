package daartme1;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


public class Node {
	private Map<String, Node> childs = new HashMap<>();
	private boolean isInit;
	private boolean isEnd;
	private String name;
	private int id;
	private static int cpt= 0;
	
	public Node(String name, boolean isInit, boolean isEnd) {
		this.name = name;
		this.isInit = isInit;
		this.isEnd = isEnd;
		this.id = cpt++;
	}

	public Map<String, Node> getChilds() {
		return childs;
	}

	public boolean isInit() {
		return isInit;
	}

	public boolean isEnd() {
		return isEnd;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for(Entry<String, Node> entry : childs.entrySet()) {
			str.append("(" + id +")" + "--" + entry.getKey() +"-->" + "(" + entry.getValue().id +")");
		}
		return str.toString();
	}
}
