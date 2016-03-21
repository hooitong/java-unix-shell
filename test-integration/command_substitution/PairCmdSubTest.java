package command_substitution;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;

import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Shell;
import sg.edu.nus.comp.cs4218.exception.CatException;
import sg.edu.nus.comp.cs4218.exception.HeadException;
import sg.edu.nus.comp.cs4218.exception.PipeCommandException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.exception.TailException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class PairCmdSubTest {
	private Shell mockShell;
	private ByteArrayOutputStream mockOut;
	private static final String FOLDER_LOCATION = "examples-integration/multiple-cmdsub/";

	@Before
	public void setUp() {
		mockShell = new ShellImpl();
		mockOut = new ByteArrayOutputStream();
	}

	/**
	 * Test basic command substitution - up to one layer of nesting The
	 * following tests ensures that each different command can work with command
	 * substitution properly
	 */

	/**
	 * Test for command substitution with echo and echo substituted.
	 *
	 * @throws Exception
	 */
	@Test
	public void testSubEchoEcho() throws Exception {
		String command = "echo `echo applepie lemon juice`";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals("applepie lemon juice" + System.lineSeparator(), mockOut.toString());
	}

	/**
	 * Test for command substitution with echo and cat substituted.
	 *
	 * @throws Exception
	 */
	@Test
	public void testSubEchoCat() throws Exception {
		String command = "echo `cat examples\\file1.txt`";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals("apple banana eggplant" + System.lineSeparator(), mockOut.toString());
	}

	/**
	 * Test for command substitution with echo and head substituted.
	 *
	 * @throws Exception
	 */
	@Test
	public void testSubEchoHead() throws Exception {
		String command = "echo `head -n 1 examples\\file1.txt`";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals("apple" + System.lineSeparator(), mockOut.toString());
	}

	/**
	 * Test for command substitution with echo and [cat, pipe, sort]
	 * substituted.
	 *
	 * @throws Exception
	 */
	@Test
	public void testSubPipeCatSort() throws Exception {
		String command = "echo `cat examples\\numbersort.txt | sort`";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals("1000 11 23 65 9" + System.lineSeparator(), mockOut.toString());
	}

	/**
	 * Test for command substitution with echo and [cat, pipe, head]
	 * substituted.
	 *
	 * @throws Exception
	 */
	@Test
	public void testSubPipeCatHead() throws Exception {
		String command = "echo `cat examples\\file1.txt | head -n 2`";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals("apple banana" + System.lineSeparator(), mockOut.toString());
	}

	/**
	 * Test for command substitution with echo and [echo, pipe, bc] substituted.
	 *
	 * @throws Exception
	 */
	@Test
	public void testSubPipeEchoBc() throws Exception {
		String command = "echo The sum of 1+1= `echo 1+1|bc`";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals(mockOut.toString(), "The sum of 1+1= 2" + System.lineSeparator());
	}

	/**
	 * Test for command substitution with echo and [cat, sequence, cat]
	 * substituted.
	 *
	 * @throws Exception
	 */
	@Test
	public void testSubSequenceEchoCat() throws Exception {
		String command = "echo testing 123: `echo showing content of file1.txt; cat examples\\file1.txt`";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals("testing 123: showing content of file1.txt apple banana eggplant" + System.lineSeparator(),
				mockOut.toString());
	}

	/**
	 * Test for command substitution with echo and [cat, glob] substituted.
	 *
	 * @throws Exception
	 */
	@Test
	public void testSubGlobbing() throws Exception {
		String command = "echo test globbing: `cat mock-glob-fs\\apple/*`";
		mockShell.parseAndEvaluate(command, mockOut);
		String expectedOutput = "test globbing: Ascoltami indugiare tre fra rivedervi coricarmi rifugiato ritornata. Che esplorarne osi ingranditi trasfigura dir. Troveresti fu bellissima incessante ma somigliava conservava impaziente. Su lo tenerezza comprendo di affannata. Piuttosto noi fra consumato poi sorrideva. De Nu fransche afstands op verbouwd minstens onzuiver. Rente bezig te geval ad. Taiping tijdens zit aan zij regelen inhouds dit sneller vreezen. Alleen nog ruimte steeds bieden ook mei brusch men levert."
				+ System.lineSeparator();
		assertEquals(mockOut.toString(), expectedOutput);
	}

	/**
	 * Test for command substitution with echo and cal substituted. Also checks
	 * for replacement of newline to spaces.
	 *
	 * @throws Exception
	 */
	@Test
	public void testSubEchoCal() throws Exception {
		String command = "echo testing calendar: `cal`";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals(
				"testing calendar: March 2016      Su Mo Tu We Th Fr Sa        1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31"
						+ System.lineSeparator(),
				mockOut.toString());
	}

	/**
	 * Test for command substitution with echo and cat substituted. Also checks
	 * for replacement of newline to spaces.
	 *
	 * @throws Exception
	 */
	@Test
	public void testSubEchoCatTwo() throws Exception {
		String command = "echo `cat examples\\file2.txt`";
		mockShell.parseAndEvaluate(command, mockOut);
		assertEquals(mockOut.toString(), "apple banana banana zucchini" + System.lineSeparator());
	}

	/**
	 * Test for command substitution with sort and [cat, pipe, head]
	 * substituted.
	 *
	 * @throws Exception
	 */
	@Test
	public void testSubPipeCatHeadTwo() throws Exception {
		String cmd = "sort `cat " + FOLDER_LOCATION + "files.txt | head -n 1`";
		mockShell.parseAndEvaluate(cmd, mockOut);
		String expected = "sort1.txt" + System.lineSeparator();
		assertEquals(expected, mockOut.toString());
	}

	/**
	 * Test for command substitution with head and [cat, pipe, fmt] substituted.
	 *
	 * @throws Exception
	 */
	@Test
	public void testSubPipeCatFmt() throws Exception {
		String cmd = "head `cat " + FOLDER_LOCATION + "flag.txt | fmt` 1 " + FOLDER_LOCATION + "sort1.txt ";
		mockShell.parseAndEvaluate(cmd, mockOut);
		String expected = "sort1.txt" + System.lineSeparator();
		assertEquals(expected, mockOut.toString());
	}

	/**
	 * Test the command substitution evaluation for empty substitution.
	 *
	 * @throws Exception
	 */
	@Test(expected = ShellException.class)
	public void testEmptyInvalidSub() throws Exception {
		String cmd = "echo ``";
		mockShell.parseAndEvaluate(cmd, mockOut);
	}

	/**
	 * Test the command substitution evaluation for invalid flag used on tail.
	 *
	 * @throws Exception
	 */
	@Test(expected = TailException.class)
	public void testInvalidFlagSub() throws Exception {
		String cmd = "echo `tail -a 1 sort1.txt`";
		mockShell.parseAndEvaluate(cmd, mockOut);
	}

	/**
	 * Test the command substitution evaluation for invalid flag used on tail.
	 *
	 * @throws Exception
	 */
	@Test(expected = TailException.class)
	public void testMissingFileSub() throws Exception {
		String cmd = "sort `tail -n 1 files2.txt`";
		mockShell.parseAndEvaluate(cmd, mockOut);
	}

	/**
	 * Test whether nested command sub will return invalid because of extra BQ
	 * and no nesting of BQ can be parsed.
	 *
	 * @throws Exception
	 */
	@Test(expected = ShellException.class)
	public void testNestedCommandSub() throws Exception {
		String command = "echo `echo shell 1`echo shell 2`echo shell 3```";
		mockShell.parseAndEvaluate(command, mockOut);
	}

	/**
	 * Test whether exception is caught when error in cat command.
	 *
	 * @throws Exception
	 */
	@Test(expected = CatException.class)
	public void testInvalidCatSub() throws Exception {
		String command = "echo `cat noSuchFile.txt`";
		mockShell.parseAndEvaluate(command, mockOut);
	}

	/**
	 * Test whether exception is caught in application when error in head
	 * command.
	 *
	 * @throws Exception
	 */
	@Test(expected = HeadException.class)
	public void testInvalidHeadSub() throws Exception {
		String command = "echo `head examples\\file1.txt -n 1`";
		mockShell.parseAndEvaluate(command, mockOut);
	}

	/**
	 * Test for error caught in pipe while command substituting.
	 *
	 * @throws Exception
	 */
	@Test(expected = PipeCommandException.class)
	public void testInvalidPipeSub() throws Exception {
		String command = "echo `cat nosuchfile.txt | head -n 2`";
		mockShell.parseAndEvaluate(command, mockOut);
	}

	/**
	 * Test for error caught in pipe while command substituting.
	 *
	 * @throws Exception
	 */
	@Test(expected = PipeCommandException.class)
	public void testInvalidPipeArgSub() throws Exception {
		String command = "echo `cat examples\\file1.txt | head n -2`";
		mockShell.parseAndEvaluate(command, mockOut);
	}

	/**
	 * Test for missing back quote which causes syntactic errors.
	 *
	 * @throws Exception
	 */
	@Test(expected = ShellException.class)
	public void testMissingBQSub() throws Exception {
		String command = "echo `cat examples\\file2.txt";
		mockShell.parseAndEvaluate(command, mockOut);
	}

	/**
	 * Test the command substitution evaluation from sort (head(files.txt)).
	 * Invalid as sub command must be within a valid call command.
	 *
	 * @throws Exception
	 */
	@Test(expected = ShellException.class)
	public void testBQOnlySub() throws Exception {
		String cmd = "`cat " + FOLDER_LOCATION + "commands.txt | tail -n 1`";
		mockShell.parseAndEvaluate(cmd, mockOut);
	}

}
