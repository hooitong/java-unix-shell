package sg.edu.nus.comp.cs4218.misc;

public final class SignChecker {
	private final static char EQUAL = '=';
	private final static char GREATER = '>';
	private final static char LESS = '<';
	private final static char PLUS = '+';
	private final static char MINUS = '-';
	private final static char DIVIDE = '/';
	private final static char TIMES = '*';
	private final static char POW = '^';
	private final static char NOT_SIGN = '!';
	private final static char AND_SIGN = '&';
	private final static char OR_SINGLE_SIGN = '|';
	private final static String DOT = ".";

	private SignChecker() {
	}

	/**
	 * This method checks if a string is either an integer or a float
	 * 
	 * @param input
	 *            input string
	 * @return boolean True if it is an integer/float else false
	 */
	public static boolean isNumeric(String input) {
		boolean result = true;
		if (input.contains(DOT)) {
			try {
				Float.parseFloat(input);
			} catch (Exception e) {
				result = false;
			}
		} else {
			try {
				Integer.parseInt(input);
			} catch (Exception e) {
				result = false;
			}
		}

		return result;
	}

	/**
	 * This method checks if a character is an operator of the following without
	 * quotes " + - / ^ "
	 * 
	 * @param currChar
	 *            input character
	 * @return boolean
	 */
	public static boolean isOperator(char currChar) {
		boolean result = false;
		if (currChar == PLUS || currChar == MINUS || currChar == DIVIDE || currChar == TIMES || currChar == POW) {
			result = true;
		}
		return result;
	}

	/**
	 * This method checks if the input character is part of a
	 * relational/conditional character. It is defined as one of the following
	 * without quotes "< > ? ! ="
	 * 
	 * @param currChar
	 * @return
	 */
	public static boolean isRelationCondtional(char currChar) {
		boolean result = false;
		if (currChar == LESS || currChar == GREATER || currChar == EQUAL || currChar == NOT_SIGN || currChar == AND_SIGN
				|| currChar == OR_SINGLE_SIGN) {
			result = true;
		}
		return result;
	}
}
