package ComSubHeadEchoCat;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.CatException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;
import sg.edu.nus.comp.cs4218.impl.app.CatApplication;

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
	private static ShellImpl shell;

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
		filePath = "Examples/";
		shell = new ShellImpl();
		output = new ByteArrayOutputStream();
	}

	/**
	 *
	 * Bug report:
	 *
	 * Nullpointer exception not caught by Cat application When outputstream is
	 * null Error is between line 85-93
	 *
	 */

	@Test
	public void nullPointerExceptionNotCaught() throws CatException {
		args = new String[1];
		args[0] = filePath + TEXT_INPUT_TXT;
		cApp.run(args, null, null);
	}

	/**
	 *
	 * Bug report:
	 *
	 * Nullpointer exception not caught by Cat application Error is not caught
	 * when args is null Error is between line 75-83
	 */
	@Test
	public void nullPointerExceptionNotCaught2() throws CatException {

		args = new String[3];
		output = System.out;
		cApp.run(args, null, output);

	}

	/**
	 * 
	 * Bug report: Does not throw exception/stop application when path to file
	 * stored in When arg contain only spaces or symbols.
	 * 
	 * Error is at line 77
	 * 
	 * Excepted: cat exception Received: java.nio.file.InvalidPathException
	 *
	 */
	// @Test
	public void invalidPathExceptionNotCaughtA() throws CatException {

		args = new String[1];
		args[0] = " ";
		output = new ByteArrayOutputStream();
		cApp.run(args, null, output);

	}

	// @Test
	public void invalidPathExceptionNotCaughtB() throws Exception {
		String command = "cat    ";
		try {
			shell.parseAndEvaluate(command, output);
			System.out.println(output.toString());
		} catch (ShellException shell) {
			System.out.println(shell);
		} catch (CatException cat) {
			System.out.println(cat);
		}

	}

	@Test
	public void invalidPathExceptionNotCaughtC() throws CatException {

		args = new String[1];
		args[0] = "*822";
		output = new ByteArrayOutputStream();
		cApp.run(args, null, output);

	}

	/**
	 * Bug report: There are two files that are in the output stream, however,
	 * there is no new line that separate the first file from the second Even
	 * though when printing a single file, this application is capable of
	 * printing one new line after each file. A new line is expected here. After
	 * output of the first file.
	 * 
	 * Developer team can add a new line in the bytearray at line 94
	 */
	@Test
	public void newLineIsExpected() throws CatException {

		args = new String[3];
		args[0] = filePath + ONELINETXT;
		args[1] = filePath + EMPTYFILE;
		args[2] = filePath + TEXT_INPUT2_TXT;
		output = new ByteArrayOutputStream();
		cApp.run(args, null, output);
		assertEquals(output.toString(), expectedOutput3() + System.lineSeparator() + expectedOutput2());

	}

	String expectedOutput1() {
		return "Roses are red," + System.lineSeparator() + "Violets are blue," + System.lineSeparator()
				+ "All of my base," + System.lineSeparator() + "Are belong to you." + System.lineSeparator()
				+ "//end of file";
	}

	String expectedOutput2() {
		return "1 Roses are red," + System.lineSeparator() + "2 Violets are blue," + System.lineSeparator()
				+ "3 All of my base," + System.lineSeparator() + "4 Are belong to you." + System.lineSeparator()
				+ "5 Roses are red," + System.lineSeparator() + "6 Violets are blue," + System.lineSeparator()
				+ "7 Some poems rhyme" + System.lineSeparator() + "8 But this one doesn't." + System.lineSeparator()
				+ "9 Roses are red," + System.lineSeparator() + "10 Violets are blue," + System.lineSeparator()
				+ "11 Make me a sandwich," + System.lineSeparator() + "12 Or I will kill you." + System.lineSeparator()
				+ "13 Roses are grey," + System.lineSeparator() + "14 Violets are grey," + System.lineSeparator()
				+ "15 I'm color blind." + System.lineSeparator() + "16 from http://uncyclopedia.wikia.com/wiki/Poetry";
	}

	String expectedOutput3() {
		return "test test ";
	}

	@After
	public void tearDown() throws Exception {
		filePath = null;
		args = null;
		shell = null;
		output = null;
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		cApp = null;
	}

}
