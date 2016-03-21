package sg.edu.nus.comp.cs4218.impl;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.util.Vector;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.cmd.CallCommand;

public class RedirectionTest {
	private CallCommand stubCommand;
	private ShellImpl shell;
	private ByteArrayOutputStream baos;
	private static final String FOLDER_LOCATION = "examples-integration/multiple-pipe/";

	private static final String NEW_LINE = System.lineSeparator();

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setUp() {
		stubCommand = new CallCommand("");
		shell = new ShellImpl();
		baos = new ByteArrayOutputStream();
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

	/*
	 * Test whether the IO redirection works for the sort app at shell level
	 * line.
	 *
	 * @throws Exception
	 */
	@Test
	public void testSortRedir() throws Exception {
		shell.parseAndEvaluate("sort -n < examples/redirectInput1.txt > examples/redirectOutput1.txt", null);
		shell.parseAndEvaluate("tail -n 1 < examples/redirectOutput1.txt", baos);
		assertEquals("zackary" + NEW_LINE, new String(baos.toByteArray()));
	}

	/*
	 * Test whether the IO redirection works for the tail app at shell level
	 * line.
	 *
	 * @throws Exception
	 */
	@Test
	public void testTailRedir() throws Exception {
		shell.parseAndEvaluate("tail -n 1 < examples/redirectOutput2.txt", baos);
		assertEquals("This is the last line" + NEW_LINE, new String(baos.toByteArray()));
	}

	/*
	 * Test whether the IO redirection works for the head app at shell level
	 * line.
	 *
	 * @throws Exception
	 */
	@Test
	public void testHeadRedir() throws Exception {
		shell.parseAndEvaluate("head -n 1 < examples/redirectOutput3.txt", baos);
		assertEquals("This is the first line" + System.lineSeparator(), new String(baos.toByteArray()));
	}

	/*
	 * Test whether the IO redirection works for a non-existant file.
	 * Pre-condition : redirectOutput4.txt does not exist in ./examples
	 *
	 * @throws Exception
	 */
	@Test
	public void testNonExistantOutputRedir() throws Exception {
		shell.parseAndEvaluate("sort -n < examples/redirectInput1.txt > examples/redirectOutput4.txt", baos);
		shell.parseAndEvaluate("tail -n 1 < examples/redirectOutput4.txt", baos);
		assertEquals("zackary" + NEW_LINE, new String(baos.toByteArray()));
	}

	/*
	 * Test whether the IO redirection works for a non-existant file.
	 * Pre-condition : redirectOutput4.txt does not exist in ./examples
	 *
	 * @throws Exception
	 */
	@Test
	public void testNonExistantInputRedir() throws Exception {
		exception.expect(ShellException.class);
		exception.expectMessage("The system cannot find the file specified");
		shell.parseAndEvaluate("tail -n 1 < examples/redirectOutputNE.txt", baos);
	}

	/*
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
	 * Test whether exception is raised for multiple output redirection
	 *
	 * @throws Exception
	 */
	@Test
	public void testMultipleOutputRedir() throws Exception {
		exception.expect(ShellException.class);
		exception.expectMessage("Invalid syntax encountered");

		String stringToTest = "sort -n < file1.txt > file2.txt > file3.txt";
		Vector<String> cmdVector = new Vector<String>();
		cmdVector.addElement("");
		cmdVector.addElement("");
		stubCommand.extractOutputRedir(stringToTest, cmdVector, 20);
	}

	/**
	 * Test whether exception is raised for multiple input redirection line.
	 *
	 * @throws Exception
	 */
	@Test
	public void testMultipleInputRedir() throws Exception {
		exception.expect(ShellException.class);
		exception.expectMessage("Invalid syntax encountered");

		String stringToTest = "sort -n < file1.txt < file2.txt > file3.txt";
		Vector<String> cmdVector = new Vector<String>();
		cmdVector.addElement("");
		cmdVector.addElement("");
		stubCommand.extractInputRedir(stringToTest, cmdVector, 8);
	}
}