package br.com.zaqueucavalcante.minefield.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Board {

	private int rows;
	private int columns;
	private int minesNumber;

	private final List<Field> fields;

	public Board(int rows, int columns, int minesNumber) {
		this.rows = rows;
		this.columns = columns;
		this.minesNumber = minesNumber;
		this.fields = new ArrayList<>();

		this.generateFields();
		this.setNeighbors();
		this.distributeMines();
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public List<Field> getFields() {
		return fields;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	private void generateFields() {
		for (int row = 0; row < this.rows; row++) {
			for (int column = 0; column < this.columns; column++) {
				this.fields.add(new Field(row, column));
			}
		}
	}

	private void setNeighbors() {
		for (Field fieldA : this.fields) {
			for (Field fieldB : this.fields) {
				fieldA.addNeighbor(fieldB);
			}
		}
	}

	private void distributeMines() {
		long numberOfDistributedMines = 0;
		int randomFieldIndex;
		int numberOfFields = this.fields.size();
		Predicate<Field> containsAMine = field -> field.containsAMine();
		do {
			numberOfDistributedMines = this.fields.stream().filter(containsAMine).count();
			randomFieldIndex = (int) (Math.random() * numberOfFields);
			this.fields.get(randomFieldIndex).placeMine();
		} while (numberOfDistributedMines < this.minesNumber);
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public void openField(int row, int column) {
		Predicate<Field> rowMatch = field -> field.getRow() == row;
		Predicate<Field> columnMatch = field -> field.getColumn() == column;
		Consumer<Field> openField = field -> field.open();
		this.fields.parallelStream().
			filter(rowMatch).
			filter(columnMatch).
			findFirst().
			ifPresent(openField);
	}
	
	public void checkField(int row, int column) {
		Predicate<Field> rowMatch = field -> field.getRow() == row;
		Predicate<Field> columnMatch = field -> field.getColumn() == column;
		Consumer<Field> checkField = field -> field.check();
		this.fields.parallelStream().
			filter(rowMatch).
			filter(columnMatch).
			findFirst().
			ifPresent(checkField);
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public boolean allIsDone() {
		return this.fields.stream().allMatch(field -> field.isSolved());
	}

	public void resetAllFields() {
		this.fields.stream().forEach(field -> field.resetBooleanValues());
		this.distributeMines();
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
}
