package commandsubstituition;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.exception.TailException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;



public class MultipleCmdSubTest {
	private ShellImpl shell;
	private ByteArrayOutputStream stdout;
	private final static String NEW_LINE = System.lineSeparator();
	private static final String FOLDER_LOCATION = "examples-integration/Multiple-CmdSub/";

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setUp() throws Exception {
		shell = new ShellImpl();
		stdout = new ByteArrayOutputStream();
	}

	/**
	 * Test the command substituition evaluation from sort (head(files.txt))
	 * 
	 * @throws AbstractApplicationException
	 * @throws ShellException
	 */
	@Test
	public void testSubFileName() throws AbstractApplicationException,
			ShellException {
		String cmd = "sort `cat " + FOLDER_LOCATION + "files.txt | head -n 1`";
		shell.parseAndEvaluate(cmd, stdout);
		String expected = "grass" + NEW_LINE + "muse";
		assertEquals(expected, stdout.toString());
	}
	
	/**
	 * Test the command substituition evaluation from sort (head(files.txt))
	 * 
	 * @throws AbstractApplicationException
	 * @throws ShellException
	 */
	@Test
	public void testSubCommand() throws AbstractApplicationException,
			ShellException {
		String cmd = "`cat " + FOLDER_LOCATION + "commands2.txt`";
		shell.parseAndEvaluate(cmd, stdout);
		String expected = "sort1.txt" + NEW_LINE;
		assertEquals(expected, stdout.toString());
	}
	
	/**
	 * Test the command substituition evaluation from sort (head(files.txt))
	 * 
	 * @throws AbstractApplicationException
	 * @throws ShellException
	 */
	@Test
	public void testCmdSubOnlyQuotes() throws AbstractApplicationException,
			ShellException {
		String cmd = "`cat " + FOLDER_LOCATION + "commands.txt | tail -n 1`";
		shell.parseAndEvaluate(cmd, stdout);
		String expected = "grass" + NEW_LINE;
		assertEquals(expected, stdout.toString());
	}
	
	/**
	 * Test the command substituition evaluation for IO files
	 * 
	 * @throws AbstractApplicationException
	 * @throws ShellException
	 */
	@Test
	public void testSubIOFile() throws AbstractApplicationException,
			ShellException {
		String cmd = "sort -n < `cat " + FOLDER_LOCATION + "redirection1.txt | head";
		shell.parseAndEvaluate(cmd, stdout);
		String expected = "animal" + NEW_LINE + "kangaroo" + NEW_LINE + "zoo" + NEW_LINE;
		assertEquals(expected, stdout.toString());
	}
	
	/**
	 * Test the command substituition evaluation from flag 
	 * 
	 * @throws AbstractApplicationException
	 * @throws ShellException
	 */
	@Test
	public void testSubFlag() throws AbstractApplicationException,
			ShellException {
		String cmd = "head `cat " + FOLDER_LOCATION + "flag.txt | fmt` " + FOLDER_LOCATION + "sort1.txt ";
		shell.parseAndEvaluate(cmd, stdout);
		String expected = "muse" + NEW_LINE;
		assertEquals(expected, stdout.toString());
	}
	
	/**
	 * Test the command substituition evaluation for empty substituition
	 * 
	 * @throws AbstractApplicationException
	 * @throws ShellException
	 */
	@Test
	public void testEmptyCmdSub() throws AbstractApplicationException,
			ShellException {
		String cmd = "echo ``";
		shell.parseAndEvaluate(cmd, stdout);
		String expected = NEW_LINE;
		assertEquals(expected, stdout.toString());
	}

	
	/**
	 * Test the command substituition evaluation for invalid flag used on tail
	 * 
	 * @throws AbstractApplicationException
	 * @throws ShellException
	 */
	@Test
	public void testNegInvalidFlag() throws AbstractApplicationException,
			ShellException {
		exception.expect(ShellException.class);
		//exception.expectMessage("Number of lines not a number");
		
		String cmd = "echo `tail -a 1 sort1.txt`";
		shell.parseAndEvaluate(cmd, stdout);
	}
	
	/**
	 * Test the command substituition evaluation for invalid flag used on tail
	 * 
	 * @throws AbstractApplicationException
	 * @throws ShellException
	 */
	@Test
	public void testNegMissingFile() throws AbstractApplicationException,
			ShellException {
		exception.expect(ShellException.class);
		//exception.expectMessage("Number of lines not a number");
		
		String cmd = "sort `tail -n 1 files2.txt`";
		shell.parseAndEvaluate(cmd, stdout);
	}

}