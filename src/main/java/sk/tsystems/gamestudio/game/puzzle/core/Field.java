package sk.tsystems.gamestudio.game.puzzle.core;

import java.util.Random;

public class Field {
	private final int rowCount;

	private final int columnCount;

	private final Tile[][] tiles;

	private Coordinate emptyCoordinate;
	
	private int movesCount;
	
	public Field(int rowCount, int columnCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		tiles = new Tile[rowCount][columnCount];
		generate();
	}

	private void generate() {
		generateInitialPosition();
		shuffle();
		movesCount = 0;
	}

	private void generateInitialPosition() {
		int value = 1;
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				tiles[row][column] = new Tile(value);
				value++;
			}
		}
		tiles[rowCount - 1][columnCount - 1] = null;
		emptyCoordinate = new Coordinate(rowCount - 1, columnCount - 1);
	}

	private void shuffle() {
		Random random = new Random();
		int moves = rowCount * columnCount * 50;
		for(int i = 0; i < moves;) {
			if(moveTile(random.nextInt(rowCount * columnCount - 1) + 1))
				i++;
		}
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}
	
	private Coordinate getTile(int value) {
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				Tile tile = tiles[row][column];
				if(tile != null && tile.getValue() == value)
					return new Coordinate(row, column);
			}
		}
		return null;
	}
	
	public boolean moveTile(int value) {
		Coordinate coordinate = getTile(value);
		if(coordinate != null) {
			int dr = Math.abs(coordinate.getRow() - emptyCoordinate.getRow());
			int dc = Math.abs(coordinate.getColumn() - emptyCoordinate.getColumn());
			if((dr == 0 && dc == 1) || (dr == 1 && dc == 0)) {
				tiles[emptyCoordinate.getRow()][emptyCoordinate.getColumn()] 
						= tiles[coordinate.getRow()][coordinate.getColumn()];
				tiles[coordinate.getRow()][coordinate.getColumn()] = null;
				emptyCoordinate = coordinate;
				movesCount++;
				return true;
			}
		}
		return false;
	}
	
	public boolean isSolved() {
		int value = 1;
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				Tile tile = tiles[row][column];
				if(tile != null && tile.getValue() != value)
					return false;
				value++;
			}
		}
		return true;
	}
	
	public int getScore() {
		return rowCount * columnCount * 100 - movesCount;
	}
}
