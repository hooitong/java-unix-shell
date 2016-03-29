import static org.junit.Assert.assertEquals;

import org.junit.Test;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

import java.io.ByteArrayOutputStream;

public class CommandSubHack {

    ShellImpl mockShell;
    ByteArrayOutputStream mockOutput;

    public CommandSubHack() {
        mockShell = new ShellImpl();
        mockOutput = new ByteArrayOutputStream();
    }

    /**
     * newlines in command sub output are not replaced with spaces.
     *
     * This bug is due to violating "Whitespace characters are used during the argument splitting step. Since our shell
     * does not support multi­line commands, newlines in OUT should be replaced with spaces." Ref spec Page 9 Section
     * Semantics Paragraph 3.
     */
    @Test
    public void testCommandSub() throws Exception {
        String cmdline = "echo `cat resource-hack/test1.txt`";
        mockShell.parseAndEvaluate(cmdline, mockOutput);
        String expected = "line 1 line 2  line 3";
        assertEquals(expected, new String(mockOutput.toByteArray()));
    }

    /**
     * Command substitution occurs despite within single quotes.
     *
     * This bug is due to violating "A part of a call command surrounded by backquotes (`) is interpreted as command substitution 
     * iff the backquotes are not inside single quotes (corresponds the the non­terminal​ <backquoted>​ ). Ref spec page 9
     * section command substitution paragraph 2.
     *
     */
    @Test
    public void testNestedBQInSingleQuote() throws Exception {
        String cmdline = "echo '`abc`'";
        mockShell.parseAndEvaluate(cmdline, mockOutput);
        String expected = "`abc`";
        assertEquals(expected, new String(mockOutput.toByteArray()));
    }


    /**
     * (Head/Tail problem found via Command Sub)
     * Command sub returns -n with a space at the back which cannot be process as a flag by head/tail.
     *
     * This bug is due to displaying invalid args despite valid args as seen from
     * "OPTIONS – “­n 15” means printing 15 lines. Print first 10 lines if not specified." ref
     * spec page 10 section head paragraph 2.
     *
     * Problematic code at HeadApplication.java line 77.
     */
    @Test
    public void testSubPipeCatFmt() throws Exception {
        String cmd = "head `cat resource-hack/flag.txt | fmt` 1 resource-hack/sort1.txt";
        mockShell.parseAndEvaluate(cmd, mockOutput);
        String expected = "sort1.txt" + System.lineSeparator();
        assertEquals(expected, mockOutput.toString());
    }

    /**
     * Echo is not properly evaluated as part of the output of the command sub but rather a print to the command line.
     *
     * This bug is due to not conforming to "SUBCMD is evaluated as a separate shell command yielding the output OUT.
     * SUBCMD together with the backquotes is substituted in CALL with OUT." Ref Spec Page 9 Section Semantics Paragraph 2.
     */
    @Test
    public void testSubSequenceEchoCat() throws Exception {
        String command = "echo testing 123: `echo showing content of file1.txt; cat resource-hack/file1.txt`";
        mockShell.parseAndEvaluate(command, mockOutput);
        assertEquals("testing 123: showing content of file1.txt apple banana eggplant" + System.lineSeparator(),
                mockOutput.toString());
    }

    /**
     * Plus and is are read as applications rather than arguments to the main echo. Echo supports multiple argument and
     * should not be parsing the arguments as an app.
     *
     * This bug violates "The echo command writes its arguments separated by spaces and terminates by a newline on the
     * standard output." ref specs page 10 section echo paragraph 1.
     */
    @Test
    public void testMultipleSub() throws Exception {
        String command = "echo `cat resource-hack/examples-2/numbersort.txt| head -n 1` plus `cat resource-hack/examples-2/numbersort.txt|sort|head -n 1` is `echo 65+1000` ";
        mockShell.parseAndEvaluate(command, mockOutput);
        assertEquals("65 plus 1000 is 65+1000" + System.lineSeparator(), mockOutput.toString());
    }

    /**
     * Invalid syntax despite valid sequence command. Seems to occur when semicolon is used after a command
     * substitution.
     *
     * This bug does not conform to the syntax "<seq> ::= <command> “;” <command>" ref specs page 8 section semi-colon
     * paragraph 2.
     *
     */
    @Test
    public void testSemiColonAfterSub() throws Exception {
        String command = "cat `cat resource-hack/examples-1/comsub1.txt| head -n 1`; cat `cat resource-hack/examples-1/comsub2.txt| head -n 1`";
        mockShell.parseAndEvaluate(command, mockOutput);
        assertEquals("apple" + System.lineSeparator() + "banana" + System.lineSeparator() + "eggplant65"
                        + System.lineSeparator() + "9" + System.lineSeparator() + "1000" + System.lineSeparator() + "23"
                        + System.lineSeparator() + "11", mockOutput.toString());
    }
}
