package sk.tsystems.gamestudio.game.mines.core;

import java.util.Random;

public class Field {
	// variables
	private final int rowCount;
	private final int columnCount;
	private final int mineCount;
	private final Tile[][] tiles;
	private GameState state = GameState.PLAYING;
	private int numberOfOpenTiles;

	// konstruktor
	public Field(int rowCount, int columnCount, int mineCount) {
		if(mineCount>=rowCount*columnCount)
			throw new IllegalArgumentException("Invalid number of mines:" + mineCount);
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.mineCount = mineCount;
		tiles = new Tile[rowCount][columnCount];
		generate();
	}

	// metody

	private void generate() {
		generateMines();
		fillWithClues();
	}

	private void generateMines() {
		Random random = new Random();

		int minesToSet = mineCount;
		while (minesToSet > 0) {
			int row = random.nextInt(rowCount);
			int column = random.nextInt(columnCount);
			if (tiles[row][column] == null) {
				tiles[row][column] = new Mine();
				minesToSet--;
			}
		}

	}

	private void fillWithClues() {
		boolean isRowInAraay = true;
		boolean isColumnInArray = true;
		// cykly na cele pole
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				if (tiles[row][column] == null) {
					int clueValue = 0;

					// cykly ktore vybavia jedno policko

					for (int i = row - 1; i <= row + 1; i++) {
						if (i == -1 || i == rowCount) {
							isRowInAraay = false;
						}
						for (int j = column - 1; j <= column + 1; j++) {
							if (j == -1 || j == columnCount) {
								isColumnInArray = false;
							}
							if (isRowInAraay && isColumnInArray) {
								if (tiles[i][j] instanceof Mine) {
									clueValue++;
								}
							}
							isColumnInArray = true;
						}
						isRowInAraay = true;
					}
					tiles[row][column] = new Clue(clueValue);
				}
			}
		}
	}

	public void markTile(int row, int column) {
		Tile tile = tiles[row][column];
		if (tile.getState().equals(TileState.CLOSED))
			tile.setState(TileState.MARKED);
		else if (tile.getState().equals(TileState.MARKED))
			tile.setState(TileState.CLOSED);
	}

	public void openTile(int row, int column) {
		Tile tile = tiles[row][column];

		if (tile.getState().equals(TileState.CLOSED)) {
			tile.setState(TileState.OPENED);
			numberOfOpenTiles++;

			if (tile instanceof Mine) {
				state = GameState.FAILED;
				return;
			}
			if (tile instanceof Clue && ((Clue) tile).getValue() == 0) {
				openNeighbourTiles(row, column);
			}
		}
		if (isSolved()) {
			state = GameState.SOLVED;
			return;
		}
	}

	private void openNeighbourTiles(int row, int column) {
		if (tileExists(row - 1, column - 1))
			openTile(row - 1, column - 1);
		if (tileExists(row - 1, column))
			openTile(row - 1, column);
		if (tileExists(row - 1, column + 1))
			openTile(row - 1, column + 1);
		if (tileExists(row, column - 1))
			openTile(row, column - 1);
		if (tileExists(row, column + 1))
			openTile(row, column + 1);
		if (tileExists(row + 1, column - 1))
			openTile(row + 1, column - 1);
		if (tileExists(row + 1, column))
			openTile(row + 1, column);
		if (tileExists(row + 1, column + 1))
			openTile(row + 1, column + 1);
	}

	private boolean tileExists(int row, int column) {

		return row >= 0 && row < rowCount && column >= 0 && column < columnCount;
	}

	private boolean isSolved() {
		return rowCount * columnCount - mineCount == numberOfOpenTiles;
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public int getMineCount() {
		return mineCount;
	}

	public GameState getState() {
		return state;
	}

	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}

}
