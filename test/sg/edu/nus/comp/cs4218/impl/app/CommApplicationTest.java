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
	private static String col1Result, col2Result, col3Result, bothMatchResult, noMatchResult;

	private static CommApplication caTest;
	private static String[] args;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		caTest = new CommApplication();
		args = new String[] { "examples/file1.txt", "examples/file2.txt" };
		col1Result = (TAB_LINE + NEW_LINE + TAB_LINE + NEW_LINE + TAB_LINE + NEW_LINE + "eggplant" + NEW_LINE
				+ TAB_LINE);
		col2Result = (TAB_LINE + NEW_LINE + TAB_LINE + NEW_LINE + "banana" + NEW_LINE + TAB_LINE + NEW_LINE
				+ "zucchini");
		col3Result = ("apple" + NEW_LINE + "banana" + NEW_LINE + TAB_LINE + NEW_LINE + TAB_LINE + NEW_LINE + TAB_LINE);

		String[] col1ResultArr = col1Result.split(NEW_LINE);
		String[] col2ResultArr = col2Result.split(NEW_LINE);
		String[] col3ResultArr = col3Result.split(NEW_LINE);
		StringBuilder sbBoth = new StringBuilder("");
		StringBuilder sbNone = new StringBuilder("");
		for (int i = 0; i < col1ResultArr.length - 1; i++) {
			sbBoth.append(col1ResultArr[i]).append(col2ResultArr[i]).append(col3ResultArr[i]).append(NEW_LINE);
			sbNone.append(col1ResultArr[i]).append(col2ResultArr[i]).append(NEW_LINE);
		}
		sbBoth.append(col1ResultArr[col1ResultArr.length - 1]).append(col2ResultArr[col2ResultArr.length - 1])
				.append(col3ResultArr[col3ResultArr.length - 1]);
		sbNone.append(col1ResultArr[col1ResultArr.length - 1]).append(col2ResultArr[col2ResultArr.length - 1]);
		bothMatchResult = sbBoth.toString();
		noMatchResult = sbNone.toString();
	}

	@Test
	public void testCommNoMatches() {
		String actualResult = caTest.commNoMatches(args);
		assertEquals(noMatchResult, actualResult);

	}

	@Test
	public void testCommOnlyFirst() {
		String actualResult = caTest.commOnlyFirst(args);
		assertEquals(col1Result, actualResult);
	}

	@Test
	public void testCommOnlySecond() {
		String actualResult = caTest.commOnlySecond(args);
		assertEquals(col2Result, actualResult);
	}

	@Test
	public void testCommBothMathches() {
		String actualResult = caTest.commBothMathches(args);
		System.out.println(bothMatchResult);
		assertEquals(bothMatchResult, actualResult);
	}

	@Test
	public void testCommAllMatches() {
		String actualResult = caTest.commAllMatches(args);
		assertEquals(col3Result, actualResult);
	}

	/**
	 * tests the run method using args for both files
	 * 
	 * @throws CommException
	 */
	@Test
	public void testRunArgsOnly() throws CommException {
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		caTest.run(args, null, stdout);
		assertEquals(bothMatchResult, stdout.toString());
	}

	/**
	 * tests the run method using stdin and args
	 * 
	 * @throws CommException
	 */
	@Test
	public void testRunWithArgsStdin() throws CommException {
		String contentStr = "apple" + NEW_LINE + "banana" + NEW_LINE + "eggplant";
		InputStream inputStream = new java.io.ByteArrayInputStream(contentStr.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		String[] args2 = new String[] { "examples/file2.txt" };
		caTest.run(args2, inputStream, stdout);
		assertEquals(bothMatchResult, stdout.toString());
	}

	/**
	 * tests the run method using args for 1 file but missing stdin
	 * 
	 * @throws CommException
	 */
	@Test(expected = CommException.class)
	public void testRunArgsMissingStdin() throws CommException {
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		String[] args1 = new String[] { "file3.txt" };
		caTest.run(args1, null, stdout);
	}

	/**
	 * tests the run method but missing args and stdin
	 * 
	 * @throws CommException
	 */
	@Test(expected = CommException.class)
	public void testRunMissingArgsStdin() throws CommException {
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		String[] args1 = new String[] {};
		caTest.run(args1, null, stdout);
	}

	/**
	 * tests the run method but missing args
	 * 
	 * @throws CommException
	 */
	@Test(expected = CommException.class)
	public void testRunStdinMissingArgs() throws CommException {
		String contentStr = "PINEAPPLE" + NEW_LINE + "PEAR" + NEW_LINE + "MANGO";
		InputStream inputStream = new java.io.ByteArrayInputStream(contentStr.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		String[] args1 = new String[] {};
		caTest.run(args1, inputStream, stdout);
	}
}
