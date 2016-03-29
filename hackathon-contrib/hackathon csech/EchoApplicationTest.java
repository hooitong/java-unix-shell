/*
 * No error in echo - unless you include the /n is not detected as System.lineSeparator
 */

package ComSubHeadEchoCat;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.EchoException;
import sg.edu.nus.comp.cs4218.impl.app.EchoApplication;

public class EchoApplicationTest {
	static EchoApplication eApp;
	static String[] args;
	OutputStream output;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		eApp = new EchoApplication();
	}
	
	@Before
	public void setUp() throws Exception {
		output = new ByteArrayOutputStream();
	}
	
		
	/*
	 * Bug report:
	 * If we compare to System.lineSeparator(), the application output of new line is not displayed
	 */

	@Test
	public void gotNewLineAfterOutputIfUseSlashN() throws EchoException {
		String temp = "This is a trap";
		args = temp.split(" ");
		output = new ByteArrayOutputStream();

		eApp.run(args, null, output);
		System.out.println("*****" + output.toString() + "****");
		assertEquals(output.toString(), "This is a trap\n");
		//assertEquals(output.toString(), "This is a trap" + System.lineSeparator());
	}

	@After
	public void tearDown() throws Exception {
		args = null;
		output = null;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		eApp = null;

	}

}
