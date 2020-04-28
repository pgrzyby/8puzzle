package eightPuzz;

import java.util.ArrayList;

/* The PuzNode class represents the nodes in the search trees generated in each search strategy.
 * The PuzNode contains several fields that help with the search process. 
 */
public class PuzNode {

	private Board state;					 //state of the puzzle/arrangement of tiles
	private PuzNode parent;					 //the parent node 
	private String action;					 //action taken to get to this PuzNode's state
	private int iCost;   					 //the individual cost of getting to this state (based on the integer moved) 
	private int pCost;    	   				 //total pathCost to get from initial state to here
	private int hCost;	  					 //the heuristicCost to get to the goal from here
	private boolean expanded;				 //has the node been explored?
	private ArrayList<PuzNode> children;     //list of node's children
	
	//Constructor for root node (no parent)
	public PuzNode(Board state) {
		this.state = state;
		this.action = "";
		this.parent = null;
		this.pCost = 0;
		this.hCost = 0;
		this.expanded = false;
		this.children = new ArrayList<PuzNode>();
	}

	// Constructor for all other nodes
	public PuzNode(PuzNode parent, String action) {

		this.state = new Board(parent.getState());				//we set it to the parent's state originally
		this.action = action;							
		this.parent = parent;
		this.iCost = state.move(action);   						//simultaneously adjust the state through the action + find the cost of that action
		this.pCost = parent.pCost + iCost; 						//path cost is parent pathcost + individual cost 
		this.hCost = 0;
		this.expanded = false;
		this.children = new ArrayList<PuzNode>();

		
	}

	// Accessor functions:
	
	public Board getState() {
		return state;
	}
	
	public String getStateAsString() {
		return state.toString();
	}

	public PuzNode getParent() {
		return this.parent;
	}

	public String getAction() {
		return this.action;
	}
	
	public int getIndivCost() {
		return iCost;
	}

	public int getPathCost() {
		return pCost;
	}

	public int getHCost() {
		return hCost;
	}

	public void setHCost(int hCost) {
		this.hCost = hCost;
	}

	public int getFCost() {
		return hCost + pCost;
	}

	public boolean getExpanded() {
		return this.expanded;
	}

	public void setExpanded(boolean variable) {
		this.expanded = variable;
	}

	public ArrayList<PuzNode> getChildren() {
		return children;
	}

	public void addChild(PuzNode n) {
		this.children.add(n);
	}

}
