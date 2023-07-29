package src;
import java.util.ArrayList;
import java.util.Scanner;

public class PegSolitaire 
{	
	/**
	 * This method is responsible for everything from displaying the opening 
	 * welcome message to printing out the final thank you.  It will clearly be
	 * helpful to call several of the following methods from here, and from the
	 * methods called from here.  See the Sample Runs below for a more complete
	 * idea of everything this method is responsible for.
	 * 
	 * @param args - any command line arguments may be ignored by this method.
	 */
	public static void main(String[] args)
	{
		Game game = new Game();
		System.out.println(
			"WELCOME TO CS300 PEG SOLITAIRE!\n" +
			"===============================\n\n" +
			"Board Style Menu\n" +
			"  1) Cross\n" +
			"  2) Circle\n" +
			"  3) Triangle\n" +
			"  4) Simple T\n");

		Scanner in = new Scanner(System.in);
		int boardType = game.readValidInt(in, "Choose a board style: ", 1, 4);
		in.close();
	}
}
