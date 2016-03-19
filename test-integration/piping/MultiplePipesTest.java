package piping;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.PipeCommandException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.exception.SortException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;



public class MultiplePipesTest {
	private ShellImpl shell;
	private ByteArrayOutputStream stdout;
	private final static String NEW_LINE = System.lineSeparator();
	private final static String TAB = "\t";
	private static final String FOLDER_LOCATION = "examples-integration/Multiple-Pipe/";
	private static final String UTF8 = "UTF-8";

	@Before
	public void setUp() throws Exception {
		shell = new ShellImpl();
		stdout = new ByteArrayOutputStream();
	}

	/**
	 * test the pipe evaluation from Cat app -> Tail app -> Head
	 * 
	 * @throws AbstractApplicationException
	 * @throws ShellException
	 */
	@Test
	public void testPipeCatTailHead() throws AbstractApplicationException,
			ShellException {
		String cmd = "cat " + FOLDER_LOCATION + "sampleOriginalPart1.txt "
				+ FOLDER_LOCATION + "sampleOriginalPart2.txt | tail -n 4";
		shell.parseAndEvaluate(cmd, stdout);
		String expected = "ultimately deriving from the Latin ingenium (c. 1250), "
				+ NEW_LINE
				+ "meaning \"innate quality, "
				+ NEW_LINE
				+ "especially mental power,"
				+ NEW_LINE
				+ "hence a clever invention.\"[5]";
		assertEquals(expected, stdout.toString());

	}

	/**
	 * test the pipe evaluation from Tail app -> Sort app -> Head app
	 * 
	 * @throws AbstractApplicationException
	 * @throws ShellException
	 */
	@Test
	public void testPipeTailSortHead() throws AbstractApplicationException,
			ShellException {
		String cmd = "tail -n 7 " + FOLDER_LOCATION
				+ "items.txt  | sort | head -n 3";
		shell.parseAndEvaluate(cmd, stdout);
		String expected = "desktop" + NEW_LINE + "headset" + NEW_LINE + "key"
				+ NEW_LINE;
		assertEquals(expected, stdout.toString());
	}

	/**
	 * test the pipe evaluation from Echo app -> Fmt app -> Head app
	 * 
	 * @throws AbstractApplicationException
	 * @throws ShellException
	 */
	@Test
	public void testPipeEchoFmtHead() throws AbstractApplicationException,
			ShellException {
		String cmd = "echo \"Engineering has existed since ancient times as humans devised fundamental inventions such as the wedge, lever, wheel, and pulley.\" | fmt -w 20 | head -n 2";
		shell.parseAndEvaluate(cmd, stdout);
		String expected = "Engineering has" + NEW_LINE + "existed since"
				+ NEW_LINE;
		assertEquals(expected, stdout.toString());
	}

	/**
	 * test the pipe evaluation from Cat app -> Sort app -> Comm app
	 * 
	 * @throws AbstractApplicationException
	 * @throws ShellException
	 */
	@Test
	public void testPipeCatSortComm() throws AbstractApplicationException,
			ShellException {
		String cmd = "cat " + FOLDER_LOCATION + "comm1.txt " + FOLDER_LOCATION
				+ "comm2.txt | sort | comm " + FOLDER_LOCATION + "comm3.txt";
		shell.parseAndEvaluate(cmd, stdout);
		String expected = TAB + TAB + "apple" + NEW_LINE + TAB + TAB + "banana"
				+ NEW_LINE + TAB + "banana" + TAB + NEW_LINE + "eggplant" + TAB
				+ TAB + NEW_LINE + TAB + "zucchini" + TAB;
		assertEquals(expected, stdout.toString());
	}

	/**
	 * test the pipe evaluation from Sort app -> Tail app -> Fmt app
	 * 
	 * @throws AbstractApplicationException
	 * @throws ShellException
	 */
	@Test
	public void testPipeSortTailFmt() throws AbstractApplicationException,
			ShellException {
		String cmd = "sort " + FOLDER_LOCATION + "sort1.txt " + FOLDER_LOCATION
				+ "sort2.txt " + FOLDER_LOCATION + "sort3.txt |tail -n 3|fmt";
		shell.parseAndEvaluate(cmd, stdout);
		String expected = "muse science yawn";
		assertEquals(expected, stdout.toString());
	}

	/**
	 * test the pipe evaluation from Head app -> Fmt app -> Tail app
	 * 
	 * @throws AbstractApplicationException
	 * @throws ShellException
	 */
	@Test
	public void testPipeHeadFmtTail() throws AbstractApplicationException,
			ShellException {
		String cmd = "head " + FOLDER_LOCATION
				+ "sampleOriginal.txt  | fmt -w 40 |tail -n 2";
		shell.parseAndEvaluate(cmd, stdout);
		String expected = "innate quality, especially mental" + NEW_LINE
				+ "power, hence a clever invention.\"[5]";

		System.out.println(stdout.toString());
		assertEquals(expected, stdout.toString());

	}

	/**
	 * test the pipe evaluation from Head app -> Unknown app -> Tail app throws
	 * an exception when an unknown application is called between pipes
	 * 
	 * @throws AbstractApplicationException
	 * @throws ShellException
	 */
	@Test(expected = PipeCommandException.class)
	public void testPipeHeadUnknownTail() throws AbstractApplicationException,
			ShellException {
		String cmd = "head " + FOLDER_LOCATION
				+ "sampleOriginal.txt  | dmt -w 40 |tail -n 2";
		shell.parseAndEvaluate(cmd, stdout);

	}

	/**
	 * test the pipe evaluation from Sort app (missing file args and no stdin)
	 * -> Tail app -> Fmt app throws an exception when there is missing stdin
	 * for sort application and the file is not specified in the args
	 * 
	 * @throws AbstractApplicationException
	 * @throws ShellException
	 */
	@Test(expected = PipeCommandException.class)
	public void testPipeMissingSortArgsTailFmt()
			throws AbstractApplicationException, ShellException {
		String cmd = "sort -n |tail -n 2|fmt";
		shell.parseAndEvaluate(cmd, stdout);
	}

	/**
	 * test the pipe evaluation from Tail app -> Sort app -> Head app ("-k"
	 * instead of "-n" used) Throws an exception as the last application in the
	 * pipe (head) has an invalid argument "k" supplied instead of "n"
	 * 
	 * @throws AbstractApplicationException
	 * @throws ShellException
	 */
	@Test(expected = PipeCommandException.class)
	public void testPipeTailSortBadHeadArgs()
			throws AbstractApplicationException, ShellException {
		String cmd = "tail -n 7 " + FOLDER_LOCATION
				+ "items.txt  | sort | head -k 3";
		shell.parseAndEvaluate(cmd, stdout);
	}

}
