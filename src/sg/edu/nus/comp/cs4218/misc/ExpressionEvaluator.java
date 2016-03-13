package sg.edu.nus.comp.cs4218.misc;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Stack;
import java.util.Vector;

public final class ExpressionEvaluator {
	private String[] argArr;
	public String leftExp;
	public String rightExp;
	public boolean isEqualPresent;
	public boolean isTwoEqualPresent;
	public boolean isNotPresent;
	public boolean isAndPresent;
	public boolean isOrPresent;
	public boolean isGreaterPresent;
	public boolean isLesserPresent;

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
	public final static char DOT_CHAR = '.';
	public final static char OPENBRAC = '(';
	public final static char CLOSEBRACE = ')';
	public final static int ZERO = 0;
	public final static int ONE = 1;
	public final static int TWO = 2;
	public final static String ZERO_STRING = "0";
	public final static int MAX_VALUE = 1000000000;

	private ExpressionEvaluator() {
	}

	public static Vector<String> infixToPostfix(String exp) {
		Stack<String> tStack = new Stack<String>();
		Vector<String> splittedString = splitSeparateStrings(exp);
		Vector<String> resultVector = new Vector<String>();
		StringBuilder stringBuilder = new StringBuilder("");

		for (int i = 0; i < splittedString.size(); i++) {
			String cString = splittedString.get(i);

			if (cString.charAt(ZERO) == OPENBRAC) {
				tStack.push(String.valueOf(cString));
			} else if (cString.charAt(ZERO) == CLOSEBRACE) {
				while (!tStack.empty()
						&& tStack.peek().charAt(ZERO) != OPENBRAC) {
					resultVector.add(tStack.pop());
				}
				tStack.pop();
			}

			if (SignChecker.isAlphabet(cString)
					|| SignChecker.isNumeric(cString)) {
				resultVector.add(String.valueOf(cString));
			}
			if (SignChecker.isOperator(cString.charAt(ZERO))) {
				if (tStack.isEmpty()) {
					tStack.push(String.valueOf(cString));
				} else {
					while (!tStack.empty()
							&& (getPrecedence(cString) <= getPrecedence(tStack
									.peek()))) {
						resultVector.add(tStack.pop());
					}
					tStack.push(String.valueOf(cString));
				}
			}
			if (SignChecker.isRelationCondtional(cString.charAt(ZERO))) {
				while (!tStack.empty()
						&& (getPrecedence(cString) <= getPrecedence(tStack
								.peek()))) {
					resultVector.add(tStack.pop());
				}
				tStack.push(String.valueOf(cString));
			}
		}
		while (!tStack.isEmpty()) {
			resultVector.add(tStack.pop());
		}
		return resultVector;
	}

	private static int getPrecedence(String currChar) {
		int result = -1;
		if (currChar.compareTo(POW) == 0) {
			result = 9;
		} else if (currChar.compareTo(DIVIDE) == 0
				|| currChar.compareTo(TIMES) == 0) {
			result = 8;
		} else if (currChar.compareTo(PLUS) == 0
				|| currChar.compareTo(MINUS) == 0) {
			result = 7;
		} else if (currChar.compareTo(NOT_EQUAL) == 0
				|| currChar.contains(EQUAL_EQUAL_SIGN)) {
			result = 6;
		} else if (currChar.compareTo(LESS) == 0
				|| currChar.contains(LESS_EQUAL) || currChar.contains(GREATER)
				|| currChar.contains(GREATER_EQUAL)) {
			result = 5;
		} else if (currChar.compareTo(NOT) == 0) {
			result = 4;
		} else if (currChar.compareTo(AND_SIGN) == 0) {
			result = 3;
		} else if (currChar.compareTo(OR_SIGN) == 0) {
			result = 2;
		}
		return result;
	}

	public static boolean isBracketCountSame(String input) {
		int openBraceCount = 0, closeBraceCount = 0;
		boolean result = false;
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == OPENBRAC) {
				openBraceCount++;
			}
			if (input.charAt(i) == CLOSEBRACE) {
				closeBraceCount++;
			}
		}
		if (openBraceCount == closeBraceCount) {
			result = true;
		}
		return result;
	}

	public static String computeResult(Vector<String> postfixVector) {
		Stack<String> tStack = new Stack<String>();
		for (int i = 0; i < postfixVector.size(); i++) {
			String cString = postfixVector.get(i);
			if (SignChecker.isAlphabet(cString)
					|| SignChecker.isNumeric(cString)) {
				tStack.add(String.valueOf(cString));
			} else {
				if (cString.compareTo(NOT) == 0) {
					tStack.push(calculateRelation(tStack.pop(), ZERO_STRING, cString));
				} else if(SignChecker.isOperator(cString.charAt(ZERO))){
					tStack.push(calculateArithmetic(tStack.pop(), tStack.pop(), cString));
				}else{
					tStack.push(calculateRelation(tStack.pop(), tStack.pop(), cString));
				}

			}
		}
		return tStack.pop();
	}

	public static String calculateArithmetic(String numStr2, String numStr1,
			String operator) {
		String result = "";
		BigDecimal bdNum1 = new BigDecimal(numStr1);
		BigDecimal bdNum2 = new BigDecimal(numStr2);
		switch (operator) {
		case PLUS:
			result = bdNum1.add(bdNum2).toString();
			break;
		case MINUS:
			result = bdNum1.subtract(bdNum2).toString();
			break;
		case DIVIDE:
			result = bdNum1.divide(bdNum2, 0, RoundingMode.HALF_UP).toString();
			break;
		case TIMES:
			result = bdNum1.multiply(bdNum2).toString();
			break;
		case POW:
			bdNum1 = bdNum1.pow(bdNum2.intValueExact());
			result = bdNum1.setScale(ONE, RoundingMode.HALF_UP).toString();
			break;
		default:
			break;
		}
			return result;
	}

	public static String calculateRelation(String numStr2, String numStr1,
			String operator) {
		String result = "";
		BigDecimal bdNum1 = new BigDecimal(numStr1);
		BigDecimal bdNum2 = new BigDecimal(numStr2);
		switch (operator) {
		case EQUAL_EQUAL_SIGN:
			result = bdNum1.compareTo(bdNum2) == ZERO ? "1" : "0";
			break;
		case GREATER_EQUAL:
			result = bdNum1.compareTo(bdNum2) >= ZERO ? "1" : "0";
			break;
		case GREATER:
			result = bdNum1.compareTo(bdNum2) > ZERO ? "1" : "0";
			break;
		case LESS:
			result = bdNum1.compareTo(bdNum2) < ZERO ? "1" : "0";
			break;
		case LESS_EQUAL:
			result = bdNum1.compareTo(bdNum2) <= ZERO ? "1" : "0";
			break;
		case NOT_EQUAL:
			result = bdNum1.compareTo(bdNum2) == ZERO ? "0" : "1";
			break;
		case NOT:
			result = bdNum2.compareTo(BigDecimal.ZERO) > ZERO ? "0" : "1";
			break;
		case AND_SIGN:
			result = bdNum1.compareTo(BigDecimal.ZERO) > ZERO
					&& bdNum2.compareTo(BigDecimal.ZERO) > ZERO ? "1" : "0";
			break;
		case OR_SIGN:
			result = bdNum1.compareTo(BigDecimal.ZERO) > ZERO
					|| bdNum2.compareTo(BigDecimal.ZERO) > ZERO ? "1" : "0";
			break;
		default:
			break;
		}
		return result;
	}

	public static Vector<String> splitSeparateStrings(String input) {
		StringBuilder stringBuilder = new StringBuilder("");
		StringBuilder stringBuilderR = new StringBuilder("");
		Vector<String> resultVector = new Vector<String>();
		for (int i = 0; i < input.length(); i++) {
			char currChar = input.charAt(i);
			if (Character.isDigit(currChar) || currChar == DOT_CHAR) {
				stringBuilder.append(currChar);
				if (stringBuilderR.length() != ZERO) {
					resultVector.add(stringBuilderR.toString());
					stringBuilderR.setLength(0);
				}
			} else if (SignChecker.isOperator(currChar) || currChar == OPENBRAC
					|| currChar == CLOSEBRACE) {
				if ((currChar == CLOSEBRACE || currChar == OPENBRAC)
						&& stringBuilderR.length() != ZERO) {
					resultVector.add(stringBuilderR.toString());
					stringBuilderR.setLength(0);
				}
				if (stringBuilder.length() != ZERO) {
					resultVector.add(stringBuilder.toString());
					stringBuilder.setLength(0);
				}
				resultVector.add(String.valueOf(currChar));
			} else if (SignChecker.isRelationCondtional(currChar)) {
				stringBuilderR.append(currChar);
				if (stringBuilder.length() != ZERO) {
					resultVector.add(stringBuilder.toString());
					stringBuilder.setLength(0);
				}
			}
		}
		if (stringBuilder.length() != ZERO) {
			resultVector.add(stringBuilder.toString());
		}
		return resultVector;
	}

}
