package sg.edu.nus.comp.cs4218.impl;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Shell;
import sg.edu.nus.comp.cs4218.exception.CatException;
import sg.edu.nus.comp.cs4218.exception.HeadException;
import sg.edu.nus.comp.cs4218.exception.PipeCommandException;

public class CommandSubstitutionTest {
	private Shell mockShell;
	private ByteArrayOutputStream mockOut;

	@Before
	public void setUp() {
		mockShell = new ShellImpl();
		mockOut = new ByteArrayOutputStream();
	}

	/*
	 * Test basic command substitution - up to one layer of nesting The
	 * following tests ensures that each different command can work with command
	 * substitution properly
	 * 
	 */

	// Test that echo can be command substituted

	@Test
	public void testCommandSubEcho() throws Exception {
		String command = "echo `echo applepie lemon juice`";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals(mockOut.toString(), "applepie lemon juice" + System.lineSeparator());
	}

	// Test for multiple command substitution in a single command.
	@Test
	public void testCommandSubEcho2() throws Exception {
		String command = "echo `echo lemon juice` `echo applepie`";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals(mockOut.toString(), "lemon juice applepie" + System.lineSeparator());
	}

	// Test that cat command can be substituted
	@Test
	public void testCommandSubCat() throws Exception {
		String command = "echo `cat examples\\file1.txt`";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals(mockOut.toString(), "apple banana eggplant" + System.lineSeparator());
	}

	/**
	 * Test whether exception is caught when error in cat command
	 * 
	 */
	@Test(expected = CatException.class)
	public void testCommandSubCatWithError() throws Exception {
		String command = "echo `cat noSuchFile.txt`";
		mockShell.parseAndEvaluate(command, mockOut);
	}

	// Test sub head
	@Test
	public void testCommandSubHead() throws Exception {
		String command = "echo `head -n 1 examples\\file1.txt`";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals(mockOut.toString(), "apple" + System.lineSeparator());
	}

	/*
	 * Test whether exception is caught in application when error in head
	 * command
	 */
	@Test(expected = HeadException.class)
	public void testCommandSubHeadWithError() throws Exception {
		String command = "echo `head examples\\file1.txt -n 1`";
		mockShell.parseAndEvaluate(command, mockOut);
	}

	/*
	 * Test that pipe and sort works in command substitution
	 */
	@Test
	public void testCommandSubSort() throws Exception {

		String command = "echo `cat examples\\numbersort.txt | sort`";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals(mockOut.toString(), "1000 11 23 65 9" + System.lineSeparator());

	}

	/*
	 * Test for correct line read when reading a file and then piping to head
	 */
	@Test
	public void testCommandPipe() throws Exception {
		String command = "echo `cat examples\\file1.txt | head -n 2`";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals(mockOut.toString(), "apple banana" + System.lineSeparator());
	}

	/*
	 * Test for error caught in pipe while command substituting
	 */
	@Test(expected = PipeCommandException.class)
	public void testCommandPipe2() throws Exception {
		String command = "echo `cat nosuchfile.txt | head -n 2`";
		mockShell.parseAndEvaluate(command, mockOut);
		System.out.println(mockOut.toString());
	}

	/*
	 * Test for error caught in pipe while command substituting
	 */
	@Test(expected = PipeCommandException.class)
	public void testCommandPipe3() throws Exception {
		String command = "echo `cat examples\\file1.txt | head n -2`";
		mockShell.parseAndEvaluate(command, mockOut);
	}

	/*
	 * Semicolon operator
	 */
	@Test
	public void testSemicolon() throws Exception {
		String command = "echo testing 123: `echo showing content of file1.txt; cat examples\\file1.txt`";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals(mockOut.toString(),
				"testing 123: showing content of file1.txt apple banana eggplant" + System.lineSeparator());
	}

	/*
	 * Globbing
	 * 
	 * Bug report: Expected a string containing list of files in examples but
	 * output has no result.
	 * 
	 * Failed to command substitute globbing
	 * 
	 * Part where code fail is unknown
	 */
	@Test
	public void testGlobbing() throws Exception {
		String command = "echo test globbing: `cat mock-glob-fs\\apple/*`";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals(mockOut.toString(), "");
	}

	/*
	 * Calendar
	 */
	@Test
	public void testCal() throws Exception {
		String command = "echo testing calendar: `cal`";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals(mockOut.toString(),
				"testing calendar: March 2016      Su Mo Tu We Th Fr Sa        1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31"
						+ System.lineSeparator());
	}

	/*
	 * Date
	 */
	@Test
	public void testDate() throws Exception {
		String command = "echo `echo today date is:` `date`";
		mockShell.parseAndEvaluate(command, mockOut);
		Date currentDate = new Date();
		assertEquals(mockOut.toString(), "today date is: " + currentDate + System.lineSeparator());
	}

	/*
	 * Test bc with pipe
	 */
	@Test
	public void testBC() throws Exception {
		String command = "echo The sum of 1+1= `echo 1+1|bc`";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals(mockOut.toString(), "The sum of 1+1= 2" + System.lineSeparator());
	}

	// Read from file
	@Test
	public void testRemoveNewLineSymbol() throws Exception {
		String command = "echo `cat examples\\file2.txt`";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals(mockOut.toString(), "apple banana banana zucchini" + System.lineSeparator());
	}

	/*
	 * Test if error is handled properly when improper backquotes are used.
	 * 
	 */
	@Test
	public void testMissingBackquote() throws Exception {
		String command = "echo `cat examples\\file2.txt";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals(mockOut.toString(), "");
	}
	
	@Test
	public void testNestedCommandSub() throws Exception {
		String command = "echo `echo shell 1`echo shell 2`echo shell 3```";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals(mockOut.toString(), "");
	
	}

}
