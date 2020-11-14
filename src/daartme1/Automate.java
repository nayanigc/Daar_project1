package daartme1;


import java.util.ArrayList;
import java.util.List;



public class Automate {
private List<Node> nodes = new ArrayList<>();
private int compteur = 0;
	public Automate(String string) {
		createAutomate(string);
	}
/** 
 * 
 * **/
	
	private void createAutomate(String string) {
		List<String> str = new ArrayList<>();
		boolean wasOr = false;
		boolean wasStar = false;
		boolean opened = false;
		boolean closed = false;
		boolean pointed = false;
		Node nodeBeforeOr = null;
		
		int position = 0;
		for(char c : string.toCharArray()) {
			str.add(String.valueOf(c));
		}
		
		for (int i = 0; i < str.size(); i++) {
			String current = str.get(i);
			boolean nextIsStar = false;
			boolean nextIsClose = false; 
			if(i + 1 < str.size()) {
				nextIsStar = str.get(i+1).equals("*");
				nextIsClose = str.get(i+1).equals(")");
			}
			
			switch (current) {
			
			case "(":
				opened = true;
				position = i;
				break;
				
			case ")":
				closed = true;
				break;
				
			case ".":
				pointed = true;
				break;
			
			case "[":
				break;
			
			case "]":
				break;
			
			case "-":
				break;
			
			case "_":
				break;
			
			case "+":
				break;
				
			case "/":
				break;
			
			case "|":
				if(opened && !closed) {
					nodeBeforeOr = nodes.get(nodes.size()-1);
				}
				wasOr = true;
				break;
			
			case "*": {
				if(!wasOr && wasStar) {
					if(closed) {
						break;
					}else {
						String lastString = str.get(i-1);
						Node lastNode = nodes.get(nodes.size()-2);
						lastNode.getChilds().put(lastString, lastNode);
						wasStar = true;
					}
					
					
				}else if(!wasOr && !closed) {
					if(!wasStar) {
						create_a_star_star (str,current,i,wasOr,opened, closed,wasStar);
					}else {
						String lastString = str.get(i-1);
						Node lastNode = nodes.get(nodes.size()-3);
						lastNode.getChilds().put(lastString, lastNode);
						wasStar = true;	
					}
					
					
				}else if(!wasOr && closed ) {
						if(pointed) {
							if (current.equals("*")) {
								Node currentNode = new Node(current, false, false);
								nodes.add(currentNode);
								currentNode.toString();
							}
							
						}else {
							creat_automate_ab_parenthese_star(str,i);
						}
						
				}else{
					  
					  create_automate_or_parenthese_star(str,current,wasOr,wasStar,i,closed);
					  
				}
				
				break;
			}
			default:
				if(str.size() == 1) {
					create_automate_empty(current);
				
				}else if(i == 0) {
					if(!nextIsStar) {
						create_automate_empty(current);
					}else {
						nodes.add(new Node("", true, false));
					}
					
				}else {
					if(!nextIsStar) {
						if(nodeBeforeOr != null) {
							if(!wasOr){
								if (closed) {
									Node currentNode = new Node(current, false, false);
									Node current2Node = new Node(current, false, false);
									Node parentNode = nodes.get(nodes.size()-2);
									Node bigparentNode =  nodes.get(nodes.size()-1);
									nodes.add(currentNode);
									nodes.add(current2Node);
									parentNode.getChilds().put(current, currentNode);
									bigparentNode.getChilds().put(current,current2Node);
									//wasOr = false;
									wasStar = false;
									opened = false;
								}else {
									Node currentNode = new Node(current, false, false);
									Node parentNode = nodes.get(nodes.size()-1);
									nodes.add(currentNode);
									parentNode.getChilds().put(current, currentNode);
									
								}
							}else {
								if (!closed) {
									if(pointed)	{
										Node currentNode = new Node(current, false, false);
										Node parentNode = nodes.get(position-1);
										nodes.add(currentNode);
										parentNode.getChilds().put(current, currentNode);
										//wasOr = false;
										wasStar = false;
										compteur++;
									}else {
										Node currentNode = new Node(current, false, false);
										Node parentNode = nodes.get(position);
										nodes.add(currentNode);
										parentNode.getChilds().put(current, currentNode);
										//wasOr = false;
										wasStar = false;
										compteur++;
									}
									
								}else {
									/**
									 * error du au fait de le faire branche sur voir plus de branche
									 * il faut que je lui dis si il y a un élément avant la parenthese ne prend pas
									 * en compte sinon oui
									 * **/ 
									if (closed && pointed) {
										if(wasOr) {
											while (compteur > 0) {	
												Node currentNode = new Node(current, false, false);
												Node parentNode = nodes.get(compteur);
												nodes.add(currentNode);
												parentNode.getChilds().put(current, currentNode);
												compteur--;
											}
											
											opened = false;
											closed =false;
											pointed = false;
											wasOr = false;
											wasStar = false;
										}
										
									}else {
										/**
										 * Faire la meme chose qu'au if enfont du nombre element sur lequel branche 
										 * **/
									Node currentNode = new Node(current, false, false);
									Node parentNode = nodes.get(nodes.size()-2);
									nodes.add(currentNode);
									parentNode.getChilds().put(current, currentNode);
									wasOr = false;
									wasStar = false;
									}
								}
						}
						
					}else {
						if(wasOr) {
							System.out.println("enter wasOR : "+current );
							Node currentNode = new Node(current, false, false);
							Node parentNode = nodes.get(nodes.size()-2);
							nodes.add(currentNode);
							parentNode.getChilds().put(current, currentNode);
							wasOr = false;
							wasStar = false;
						}else if (opened){
							if (nodes.isEmpty()) {
								create_automate_empty(current);
							}else {
								System.out.println(opened);
								create_next_node_automate(i, current,str,opened,closed,pointed);
							}
						}else {
								Node currentNode = new Node(current, false, false);
								Node parentNode = nodes.get(nodes.size()-1);
								nodes.add(currentNode);
								parentNode.getChilds().put(current, currentNode);
							
							
						}
						
		
					}
						
			}else {
				System.out.println("enter closed false : "+current);		
				//create_automate_empty( current);
					}
				}		
			}
		}
	}
	

	
	private void create_automate_empty(String current) {
		Node initNode = new Node("init", true, false);
		Node currentNode = new Node(current, false, false);
		nodes.add(initNode);
		nodes.add(currentNode);
		initNode.getChilds().put(current, currentNode);
		compteur++;
	}
	private int position_star(List<String>str) {
		for (int i = 0; i<str.size();i++) {
			if(str.get(i).equals("*")) {
				return i;
			}
		}
		return 0;
	}

	private void create_next_node_automate(int i, String current,List<String> str,boolean opened,boolean closed,boolean pointed) {
		int pos = position_star(str);
		String lastString = " ";
		if(pos>0) {
			lastString = str.get(pos);
		}
		int t = 0;
		if (lastString.equals("*") && !pointed) {
			Node currentNode = new Node(current, false, false);
			nodes.add(currentNode);
			currentNode.toString();
		
		}else {
			Node currentNode = new Node(current, false, false);
			Node parentNode = nodes.get(nodes.size()-1);
			nodes.add(currentNode);
			parentNode.getChilds().put(current, currentNode);
			pointed = false;
		}
		
	
	}
	
	/**
	 * Erreur dans le cas (a|b)* ---> 0 -> a --> 1 au lieu de 0--> a --> 0
	 * **/
	private void create_automate_or_parenthese_star(List<String> str,String current,boolean wasOr,boolean wasStar,int i,boolean closed) {
		if (nodes.size() == 0 ) {
			create_automate_empty(str.get(i));
		}else {
			if(wasOr) {
				if (!closed) {
					String lastString = str.get(i-1);
					Node lastNode = nodes.get(nodes.size()-2);
					lastNode.getChilds().put(lastString, lastNode);
					wasStar = true;
				}else {
						if ( nodes.size() > 2 ) {
							int nbre_node = nodes.size()-1;
							String lastString = " ";
							Node lastNode;
							while (nbre_node> 0) {
								int t = i-nbre_node;
								lastString = str.get(t);
								if(nbre_node > 1) {
									lastNode = nodes.get(i-nbre_node-t);
									lastNode.getChilds().put(lastString, lastNode);
								}else {
									if(closed) {
										closed = false;
										break;
									}else{
										lastNode = nodes.get(i-nbre_node-t);
										lastNode.getChilds().put(lastString, lastNode);
									}
								}
								nbre_node--;
							}
						}
					}
				}
			}
	}
	
	
	private void creat_automate_ab_parenthese_star(List<String> str ,int i ) {
		if ( nodes.size() > 2 ) {
			int nbre_node = nodes.size();
			String lastString;
			Node lastNode;
			while (nbre_node> 0) {
				lastString = str.get(i-nbre_node);
				int t = i-nbre_node;
				if(nbre_node> 1) {
					lastNode = nodes.get(i-nbre_node-t);
					lastNode.getChilds().put(lastString, lastNode);
				}else {
					lastNode = nodes.get(i-nbre_node-1);
					lastNode.getChilds().put(lastString, lastNode);
				}
			
				nbre_node--;
			}
		}
	}
	
	private void create_a_star_star (List<String> str,String current,int i,boolean wasOr,boolean opened, boolean closed,boolean wasStar) {
		if(opened && !closed) {
			if (nodes.size() == 0) { /*Le cas ou on est dans (a*)*/
				Node initNode = new Node("init", true, false);
				nodes.add(initNode);
			}
			String lastString = str.get(i-1);
			Node lastNode = nodes.get(nodes.size()-1);
			lastNode.getChilds().put(lastString, lastNode);
			wasStar = true;
			System.out.println(lastString + nodes.size());
		}else {
			String lastString = str.get(i-1);
			Node lastNode = nodes.get(nodes.size()-1);
			lastNode.getChilds().put(lastString, lastNode);
			wasStar = true;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for(Node node : nodes) {
			str.append("\n");
			str.append(node.toString());
		}
		return str.toString();
	}
}
