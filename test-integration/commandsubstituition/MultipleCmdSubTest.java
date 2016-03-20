package commandsubstituition;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import org.junit.Before;
import org.junit.Test;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;



public class MultipleCmdSubTest {
	private ShellImpl shell;
	private ByteArrayOutputStream stdout;
	private final static String NEW_LINE = System.lineSeparator();
	private static final String FOLDER_LOCATION = "examples-integration/Multiple-CmdSub/";

	@Before
	public void setUp() throws Exception {
		shell = new ShellImpl();
		stdout = new ByteArrayOutputStream();
	}

	/**
	 * Test the command substituition evaluation from sort (head(files.txt))
	 * 
	 * @throws AbstractApplicationException
	 * @throws ShellException
	 */
	@Test
	public void testCmdSubSortCatHead() throws AbstractApplicationException,
			ShellException {
		String cmd = "sort `cat " + FOLDER_LOCATION + "files.txt | head -n 1`";
		shell.parseAndEvaluate(cmd, stdout);
		String expected = "grass" + NEW_LINE + "muse";
		assertEquals(expected, stdout.toString());
	}

}