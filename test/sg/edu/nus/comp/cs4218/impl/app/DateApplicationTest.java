package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sg.edu.nus.comp.cs4218.exception.DateException;

public class DateApplicationTest {
	private static DateApplication dateApplication;
	private static ByteArrayOutputStream baos;
	private static ByteArrayInputStream bis;

	private static final String NEW_LINE = System.lineSeparator();

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dateApplication = new DateApplication();
	}

	@Before
	public void setUp() throws Exception {
		baos = new ByteArrayOutputStream();
		bis = new ByteArrayInputStream("This is a test string".getBytes());
	}

	/**
	 * Test the one and only default case
	 *
	 * @throws Exception
	 */
	@Test
	public final void testRun() throws DateException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
		dateApplication.run(null, bis, baos);
		String output = new String(baos.toByteArray());
		assertEquals(simpleDateFormat.format(new Date()) + NEW_LINE, output);
	}

	@Test
	public final void testUnrequiredArg() throws DateException {
		exception.expect(DateException.class);
		exception.expectMessage("No arguments expected");
		String[] arguments = { "-d 10" };
		dateApplication.run(arguments, bis, baos);
	}

	@Test
	public final void testNullStdOut() throws DateException {
		exception.expect(DateException.class);
		exception.expectMessage("Cannot write to stdout as it is null");
		dateApplication.run(null, bis, null);
	}

	@After
	public void tearDown() throws Exception {
		baos = null;
		bis = null;
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		dateApplication = null;
	}
}

// Java Byte Array to String to Byte Array
// http://stackoverflow.com/questions/6684665/java-byte-array-to-string-to-byte-array
// Question by : http://stackoverflow.com/users/843387/0909em
// Answer by : http://stackoverflow.com/users/1313268/coraythan

// Is there an alternative to FileOutputStream type
// http://stackoverflow.com/questions/17595487/is-there-an-alternative-to-fileoutputstream-type-which-does-not-create-a-file
// Question by : http://stackoverflow.com/users/1904663/benvg
// Answer by : http://stackoverflow.com/users/1886855/marco-forberg