package sg.edu.nus.comp.cs4218.impl;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.CatException;
import sg.edu.nus.comp.cs4218.exception.EchoException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.exception.SortException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;
import sg.edu.nus.comp.cs4218.impl.app.BcApplication;
import sg.edu.nus.comp.cs4218.impl.app.CatApplication;
import sg.edu.nus.comp.cs4218.impl.app.CommApplication;
import sg.edu.nus.comp.cs4218.impl.app.EchoApplication;
import sg.edu.nus.comp.cs4218.impl.app.SortApplication;

public class HackathonFixedTestCases {

	private static SortApplication sortApplication;
	private static BcApplication bcApp;	
	private static CatApplication catApp;
	private static String[] testArr;
	private static ByteArrayOutputStream baos;
	private static ShellImpl shell;
	private static Scanner scanner;
	final static String TEST_FILE_NAME = "testShell.txt";
	final static String TEST_STR = " 中国语"
				+ System.lineSeparator()
				+ "عطي يونيكود رقما فريدا لكل حرف. "
				+ System.lineSeparator()
				+ System.lineSeparator();
	
	final static String TEST_FILE_NAME2 = "test Shell 2.txt";
	final static String TEST_STR1 = "Testing Stream";
	
    private static final String COMM_FILE1_CONTENT = "line1\nline2\nline3\nline8";
    private static final String COMM_FILE2_CONTENT = "line1\nline2\nline3\nline8";
    private static final String COMM_FILE1_FILE2 = "\t\t" + "line1"
            + System.lineSeparator() + "\t\t" + "line2" + System.lineSeparator()
            + "\t\t" + "line3" + System.lineSeparator()
            + "\t\t" + "line8" + System.lineSeparator() + System.lineSeparator();
	
	final static String TEST_FOLDER_NAME = "testShellFolder";

	final static String VALID_CMD_NO_EXP = "Not supposed to throw exception for valid command.";
	final static String VALID_FILE_NO_EXP = "Not supposed to have exception for valid file.";
	final static String VALID_STRM_NO_EXP = "Not supposed to have exception for valid streams.";
	final static String READONLY_EXP = "Supposed to have exception opening outputstream to read-only file.";
	final static String VALID_EXP = "Valid Exception thrown";
	final static String MISSING_EXP = "Should have exception thrown";
	static String originalFilePath;

	// find out sorting does not work
	// bc is not included in the shellImpl runApp function
	// single quoting has problem
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shell = new ShellImpl();
		baos = new ByteArrayOutputStream();
		createTestFile(TEST_FILE_NAME);
		createTestFile1("text1.txt");
		createTestFile1("test.txt");
		createTestFile2("comm_file1.txt", "comm_file2.txt");
		createTestFolder(TEST_FOLDER_NAME);
		createTestFile1(TEST_FOLDER_NAME + "/" + TEST_FILE_NAME2);
		originalFilePath = Environment.currentDirectory;
		sortApplication = new SortApplication();
		bcApp = new BcApplication();
		catApp = new CatApplication();
		testArr = new String[] { "1", "100000000000000000000000000000000000000000000000000000000000000000", "23"};
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		removeTestFile(TEST_FILE_NAME);
		removeTestFile(TEST_FOLDER_NAME + "/" + TEST_FILE_NAME2);
		removeTestFile("text1.txt");
		removeTestFile("test.txt");
		removeTestFile("comm_file1.txt");
		removeTestFile("comm_file2.txt");
		removeTestFolder(TEST_FOLDER_NAME);
		sortApplication = null;
		testArr = null;
	}

	public static void createTestFile(String fileName) throws IOException {
		Files.write(Paths.get(fileName), TEST_STR.getBytes());
	}
	public static void createTestFile1(String fileName) throws IOException {
		Files.write(Paths.get(fileName), TEST_STR1.getBytes());
	}
	public static void createTestFile2(String fileName1, String fileName2) throws IOException {
		Files.write(Paths.get(fileName1), COMM_FILE1_CONTENT.getBytes());
		Files.write(Paths.get(fileName2), COMM_FILE2_CONTENT.getBytes());
	}
	public static void removeTestFile(String fileName) throws IOException {
		File file = new File(fileName);
		file.setWritable(true);
		file.delete();
	}

	public static void createTestFolder(String folderName) throws IOException {
		new File(folderName).mkdir();
	}

	public static void removeTestFolder(String folderName) throws IOException {
		File file = new File(folderName);

		String[] entries = file.list();
		if (entries != null) {
			for (String s : entries) {
				File currentFile = new File(file.getPath(), s);
				currentFile.delete();
			}
		}
		file.delete();
		Environment.currentDirectory = originalFilePath;
	}

	public void writeToStream(OutputStream myoutputStream) throws IOException {
		myoutputStream.write(TEST_STR.getBytes());
		myoutputStream.flush();
		myoutputStream.close();
	}

	public String fileToString(String fileName) throws FileNotFoundException {
		scanner = new Scanner(new File(fileName));
		String fileStr = scanner.useDelimiter("\\Z").next();
		scanner.close();

		return fileStr;
	}


	///////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////
	//////////////////////// Hackathon Test Cases and Bug Reports /////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Hacking Team:
	 * The bug is due to not handling the Number Format Exception when integers in the file are
	 * beyond maximum. Instead of sort exception, number format is thrown.
	 * @
	 * Developer Team:
	 * This is a bug but it should not be throwing any exception. Instead, it should be able to sort the contents in the file
	 */
	@Test
	public void testInvalidSortFirstWordAsNumberVeryLargeInteger() throws SortException {
			List<String> arrayList = sortApplication.sortNumbersWithNumFlagOn(testArr);
			assertEquals("1", arrayList.get(0));
			assertEquals("23", arrayList.get(1));
			assertEquals("100000000000000000000000000000000000000000000000000000000000000000", arrayList.get(2));
	}
	

	/**
	 * The bug is due to unable to run one of the call commands(either
	 * bc or cat) which results in an exception.
	 * The input is valid so it is not supposed to throw an exception
	 * 
	 */
	@Test
	public void testPipeWithBcCat() throws IOException {
		try {
			baos.reset();
			String input = "bc 1.5*2  | cat";
			shell.parseAndEvaluate(input, baos);
			// Original from other team is 3 but the correct result
			// should be 3.0 since its a float multiplication.
			String expected =  "3.0" + System.lineSeparator();

			String output = new String(baos.toByteArray());
			assertEquals(expected, output);			
		} catch (ShellException e) {
			fail(VALID_CMD_NO_EXP);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(VALID_CMD_NO_EXP);
		}
	}
	
	/**
	 * The bug is due to not able to handle floating point results for bc.
	 * According to project specs, bc should return the correct result of certain math expression
	 * Therefore it is a bug.
	 */
	@Test
	public void testBcWithFloatingPoint() {
		String[] args = {"7.5/2"};
		try {
			baos.reset();
			bcApp.run(args, null, baos);
			String expected = "3.75" + System.lineSeparator();
			String output = new String(baos.toByteArray());
			assertEquals(expected,output);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * The bug is due to read the encoded file wrongly when reading a file using 
	 * a different encoding type.
	 * Marked as invalid in rebuttal.
	 */
	public void testReadFromFileWithDifferentEncoding() throws CatException, IOException {

		String[] args = new String[] { "test/HackathonTestCasesAndBugReports/Korean.txt" };
		StringBuilder expected = new StringBuilder();
		expected.append("별빛이 사라진 그때 너의 손을 잡았어").append(System.lineSeparator())
		.append(System.lineSeparator());

		try {
			baos.reset();
			catApp.run(args, null, baos);
			assertEquals(expected.toString(), baos.toString());
		} catch (CatException e) {
			e.printStackTrace();
			fail(VALID_FILE_NO_EXP);
		}

	}
	
	/**
	 * This bug occurs as there is no check for whether the argument passed is a file or not.
	 * Marked as invalid in rebuttal.
	 */
    public void testFileIsDirException() {
        File fileDir = new File("tempCatDir");
        fileDir.mkdir();
        CatApplication catApp = new CatApplication();
        String[] args = new String[] { "tempCatDir" };
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        
        try {
            catApp.run(args, null, outStream);
        } catch (CatException e) {
            String exceptionMsg = "cat: " + "This is a directory";
            assertEquals(exceptionMsg, e.getMessage());
        }

        fileDir.delete();
    }

}
