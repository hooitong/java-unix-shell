/**
 * Test class HeadApplication
 * HeadApplication takes in arg of up to length 3, which may contain
 * 	1. a command e.g. -n
 * 	2. number of lines to read from a file
 * 	3. either filename from stdin or filename contain in the arg
 * 
 * This test different methods in the class and if the above function works.
 * Test case uses bottom up method
 */
package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.HeadException;

public class HeadApplicationTest {

	private static final String TEXTINPUT_EMPTY = "newFile.txt";
	private static final String TEXTINPUT2_TXT = "textInput2.txt";
	private static final String TEXTINPUT_TXT = "textInput.txt";
	static HeadApplication hApp;

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
		hApp = new HeadApplication();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		filePath = "examples/";
	}

	/******************************************************************************
	 * 
	 * Part I: Test method checkIfFileIsReadable()
	 * 
	 * Note: 1. A folder is also a file. Even though it does not contain text,
	 * if it can be opened, system will return true if path to folder exist.
	 * 
	 * 2. Files with password encryption/zipped can all be opened, hence will
	 * all return true
	 * 
	 ******************************************************************************/

	// Case 1: empty text file
	@Test
	public void testMethodCheckIfFileIsReadable() throws HeadException {

		filePath += TEXTINPUT_EMPTY;
		textFile = Paths.get(filePath);
		assertTrue(hApp.checkIfFileIsReadable(textFile));

	}

	// Case 2: non-empty text file
	@Test
	public void testMethodCheckIfFileIsReadable2() throws HeadException {
		filePath += "newFile2.txt";
		textFile = Paths.get(filePath);
		assertTrue(hApp.checkIfFileIsReadable(textFile));

	}

	// Case 3: Non-existant file
	@Test(expected = HeadException.class)
	public void testMethodCheckIfFileIsReadable3() throws HeadException {
		filePath += "nonexist.txt";
		textFile = Paths.get(filePath);
		assertFalse(hApp.checkIfFileIsReadable(textFile));

	}

	// Case 4: Broken directory
	@Test(expected = HeadException.class)
	public void testMethodCheckIfFileIsReadable4() throws HeadException {
		filePath = "test/sg/edu/nus/cs4218/impl/app/testdoc/newFile.txt";
		textFile = Paths.get(filePath);
		assertFalse(hApp.checkIfFileIsReadable(textFile));

	}

	/******************************************************************************
	 * 
	 * Part II: Test method readFromFileAndWriteToStdout
	 * 
	 * Input: Path Filepath : where data is read from Int x : number of lines to
	 * read Outputstream op : Where data read is output to
	 * 
	 * Note: This method is called by readFromArgsAndWriteToStdout
	 * 
	 ******************************************************************************/

	// Case 1: null in both stdout and file input path
	@Test
	public void testMethodReadFromFileAndWriteToStdout() {
		try {
			hApp.readFromFileAndWriteToStdout(null, 1, null);
		} catch (HeadException e) {
			assertEquals("head: Stdout is null", e.getMessage());
		}
	}

	// Case 2: input file is empty and number of lines to read is 0
	@Test
	public void testMethodReadFromFileAndWriteToStdout2() {

		filePath += TEXTINPUT_EMPTY;
		textFile = Paths.get(filePath);
		output = new ByteArrayOutputStream();
		try {
			hApp.readFromFileAndWriteToStdout(output, 0, textFile);
			assertEquals(output.toString(), "");
		} catch (HeadException e) { // Exception is not expected
			e.printStackTrace();
		}
	}

	// Case 3: input file is empty and number of lines to read is 5
	@Test
	public void testMethodReadFromFileAndWriteToStdout3() {

		filePath += TEXTINPUT_EMPTY;
		textFile = Paths.get(filePath);
		output = new ByteArrayOutputStream();
		try {
			hApp.readFromFileAndWriteToStdout(output, 5, textFile);
			assertEquals(output.toString(), "");
		} catch (HeadException e) { // Exception is not expected
			e.printStackTrace();
		}
	}

	// Case 4: input file is not empty and number of lines to read is 5
	// Input file has more lines than what is required
	@Test
	public void testMethodReadFromFileAndWriteToStdout4() {

		filePath += TEXTINPUT2_TXT;
		textFile = Paths.get(filePath);
		output = new ByteArrayOutputStream();

		try {
			hApp.readFromFileAndWriteToStdout(output, 5, textFile);
			assertEquals(output.toString(), expectedOutput2());
		} catch (HeadException e) { // Exception is not expected
			e.printStackTrace();
		}
	}

	// Case 5: input file has less lines than what user wants to read
	@Test
	public void testMethodReadFromFileAndWriteToStdout5() {

		filePath += TEXTINPUT_TXT;
		textFile = Paths.get(filePath);
		output = new ByteArrayOutputStream();
		try {
			hApp.readFromFileAndWriteToStdout(output, 20, textFile);
			assertEquals(output.toString(), expectedOutput1());
		} catch (HeadException e) { // Exception is not expected
			e.printStackTrace();
		}
	}

	// Case 6: input file has exact number of lines that the user wants to read
	@Test
	public void testMethodReadFromFileAndWriteToStdout6() {

		filePath += TEXTINPUT2_TXT;
		textFile = Paths.get(filePath);
		output = new ByteArrayOutputStream();
		try {
			hApp.readFromFileAndWriteToStdout(output, 16, textFile);
			assertEquals(output.toString(), expectedOutput3());
		} catch (HeadException e) { // Exception is not expected
			e.printStackTrace();
		}
	}

	/******************************************************************************
	 * 
	 * Part III: Test method checkNumberOfLinesInput
	 * 
	 * 
	 ******************************************************************************/

	// Case 1: A number
	@Test
	public void testMethodCheckNumberOfLinesInput() {

		try {
			assertEquals(hApp.checkNumberOfLinesInput("1"), 1);
		} catch (HeadException e) { // Exception is not expected
			e.printStackTrace();
		}
	}

	// Case 2: A long number
	@Test
	public void testMethodCheckNumberOfLinesInput2() {

		try {
			assertEquals(hApp.checkNumberOfLinesInput("2147483647"), 2147483647);
		} catch (HeadException e) { // Exception is not expected
			e.printStackTrace();
		}
	}

	// Case 3: A number that is too long to be an integer
	@Test(expected = HeadException.class)
	public void testMethodCheckNumberOfLinesInput3() throws HeadException {
		hApp.checkNumberOfLinesInput("2147483648");
	}

	// Case 4: Contain spaces
	@Test(expected = HeadException.class)
	public void testMethodCheckNumberOfLinesInput4() throws HeadException {
		hApp.checkNumberOfLinesInput("12 3");
	}

	// Case 5: Contain symbol
	@Test(expected = HeadException.class)
	public void testMethodCheckNumberOfLinesInput5() throws HeadException {
		hApp.checkNumberOfLinesInput("1$$2$3");
	}

	/******************************************************************************
	 * 
	 * Part IV: Test method readFromStdinAndWriteToStdout
	 * 
	 * -Ignore checks on number of lines because it is checked in other methods
	 * 
	 * 
	 * Reference: http://tutorials.jenkov.com/java-io/inputstream.html
	 * 
	 ******************************************************************************/

	// Case 1: null inputstream
	@Test(expected = HeadException.class)
	public void testMethodReadFromStdinAndWriteToStdout() throws HeadException {
		output = System.out;
		input = null;
		hApp.readFromStdinAndWriteToStdout(output, 0, input);
	}

	// Case 2: null outputstream
	@Test(expected = HeadException.class)
	public void testMethodReadFromStdinAndWriteToStdout2() throws HeadException {
		filePath += TEXTINPUT_TXT;
		try {
			output = null;
			input = new FileInputStream(filePath);
			hApp.readFromStdinAndWriteToStdout(output, 0, input);
		} catch (IOException i) {

		}
	}

	// Case 3: null outputstream and null inputstream
	@Test(expected = HeadException.class)
	public void testMethodReadFromStdinAndWriteToStdout3() throws HeadException {
		hApp.readFromStdinAndWriteToStdout(null, 0, null);

	}

	// Case 4: Case where it works
	// Input: input has exact number of lines that the user want to read
	@Test
	public void testMethodReadFromStdinAndWriteToStdout4() {
		try {

			filePath += TEXTINPUT_TXT;
			output = new ByteArrayOutputStream();
			input = new FileInputStream(filePath);
			hApp.readFromStdinAndWriteToStdout(output, 5, input);
			assertEquals(output.toString(), expectedOutput1());
		} catch (IOException i) {
			i.printStackTrace();
		} catch (HeadException h) {
			h.printStackTrace();
		}

	}

	// Case 5: Case where it works
	// Input: file has more lines than the user wants to read
	@Test
	public void testMethodReadFromStdinAndWriteToStdout5() {
		try {

			filePath += TEXTINPUT2_TXT;
			output = new ByteArrayOutputStream();
			input = new FileInputStream(filePath);
			hApp.readFromStdinAndWriteToStdout(output, 5, input);
			assertEquals(output.toString(), expectedOutput2());
		} catch (IOException i) {
			i.printStackTrace();
		} catch (HeadException h) {
			h.printStackTrace();
		}

	}

	// Case 6: Case where it works
	// Input: file has less lines than the user wants to read
	@Test
	public void testMethodReadFromStdinAndWriteToStdout6() {

		try {

			filePath += TEXTINPUT_TXT;
			output = new ByteArrayOutputStream();
			input = new FileInputStream(filePath);
			hApp.readFromStdinAndWriteToStdout(output, 8, input);
			assertEquals(output.toString(), expectedOutput1());
		} catch (IOException i) {
			i.printStackTrace();
		} catch (HeadException h) {
			h.printStackTrace();
		}
	}

	// Case 7: Case where it works
	// Input: input file is empty
	@Test
	public void testMethodReadFromStdinAndWriteToStdout7() {

		try {

			filePath += TEXTINPUT_EMPTY;
			output = new ByteArrayOutputStream();
			input = new FileInputStream(filePath);
			hApp.readFromStdinAndWriteToStdout(output, 8, input);
			assertEquals(output.toString(), "");
		} catch (IOException i) {
			i.printStackTrace();
		} catch (HeadException h) {
			h.printStackTrace();
		}
	}

	/******************************************************************************
	 * 
	 * Part V: Test method readFromArgsAndWriteToStdout
	 * 
	 * This series of tests are to check whether filenames from args are read
	 * properly and if HeadExceptions are thrown when there is an error
	 * 
	 * @throws HeadException
	 * 
	 * 
	 ******************************************************************************/
	// Case 1: one arg
	@Test
	public void testMethodReadFromArgsAndWriteToStdout1() {

		try {
			args = new String[1];
			args[0] = filePath + TEXTINPUT_EMPTY;
			output = new ByteArrayOutputStream();
			hApp.readFromArgsAndWriteToStdout(args, output, 0);
			assertEquals(output.toString(), "");
		} catch (HeadException h) {
			h.printStackTrace();
		}
	}

	// Case 2: one arg
	// input: files has many lines but read none
	@Test
	public void testMethodReadFromArgsAndWriteToStdout2() {

		try {
			args = new String[1];
			args[0] = filePath + TEXTINPUT_TXT;
			output = new ByteArrayOutputStream();
			hApp.readFromArgsAndWriteToStdout(args, output, 0);
			assertEquals(output.toString(), "");
		} catch (HeadException h) {
			h.printStackTrace();
		}
	}

	// Case 3: one arg
	// input: Read exactly 5 lines out of 5 lines of the file
	@Test
	public void testMethodReadFromArgsAndWriteToStdout3() {

		try {
			args = new String[1];
			args[0] = filePath + TEXTINPUT_TXT;
			output = new ByteArrayOutputStream();
			hApp.readFromArgsAndWriteToStdout(args, output, 5);
			assertEquals(output.toString(), expectedOutput1());
		} catch (HeadException h) {
			h.printStackTrace();
		}
	}

	// Case 3B: one arg
	// input: input file does not exist on args 0
	@Test(expected = HeadException.class)
	public void testMethodReadFromArgsAndWriteToStdout3B() throws HeadException {

		args = new String[1];
		args[0] = "";
		output = new ByteArrayOutputStream();
		hApp.readFromArgsAndWriteToStdout(args, output, 5);

	}

	// Case 4: read filename from the 3rd argument
	@Test
	public void testMethodReadFromArgsAndWriteToStdout4() {

		try {
			args = new String[3];
			args[2] = filePath + TEXTINPUT_TXT;
			output = new ByteArrayOutputStream();
			hApp.readFromArgsAndWriteToStdout(args, output, 5);
			assertEquals(output.toString(), expectedOutput1());
		} catch (HeadException h) {
			h.printStackTrace();
		}
	}

	// Case 4B: read filename from the 3rd argument
	// Input: invalid filename
	@Test(expected = HeadException.class)
	public void testMethodReadFromArgsAndWriteToStdout4B() throws HeadException {

		args = new String[3];
		args[2] = "2";
		output = new ByteArrayOutputStream();
		hApp.readFromArgsAndWriteToStdout(args, output, 5);

	}

	// Case 5: error in args
	@Test(expected = HeadException.class)
	public void testMethodReadFromArgsAndWriteToStdout5() throws HeadException {
		args = new String[3];
		args[0] = "-n";
		args[1] = "10";
		args[2] = " ";
		output = new ByteArrayOutputStream();
		hApp.readFromArgsAndWriteToStdout(args, output, 10);

	}

	// Case 5B: error in args
	@Test(expected = HeadException.class)
	public void testMethodReadFromArgsAndWriteToStdout5B() throws HeadException {
		args = new String[1];
		args[0] = "none";
		output = new ByteArrayOutputStream();
		hApp.readFromArgsAndWriteToStdout(args, output, 10);

	}

	// Case 5C: error in args
	@Test(expected = HeadException.class)
	public void testMethodReadFromArgsAndWriteToStdout5C() throws HeadException {
		args = new String[3];
		args[0] = "-n22";
		args[1] = "filename";
		args[2] = "xxxx.txt";
		output = new ByteArrayOutputStream();
		hApp.readFromArgsAndWriteToStdout(args, output, 10);

	}

	/******************************************************************************
	 * 
	 * Part VI: Test main method run(String[] args, InputStream stdin,
	 * OutputStream stdout)
	 * 
	 * @throws HeadException
	 * 
	 *
	 * 
	 ******************************************************************************/
	// Case 0: check for null cases
	@Test(expected = HeadException.class)
	public void testrun0() throws HeadException {
		hApp.run(null, null, null);
	}

	// Case 0B: check for null cases
	@Test(expected = HeadException.class)
	public void testrun0B() throws HeadException {
		args = new String[1];
		args[0] = filePath + TEXTINPUT_TXT;
		try {
			input = new FileInputStream(filePath + TEXTINPUT_TXT);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		hApp.run(args, input, null);
	}

	// Case 0C: check for null cases
	@Test(expected = HeadException.class)
	public void testrun0C() throws HeadException {
		args = new String[1];
		args[0] = filePath + TEXTINPUT_TXT;
		try {
			input = new FileInputStream(filePath + TEXTINPUT_TXT);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		hApp.run(null, input, null);
	}

	// Case 0D: check for null cases
	@Test(expected = HeadException.class)
	public void testrun0D() throws HeadException {
		args = new String[1];
		args[0] = filePath + TEXTINPUT_TXT;
		try {
			input = new FileInputStream(filePath + TEXTINPUT_TXT);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		hApp.run(args, null, null);
	}

	// Case 0E: check for null cases
	@Test(expected = HeadException.class)
	public void testrun0E() throws HeadException {
		output = System.out;
		hApp.run(null, null, output);
	}

	// Case 1: argument
	// Expected output: 10 lines since num of lines are not specified
	@Test
	public void testrun1() {
		args = new String[1];
		args[0] = filePath + TEXTINPUT2_TXT;
		output = new ByteArrayOutputStream();
		try {
			hApp.run(args, null, output);
			assertEquals(output.toString(), expectedOutput4());
		} catch (HeadException e) {
			e.printStackTrace();
		}
	}

	// Case 1B: argument
	// Expected output: 5 lines since file is short
	@Test
	public void testrun1B() {
		args = new String[1];
		args[0] = filePath + TEXTINPUT_TXT;
		output = new ByteArrayOutputStream();
		try {
			hApp.run(args, null, output);
			assertEquals(output.toString(), expectedOutput1());
		} catch (HeadException e) {
			e.printStackTrace();
		}
	}

	// Case 1C: argument
	// Expected output: invalid filename
	@Test(expected = HeadException.class)
	public void testrun1C() throws HeadException {
		args = new String[1];
		args[0] = "-n";
		output = new ByteArrayOutputStream();
		hApp.run(args, null, output);
	}

	// Case 2: argument
	// Expected output: 5 lines of output
	@Test
	public void testrun2() {
		args = new String[3];
		args[0] = "-n";
		args[1] = "5";
		args[2] = filePath + TEXTINPUT2_TXT;
		output = new ByteArrayOutputStream();
		try {
			hApp.run(args, null, output);
			assertEquals(output.toString(), expectedOutput2());
		} catch (HeadException e) {
			e.printStackTrace();
		}
	}

	// Case 2B: argument
	// Invalid command
	@Test(expected = HeadException.class)
	public void testrun2B() throws HeadException {
		args = new String[3];
		args[0] = "--n";
		args[1] = "5";
		args[2] = filePath + TEXTINPUT2_TXT;
		output = new ByteArrayOutputStream();
		hApp.run(args, null, output);

	}

	// Case 2C: argument
	// Invalid command: error in num of lines
	// Expected output: 5 lines of output
	@Test(expected = HeadException.class)
	public void testrun2C() throws HeadException {
		args = new String[3];
		args[0] = "-n";
		args[1] = "hjadhas";
		args[2] = filePath + TEXTINPUT2_TXT;
		output = new ByteArrayOutputStream();
		hApp.run(args, null, output);

	}

	// Case 2D: argument
	// Invalid command: no such filename
	@Test(expected = HeadException.class)
	public void testrun2D() throws HeadException {
		args = new String[3];
		args[0] = "-n";
		args[1] = "5";
		args[2] = filePath + "non.txt";
		output = new ByteArrayOutputStream();
		hApp.run(args, null, output);

	}

	// Case 2E: argument
	// Invalid command: too many args
	@Test(expected = HeadException.class)
	public void testrun2E() throws HeadException {
		args = new String[5];
		args[0] = "-n";
		args[1] = " ";
		args[2] = "2";
		args[3] = "non";
		args[4] = filePath + ".txt";
		output = new ByteArrayOutputStream();
		hApp.run(args, null, output);

	}

	// Case 3: from Inputstream
	// Expected output: 10 lines of output
	@Test
	public void testrun3() {
		filePath += TEXTINPUT2_TXT;
		try {
			input = new FileInputStream(filePath);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		output = new ByteArrayOutputStream();
		try {
			hApp.run(null, input, output);
			assertEquals(output.toString(), expectedOutput4());
		} catch (HeadException e) {
			e.printStackTrace();
		}
	}

	// Case 3B: from Inputstream
	// Expected output: 5 lines since file is short (instead of default 10)
	@Test
	public void testrun3B() {
		filePath += TEXTINPUT_TXT;
		try {
			input = new FileInputStream(filePath);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		output = new ByteArrayOutputStream();
		try {
			hApp.run(null, input, output);
			assertEquals(output.toString(), expectedOutput1());
		} catch (HeadException e) {
			e.printStackTrace();
		}
	}

	// Case 4: num of lines from arg, filename from input, with valid output
	// Expected output: 16 lines of the file
	@Test
	public void testrun4() {
		args = new String[2];
		args[0] = "-n";
		args[1] = "16";
		filePath += TEXTINPUT2_TXT;
		try {
			input = new FileInputStream(filePath);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		output = new ByteArrayOutputStream();
		try {
			hApp.run(args, input, output);
			assertEquals(output.toString(), expectedOutput3());
		} catch (HeadException e) {
			e.printStackTrace();
		}
	}

	// Case 4B: num of lines from arg, filename from input, with valid output
	// error in arg
	@Test(expected = HeadException.class)
	public void testrun4B() throws HeadException {
		args = new String[2];
		args[0] = "-n";
		args[1] = "16z";
		filePath += TEXTINPUT2_TXT;
		try {
			input = new FileInputStream(filePath);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		output = new ByteArrayOutputStream();
		hApp.run(args, input, output);

	}

	String expectedOutput1() {
		return "Roses are red,\r\n" + "Violets are blue,\r\n" + "All of my base,\r\n" + "Are belong to you.\r\n"
				+ "//end of file\r\n";
	}

	String expectedOutput2() {
		return "1 Roses are red,\r\n" + "2 Violets are blue,\r\n" + "3 All of my base,\r\n" + "4 Are belong to you.\r\n"
				+ "5 Roses are red,\r\n";
	}

	String expectedOutput3() {
		return "1 Roses are red,\r\n" + "2 Violets are blue,\r\n" + "3 All of my base,\r\n" + "4 Are belong to you.\r\n"
				+ "5 Roses are red,\r\n" + "6 Violets are blue,\r\n" + "7 Some poems rhyme\r\n"
				+ "8 But this one doesn't.\r\n" + "9 Roses are red,\r\n" + "10 Violets are blue,\r\n"
				+ "11 Make me a sandwich,\r\n" + "12 Or I will kill you.\r\n" + "13 Roses are grey,\r\n"
				+ "14 Violets are grey,\r\n" + "15 I'm color blind.\r\n"
				+ "16 from http://uncyclopedia.wikia.com/wiki/Poetry\r\n";
	}

	String expectedOutput4() {
		return "1 Roses are red,\r\n" + "2 Violets are blue,\r\n" + "3 All of my base,\r\n" + "4 Are belong to you.\r\n"
				+ "5 Roses are red,\r\n" + "6 Violets are blue,\r\n" + "7 Some poems rhyme\r\n"
				+ "8 But this one doesn't.\r\n" + "9 Roses are red,\r\n" + "10 Violets are blue,\r\n";
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		filePath = null;
		args = null;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		hApp = null;

	}

}
