import org.junit.Before;
import org.junit.Test;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;
import sg.edu.nus.comp.cs4218.impl.app.CatApplication;

import java.io.ByteArrayOutputStream;

public class CatApplicationHack {
    ShellImpl mockShell;
    ByteArrayOutputStream mockOutput;
    CatApplication cApp;

    @Before
    public void setUp() {
        mockShell = new ShellImpl();
        mockOutput = new ByteArrayOutputStream();
        cApp = new CatApplication();
    }

    /**
     * Running cat without any arguments should throw an exception since there is also no expected stdin that is provided.
     * However, the program does not terminate. This also occurs for other command as well.
     *
     * The bug is due to invalid handling of " If required command line arguments are not provided or arguments are wrong or
     * inconsistent, the application throws an exception as well. " Ref Page 10 Section Applications Specification Paragraph 1.
     *
     */
    @Test(expected = ShellException.class)
    public void testCatWithoutArgument() throws Exception {
        String cmdline = "cat";
        mockShell.parseAndEvaluate(cmdline, mockOutput);
    }
}
