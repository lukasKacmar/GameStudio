package sk.tsystems.gamestudio.game.guessnumber.ui;

import java.util.Date;
import java.util.Scanner;

import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.game.guessnumber.logic.Logic;
import sk.tsystems.gamestudio.service.ScoreService.ScoreService;
//import sk.tsystems.gamestudio.service.ScoreService.ScoreServiceFile;
import sk.tsystems.gamestudio.service.ScoreService.ScoreServiceJDBC;

public class ConsoleUI {

	private ScoreService scoreService = new ScoreServiceJDBC();

	private Logic logic = new Logic();
	private int guessedNumber;
	private int moveCount = 0;
	private final int score = 15;

	public void play() {

		printScores();
		start();
		System.out.println("Enter number:");
		
		while (guessPart()==false) {
		moveCount++;	
		}
		
		System.out.println("You won!");
		System.out.println("Your score is " + (score - moveCount));
		scoreService.addScore(new Score("guessNumber",System.getProperty("user.name"), score - moveCount, new Date()));

	}
	
	private void start() {
		System.out.println("Guess the number from 1 to 100!");
		guessedNumber = logic.generateNumber();
	}

	private boolean guessPart() {
		Scanner scanner = new Scanner(System.in);
		int number = Integer.parseInt(scanner.nextLine());

		if (number == guessedNumber) {
			moveCount++;
			return true;
		}
		if (number < guessedNumber) { 
			System.out.println("Viac");
			return false;
		}else 
			System.out.println("Menej");
			return false;
		
	}
	
	private void printScores() {
		int index = 1;
		System.out.println("-----------------------------");
		System.out.println("No.  Player             Score");
		System.out.println("-----------------------------");
		for(Score score : scoreService.getBestScores("guessNumber")) {
			System.out.printf("%3d. %-16s %5d\n", index, score.getUsername(), score.getPoints());
			index++;
		}
		System.out.println("-----------------------------");
	}
}
