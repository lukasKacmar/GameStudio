package sk.tsystems.gamestudio.game.mines.core;

public class Clue extends Tile{
	private final int value;

	//constructor
	public Clue(int value) {
		this.value = value;
	}

	//getter
	public int getValue() {
		return value;
	}
	

}
