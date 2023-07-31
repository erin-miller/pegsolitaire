package src;
import java.util.Scanner;

public class PegSolitaire 
{	
	/**
	 * @param args - any command line arguments may be ignored by this method.
	 */
	public static void main(String[] args)
	{
		Game game = new Game();
		boolean continuePlay = true;
		System.out.println(
			"WELCOME TO CS300 PEG SOLITAIRE!\n" +
			"===============================\n\n" +
			"Board Style Menu\n" +
			"  1) Cross\n" +
			"  2) Circle\n" +
			"  3) Triangle\n" +
			"  4) Simple T");
		Scanner in = new Scanner(System.in);
		int boardType = game.readValidInt(in, "Choose a board style: ", 
										1, 4);
		char[][] board = game.createBoard(boardType);
		game.displayBoard(board);
		while (continuePlay) {
				int[] move = game.readValidMove(in, board);
				board = game.performMove(board, move[1], move[0], move[2]);
				game.displayBoard(board);
				if (game.countPegsRemaining(board) == 1) {
					continuePlay = false;
					System.out.println("Congrats, you won!");
				} else if (game.countMovesAvailable(board) == 0) {
					continuePlay = false;
					System.out.println("It looks like there are no more " +
									"legal moves. Please try again.");
				}
			}
		System.out.println("\n==========================================" +
			"\nTHANK YOU FOR PLAYING CS300 PEG SOLITAIRE!");
		in.close();
	}
}
