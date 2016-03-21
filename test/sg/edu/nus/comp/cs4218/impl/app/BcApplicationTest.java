package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.BcException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

/**
 * These are the tests given from other users meant for TDD purposes. More tests are also added
 * below to further improve the test for Bc.
 */
public class BcApplicationTest {
	private BcApplication bcApp;
	private OutputStream outStream;
	public final static String NEW_LINE = System.lineSeparator();

	@Before
	public void setUp() throws Exception {
		bcApp = new BcApplication();
		outStream = new ByteArrayOutputStream();
	}

	@After
	public void tearDown() throws Exception {
		outStream.close();
	}

	/**
	 * Test the additions of integer
	 * 
	 * @throws BcException
	 */
	@Test
	public void testRunAdditionInteger() throws BcException {
		String[] args = { "-1 + 4" };
		bcApp.run(args, null, outStream);
		String expected = "3" + NEW_LINE;
		assertEquals(expected, outStream.toString());
	}

	/**
	 * Test the additon of integers with parenthesis
	 * 
	 * @throws BcException
	 */
	@Test
	public void testRunIntegerWithParenthesis() throws BcException {
		String[] args = { "5-(50)" };
		bcApp.run(args, null, outStream);
		String expected = "-45" + NEW_LINE;
		assertEquals(expected, outStream.toString());
	}

	/**
	 * Test the additions of floats
	 * 
	 * @throws BcException
	 */
	@Test
	public void testRunAdditionFloats() throws BcException {
		String[] args = { "1.1+2.3" };
		bcApp.run(args, null, outStream);
		String expected = "3.4" + NEW_LINE;
		assertEquals(expected, outStream.toString());
	}

	/**
	 * Test the additions of Integers and floats
	 * 
	 * @throws BcException
	 */
	@Test
	public void testRunAdditionIntegersFloats() throws BcException {
		String[] args = { "1+2.3" };
		bcApp.run(args, null, outStream);
		String expected = "3.3" + NEW_LINE;
		assertEquals(expected, outStream.toString());
	}

	/**
	 * test the addition expression with unnested brackets
	 * 
	 * @throws BcException
	 */
	@Test
	public void testRunUnnestedBrackets() throws BcException {
		String[] args = { "1+(2+3)" };
		bcApp.run(args, null, outStream);
		String expected = "6" + NEW_LINE;
		assertEquals(expected, outStream.toString());
	}

	/**
	 * test the addition expression with nested brackets within another brackets
	 * 
	 * @throws BcException
	 */
	@Test
	public void testRunNestedBrackets() throws BcException {
		String[] args = { "5*(1+(2+3))" };
		bcApp.run(args, null, outStream);
		String expected = "30" + NEW_LINE;
		assertEquals(expected, outStream.toString());
	}

	/**
	 * Test for missing closed brackets
	 * 
	 * @throws BcException
	 */
	@Test(expected = BcException.class)
	public void testRunInvalidExpressionsMissingClosedBrackets()
			throws BcException {
		String[] args = { "4*(5/(3+2)" };
		bcApp.run(args, null, outStream);

	}

	/**
	 * Test for misplaced brackets whereby the closed brackets appear before the
	 * open brackets
	 * 
	 * @throws BcException
	 */
	@Test(expected = BcException.class)
	public void testRunInvalidExpressionsMisplacedBrackets() throws BcException {
		String[] args = { "4 * )(1+8" };
		bcApp.run(args, null, outStream);

	}

	/**
	 * test for missing open brackets
	 * 
	 * @throws BcException
	 */
	@Test(expected = BcException.class)
	public void testRunInvalidExpressionsMissingOpenBrackets()
			throws BcException {
		String[] args = { "4 * 1+8)" };
		bcApp.run(args, null, outStream);

	}

	/**
	 * test if a float string is a number
	 * 
	 */
	@Test
	public void testFloatNumber() {
		String[] args = { "1.285613898" };
		String result = bcApp.number(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	/**
	 * Test if an integer string is a number
	 */
	@Test
	public void testNumber() {
		String[] args = { "10000" };
		String result = bcApp.number(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	/**
	 * Test if a string of alphabets is not a number
	 */
	@Test
	public void testString() {
		String[] args = { "abc" };
		String result = bcApp.number(args);
		String expected = "0";

		assertEquals(expected, result);
	}

	/**
	 * Negation of a postive number string to negative
	 */
	@Test
	public void testNegatePositive() {
		String[] args = { "-(1234)" };
		String result = bcApp.negate(args);
		String expected = "-1234";

		assertEquals(expected, result);
	}

	/**
	 * Negation of a negative number string to positive
	 */
	@Test
	public void testNegateNegative() {
		String[] args = { "-(-1235)" };
		String result = bcApp.negate(args);
		String expected = "1235";

		assertEquals(expected, result);
	}

	/**
	 * Test the addition of an expresion
	 */
	@Test
	public void testAdd() {
		String[] args = { "1+2" };
		String result = bcApp.add(args);
		String expected = "3";

		assertEquals(expected, result);
	}

	/**
	 * test the subtraction of an expression
	 */
	@Test
	public void testSubtract() {
		String[] args = { "1-12" };
		String result = bcApp.subtract(args);
		String expected = "-11";

		assertEquals(expected, result);
	}

	/**
	 * Test the multiplication of an integer and a float
	 */
	@Test
	public void testMultiplyIntegerFloat() {
		String[] args = { "1*22.2" };
		String result = bcApp.multiply(args);
		String expected = "22.2";

		assertEquals(expected, result);
	}

	/**
	 * Test the multiplication of Integers
	 */
	@Test
	public void testMultiplyIntegers() {
		String[] args = { "1*22" };
		String result = bcApp.multiply(args);
		String expected = "22";

		assertEquals(expected, result);
	}

	/**
	 * Test the multiplication of Floats
	 */
	@Test
	public void testMultipyFloats() {
		String[] args = { "1.2* 3.3" };
		String result = bcApp.multiply(args);
		String expected = "3.96";

		assertEquals(expected, result);
	}

	/**
	 * Test the division of zero
	 * 
	 * @throws BcException
	 */
	@Test(expected = BcException.class)
	public void testRunDivideZerp() throws BcException {
		String[] args = { "1/0" };
		bcApp.run(args, null, outStream);
	}

	/**
	 * Test the division of integers
	 */
	@Test
	public void testDivideIntegers() {
		String[] args = { "53/52" };
		String result = bcApp.divide(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	/**
	 * Test the division of integer and float
	 */
	@Test
	public void testDivideIntegerFloat() {
		String[] args = { "83/5.5" };
		String result = bcApp.divide(args);
		String expected = "15";

		assertEquals(expected, result);
	}

	/**
	 * Test the division of floats
	 */
	@Test
	public void testDivideFloats() {
		String[] args = { "1322.5/40.8" };
		String result = bcApp.divide(args);
		String expected = "32";

		assertEquals(expected, result);
	}

	/**
	 * test the power expression
	 */
	@Test
	public void testPow() {
		String[] args = { "3.3^3" };
		String result = bcApp.pow(args);
		String expected = "35.937";
		assertEquals(expected, result);
	}

	/**
	 * test the elimination of brackets
	 */
	@Test
	public void testBracket() {
		String[] args = { "(15)" };
		String result = bcApp.bracket(args);
		String expected = "15";

		assertEquals(expected, result);
	}

	/**
	 * return true if the number is greater than the other number for the
	 * GREATER THAN Relation
	 */
	@Test
	public void testGreaterThanTrue() {
		String[] args = { "2 > 1" };
		String result = bcApp.greaterThan(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	/**
	 * return false if the number is not greater than the other number for the
	 * GREATER THAN Relation
	 */
	@Test
	public void testGreaterThanTFalse() {
		String[] args = { "1  > 2" };
		String result = bcApp.greaterThan(args);
		String expected = "0";

		assertEquals(expected, result);
	}

	/**
	 * return true if the number is clearly greater than or equal to the other
	 * number for the GREATER THAN OR EQUAL Relation
	 */
	@Test
	public void testGreaterThanOrEqualTrue1() {
		String[] args = { "2 >= 1" };
		String result = bcApp.greaterThanOrEqual(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	/**
	 * return true if the number is greater than or Clearly equal to the other
	 * number for the GREATER THAN OR EQUAL Relation
	 * 
	 */
	@Test
	public void testGreaterThanOrEqualTrue2() {
		String[] args = { "1 >= 1" };
		String result = bcApp.greaterThanOrEqual(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	/**
	 * return false if the number is clearly not greater than or equal to the
	 * other number for the GREATER THAN OR EQUAL Relation
	 */
	@Test
	public void testGreaterThanOrEqualFalse() {
		String[] args = { "1 >= 222" };
		String result = bcApp.greaterThanOrEqual(args);
		String expected = "0";

		assertEquals(expected, result);
	}

	/**
	 * return true if a number is less than the other number for the LESS THAN
	 * Relation
	 */
	@Test
	public void testLessThanTrue() {
		String[] args = { "1 < 142" };
		String result = bcApp.lessThan(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	/**
	 * return false if a number is not less than the other number for the LESS
	 * THAN Relation
	 */
	@Test
	public void testLessThanFalse() {
		String[] args = { "2 < 1" };
		String result = bcApp.lessThan(args);
		String expected = "0";

		assertEquals(expected, result);
	}

	/**
	 * return true if a number is Clearly less than or equal to the other number
	 * for the LESS THAN OR EQUAL Relation
	 */
	@Test
	public void testLessThanOrEqualTrue1() {
		String[] args = { "1 <= 42" };
		String result = bcApp.lessThanOrEqual(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	/**
	 * return true if a number is less than or Clearly equal to the other number
	 * for the LESS THAN OR EQUAL Relation
	 */
	@Test
	public void testLessThanOrEqualTrue2() {
		String[] args = { "1 <= 1" };
		String result = bcApp.lessThanOrEqual(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	/**
	 * return false if a number is clearly not less than or equal to the other
	 * number for the LESS THAN OR EQUAL Relation
	 */
	@Test
	public void testLessThanOrEqualFalse() {
		String[] args = { "2 <= 1" };
		String result = bcApp.lessThanOrEqual(args);
		String expected = "0";

		assertEquals(expected, result);
	}

	/**
	 * return true if both number matches each other for the EQUAL EQUAL
	 * Relation
	 */
	@Test
	public void testEqualEqualTrue() {
		String[] args = { "2 == 2" };
		String result = bcApp.equalEqual(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	/**
	 * returns false if both number do not match each other for the EQUAL EQUAL
	 * Relation
	 */
	@Test
	public void testEqualEqualFalse() {
		String[] args = { "1 == 2" };
		String result = bcApp.equalEqual(args);
		String expected = "0";

		assertEquals(expected, result);
	}

	/**
	 * return true if both numbers are not equal to each other for the NOT EQUAL
	 * Relation
	 */
	@Test
	public void testNotEqualTrue() {
		String[] args = { "1 != 2" };
		String result = bcApp.notEqual(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	/**
	 * return false if both number are equal to each other for the NOT EQUAL
	 * Relation
	 */
	@Test
	public void testNotEqualFalse() {
		String[] args = { "2 != 2" };
		String result = bcApp.notEqual(args);
		String expected = "0";

		assertEquals(expected, result);
	}

	/**
	 * return true if both numbers are not '0' in either LHS or RHS for the AND
	 * relation
	 */
	@Test
	public void testAndTT() {
		String[] args = { "-1&&-1" };
		String result = bcApp.and(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	/**
	 * return false if there is at least a '0' present in RHS for the AND
	 * relation
	 */
	@Test
	public void testAndTF() {
		String[] args = { "123 && 0" };
		String result = bcApp.and(args);
		String expected = "0";

		assertEquals(expected, result);
	}

	/**
	 * return false if there is at least a '0' present in LHS for the AND
	 * relation
	 */
	@Test
	public void testAndFT() {
		String[] args = { "0&&123" };
		String result = bcApp.and(args);
		String expected = "0";

		assertEquals(expected, result);
	}

	/**
	 * return false if there are '0's present in both RHS and LHS for the AND
	 * relation
	 */
	@Test
	public void testAndFF() {
		String[] args = { "0 && 0" };
		String result = bcApp.and(args);
		String expected = "0";

		assertEquals(expected, result);
	}

	/**
	 * return false if there are no '0's present in both RHS and LHS for the OR
	 * relation
	 */
	@Test
	public void testOrTT() {
		String[] args = { "12356 || 12356" };
		String result = bcApp.orMethod(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	/**
	 * return false if there is a '0' present in the RHS for the OR relation
	 */
	@Test
	public void testOrTF() {
		String[] args = { "123 || 0" };
		String result = bcApp.orMethod(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	/**
	 * return false if there is a '0' present in the LHS for the OR relation
	 */
	@Test
	public void testOrFT() {
		String[] args = { "0 || 123" };
		String result = bcApp.orMethod(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	/**
	 * return false if there are no '0's present in both RHS and LHS for the OR
	 * relation
	 */
	@Test
	public void testOrFF() {
		String[] args = { "0 || 0" };
		String result = bcApp.orMethod(args);
		String expected = "0";

		assertEquals(expected, result);
	}

	/**
	 * return true if the expr is 0 for the NOT relation
	 */
	@Test
	public void testNotF() {
		String[] args = { "!0" };
		String result = bcApp.not(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	/**
	 * return false if the expr is not 0 for the NOT relation
	 */
	@Test
	public void testNotT() {
		String[] args = { "!123" };
		String result = bcApp.not(args);
		String expected = "0";

		assertEquals(expected, result);
	}

	/**
	 * Test the negate/unary minus at the start of an expression
	 * 
	 * @throws BcException
	 */
	@Test
	public void testRunNegateAtStart() throws BcException {
		String[] args = { "-5" };
		bcApp.run(args, null, outStream);
		String expected = "-5" + NEW_LINE;
		assertEquals(expected, outStream.toString());
	}

	/**
	 * Test the negate/unary minus outside of the parenthesis
	 * 
	 * @throws BcException
	 */
	@Test
	public void testRunNegateBeforeParenthesis() throws BcException {
		String[] args = { "5+ -(50)" };
		bcApp.run(args, null, outStream);
		String expected = "-45" + NEW_LINE;
		assertEquals(expected, outStream.toString());
	}

	/**
	 * Test the negate/unary minus outside and inside of the parenthesis
	 * 
	 * @throws BcException
	 */
	@Test
	public void testRunNegateBeforeAfterParenthesis() throws BcException {
		String[] args = { "-(-50)" };
		bcApp.run(args, null, outStream);
		String expected = "50" + NEW_LINE;
		assertEquals(expected, outStream.toString());
	}

	/**
	 * Test the negate/unary minus after a relational operator
	 * 
	 * @throws BcException
	 */
	@Test
	public void testRunNegateAfterRelational() throws BcException {
		String[] args = { "2 == -2" };
		bcApp.run(args, null, outStream);
		String expected = "0" + NEW_LINE;
		assertEquals(expected, outStream.toString());
	}

	/**
	 * throws an expression if a numeric string/open parenthesis does not follow
	 * after a relational operator
	 * 
	 * @throws BcException
	 */
	@Test(expected = BcException.class)
	public void testRunInvalidExpressionEquals() throws BcException {
		String[] args = { "5  =" };
		bcApp.run(args, null, outStream);
	}

	/**throws an expression if an expression is invalid
	 * 
	 * @throws BcException
	 */
	@Test(expected = BcException.class)
	public void testRunInvalidExpressionPlusPlus() throws BcException {
		String[] args = {"2 ++ -6 "};
		bcApp.run(args, null, outStream);
	}

	/**
	 * Test if two expression are equal equal to each other through the shell
	 * 
	 * @throws AbstractApplicationException
	 * @throws ShellException
	 */
	@Test
	public void testFromShell() throws AbstractApplicationException,
			ShellException {
		String temp = "bc \"5==5\" ";
		String expected = "1" + NEW_LINE;
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		ShellImpl shell = new ShellImpl();
		shell.parseAndEvaluate(temp, stdout);
		assertEquals(expected, stdout.toString());
	}

	/**
	 * Test if the first expression is less than the second expression
	 * 
	 * @throws AbstractApplicationException
	 * @throws ShellException
	 */
	@Test
	public void testFromShell2() throws AbstractApplicationException,
			ShellException {
		String temp = "echo \"5 < 2\" | bc";
		String expected = "0" + NEW_LINE;
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		ShellImpl shell = new ShellImpl();
		shell.parseAndEvaluate(temp, stdout);
		assertEquals(expected, stdout.toString());
	}

	/**
	 * Test the evaluation for the expression containing unary minus, multiply
	 * and divide. Note associativity is important here for unary operators
	 * 
	 * @throws AbstractApplicationException
	 * @throws ShellException
	 */
	@Test
	public void testFromShell3() throws AbstractApplicationException,
			ShellException {
		String temp = "echo \"5/-1*-2\" | bc";
		String expected = "10" + NEW_LINE;
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		ShellImpl shell = new ShellImpl();
		shell.parseAndEvaluate(temp, stdout);
		assertEquals(expected, stdout.toString());
	}

	/**
	 * Test the evaluation for the expression containing unary minus and pow
	 * where the base number is negative Note associativity is important here
	 * for unary operators
	 * 
	 * @throws AbstractApplicationException
	 * @throws ShellException
	 */
	@Test
	public void testFromShell4() throws AbstractApplicationException,
			ShellException {
		String temp = "echo \"-2 ^5\" | bc";
		String expected = "-32" + NEW_LINE;
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		ShellImpl shell = new ShellImpl();
		shell.parseAndEvaluate(temp, stdout);
		assertEquals(expected, stdout.toString());
	}

	/**
	 * Test the evaluation for the expression containing unary minus and pow
	 * where the exponent if of negative number. Note associativity is important
	 * here for unary operators
	 * 
	 * @throws AbstractApplicationException
	 * @throws ShellException
	 */
	@Test
	public void testFromShell5() throws AbstractApplicationException,
			ShellException {
		String temp = "echo \"2 ^ -5\" | bc";
		String expected = "0.03125" + NEW_LINE;
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		ShellImpl shell = new ShellImpl();
		shell.parseAndEvaluate(temp, stdout);
		assertEquals(expected, stdout.toString());

	}
}