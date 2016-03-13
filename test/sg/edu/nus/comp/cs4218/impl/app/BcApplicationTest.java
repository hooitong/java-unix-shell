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

	@Test
	public void testRunAddition() throws BcException {
		String[] args = { "1+2" };
		bcApp.run(args, null, outStream);
		String expected = "3" + NEW_LINE;
		assertEquals(expected, outStream.toString());
	}

	@Test
	public void testRunUnnestedBrackets() throws BcException {
		String[] args = { "1+(2+3)" };
		bcApp.run(args, null, outStream);
		String expected = "6" + NEW_LINE;
		assertEquals(expected, outStream.toString());
	}

	@Test
	public void testRunNestedBrackets() throws BcException {
		String[] args = { "5*(1+(2+3))" };
		bcApp.run(args, null, outStream);
		String expected = "30" + NEW_LINE;
		assertEquals(expected, outStream.toString());
	}

	@Test(expected = BcException.class)
	public void testRunInvalidExpressions() throws BcException {
		String[] args = { "4*(5/(3+2)" };
		bcApp.run(args, null, outStream);

	}

	@Test
	public void testFloatNumber() {
		String[] args = { "1.285613898" };
		String result = bcApp.number(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	@Test
	public void testNumber() {
		String[] args = { "10000" };
		String result = bcApp.number(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	@Test
	public void testString() {
		String[] args = { "abc" };
		String result = bcApp.number(args);
		String expected = "0";

		assertEquals(expected, result);
	}

	@Test
	public void testNegatePositive() {
		String[] args = { "1234" };
		String result = bcApp.negate(args);
		String expected = "-1234";

		assertEquals(expected, result);
	}

	@Test
	public void testNegateNegative() {
		String[] args = { "-1235" };
		String result = bcApp.negate(args);
		String expected = "1235";

		assertEquals(expected, result);
	}

	@Test
	public void testAdd() {
		String[] args = { "1+2" };
		String result = bcApp.add(args);
		String expected = "3";

		assertEquals(expected, result);
	}

	@Test
	public void testSubtract() {
		String[] args = { "1-12" };
		String result = bcApp.subtract(args);
		String expected = "-11";

		assertEquals(expected, result);
	}

	@Test
	public void testMultiply() {
		String[] args = { "1*22.2" };
		String result = bcApp.multiply(args);
		String expected = "22.2";

		assertEquals(expected, result);
	}

	@Test
	public void testDivide() {
		String[] args = { "53/52" };
		String result = bcApp.divide(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	@Test
	public void testPow() {
		String[] args = { "3.3^3" };
		String result = bcApp.pow(args);
		String expected = "35.9";
		System.out.println("--- " + result);
		assertEquals(expected, result);
	}

	@Test
	public void testBracket() {
		String[] args = { "(1)" };
		String result = bcApp.bracket(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	@Test
	public void testGreaterThanTrue() {
		String[] args = { "2 > 1" };
		String result = bcApp.greaterThan(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	@Test
	public void testGreaterThanTFalse() {
		String[] args = { "1  > 2" };
		String result = bcApp.greaterThan(args);
		String expected = "0";

		assertEquals(expected, result);
	}

	@Test
	public void testGreaterThanOrEqualTrue1() {
		String[] args = { "2 >= 1" };
		String result = bcApp.greaterThanOrEqual(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	@Test
	public void testGreaterThanOrEqualTrue2() {
		String[] args = { "1 >= 1" };
		String result = bcApp.greaterThanOrEqual(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	@Test
	public void testGreaterThanOrEqualFalse() {
		String[] args = { "1 >= 222" };
		String result = bcApp.greaterThanOrEqual(args);
		String expected = "0";

		assertEquals(expected, result);
	}

	@Test
	public void testLessThanTrue() {
		String[] args = { "1 < 142" };
		String result = bcApp.lessThan(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	@Test
	public void testLessThanFalse() {
		String[] args = { "2 < 1" };
		String result = bcApp.lessThan(args);
		String expected = "0";

		assertEquals(expected, result);
	}

	@Test
	public void testLessThanOrEqualTrue1() {
		String[] args = { "1 <= 42" };
		String result = bcApp.lessThanOrEqual(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	@Test
	public void testLessThanOrEqualTrue2() {
		String[] args = { "1 <= 1" };
		String result = bcApp.lessThanOrEqual(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	@Test
	public void testLessThanOrEqualFalse() {
		String[] args = { "2 <= 1" };
		String result = bcApp.lessThanOrEqual(args);
		String expected = "0";

		assertEquals(expected, result);
	}

	@Test
	public void testEqualEqualTrue() {
		String[] args = { "2 == 2" };
		String result = bcApp.equalEqual(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	@Test
	public void testEqualEqualFalse() {
		String[] args = { "1 == 2" };
		String result = bcApp.equalEqual(args);
		String expected = "0";

		assertEquals(expected, result);
	}

	@Test
	public void testNotEqualTrue() {
		String[] args = { "1 != 2=" };
		String result = bcApp.notEqual(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	@Test
	public void testNotEqualFalse() {
		String[] args = { "2 != 2" };
		String result = bcApp.notEqual(args);
		String expected = "0";

		assertEquals(expected, result);
	}

	@Test
	public void testAndTT() {
		String[] args = { "123&&123" };
		String result = bcApp.and(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	@Test
	public void testAndTF() {
		String[] args = { "123 && 0" };
		String result = bcApp.and(args);
		String expected = "0";

		assertEquals(expected, result);
	}

	@Test
	public void testAndFT() {
		String[] args = { "0&&123" };
		String result = bcApp.and(args);
		String expected = "0";

		assertEquals(expected, result);
	}

	@Test
	public void testAndFF() {
		String[] args = { "0 && 0" };
		String result = bcApp.and(args);
		String expected = "0";

		assertEquals(expected, result);
	}

	@Test
	public void testOrTT() {
		String[] args = { "12356 || 12356" };
		String result = bcApp.orMethod(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	@Test
	public void testOrTF() {
		String[] args = { "123 || 0" };
		String result = bcApp.orMethod(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	@Test
	public void testOrFT() {
		String[] args = { "0 || 123" };
		String result = bcApp.orMethod(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	@Test
	public void testOrFF() {
		String[] args = { "0 || 0" };
		String result = bcApp.orMethod(args);
		String expected = "0";

		assertEquals(expected, result);
	}

	@Test
	public void testNotF() {
		String[] args = { "!0" };
		String result = bcApp.not(args);
		String expected = "1";

		assertEquals(expected, result);
	}

	@Test
	public void testNotT() {
		String[] args = { "!123" };
		String result = bcApp.not(args);
		String expected = "0";

		assertEquals(expected, result);
	}

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
}
