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

	static HeadApplication hApp;
	// Part I
	static String filePath;
	Path textFile;

	// Part II
	static OutputStream op;

	// Part IV
	static InputStream is;

	// Part V
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
		filePath = "test/sg/edu/nus/comp/cs4218/impl/app/testdoc/";
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
		// createFileForTesting();
		filePath += "newFile.txt";
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
	@Test (expected = HeadException.class)
	public void testMethodCheckIfFileIsReadable3() throws HeadException {
		filePath += "nonexist.txt";
		textFile = Paths.get(filePath);
		assertFalse(hApp.checkIfFileIsReadable(textFile));

	}

	// Case 4: Broken directory
	@Test (expected = HeadException.class)
	public void testMethodCheckIfFileIsReadable4() throws HeadException{
		filePath = "test/sg/edu/nus/cs4218/impl/app/testdoc/newFile.txt";
		textFile = Paths.get(filePath);
		assertFalse(hApp.checkIfFileIsReadable(textFile));
		
	}
	
	/******************************************************************************
	 * 
	 * Part II: Test method readFromFileAndWriteToStdout
	 * 
	 * -Ignore check invalid number of lines -Ignore filepath check
	 * 
	 * To do: instead of printing out output, compare output written to the
	 * correct text file
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
	public void testMethodReadFromFileAndWrieToStdout2() {

		filePath += "newFile.txt";
		textFile = Paths.get(filePath);
		op =  new ByteArrayOutputStream();
		try {
			hApp.readFromFileAndWriteToStdout(op, 0, textFile);
			assertEquals(op.toString(), "");
		} catch (HeadException e) { // Exception is not expected
			e.printStackTrace();
		}
	}

	// Case 3: input file is empty and number of lines to read is 5
	@Test
	public void testMethodReadFromFileAndWrieToStdout3() {

		filePath += "newFile.txt";
		textFile = Paths.get(filePath);
		op = new ByteArrayOutputStream();
		try {
			hApp.readFromFileAndWriteToStdout(op, 5, textFile);
			assertEquals(op.toString(), "");
		} catch (HeadException e) { // Exception is not expected
			e.printStackTrace();
		}
	}

	// Case 4: input file is not empty and number of lines to read is 5
	// Input file has more lines than what is required
	@Test
	public void testMethodReadFromFileAndWrieToStdout4() {

		filePath += "textInput2.txt";
		textFile = Paths.get(filePath);
		op = new ByteArrayOutputStream();
		
		try {
			hApp.readFromFileAndWriteToStdout(op, 5, textFile);
			assertEquals(op.toString(), expectedOutput2());
		} catch (HeadException e) { // Exception is not expected
			e.printStackTrace();
		}
	}

	// Case 5: input file has less lines than what user wants to read
	@Test
	public void testMethodReadFromFileAndWrieToStdout5() {

		filePath += "textInput.txt";
		textFile = Paths.get(filePath);
		op = new ByteArrayOutputStream();
		try {
			hApp.readFromFileAndWriteToStdout(op, 20, textFile);
			assertEquals(op.toString(), expectedOutput1());
		} catch (HeadException e) { // Exception is not expected
			e.printStackTrace();
		}
	}

	// Case 6: input file has exact number of lines that the user wants to read
	@Test
	public void testMethodReadFromFileAndWrieToStdout6() {

		filePath += "textInput2.txt";
		textFile = Paths.get(filePath);
		op = new ByteArrayOutputStream();
		try {
			hApp.readFromFileAndWriteToStdout(op, 16, textFile);
			assertEquals(op.toString(), expectedOutput3());
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

	// Case 6: Contain space in front of the number
	// Should this be discarded?
	@Test(expected = HeadException.class)
	public void testMethodCheckNumberOfLinesInput6() throws HeadException {
		hApp.checkNumberOfLinesInput(" 13");
	}

	/******************************************************************************
	 * 
	 * Part IV: Test method readFromStdinAndWriteToStdout
	 * 
	 * -Ignore checks on number of lines because it is checked in other methods
	 * 
	 * To do: Missing IO check
	 * 
	 * Reference: http://tutorials.jenkov.com/java-io/inputstream.html
	 * 
	 ******************************************************************************/

	// Case 1: null inputstream
	@Test(expected = HeadException.class)
	public void testMethodReadFromStdinAndWriteToStdout() throws HeadException {
		op = System.out;
		is = null;
		hApp.readFromStdinAndWriteToStdout(op, 0, is);
	}

	// Case 2: null outputstream
	@Test(expected = HeadException.class)
	public void testMethodReadFromStdinAndWriteToStdout2() throws HeadException {
		filePath += "textInput.txt";
		try {
			op = null;
			is = new FileInputStream(filePath);
			hApp.readFromStdinAndWriteToStdout(op, 0, is);
		} catch (IOException i) {

		}
	}

	// Case 3: null outputstream and null inputstream
	@Test(expected = HeadException.class)
	public void testMethodReadFromStdinAndWriteToStdout3() throws HeadException {
		hApp.readFromStdinAndWriteToStdout(null, 0, null);

	}

	// Case 4: Case where it works
	@Test
	public void testMethodReadFromStdinAndWriteToStdout4() {
		try {

			filePath += "textInput.txt";
			op = new ByteArrayOutputStream();
			is = new FileInputStream(filePath);
			hApp.readFromStdinAndWriteToStdout(op, 5, is);
			assertEquals(op.toString(), expectedOutput1());
		} catch (IOException i) {
			i.printStackTrace();
		} catch (HeadException h) {
			h.printStackTrace();
		}

	}

	/******************************************************************************
	 * 
	 * Part V: Test main method run(String[] args, InputStream stdin,
	 * OutputStream stdout)
	 * 
	 * @throws HeadException
	 * 
	 *             - ?ignore whether filename provided in args is valid because
	 *             assumed checked before sending over
	 *
	 * 
	 ******************************************************************************/

	// Case 1: null on all three variables
	@Test(expected = HeadException.class)
	public void testRun() throws HeadException {

		hApp.run(null, null, null);

	}

	// Case 2: If args is empty, system check that inputstream is not null and
	// takes data from inputstream
	// Error because nullpointer alert received.
	// Expected output: 10 lines or up to EOF of the file being read
	@Test
	public void testRun2() {
		try {
			filePath += "textInput.txt";
			is = new FileInputStream(filePath);
			op = new ByteArrayOutputStream();
			hApp.run(null, is, op);
			assertEquals(op.toString(), expectedOutput1());
		} catch (IOException i) {
			i.printStackTrace();
		} catch (HeadException h) {
			h.printStackTrace();
		}
	}

	// Case 2B: If args is empty, system check that inputstream is not null and
	// takes data from inputstream
	// Here inputStream is also set as null, hence exception is expected.
	@Test(expected = HeadException.class)
	public void testRun2B() throws HeadException {

		op = System.out;
		hApp.run(null, null, op);
	}

	// Case 3: null on stdin but contain one item in arg (filename)
	@Test
	public void testRun3() throws HeadException {
		filePath += "textInput.txt";
		args = new String[1];
		args[0] = filePath;
		op = new ByteArrayOutputStream();
		hApp.run(args, null, op);
		assertEquals(op.toString(), expectedOutput1());

	}

	// Case 3B: null on stdin but two items (not filename)
	// Expect exception because no valid input file
	@Test(expected = HeadException.class)
	public void testRun3B() throws HeadException {
		String command = "-n";
		String num = "5";
		args = new String[2];
		args[0] = command;
		args[1] = num;
		op = System.out;
		hApp.run(args, null, op);
	}

	// Case 3C: null on stdin but arg contain one invalid file
	// Expect exception because no valid input file
	@Test(expected = HeadException.class)
	public void testRun3C() throws HeadException {
		filePath += "invalid.txt";
		args = new String[1];
		args[0] = filePath;
		op = System.out;
		hApp.run(args, null, op);
	}

	// Case 3D: null on stdin but contains all 3 items: command, int, filename
	// Expected output: 5 lines or EOF of the filename
	@Test
	public void testRun3D() throws HeadException {
		filePath += "textInput.txt";
		String comm = "-n";
		String numOfLines = "5";
		args = new String[3];
		args[0] = comm;
		args[1] = numOfLines;
		args[2] = filePath;
		
		op = new ByteArrayOutputStream();
		hApp.run(args, null, op);
		assertEquals(op.toString(), expectedOutput1());
	}

	/**
	 * Case 3E: Testing integration of other methods 
	 * 	Test if invalid number is caught
	 *
	 */

	@Test(expected = HeadException.class)
	public void testRun3E() throws HeadException {
		filePath += "textInput.txt";
		String comm = "-n";
		String numOfLines = "*@5";
		args = new String[3];
		args[0] = comm;
		args[1] = numOfLines;
		args[2] = filePath;
		op = System.out;
		hApp.run(args, null, op);
	}

	/**
	 * Case 3F: Testing integration of other methods 
	 * 	Test for invalid command
	 * 	when args length is 3
	 *
	 */

	@Test(expected = HeadException.class)
	public void testRun3F() throws HeadException {
		filePath += "textInput.txt";
		String comm = "-----n";
		String numOfLines = "5";
		args = new String[3];
		args[0] = comm;
		args[1] = numOfLines;
		args[2] = filePath;
		op = System.out;
		hApp.run(args, null, op);
	}

	/**
	 * Case 3G: Testing integration of other methods 
	 * Test for invalid filename
	 * when args length is 3
	 *
	 */

	@Test(expected = HeadException.class)
	public void testRun3G() throws HeadException {
		filePath += "invalid.txt";
		String comm = "-n";
		String numOfLines = "5";
		args = new String[3];
		args[0] = comm;
		args[1] = numOfLines;
		args[2] = filePath;
		op = System.out;
		hApp.run(args, null, op);
	}

	// Case 4: null on output
	@Test(expected = HeadException.class)
	public void testRun4() throws HeadException {
		String temp = "quantum.txt";
		args = new String[1];
		args[0] = temp;
		try {
			filePath += "textInput.txt";
			op = System.out;
			is = new FileInputStream(filePath);
			hApp.run(args, is, null);
		} catch (IOException i) {
			i.printStackTrace();
		}
	}
	
	// Case 4B: Null on input and output, but argument is non-empty
		@Test(expected = HeadException.class)
		public void testRun4B() throws HeadException {
			String temp = "quantum.txt";
			args = new String[1];
			args[0] = temp;
			op = System.out;
			hApp.run(args, null, null);
			
		}
	
	// Case 5: Reading input from stdin
	//			- Empty arg
	//			- Expected output 10 lines of text
		@Test
		public void testRun5(){
			
			try {
				filePath += "textInput2.txt";
				op = System.out;
				is = new FileInputStream(filePath);
				hApp.run(null, is, op);
			} catch (IOException i) {
				i.printStackTrace();
			}catch (HeadException h){
				h.printStackTrace();
			}
		}
		
		// Case 5B: Reading input from stdin
		//			- arg with command
		//			- Expected output 8 lines of text
			@Test
			public void testRun5B(){
				String comm = "-n";
				String num = "8";
				args = new String[2];
				args[0] = comm;
				args[1] = num;
				try {
					filePath += "textInput2.txt";
					op = System.out;
					is = new FileInputStream(filePath);
					hApp.run(args, is, op);
				} catch (IOException i) {
					i.printStackTrace();
				}catch (HeadException h){
					h.printStackTrace();
				}
			}
	
		
	String expectedOutput1(){
		return "Roses are red,\r\n"
				+ "Violets are blue,\r\n"
				+ "All of my base,\r\n"
				+ "Are belong to you.\r\n"
				+ "//end of file\r\n";
	}
	
	String expectedOutput2(){
		return "1 Roses are red,\r\n"
				+ "2 Violets are blue,\r\n"
				+ "3 All of my base,\r\n"
				+ "4 Are belong to you.\r\n"
				+ "5 Roses are red,\r\n";
	}

	String expectedOutput3(){
		return "1 Roses are red,\r\n"
				+ "2 Violets are blue,\r\n"
				+ "3 All of my base,\r\n"
				+ "4 Are belong to you.\r\n"
				+ "5 Roses are red,\r\n"
				+ "6 Violets are blue,\r\n"
				+ "7 Some poems rhyme\r\n"
				+ "8 But this one doesn't.\r\n"
				+ "9 Roses are red,\r\n"
				+ "10 Violets are blue,\r\n"
				+ "11 Make me a sandwich,\r\n"
				+ "12 Or I will kill you.\r\n"
				+ "13 Roses are grey,\r\n"
				+ "14 Violets are grey,\r\n"
				+ "15 I'm color blind.\r\n"
				+ "16 from http://uncyclopedia.wikia.com/wiki/Poetry\r\n";
	}
	
	String expectedOutput4(){
		return "1 Roses are red,\r\n"
				+ "2 Violets are blue,\r\n"
				+ "3 All of my base,\r\n"
				+ "4 Are belong to you.\r\n"
				+ "5 Roses are red,\r\n"
				+ "6 Violets are blue,\r\n"
				+ "7 Some poems rhyme\r\n"
				+ "8 But this one doesn't.\r\n"
				+ "9 Roses are red,\r\n"
				+ "10 Violets are blue,\r\n";
	}
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		filePath = null;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		hApp = null;
		args = null;

	}

}
