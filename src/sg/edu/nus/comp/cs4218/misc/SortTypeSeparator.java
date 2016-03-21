package sg.edu.nus.comp.cs4218.misc;

public final class SortTypeSeparator {
	private SortTypeSeparator() {
	}

	/**
	 * Counts lower case letters in a string
	 * 
	 * @param currentLine
	 *            input line
	 * @return int count of lower case letters
	 */
	static int getSimpleCharInLineCount(String currentLine) {
		int count = 0;
		for (int j = 0; j < currentLine.length(); j++) {
			int currentChar = currentLine.charAt(j);
			if (Character.isLetter(currentChar) && Character.isLowerCase(currentChar)) {
				count++;
			}
		}

		return count;
	}

	/**
	 * Counts uppercase letters in a string
	 * 
	 * @param currentLine
	 *            input line
	 * @return int count of uppercase leters
	 */
	static int getCapitalCharInLineCount(String currentLine) {
		int count = 0;
		for (int j = 0; j < currentLine.length(); j++) {
			char currentChar = currentLine.charAt(j);
			if (Character.isLetter(currentChar) && Character.isUpperCase(currentChar)) {
				count++;
			}
		}

		return count;
	}

	/**
	 * Get a count of digits present in a string /**
	 * 
	 * @param currentLine
	 *            input line
	 * @return int a count of digits
	 */
	static int getNumberCharInLineCount(String currentLine) {
		int count = 0;
		for (int j = 0; j < currentLine.length(); j++) {
			int currentChar = currentLine.charAt(j);
			if (Character.isDigit(currentChar)) {
				count++;
			}
		}

		return count;
	}

	/**
	 * count the number of special characters in a string
	 * 
	 * @param string
	 *            input line
	 * @return int a count of special characters
	 */
	static int getSpecialCharInLineCount(String currentLine) {
		int count = 0;
		for (int j = 0; j < currentLine.length(); j++) {
			char currentChar = currentLine.charAt(j);
			if (!Character.isDigit(currentChar) && !Character.isLetter(currentChar)) {
				count++;
			}
		}
		return count;
	}
}
