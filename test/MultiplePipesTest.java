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
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;
import sg.edu.nus.comp.cs4218.impl.app.CatApplication;

public class MultiplePipesTest {
	private ShellImpl shell;
	private ByteArrayOutputStream stdout;
	private final static String NEW_LINE = System.lineSeparator();
	private final static String TAB = "\t";
	private static final String FOLDER_LOCATION = "examples-integration/Multiple-Pipe/";
	private static final String UTF8 = "UTF-8";
	private CatApplication cat = new CatApplication();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		shell = new ShellImpl();
		stdout = new ByteArrayOutputStream();
	}

	@After
	public void tearDown() throws Exception {
	}

	/*
	 * @Test public void testPipeCatTailHead() throws
	 * AbstractApplicationException, ShellException { // String cmd = "cat " +
	 * RESOURCE_ROOT + "sampleOriginalPart1.txt " + // RESOURCE_ROOT +
	 * "sampleOriginalPart2.txt | head"; String cmd = "cat " + FOLDER_LOCATION +
	 * "" + "sampleOriginalPart1.txt " + FOLDER_LOCATION + "" +
	 * "sampleOriginalPart2.txt | tail -n 5 | head -n 1"; // String intial = //
	 * "examples-integration/Multiple-Pipe/sampleOriginalPart1.txt examples-integration/Multiple-Pipe/sampleOriginalPart2.txt"
	 * ; // InputStream inputStream = new java.io.ByteArrayInputStream( //
	 * intial.getBytes()); shell.parseAndEvaluate(cmd, stdout); // String[] arr
	 * = new String[]{RESOURCE_ROOT+"sampleOriginalPart1.txt" //
	 * ,RESOURCE_ROOT+"sampleOriginalPart2.txt"}; // String[] arr = new
	 * String[]{}; // cat.run(arr, inputStream, stdout); String expected =
	 * "The word \"engine\" itself is of even older origin, " + NEW_LINE;
	 * assertEquals(expected, stdout.toString());
	 * 
	 * }
	 * 
	 * @Test public void testPipeTailSortHead() throws
	 * AbstractApplicationException, ShellException { String cmd = "tail -n 7 "
	 * + FOLDER_LOCATION + "items.txt  | sort | head -n 3";
	 * shell.parseAndEvaluate(cmd, stdout); String expected = "desktop" +
	 * NEW_LINE + "headset" + NEW_LINE + "key" + NEW_LINE;
	 * assertEquals(expected, stdout.toString()); }
	 */

//	 @Test
//	 public void testPipeHeadFmtTail() throws AbstractApplicationException,
//	 ShellException {
//	 String cmd = "head " + FOLDER_LOCATION
//	 + "sampleOriginal.txt  | fmt -w 20 |tail -n 4 ";
//	 System.out.println(cmd);
//	 shell.parseAndEvaluate(cmd, stdout);
//	 String expected = "meaning" + NEW_LINE + "\"innate" + NEW_LINE
//	 + "quality," + NEW_LINE;
//	 System.out.println(stdout.toString());
//	 assertEquals(expected, stdout.toString());
//	
//	 }

	/*@Test
	public void testPipeEchoFmtHead() throws AbstractApplicationException,
			ShellException {
		String cmd = "echo \"Engineering has existed since ancient times as humans devised fundamental inventions such as the wedge, lever, wheel, and pulley.\" | fmt -w 20 | head -n 2";
		System.out.println(cmd);
		shell.parseAndEvaluate(cmd, stdout);
		String expected = "Engineering has" + NEW_LINE + "existed since"
				+ NEW_LINE;
		System.out.println(stdout.toString());
		assertEquals(expected, stdout.toString());
	}*/

	/*
	 * @Test public void testPipeCatSortComm() throws
	 * AbstractApplicationException, ShellException { String cmd = "cat " +
	 * FOLDER_LOCATION + "comm1.txt " + FOLDER_LOCATION +
	 * "comm2.txt | sort | comm " + FOLDER_LOCATION + "comm3.txt";
	 * System.out.println(cmd); shell.parseAndEvaluate(cmd, stdout); String
	 * expected = TAB + TAB + "apple" + NEW_LINE + TAB + TAB + "banana" +
	 * NEW_LINE + TAB + "banana" + TAB + NEW_LINE + "eggplant" + TAB + TAB +
	 * NEW_LINE + TAB + "zucchini" + TAB; System.out.println(stdout.toString());
	 * assertEquals(expected, stdout.toString()); }
	 */

	// @Test
	// public void testPipeSortTailFmt() throws AbstractApplicationException,
	// ShellException {
	// String cmd = "sort " + FOLDER_LOCATION + "sort1.txt " + FOLDER_LOCATION
	// + "sort2.txt " + FOLDER_LOCATION + "sort3.txt |tail -n 3| fmt";
	// System.out.println(cmd);
	// shell.parseAndEvaluate(cmd, stdout);
	// String expected = "muse science yawn";
	// System.out.println(stdout.toString());
	// assertEquals(expected, stdout.toString());
	// }

}
