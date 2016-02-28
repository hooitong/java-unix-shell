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
	private static ByteArrayOutputStream baos;
	private static ByteArrayInputStream bis;
	private static String fileToRead = "examples/sample.txt";
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		tailApplication = new TailApplication();
	}

	@Before
	public void setUp() throws Exception 
	{
		baos = new ByteArrayOutputStream();
		bis = new ByteArrayInputStream("This is a test string".getBytes());
	}

	@Test
	public final void testRun() throws TailException 
	{
		String[] arguments = {"-n","1",fileToRead};
		tailApplication.run(arguments, bis, baos);
		String result = new String(baos.toByteArray());
		
		assertEquals("Apartments frequently or motionless on reasonable projecting expression. Way mrs end gave tall walk fact bed.",result);
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
	    
		String[] arguments = {"-n","ad3",fileToRead};
		tailApplication.run(arguments, null, baos);
	}
	
	@Test
	public void testZeroNumLines() throws TailException
	{
		String[] arguments = {"-n","0",fileToRead};
		tailApplication.run(arguments, null, baos);
		String resultString = new String(baos.toByteArray());
		
		assertEquals(resultString,"");
	}
	
	@Test
	public void testNegativeNumLines() throws TailException
	{
		exception.expect(TailException.class);
	    exception.expectMessage("Number of lines should be at least 0");
	    
		String[] arguments = {"-n","-5",fileToRead};
		tailApplication.run(arguments, null, baos);
	}
	
	@Test
	public void testNullArg() throws TailException
	{
		exception.expect(TailException.class);
	    exception.expectMessage("Null pointer exception - stdin is not defined");
	    
		tailApplication.run(null, null, baos);
	}
	
	@Test
	public void testZeroArg() throws TailException
	{
		exception.expect(TailException.class);
	    exception.expectMessage("Null pointer exception - stdin is not defined");
	    String[] arguments = {};
		tailApplication.run(arguments, null, baos);
	}
	
	@Test
	public void testOneArgWithOptionsOnly() throws TailException
	{
		exception.expect(TailException.class);
	    exception.expectMessage("Null pointer exception - stdin is not defined");
	    String[] arguments = {"-n","20"};
		tailApplication.run(arguments, null, baos);
	}
	
	@Test
	public void testOneArgWithFileOnly() throws TailException
	{
	    String[] arguments = {fileToRead};
		tailApplication.run(arguments, null, baos);
		String result = new String(baos.toByteArray());
		String[] resultLines = result.split(NEW_LINE);
		assertEquals("Apartments frequently or motionless on reasonable projecting expression. Way mrs end gave tall walk fact bed.",resultLines[resultLines.length-1]);
	}
	
	
	@After
	public void tearDown() throws Exception 
	{
		baos = null;
		bis = null;
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception 
	{
		tailApplication = null;
	}

}
