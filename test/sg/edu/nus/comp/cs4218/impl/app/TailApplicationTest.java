/**
 * 
 */
package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.Stack;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sg.edu.nus.comp.cs4218.exception.TailException;

public class TailApplicationTest {
	private static TailApplication tailApplication;
	private static final String NEW_LINE = System.lineSeparator();
	private static ByteArrayOutputStream baos;
	private static ByteArrayInputStream bis;
	private static String fileToRead = "examples/sample.txt";
	private static String fileToReadEmpty = "examples/sampleEmpty.txt";

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		tailApplication = new TailApplication();
	}

	@Before
	public void setUp() throws Exception {
		baos = new ByteArrayOutputStream();
		bis = new ByteArrayInputStream("This is a test string".getBytes());
	}

	/**
	 * Test if tail extraction is done for a normal case
	 *
	 * @throws TailException
	 */
	@Test
	public final void testExtractTail() throws TailException {
		String[] arguments = { "-n", "1", fileToRead };
		tailApplication.run(arguments, bis, baos);
		String result = new String(baos.toByteArray());
		assertEquals(
				"Apartments frequently or motionless on reasonable projecting expression. Way mrs end gave tall walk fact bed."
						+ NEW_LINE,
				result);
	}

	/**
	 * Test if tail extraction is done accurately if specified number of lines is 
	 * more than that in the file
	 *
	 * @throws TailException
	 */
	@Test
	public final void testLessThanNLines() throws TailException {
		int numLines = 3;
		Stack<String> textToExtractFrom = new Stack<String>();
		textToExtractFrom.push("This is the first line.");
		textToExtractFrom.push("This is the second line.");
		LinkedList<String> result = tailApplication.extractTail(textToExtractFrom, numLines);

		assertEquals(result.size(), 2);
		assertEquals(result.get(0), "This is the first line.");
		assertEquals(result.get(1), "This is the second line.");
	}

	/**
	 * Test if exception is thrown for non numeric number of lines
	 *
	 * @throws TailException
	 */
	@Test
	public void testNonNumericNumLines() throws TailException {
		exception.expect(TailException.class);
		exception.expectMessage("Number of lines not a number");

		String[] arguments = { "-n", "ad3", fileToRead };
		tailApplication.run(arguments, null, baos);
	}

	/**
	 * Test if exception is thrown for wrong flag used to denote number of lines i.e. not equal to -n
	 *
	 * @throws TailException
	 */
	@Test
	public void testIncorrectFlagFileSpecified() throws TailException {
		exception.expect(TailException.class);
		exception.expectMessage("Incorrect flag used to denote number of lines to print");

		String[] arguments = { "-a", "2", fileToRead };
		tailApplication.run(arguments, null, baos);
	}

	/**
	 * Test if text can be read from stdin
	 *
	 * @throws TailException
	 */
	@Test
	public void testReadFromStdIn() throws TailException {
		String[] arguments = { "-n", "1" };
		tailApplication.run(arguments, bis, baos);
		String result = new String(baos.toByteArray());
		String[] resultLines = result.split(NEW_LINE);
		assertEquals("This is a test string", resultLines[resultLines.length - 1]);
	}

	/**
	 * Test for number of lines to be 0 i.e. return empty string
	 *
	 * @throws TailException
	 */
	@Test
	public void testToReadZeroLines() throws TailException {
		String[] arguments = { "-n", "0" };
		tailApplication.run(arguments, bis, baos);
		String result = new String(baos.toByteArray());
		assertEquals("", result);
	}

	/**
	 * Test if tail can work on empty text body
	 *
	 * @throws TailException
	 */
	@Test
	public void testEmptyText() throws TailException {
		String[] arguments = { "-n", "0", fileToReadEmpty };
		tailApplication.run(arguments, null, baos);
		String result = new String(baos.toByteArray());
		assertEquals("", result);
	}

	/**
	 * Test if exception is thrown in the event stdout is null
	 *
	 * @throws TailException
	 */
	@Test
	public void testNullStdOutput() throws TailException {
		exception.expect(TailException.class);
		exception.expectMessage("Null pointer exception - stdout is not defined");

		String[] arguments = { "-n", "0" };
		tailApplication.run(arguments, bis, null);
	}

	/**
	 * Test if exception is thrown for incorrect number of arguments
	 *
	 * @throws TailException
	 */
	@Test
	public void testIncorrectNumArguments() throws TailException {
		exception.expect(TailException.class);
		exception.expectMessage("Incorrect number of arguments");

		String[] arguments = { "-a", "2", fileToRead, "fourth arg" };
		tailApplication.run(arguments, null, baos);
	}

	/**
	 * Test if TailApplication processes zero number of lines correctly
	 *
	 * @throws TailException
	 */
	@Test
	public void testZeroNumLines() throws TailException {
		String[] arguments = { "-n", "0", fileToRead };
		tailApplication.run(arguments, null, baos);
		String resultString = new String(baos.toByteArray());

		assertEquals("", resultString);
	}

	/**
	 * Test if exception is thrown if number of lines specified is negative
	 *
	 * @throws TailException
	 */
	@Test
	public void testNegativeNumLines() throws TailException {
		exception.expect(TailException.class);
		exception.expectMessage("Number of lines should be at least 0");

		String[] arguments = { "-n", "-5", fileToRead };
		tailApplication.run(arguments, null, baos);
	}

	/**
	 * Test if exception is thrown if stdin is null and file not specified
	 *
	 * @throws TailException
	 */
	@Test
	public void testNullStdIn() throws TailException {
		exception.expect(TailException.class);
		exception.expectMessage("Null pointer exception - stdin is not defined");

		tailApplication.run(null, null, baos);
	}

	/**
	 * Test for zero but not null arguments
	 *
	 * @throws TailException
	 */
	@Test
	public void testZeroArg() throws TailException {
		exception.expect(TailException.class);
		exception.expectMessage("Null pointer exception - stdin is not defined");
		String[] arguments = {};
		tailApplication.run(arguments, null, baos);
	}

	/**
	 * Test for one argumenet with options only
	 *
	 * @throws TailException
	 */
	@Test
	public void testOneArgWithOptionsOnly() throws TailException {
		exception.expect(TailException.class);
		exception.expectMessage("Null pointer exception - stdin is not defined");
		String[] arguments = { "-n", "20" };
		tailApplication.run(arguments, null, baos);
	}

	/**
	 * Test for one argument with file specified only
	 *
	 * @throws TailException
	 */
	@Test
	public void testOneArgWithFileOnly() throws TailException {
		String[] arguments = { fileToRead };
		tailApplication.run(arguments, null, baos);
		String result = new String(baos.toByteArray());
		String[] resultLines = result.split(NEW_LINE);
		assertEquals(
				"Apartments frequently or motionless on reasonable projecting expression. Way mrs end gave tall walk fact bed.",
				resultLines[resultLines.length - 1]);
	}

	@After
	public void tearDown() throws Exception {
		baos = null;
		bis = null;
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		tailApplication = null;
	}

}
