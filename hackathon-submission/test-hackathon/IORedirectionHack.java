import static org.junit.Assert.assertEquals;

import org.junit.Test;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

import java.io.ByteArrayOutputStream;

public class IORedirectionHack {
    ShellImpl mockShell;
    ByteArrayOutputStream mockOutput;

    public IORedirectionHack() {
        mockShell = new ShellImpl();
        mockOutput = new ByteArrayOutputStream();
    }

    /**
     * When redirecting a valid file but empty, null pointer exception is thrown.
     *
     * This bug is due to violation of "If expected stdin is not provided, the application must throw an
     * exception. If required command line arguments are not provided or arguments are wrong or
     * inconsistent, the application throws an exception as well." Expected stdin is valid and should not be throwing
     * exception but empty line. Ref spec page 10 section applications specification paragraph 1.
     *
     * Error at sg.edu.nus.comp.cs4218.impl.app.HeadApplication.readFromStdinAndWriteToStdout(HeadApplication.java:132)
     */
    @Test
    public void testIORedirection() throws Exception{
        String cmdline = "head -n 1 < resource-hack/empty.txt";
        mockShell.parseAndEvaluate(cmdline, mockOutput);
        assertEquals(System.lineSeparator(), new String(mockOutput.toByteArray()));
    }
}
