package sk.tsystems.gamestudio.game.mines.consoleUI;

import java.util.Date;
import java.util.Scanner;

import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.game.mines.core.Clue;
import sk.tsystems.gamestudio.game.mines.core.Field;
import sk.tsystems.gamestudio.game.mines.core.GameState;
import sk.tsystems.gamestudio.game.mines.core.Mine;
import sk.tsystems.gamestudio.game.mines.core.Tile;

import sk.tsystems.gamestudio.service.ScoreService.ScoreService;
import sk.tsystems.gamestudio.service.ScoreService.ScoreServiceFile;
import sk.tsystems.gamestudio.service.ScoreService.ScoreServiceJDBC;

public class ConsoleUI {

	private Field field;
	private ScoreService scoreService = new ScoreServiceJDBC();
	private int score;
	private long time;
	private long initialTime;

	public ConsoleUI(Field field) {
		this.field = field;
	}

	public void play() {

		printScores();
		initialTime = System.currentTimeMillis();
		do {
			printField(field);
			processInput();
		} while (field.getState() == GameState.PLAYING);
		printField(field);

		if (field.getState() == GameState.SOLVED) {
			System.out.println("Game Solved!");
			time = (System.currentTimeMillis() - initialTime) / 1000;
			score = 500 - (int) time;
			System.out.println("You solved the game in " + (time) + " sec. Score = " + score);
			scoreService.addScore(new Score("Mines",System.getProperty("user.name"), score, new Date()));
		} else if (field.getState() == GameState.FAILED)
			System.out.println("Game FAILED!");

	}

	private void processInput() {
		System.out.println("Enter input:");
		Scanner scanner = new Scanner(System.in);
		String line = scanner.nextLine().toUpperCase().trim();

		if ("X".equals(line))
			System.exit(0);
		if (line.matches("[OM][A-I][1-9]")) {
			int row = line.charAt(1) - 'A';
			int column = line.charAt(2) - '1';
			if (line.startsWith("O"))
				field.openTile(row, column);
			else
				field.markTile(row, column);
		} else
			System.out.println("Invalid input");

	}

	private static void printField(Field field) {
		System.out.print(" ");
		for (int column = 0; column < field.getColumnCount(); column++) {
			System.out.print(" ");
			System.out.print(column + 1);
		}
		System.out.println();

		for (int row = 0; row < field.getRowCount(); row++) {

			System.out.print((char) (row + 'A'));

			for (int column = 0; column < field.getColumnCount(); column++) {

				Tile tile = field.getTile(row, column);
				System.out.print(" ");
				switch (tile.getState()) {
				case OPENED:
					if (tile instanceof Mine)
						System.out.print("X");

					else if (tile instanceof Clue)
						System.out.print(((Clue) tile).getValue());
					break;
				case CLOSED:
					System.out.print("-");
					break;
				case MARKED:
					System.out.print("M");
					break;
				}
			}
			System.out.println();
		}
	}

	private void printScores() {
		int index = 1;
		System.out.println("-----------------------------");
		System.out.println("No.  Player             Score");
		System.out.println("-----------------------------");
		for (Score score : scoreService.getBestScores("Mines")) {
			System.out.printf("%3d. %-16s %5d\n", index, score.getUsername(), score.getPoints());
			index++;
		}
		System.out.println("-----------------------------");
	}
}
