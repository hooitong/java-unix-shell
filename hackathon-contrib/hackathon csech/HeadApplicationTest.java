package ComSubHeadEchoCat;


import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.HeadException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;
import sg.edu.nus.comp.cs4218.impl.app.HeadApplication;

public class HeadApplicationTest {
	private static final String TEXTINPUT2_TXT = "textInput2.txt";
	private static final String TEXTINPUT_TXT = "textInput.txt";
	static HeadApplication hApp;

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
		hApp = new HeadApplication();
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

	/*
	 * Bug report:
	 * 
	 * Output received: NullPointerException not caught
	 * Expected output: 5 that the file contain
	 * Since number of lines of file to read is not stated, the application has expected to read 10 lines
	 * However it should have stopped at the end of file.
	 * Error: at line 130 of head application, end while loop when readline is null
	 * 
	 */
	
	@Test
	public void applicationDidNotStopReadingAfterEOF() {
		filePath += TEXTINPUT_TXT;
		try {
			input = new FileInputStream(filePath);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			hApp.run(null, input, output);
			assertEquals(output.toString(), expectedOutput1());
		} catch (HeadException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Bug report:
	 * 
	 * Error: NullPointerException not caught
	 * Expected output: entire 16 lines of the text file
	 * Even though user requires 17 lines of the file, the application is suppose to stop reading at 16th line or catch
	 * the nullpointerexception while trying to read the 17th line
	 * Error: at line 130 of head application, end while loop when readline is null
	 * 
	 */
	@Test
	public void applicationDidNotStopReadingAfterEOF2() {
		args = new String[2];
		args[0] = "-n";
		args[1] = "17";
		filePath += TEXTINPUT2_TXT;
		try {
			input = new FileInputStream(filePath);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			hApp.run(args, input, output);
			assertEquals(output.toString(), expectedOutput3());
		} catch (HeadException e) {
			e.printStackTrace();
		}
	}
	
	//This test case is only to show that error only occurs if user is reading from input stream
			@Test
			public void applicationDidNotStopReadingAfterEOF3() throws Exception {
				String command = "cat examples//textInput2.txt | head -n 17 ";
				shell.parseAndEvaluate(command, output);
				System.out.println(output.toString());
			}
			

	/*
	 * Bug report:
	 * 
	 * No error caught/exception thrown when arg is length 2 but contain no inputstream or filename
	 * 
	 * Error is because inputstream is always created whenever there's head command.
	 * Hence inputstream is read as empty but not as null.
	 * So developer can either change the readline to stop as no new line to read
	 * or check if inputstream is empty instead of null
	 * or do not create a new inputstream when calling for headapp
	 * 
	 *
	 */
	@Test
	public void noInputFileNotDetected() throws Exception {
		try{
			String command = "head -n 5";
			shell.parseAndEvaluate(command, output);
			
			//Uncomment below to check if the system is actually still running but waiting for next command.
			//command = "head -n 17 textInput2.txt";
			//shell.parseAndEvaluate(command, output);
			
			System.out.println(output.toString());
			assertEquals(output.toString(), "");
			System.out.println("If this line is not printed, there is an error because your system has crash:)");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	String expectedOutput1() {
		return "Roses are red," + System.lineSeparator() + "Violets are blue," + System.lineSeparator()
				+ "All of my base," + System.lineSeparator() + "Are belong to you." + System.lineSeparator()
				+ "//end of file" + System.lineSeparator();
	}

	String expectedOutput3() {
		return "1 Roses are red," + System.lineSeparator() + "2 Violets are blue," + System.lineSeparator()
				+ "3 All of my base," + System.lineSeparator() + "4 Are belong to you." + System.lineSeparator()
				+ "5 Roses are red," + System.lineSeparator() + "6 Violets are blue," + System.lineSeparator()
				+ "7 Some poems rhyme" + System.lineSeparator() + "8 But this one doesn't." + System.lineSeparator()
				+ "9 Roses are red," + System.lineSeparator() + "10 Violets are blue," + System.lineSeparator()
				+ "11 Make me a sandwich," + System.lineSeparator() + "12 Or I will kill you." + System.lineSeparator()
				+ "13 Roses are grey," + System.lineSeparator() + "14 Violets are grey," + System.lineSeparator()
				+ "15 I'm color blind." + System.lineSeparator() + "16 from http://uncyclopedia.wikia.com/wiki/Poetry"
				+ System.lineSeparator();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		filePath = null;
		args = null;
		output = null;
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		hApp = null;
		shell = null;
		

	}
}
