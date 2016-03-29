import org.junit.*;
import sg.edu.nus.comp.cs4218.exception.EchoException;
import sg.edu.nus.comp.cs4218.impl.app.EchoApplication;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import static org.junit.Assert.assertEquals;

public class EchoApplicationHack {
    EchoApplication eApp;
    OutputStream output;

    @Before
    public void setUp() throws Exception {
        eApp = new EchoApplication();
        output = new ByteArrayOutputStream();
    }

	/**
	 * Echo ends the output with a '\n' and not System.lineSeperator() which fails
     * the test case.
     *
     * The bug is due to failing the requirement "Use Java properties "file.separator" and
     * "path.separator" when working with file system. Use “line.separator” property when working with
     * newlines." Ref specs page 1 section coding paragraph 1.
     *
	 */
    @Test
    public void gotNewLineAfterOutputIfUseSlashN() throws EchoException {
        String temp = "This is a trap";
        String[] args = temp.split(" ");
        eApp.run(args, null, output);
        assertEquals(output.toString(), "This is a trap" + System.lineSeparator());
    }
}
