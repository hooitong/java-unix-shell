package sg.edu.nus.comp.cs4218.misc;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Stack;
import java.util.Vector;

import sg.edu.nus.comp.cs4218.exception.BcException;

public final class ExpressionEvaluator {
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

	private ExpressionEvaluator() {
	}

	/**
	 * This methods converts the input expression string from infix to postfix
	 * format
	 * 
	 * @param exp
	 *            input expression string string
	 * @return Vector<String>
	 * @throws BcException
	 */
	public static Vector<String> infixToPostfix(String exp) throws BcException {
		Stack<String> tStack = new Stack<String>();
		Vector<String> splittedString = splitSeparateStrings(exp);
		Vector<String> resultVector = new Vector<String>();
		for (int i = 0; i < splittedString.size(); i++) {
			String cString = splittedString.get(i);
			if (cString.charAt(ZERO) == OPENPAREN) {
				tStack.push(String.valueOf(cString));
			} else if (cString.charAt(ZERO) == CLOSEDPAREN) {
				while (!tStack.empty()
						&& tStack.peek().charAt(ZERO) != OPENPAREN) {
					resultVector.add(tStack.pop());
				}
				if (tStack.isEmpty()) {
					throw new BcException(
							"Close parenthesis appeared before Open parenthesis");
				}
				tStack.pop();
			} else if (SignChecker.isNumeric(cString)) {
				resultVector.add(String.valueOf(cString));
			} else if (SignChecker.isOperator(cString.charAt(ZERO))
					|| SignChecker.isRelationCondtional(cString.charAt(ZERO))) {
				if (cString.charAt(ZERO) == NEGATE_CHAR) {
					cString = checkForUnaryMinus(splittedString, i, cString);
				}
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

	/**
	 * check for valid position of a unary minus, if so mark it with a '$'
	 * instead of a '-' as it will interfere during the precedence checking
	 * 
	 * @param splittedString
	 * @param index
	 * @param cString
	 * @return
	 */
	private static String checkForUnaryMinus(Vector<String> splittedString,
			int index, String cString) {
		if (index == ZERO) {
			return UNARY_MARKER;
		} else if (index < splittedString.size() - 1) {
			char prevChar = splittedString.get(index - 1).charAt(ZERO);
			if (SignChecker.isOperator(prevChar)
					|| SignChecker.isRelationCondtional(prevChar)
					|| prevChar == OPENPAREN) {
				return UNARY_MARKER;
			}
		}
		return cString;
	}

	/**
	 * This method cross checks the input string against the operators
	 * precedence list
	 * 
	 * @param tempStr
	 *            input string
	 * @return int which indicates its importance
	 */
	private static int getPrecedence(String tempStr) {
		int result = -1;
		if (tempStr.compareTo(UNARY_MARKER) == 0) {
			result = 10;
		} else if (tempStr.compareTo(POW) == 0) {
			result = 9;
		} else if (tempStr.compareTo(DIVIDE) == 0
				|| tempStr.compareTo(TIMES) == 0) {
			result = 8;
		} else if (tempStr.compareTo(PLUS) == 0
				|| tempStr.compareTo(MINUS) == 0) {
			result = 7;
		} else if (tempStr.compareTo(NOT_EQUAL) == 0
				|| tempStr.contains(EQUAL_EQUAL_SIGN)) {
			result = 6;
		} else if (tempStr.compareTo(LESS) == 0 || tempStr.contains(LESS_EQUAL)
				|| tempStr.contains(GREATER) || tempStr.contains(GREATER_EQUAL)) {
			result = 5;
		} else if (tempStr.compareTo(NOT) == 0) {
			result = 4;
		} else if (tempStr.compareTo(AND_SIGN) == 0) {
			result = 3;
		} else if (tempStr.compareTo(OR_SIGN) == 0) {
			result = 2;
		}
		return result;
	}

	/**
	 * This method checks if the input string has equal number of parentheses
	 * 
	 * @param input
	 *            input string
	 * @return boolean
	 */
	public static boolean isParenthesesCountSame(String input) {
		int openParenCount = 0, closeParenCount = 0;
		boolean result = false;
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == OPENPAREN) {
				openParenCount++;
			}
			if (input.charAt(i) == CLOSEDPAREN) {
				closeParenCount++;
			}
		}
		if (openParenCount == closeParenCount) {
			result = true;
		}
		return result;
	}

	/**
	 * This method tries to evaluate the original expression using the vector of
	 * strings (postfix vector)
	 * 
	 * @param postfixVector
	 *            Vector<String> in postfix
	 * @return string
	 * @throws BcException
	 */
	public static String computeResult(Vector<String> postfixVector)
			throws BcException {
		Stack<String> tStack = new Stack<String>();
		for (int i = 0; i < postfixVector.size(); i++) {
			String cString = postfixVector.get(i);
			String num1Str = "";
			String num2Str = "";
			if (SignChecker.isNumeric(cString)) {
				tStack.push(String.valueOf(cString));
			} else {
				if (cString.compareTo(UNARY_MARKER) == 0) {
					num1Str = EspressionEvaluationAdditional.getTopStack(tStack);
					tStack.push(calculateArithmetic(NEGATIVE_ONE, num1Str,
							TIMES));
				} else if (cString.compareTo(NOT) == 0) {
					num1Str = EspressionEvaluationAdditional.getTopStack(tStack);
					tStack.push(calculateRelation(num1Str, ZERO_STRING, cString));
				} else if (SignChecker.isOperator(cString.charAt(ZERO))) {
					num2Str = EspressionEvaluationAdditional.getTopStack(tStack);
					num1Str = EspressionEvaluationAdditional.getTopStack(tStack);
					tStack.push(calculateArithmetic(num2Str, num1Str, cString));
				} else {
					num2Str = EspressionEvaluationAdditional.getTopStack(tStack);
					num1Str = EspressionEvaluationAdditional.getTopStack(tStack);
					String solution = calculateRelation(num2Str, num1Str,
							cString);
					tStack.push(solution);
				}

			}
		}
		return tStack.pop();
	}

	

	/**
	 * This method performs arithmetic operations for the following without
	 * quotes "* + / - ^"
	 * 
	 * @param numStr2
	 *            input string1
	 * @param numStr1
	 *            input string2
	 * @param operator
	 *            operation supported are of the following quote
	 *            quotes"* + / - ^"
	 * @return string
	 * @throws BcException
	 */
	public static String calculateArithmetic(String numStr2, String numStr1,
			String operator) throws BcException {
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
			catchDivideByZeroException(bdNum2);
			result = bdNum1.divide(bdNum2, 0, RoundingMode.HALF_UP).toString();
			break;
		case TIMES:
			result = bdNum1.multiply(bdNum2).toString();
			break;
		case POW:
			result = bdNum1.pow(bdNum2.intValueExact(), new MathContext(FIVE))
					.toString();
			break;
		default:
			break;
		}
		return result;
	}

	/**
	 * This method checks if the divisor is zero for the division method
	 * 
	 * @param bdNum2
	 * @throws BcException
	 */
	private static void catchDivideByZeroException(BigDecimal bdNum2)
			throws BcException {
		if (bdNum2.compareTo(BigDecimal.ZERO) == 0) {
			throw new BcException("Divide by zero error");
		}
	}

	/**
	 * This method performs relation comparison two expression strings
	 * 
	 * @param numStr2
	 *            input string1
	 * @param numStr1
	 *            input string2
	 * @param operator
	 *            supported operators are of the following
	 *            "< > <= >= ! != || &&"
	 * @return
	 */
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
			result = bdNum2.compareTo(BigDecimal.ZERO) == ZERO ? "1" : "0";
			break;
		case AND_SIGN:
			result = bdNum1.compareTo(BigDecimal.ZERO) == ZERO
					|| bdNum2.compareTo(BigDecimal.ZERO) == ZERO ? "0" : "1";
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

	/**
	 * This method splits an expression string into digits, relational and
	 * arithmetic operators
	 * 
	 * @param input
	 *            input expression string
	 * @return Vector<String>
	 * @throws BcException
	 */
	public static Vector<String> splitSeparateStrings(String input)
			throws BcException {
		StringBuilder stringBuilder = new StringBuilder("");
		StringBuilder stringBuilderR = new StringBuilder("");
		Vector<String> resultVector = new Vector<String>();
		for (int i = 0; i < input.length(); i++) {
			char currChar = input.charAt(i);

			if (currChar == WHITE_SPACE) {
				continue;
			}
			if (Character.isDigit(currChar) || currChar == DOT_CHAR) {
				stringBuilder.append(currChar);
				if (stringBuilderR.length() != ZERO) {
					resultVector.add(stringBuilderR.toString());
					stringBuilderR.setLength(ZERO);
				}
			} else if (SignChecker.isOperator(currChar)
					|| currChar == OPENPAREN || currChar == CLOSEDPAREN) {
				if ((currChar == CLOSEDPAREN || currChar == OPENPAREN)
						&& stringBuilderR.length() != ZERO) {
					resultVector.add(stringBuilderR.toString());
					stringBuilderR.setLength(ZERO);
				}
				if (stringBuilderR.length() != ZERO) {
					resultVector.add(stringBuilderR.toString());
					stringBuilderR.setLength(ZERO);
				}
				if (stringBuilder.length() != ZERO) {
					resultVector.add(stringBuilder.toString());
					stringBuilder.setLength(ZERO);
				}
				resultVector.add(String.valueOf(currChar));
			} else if (SignChecker.isRelationCondtional(currChar)) {
				stringBuilderR.append(currChar);
				if (stringBuilder.length() != ZERO) {
					resultVector.add(stringBuilder.toString());
					stringBuilder.setLength(ZERO);
				}
			} else {
				throw new BcException("Unknown character detected " + currChar);
			}
		}
		if (stringBuilder.length() != ZERO) {
			resultVector.add(stringBuilder.toString());
		}
		catchRelationalOperatorException(stringBuilderR);
		return resultVector;
	}

	/**
	 * throws an exception if the relation stringbuilder is not pushed into the
	 * vector after the end of the loop. this means that no numeric string or
	 * open parenthesis follows after the relational operator
	 * 
	 * @param stringBuilderR
	 * @throws BcException
	 */
	private static void catchRelationalOperatorException(
			StringBuilder stringBuilderR) throws BcException {
		if (stringBuilderR.length() != 0) {
			throw new BcException("Incomplete Relational Operator");
		}
	}
}
