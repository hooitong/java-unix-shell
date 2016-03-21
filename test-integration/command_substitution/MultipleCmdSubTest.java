package command_substitution;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sg.edu.nus.comp.cs4218.exception.PipeCommandException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;



public class MultipleCmdSubTest {
	private ShellImpl mockShell;
	private ByteArrayOutputStream mockOut;

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setUp() throws Exception {
		mockShell = new ShellImpl();
		mockOut = new ByteArrayOutputStream();
	}

	/*
	 * Test when multiple command substitutions are used.
	 */
	@Test
	public void testMultipleCommandsExpectedA() throws Exception {
		String command = "cat examples\\comsub1.txt| head -n 1";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals("examples\\file1.txt" + System.lineSeparator(), mockOut.toString());

	}

	@Test
	public void testMultipleCommandsA1() throws Exception {
		String command = "echo `cat examples\\comsub1.txt` testing | head -n 1";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals("examples\\file1.txt examples\\file2.txt examples\\file3.txt testing" + System.lineSeparator(), mockOut.toString());

	}

	@Test(expected = PipeCommandException.class)
	public void testMultipleCommandsA2() throws Exception {
		String command = "`cat examples\\comsub1.txt`| head -n 1";
		mockShell.parseAndEvaluate(command, mockOut);
	}

	/**
	 * Read two different number from the same file and check if their addition is the same as expected
	 */
	@Test
	public void testMultipleCommandsB() throws Exception {
		String command = "echo `cat examples\\numbersort.txt| head -n 1` plus `cat examples\\numbersort.txt|sort|head -n 1` is `echo 65+1000|bc` ";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals("65 plus 1000 is 1065" + System.lineSeparator(), mockOut.toString());
	}

	/**
	 * Test case read two different file name from two different files and send the content to sort
	 * Finally printing the first line of the content.
	 *
	 * @throws Exception
	 */
	@Test
	public void testMultipleCommandsC() throws Exception {
		String command = "cat `head -n 1 examples\\comsub1.txt` `head -n 1 examples\\comsub2.txt` | sort | head -n 1";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals("1000" + System.lineSeparator(), mockOut.toString());
	}
}