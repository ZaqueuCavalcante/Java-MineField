package br.com.zaqueucavalcante.minefield.model;

import br.com.zaqueucavalcante.minefield.view.UserInterface;

public class Game {

	public static void main(String[] args) {
		Board board = new Board(6, 6, 25);
		UserInterface.clearScreen();
		UserInterface.print(board);
//		board.openField(2, 2);
		board.checkField(3, 3);
		board.checkField(2, 2);
		board.checkField(1, 1);
		UserInterface.clearScreen();
		UserInterface.print(board);
	}
}
