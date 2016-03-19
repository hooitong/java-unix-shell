package integrationTest;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;

import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class CommandSubstituitionTest 
{
	private static final String RESOURCE_ROOT = "examples-integration/pair-piping/";
    private ShellImpl mockShell;
    private ByteArrayOutputStream mockOutputStream;
    
    @Before
    public void setUp() {
        mockShell = new ShellImpl();
        mockOutputStream = new ByteArrayOutputStream();
    }

   
    /**
     * Tests the piping evaluation from Cat (multiple files) to Echo.
     *
     * @throws Exception
     */
    @Test
    public void cmdSubSort() throws Exception {
        String testInput = "sort `cat files.txt | head ­n 1";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
        String output = new String(mockOutputStream.toByteArray(), "UTF-8");
        //String expectedOutput = "abctest" + System.lineSeparator();
        //assertEquals(expectedOutput, output);
    }
}
