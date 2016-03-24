package sg.edu.nus.comp.cs4218.impl.app;
/***
 * This class is test for EchoApplication
 * Application takes in user input in the argument
 * And add new line after each arg
 * Then present in the output stream.
 */

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.EchoException;

public class EchoApplicationTest {

	static EchoApplication eApp;
	static String[] args;
	OutputStream output;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		eApp = new EchoApplication();
	}

	// Case 1: Where arg is null - No input to write
	@Test(expected = EchoException.class)
	public void testRun() throws EchoException {
		output = System.out;
		eApp.run(null, null, output);
	}

	// Case 2: Stdout is null - nowhere to output
	@Test(expected = EchoException.class)
	public void testRun2() throws EchoException {
		args = new String[1];
		args[0] = "test";
		eApp.run(args, null, null);
	}

	// Case 3: Stdout is null - nowhere to output
	@Test
	public void testRun3() throws EchoException {
		args = new String[1];
		args[0] = "test";
		output = new ByteArrayOutputStream();
		eApp.run(args, null, output);
		assertEquals(output.toString(), args[0] + System.lineSeparator());
	}

	// Case 4: empty string in args
	@Test
	public void testRun4() throws EchoException {
		args = new String[1];
		args[0] = "";
		output = new ByteArrayOutputStream();
		eApp.run(args, null, output);
		assertEquals(output.toString(), System.lineSeparator() + System.lineSeparator());
	}

	// Case 5:
	// Excepted output: This is a trap.
	@Test
	public void testRun5() throws EchoException {
		args = new String[1];
		args[0] = "This is a trap.";
		output = new ByteArrayOutputStream();
		eApp.run(args, null, output);
		assertEquals(output.toString(), args[0] + System.lineSeparator());
	}

	// Case 6: Proper run of the system
	@Test
	public void testRun6() throws EchoException {
		String temp = "This is a trap";
		args = temp.split(" ");
		output = new ByteArrayOutputStream();

		eApp.run(args, null, output);
		assertEquals(output.toString(), "This is a trap" + System.lineSeparator());
	}

	@After
	public void tearDown() throws Exception {
		args = null;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		eApp = null;

	}

}
