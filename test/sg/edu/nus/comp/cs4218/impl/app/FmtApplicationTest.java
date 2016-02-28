package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.FmtException;

public class FmtApplicationTest 
{
	private static FmtApplication fmtApplication;
	private static final String NEW_LINE = System.lineSeparator();
	private static ByteArrayOutputStream baos;
	private static ByteArrayInputStream bis;
	private static String fileToRead = "examples/sample.txt";
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		fmtApplication = new FmtApplication();
	}
	
	@Before
	public void setUp() throws Exception 
	{
		baos = new ByteArrayOutputStream();
		bis = new ByteArrayInputStream("This is a test string".getBytes());
	}
	
	@Test
	public final void testWrapText() throws FmtException 
	{
		int wrapValue = 11;
		String wrappedText = fmtApplication.wrapText("The random String is tall", wrapValue);
		String expectedString = "The random" + NEW_LINE + "String is" + NEW_LINE + "tall";
		assertEquals(expectedString,wrappedText);
	}

	@Test
	public void testTooShortWrapWidth() throws FmtException
	{
	    exception.expect(FmtException.class);
	    exception.expectMessage("Wrap width too short");
	    int wrapValue = 5;
		fmtApplication.wrapText("The random String", wrapValue);
	}
	
	@Test
	public void testNegativeWrapWidth() throws FmtException
	{
	    exception.expect(FmtException.class);
	    exception.expectMessage("Wrap width should be at least 1");
	    
		String[] arguments = {"-w","-50",fileToRead};
		fmtApplication.run(arguments, null, baos);
	}
	
	@Test
	public void testZeroWrapWidth() throws FmtException
	{
	    exception.expect(FmtException.class);
	    exception.expectMessage("Wrap width should be at least 1");
	    
		String[] arguments = {"-w","0",fileToRead};
		fmtApplication.run(arguments, null, baos);
	}
	
	@Test
	public void testNonNumericWrapWidth() throws FmtException
	{
	    exception.expect(FmtException.class);
	    exception.expectMessage("Wrap width not a number");
	    
		String[] arguments = {"-w","ad3",fileToRead};
		fmtApplication.run(arguments, null, baos);
	}
	
	@Test
	public void testInvalidWrapWidthFlag() throws FmtException
	{
	    exception.expect(FmtException.class);
	    exception.expectMessage("Incorrect flag used to denote number of lines to print");
	    
		String[] arguments = {"-n","50",fileToRead};
		fmtApplication.run(arguments, null, baos);
	}
	
	@Test
	public void testEmptyText() throws FmtException
	{
		int wrapValue = 11;
		String wrappedText = fmtApplication.wrapText("", wrapValue);
		String expectedString = "";
		assertEquals(expectedString,wrappedText);
	}
	
	@Test
	public void testReadFromFile() throws FmtException
	{
		Path currentDir = Paths.get(Environment.currentDirectory);
		Path filePath = currentDir.resolve("testRead.txt");
		String readString = fmtApplication.readFromFile(filePath);
		String expectedString = "Selon la préfecture, des engins explosifs avaient été";
		assertEquals(expectedString,readString);
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
		fmtApplication = null;
	}
}

//References
//How do you assert that a certain exception is thrown in JUnit 4 tests?
//http://stackoverflow.com/questions/156503/how-do-you-assert-that-a-certain-exception-is-thrown-in-junit-4-tests
//Qn By : http://stackoverflow.com/users/1666/scdf
//Ans By : http://stackoverflow.com/users/21234/skaffman
