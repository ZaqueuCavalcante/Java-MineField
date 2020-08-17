package br.com.zaqueucavalcante.minefield.view;

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
		for (int row = 0; row < board.getRows(); row++) {
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
			return Long.toString(field.getNumberOfMinedNeighbors());
		} else if (field.isOpen()) {
			return AnsiColors.ANSI_BLUE + " ";
		} else {
			return AnsiColors.ANSI_WHITE + "?";
		}
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	
}
