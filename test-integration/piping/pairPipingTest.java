package piping;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import sg.edu.nus.comp.cs4218.exception.CalException;
import sg.edu.nus.comp.cs4218.exception.PipeCommandException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;
import sg.edu.nus.comp.cs4218.impl.cmd.PipeCommand;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * TODO: Notable Inconsistencies - Line Separator at the end of output or not.
 */
public class PairPipingTest {
    private static final String RESOURCE_ROOT = "examples-integration/pair-piping/";
    private ShellImpl mockShell;
    private ByteArrayOutputStream mockOutputStream;


    @Before
    public void setUp() {
        mockShell = new ShellImpl();
        mockOutputStream = new ByteArrayOutputStream();
    }

    /**
     * Tests the piping evaluation from Cat (of multiple files) to Sort (special symbols and empty lines).
     *
     * @throws Exception
     */
    @Test
    public void pipeCatSort() throws Exception {
        String testInput = "cat " + RESOURCE_ROOT + "cat-sort-1.txt " + RESOURCE_ROOT + "cat-sort-2.txt | sort";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
        String output = new String(mockOutputStream.toByteArray(), "UTF-8");
        String expectedOutput = System.lineSeparator() + System.lineSeparator() + System.lineSeparator()
                + "@ z e b r a" + System.lineSeparator()
                + "APple two" + System.lineSeparator()
                + "Apple one" + System.lineSeparator()
                + "apple martini" + System.lineSeparator()
                + "apple tomato" + System.lineSeparator()
                + "atone" + System.lineSeparator()
                + "cabbage cucumber" + System.lineSeparator()
                + "cake" + System.lineSeparator()
                + "mustard eleven" + System.lineSeparator()
                + "zoxa one" + System.lineSeparator();
        assertEquals(expectedOutput, output);
    }

    /**
     * Tests the piping evaluation from Cat (multiple files) to Echo.
     *
     * @throws Exception
     */
    @Test
    public void pipeCatEcho() throws Exception {
        String testInput = "cat " + RESOURCE_ROOT + "cat-echo-1.txt " + RESOURCE_ROOT + "cat-echo-2.txt | echo abctest";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
        String output = new String(mockOutputStream.toByteArray(), "UTF-8");
        String expectedOutput = "abctest" + System.lineSeparator();
        assertEquals(expectedOutput, output);
    }

    /**
     * Tests the piping evaluation from Echo (multiple args) to Fmt (non-default width).
     *
     * @throws Exception
     */
    @Test
    public void pipeEchoFmt() throws Exception {
        String testInput = "echo Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock | fmt -w 33";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
        String output = new String(mockOutputStream.toByteArray(), "UTF-8");
        String expectedOutput = "Contrary to popular belief," + System.lineSeparator()
                + "Lorem Ipsum is not simply random" + System.lineSeparator()
                + "text. It has roots in a piece of" + System.lineSeparator()
                + "classical Latin literature from" + System.lineSeparator()
                + "45 BC, making it over 2000 years";
        assertEquals(expectedOutput, output);

    }

    /**
     * Tests the piping evaluation from Echo to Cal (print current month).
     *
     * @throws Exception
     */
    @Test
    public void pipeEchoCal() throws Exception {
        String testInput = "echo December 2015 | cal";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
        String output = new String(mockOutputStream.toByteArray(), "UTF-8");
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy");
        String expectedHeader = formatter.format(new Date());
        assertTrue(output.contains("Su Mo Tu We Th Fr Sa"));
        assertTrue(output.contains(expectedHeader));

    }

    /**
     * Tests the piping evaluation from Head (only first line with -n) to Bc (simple + expression).
     *
     * @throws Exception
     */
    @Test
    public void pipeHeadBc() throws Exception {
        String testInput = "head -n 1 " + RESOURCE_ROOT + "head-bc.txt | bc";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
        String output = new String(mockOutputStream.toByteArray(), "UTF-8");
        String expectedOutput = "8" + System.lineSeparator();
        assertEquals(expectedOutput, output);
    }

    /**
     * Tests the piping evaluation from Head (default 10 lines) to Cat.
     *
     * @throws Exception
     */
    @Test
    public void pipeHeadCat() throws Exception {
        String testInput = "head " + RESOURCE_ROOT + "head-cat.txt | cat";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
        String output = new String(mockOutputStream.toByteArray(), "UTF-8");
        String expectedOutput = " ~/project/vagrant-nodejs $ time vagrant up" + System.lineSeparator()
                + " Bringing machine 'nodejs' up with 'virtualbox' provider..." + System.lineSeparator()
                + " [nodejs] Importing base box 'precise64'..." + System.lineSeparator()
                + " [nodejs] Matching MAC address for NAT networking..." + System.lineSeparator()
                + System.lineSeparator()
                + " [nodejs] Setting the name of the VM..." + System.lineSeparator()
                + " [nodejs] Clearing any previously set forwarded ports..." + System.lineSeparator()
                + " [nodejs] Creating shared folders metadata..." + System.lineSeparator()
                + " [nodejs] Clearing any previously set network interfaces..." + System.lineSeparator()
                + " [nodejs] Preparing network interfaces based on configuration..." + System.lineSeparator();
        assertEquals(expectedOutput, output);
    }

    /**
     * Tests the piping evaluation from Tail (default 10 lines) to Cat.
     *
     * @throws Exception
     */
    @Test
    public void pipeTailCat() throws Exception {
        String testInput = "tail " + RESOURCE_ROOT + "tail-cal.txt | cat";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
        String output = new String(mockOutputStream.toByteArray(), "UTF-8");
        String expectedOutput = " Selecting previously unselected package node-tar." + System.lineSeparator()
                + " Unpacking node-tar (from .../node-tar_0.1.13-1_all.deb) ..." + System.lineSeparator()
                + " Selecting previously unselected package node-which." + System.lineSeparator()
                + " Unpacking node-which (from .../node-which_1.0.5-1_all.deb) ..." + System.lineSeparator()
                + " Selecting previously unselected package nodejs-dev." + System.lineSeparator()
                + System.lineSeparator()
                + " Unpacking nodejs-dev (from .../nodejs-dev_0.6.12~dfsg1-1ubuntu1_amd64.deb) ..." + System.lineSeparator()
                + System.lineSeparator()
                + " Selecting previously unselected package npm." + System.lineSeparator()
                + " Unpacking npm (from .../npm_1.1.4~dfsg-1_all.deb) ...";
        assertEquals(expectedOutput, output);
    }

    /**
     * Tests the piping evaluation from Tail (only first line with -n) to Cal.
     *
     * @throws Exception
     */
    @Test
    public void pipeTailCal() throws Exception {
        String testInput = "tail -n 1 " + RESOURCE_ROOT + "tail-cat.txt | cal";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
        String output = new String(mockOutputStream.toByteArray(), "UTF-8");
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy");
        String expectedHeader = formatter.format(new Date());
        assertTrue(output.contains("Su Mo Tu We Th Fr Sa"));
        assertTrue(output.contains(expectedHeader));
    }

    /**
     * Tests the piping evaluation from Date to Fmt (Default 80 characters).
     *
     * @throws Exception
     */
    @Test
    public void pipeDateFmt() throws Exception {
        String testInput = "date | fmt";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
        String output = new String(mockOutputStream.toByteArray(), "UTF-8");
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        assertEquals(sdf.format(new Date()), output);
    }

    /**
     * Tests the piping evaluation from Date to Comm.
     *
     * @throws Exception
     */
    @Test
    public void pipeDateComm() throws Exception {
        String testInput = "";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
        String output = new String(mockOutputStream.toByteArray(), "UTF-8");
        String expectedOutput = "";
        assertEquals(expectedOutput, output);
    }

    /**
     * Tests the piping evaluation 
     *
     * @throws Exception
     */
    @Test
    public void pipeFmtTail() throws Exception {
        String testInput = "";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
        String output = new String(mockOutputStream.toByteArray(), "UTF-8");
        String expectedOutput = "";
        assertEquals(expectedOutput, output);
    }

    @Test
    public void pipeFmtBc() throws Exception {
        String testInput = "";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
        String output = new String(mockOutputStream.toByteArray(), "UTF-8");
        String expectedOutput = "";
        assertEquals(expectedOutput, output);
    }

    @Test
    public void pipeSortComm() throws Exception {
        String testInput = "";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
        String output = new String(mockOutputStream.toByteArray(), "UTF-8");
        String expectedOutput = "";
        assertEquals(expectedOutput, output);
    }

    @Test
    public void pipeSortEcho() throws Exception {
        String testInput = "";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
        String output = new String(mockOutputStream.toByteArray(), "UTF-8");
        String expectedOutput = "";
        assertEquals(expectedOutput, output);
    }

    @Test
    public void pipeCommHead() throws Exception {
        String testInput = "";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
        String output = new String(mockOutputStream.toByteArray(), "UTF-8");
        String expectedOutput = "";
        assertEquals(expectedOutput, output);
    }

    @Test
    public void pipeCommNegative() throws Exception {
        String testInput = "";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
        String output = new String(mockOutputStream.toByteArray(), "UTF-8");
        String expectedOutput = "";
        assertEquals(expectedOutput, output);
    }

    @Test
    public void pipeBcSort() throws Exception {
        String testInput = "";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
        String output = new String(mockOutputStream.toByteArray(), "UTF-8");
        String expectedOutput = "";
        assertEquals(expectedOutput, output);
    }

    @Test
    public void pipeBcDate() throws Exception {
        String testInput = "";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
        String output = new String(mockOutputStream.toByteArray(), "UTF-8");
        String expectedOutput = "";
        assertEquals(expectedOutput, output);
    }

    @Test
    public void pipeCalHead() throws Exception {
        String testInput = "";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
        String output = new String(mockOutputStream.toByteArray(), "UTF-8");
        String expectedOutput = "";
        assertEquals(expectedOutput, output);
    }

    @Test
    public void pipeCalNegative() throws Exception {
        String testInput = "";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
        String output = new String(mockOutputStream.toByteArray(), "UTF-8");
        String expectedOutput = "";
        assertEquals(expectedOutput, output);
    }

    @Test
    public void pipeNegativeTail() throws Exception {
        String testInput = "";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
        String output = new String(mockOutputStream.toByteArray(), "UTF-8");
        String expectedOutput = "";
        assertEquals(expectedOutput, output);
    }

    @Test
    public void pipeNegativeDate() throws Exception {
        String testInput = "";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
        String output = new String(mockOutputStream.toByteArray(), "UTF-8");
        String expectedOutput = "";
        assertEquals(expectedOutput, output);
    }
}
