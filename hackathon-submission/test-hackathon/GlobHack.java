import static org.junit.Assert.assertEquals;

import org.junit.Test;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

import java.io.ByteArrayOutputStream;

public class GlobHack {
    ShellImpl mockShell;
    ByteArrayOutputStream mockOutput;

    public GlobHack() {
        mockShell = new ShellImpl();
        mockOutput = new ByteArrayOutputStream();
    }

    /**
     * Does not fill asterisk with sequence of non slash chars which is test1 and test2 and evaluate cat. Instead
     * throws exception of invalid globbing scenario.
     *
     * This bug is due to "Collect all the paths to existing files and directories such that these paths 
     * can be obtained by replacing all the unquoted asterisk symbols in ARG by some (possibly empty) sequences of
     * non­slash characters" Ref spec Page 8 Section Globbing Paragraph 3
     *
     * Error originated at sg.edu.nus.comp.cs4218.impl.ShellImpl.processGlob(ShellImpl.java:118)
     */
    @Test
    public void testGlob() throws Exception {
        String cmdline = "cat resource-hack/glob-failure/*.txt";
        mockShell.parseAndEvaluate(cmdline, mockOutput);
        String expectedOutput = "not a failure anymore";
        assertEquals(expectedOutput, new String(mockOutput.toByteArray()));
    }
}
