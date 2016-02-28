package sg.edu.nus.comp.cs4218.impl.cmd;

import java.io.ByteArrayOutputStream;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CallCommandTest {
	private static final String TEMPLATE_EXTRACT_ARGS = " %s ";
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
		assert (evaluatedString.equals(expectedArgument));
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
		assert (testCommand.argsArray.length == 1);
		assert (testCommand.argsArray[0].equals(expectedArgument));
		assert (testCommand.app.equals(mockApplication));
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
		stubCommand.extractArgs(String.format(TEMPLATE_EXTRACT_ARGS, cmdline), testVector);
		assert (testVector.size() == 2);
		assert (testVector.get(1).equals(expectedArgument));
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
        stubCommand.extractArgs(String.format(TEMPLATE_EXTRACT_ARGS, cmdline), testVector);
		assert (testVector.size() == 2);
		assert (testVector.get(1).equals(expectedArgument));
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
        stubCommand.extractArgs(String.format(TEMPLATE_EXTRACT_ARGS, cmdline), testVector);
		assert (testVector.size() == 2);
		assert (testVector.get(1).equals(expectedArgument));
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
        stubCommand.extractArgs(String.format(TEMPLATE_EXTRACT_ARGS, cmdline), testVector);
		assert (testVector.size() == 2);
		assert (testVector.get(1).equals(expectedArgument));
	}

	/**
	 * Test combination of single and double quote in one extraction.
	 *
	 * @throws Exception
	 */
	@Test
	public void testExtractArgsSingleDoubleQuotes() throws Exception {
		String expectedArgumentOne = "apple \" 123 \" prince";
		String expectedArgumentTwo = "caitlyn 'killer frost' snow";
		String cmdline = "cat '" + expectedArgumentOne + "' \"" + expectedArgumentTwo + "\"";
        stubCommand.extractArgs(String.format(TEMPLATE_EXTRACT_ARGS, cmdline), testVector);
		assert (testVector.size() == 3);
		assert (testVector.get(1).equals(expectedArgumentOne));
		assert (testVector.get(2).equals(expectedArgumentTwo));
	}

	/**
	 * Test combination of double and back quote in one extraction.
	 *
	 * @throws Exception
	 */
	@Test
	public void testExtractArgsDoubleBackQuotes() throws Exception {
		String expectedArgumentOne = "`sweet 322throw ' 123 \" prince`";
		String expectedArgumentTwo = "mel lisa `echo super-girl` benoist";
		String cmdline = "cat " + expectedArgumentOne + " \"" + expectedArgumentTwo + "\"";
        stubCommand.extractArgs(String.format(TEMPLATE_EXTRACT_ARGS, cmdline), testVector);;
		assert (testVector.size() == 3);
		assert (testVector.get(1).equals(expectedArgumentOne));
		assert (testVector.get(2).equals(expectedArgumentTwo));
	}

	/**
	 * Test combination of single and back quote in one extraction.
	 *
	 * @throws Exception
	 */
	@Test
	public void testExtractArgsSingleBackQuotes() throws Exception {
		String expectedArgumentOne = "`test monster \" kappa 123 ' jelly`";
		String expectedArgumentTwo = "barry `flash earth-1 ` AllEn";
		String cmdline = "echo " + expectedArgumentOne + " '" + expectedArgumentTwo + "'";
        stubCommand.extractArgs(String.format(TEMPLATE_EXTRACT_ARGS, cmdline), testVector);
		assert (testVector.size() == 3);
		assert (testVector.get(1).equals(expectedArgumentOne));
		assert (testVector.get(2).equals(expectedArgumentTwo));
	}

	/**
	 * Test whether the input redirection can be parsed from the given command
	 * line.
	 *
	 * @throws Exception
	 */
	@Test
	public void testExtractInputRedir() throws Exception {
		String stringToTest = "sort ­n < file1.txt > file2.txt";
		Vector<String> cmdVector = new Vector<String>();
		cmdVector.addElement("");
		cmdVector.addElement("");
        stubCommand.extractInputRedir(stringToTest, cmdVector, 8);
        assertEquals(cmdVector.get(0),"file1.txt");
	}

	/**
	 * Test whether the output redirection can be parsed from the given command
	 * line.
	 *
	 * @throws Exception
	 */
	@Test
	public void testExtractOutputRedir() throws Exception {
		String stringToTest = "sort ­n < file1.txt > file2.txt";
		Vector<String> cmdVector = new Vector<String>();
		cmdVector.addElement("");
		cmdVector.addElement("");
        stubCommand.extractOutputRedir(stringToTest, cmdVector, 20);
        assertEquals(cmdVector.get(1),"file2.txt");
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
