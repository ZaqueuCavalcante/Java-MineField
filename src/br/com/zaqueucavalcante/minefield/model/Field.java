package br.com.zaqueucavalcante.minefield.model;

import java.util.ArrayList;
import java.util.List;

import br.com.zaqueucavalcante.minefield.exception.ExplosionException;

public class Field {

	private final int row;
	private final int column;

	private boolean open;
	private boolean containsAMine;
	private boolean check;

	private List<Field> neighbors;

	public Field(int row, int column) {
		this.row = row;
		this.column = column;
		this.neighbors = new ArrayList<>();
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public boolean isOpen() {
		return open;
	}

	public boolean isCheck() {
		return check;
	}

	public boolean containsAMine() {
		return containsAMine;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public boolean addNeighbor(Field neighbor) {
		double distance = calculateEuclideanDistance(this, neighbor);
		if (proximityTest(distance)) {
			this.neighbors.add(neighbor);
			return true;
		}
		return false;
	}

	private double calculateEuclideanDistance(Field A, Field B) {
		double deltaX = A.column - B.column;
		double deltaY = A.row - B.row;
		double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		return distance;
	}

	private boolean proximityTest(double distance) {
		double maxDistance = 1.42;
		if (distance > 0 && distance < maxDistance) {
			return true;
		}
		return false;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public void check() {
		if (!this.open) {
			this.check = !this.check;
		}
	}

	public void placeMine() {
		this.containsAMine = true;
	}

	public boolean open() {
		if (!this.open && !this.check) {
			this.open = true;
			if (this.containsAMine) {
				throw new ExplosionException();
			}
			if (this.neighborhoodIsSafe()) {
				this.neighbors.forEach(neighbor -> neighbor.open());
			}
			return true;
		}
		return false;
	}

	private boolean neighborhoodIsSafe() {
		return this.neighbors.stream().noneMatch(neighbor -> neighbor.containsAMine);
	}

	public long getNumberOfMinedNeighbors() {
		return this.neighbors.stream().filter(neighbor -> neighbor.containsAMine).count();
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public boolean isSolved() {
		boolean openAndWithoutAMine = this.open && !this.containsAMine;
		boolean checkAndWithAMine = this.check && this.containsAMine;
		return openAndWithoutAMine || checkAndWithAMine;
	}

	public void resetBooleanValues() {
		this.open = false;
		this.containsAMine = false;
		this.check = false;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
}
