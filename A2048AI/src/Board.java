import java.util.Random;

public class Board {
 
	public int[][] board; // holds state of game
	private Random rnd = new Random(); // setup random # generator
	private int size;
	public int score;
	
	
	public Board() {
		
		// instantiate the board
		board = new int[4][4];
		populateOne();
		populateOne();
		size = 2;
		score = 0;
	}

	public String toString() {
		
		
		String result = "";
		
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[0].length; j++) {
				result += String.format("%04d ", board[i][j]);
			}
			result += "\n";
		}
		
		return result;
	}



	public void populateOne() {
	
		if(size < board.length * board[0].length) {
			int row = rnd.nextInt(board.length);
			int col = rnd.nextInt(board[0].length);
			
			while(board[row][col] != 0) {
				row = rnd.nextInt(board.length);
				col = rnd.nextInt(board[0].length);
			}
			
			if(rnd.nextInt(10) == 8) {
				board[row][col] = 4;
			} else {
				board[row][col] = 2;
			}
		}
		
			

	}

	public void slideRight(int[] row) {
		
		int index = row.length-1;
		
		for(int i = row.length-1; i >= 0; i--) {
			if(row[i] != 0) {
				if(index != i) {
					row[index] = row[i];
					row[i] = 0;
				}
				index--;
			}
		}
		
	
	}

	public void slideRight() {

		// go through 2D array, move all digits as far right as possible
		//setup a loop to grab ONE row at a time from 2d array board
		for(int i = 0; i < board.length; i++) {
			slideRight(board[i]);
		}
	
		
	}

	public void slideLeft(int[] arr) {
		
		int index = 0;
		
		for(int i = 0; i < arr.length; i++) {
			if(arr[i] != 0) {
				if(index != i) {
					arr[index] = arr[i];
					arr[i] = 0;
				}
				
				index++;
			}
		}
		
		
		
	}


	public void slideLeft() {
		
		// grabbing a row from a 2D array
		// if it's called arr then arr[i] grabs ONE row!
	 
		//visit every single row in the 2D array
		//call the slideLeft method that takes in one argument
		for(int i = 0; i < board.length; i++) {
			slideLeft(board[i]);
		}
		
	}

	/**
	 * Given a 2D array and a column number, return a 1D array representing the
	 * elements in the given column number.
	 */
	public int[] getCol(int[][] data, int c) {
		
		//you can also add print out statements here
		int[] result = new int[data.length];
		
		for(int i = 0; i < data.length; i++) {
			result[i] = data[i][c];
		}
		return result;
		
	}

	/**
	 * Given an array of integers, slide all non-zero elements to the top.
	 * 
	 * zero elements are considered open spots.
	 */

	public void slideUp(int[] arr) {
		/* calls a helper method */
		// do not rewrite logic you already have!
		slideLeft(arr);
		
	}

	/*
	 * 
	 * Slide all elements in the board towards the top.
	 * 
	 * You must use slideUp and getCol for full credit.
	 */
	public void slideUp() {
		
		//visit every column index
		//grab each column as an array using getCol -> keep track of it in a 1d array
		// variable/reference
		//have slideLeft perform manipulation on the array
		// copy over the 1D array representation of the column
		// back to the 2D board array
		
		for(int i = 0; i < board[0].length; i++) {
			int[] arr = getCol(board, i);
			slideUp(arr);
			for(int j = 0; j < arr.length; j++) {
				board[j][i] = arr[j];
			}
			
		}
		
		
	}

	public void slideDown(int[] arr) {

		slideRight(arr);
	}

	/*
	 * slide all the numbers down so that any
	 * empty space is at the top
	 * You must use slideDown and getCol for full credit.
	 */

	public void slideDown() {
		for(int i = 0; i < board[0].length; i++) {
			int[] arr = getCol(board, i);
			slideDown(arr);
			for(int j = 0; j < arr.length; j++) {
				board[j][i] = arr[j];
			}
		}
	}

	/*
	 * Given the 2D array, board, combineRight will take adjacent numbers that
	 * are the same and combine them (add them).
	 * After adding them together, one of the numbers is zeroed out. For
	 * example, if row 0 contained [0 0 4 4],
	 * a call to combineRight will produce [0 0 0 8]. If row 1 contained [2 2 2
	 * 2], a call to combineRight will
	 * produce [0 4 0 4].
	 * 
	 * Notice that the left element is zeroed out.
	 */

	public void combineRight() {
		for(int j = 0; j < board.length; j++) {
			for(int i = board[0].length-1; i > 0; i--){
				if(board[j][i] == board[j][i-1]) {
					board[j][i] += board[j][i-1];
					score+=board[j][i];
					board[j][i-1] = 0;
				}
			}
			
		}
		
	}

	/*
	 * same behavior as combineRight but the right element is zeroed out when
	 * two elements are combined
	 */

	public void combineLeft() {
		for(int j = 0; j < board.length; j++) {
			for(int i = 0; i < board[0].length-1; i++){
				if(board[j][i] == board[j][i+1]) {
					board[j][i] += board[j][i+1];
					score+=board[j][i];
					board[j][i+1] = 0;
				}
			}
		}
	}
	
	/*
	 * same behavior as combineRight but the bottom element is zeroed out when
	 * two elements are combined
	 */

	public void combineUp() {
		for(int i = 0; i < board[0].length; i++) {
			int[] currCol = getCol(board, i);
			for(int j = 0; j < currCol.length-1; j++) {
				if(currCol[j] == currCol[j+1]) {
					currCol[j] += currCol[j+1];
					currCol[j+1] = 0;
				}
			}
			for(int j = 0; j < currCol.length; j++) {
				board[j][i] = currCol[j];
			}
		}
	}

	/*
	 * same behavior as combineRight but the top element is zeroed out when two
	 * elements are combined
	 */

	public void combineDown() {
		for(int i = 0; i < board[0].length; i++) {
			int[] currCol = getCol(board, i);
			for(int j = currCol.length-1; j > 0; j--) {
				if(currCol[j] == currCol[j-1]) {
					currCol[j] += currCol[j-1];
					currCol[j-1] = 0;
				}
			}
			for(int j = 0; j < currCol.length; j++) {
				board[j][i] = currCol[j];
			}
		}
	}

	
	
	/* reminder: these are the methods that will ultimately invoke
	 * a series of methods
	 * 
	 * the combine and slide methods should not worry about each other's methods
	 */
	public void left() {
		//1) numbers slide to the left
		//2) combine
		//3) slide
		slideLeft();
		combineLeft();
		slideLeft();
	}

	public void right() {
		slideRight();
		combineRight();
		slideRight();
	}

	public void up() {
		slideUp();
		combineUp();
		slideUp();
	}

	public void down() {
		slideDown();
		combineDown();
		slideDown();
	}
	
	

	public boolean gameOver() {
		return false;
	}

	public int[][] getBoard() {
		return board;
	}

	// populate with a given 2d array
	public void populate(int[][] arr) {
		for (int r = 0; r < arr.length; r++) {
			for (int c = 0; c < arr[r].length; c++) {
				board[r][c] = arr[r][c];
			}
		}
	}

}
