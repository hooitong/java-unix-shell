/**
 * 
 */
package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sg.edu.nus.comp.cs4218.exception.TailException;

/**
 * @author Yusuf
 *
 */
public class TailApplicationTest 
{
	private static TailApplication tailApplication;
	private static final String NEW_LINE = System.lineSeparator();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		tailApplication = new TailApplication();
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
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	
	@Test
	public final void testRun() throws TailException 
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		String testString = "Hey man";
		ByteArrayInputStream bis = new ByteArrayInputStream(testString.getBytes());
		
		String[] arguements = {"-n 1","sample.txt"};
		tailApplication.run(arguements, bis, bos);
		String ss = new String(bos.toByteArray());
		
		assertEquals("Apartments frequently or motionless on reasonable projecting expression. Way mrs end gave tall walk fact bed.",ss);
	}

}
