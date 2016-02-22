/**
 * 
 */
package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.Stack;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sg.edu.nus.comp.cs4218.exception.TailException;

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
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String testString = "Hey man";
		ByteArrayInputStream bis = new ByteArrayInputStream(testString.getBytes());
		
		String[] arguements = {"-n 1","sample.txt"};
		tailApplication.run(arguements, bis, baos);
		String ss = new String(baos.toByteArray());
		
		assertEquals("Apartments frequently or motionless on reasonable projecting expression. Way mrs end gave tall walk fact bed.",ss);
	}
	
	@Test
	public final void testLessThanNLines() throws TailException 
	{
		int numLines = 3;
		Stack<String> textToExtractFrom = new Stack<String>();
		textToExtractFrom.push("This is the first line.");
		textToExtractFrom.push("This is the second line.");
		LinkedList<String> result = tailApplication.extractTail(textToExtractFrom, numLines);
		
		assertEquals(result.size(),2);
		assertEquals(result.get(0),"This is the first line.");
		assertEquals(result.get(1),"This is the second line.");
	}
	
	@Test
	public void testNonNumericNumLines() throws TailException
	{
	    exception.expect(TailException.class);
	    exception.expectMessage("Number of lines not a number");
	    
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String[] arguments = {"-n ad3","sample.txt"};
		tailApplication.run(arguments, null, baos);
	}
	
	@Test
	public void testZeroNumLines() throws TailException
	{
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String[] arguments = {"-n 0","sample.txt"};
		tailApplication.run(arguments, null, baos);
		String resultString = new String(baos.toByteArray());
		
		assertEquals(resultString,"");
	}

}
