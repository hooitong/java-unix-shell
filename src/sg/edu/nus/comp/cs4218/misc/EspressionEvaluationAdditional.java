package sg.edu.nus.comp.cs4218.misc;

import java.util.Stack;

import sg.edu.nus.comp.cs4218.exception.BcException;

public final class EspressionEvaluationAdditional {
	public final static String NEGATE = "-";
	public final static String NOT_EQUAL = "!=";
	public final static String AND_SIGN = "&&";
	public final static String OR_SIGN = "||";
	public final static String EQUAL = "=";
	public final static String EQUAL_EQUAL_SIGN = "==";
	public final static String GREATER = ">";
	public final static String GREATER_EQUAL = ">=";
	public final static String LESS = "<";
	public final static String LESS_EQUAL = "<=";
	public final static String PLUS = "+";
	public final static String MINUS = "-";
	public final static String DIVIDE = "/";
	public final static String TIMES = "*";
	public final static String NOT = "!";
	public final static String POW = "^";
	public final static String DOT = ".";
	public final static String UNARY_MARKER = "$";
	public final static String NEGATIVE_ONE = "-1";
	public final static char WHITE_SPACE = ' ';
	public final static char DOT_CHAR = '.';
	public final static char NEGATE_CHAR = '-';
	public final static char UNARY_CHAR_MARKER = '$';
	public final static char OPENPAREN = '(';
	public final static char CLOSEDPAREN = ')';
	public final static int ZERO = 0;
	public final static int ONE = 1;
	public final static int TWO = 2;
	public final static int FIVE = 5;
	public final static String ZERO_STRING = "0";
	public final static int MAX_VALUE = 1000000000;

	private EspressionEvaluationAdditional() {
	}

	/**
	 * returns a valid numeric string from the top of stack. else it will throw
	 * an exception
	 * 
	 * @param tStack
	 * @return
	 * @throws BcException
	 */
	public static String getTopStack(Stack<String> tStack) throws BcException {
		String num2Str;
		catchEmptyStackException(tStack);
		num2Str = tStack.pop();
		catchNotNumStringException(num2Str);
		return num2Str;
	}

	/**
	 * throws an exception if the stack is empty as any calculation requires to
	 * have a numeric string at the top
	 * 
	 * @param tStack
	 * @throws BcException
	 */
	public static void catchEmptyStackException(Stack<String> tStack)
			throws BcException {
		if (tStack.isEmpty()) {
			throw new BcException(
					"Stack is empty, unable to retrieve numeric strings");
		}
	}

	/**
	 * throws an exception if the top of the stack is not a numeric string
	 * 
	 * @param num1Str
	 * @throws BcException
	 */
	public static void catchNotNumStringException(String num1Str)
			throws BcException {
		if (!SignChecker.isNumeric(num1Str)) {
			throw new BcException(
					"Unable to perform operations as string is not numeric: "
							+ num1Str);
		}
	}
}
