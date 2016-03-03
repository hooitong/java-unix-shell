package sg.edu.nus.comp.cs4218.impl;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Shell;
import sg.edu.nus.comp.cs4218.exception.ShellException;

public class CommandSubstitutionTest {
	private Shell mockShell;
	private ByteArrayOutputStream mockOut;

	@Before
	public void setUp() {
		mockShell = new ShellImpl();
		mockOut = new ByteArrayOutputStream();
	}

	/**
	 * 
	 * Input: assume that it takes in a string of command Output: assume that it
	 * output the final result
	 * 
	 * Test case:
	 * 
	 * error: bad symbols error: no such command error: bad commands
	 * 
	 */
	@Test
	public void testRemoveNewLineSymbol() throws Exception {
		String command = "echo `echo a\n aa\n aaa\n aaaa`";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals(new String(mockOut.toByteArray(), Charset.defaultCharset()), "a aa aaa aaaa");
	}

	@Test
	public void testMultipleCommands() throws Exception {
		String command = "echo 12345 `echo 67890 `echo abcde``";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals(new String(mockOut.toByteArray(), Charset.defaultCharset()), "12345 67890echo abcde");
	}

	/**
	 * File looks like this
	 * 
	 * apple banana
	 */
	@Test
	public void testCommandAndOutputOneLine() throws Exception {
		String command = "echo `head -n 1 examples/file1.txt`";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals(new String(mockOut.toByteArray(), Charset.defaultCharset()), "apple");
	}

	@Test
	public void testCommandAndOutputWhereFirstLineIsSubstituteAsSpace() throws Exception {
		String command = "echo `head -n 2 examples/file1.txt`";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals(new String(mockOut.toByteArray(), Charset.defaultCharset()), "banana");
	}

	@Test(expected = ShellException.class)
	public void testMissingBackQuote() throws Exception {
		String command = "echo echo a\n aa\n aaa\n aaaa`";
		mockShell.parseAndEvaluate(command, mockOut);
	}

	// Error is cannot read 12345 given that README.md exist
	@Test(expected = ShellException.class)
	public void testNoSuchDirectory() throws Exception {
		String command = "sort `cat README.md | echo 12345`";
		mockShell.parseAndEvaluate(command, mockOut);
	}

	@Test(expected = ShellException.class)
	public void testInvalidCommand() throws Exception {
		String command = "echo `hed -n 1 README.md`";
		mockShell.parseAndEvaluate(command, mockOut);
	}
}
