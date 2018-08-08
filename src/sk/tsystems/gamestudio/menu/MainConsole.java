package sk.tsystems.gamestudio.menu;

import java.util.Scanner;

import sk.tsystems.gamestudio.game.guessnumber.GuessnumberMain;
import sk.tsystems.gamestudio.game.puzzle.PuzzleMain;
import sk.tsystems.gamestudio.game.mines.MinesMain;

public class MainConsole {

	public static void main(String[] args) {
		
		System.out.println("Choose a game:");
		System.out.println("1. Guess The Number");
		System.out.println("2. n - puzzle");
		System.out.println("3. MineSweeper");
		Scanner scanner = new Scanner(System.in);
		int input = Integer.parseInt(scanner.nextLine());
		
		if (input == 1) {
			GuessnumberMain guessnumbermain = new GuessnumberMain();
			guessnumbermain.startGuessnumber();
		}

		if (input == 2) {
			PuzzleMain puzzlemain = new PuzzleMain();
			puzzlemain.startPuzzle();
			}

		if (input == 3) {
			MinesMain minesmain = new MinesMain();
			minesmain.startMines();
		}
	}

}
