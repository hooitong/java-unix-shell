package sg.edu.nus.comp.cs4218.impl.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import sg.edu.nus.comp.cs4218.app.Bc;
import sg.edu.nus.comp.cs4218.exception.BcException;
import sg.edu.nus.comp.cs4218.misc.ExpressionEvaluator;
import sg.edu.nus.comp.cs4218.misc.SignChecker;

public class BcApplication implements Bc {
	private final static int ZERO = 0;
	private static final String CHARSET_UTF_8 = "UTF-8";
	/**
	 * Returns resultant string with expression of the form <number>, where
	 * number can by any natural number or floating point number, evaluated
	 */
	@Override
	public String number(String... args) {
		return SignChecker.isNumeric(args[ZERO]) ? "1" : "0";
	}
	/**
	 * Returns resultant string with expression of the form -<expression>
	 * evaluated
	 */
	@Override
	public String negate(String... args) {
		return evaluateHelper(args);
	}
	/**
	 * Returns resultant string with expression of the form <expression> +
	 * <expression> evaluated
	 */
	@Override
	public String add(String... args) {
		return evaluateHelper(args);
	}
	/**
	 * Returns resultant string with expression of the form <expression> -
	 * <expression> evaluated
	 */
	@Override
	public String subtract(String... args) {
		return evaluateHelper(args);
	}
	/**
	 * Returns resultant string with expression of the form <expression> *
	 * <expression> evaluated
	 */
	@Override
	public String multiply(String... args) {
		return evaluateHelper(args);
	}
	/**
	 * Returns resultant string with expression of the form <expression> /
	 * <expression> evaluated
	 */
	@Override
	public String divide(String... args) {
		return evaluateHelper(args);
	}
	/**
	 * Returns resultant string with expression of the form (<expression>)
	 * evaluated
	 */
	@Override
	public String pow(String... args) {
		return evaluateHelper(args);
	}
	/**
	 * Returns resultant string with expression of the form <expression> >
	 * <expression> evaluated
	 */
	@Override
	public String bracket(String... args) {
		return evaluateHelper(args);
	}
	/**
	 * Returns resultant string with expression of the form <expression> >=
	 * <expression> evaluated
	 */
	@Override
	public String greaterThan(String... args) {
		return evaluateHelper(args);
	}
	/**
	 * Returns resultant string with expression of the form <expression> >=
	 * <expression> evaluated
	 */
	@Override
	public String greaterThanOrEqual(String... args) {
		return evaluateHelper(args);
	}
	/**
	 * Returns resultant string with expression of the form <expression> <
	 * <expression> evaluated
	 */
	@Override
	public String lessThan(String... args) {
		return evaluateHelper(args);
	}
	/**
	 * Returns resultant string with expression of the form <expression> <=
	 * <expression> evaluated
	 */
	@Override
	public String lessThanOrEqual(String... args) {
		return evaluateHelper(args);
	}
	/**
	 * Returns resultant string with expression of the form <expression> ==
	 * <expression> evaluated
	 */
	@Override
	public String equalEqual(String... args) {
		return evaluateHelper(args);
	}
	/**
	 * Returns resultant string with expression of the form <expression> !=
	 * <expression> evaluated
	 */
	@Override
	public String notEqual(String... args) {
		return evaluateHelper(args);
	}
	/**
	 * Returns resultant string with expression of the form <expression> &&
	 * <expression> evaluated
	 */
	@Override
	public String and(String... args) {
		return evaluateHelper(args);
	}
	/**
	 * Returns resultant string with expression of the form <expression> ||
	 * <expression> evaluated
	 */
	@Override
	public String orMethod(String... args) {
		return evaluateHelper(args);
	}
	/**
	 * Returns resultant string with expression of the form !<expression>
	 * evaluated
	 */
	@Override
	public String not(String... args) {
		return evaluateHelper(args);
	}

	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout)
			throws BcException {
		String[] stdinArr = null;
		if (args == null || args.length == ZERO) {
			stdinArr = readFromStdinAndWriteToStringArray(stdin);
		} else if (args.length > 1) {
			throw new BcException(
					"Too many arguments, BC arguments must be wrapped with quotes");
		} else {
			stdinArr = Arrays.copyOf(args, args.length);
		}
		boolean isBracketBalanced = ExpressionEvaluator
				.isParenthesesCountSame(stdinArr[ZERO]);
		catchUnequalBracketsException(isBracketBalanced);
		String expresssion = stdinArr[ZERO];
		String finalResult = "";
		Vector<String> vVector1 = ExpressionEvaluator
				.infixToPostfix(expresssion);
		finalResult = ExpressionEvaluator.computeResult(vVector1);
		stdoutSortedArray(stdout, finalResult);
	}

	private void catchUnequalBracketsException(boolean isBracketBalanced)
			throws BcException {
		if (!isBracketBalanced) {
			throw new BcException("unequal amount of brackets");
		}
	}

	/**
	 * Write the sorted array to the output stream
	 * 
	 * @param stdout
	 *            stream to write out
	 * @param finalResult
	 *            array sorted array
	 * @throws BcException
	 */
	private void stdoutSortedArray(OutputStream stdout, String finalResult)
			throws BcException {
		if (stdout == null) {
			throw new BcException("stdout is not present");
		}

		try {
			stdout.write(finalResult.getBytes(CHARSET_UTF_8));
			stdout.write(System.lineSeparator().getBytes("UTF-8"));
		} catch (IOException e) {
			throw new BcException("Could not write to output stream", e);
		}

	}

	/**
	 * Read from stdin and write to a string array
	 * 
	 * @param stdin
	 *            An input Stream. Reading from stdin and not a file
	 * @throws BcException
	 */
	private String[] readFromStdinAndWriteToStringArray(InputStream stdin)
			throws BcException {
		List<String> resultList = new ArrayList<String>();
		if (stdin == null) {
			throw new BcException("Null Pointer Exception");
		}
		BufferedReader buffReader = new BufferedReader(new InputStreamReader(
				stdin));
		String input = "";
		try {
			while ((input = buffReader.readLine()) != null) {
				resultList.add(input);
			}
		} catch (Exception e) {
			throw new BcException("Exception caught", e);
		}
		return resultList.toArray(new String[resultList.size()]);
	}

	public static String evaluateHelper(String... args) {
		String expresssion = args[ZERO];
		String finalResult = "";
		try {
			Vector<String> vVector1 = ExpressionEvaluator
					.infixToPostfix(expresssion);
			finalResult = ExpressionEvaluator.computeResult(vVector1);
		} catch (BcException bcException) {
			finalResult = bcException.getMessage();
		}

		return finalResult;
	}
}
