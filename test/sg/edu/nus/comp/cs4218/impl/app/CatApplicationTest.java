
/**
 * 
 * References:
 * 
 * http://tutorials.jenkov.com/java-unit-testing/io-testing.html - io-testing
 * http://junit.sourceforge.net/javadoc/org/junit/Assert.html - assertion
 * https://docs.oracle.com/javase/tutorial/essential/io/pathOps.html -
 * information on file path
 * 
 */
package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.CatException;

public class CatApplicationTest {

	private static final String ONELINETXT = "newFile2.txt";
	private static final String TEXT_INPUT2_TXT = "textInput2.txt";
	private static final String EMPTYFILE = "newFile.txt";
	private static final String TEXT_INPUT_TXT = "textInput.txt";
	static CatApplication cApp;
	static String filePath;
	Path textFile;
	static OutputStream output;
	static InputStream input;
	static String[] args;

	/**
	 * @throws java.lang.Exception
	 */

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		cApp = new CatApplication();
	}

	/**
	 * @throws java.lang.Exception
	 */

	@Before
	public void setUp() throws Exception {
		filePath = "examples/";
	}

	/**
	 * Case 0A: No output stream Input: valid args (contain one usable item),
	 * null inputstream, null output Output: throws CatException
	 * 
	 */

	@Test(expected = CatException.class)
	public void runCAppWithStdin0A() throws CatException {
		args = new String[1];
		args[0] = filePath + TEXT_INPUT_TXT;
		output = System.out;
		cApp.run(args, null, null);
	}

	/**
	 * Case 0B: No output stream Input: null args, valid inputstream, nulloutput
	 * Output: throws CatException
	 * 
	 */
	@Test(expected = CatException.class)
	public void runCAppWithStdin0B() throws CatException {
		try {
			filePath += TEXT_INPUT_TXT;
			input = new FileInputStream(filePath);
			output = System.out;
			cApp.run(null, input, null);
		} catch (FileNotFoundException fnf) {
			fnf.printStackTrace();
		}
	}

	/**
	 * Case 0C: No output stream Input: null args, input stream, output stream
	 * Output: throws CatException
	 * 
	 */
	@Test(expected = CatException.class)
	public void runCAppWithStdin0C() throws CatException {
		cApp.run(null, null, null);

	}

	/**
	 * Case 0D: has both input but no output Output: throws CatException
	 * 
	 */
	@Test(expected = CatException.class)
	public void runCAppWithStdin0D() throws CatException {
		try {
			args = new String[1];
			args[0] = filePath + TEXT_INPUT2_TXT;
			filePath += TEXT_INPUT_TXT;
			input = new FileInputStream(filePath);
			cApp.run(args, input, null);
		} catch (FileNotFoundException fnf) {
			fnf.printStackTrace();
		}

	}
	// Case 1A: Run using stdin

	@Test
	public void runCAppWithStdin1A() {
		try {
			filePath += TEXT_INPUT_TXT;
			input = new FileInputStream(filePath);
			output = new ByteArrayOutputStream();
			cApp.run(null, input, output);
			assertEquals(output.toString(), expectedOutput1());

		} catch (IOException i) {
			i.printStackTrace();
		} catch (CatException c) {
			c.printStackTrace();
		}

	}

	// Case 1B: Run using stdin
	// input: file is empty

	@Test
	public void runCAppWithStdin1B() {
		try {
			filePath += EMPTYFILE;
			input = new FileInputStream(filePath);
		} catch (IOException i) {
			i.printStackTrace();
		}
		try {
			output = new ByteArrayOutputStream();
			cApp.run(null, input, output);
			assertEquals(output.toString(), "");

		} catch (CatException c) {
			c.printStackTrace();
		}

	}

	/**
	 * Case 2A: Run using args Input: args contain one file name, null stdin,
	 * valid output
	 * 
	 * @throws CatException
	 */
	@Test
	public void runCAppWithStdin2A() {
		try {
			args = new String[1];
			args[0] = filePath + ONELINETXT;
			output = new ByteArrayOutputStream();
			cApp.run(args, null, output);
			assertEquals(output.toString(), expectedOutput3());
		} catch (CatException cat) {
			cat.printStackTrace();
		}

	}

	/**
	 * Case 2B: 1 arg but empty
	 *
	 * @throws CatException
	 */
	@Test(expected = CatException.class)
	public void runCAppWithStdin2B() throws CatException {

		args = new String[1];
		args[0] = "";
		output = new ByteArrayOutputStream();
		cApp.run(args, null, output);

	}

	/**
	 * Case 2B: 1 arg but filled with space
	 *
	 * @throws CatException
	 */
	@Test(expected = CatException.class)
	public void runCAppWithStdin2C() throws CatException {

		args = new String[1];
		args[0] = " ";
		output = new ByteArrayOutputStream();
		cApp.run(args, null, output);

	}

	/**
	 * Case 2D: 1 filename but empty
	 *
	 * @throws CatException
	 */
	@Test
	public void runCAppWithStdin2D() {
		try {
			args = new String[1];
			args[0] = filePath + EMPTYFILE;
			output = new ByteArrayOutputStream();
			cApp.run(args, null, output);
			assertEquals(output.toString(), "");
		} catch (CatException cat) {
			cat.printStackTrace();
		}

	}

	/**
	 * Case 3A: 0 arg
	 * 
	 * @throws CatException
	 */
	@Test(expected = CatException.class)
	public void runCAppWithStdin3A() throws CatException {

		args = new String[3];
		output = System.out;
		cApp.run(args, null, output);

	}

	/**
	 * Case 3B: Multiple filenames, all should work
	 * 
	 * @throws CatException
	 */
	@Test
	public void runCAppWithStdin3B() throws CatException {

		args = new String[3];
		args[0] = filePath + ONELINETXT;
		args[1] = filePath + EMPTYFILE;
		args[2] = filePath + TEXT_INPUT2_TXT;
		output = new ByteArrayOutputStream();
		cApp.run(args, null, output);
		assertEquals(output.toString(), expectedOutput3() + "\n" + expectedOutput2());

	}

	/**
	 * Case 3C: If a file is invalid, the other two files are not read
	 * 
	 * @throws CatException
	 */
	@Test(expected = CatException.class)
	public void runCAppWithStdin3C() throws CatException {

		args = new String[3];
		args[0] = "filezzzz.txt";
		args[1] = filePath + ONELINETXT;
		args[2] = filePath + TEXT_INPUT2_TXT;
		output = new ByteArrayOutputStream();
		cApp.run(args, null, output);

	}

	String expectedOutput1() {
		return "Roses are red,\r\n" + "Violets are blue,\r\n" + "All of my base,\r\n" + "Are belong to you.\r\n"
				+ "//end of file";
	}

	String expectedOutput2() {
		return "1 Roses are red,\r\n" + "2 Violets are blue,\r\n" + "3 All of my base,\r\n" + "4 Are belong to you.\r\n"
				+ "5 Roses are red,\r\n" + "6 Violets are blue,\r\n" + "7 Some poems rhyme\r\n"
				+ "8 But this one doesn't.\r\n" + "9 Roses are red,\r\n" + "10 Violets are blue,\r\n"
				+ "11 Make me a sandwich,\r\n" + "12 Or I will kill you.\r\n" + "13 Roses are grey,\r\n"
				+ "14 Violets are grey,\r\n" + "15 I'm color blind.\r\n"
				+ "16 from http://uncyclopedia.wikia.com/wiki/Poetry";
	}

	String expectedOutput3() {
		return "test test ";
	}

	@After
	public void tearDown() throws Exception {
		filePath = null;
		args = null;
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		cApp = null;
	}
}