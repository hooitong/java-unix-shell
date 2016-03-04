import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.nio.file.Paths;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.impl.cmd.CallCommand;

public class RedirectionTest {
	private CallCommand stubCommand;

	@Before
	public void setUp() {
		stubCommand = new CallCommand("");
	}
	
	/**
	 * Test whether the input redirection can be parsed from the given command
	 * line.
	 *
	 * @throws Exception
	 */
	@Test
	public void testExtractInputRedir() throws Exception {
		String stringToTest = "sort -n < file1.txt > file2.txt";
		Vector<String> cmdVector = new Vector<String>();
		cmdVector.addElement("");
		cmdVector.addElement("");
		stubCommand.extractInputRedir(stringToTest, cmdVector, 8);
		assertEquals(cmdVector.get(0), "file1.txt");
	}

	/**
	 * Test whether the output redirection can be parsed from the given command
	 * line.
	 *
	 * @throws Exception
	 */
	@Test
	public void testExtractOutputRedir() throws Exception {
		String stringToTest = "sort -n < file1.txt > file2.txt";
		Vector<String> cmdVector = new Vector<String>();
		cmdVector.addElement("");
		cmdVector.addElement("");
		stubCommand.extractOutputRedir(stringToTest, cmdVector, 20);
		assertEquals(cmdVector.get(1), "file2.txt");
	}
}