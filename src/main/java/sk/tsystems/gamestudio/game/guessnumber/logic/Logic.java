package sk.tsystems.gamestudio.game.guessnumber.logic;

import java.util.Random;

public class Logic {
	
	public int generateNumber() {
		Random random = new Random();
		int number = random.nextInt(100);
		return number;
	}
		
	
}
