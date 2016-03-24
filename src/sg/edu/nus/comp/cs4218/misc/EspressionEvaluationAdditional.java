package sg.edu.nus.comp.cs4218.misc;

import java.util.Stack;

import sg.edu.nus.comp.cs4218.exception.BcException;

public final class EspressionEvaluationAdditional {
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
	public static void catchEmptyStackException(Stack<String> tStack) throws BcException {
		if (tStack.isEmpty()) {
			throw new BcException("Stack is empty, unable to retrieve numeric strings");
		}
	}

	/**
	 * throws an exception if the top of the stack is not a numeric string
	 * 
	 * @param num1Str
	 * @throws BcException
	 */
	public static void catchNotNumStringException(String num1Str) throws BcException {
		if (!SignChecker.isNumeric(num1Str)) {
			throw new BcException("Unable to perform operations as string is not numeric: " + num1Str);
		}
	}
}
