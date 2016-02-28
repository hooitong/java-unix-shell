import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.ShellException;

public class testCommandsubstitionx {

	CommandSubstition cs = new CommandSubstition();

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
	public void testRemoveNewLineSymbol() throws ShellException {
		String command = "echo `echo a\n aa\n aaa\n aaaa`";
		String result = cs.run(command);
		assertEquals(result, "a aa aaa aaaa");
	}

	@Test
	public void testMultipleCommands() throws ShellException {
		String command = "echo 12345 `echo 67890 `echo abcde``";
		String result = cs.run(command);
		assertEquals(result, "12345 67890echo abcde");
	}

	/*
	 * File looks like this
	 * 
	 * apple banana
	 */
	@Test
	public void testCommandAndOutputOneLine() throws ShellException {
		String command = "echo `head -n 1 file1.txt";
		String result = cs.run(command);
		assertEquals(result, "apple");
	}

	@Test
	public void testCommandAndOutputWhereFirstLineIsSubstituteAsSpace() throws ShellException {
		String command = "echo `head -n 2 file1.txt";
		String result = cs.run(command);
		assertEquals(result, " banana");
	}

	@Test(expect = ShellException.class)
	public void testMissingBackQuote() throws ShellException {
		String command = "echo echo a\n aa\n aaa\n aaaa`";
		String result = cs.run(command);
	}

	// Error is cannot read 12345 given that README.md exist
	@Test(expect = ShellException.class)
	public void testNoSuchDirectory() throws ShellException {
		String command = "sort `cat README.md | echo 12345`";
		String result = cs.run(command);
	}

	@Test(expect = ShellException.class)
	public void testInvalidCommand() throws ShellException {
		String command = "echo `hed -n 1 README.md`";
		String result = cs.run(command);
	}

}
