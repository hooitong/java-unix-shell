import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.*;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;
import sg.edu.nus.comp.cs4218.impl.app.SortApplication;

import java.io.*;

public class SortApplicationHack {
    private ShellImpl mockShell;
    private ByteArrayOutputStream mockOutput;

    private SortApplication sortApplication;
    private InputStream stdin;
    private OutputStream stdout;

    @Before
    public void setUp() throws Exception {
        mockShell = new ShellImpl();
        mockOutput = new ByteArrayOutputStream();
        sortApplication = new SortApplication();
        stdin = null;
        stdout = new ByteArrayOutputStream();
    }

    /**
     * A wrong order of sorting has been applied, where the sorting order should be
     * Special->Digit->Capital->Lower and only when both belongs to the same class then ascii value is compared.
     *
     * This bug as such does not conform to "Compares each line character by character. A special character (e.g., +) 
     * comes before numbers. A number comes before capital letters. A capital letter comes before small letters, etc.
     * Within each character class, the characters are sorted according to their ​ASCII​ value." Ref spec page 11
     * section sort paragraph 1.
     */
    @Test
    public void testSort1() throws Exception {
        String[] args = { "resource-hack/examples-1/sort.txt" };
        sortApplication.run(args, stdin, stdout);
        String expected = "\"\n>\n{\n3\n9\nM\no";
        assertEquals(expected, stdout.toString());
    }

    /**
     * if a invalid flag e.g. 'k' is used instead of 'n', no exception is thrown at all.
     *
     * This bug is due to violating the syntax "sort [­n] [FILE]" ref spec page 11 section sort
     * paragraph 2.
     */
    @Test
    public void testSort2() throws Exception {
        String[] args = { "-k", "resource-hack/examples-1/sort.txt" };
        sortApplication.run(args, stdin, stdout);
        fail("should throw exception for wrong flag used ie -k instead of -n");
    }

    /**
     * Sort does not use the stdin which is a valid and readable file when no arguments are given
     *
     * The bug does not conform to "FILE  – the name of the file(s). If not specified, use stdin." Ref specs
     * page 11 section sort paragraph 3.
     *
     */
    @Test
    public void testSortIO() throws Exception{
        String cmdline = "sort -n < resource-hack/sort1.txt";
        mockShell.parseAndEvaluate(cmdline, mockOutput);
        String expectedOutput = "sort1.txt";
        assertEquals(expectedOutput, new String(mockOutput.toByteArray()));
    }

    /**
     * When given multiple argument, instead of reading from file or error depending on assumption, sorts the filenames as
     * if they were string literals. This also shows that they have violated the syntax for not parsing arguments as files.
     *
     * This bug is due to the violation of the syntax "sort [­n] [FILE] ... FILE  – the name of the file(s)."
     * Ref specs page 11 section sort paragraph 2.
     */
    @Test
    public void testSortMultipleArgs() throws Exception {
        String cmdline = "sort resource-hack/multi1.txt resource-hack/multi2.txt resource-hack/multi3.txt";
        mockShell.parseAndEvaluate(cmdline, mockOutput);
        // This expected output is one of the assumption simliar to UNIX bash shell where it displays a sorted
        // concatenation of all files. However if you only restrict to one file then you should be expecting an
        // exception which is not in the case of your output.
        String expectedOutput = "abcdefghi";
        assertEquals(expectedOutput, new String(mockOutput.toByteArray()));
    }
}
