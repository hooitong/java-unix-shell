package sg.edu.nus.comp.cs4218.impl.app;
/***
 * This class is test for Echoapplication
 * Application takes in user input in the argument
 * And add new line after each arg
 * Then present in the output stream.
 */


import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.EchoException;

public class EchoApplicationTest {

	
	static EchoApplication ea;
	static String[] args;
	InputStream is;
	OutputStream op;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ea = new EchoApplication();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		args = null;
	}


	// Case 1: Where arg is null - No input to write
	@Test(expected = EchoException.class)
	public void testRun() throws EchoException{
		op = System.out;
		ea.run(null, null, op);

	}
	
	// Case 2: Stdout is null - nowhere to output
		@Test(expected = EchoException.class)
		public void testRun2() throws EchoException{
			
			args = new String[1];
			args[0] = "test";
			ea.run(args, null, null);

		}

		// Case 3: Stdout is null - nowhere to output
				@Test
				public void testRun3() throws EchoException{
					
					args = new String[1];
					args[0] = "test";
					op =  new ByteArrayOutputStream();
					ea.run(args, null, op);
					assertEquals(op.toString(), args[0] + "\n");

				}

		// Case 4: empty string in args
				@Test
				public void testRun4()throws EchoException{
					
					args = new String[1];
					args[0] = "";
					op =  new ByteArrayOutputStream();
					ea.run(args, null, op);
					assertEquals(op.toString(), "\n\n");
				}	
				
				
				// Case 5: 
				// Excepted output: This is a trap.
				@Test
				public void testRun6() throws EchoException{
					
					args = new String[1];
					args[0] = "This is a trap.";
					op =  new ByteArrayOutputStream();
					ea.run(args, null, op);
					assertEquals(op.toString(), args[0] + "\n");

				}	
				
				// Case 6: Proper run of the system
				@Test
				public void testRun7() throws EchoException{
					String temp = "This is a trap";
					args = temp.split(" ");
					op =  new ByteArrayOutputStream();
					
					ea.run(args, null, op);
				}	
				
				
				
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		ea = null;
		args = null;

	}


}
