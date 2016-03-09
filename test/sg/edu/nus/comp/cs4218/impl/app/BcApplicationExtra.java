package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.BcException;

public class BcApplicationExtra {
	private BcApplication bcApp;
	private OutputStream outStream;

	@Before
	public void setUp() throws Exception {
		bcApp = new BcApplication();
		outStream = new ByteArrayOutputStream();
	}

	@After
	public void tearDown() throws Exception {
		outStream.close();
	}

	@Test
	public void testRunAddition() throws BcException {
		String[] args = {"1+2"};
		bcApp.run(args, null, outStream);
		String expected = "3\n";
		assertEquals(expected, outStream.toString());
	}

	@Test
	public void testRunUnnestedBrackets() throws BcException {
		String[] args = {"1+(2+3)"};
		bcApp.run(args, null, outStream);
		String expected = "6\n";
		assertEquals(expected, outStream.toString());
	}

	@Test
	public void testRunNestedBrackets() throws BcException {
		String[] args = {"5*(1+(2+3))"};
		bcApp.run(args, null, outStream);
		String expected = "30\n";
		assertEquals(expected, outStream.toString());
	}

	@Test
	public void testRunInvalidExpressions() {
		String[] args = {"4*(5/(3+2)"};
		try {
			bcApp.run(args, null, outStream);
		} catch (BcException e) {

		} catch (Exception e) {
			fail("BcApplication should throw BcException!");
		}
	}

	@Test
	public void testNumber() {
		String[] args = {"1"};
		String result = bcApp.number(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	@Test
	public void testNegatePositive() {
		String[] args = {"123"};
		String result = bcApp.negate(args);
		String expected = "-123";

		assertEquals(expected, result);
	}

	@Test
	public void testNegateNegative() {
		String[] args = {"-123"};
		String result = bcApp.negate(args);
		String expected = "123";

		assertEquals(expected, result);
	}

	@Test
	public void testAdd() {
		String[] args = {"1", "2"};
		String result = bcApp.add(args);
		String expected = "3";

		assertEquals(expected, result);
	}

	@Test
	public void testSubtract() {
		String[] args = {"1", "2"};
		String result = bcApp.subtract(args);
		String expected = "-1";

		assertEquals(expected, result);
	}

	@Test
	public void testMultiply() {
		String[] args = {"1", "2"};
		String result = bcApp.multiply(args);
		String expected = "2";

		assertEquals(expected, result);
	}

	@Test
	public void testDivide() {
		String[] args = {"1", "2"};
		String result = bcApp.divide(args);
		String expected = "0";

		assertEquals(expected, result);
	}

	@Test
	public void testPow() {
		String[] args = {"2", "3"};
		String result = bcApp.pow(args);
		String expected = "8";

		assertEquals(expected, result);
	}

	@Test
	public void testBracket() {
		String[] args = {"(1)"};
		String result = bcApp.bracket(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	@Test
	public void testGreaterThanTrue() {
		String[] args = {"2", "1"};
		String result = bcApp.greaterThan(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	@Test
	public void testGreaterThanTFalse() {
		String[] args = {"1", "2"};
		String result = bcApp.greaterThan(args);
		String expected = "0";

		assertEquals(expected, result);
	}

	@Test
	public void testGreaterThanOrEqualTrue1() {
		String[] args = {"2", "1"};
		String result = bcApp.greaterThanOrEqual(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	@Test
	public void testGreaterThanOrEqualTrue2() {
		String[] args = {"1", "1"};
		String result = bcApp.greaterThanOrEqual(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	@Test
	public void testGreaterThanOrEqualFalse() {
		String[] args = {"1", "2"};
		String result = bcApp.greaterThanOrEqual(args);
		String expected = "0";

		assertEquals(expected, result);
	}

	@Test
	public void testLessThanTrue() {
		String[] args = {"1", "2"};
		String result = bcApp.lessThan(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	@Test
	public void testLessThanFalse() {
		String[] args = {"2", "1"};
		String result = bcApp.lessThan(args);
		String expected = "0";

		assertEquals(expected, result);
	}

	@Test
	public void testLessThanOrEqualTrue1() {
		String[] args = {"1", "2"};
		String result = bcApp.lessThanOrEqual(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	@Test
	public void testLessThanOrEqualTrue2() {
		String[] args = {"1", "1"};
		String result = bcApp.lessThanOrEqual(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	@Test
	public void testLessThanOrEqualFalse() {
		String[] args = {"2", "1"};
		String result = bcApp.lessThanOrEqual(args);
		String expected = "0";

		assertEquals(expected, result);
	}

	@Test
	public void testEqualEqualTrue() {
		String[] args = {"2", "2"};
		String result = bcApp.equalEqual(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	@Test
	public void testEqualEqualFalse() {
		String[] args = {"1", "2"};
		String result = bcApp.equalEqual(args);
		String expected = "0";

		assertEquals(expected, result);
	}

	@Test
	public void testNotEqualTrue() {
		String[] args = {"1", "2"};
		String result = bcApp.notEqual(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	@Test
	public void testNotEqualFalse() {
		String[] args = {"2", "2"};
		String result = bcApp.notEqual(args);
		String expected = "0";

		assertEquals(expected, result);
	}

	@Test
	public void testAndTT() {
		String[] args = {"123", "123"};
		String result = bcApp.and(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	@Test
	public void testAndTF() {
		String[] args = {"123", "0"};
		String result = bcApp.and(args);
		String expected = "0";

		assertEquals(expected, result);
	}

	@Test
	public void testAndFT() {
		String[] args = {"0", "123"};
		String result = bcApp.and(args);
		String expected = "0";

		assertEquals(expected, result);
	}

	@Test
	public void testAndFF() {
		String[] args = {"0", "0"};
		String result = bcApp.and(args);
		String expected = "0";

		assertEquals(expected, result);
	}

	@Test
	public void testOrTT() {
		String[] args = {"123", "123"};
		String result = bcApp.or(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	@Test
	public void testOrTF() {
		String[] args = {"123", "0"};
		String result = bcApp.or(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	@Test
	public void testOrFT() {
		String[] args = {"0", "123"};
		String result = bcApp.or(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	@Test
	public void testOrFF() {
		String[] args = {"0", "0"};
		String result = bcApp.or(args);
		String expected = "0";

		assertEquals(expected, result);
	}

	@Test
	public void testNotF() {
		String[] args = {"0"};
		String result = bcApp.not(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	@Test
	public void testNotT() {
		String[] args = {"123"};
		String result = bcApp.not(args);
		String expected = "0";

		assertEquals(expected, result);
	}
}
