package sg.edu.nus.comp.cs4218.misc;

import java.util.Stack;
import java.util.Vector;

public class ExpressionEvaluator {
	private String[] argArr;
	public String leftExp;
	public String rightExp;
	public boolean isEqualPresent;
	public boolean isEqualEqualPresent;
	public boolean isNotPresent;
	public boolean isAndPresent;
	public boolean isOrPresent;
	public boolean isGreaterPresent;
	public boolean isLesserPresent;

	public final static String NEGATE = "-";
	public final static String EQUALSTR = "=";
	public final static String NOTEQUALSTR = "!=";
	public final static String GREATERSTR = ">";
	public final static String LESSSTR = "<";
	public final static String NOTSTR = "!";
	public final static String ANDSTR = "&&";
	public final static String ORSTR = "||";
	public final static char EQUAL = '=';
	public final static char GREATER = '>';
	public final static char LESS = '<';
	public final static char PLUS = '+';
	public final static char MINUS = '-';
	public final static char DIVIDE = '/';
	public final static char TIMES = '*';
	public final static char POW = '^';
	public final static char OPENBRAC = '(';
	public final static char CLOSEBRAC = ')';
	public final static int ZERO = 0;
	public final static int ONE = 1;
	public final static int TWO = 2;
	public final static int MAX_VALUE = 1000000000;

	public ExpressionEvaluator() {
		this.leftExp = "";
		this.rightExp = "";
		this.isEqualPresent = false;
		this.isEqualEqualPresent = false;
		this.isGreaterPresent = false;
		this.isLesserPresent = false;
	}

	// private breakExpression() {
	//
	// }

	public Vector<String> infixToPostfix(String exp) {
		Stack<String> tStack = new Stack<String>();
		Vector<String> splittedString = splitSeparateStrings(exp);
		Vector<String> resultVector = new Vector<String>();

		for (int i = 0; i < splittedString.size(); i++) {
			String cString = splittedString.get(i);
			if (cString.charAt(ZERO) == OPENBRAC) {
				tStack.push(String.valueOf(cString));
			} else if (cString.charAt(ZERO) == CLOSEBRAC) {
				while (!tStack.empty()
						&& tStack.peek().charAt(ZERO) != OPENBRAC) {
					resultVector.add(tStack.pop());
				}
				tStack.pop();
			}

			if (isAlphabet(cString) || isNumeric(cString)) {
				resultVector.add(String.valueOf(cString));
			}
			if (isOperator(cString.charAt(ZERO))) {
				if (tStack.isEmpty()) {
					tStack.push(String.valueOf(cString));
				} else {
					while (!tStack.empty()
							&& (getPrecedence(cString.charAt(0)) <= getPrecedence(tStack
									.peek().charAt(ZERO)))) {
						resultVector.add(tStack.pop());
					}
					tStack.push(String.valueOf(cString));
				}
			}
		}
		while (!tStack.isEmpty()) {
			resultVector.add(tStack.pop());
		}
		return resultVector;
	}

	private boolean isOperator(char currChar) {
		boolean result = false;
		if (currChar == PLUS || currChar == MINUS || currChar == DIVIDE
				|| currChar == TIMES || currChar == POW) {
			result = true;
		}
		return result;
	}

	private int getPrecedence(char currChar) {
		int result = -1;
		if (currChar == POW) {
			result = 3;
		} else if (currChar == DIVIDE || currChar == TIMES) {
			result = 2;
		} else if (currChar == PLUS || currChar == MINUS) {
			result = 1;
		}
		return result;
	}

	public boolean isBracCountSame(String input) {
		int openBracCount = 0, closeBracCount = 0;
		boolean result = false;
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == OPENBRAC) {
				openBracCount++;
			}
			if (input.charAt(i) == CLOSEBRAC) {
				closeBracCount++;
			}
		}
		if (openBracCount == closeBracCount) {
			result = true;
		}
		return result;
	}

	public String computeResult(Vector<String> postfixVector) {
		Stack<String> tStack = new Stack<String>();
		for (int i = 0; i < postfixVector.size(); i++) {
			String cString = postfixVector.get(i);
			if (isAlphabet(cString) || isNumeric(cString)) {
				tStack.add(String.valueOf(cString));
			} else {
				tStack.push(Integer.toString(calculate(tStack.pop(),
						tStack.pop(), cString.charAt(ZERO))));
			}
		}
		return tStack.pop();
	}

	private int calculate(String numStr2, String numStr1, char operator) {
		int result = 1000000000;
		switch (operator) {
		case PLUS:
			result = Integer.parseInt(numStr1) + Integer.parseInt(numStr2);
			break;
		case MINUS:
			result = Integer.parseInt(numStr1) - Integer.parseInt(numStr2);
			break;
		case DIVIDE:
			result = Integer.parseInt(numStr1) / Integer.parseInt(numStr2);
			break;
		case TIMES:
			result = Integer.parseInt(numStr1) * Integer.parseInt(numStr2);
			break;
		case POW:
			result = (int) Math.pow(Integer.parseInt(numStr1),
					Integer.parseInt(numStr2));
			break;
		default:
			break;
		}
		return result;
	}

	public Vector<String> splitSeparateStrings(String input) {
		StringBuilder stringBuilder = new StringBuilder("");
		Vector<String> resultVector = new Vector<String>();
		for (int i = 0; i < input.length(); i++) {
			char currChar = input.charAt(i);
			if (Character.isAlphabetic(currChar) || Character.isDigit(currChar)) {
				stringBuilder.append(currChar);
			} else if (isOperator(currChar) || currChar == OPENBRAC
					|| currChar == CLOSEBRAC) {
				if (stringBuilder.length() > 0) {
					resultVector.add(stringBuilder.toString());
					stringBuilder.setLength(0);
				}
				resultVector.add(String.valueOf(currChar));
			} else {
				//
			}
		}
		if (stringBuilder.length() > 0) {
			resultVector.add(stringBuilder.toString());
		}
		return resultVector;
	}

	public void separateExpressions(String input) {
		String delimiters = "\\|\\| | <= | < | >= | > | != | == | &&";
		String[] arr = input.split(delimiters);
		this.leftExp = arr[ZERO];
		if (arr.length == 2) {
			this.rightExp = arr[ONE];
		}
	}

	public boolean isNumeric(String input) {
		boolean result = true;
		for (int i = 0; i < input.length(); i++) {
			if (!Character.isDigit(input.charAt(i))) {
				result = false;
			}
		}
		return result;
	}

	public boolean isAlphabet(String input) {
		boolean result = true;
		for (int i = 0; i < input.length(); i++) {
			if (!Character.isAlphabetic(input.charAt(i))) {
				result = false;
			}
		}
		return result;
	}

	public String negation(String input) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (input.charAt(ZERO) == MINUS) {
			stringBuilder.append(input.substring(ONE, input.length()));
		} else {
			stringBuilder.append("-").append(input);
		}
		return stringBuilder.toString();
	}

	public boolean isGreaterThanEqual(String exp1, String exp2) {
		return Integer.parseInt(exp1) >= Integer.parseInt(exp2);
	}

	public boolean isGreaterThan(String exp1, String exp2) {
		return Integer.parseInt(exp1) > Integer.parseInt(exp2);
	}

	public boolean isLessThanEqual(String exp1, String exp2) {
		return Integer.parseInt(exp1) <= Integer.parseInt(exp2);
	}

	public boolean isLessThan(String exp1, String exp2) {
		return Integer.parseInt(exp1) < Integer.parseInt(exp2);
	}

	public boolean isEqualEqual(String exp1, String exp2) {
		return Integer.parseInt(exp1) == Integer.parseInt(exp2);
	}

	public boolean isNotEqual(String exp1, String exp2) {
		return Integer.parseInt(exp1) != Integer.parseInt(exp2);
	}

	public boolean not(String exp1) {
		return !Boolean.parseBoolean(exp1);
	}

	public boolean or(String exp1, String exp2) {
		return Integer.parseInt(exp1) > 0 || Integer.parseInt(exp2) > 0;
	}

	public boolean and(String exp1, String exp2) {
		return Integer.parseInt(exp1) > 0 && Integer.parseInt(exp2) > 0;
	}

	public void checkEqualSign(String expresssion) {
		int count = 0;
		for (int i = 0; i < expresssion.length(); i++) {
			if (expresssion.charAt(i) == EQUAL) {
				count++;
			}
		}
		if (count == 1) {
			this.isEqualPresent = true;
		} else if (count == 2) {
			this.isEqualEqualPresent = true;
		}
	}

	public void checkIsLesserSign(String expresssion) {
		if (expresssion.contains(LESSSTR)) {
			this.isLesserPresent = true;
		}
	}

	public void checkIsGreaterSign(String expresssion) {
		if (expresssion.contains(GREATERSTR)) {
			this.isGreaterPresent = true;
		}
	}

	public void checkIsNotSign(String expresssion) {
		if (expresssion.contains(NOTEQUALSTR)) {
			this.isGreaterPresent = true;
		}
	}

	public void checkIsAndSign(String expresssion) {
		if (expresssion.contains(ANDSTR)) {
			this.isAndPresent = true;
		}
	}

	public void checkIsOrSign(String expresssion) {
		if (expresssion.contains(ORSTR)) {
			this.isOrPresent = true;
		}
	}

}
