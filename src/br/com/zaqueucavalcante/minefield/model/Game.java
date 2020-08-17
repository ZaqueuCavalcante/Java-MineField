package br.com.zaqueucavalcante.minefield.model;

import java.util.InputMismatchException;
import java.util.Scanner;

import br.com.zaqueucavalcante.minefield.exception.ExplosionException;
import br.com.zaqueucavalcante.minefield.view.UserInterface;

public class Game {

	public static void main(String[] args) {
		Board board = new Board(10, 10, 6);
		Scanner scanner = new Scanner(System.in);
		
		board.openField(3, 3);
		board.checkField(3, 3);
		board.checkField(2, 2);
		board.checkField(1, 1);
		
		while (!board.allIsDone()) {
			try {
				UserInterface.clearScreen();
				UserInterface.print(board);
				
				System.out.println();
				System.out.print("Enter with (x,y) position: ");
				Field field = UserInterface.readPosition(scanner);
				
				System.out.println();
				System.out.println("1 -> Open ");
				System.out.println("2 -> (Un)Check ");
				System.out.print("---> ");
				String userInput = UserInterface.readUserInput(scanner);
				
				if ("1".equals(userInput)) {
					board.openField(field.getRow(), field.getColumn());
				} else if ("2".equals(userInput)) {
					board.checkField(field.getRow(), field.getColumn());
				}
				
			} catch (ExplosionException e) {
				System.out.println(e.getMessage());
				scanner.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				scanner.nextLine();
			}
		}
		UserInterface.clearScreen();
		UserInterface.print(board);
	}
}
