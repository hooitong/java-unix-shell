import static org.junit.Assert.assertEquals;

import org.junit.Test;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

import java.io.ByteArrayOutputStream;

public class FmtApplicationHack {

    ShellImpl mockShell;
    ByteArrayOutputStream mockOutput;

    public FmtApplicationHack() {
        mockShell = new ShellImpl();
        mockOutput = new ByteArrayOutputStream();
    }

    /**
     * When formatting with minimal width (1), the number 1, 2, 3 from test case was not found in output.
     *
     * This bug is due to not conforming "Wrap the given text at the specified maximum width." Ref spec page
     * 11 section fmt paragraph 1.
     */
    @Test
    public void testFmtMinimalWidth() throws Exception {
        String cmdline = "fmt -w 1 resource-hack/test1.txt";
        mockShell.parseAndEvaluate(cmdline, mockOutput);
        String expected = "line" + System.lineSeparator()
                + "1" + System.lineSeparator()
                + "line" + System.lineSeparator()
                + "2" + System.lineSeparator()
                + "line" + System.lineSeparator()
                + "3" + System.lineSeparator();
        assertEquals(expected, new String(mockOutput.toByteArray()));
    }
}
