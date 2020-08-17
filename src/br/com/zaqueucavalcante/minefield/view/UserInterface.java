package br.com.zaqueucavalcante.minefield.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import br.com.zaqueucavalcante.minefield.model.Board;
import br.com.zaqueucavalcante.minefield.model.Field;

public class UserInterface {

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	// https://stackoverflow.com/questions/2979383/java-clear-the-console
	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public static void print(Board board) {
		StringBuilder stringBuilder = new StringBuilder();
		int fieldIndex = 0;
		String fieldSymbol;
		
		stringBuilder.append(" ");
		for (int column = 0; column < board.getColumns(); column++) {
			stringBuilder.append(" ");
			stringBuilder.append(column);
			stringBuilder.append(" ");
		}
		stringBuilder.append("\n");
		
		for (int row = 0; row < board.getRows(); row++) {
			stringBuilder.append(row);
			
			for (int column = 0; column < board.getColumns(); column++) {
				stringBuilder.append(" ");
				fieldSymbol = getSymbol(board.getFields().get(fieldIndex));
				stringBuilder.append(fieldSymbol);
				stringBuilder.append(" ");
				fieldIndex++;
			}
			stringBuilder.append("\n");
		}
		System.out.println(stringBuilder);
	}

	private static String getSymbol(Field field) {
		if (field.isCheck()) {
			return AnsiColors.ANSI_GREEN + "x";
		} else if (field.isOpen() && field.containsAMine()) {
			return AnsiColors.ANSI_RED + "*";
		} else if (field.isOpen() && field.getNumberOfMinedNeighbors() > 0) {
			return AnsiColors.ANSI_YELLOW + Long.toString(field.getNumberOfMinedNeighbors());
		} else if (field.isOpen()) {
			return AnsiColors.ANSI_BLACK_BACKGROUND + " " + AnsiColors.ANSI_RESET;
		} else {
			return AnsiColors.ANSI_WHITE + "?";
		}
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public static Field readPosition(Scanner scanner) {
		try {
			String userInput = scanner.nextLine();
			int row = Integer.parseInt(userInput.substring(0, 1));
			int column = Integer.parseInt(userInput.substring(2, 3));
			return new Field(row, column);
		} catch (RuntimeException e) {
			throw new InputMismatchException("Error reading position. Valid format is (x,y).");
		}
	}

	public static String readUserInput(Scanner scanner) {
		try {
			String userInput = scanner.nextLine();
			return userInput;
		} catch (RuntimeException e) {
			throw new InputMismatchException("Error reading input.");
		}
	}

}
