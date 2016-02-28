package sg.edu.nus.comp.cs4218.impl.app;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class BcApplicationTest {
	/* As derived from Bash's bc application */
	private static final String DEFAULT_ERROR = "0";
	private static final int RANDOM_LENGTH = 10000;

	private BcApplication testBc;
	private String mockNumberLeft;
	private String mockNumberRight;

	@Before
	public void setUp() {
		Random random = new Random();
		testBc = new BcApplication();
		mockNumberLeft = Float.toString(random.nextFloat());
		mockNumberRight = Integer.toString(random.nextInt(RANDOM_LENGTH) + 1);
	}

	/**
	 * Tests whether the app can parse a randomized natural number expression.
	 *
	 * @throws Exception
	 */
	@Test
	public void testNaturalNumber() throws Exception {
		String result = testBc.number(new String[] { mockNumberRight });
		assertTrue(mockNumberRight.equals(result));
	}

	/**
	 * Tests whether the app can parse a randomized floating number expression.
	 *
	 * @throws Exception
	 */
	@Test
	public void testFloatNumber() throws Exception {
		String result = testBc.number(new String[] { mockNumberLeft });
		assertTrue(mockNumberLeft.equals(result));
	}

	/**
	 * Tests whether the app can handle invalid number expressions and return
	 * expected invalid output of 0.
	 *
	 * @throws Exception
	 */
	@Test
	public void testInvalidNumber() throws Exception {
		String mockFalseNumber = "999,999";
		String result = testBc.number(new String[] { mockFalseNumber });
		assertTrue(DEFAULT_ERROR.equals(result));
	}

	/**
	 * Tests whether the app can handle a negation expression.
	 *
	 * @throws Exception
	 */
	@Test
	public void testNegate() throws Exception {
		String unaryOpr = "-";
		String result = testBc.negate(new String[] { unaryOpr, mockNumberLeft });
		assertTrue((unaryOpr + mockNumberLeft).equals(result));
	}

	/**
	 * Tests whether the app can handle a addition expression.
	 *
	 * @throws Exception
	 */
	@Test
	public void testAdd() throws Exception {
		String binOpr = "+";
		String expectedResult = Float.toString(Float.parseFloat(mockNumberLeft) + Integer.parseInt(mockNumberRight));
		String result = testBc.add(new String[] { mockNumberLeft, binOpr, mockNumberRight });
		assertTrue(expectedResult.equals(result));
	}

	/**
	 * Tests whether the app can handle a subtraction expression.
	 *
	 * @throws Exception
	 */
	@Test
	public void testSubtract() throws Exception {
		String binOpr = "-";
		String expectedResult = Float.toString(Float.parseFloat(mockNumberLeft) - Integer.parseInt(mockNumberRight));
		String result = testBc.subtract(new String[] { mockNumberLeft, binOpr, mockNumberRight });
		assertTrue(expectedResult.equals(result));
	}

	/**
	 * Tests whether the app can handle a multiplication expression.
	 *
	 * @throws Exception
	 */
	@Test
	public void testMultiply() throws Exception {
		String binOpr = "*";
		String expectedResult = Float.toString(Float.parseFloat(mockNumberLeft) * Integer.parseInt(mockNumberRight));
		String result = testBc.multiply(new String[] { mockNumberLeft, binOpr, mockNumberRight });
		assertTrue(expectedResult.equals(result));
	}

	/**
	 * Tests whether the app can handle a division expression.
	 *
	 * @throws Exception
	 */
	@Test
	public void testDivide() throws Exception {
		String binOpr = "/";
		String expectedResult = Float.toString(Float.parseFloat(mockNumberLeft) / Integer.parseInt(mockNumberRight));
		String result = testBc.divide(new String[] { mockNumberLeft, binOpr, mockNumberRight });
		assertTrue(expectedResult.equals(result));
	}

	/**
	 * Tests whether the app can handle a power expression.
	 *
	 * @throws Exception
	 */
	@Test
	public void testPow() throws Exception {
		String binOpr = "^";
		String expectedResult = Double
				.toString(Math.pow(Float.parseFloat(mockNumberLeft), Integer.parseInt(mockNumberRight)));
		String result = testBc.pow(new String[] { mockNumberLeft, binOpr, mockNumberRight });
		assertTrue(expectedResult.equals(result));
	}

	/**
	 * Tests whether the app can handle a bracketed expression.
	 *
	 * @throws Exception
	 */
	@Test
	public void testBracket() throws Exception {
		String bracketOpen = "(";
		String bracketClose = ")";
		String result = testBc.bracket(new String[] { bracketOpen, mockNumberRight, bracketClose });
		assertTrue(mockNumberRight.equals(result));
	}

	/**
	 * Tests whether the app can handle a greater than expression.
	 *
	 * @throws Exception
	 */
	@Test
	public void testGreaterThan() throws Exception {
		String binOpr = ">";
		String expectedResult = Float.parseFloat(mockNumberLeft) > Integer.parseInt(mockNumberRight) ? "1" : "0";
		String result = testBc.greaterThan(new String[] { mockNumberLeft, binOpr, mockNumberRight });
		assertTrue(expectedResult.equals(result));
	}

	/**
	 * Tests whether the app can handle a greater than or equal expression.
	 *
	 * @throws Exception
	 */
	@Test
	public void testGreaterThanOrEqual() throws Exception {
		String binOpr = ">=";
		String expectedResult = Float.parseFloat(mockNumberLeft) >= Integer.parseInt(mockNumberRight) ? "1" : "0";
		String result = testBc.greaterThanOrEqual(new String[] { mockNumberLeft, binOpr, mockNumberRight });
		assertTrue(expectedResult.equals(result));
	}

	/**
	 * Tests whether the app can handle a less than expression.
	 *
	 * @throws Exception
	 */
	@Test
	public void testLessThan() throws Exception {
		String binOpr = "<";
		String expectedResult = Float.parseFloat(mockNumberLeft) < Integer.parseInt(mockNumberRight) ? "1" : "0";
		String result = testBc.lessThan(new String[] { mockNumberLeft, binOpr, mockNumberRight });
		assertTrue(expectedResult.equals(result));
	}

	/**
	 * Tests whether the app can handle a less than or equal expression.
	 *
	 * @throws Exception
	 */
	@Test
	public void testLessThanOrEqual() throws Exception {
		String binOpr = "<=";
		String expectedResult = Float.parseFloat(mockNumberLeft) <= Integer.parseInt(mockNumberRight) ? "1" : "0";
		String result = testBc.lessThanOrEqual(new String[] { mockNumberLeft, binOpr, mockNumberRight });
		assertTrue(expectedResult.equals(result));
	}

	/**
	 * Tests whether the app can handle an equal comparison expression.
	 *
	 * @throws Exception
	 */
	@Test
	public void testEqualEqual() throws Exception {
		String binOpr = "==";
		String expectedResult = Float.parseFloat(mockNumberLeft) == Integer.parseInt(mockNumberRight) ? "1" : "0";
		String result = testBc.equalEqual(new String[] { mockNumberLeft, binOpr, mockNumberRight });
		assertTrue(expectedResult.equals(result));
	}

	/**
	 * Tests whether the app can handle a not equal comparison expression.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testNotEqual() throws Exception {
		String binOpr = "!=";
		String expectedResult = Float.parseFloat(mockNumberLeft) == Integer.parseInt(mockNumberRight) ? "0" : "1";
		String result = testBc.notEqual(new String[] { mockNumberLeft, binOpr, mockNumberRight });
		assertTrue(expectedResult.equals(result));
	}

	/**
	 * Tests whether the app can handle an AND logical expression.
	 *
	 * @throws Exception
	 */
	@Test
	public void testAnd() throws Exception {
		String binOpr = "&&";
		String expectedResult = (Float.parseFloat(mockNumberLeft) == 0) || (Integer.parseInt(mockNumberRight) == 0)
				? "0" : "1";
		String result = testBc.and(new String[] { mockNumberLeft, binOpr, mockNumberRight });
		assertTrue(expectedResult.equals(result));
	}

	/**
	 * Tests whether the app can handle an OR logical expression.
	 *
	 * @throws Exception
	 */
	@Test
	public void testOr() throws Exception {
		String binOpr = "||";
		String expectedResult = (Float.parseFloat(mockNumberLeft) == 0) && (Integer.parseInt(mockNumberRight) == 0)
				? "0" : "1";
		String result = testBc.or(new String[] { mockNumberLeft, binOpr, mockNumberRight });
		assertTrue(expectedResult.equals(result));
	}

	/**
	 * Tests whether the app can handle a NOT logical expression.
	 *
	 * @throws Exception
	 */
	@Test
	public void testNot() throws Exception {
		String unaryOpr = "!";
		String expectedResult = Integer.parseInt(mockNumberRight) == 0 ? "1" : "0";
		String result = testBc.not(new String[] { unaryOpr, mockNumberRight });
		assertTrue(expectedResult.equals(result));
	}
}