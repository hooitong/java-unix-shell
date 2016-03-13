package sg.edu.nus.comp.cs4218.impl.cmd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.nio.file.Paths;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

public class CallCommandTest {
	private static final String TEMPLATE_ARGS = " %s ";
	private Vector<String> testVector;
	private CallCommand stubCommand;

	@Before
	public void setUp() {
		testVector = new Vector<>();
		stubCommand = new CallCommand("");
	}

	/**
	 * Side-effect based method. Test whether command be can executed normally.
	 *
	 * @throws Exception
	 */
	@Test
	public void testEvaluate() throws Exception {
		String expectedArgument = "test.txt";
		String mockApplication = "echo";
		String cmdline = mockApplication + " " + expectedArgument;
		CallCommand testCommand = new CallCommand(cmdline);
		ByteArrayOutputStream stubOutput = new ByteArrayOutputStream();
		testCommand.parse();
		testCommand.evaluate(System.in, stubOutput);
		String evaluatedString = new String(stubOutput.toByteArray(), "UTF-8").trim();
		assertTrue(evaluatedString.equals(expectedArgument));
	}

	/**
	 * Side-effect based method. Test whether command can parse command lines
	 * properly.
	 *
	 * @throws Exception
	 */
	@Test
	public void testParse() throws Exception {
		String expectedArgument = "apple/3222.txt";
		String mockApplication = "cat";
		String cmdline = mockApplication + " " + expectedArgument;
		CallCommand testCommand = new CallCommand(cmdline);
		testCommand.parse();
		assertTrue(testCommand.argsArray.length == 1);
		assertTrue(testCommand.argsArray[0].equals(expectedArgument));
		assertTrue(testCommand.app.equals(mockApplication));
	}

	/**
	 * Test whether simple arguments (without quotes) can be extracted properly.
	 *
	 * @throws Exception
	 */
	@Test
	public void testExtractArgs() throws Exception {
		String expectedArgument = "apple/3222.txt";
		String cmdline = "cat " + expectedArgument;
		stubCommand.extractArgs(String.format(TEMPLATE_ARGS, cmdline), testVector);
		assertTrue(testVector.size() == 2);
		assertTrue(testVector.get(1).equals(expectedArgument));
	}

	/**
	 * Test whether single quotes can be properly extracted into arguments.
	 * Special characters within quotes must not be interpreted as keywords.
	 *
	 * @throws Exception
	 */
	@Test
	public void testExtractArgsSingleQuotes() throws Exception {
		String expectedArgument = "apple is good for health ; |";
		String cmdline = "echo '" + expectedArgument + "'";
		stubCommand.extractArgs(String.format(TEMPLATE_ARGS, cmdline), testVector);
		assertTrue(testVector.size() == 2);
		assertTrue(testVector.get(1).equals(expectedArgument));
	}

	/**
	 * Test whether double quotes can be properly extracted into arguments.
	 * Special characters within quotes must not be interpreted as keywords.
	 *
	 * @throws Exception
	 */
	@Test
	public void testExtractArgsDoubleQuotes() throws Exception {
		String expectedArgument = "orange is | not good ; for health ";
		String cmdline = "echo \"" + expectedArgument + "\"";
		Vector<String> testVector = new Vector<>();
		stubCommand.extractArgs(String.format(TEMPLATE_ARGS, cmdline), testVector);
		assertTrue(testVector.size() == 2);
		assertTrue(testVector.get(1).equals(expectedArgument));
	}

	/**
	 * Test whether back quotes can be properly extracted into arguments
	 *
	 * @throws Exception
	 */
	@Test
	public void testExtractArgsBackQuotes() throws Exception {
		String expectedArgument = "`echo a`";
		String cmdline = "echo " + expectedArgument;
		stubCommand.extractArgs(String.format(TEMPLATE_ARGS, cmdline), testVector);
		assertTrue(testVector.size() == 2);
		assertTrue(testVector.get(1).equals(expectedArgument));
	}

	/**
	 * Test combination of single and double quote in one extraction.
	 *
	 * @throws Exception
	 */
	@Test
	public void testExtractArgsSingleDoubleQuotes() throws Exception {
		String expectedArgOne = "apple \" 123 \" prince";
		String expectedArgTwo = "caitlyn 'killer frost' snow";
		String cmdline = "cat '" + expectedArgOne + "' \"" + expectedArgTwo + "\"";
		stubCommand.extractArgs(String.format(TEMPLATE_ARGS, cmdline), testVector);
		assertTrue(testVector.size() == 3);
		assertTrue(testVector.get(1).equals(expectedArgOne));
		assertTrue(testVector.get(2).equals(expectedArgTwo));
	}

	/**
	 * Test combination of double and back quote in one extraction.
	 *
	 * @throws Exception
	 */
	@Test
	public void testExtractArgsDoubleBackQuotes() throws Exception {
		String expectedArgOne = "`sweet 322throw ' 123 \" prince`";
		String expectedArgTwo = "mel lisa `echo super-girl` benoist";
		String cmdline = "cat " + expectedArgOne + " \"" + expectedArgTwo + "\"";
		stubCommand.extractArgs(String.format(TEMPLATE_ARGS, cmdline), testVector);
		;
		assertTrue(testVector.size() == 3);
		assertTrue(testVector.get(1).equals(expectedArgOne));
		assertTrue(testVector.get(2).equals(expectedArgTwo));
	}

	/**
	 * Test combination of single and back quote in one extraction.
	 *
	 * @throws Exception
	 */
	@Test
	public void testExtractArgsSingleBackQuotes() throws Exception {
		String expectedArgOne = "`test monster \" kappa 123 ' jelly`";
		String expectedArgTwo = "barry `flash earth-1 ` AllEn";
		String cmdline = "echo " + expectedArgOne + " '" + expectedArgTwo + "'";
		stubCommand.extractArgs(String.format(TEMPLATE_ARGS, cmdline), testVector);
		assertTrue(testVector.size() == 3);
		assertTrue(testVector.get(1).equals(expectedArgOne));
		assertTrue(testVector.get(2).equals(expectedArgTwo));
	}

	/**
	 * Test whether the input redirection can be parsed from the given command
	 * line.
	 *
	 * @throws Exception
	 */
	@Test
	public void testExtractInputRedir() throws Exception {
		String stringToTest = "sort -n < file1.txt > file2.txt";
		Vector<String> cmdVector = new Vector<String>();
		cmdVector.addElement("");
		cmdVector.addElement("");
		stubCommand.extractInputRedir(stringToTest, cmdVector, 8);
		assertEquals(cmdVector.get(0), "file1.txt");
	}

	/**
	 * Test whether the output redirection can be parsed from the given command
	 * line.
	 *
	 * @throws Exception
	 */
	@Test
	public void testExtractOutputRedir() throws Exception {
		String stringToTest = "sort -n < file1.txt > file2.txt";
		Vector<String> cmdVector = new Vector<String>();
		cmdVector.addElement("");
		cmdVector.addElement("");
		stubCommand.extractOutputRedir(stringToTest, cmdVector, 20);
		assertEquals(cmdVector.get(1), "file2.txt");
	}

	/**
	 * Test whether the method can process an array of valid arguments, evaluate
	 * globbing and return the newly replaced arguments.
	 *
	 * @throws Exception
	 */
	@Test
	public void testValidEvaluateGlob() throws Exception {
		String[] mockArgs = { "mock-glob-fs/*.txt", "mock-glob-fs/*/*/*.txt", "mock-glob-fs/21-herb/*" };
		String[] results = stubCommand.evaluateGlob(mockArgs);
		String firstArgOne = Paths.get("mock-glob-fs/quantum.txt").toAbsolutePath().toString();
		String secondArgOne = Paths.get("mock-glob-fs/21-herb/hola/Kappa.txt").toAbsolutePath().toString();
		String thirdArgOne = Paths.get("mock-glob-fs/21-herb/hola").toAbsolutePath().toString();
		String thirdArgTwo = Paths.get("mock-glob-fs/21-herb/sideload.txt").toAbsolutePath().toString();
		assertTrue(results.length == 4);
		assertTrue(firstArgOne.equals(results[0]));
		assertTrue(secondArgOne.equals(results[1]));
		assertTrue(thirdArgOne.equals(results[2]));
		assertTrue(thirdArgTwo.equals(results[3]));
	}

	/**
	 * Test whether the method can process arguments that contains single and
	 * double quotes and not evaluate globbing on them and return the original
	 * argument.
	 *
	 * @throws Exception
	 */
	@Test
	public void testQuotedEvaluateGlob() throws Exception {
		String singleQuote = "'mock-glob-fs/*.txt'";
		String doubleQuote = "\"mock-glob-fs/21-herb/hola\"";
		String mixedQuotes = "'mock-glob-fs/\"*.txt\"'";
		String quotedFile = "mock-glob-fs/tango/'.*";
		String[] mockArgs = { singleQuote, doubleQuote, mixedQuotes, quotedFile };
		String[] results = stubCommand.evaluateGlob(mockArgs);
		String quotedFileResult = Paths.get("mock-glob-fs/tango/'.json").toAbsolutePath().toString();
		assertTrue(results.length == 4);
		assertTrue(singleQuote.equals(results[0]));
		assertTrue(doubleQuote.equals(results[1]));
		assertTrue(mixedQuotes.equals(results[2]));
		assertTrue(quotedFileResult.equals(results[3]));
	}

	/**
	 * Test whether the method can process arguments that contains asterisks but
	 * cannot be matched or invalid syntax and return the original arguments.
	 *
	 * @throws Exception
	 */
	@Test
	public void testInvalidEvaluateGlob() throws Exception {
		String invalidFile = "mock-glob-fs/*.kat";
		String noGlobArg = "mock-glob-fs/quantum.txt";
		String[] mockArgs = { invalidFile, noGlobArg };
		String[] results = stubCommand.evaluateGlob(mockArgs);
		assertTrue(results.length == 1);
		assertTrue(noGlobArg.equals(results[0]));
	}

	/**
	 * Test whether the current execution of the command can be terminated
	 * correctly.
	 *
	 * @throws Exception
	 */
	@Test
	public void testTerminate() throws Exception {
		fail("Not yet implemented");
	}
}
