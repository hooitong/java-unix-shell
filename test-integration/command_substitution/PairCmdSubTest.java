package command_substitution;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Shell;
import sg.edu.nus.comp.cs4218.exception.*;
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
     * Test for command substitution
     * @throws Exception
     */
    @Test
    public void testSubEchoEcho() throws Exception {
        String command = "echo `echo applepie lemon juice`";
        mockShell.parseAndEvaluate(command, mockOut);
        assertEquals("applepie lemon juice" + System.lineSeparator(), mockOut.toString());
    }


    /**
     * Test for multiple command substitution in a single command.
     */
    @Test
    public void testSubEchoEchoEcho() throws Exception {
        String command = "echo `echo lemon juice` `echo applepie`";
        mockShell.parseAndEvaluate(command, mockOut);
        assertEquals("lemon juice applepie" + System.lineSeparator(), mockOut.toString());
    }

    /**
     * Test that cat command can be substituted.
     */
    @Test
    public void testSubEchoCat() throws Exception {
        String command = "echo `cat examples\\file1.txt`";
        mockShell.parseAndEvaluate(command, mockOut);
        assertEquals("apple banana eggplant" + System.lineSeparator(), mockOut.toString());
    }

    /**
     * Test whether exception is caught when error in cat command.
     */
    @Test(expected = CatException.class)
    public void testSubInvalidCat() throws Exception {
        String command = "echo `cat noSuchFile.txt`";
        mockShell.parseAndEvaluate(command, mockOut);
    }

    /**
     * Test sub head.
     */
    @Test
    public void testSubEchoHead() throws Exception {
        String command = "echo `head -n 1 examples\\file1.txt`";
        mockShell.parseAndEvaluate(command, mockOut);
        assertEquals("apple" + System.lineSeparator(), mockOut.toString());
    }

    /**
     * Test whether exception is caught in application when error in head
     * command.
     */
    @Test(expected = HeadException.class)
    public void testSubInvalidHead() throws Exception {
        String command = "echo `head examples\\file1.txt -n 1`";
        mockShell.parseAndEvaluate(command, mockOut);
    }

    /**
     * Test that pipe and sort works in command substitution.
     */
    @Test
    public void testSubPipeCatSort() throws Exception {
        String command = "echo `cat examples\\numbersort.txt | sort`";
        mockShell.parseAndEvaluate(command, mockOut);
        assertEquals("1000 11 23 65 9" + System.lineSeparator(), mockOut.toString());
    }

    /**
     * Test for correct line read when reading a file and then piping to head.
     */
    @Test
    public void testSubPipeCatHead() throws Exception {
        String command = "echo `cat examples\\file1.txt | head -n 2`";
        mockShell.parseAndEvaluate(command, mockOut);
        assertEquals("apple banana" + System.lineSeparator(), mockOut.toString());
    }

    /**
     * Test bc with pipe
     */
    @Test
    public void testSubPipeEchoBc() throws Exception {
        String command = "echo The sum of 1+1= `echo 1+1|bc`";
        mockShell.parseAndEvaluate(command, mockOut);
        assertEquals(mockOut.toString(), "The sum of 1+1= 2" + System.lineSeparator());
    }


    /**
     * Test for error caught in pipe while command substituting.
     */
    @Test(expected = PipeCommandException.class)
    public void testSubInvalidPipe() throws Exception {
        String command = "echo `cat nosuchfile.txt | head -n 2`";
        mockShell.parseAndEvaluate(command, mockOut);
    }

    /**
     * Test for error caught in pipe while command substituting
     */
    @Test(expected = PipeCommandException.class)
    public void testSubPipeInvalidArg() throws Exception {
        String command = "echo `cat examples\\file1.txt | head n -2`";
        mockShell.parseAndEvaluate(command, mockOut);
    }

    /**
     * Semicolon operator
     */
    @Test
    public void testSubSequenceEchoCat() throws Exception {
        String command = "echo testing 123: `echo showing content of file1.txt; cat examples\\file1.txt`";
        mockShell.parseAndEvaluate(command, mockOut);
        assertEquals("testing 123: showing content of file1.txt apple banana eggplant" + System.lineSeparator(), mockOut.toString());
    }

    /**
     * Globbing
     */
    @Test
    public void testSubGlobbing() throws Exception {
        String command = "echo test globbing: `cat mock-glob-fs\\apple/*`";
        mockShell.parseAndEvaluate(command, mockOut);
        String expectedOutput = "test globbing: Ascoltami indugiare tre fra rivedervi coricarmi rifugiato ritornata. Che esplorarne osi ingranditi trasfigura dir. Troveresti fu bellissima incessante ma somigliava conservava impaziente. Su lo tenerezza comprendo di affannata. Piuttosto noi fra consumato poi sorrideva. De Nu fransche afstands op verbouwd minstens onzuiver. Rente bezig te geval ad. Taiping tijdens zit aan zij regelen inhouds dit sneller vreezen. Alleen nog ruimte steeds bieden ook mei brusch men levert." + System.lineSeparator();
        assertEquals(mockOut.toString(), expectedOutput);
    }

    /*
     * Calendar
     */
    @Test
    public void testSubEchoCal() throws Exception {
        String command = "echo testing calendar: `cal`";
        mockShell.parseAndEvaluate(command, mockOut);
        assertEquals(mockOut.toString(),
                "testing calendar: March 2016      Su Mo Tu We Th Fr Sa        1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31"
                        + System.lineSeparator());
    }

    /**
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
     *
     * @throws Exception
     */
    @Test
    public void testRemoveNewLineSymbol() throws Exception {
        String command = "echo `cat examples\\file2.txt`";
        mockShell.parseAndEvaluate(command, mockOut);
        assertEquals(mockOut.toString(), "apple banana banana zucchini" + System.lineSeparator());
    }

    /**
     *
     * @throws Exception
     */
    @Test (expected = ShellException.class)
    public void testMissingBackquote() throws Exception {
        String command = "echo `cat examples\\file2.txt";
        mockShell.parseAndEvaluate(command, mockOut);
    }

    /**
     * Test the command substitution evaluation from sort (head(files.txt))
     *
     */
    @Test
    public void testSubFileName() throws Exception{
        String cmd = "sort `cat " + FOLDER_LOCATION + "files.txt | head -n 1`";
        System.out.println(cmd);
        mockShell.parseAndEvaluate(cmd, mockOut);
        String expected = "grass" + System.lineSeparator() + "muse";
        assertEquals(expected, mockOut.toString());
    }

    /**
     * Test the command substitution evaluation from sort (head(files.txt))
     *
     * @throws AbstractApplicationException
     * @throws ShellException
     */
    @Test
    public void testSubCommand() throws AbstractApplicationException,
            ShellException {
        String cmd = "`cat " + FOLDER_LOCATION + "commands2.txt`";
        mockShell.parseAndEvaluate(cmd, mockOut);
        String expected = "sort1.txt" + System.lineSeparator();
        assertEquals(expected, mockOut.toString());
    }

    /**
     * Test the command substitution evaluation from sort (head(files.txt))
     *
     * @throws AbstractApplicationException
     * @throws ShellException
     */
    @Test
    public void testCmdSubOnlyQuotes() throws AbstractApplicationException,
            ShellException {
        String cmd = "`cat " + FOLDER_LOCATION + "commands.txt | tail -n 1`";
        mockShell.parseAndEvaluate(cmd, mockOut);
        String expected = "grass" + System.lineSeparator();
        assertEquals(expected, mockOut.toString());
    }

    /**
     * Test the command substitution evaluation for IO files
     *
     * @throws AbstractApplicationException
     * @throws ShellException
     */
    @Test
    public void testSubIOFile() throws AbstractApplicationException,
            ShellException {
        String cmd = "sort -n < `cat " + FOLDER_LOCATION + "redirection1.txt | head";
        mockShell.parseAndEvaluate(cmd, mockOut);
        String expected = "animal" + System.lineSeparator() + "kangaroo" + System.lineSeparator() + "zoo" + System.lineSeparator();
        assertEquals(expected, mockOut.toString());
    }

    /**
     * Test the command substitution evaluation from flag
     *
     * @throws AbstractApplicationException
     * @throws ShellException
     */
    @Test
    public void testSubFlag() throws AbstractApplicationException,
            ShellException {
        String cmd = "head `cat " + FOLDER_LOCATION + "flag.txt | fmt` " + FOLDER_LOCATION + "sort1.txt ";
        mockShell.parseAndEvaluate(cmd, mockOut);
        String expected = "muse" + System.lineSeparator();
        assertEquals(expected, mockOut.toString());
    }

    /**
     * Test the command substitution evaluation for empty substitution
     *
     * @throws AbstractApplicationException
     * @throws ShellException
     */
    @Test
    public void testEmptyCmdSub() throws AbstractApplicationException,
            ShellException {
        String cmd = "echo ``";
        mockShell.parseAndEvaluate(cmd, mockOut);
        String expected = System.lineSeparator();
        assertEquals(expected, mockOut.toString());
    }


    /**
     * Test the command substitution evaluation for invalid flag used on tail
     *
     * @throws AbstractApplicationException
     * @throws ShellException
     */
    @Test (expected = ShellException.class)
    public void testNegInvalidFlag() throws AbstractApplicationException,
            ShellException {
        String cmd = "echo `tail -a 1 sort1.txt`";
        mockShell.parseAndEvaluate(cmd, mockOut);
    }

    /**
     * Test the command substitution evaluation for invalid flag used on tail
     *
     * @throws AbstractApplicationException
     * @throws ShellException
     */
    @Test (expected = ShellException.class)
    public void testNegMissingFile() throws AbstractApplicationException,
            ShellException {
        String cmd = "sort `tail -n 1 files2.txt`";
        mockShell.parseAndEvaluate(cmd, mockOut);
    }


    @Test
    public void testNestedCommandSub() throws Exception {
        String command = "echo `echo shell 1`echo shell 2`echo shell 3```";
        mockShell.parseAndEvaluate(command, mockOut);
        assertEquals(mockOut.toString(), "");

    }
}
