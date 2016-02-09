package sg.edu.nus.comp.cs4218.impl.cmd;

import org.junit.Before;
import org.junit.Test;
import sg.edu.nus.comp.cs4218.exception.ShellException;

import java.io.ByteArrayOutputStream;
import java.util.Vector;

public class CallCommandTest {
    /* Mandatory Basic Test */
    protected CallCommand testCommand;
    protected String mockApplication = "echo";
    protected String mockArgument = "test";
    protected String testLine = mockApplication + " " + mockArgument;
    @Before
    public void setUp() throws ShellException {

        testCommand = new CallCommand(testLine);
        testCommand.parse();
    }

    /**
     * Side-effect based method. Tests whether command be can executed normally.
     *
     * @throws Exception
     */
    @Test
    public void testEvaluate() throws Exception {
        /* Testing for correct output */
        ByteArrayOutputStream stubOutput = new ByteArrayOutputStream();
        testCommand.evaluate(null, stubOutput);
        String evaluatedString = new String(stubOutput.toByteArray());
        assert(evaluatedString.equals(mockArgument));
    }

    /**
     * Side-effect based method. Tests whether command can parse command lines properly.
     *
     * @throws Exception
     */
    @Test
    public void testParse() throws Exception {
        /* Check if default parse is working already */
        assert(testCommand.argsArray.length == 1);
        assert(testCommand.argsArray[0].equals(mockArgument));
        assert(testCommand.app.equals(mockApplication));
    }

    /**
     * Tests whether arguments can be extracted properly.
     *
     * @throws Exception
     */
    @Test
    public void testExtractArgs() throws Exception {
        Vector<String> testVector = new Vector<>();
        testCommand.extractArgs(testLine, testVector);
        assert(testVector.size() == 1);
        assert(testVector.get(0).equals(mockArgument));
    }

    /**
     * Tests whether the input redirection can be parsed from the given command line.
     *
     * @throws Exception
     */
    @Test
    public void testExtractInputRedir() throws Exception {

    }

    /**
     * Tests whether the output redirection can be parsed from the given command line.
     *
     * @throws Exception
     */
    @Test
    public void testExtractOutputRedir() throws Exception {

    }

    /**
     * Tests whether the current execution of the command can be terminated correctly.
     * @throws Exception
     */
    @Test
    public void testTerminate() throws Exception {

    }
}
