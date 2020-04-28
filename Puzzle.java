package eightPuzz;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/* The Puzzle class creates the puzzle and solves it. It takes in the initial and goal states, solves
 * the puzzle based on the search strategy called, and prints the solution path and relevant information
 *  to the screen 
 */
public class Puzzle {

	Board initial;
	Board goal;
	
	// Constructor: takes in the initial and goal state (as int[])
	public Puzzle(int[] initial, int[] goal) {
		this.initial = new Board(initial);
		this.goal = new Board(goal);

	}

	// Finds solution path from initial state to goal state using Breadth-First Search
	public void solveBFS() {
		
		// variables to store costs and visited states
		Set<String> visited = new HashSet<String>();
		int steps = 0;
		int max = 0;

		// creates the queue that dictates the order nodes will be expanded
		Queue<PuzNode> frontier = new LinkedList<PuzNode>();
		PuzNode root = new PuzNode(initial);
		PuzNode current= root;
		
		//so long as the current state isn't the goal, search the queue
		while (!isGoal(current.getState())) { 					
			visited.add(current.getStateAsString());
			ArrayList<PuzNode> possible = getChildren(current); 		// return list of possible children
			
			//if the possible state hasn't been visited, add it as a child
			for (PuzNode i : possible) {
				if (!visited.contains(i.getStateAsString())) {  
					current.addChild(i);
					frontier.add(i);
				}
			}
			
			//check if the queue size is the largest so far, and remove the next Node to be visited
			if (frontier.size() > max) max = frontier.size();
			current = frontier.poll();
			steps += 1;
		}
		
		//once solution is found, print the path and relevant stats
		printSolution("BFS", current, steps, max);

	}

	// Finds solution path from initial state to goal state using Depth-First Search
	public void solveDFS() {
		
		// variables to store costs and visited states
		Set<String> visited = new HashSet<String>();
		int steps = 0;
		int max = 0; 
		
		// creates the stack that dictates the order nodes will be expanded, places initial state on stack
		Stack<PuzNode> s = new Stack<PuzNode>(); 
		PuzNode root = new PuzNode(initial); 
		PuzNode current = root; 

		//so long as the current state isn't the goal, search the queue
		while (!isGoal(current.getState())) { 
			
			visited.add(current.getStateAsString()); 
			ArrayList<PuzNode> possible = getChildren(current); 			// return list of possible children
			
			//if the possible state hasn't been visited, add it as a child
			for (PuzNode i : possible) {
				if (!visited.contains(i.getStateAsString())) { 
					//visited.add(i.getStateAsString());
					current.addChild(i);
					s.push(i); 
				}
			}
			//check if the queue size is the largest so far, and remove the next Node to be visited
			if (s.size() > max) max = s.size(); 
			current = s.pop();	
			steps += 1;	                        
		}
		
		//once solution is found, print the path and relevant stats
		printSolution("DFS", current, steps, max);

	}

	/* Finds solution path from initial state to goal state using Uniform-Cost Search
	 *Nodes visitation order is based on node pathcost
	 */
	public void solveUC() {

		// variables to store costs and visited states
		Set<String> visited = new HashSet<String>(); 
		int steps = 0;
		int max = 0;

		// Priority queue initialized using PathCostComparator, places initial state on stack
		PriorityQueue<PuzNode> frontier = new PriorityQueue<PuzNode>(20, new PathCostComparator()); 																						
		PuzNode root = new PuzNode(initial);
		PuzNode current = root;

		//so long as the current state isn't the goal, search the queue
		while (!isGoal(current.getState())) { 
			
			visited.add(current.getStateAsString());
			ArrayList<PuzNode> possible = getChildren(current); 			// return list of possible children 
			
			//if the possible state hasn't been visited, add it as a child
			for (PuzNode i : possible) {
				if (!visited.contains(i.getStateAsString())) { 
					current.addChild(i);
					frontier.add(i);
				}
			}
			//check if the queue size is the largest so far, and remove the next Node to be visited
			if (frontier.size() > max) max = frontier.size();
			current = frontier.remove(); 
			steps += 1;
		}

		//once solution is found, print the path and relevant stats
		printSolution("Uniform-Cost", current, steps, max);
	}

	/* Finds solution path from initial state to goal state using Greedy Best-First Search
	 * Nodes visitation order is based on node's heuristic#1 cost (misplaced tiles)
	 */
	public void solveGBF() {
		
		// variables to store costs and visited states
		Set<String> visited = new HashSet<String>(); // empty set for explored
		int totalCost = 0;
		int maxSize = 0;

		// Priority queue initialized using HuerCostComparator, places initial state on stack
		PriorityQueue<PuzNode> frontier = new PriorityQueue<PuzNode>(20, new HuerCostComparator()); 																						
		PuzNode root = new PuzNode(initial);
		PuzNode current = root;

		//so long as the current state isn't the goal, search the queue
		while (!isGoal(current.getState())) { 
			visited.add(current.getStateAsString());
			ArrayList<PuzNode> possible = getChildren(current); 
			
			//if the possible state hasn't been visited, add it as a child
			for (PuzNode i : possible) {
				if (!visited.contains(i.getStateAsString())) { 
					current.addChild(i);
					i.setHCost(huer1(i));	// this will update its hCost
					frontier.add(i);
				}
			}
			//check if the queue size is the largest so far, and remove the next Node to be visited
			if (frontier.size() > maxSize) maxSize = frontier.size();
			current = frontier.remove();
			totalCost += 1;
		}
		//once solution is found, print the path and relevant stats
		printSolution("GBF", current, totalCost, maxSize);
	}

	/* Finds solution path from initial state to goal state using A*1
	 *  Nodes visitation order is based on node's heuristic#1 cost (misplaced tiles) + pathcost
	 */
	public void solveAStar1() {
		
		// variables to store costs and visited states
		Set<String> visited = new HashSet<String>(); // empty set for explored
		int steps = 0;
		int max = 0;

		// Priority queue initialized using PathCostComparator, places initial state on stack
		PriorityQueue<PuzNode> frontier = new PriorityQueue<PuzNode>(20, new FCostComparator());
		PuzNode root = new PuzNode(initial);
		PuzNode current = root;

		//so long as the current state isn't the goal, search the queue
		while (!isGoal(current.getState())) { 
			visited.add(current.getStateAsString());
			ArrayList<PuzNode> possible = getChildren(current);     // return list of possible children
			
			//if the possible state hasn't been visited, add it as a child
			for (PuzNode i : possible) {
				if (!visited.contains(i.getStateAsString())) { 
					current.addChild(i);
					i.setHCost(huer1(i));									// this will update its hCost
					frontier.add(i);
				}
			}
			
			//check if the queue size is the largest so far, and remove the next Node to be visited
			if (frontier.size() > max) max = frontier.size();
			current = frontier.remove(); // remove last element, element with smallest pathCost
			steps += 1;
		}
		//once solution is found, print the path and relevant stats
		printSolution("A*1", current, steps, max);
	}

	/* Finds solution path from initial state to goal state using A*1
	 *  Nodes visitation order is based on node's heuristic#1 cost (manhattan distance) + pathcost
	 */
	public void solveAStar2() {
		
		// variables to store costs and visited states
		Set<String> visited = new HashSet<String>(); // empty set for explored
		int totalCost = 0;
		int maxSize = 0;

		// Priority queue initialized using PathCostComparator, places initial state on stack
		PriorityQueue<PuzNode> frontier = new PriorityQueue<PuzNode>(20, new FCostComparator()); 
		PuzNode root = new PuzNode(initial);
		PuzNode current = root;

		//so long as the current state isn't the goal, search the queue
		while (!isGoal(current.getState())) { 
			visited.add(current.getState().toString());
			ArrayList<PuzNode> possible = getChildren(current); 
			
			//if the possible state hasn't been visited, add it as a child
			for (PuzNode i : possible) {
				if (!visited.contains(i.getStateAsString())) { 
					current.addChild(i);
					i.setHCost(huer2(i));	                // this will update its hCost
					frontier.add(i);
				}
			}
			
			//check if the queue size is the largest so far, and remove the next Node to be visited
			if (frontier.size() > maxSize) maxSize = frontier.size();
			current = frontier.remove(); 
			totalCost += 1;
		}
		//once solution is found, print the path and relevant stats
		printSolution("A*2", current, totalCost, maxSize);
	}


	//HELPER FUNCTIONS: 
	
	// Given parent node, returns an ArrayList of possible child PuzNodes
	public ArrayList<PuzNode> getChildren(PuzNode n) {

		ArrayList<PuzNode> result;

		// If the node doesn't already have a list of children
		if (n.getChildren().isEmpty()) {
			n.setExpanded(true);
			result = new ArrayList<PuzNode>();

			// find the empty tile
			int empty = n.getState().findEmpty() + 1;

			// using position of empty tile, use possible actions to find possible children
			if (empty == 1) {
				result.add(new PuzNode(n, "right")); 
				result.add(new PuzNode(n, "down")); 

				return result;
			} else if (empty == 2) {
				result.add(new PuzNode(n, "left")); 
				result.add(new PuzNode(n, "right"));
				result.add(new PuzNode(n, "down")); 

				return result;
			} else if (empty == 3) {
				result.add(new PuzNode(n, "left")); 
				result.add(new PuzNode(n, "down")); 

				return result;

			} else if (empty == 4) {
				result.add(new PuzNode(n, "right"));
				result.add(new PuzNode(n, "up")); 
				result.add(new PuzNode(n, "down"));

				return result;

			}

			else if (empty == 5) {
				result.add(new PuzNode(n, "left")); 
				result.add(new PuzNode(n, "right"));
				result.add(new PuzNode(n, "up")); 
				result.add(new PuzNode(n, "down")); 

				return result;

			} else if (empty == 6) {
				result.add(new PuzNode(n, "left")); 
				result.add(new PuzNode(n, "up")); 
				result.add(new PuzNode(n, "down"));

				return result;

			}

			else if (empty == 7) {
				result.add(new PuzNode(n, "right")); 
				result.add(new PuzNode(n, "up")); 

				return result;

			} else if (empty == 8) {
				result.add(new PuzNode(n, "left")); 
				result.add(new PuzNode(n, "right")); 
				result.add(new PuzNode(n, "up")); 

				return result;

			} else if (empty == 9) {
				result.add(new PuzNode(n, "left")); 
				result.add(new PuzNode(n, "up")); 

				return result;

			}

		}
		return n.getChildren();

	}
	
	// Returns true if current Board is equal to goal Board
	private boolean isGoal(Board current) {
		return (current.equals(goal));
	}

	// Hueristic #2: Finds the manhattan distance between current Board and goal Board
	public int huer2(PuzNode current) {
		
	       int total = 0;
	        for (int i = 0; i < 9; i += 1)
	            for (int j = 0; j < 9; j += 1) {
	                if (current.getState().get(i) == goal.get(j)) {
	                    total += ((Math.abs(findX(i)-findX(j))) + Math.abs(findY(i) - findY(j)));
	                    break;
	                }
	            }
	       current.setHCost(total);
	       return total;
	    }
	
	// huer2() Helper: Translates ArrayList index to Y coordinate
	private int findY(int i) {

		if(i<4) return 0;
		else if(i<6) return 1;
		else return 2;

	}
	
	// huer2() Helper: Translates ArrayList index to X coordinate
	private int findX(int i) {

		if(i%3 ==0) return 0;
		else if(i%3 == 1) return 1;
		else return 2;
		

	}

	// Hueristic #1: Finds the misplaced tile count between the current Board and goal Board
	public int huer1(PuzNode current) {
		int total = 0;
		for (int i = 0; i < current.getState().size(); i++) {
			if (current.getState().get(i)!=(goal.get(i))) {
				total++;
			}
		}
		current.setHCost(total);
		return total;

	}

	//Prints the solution to screen with relevant information 
	public void printSolution(String alg, PuzNode current, int max, int time) {

		// Use a stack to put in all nodes from the current one all the way back to the
		// initial one
		Stack<PuzNode> answer = new Stack<PuzNode>();

		PuzNode temp = current;
		while (temp.getParent() != null) {
			answer.push(temp);
			temp = temp.getParent();
		}

		System.out
				.println("******************************************************************************************");
		System.out.println("Solution found using " + alg + " algorithm\n");
		System.out.println("Initial state: " + this.initial.toString() + "\nGoal state: " + this.goal.toString());
		System.out.println(
				"____________________________________________________________________________________________________");
		System.out.println("\nMoves taken:");

		System.out.println(initial.toString() + "\nInitial State");
		PuzNode i = null;
		int length = 0;   //length of the solution path
		while (!answer.empty()) {
			i = answer.pop();
			length++;
			System.out.println(i.getState().toString());
			System.out.println("Moved " + i.getAction().toUpperCase() + ", cost " + i.getIndivCost()
					+ ", increasing path-cost to " + i.getPathCost());
			if (i.getHCost() != 0)
				System.out.println(" Hueristic Cost is " + i.getHCost());
				System.out.println(" F cost is " + i.getFCost());
		}

		System.out.println(
				"____________________________________________________________________________________________________");
		System.out.println("Length of the Solution Path: " + length);
		System.out.println("Total Path Cost: " + current.getPathCost());
		System.out.println("Time: " + time + " nodes taken off queue");
		System.out.println("Max queue size: " + max + " nodes");

	}

}

//Comparators created for Priority Queue use in UC, GBF, A*1, A*2:


//Comparator used by Uniform-Cost Search: Compares the PathCost of two nodes
class PathCostComparator implements Comparator<PuzNode> {

	public int compare(PuzNode one, PuzNode two) {
		if (one.getPathCost() > two.getPathCost())
			return 1;
		else if (one.getPathCost() < two.getPathCost())
			return -1;
		else
			return 0;

	}

}
//Comparator used by Greedy Best-First Search: Compares the Heuristic Cost of two nodes
class HuerCostComparator implements Comparator<PuzNode> {

	public int compare(PuzNode one, PuzNode two) {
		if (one.getHCost() > two.getHCost())
			return 1;
		else if (one.getHCost() < two.getHCost())
			return -1;
		else
			return 0;

	}
}

//Comparator used by A*1 and A*2: Compares the F-Cost of two nodes (Heuristic Cost + Path Cost)
class FCostComparator implements Comparator<PuzNode>{
	
	public int compare(PuzNode one, PuzNode two) {
		if (one.getFCost() > two.getFCost()) return 1;
		else if (one.getFCost() < two.getFCost()) return -1;
		else return 0;
		
	}

}



