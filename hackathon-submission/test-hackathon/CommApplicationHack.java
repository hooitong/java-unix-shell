import org.junit.Before;
import org.junit.Test;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;
import sg.edu.nus.comp.cs4218.impl.app.CommApplication;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class CommApplicationHack {

    ShellImpl mockShell;
    ByteArrayOutputStream mockOutput;
    CommApplication cApp;
    private static final String TAB_LINE = "\t";
    private static final String NEW_LINE = System.lineSeparator();

    @Before
    public void setUp() {
        mockShell = new ShellImpl();
        mockOutput = new ByteArrayOutputStream();
        cApp = new CommApplication();
    }

    /**
     * Exception thrown which requires sorted input to be fed. Note an input has
     * to be sorted before it can be used in comm, is not written anywhere in the
     * public API description for CommApplication. (except a private method that checks
     * sorted files, no justification or explanation in public API on the sorted requirement)
     *
     * This bug is due to applying a strict restriction that does not agree with
     * "The comm command compares each line of two files and prints these lines in three columns
     * separated by the Tabulator­character (ASCII value 9) in the order of their occurrence."
     * ref specs page 11 section comm paragraph 1.
     */
    @Test
    public void testComm() throws Exception {
        String[] args = { "resource-hack/examples-1/comm3.txt", "resource-hack/examples-1/comm4.txt" };
        cApp.run(args, null, mockOutput);
        String expected = TAB_LINE + "\"" + TAB_LINE + NEW_LINE + TAB_LINE + ">" + TAB_LINE + NEW_LINE + TAB_LINE + "M"
                + TAB_LINE + NEW_LINE + TAB_LINE + "9" + TAB_LINE + NEW_LINE + "o" + TAB_LINE + TAB_LINE + NEW_LINE
                + "3" + TAB_LINE + TAB_LINE + NEW_LINE + "{" + TAB_LINE + TAB_LINE;
        assertEquals(expected, mockOutput.toString());
    }

    /**
     * Returns truncated string which in this case returning est2 rather than test2.
     *
     * This bug is due to not fulfilling the requirement of "The comm command compares each line of two files and prints these lines in three columns
     * separated by the Tabulator­character (ASCII value 9) in the order of their occurrence." Ref spec page 11 section comm paragraph 1.
     */
    @Test
    public void pipeDateComm() throws Exception {
        String testInput = "date | comm resource-hack/date-comm.txt";
        mockShell.parseAndEvaluate(testInput, mockOutput);
        String output = new String(mockOutput.toByteArray(), "UTF-8");
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        String expectedOutput = sdf.format(new Date()) + "\t\t" + System.lineSeparator() + "\tcake5\t"
                + System.lineSeparator() + "\ttest2\t";
        assertEquals(expectedOutput, output);
    }
}
