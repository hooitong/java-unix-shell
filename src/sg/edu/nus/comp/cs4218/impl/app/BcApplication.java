package sg.edu.nus.comp.cs4218.impl.app;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

import sg.edu.nus.comp.cs4218.app.Bc;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.BcException;
import sg.edu.nus.comp.cs4218.misc.ExpressionEvaluator;

public class BcApplication implements Bc {
	public final static String NEGATE = "-";
	public final static String EQUALSTR = "=";
	public final static String GREATERSTR = ">";
	public final static String LESSSTR = "<";
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

	@Override
	public String number(String[] args) {
		return null;
	}

	@Override
	public String negate(String[] args) {
		return null;
	}

	@Override
	public String add(String[] args) {
		return null;
	}

	@Override
	public String subtract(String[] args) {
		return null;
	}

	@Override
	public String multiply(String[] args) {
		return null;
	}

	@Override
	public String divide(String[] args) {
		return null;
	}

	@Override
	public String pow(String[] args) {
		return null;
	}

	@Override
	public String bracket(String[] args) {
		return null;
	}

	@Override
	public String greaterThan(String[] args) {
		return null;
	}

	@Override
	public String greaterThanOrEqual(String[] args) {
		return null;
	}

	@Override
	public String lessThan(String[] args) {
		return null;
	}

	@Override
	public String lessThanOrEqual(String[] args) {
		return null;
	}

	@Override
	public String equalEqual(String[] args) {
		return null;
	}

	@Override
	public String notEqual(String[] args) {
		return null;
	}

	@Override
	public String and(String[] args) {
		return null;
	}

	@Override
	public String or(String[] args) {
		return null;
	}

	@Override
	public String not(String[] args) {
		return null;
	}

	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws BcException {
		String expresssion = args[0];
		String leftResult = "", rightResult = "", finalResult = "";
		ExpressionEvaluator expressionEvaluator = new ExpressionEvaluator();
		expressionEvaluator.checkIsGreaterSign(expresssion);
		expressionEvaluator.checkIsLesserSign(expresssion);
		expressionEvaluator.checkEqualSign(expresssion);
		expressionEvaluator.checkIsNotSign(expresssion);
		expressionEvaluator.checkIsAndSign(expresssion);
		expressionEvaluator.checkIsOrSign(expresssion);
		expressionEvaluator.separateExpressions(expresssion);
		Vector<String> vVector1 = expressionEvaluator.infixToPostfix(expressionEvaluator.leftExp);
		finalResult = leftResult = expressionEvaluator.computeResult(vVector1);

		if(!expressionEvaluator.rightExp.isEmpty()){
			Vector<String> vVector2 = expressionEvaluator.infixToPostfix(expressionEvaluator.rightExp);
			rightResult = expressionEvaluator.computeResult(vVector2);
			if(expressionEvaluator.isEqualEqualPresent){
				finalResult = expressionEvaluator.isEqualEqual(leftResult, rightResult) ? "1" : "0";
			}else if(expressionEvaluator.isGreaterPresent && expressionEvaluator.isEqualPresent){
				finalResult = expressionEvaluator.isGreaterThanEqual(leftResult, rightResult) ? "1" : "0";
			}else if(expressionEvaluator.isGreaterPresent){
				finalResult = expressionEvaluator.isGreaterThan(leftResult, rightResult) ? "1" : "0";
			}else if(expressionEvaluator.isLesserPresent && expressionEvaluator.isEqualPresent){
				finalResult = expressionEvaluator.isLessThanEqual(leftResult, rightResult) ? "1" : "0";
			}else if(expressionEvaluator.isLesserPresent){
				finalResult = expressionEvaluator.isLessThan(leftResult, rightResult) ? "1" : "0";
			}else if(expressionEvaluator.isNotPresent && expressionEvaluator.isEqualPresent){
				finalResult = expressionEvaluator.isNotEqual(leftResult, rightResult) ? "1" : "0";
			}else if(expressionEvaluator.isNotPresent){
				finalResult = expressionEvaluator.not(leftResult) ? "1" : "0";
			}else if(expressionEvaluator.isAndPresent){
				finalResult = expressionEvaluator.and(leftResult, rightResult) ? "1" : "0";
			}else if(expressionEvaluator.isOrPresent){
				boolean test = expressionEvaluator.or(leftResult, rightResult);
				finalResult = expressionEvaluator.or(leftResult, rightResult) ? "1" : "0";
			}
		}

		System.out.println(finalResult);
	}
}
