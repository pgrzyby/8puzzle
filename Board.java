package eightPuzz;

import java.util.ArrayList;

/* The Board class represents the state of the 8Puzzle, or where the tiles are.
 * The state is represented by an ArrayList of Integers, each Integer symbolizing
 * a tile on the 8Puzzle Board
 * 
 */

public class Board {

	public ArrayList<Integer> board;

	// Constructor: takes in board in form int[]
	public Board(int[] board) {
		ArrayList<Integer> b = new ArrayList<Integer>();
		for (int i = 0; i < board.length; i++) {
			b.add(board[i]);
		}
		this.board = b;
	}

	// Constructor: takes in board in form Board
	public Board(Board board) {
		ArrayList<Integer> b = new ArrayList<Integer>();
		for (int i = 0; i < board.board.size(); i++) {
			b.add(board.board.get(i));
		}
		this.board = b;
	}

	// Access the board
	public int get(int i) {
		return board.get(i);
	}

	// Returns size of the board
	public int size() {
		return board.size();
	}

	// Returns index of '0' in the array
	public int findEmpty() {
		for (int i = 0; i < board.size(); i++) {
			if (board.get(i) == 0) 
				return i;
		}
		return -1; 
	}

	/* Board alters itself by exchanging elements at specified indicies
	 * Returns the cost of the move
	 */
	public int move(String action) {

		// index of Blank spot
		int blank = findEmpty();

		if (action.equals("left")) {
			return exchange(blank, blank - 1);
		} else if (action.equals("right")) {
			return exchange(blank, blank + 1);
		} else if (action.equals("up")) {
			return exchange(blank, blank - 3);
		} else {
			assert (action.equals("down"));
			return exchange(blank, blank + 3);
		}
	}

	/*
	 * Exchanges two tiles: index1 is Blank spot, index 2 is other tile Calculates
	 * and return the pathcost
	 */
	private int exchange(int index1, int index2) {

		int one = board.get(index1);
		int two = board.get(index2);
		board.set(index2, one);
		board.set(index1, two);
		assert (board.get(index1) == two);
		assert (board.get(index2) == one);

		return two; // the value of the tile
	}

	/*
	 * Returns true if the current Board and other Board are equal. Equality in this
	 * context means having tiles in same place.
	 */
	public boolean equals(Board other) {

		for (int i = 0; i < board.size(); i++) {
			if (board.get(i) != other.board.get(i))
				return false;
		}
		return true;
	}

	// Formats and returns the current state into String
	public String toString() {
		String result = "";

		int j = 0;
		for (Integer i : board) {
			if (j % 3 == 0)
				result += ("\n" + i + " ");
			else
				result += (i + " ");
			j++;
		}

		return result;
	}

}
