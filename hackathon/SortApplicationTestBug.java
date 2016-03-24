package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.SortException;

public class SortApplicationTestBug {

	private SortApplication sortApplication;
	private InputStream stdin;
	private OutputStream stdout;

	private static final String FILE1 = "sort_file1.txt";
	private static final Path PATH_FILE1 = Paths.get(FILE1);
	private static final String FILE1_CONTENT = "1\n2\n10";
	private static final String FILE1_SORT_DEFAULT = "1\r\n10\r\n2\r\n";
	private static final String FILE1_SORT_NUM = "1\r\n2\r\n10\r\n";

	private static final String FILE2 = "sort_file2.txt";
	private static final Path PATH_FILE2 = Paths.get(FILE2);
	private static final String FILE2_CONTENT = "yO1\\n\n2Yo\\t\n1yO0\\r\n2yO0\\n\n";
	private static final String FILE2_SORT_DEFAULT = "1yO0\\r\r\n2Yo\\t\r\n2yO0\\n\r\nyO1\\n\r\n";
	private static final String FILE2_SORT_NUM = "1yO0\\r\r\n2Yo\\t\r\n2yO0\\n\r\nyO1\\n\r\n";

	private static final String FILE3 = "sort_file3.txt";
	private static final Path PATH_FILE3 = Paths.get(FILE3);
	private static final String FILE3_CONTENT = "一\\n\n二\\t\n三\\r\n四\\n\n";
	private static final String FILE3_SORT_DEFAULT = "一\\n\r\n三\\r\r\n二\\t\r\n四\\n\r\n";
	// private static final String FILE3_SORT_NUM =
	// "1yO0\\r\r\n2Yo\\t\r\n2yO0\\n\r\nyO1\\n\r\n";

	private static final String EXP_NULL_POINTER = "Null Pointer Exception";
	private static final String EXP_FILE_NOT_FOUND = "No such file exists";
	private static final String EXP_NO_ARGS = "No arguments!";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Files.deleteIfExists(PATH_FILE1);
		Files.deleteIfExists(PATH_FILE2);

		Files.createFile(PATH_FILE1);
		File file1 = new File(PATH_FILE1.toString());
		FileWriter fileWriter = new FileWriter(file1);
		fileWriter.write(FILE1_CONTENT);
		fileWriter.flush();
		fileWriter.close();

		Files.createFile(PATH_FILE2);
		File file2 = new File(PATH_FILE2.toString());
		fileWriter = new FileWriter(file2);
		fileWriter.write(FILE2_CONTENT);
		fileWriter.flush();
		fileWriter.close();

		Files.createFile(PATH_FILE3);
		File file3 = new File(PATH_FILE3.toString());
		fileWriter = new FileWriter(file3);
		fileWriter.write(FILE3_CONTENT);
		fileWriter.flush();
		fileWriter.close();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		Files.deleteIfExists(PATH_FILE1);
		Files.deleteIfExists(PATH_FILE2);
		Files.deleteIfExists(PATH_FILE3);
	}

	@Before
	public void setUp() throws Exception {
		sortApplication = new SortApplication();
		stdin = null;
		stdout = new ByteArrayOutputStream();
	}

	@After
	public void tearDown() throws Exception {
		sortApplication = null;
		stdin = null;
		stdout = null;
	}

	/**
	 * This bugs shows that a wrong order of sorting has been applied. order
	 * should be Special->Digit->Capital->Lower as stated in the project
	 * description. Only when both belongs to the same class then ascii value is
	 * compared.
	 * 
	 * @throws SortException
	 */
	@Test
	public void testSort() throws SortException {
		String[] args = { "sort.txt" };
		sortApplication.run(args, stdin, stdout);
		String expected = "\"\n>\n{\n3\n9\nM\no";
		assertEquals(expected, stdout.toString());
	}
	
	/**
	 * This bugs shows that if a 'k' instead is used instead of 'n', no exception is thrown for invalid flag
	 * 
	 * @throws SortException
	 */
	@Test
	public void testSort2() throws SortException {
		String[] args = { "-k", "sort.txt" };
		sortApplication.run(args, stdin, stdout);
		fail("should throw exception for wrong flag used ie -k instead of -n");
	}
}
