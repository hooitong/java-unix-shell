package sg.edu.nus.comp.cs4218.impl;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Shell;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.exception.CatException;
import sg.edu.nus.comp.cs4218.exception.HeadException;
import sg.edu.nus.comp.cs4218.exception.PipeCommandException;

public class CommandSubstitutionTest {
	private Shell mockShell;
	private ByteArrayOutputStream mockOut;

	@Before
	public void setUp() {
		mockShell = new ShellImpl();
		mockOut = new ByteArrayOutputStream();
	}

	/*
	 * Test basic command substitution - up to one layer of nesting
	 */

	// Test echo nested within echo

	@Test
	public void testCommandSubEcho() throws Exception {
		String command = "echo `echo applepie lemon juice`";
		mockShell.parseAndEvaluate(command, mockOut);
		// System.out.println(mockOut.toString());
		// assertEquals(new String(mockOut.toByteArray(),
		// Charset.defaultCharset()), "a aa aaa aaaa");
	}

	@Test
	public void testCommandSubEcho2() throws Exception {
		String command = "echo `echo lemon juice` `echo applepie`";
		mockShell.parseAndEvaluate(command, mockOut);
		System.out.println(mockOut.toString());
		// assertEquals(new String(mockOut.toByteArray(),
		// Charset.defaultCharset()), "a aa aaa aaaa");
	}

	// Test sub cat
	@Test
	public void testCommandSubCat() throws Exception {
		String command = "echo `cat examples\\file1.txt`";
		mockShell.parseAndEvaluate(command, mockOut);
		System.out.println(mockOut.toString());
		// assertEquals(new String(mockOut.toByteArray(),
		// Charset.defaultCharset()), "a aa aaa aaaa");
	}

	/**
	 * Test whether exception is caught when error in cat command
	 * 
	 */
	@Test(expected = CatException.class)
	public void testCommandSubCatWithError() throws Exception {
		String command = "echo `cat noSuchFile.txt`";
		mockShell.parseAndEvaluate(command, mockOut);
		System.out.println(mockOut.toString());
		// assertEquals(new String(mockOut.toByteArray(),
		// Charset.defaultCharset()), "a aa aaa aaaa");
	}

	// Test sub head
	@Test
	public void testCommandSubHead() throws Exception {
		String command = "echo `head -n 1 examples\\file1.txt`";
		mockShell.parseAndEvaluate(command, mockOut);
		System.out.println(mockOut.toString());
		// assertEquals(new String(mockOut.toByteArray(),
		// Charset.defaultCharset()), "a aa aaa aaaa");
	}

	/*
	 * Test whether exception is caught in application when error in head
	 * command
	 */
	@Test(expected = HeadException.class)
	public void testCommandSubHeadWithError() throws Exception {
		String command = "echo `head examples\\file1.txt -n 1`";
		mockShell.parseAndEvaluate(command, mockOut);
		System.out.println(mockOut.toString());
		// assertEquals(new String(mockOut.toByteArray(),
		// Charset.defaultCharset()), "a aa aaa aaaa");
	}
	
	/*
	 * 
	 */
	@Test
	public void testCommandSubSort() throws Exception {
		
		String command = "echo `cat examples\\numbersort.txt | sort`";
		mockShell.parseAndEvaluate(command, mockOut);
		System.out.println(mockOut.toString());
		
		// assertEquals(new String(mockOut.toByteArray(),
		// Charset.defaultCharset()), "a aa aaa aaaa");
		
		
	}

	/*
	 * Test for correct line read when reading a file and then piping to head
	 */
	@Test
	public void testCommandPipe() throws Exception {
		String command = "echo `cat examples\\file1.txt | head -n 2`";
		mockShell.parseAndEvaluate(command, mockOut);
		System.out.println(mockOut.toString());
		// assertEquals(new String(mockOut.toByteArray(),
		// Charset.defaultCharset()), "a aa aaa aaaa");
	}

	/*
	 * Test for error caught in pipe while command substituting
	 */
	@Test(expected = PipeCommandException.class)
	public void testCommandPipe2() throws Exception {
		String command = "echo `cat nosuchfile.txt | head -n 2`";
		mockShell.parseAndEvaluate(command, mockOut);
		System.out.println(mockOut.toString());
		// assertEquals(new String(mockOut.toByteArray(),
		// Charset.defaultCharset()), "a aa aaa aaaa");
	}

	/*
	 * Test for error caught in pipe while command substituting
	 */
	@Test(expected = PipeCommandException.class)
	public void testCommandPipe3() throws Exception {
		String command = "echo `cat examples\\file1.txt | head n -2`";
		mockShell.parseAndEvaluate(command, mockOut);
		System.out.println(mockOut.toString());
		// assertEquals(new String(mockOut.toByteArray(),
		// Charset.defaultCharset()), "a aa aaa aaaa");
	}
	
	/*
	 * Semicolon operator
	 */
	@Test
	public void testSemicolon() throws Exception {
		String command = "echo testing 123: `echo showing content of file1.txt; cat examples\\file1.txt`";
		//String command = "echo testing 123; cat examples\\file1.txt";
		mockShell.parseAndEvaluate(command, mockOut);
		System.out.println(mockOut.toString());
		// assertEquals(new String(mockOut.toByteArray(),
		// Charset.defaultCharset()), "a aa aaa aaaa");
	}

	/*
	 * Globbing
	 */
		@Test
		public void testGlobbing() throws Exception {
			System.out.println("SPECIAL TEST******************************************");
			String command = "echo `cat examples/*`";
			mockShell.parseAndEvaluate(command, mockOut);
			System.out.println(mockOut.toString());
			System.out.println("*******************************TESTEND");
			// assertEquals(new String(mockOut.toByteArray(),
			// Charset.defaultCharset()), "a aa aaa aaaa");
		}
	
	
	/*
	 * Calendar
	 */
	@Test
	public void testCal() throws Exception {
		String command = "echo testing calendar: `cal`";
		//String command = "echo testing 123; cat examples\\file1.txt";
		mockShell.parseAndEvaluate(command, mockOut);
		System.out.println(mockOut.toString());
		// assertEquals(new String(mockOut.toByteArray(),
		// Charset.defaultCharset()), "a aa aaa aaaa");
	}
	
	/*
	 * Date
	 */
	@Test
	public void testDate() throws Exception {
		String command = "echo `echo today date is:` `date`";
		//String command = "echo testing 123; cat examples\\file1.txt";
		mockShell.parseAndEvaluate(command, mockOut);
		System.out.println(mockOut.toString());
		// assertEquals(new String(mockOut.toByteArray(),
		// Charset.defaultCharset()), "a aa aaa aaaa");
	}
	
	@Test (expected = ShellException.class)
	public void testDate2() throws Exception {
		String command = "echo today date is:`date`";
		//String command = "echo testing 123; cat examples\\file1.txt";
		mockShell.parseAndEvaluate(command, mockOut);
		System.out.println(mockOut.toString());
		// assertEquals(new String(mockOut.toByteArray(),
		// Charset.defaultCharset()), "a aa aaa aaaa");
	}
	
	/*
	 * Test bc
	 */
	@Test
	public void testBC() throws Exception {
		String command = "echo what is the sum of 1+1: `echo 1+1|bc`";
		//String command = "echo testing 123; cat examples\\file1.txt";
		mockShell.parseAndEvaluate(command, mockOut);
		System.out.println(mockOut.toString());
		// assertEquals(new String(mockOut.toByteArray(),
		// Charset.defaultCharset()), "a aa aaa aaaa");
	}
	
	//Read from file
	@Test
	public void testRemoveNewLineSymbol() throws Exception {
		String command = "echo `cat examples\\file2.txt`";
		mockShell.parseAndEvaluate(command, mockOut);
		System.out.println(mockOut.toString());
		// assertEquals(new String(mockOut.toByteArray(),
		// Charset.defaultCharset()), "a aa aaa aaaa");
	}
	
	
	/**
	 * 
	 * Input: assume that it takes in a string of command Output: assume that it
	 * output the final result
	 * 
	 * Test case:
	 * 
	 * error: bad symbols error: no such command error: bad commands
	 * 
	 */

	/*
	 * @Test public void testRemoveNewLineSymbol() throws Exception { String
	 * command = "echo `echo a\n aa\n aaa\n aaaa`";
	 * mockShell.parseAndEvaluate(command, mockOut); assertEquals(new
	 * String(mockOut.toByteArray(), Charset.defaultCharset()), "a aa aaa aaaa"
	 * ); }
	 * 
	 * @Test public void testMultipleCommands() throws Exception { String
	 * command = "echo 12345 `echo 67890 `echo abcde``";
	 * mockShell.parseAndEvaluate(command, mockOut); assertEquals(new
	 * String(mockOut.toByteArray(), Charset.defaultCharset()),
	 * "12345 67890echo abcde"); }
	 */

	/**
	 * File looks like this
	 * 
	 * apple banana
	 */

	/*
	 * @Test public void testCommandAndOutputOneLine() throws Exception { String
	 * command = "echo `head -n 1 examples/file1.txt`";
	 * mockShell.parseAndEvaluate(command, mockOut); assertEquals(new
	 * String(mockOut.toByteArray(), Charset.defaultCharset()), "apple"); }
	 * 
	 * @Test public void testCommandAndOutputWhereFirstLineIsSubstituteAsSpace()
	 * throws Exception { String command = "echo `head -n 2 examples/file1.txt`"
	 * ; mockShell.parseAndEvaluate(command, mockOut); assertEquals(new
	 * String(mockOut.toByteArray(), Charset.defaultCharset()), "banana"); }
	 * 
	 * @Test(expected = ShellException.class) public void testMissingBackQuote()
	 * throws Exception { String command = "echo echo a\n aa\n aaa\n aaaa`";
	 * mockShell.parseAndEvaluate(command, mockOut); }
	 * 
	 * // Error is cannot read 12345 given that README.md exist
	 * 
	 * @Test(expected = ShellException.class) public void testNoSuchDirectory()
	 * throws Exception { String command = "sort `cat README.md | echo 12345`";
	 * mockShell.parseAndEvaluate(command, mockOut); }
	 * 
	 * @Test(expected = ShellException.class) public void testInvalidCommand()
	 * throws Exception { String command = "echo `hed -n 1 README.md`";
	 * mockShell.parseAndEvaluate(command, mockOut); }
	 * 
	 * //Test case has nested commands
	 * 
	 * @Test public void testCommandSubEcho3() throws Exception { String command
	 * = "echo `echo lemon juice `echo applepie``";
	 * mockShell.parseAndEvaluate(command, mockOut);
	 * System.out.println(mockOut.toString()); //assertEquals(new
	 * String(mockOut.toByteArray(), Charset.defaultCharset()), "a aa aaa aaaa"
	 * ); }
	 * 
	 */
	
	/*
	 * Test bc - under integration test
	 */
	@Test (expected = ShellException.class)
	public void testBCWithIntegrationTest() throws Exception {
		System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
		String command = "cat file1`echo .md`";
		//String command = "echo testing 123; cat examples\\file1.txt";
		mockShell.parseAndEvaluate(command, mockOut);
		System.out.println(mockOut.toString());
		// assertEquals(new String(mockOut.toByteArray(),
		// Charset.defaultCharset()), "a aa aaa aaaa");
	}
	
	
}
