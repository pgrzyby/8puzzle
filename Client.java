package eightPuzz;

/* This class acts as the client, creating a Puzzle object with the initial and goal states, and 
 * calling the search strategy methods to solve them. The solution path is then printed to the console.
 */
public class Client {

	public static void main(String[] args) {
		
		//Save all initial states		
		int [] easyState = {1,3,4,8,6,2,7,0,5};
		int [] mediumState = {2,8,1,0,4,3,7,6,5};
		int [] hardState = {5,6,7,4,0,8,3,2,1};
	    int [] goalState = {1,2,3,8,0,4,7,6,5};
		

      //Solve EASY state with all search algorithms and print solutions to screen
	    Puzzle easy = new Puzzle(easyState,goalState);
	    System.out.println("Running Problem 1: Easy State");
	    System.out.println("*******************************************************************");
	    easy.solveBFS();
	    easy.solveDFS();
	    easy.solveUC();
	    easy.solveGBF();
	    easy.solveAStar1();
	    easy.solveAStar2();
	    System.out.println("*******************************************************************");
	    
	    
	    // Solve MEDIUM state with all search algorithms and print solutions to screen
	    Puzzle medium = new Puzzle(mediumState, goalState);
	    System.out.println("Running Problem 2: Medium State");
	    System.out.println("*******************************************************************");
	    medium.solveBFS();
	    medium.solveDFS();
	    medium.solveUC();
	    medium.solveGBF();
	    medium.solveAStar1();
	    medium.solveAStar2();
	    System.out.println("*******************************************************************");
	     
	    //Run all algs on 3
	    Puzzle hard = new Puzzle(hardState, goalState);
	    System.out.println("Running Problem 3: Hard State");
	    System.out.println("*******************************************************************");
	    hard.solveBFS();
        hard.solveDFS();
	    hard.solveUC();
	    hard.solveGBF();
	    hard.solveAStar1();
	    hard.solveAStar2();
	    System.out.println("*******************************************************************");
	    
	    
	    //For the report
//	    System.out.println("BFS:");
//	    System.out.println("*******************************************************************");
//	    easy.solveBFS();
//	    medium.solveBFS();
//	    hard.solveBFS();
//	    
//	    
//	    System.out.println("A*2:");
//	    System.out.println("*******************************************************************");
//	    easy.solveAStar2();
//	    medium.solveAStar2();
//	    hard.solveAStar2();
	    

	}

}
