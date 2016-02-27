package sg.edu.nus.comp.cs4218.impl.app;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.scene.control.Tab;
import sg.edu.nus.comp.cs4218.app.Comm;
import sg.edu.nus.comp.cs4218.exception.CommException;
import sg.edu.nus.comp.cs4218.Environment;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CommApplicationTest {

	private static final String NEW_LINE = System.lineSeparator();
	private static final String TAB_LINE = "\t";

	private static CommApplication caTest;
	private static String[] args;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		caTest = new CommApplication();
		args = new String[] { "file1.txt", "file2.txt" };
	}

	@Test
	public void testCommNoMatches() {
		String expectedResult = (TAB_LINE + TAB_LINE + NEW_LINE + TAB_LINE
				+ TAB_LINE + NEW_LINE + TAB_LINE + "banana" + NEW_LINE
				+ "eggplant" + TAB_LINE + NEW_LINE + TAB_LINE + "zucchini");
		String actualResult = caTest.commNoMatches(args);
		assertEquals(expectedResult, actualResult);

	}

	@Test
	public void testCommOnlyFirst() {
		String expectedResult = (TAB_LINE + NEW_LINE + TAB_LINE + NEW_LINE
				+ TAB_LINE + NEW_LINE + "eggplant" + NEW_LINE + TAB_LINE);
		String actualResult = caTest.commOnlyFirst(args);
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testCommOnlySecond() {
		String expectedResult = (TAB_LINE + NEW_LINE + TAB_LINE + NEW_LINE
				+ "banana" + NEW_LINE + TAB_LINE + NEW_LINE + "zucchini");
		String actualResult = caTest.commOnlySecond(args);
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testCommBothMathches() {
		String expectedResult = (TAB_LINE + TAB_LINE + "apple" + NEW_LINE
				+ TAB_LINE + TAB_LINE + "banana" + NEW_LINE + TAB_LINE
				+ "banana" + TAB_LINE + NEW_LINE + "eggplant" + TAB_LINE
				+ TAB_LINE + NEW_LINE + TAB_LINE + "zucchini" + TAB_LINE);
		String actualResult = caTest.commBothMathches(args);
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testCommAllMatches() {
		String expectedResult = ("apple" + NEW_LINE + "banana" + NEW_LINE
				+ TAB_LINE + NEW_LINE + TAB_LINE + NEW_LINE + TAB_LINE);
		String actualResult = caTest.commAllMatches(args);
		assertEquals(expectedResult, actualResult);
	}

	/**
	 * tests the run method without using stdin
	 * 
	 * @throws CommException
	 */
	@Test
	public void testRun() throws CommException {
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		;
		String expectedResult = (TAB_LINE + TAB_LINE + "apple" + NEW_LINE
				+ TAB_LINE + TAB_LINE + "banana" + NEW_LINE + TAB_LINE
				+ "banana" + TAB_LINE + NEW_LINE + "eggplant" + TAB_LINE
				+ TAB_LINE + NEW_LINE + TAB_LINE + "zucchini" + TAB_LINE);
		caTest.run(args, null, stdout);
		assertEquals(expectedResult, stdout.toString());
	}

	/**
	 * tests the run method using stdin
	 * 
	 * @throws CommException
	 */
	@Test
	public void testRunWithStdin() throws CommException {
		String contentStr = "apple" + NEW_LINE + "banana" + NEW_LINE
				+ "eggplant";
		InputStream inputStream = new java.io.ByteArrayInputStream(contentStr.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		String[] args2 = new String[] { "file2.txt" };
		String expectedResult = (TAB_LINE + TAB_LINE + "apple" + NEW_LINE
				+ TAB_LINE + TAB_LINE + "banana" + NEW_LINE + TAB_LINE
				+ "banana" + TAB_LINE + NEW_LINE + "eggplant" + TAB_LINE
				+ TAB_LINE + NEW_LINE + TAB_LINE + "zucchini" + TAB_LINE);
		caTest.run(args2, inputStream, stdout);
		assertEquals(expectedResult, stdout.toString());
	}
};
