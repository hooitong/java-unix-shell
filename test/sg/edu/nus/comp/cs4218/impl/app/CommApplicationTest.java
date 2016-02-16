package sg.edu.nus.comp.cs4218.impl.app;

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

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCommNoMatches() throws CommException {
		String expectedResult = (TAB_LINE + TAB_LINE + NEW_LINE + TAB_LINE
				+ TAB_LINE + NEW_LINE + TAB_LINE + "banana" + NEW_LINE
				+ "eggplant" + TAB_LINE + NEW_LINE + TAB_LINE + "zucchini");
		String actualResult = caTest.commNoMatches(args, null);
		assertEquals(expectedResult, actualResult);

	}

	@Test
	public void testCommOnlyFirst() throws CommException {
		String expectedResult = (TAB_LINE + NEW_LINE + TAB_LINE + NEW_LINE
				+ TAB_LINE + NEW_LINE + "eggplant" + NEW_LINE + TAB_LINE);
		String actualResult = caTest.commOnlyFirst(args, null);
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testCommOnlySecond() throws CommException {
		String expectedResult = (TAB_LINE + NEW_LINE + TAB_LINE + NEW_LINE
				+ "banana" + NEW_LINE + TAB_LINE + NEW_LINE + "zucchini");
		String actualResult = caTest.commOnlySecond(args, null);
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testCommBothMathches() throws CommException {
		String expectedResult = (TAB_LINE + TAB_LINE + "apple" + NEW_LINE
				+ TAB_LINE + TAB_LINE + "banana" + NEW_LINE + TAB_LINE
				+ "banana" + TAB_LINE + NEW_LINE + "eggplant" + TAB_LINE
				+ TAB_LINE + NEW_LINE + TAB_LINE + "zucchini" + TAB_LINE);
		String actualResult = caTest.commBothMathches(args, null);
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testCommAllMatches() throws CommException {
		String expectedResult = ("apple" + NEW_LINE + "banana" + NEW_LINE
				+ TAB_LINE + NEW_LINE + TAB_LINE + NEW_LINE + TAB_LINE);
		String actualResult = caTest.commAllMatches(args, null);
		assertEquals(expectedResult, actualResult);
	}


}
