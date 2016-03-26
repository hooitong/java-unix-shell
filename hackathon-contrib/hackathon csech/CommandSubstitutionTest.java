package ComSubHeadEchoCat;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Shell;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class CommandSubstitutionTest {
	private Shell mockShell;
	private ByteArrayOutputStream mockOut;

	@Before
	public void setUp() {
		mockShell = new ShellImpl();
		mockOut = new ByteArrayOutputStream();
	}

	/*
	 * Bug report: Newline removed by commandsub was not replaced by a space.
	 * After newline is removed by command sub, a space should have been
	 * replaced
	 * 
	 * Error: go to shellimpl line 72
	 */
	@Test
	public void newLineRemovedByCommandSubNotReplacedByASpace() throws Exception {
		String command = "echo `echo 12345` `head -n 2 examples//textInput2.txt`";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals(mockOut.toString(), "12345 1 Roses are red, 2 Violets are blue,\n");

	}

	/*
	 * Bug report:
	 * 
	 * Valid output before semicolon was lost during command substitution The
	 * content is displayed on console without the rest of the data
	 */

	@Test
	public void testSemicolon() throws Exception {
		String command = "echo testing 123: `echo showing content of file1.txt; cat examples\\file1.txt`";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals(mockOut.toString(),
				"testing 123: showing content of file1.txt apple banana eggplant" + System.lineSeparator());
	}

	/*
	 * THIS MIGHT NOT BE A BUG. BUT ABOVE ONE DEFINITELY IS. Bug report:
	 * 
	 * Semicolon operator failed Only second part of the semicolon is displayed
	 * o.o or am I not displaying this shit correctly?
	 */

	@Test
	public void testSemicolonSubTestA() throws Exception {
		String command = "echo showing contents of textInput.txt; cat examples//textInput.txt";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals(mockOut.toString(), "Showing content of textInput.txt\n" + "Roses are red,\n"
				+ "Violets are blue,\n" + "All of my base,\n" + "Are belong to you.\n" + "//end of file\n");

	}

	/*
	 * Bug report:
	 * 
	 * invalid app when running two command subs with piping
	 */

	@Test
	public void testMultipleCommands() throws Exception {
		String command = "echo `cat examples\\numbersort.txt| head -n 1` plus `cat examples\\numbersort.txt|sort|head -n 1` is `echo 65+1000|bc` ";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals(mockOut.toString(), "65 plus 1000 is 1065" + System.lineSeparator());

	}

	/*
	 * Bug report:
	 * 
	 * Valid app but mistakenly thrown by shell as invalid app
	 * 
	 * See Trace: sg.edu.nus.comp.cs4218.exception.ShellException: shell: `cat
	 * examples\numbersort.txt | sort`: Invalid app. ...
	 */
	@Test
	public void testMultipleCommandsSubTestA() throws Exception {
		String command = "echo `cat examples\\numbersort.txt| head -n 1` `cat examples\\numbersort.txt | sort`";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals(mockOut.toString(), "65 plus 1000 is 1065" + System.lineSeparator());

	}

	@Test
	public void testMultipleCommandsSubTestB() throws Exception {
		String command = "echo `cat examples\\numbersort.txt| head -n 1` `cat examples\\numbersort.txt | head -n 1`";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals(mockOut.toString(), "65 1000");

	}

	@Test
	public void testMultipleCommandsSubTestC() throws Exception {
		String command = "echo `head -n 1 examples\\numbersort.txt` `head -n 1 examples\\numbersort.txt`";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals(mockOut.toString(), "65 65\n");

	}

	/*
	 * Bug report:
	 * 
	 * As long as there's a pipe in the first com sub, the next com sub will be
	 * marked as invalid by the application
	 */
	@Test
	public void testMultipleCommandsSubTestD() throws Exception {
		String command = "echo `cat examples\\numbersort.txt| head -n 1` `head -n 1 examples\\numbersort.txt`";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals(mockOut.toString(), "65 65\n");

	}

	/*
	 * Bug report:
	 * 
	 * fmt failed. Wrap expected at max 80 characters per line. No wrapping
	 * observed.
	 */
	@Test
	public void testFMTWhenUsedWithPipeAndComSub() throws Exception {
		String command = "cat `head -n 1 examples\\type1` `head -n 1 examples\\type2` `head -n 1 examples\\type3` | sort | fmt";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals(mockOut.toString(),
				"//end of file All of my base, Are belong to you. Roses are red, Violets are" + System.lineSeparator()
						+ "blue, apple banana eggplant examples\\file1.txt examples\\file2.txt" + System.lineSeparator()
						+ "examples\\file3.txt" + System.lineSeparator());

	}

	/*
	 * Bug report: unexpected ShellException
	 */

	@Test
	public void errorWhenSemiColonIsUsed() throws Exception {
		String command = "cat `cat examples\\comsub1.txt| head -n 1`; cat `cat examples\\comsub2.txt| head -n 1`";
		mockShell.parseAndEvaluate(command, mockOut);
		// System.out.println(mockOut.toString());
		assertEquals(mockOut.toString(),
				"apple" + System.lineSeparator() + "banana" + System.lineSeparator() + "eggplant65"
						+ System.lineSeparator() + "9" + System.lineSeparator() + "1000" + System.lineSeparator() + "23"
						+ System.lineSeparator() + "11");

	}
	
	@After
	public void tearDown() throws Exception {
		
		mockShell = null;
		mockOut = null;
	}

}