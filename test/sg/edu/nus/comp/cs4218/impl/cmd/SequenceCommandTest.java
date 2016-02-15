package sg.edu.nus.comp.cs4218.impl.cmd;

import org.junit.Test;
import sg.edu.nus.comp.cs4218.exception.ShellException;

public class SequenceCommandTest {
    /**
     * Test if method <i>parse</i> properly throws exception when
     * cmdline with no sequence command is given.
     *
     * @throws Exception
     */
    @Test(expected=ShellException.class)
    public void testParseMissingSymbol() throws Exception {
        String cmdline = "grep abc > cat *";
        SequenceCommand sc = new SequenceCommand(cmdline);
        sc.parse();
    }

    /**
     * Test if method <i>parse</i> properly throws exception
     * when cmdline with sequence command but not complete
     * syntax is given.
     *
     * @throws Exception
     */
    @Test(expected=ShellException.class)
    public void testParseInvalidArgs() throws Exception {
        String cmdline = "cat abc.txt ; ";
        SequenceCommand sc = new SequenceCommand(cmdline);
        sc.parse();
    }

    /**
     * Test if method <i>parse</i> properly splits arguments
     * of the sequence command into two sub-commands.
     *
     * @throws Exception
     */
    @Test
    public void testParseSingleValid() throws Exception {
        String cmdline = "cat abc.txt ; grep abc/*.txt";
        SequenceCommand sc = new SequenceCommand(cmdline);
        sc.parse();
    }

    /**
     * Test if method <i>parse</i> can properly handle
     * multiple sequence operator by only splitting the first
     * operator's syntax.
     *
     * @throws Exception
     */
    @Test
    public void testParseMultipleValid() throws Exception {
        String cmdline = "grep test/apple/* ; cat abc.txt ; cat tk.test > a.out ";
        SequenceCommand sc = new SequenceCommand(cmdline);
        sc.parse();
    }

    /**
     * Test if method <i>terminate</i> can effectively stop
     * the termination of the command execution.
     *
     * @throws Exception
     */
    @Test
    public void testTerminate() throws Exception {

    }
}
