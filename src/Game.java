package src;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Game {

	private final char peg = '@';
	private final char hole = '-';
	private final char blank = '#';

    /**
	 * @param in - user input from standard in is ready through this.
	 * @param prompt - message describing what the user is expected to enter.
	 * @param min - the smallest valid integer that the user may enter.
	 * @param max - the largest valid integer that the user may enter.
	 * @return - the valid integer between min and max entered by the user.
	 */
	public int readValidInt(Scanner in, String prompt, int min, int max)
	{
		int input;
		System.out.print(prompt);
		input = isInteger(in);
		while ((input < min || input > max)) {
			System.out.printf("Please enter your choice as an integer between " +
							"%d and %d: ", min, max);
			input = isInteger(in);
		}
		return input;
	}

	/**
	 * @param boardType - 1-4 indicating one of the following initial patterns:
	 * @return - the fully initialized two dimensional array.
	 */
	public char[][] createBoard(int boardType)
	{
		char[][] board;
		switch(boardType) {
			case 1: // CROSS
				board = createCross();
				break;

			case 2: // CIRCLE
				board = createCircle();
				break;

			case 3: // TRIANGLE
				board = createTriangle();
				break;

			case 4: // SIMPLE T
				board = createSimpleT();
				break;

			default:
				board = createBlankBoard(1,1);
		}
		return board;
	}

	/**
	 * @param board - the current state of the board being drawn.
	 */
	public void displayBoard(char[][] board) {
		System.out.print("\n   ");
		for (int i=0; i < board[0].length; i++) {
			System.out.print(i+1);
		}
		System.out.println();
		for (int row=0; row < board.length; row++) {
			System.out.print(row+1 + "  ");
			for (int col=0; col < board[row].length; col++) {
				System.out.print(board[row][col]);
				}
			System.out.println();
			}
	}

	
	/**
	 * @param in - user input from standard in is ready through this.
	 * @param board - the state of the board that moves must be legal on.
	 * @return - the user's choice of column, row, and direction representing
	 *   a valid move and store in that order with an array.
	 */
	public int[] readValidMove(Scanner in, char[][] board)
	{
		int row = -1;
		int col = -1;
		int direction = -1;
		String directionType;
		boolean valid = false;
		while (!valid) {
			col = readValidInt(in, "Choose the COLUMN of a peg you'd " +
							"like to move: ", 1, board[0].length);
			row = readValidInt(in, "Choose the ROW of a peg you'd " +
							"like to move: ", 1, board.length);
			direction = readValidInt(in, "Choose a DIRECTION to move that peg " +
							"1) UP, 2) DOWN, 3) LEFT, or 4) RIGHT: ", 1, 4);
			if (isValidMove(board, row, col, direction)) {
				valid = true;
			} else {
				directionType = findDirectionType(direction);
				System.out.printf("Moving a peg from row %d and column %d " +
							"%s is not currently a legal move.%n%n",
							row, col, directionType);
			}
		}
		return new int[] {col, row, direction};
	}
	
	/**
	 * @param board - the state of the board that moves must be legal on.
	 * @param row - the vertical position of the peg proposed to be moved.
	 * @param column - the horizontal position of the peg proposed to be moved.
	 * @param direction - the direction proposed to move/jump that peg in.
	 * @return - true when the proposed move is legal, otherwise false.
	 */

	public boolean isValidMove(char[][] board, int row, int column, int direction)
	{
		row -= 1;
		column -= 1;
		try {
			if (!(board[row][column] == peg)) {
				return false;
			}

			switch(direction) {
				case 1: 
					if ((!(board[row-1][column] == peg)) || 
						(!(board[row-2][column] == hole))) {
						return false;
					}
					break;
				case 2: 
					if ((!(board[row+1][column] == peg)) || 
						(!(board[row+2][column] == hole))) {
						return false;
					}
					break;
				case 3: 
					if ((!(board[row][column-1] == peg)) || 
						(!(board[row][column-2] == hole))) {
						return false;
					}
					break;
				default: 
					if ((!(board[row][column+1] == peg)) || 
						(!(board[row][column+2] == hole))) {
						return false;
					}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * @param board - the state of the board will be changed by this move.
	 * @param row - the vertical position that a peg will be moved from.
	 * @param column - the horizontal position that a peg will be moved from.
	 * @param direction - the direction of the neighbor to jump this peg over.
	 * @return - the updated board state after the specified move is taken.
	 */
	public char[][] performMove(char[][] board, int row, int column, int direction)
	{
		row -= 1;
		column -=1 ;
		board[row][column] = hole;
		switch(direction) {
			case 1:
				board[row-1][column] = hole;
				board[row-2][column] = peg;
				break;
			case 2:
				board[row+1][column] = hole;
				board[row+2][column] = peg;
				break;
			case 3:
				board[row][column-1] = hole;
				board[row][column-2] = peg;
				break;
			default:
				board[row][column+1] = hole;
				board[row][column+2] = peg;
		}
		return board;
	}
	
	/**
	 * @param board - the board that pegs are counted from.
	 * @return - the number of pegs found in that board.
	 */
	public int countPegsRemaining(char[][] board)
	{
		int pegs = 0;
		for (int row=0; row < board.length; row++) {
			for (int col=0; col < board[row].length; col++) {
				if (board[row][col] == peg) {
					pegs++;
				}
			}
		}
		return pegs;
	}
	
	/**
	 * @param board - the board that possible moves are counted from.
	 * @return - the number of legal moves found in that board.
	 */
	public int countMovesAvailable(char[][] board)
	{
		int moves = 0;
		for (int row=0; row < board.length; row++) {
			for (int col=0; col < board[row].length; col++) {
				for (int direction=1; direction <= 4; direction++){
					if (isValidMove(board, row+1, col+1, direction)) {
						moves++;
						}
					}
				}
			}
		return moves;
	}	

	private int isInteger(Scanner in) {
		int input;
		if (in.hasNextInt()) {
			input = in.nextInt();
		} else {
			input = -1;
		}
		in.nextLine();
		return input;
	}

private char[][] createSimpleT() {
		char[][] board;
		board = new char[5][5];
		for (int row=0; row < board.length; row++) {
			for (int col=0; col < board[row].length; col++) {
				board[row][col] = hole;
				if (row > 0 && row < 4) {
					board[row][2] = peg;
				}
				if (row == 1) {
					board[row][1] = peg;
					board[row][3] = peg;
				}
			}
		}
		return board;
	}

	private char[][] createTriangle() {
		char[][] board;
		int posAdj;
		int negAdj;
		int slot;
		board = createBlankBoard(9,4);
		int mid = 4;
		posAdj = mid+1;
		negAdj = mid-1;
		slot = negAdj;
		for (int row=0; row < board.length; row++) {
			while (slot < posAdj) {
				board[row][slot] = peg;
				slot++;
			}
			board[row][posAdj] = hole;
			board[row][negAdj] = hole;
			posAdj = mid+row+2;
			negAdj = mid-row-2;
			slot = negAdj;
			}
		board[2][4] = hole;
		return board;
	}

	private char[][] createCircle() {
		char[][] board;
		int posAdj;
		int negAdj;
		int slot;
		board = createBlankBoard(6, 6);
		posAdj = 4;
		negAdj = 2;
		slot = negAdj;
		boolean flip = false;
		for (int row=0; row < board.length; row++) {
			if (negAdj == -1) {
				posAdj = 6;
				negAdj = 0;
				slot = negAdj;
				flip = true;
			}

			while (slot < posAdj) {
				board[row][slot] = peg;
				slot++;
			}

			if (!((row == 2) || (row == 3))) {
				board[row][posAdj] = hole;
				board[row][negAdj-1] = hole;
			}
			
			if (flip) {
				posAdj--;
				negAdj++;
			} else {
				posAdj++;
				negAdj--;
			}
			slot = negAdj;
		}
		return board;
	}

	private char[][] createCross() {
		char[][] board;
		board = createBlankBoard(9, 7);
		List<Integer> corners = Arrays.asList(0,1,5,6);
		for (int row=0; row < board.length; row++) {
			for (int col=0; col < board[row].length; col++) {
				if (corners.contains(row)) {
					for (int i=3; i < 6; i++) {
						board[row][i] = peg;
					}
				} else {
					board[row][col] = peg;
				}
			}
		}
		board[3][4] = hole;
		return board;
	}

	private String findDirectionType(int direction) {
		switch(direction) {
			case 1:
				return "UP";
			case 2:
				return "DOWN";
			case 3:
				return "LEFT";
			default:
				return "RIGHT";
		}
	}

	private char[][] createBlankBoard(int width, int height) {
		char[][] board = new char[height][width];
		for (int row=0; row < board.length; row++) {
			for (int col=0; col < board[row].length; col++) {
				board[row][col] = blank;
				}
			}
		return board;
	}		
}