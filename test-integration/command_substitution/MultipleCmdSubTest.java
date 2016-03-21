package command_substitution;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.PipeCommandException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class MultipleCmdSubTest {
	private ShellImpl mockShell;
	private ByteArrayOutputStream mockOut;

	@Before
	public void setUp() throws Exception {
		mockShell = new ShellImpl();
		mockOut = new ByteArrayOutputStream();
	}

	/**
	 * Test for multiple command substitution in a single command with [echo],
	 * [echo] substituted.
	 *
	 * @throws Exception
	 */
	@Test
	public void testSubEchoEcho() throws Exception {
		String command = "echo `echo lemon juice` `echo applepie`";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals("lemon juice applepie" + System.lineSeparator(), mockOut.toString());
	}

	/**
	 * Test for multiple command substitution in a single command with [cat,
	 * pipe head], [echo] substituted.
	 *
	 * @throws Exception
	 */
	@Test
	public void testSubPipeEcho() throws Exception {
		String command = "echo `cat examples\\comsub1.txt` testing | head -n `echo 1`";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals("examples\\file1.txt examples\\file2.txt examples\\file3.txt testing" + System.lineSeparator(),
				mockOut.toString());

	}

	/**
	 * Test for multiple command substitution in a single command with [cat,
	 * pipe, head], [cat, pipe, sort, pipe, head], [echo, pipe, bc] substituted.
	 * Read two different number from the same file and check if their addition
	 * is the same as expected.
	 *
	 * @throws Exception
	 */
	@Test
	public void testSubPipePipePipe() throws Exception {
		String command = "echo `cat examples\\numbersort.txt| head -n 1` plus `cat examples\\numbersort.txt|sort|head -n 1` is `echo 65+1000|bc` ";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals("65 plus 1000 is 1065" + System.lineSeparator(), mockOut.toString());
	}

	/**
	 * Test for multiple command substitution in a single command with [head],
	 * [head] substituted. Test case read two different file name from two
	 * different files and send the content to sort Finally printing the first
	 * line of the content.
	 *
	 * @throws Exception
	 */
	@Test
	public void testSubHeadHead() throws Exception {
		String command = "cat `head -n 1 examples\\comsub1.txt` `head -n 1 examples\\comsub2.txt` | sort | head -n 1";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals("1000" + System.lineSeparator(), mockOut.toString());
	}

	/**
	 * Test for multiple command substitution in a single command with [echo],
	 * [date] substituted.
	 *
	 * @throws Exception
	 */
	@Test
	public void testSubEchoDate() throws Exception {
		String command = "echo `echo today date is:` `date`";
		mockShell.parseAndEvaluate(command, mockOut);
		Date currentDate = new Date();
		assertEquals(mockOut.toString(), "today date is: " + currentDate + System.lineSeparator());
	}

	/**
	 * Test for throwing error if sub-command is not part of a main call
	 * command.
	 *
	 * @throws Exception
	 */
	@Test(expected = PipeCommandException.class)
	public void testInvalidCommandSub() throws Exception {
		String command = "`cat examples\\comsub1.txt`| `head -n 1`";
		mockShell.parseAndEvaluate(command, mockOut);
	}

}