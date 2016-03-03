package sg.edu.nus.comp.cs4218.impl;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Command;
import sg.edu.nus.comp.cs4218.Shell;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.cmd.CallCommand;
import sg.edu.nus.comp.cs4218.impl.cmd.SequenceCommand;

public class ShellImplTest {
	/**
	 * Test whether the application can handle a valid execution of running an
	 * application.
	 *
	 * @throws Exception
	 */
	@Test
	public void testRunAppValid() throws Exception {
		try {
			ShellImpl.runApp("cat", null, null, null);
		} catch (AbstractApplicationException e) {
			/* Ignore the testing of application themselves */
		}
	}

	/**
	 * Test whether the application can handle invalid app execution by throwing
	 * the expected exception gracefully.
	 *
	 * @throws Exception
	 */
	@Test(expected = ShellException.class)
	public void testRunAppInvalid() throws Exception {
		ShellImpl.runApp("apple", null, null, null);
	}

	/**
	 * Test whether the application can properly parse a valid call command and
	 * return as a command object.
	 *
	 * @throws Exception
	 */
	@Test
	public void testParseCallCommand() throws Exception {
		String cmdline = "echo /apple/martini/10123/a.txt";
		Command mockCommand = ShellImpl.parse(cmdline);
		assertTrue(mockCommand instanceof CallCommand);
	}

	/**
	 * Test whether the application can properly parse a valid sequence command
	 * and return as a command object.
	 *
	 * @throws Exception
	 */
	@Test
	public void testParseSingleSequenceCommand() throws Exception {
		String cmdline = "echo /apple/ti/*.txt; cat martini/*";
		Command mockCommand = ShellImpl.parse(cmdline);
		assertTrue(mockCommand instanceof SequenceCommand);
	}

	/**
	 * Test whether the application can properly parse command that contains
	 * nested sequence commands and return the correct parent command object.
	 *
	 * @throws Exception
	 */
	@Test
	public void testParseMultiSequenceCommand() throws Exception {
		String cmdline = "tail *.txt ; cat apple/* ;echo *";
		Command mockCommand = ShellImpl.parse(cmdline);
		assertTrue(mockCommand instanceof SequenceCommand);
	}

	/**
	 * Test whether the application can handle invalid commands graceful and
	 * return the expected exception.
	 *
	 * @throws Exception
	 */
	@Test(expected = ShellException.class)
	public void testParseInvalidCommand() throws Exception {
		String cmdline = "test; ";
		Command mockCommand = ShellImpl.parse(cmdline);
		fail("Should be throwing exception due to invalid syntax");
	}
}
