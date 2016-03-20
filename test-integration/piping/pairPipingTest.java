package piping;

import org.junit.Before;
import org.junit.Test;
import sg.edu.nus.comp.cs4218.exception.PipeCommandException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        String testInput = "date | comm " + RESOURCE_ROOT + "date-comm.txt";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
        String output = new String(mockOutputStream.toByteArray(), "UTF-8");
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        String expectedOutput = sdf.format(new Date()) + "\t\t" + System.lineSeparator()
                + "\tcake5\t" + System.lineSeparator()
                + "\ttest2\t";
        assertEquals(expectedOutput, output);
    }

    /**
     * Tests the piping evaluation from Fmt (restricted to 10 char using w) to Tail (last 2 lines using -n).
     *
     * @throws Exception
     */
    @Test
    public void pipeFmtTail() throws Exception {
        String testInput = "fmt -w 10 " + RESOURCE_ROOT + "fmt-tail.txt | tail -n 2";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
        String output = new String(mockOutputStream.toByteArray(), "UTF-8");
        String expectedOutput = "";
        assertEquals(expectedOutput, output);
    }

    /**
     * Tests the piping evaluation from Fmt to Bc.
     *
     * @throws Exception
     */
    @Test
    public void pipeFmtBc() throws Exception {
        String testInput = "fmt " + RESOURCE_ROOT + "fmt-bc.txt | bc";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
        String output = new String(mockOutputStream.toByteArray(), "UTF-8");
        String expectedOutput = "500" + System.lineSeparator();
        assertEquals(expectedOutput, output);
    }

    /**
     * Tests the pipe evaluation from Sort (numbered with -n) to Comm.
     *
     * @throws Exception
     */
    @Test
    public void pipeSortComm() throws Exception {
        String testInput = "sort " + RESOURCE_ROOT + "sort-comm-1.txt | comm " + RESOURCE_ROOT + "sort-comm-2.txt";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
        String output = new String(mockOutputStream.toByteArray(), "UTF-8");
        String expectedOutput = "\t\t000\t0x0000\tNone\tNone\tBag Unknown pocket icon.png\tUnknown pocket" + System.lineSeparator()
                + "\t\t001\t0x0001\tMaster Ball\tMaster Ball\tBag Items pocket icon.png\tItems pocket" + System.lineSeparator()
                + "\t\t002\t0x0002\tUltra Ball\tUltra Ball\tBag Items pocket icon.png\tItems pocket" + System.lineSeparator()
                + "\t\t003\t0x0003\tGreat Ball\tGreat Ball\tBag Items pocket icon.png\tItems pocket" + System.lineSeparator()
                + "\t\t004\t0x0004\tPoké Ball\tPoké Ball\tBag Items pocket icon.png\tItems pocket" + System.lineSeparator()
                + "\t\t005\t0x0005\tSafari Ball\tSafari Ball\tBag Items pocket icon.png\tItems pocket" + System.lineSeparator()
                + "\t\t006\t0x0006\tNet Ball\tNet Ball\tBag Items pocket icon.png\tItems pocket";
        assertEquals(expectedOutput, output);
    }

    /**
     * Tests the pipe evaluation from Sort to Echo.
     *
     * @throws Exception
     */
    @Test
    public void pipeSortEcho() throws Exception {
        String testInput = "sort " + RESOURCE_ROOT + "sort-echo.txt | echo a";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
        String output = new String(mockOutputStream.toByteArray(), "UTF-8");
        String expectedOutput = "a" + System.lineSeparator();
        assertEquals(expectedOutput, output);
    }

    /**
     * Tests the pipe evaluation from Comm to Head (4 lines with -n).
     *
     * @throws Exception
     */
    @Test
    public void pipeCommHead() throws Exception {
        String testInput = "comm " + RESOURCE_ROOT + "comm-head-1.txt " + RESOURCE_ROOT + "comm-head-2.txt | head -n 4";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
        String output = new String(mockOutputStream.toByteArray(), "UTF-8");
        String expectedOutput = "\t\tapple" + System.lineSeparator()
                + "\t\tbanana" + System.lineSeparator()
                + "\tbanana\t" + System.lineSeparator()
                + "eggplant\t\t" + System.lineSeparator();
        assertEquals(expectedOutput, output);
    }

    /**
     * Invalid call command on the other side of the pipe.
     *
     * @throws Exception
     */
    @Test (expected = PipeCommandException.class)
    public void pipeCommNegative() throws Exception {
        String testInput = "comm " + RESOURCE_ROOT + "comm-negative-1.txt " + RESOURCE_ROOT + "comm-negative-2.txt | ILL EAGLE";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
    }

    /**
     * Tests the pipe evaluation from Bc (complex expression) to Sort.
     *
     * @throws Exception
     */
    @Test
    public void pipeBcSort() throws Exception {
        String testInput = "bc \"(1 + 3) * 5 + 5 ^ 2 >= 55\" | sort";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
        String output = new String(mockOutputStream.toByteArray(), "UTF-8");
        String expectedOutput = "0" + System.lineSeparator();
        assertEquals(expectedOutput, output);
    }

    /**
     * Tests the pipe evaluation from Bc (simple expression) to Date.
     *
     * @throws Exception
     */
    @Test
    public void pipeBcDate() throws Exception {
        String testInput = "bc '9 + 20 * 5' | date";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
        String output = new String(mockOutputStream.toByteArray(), "UTF-8");
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        assertEquals(sdf.format(new Date()), output);
    }

    /**
     * Tests the pipe evaluation from Cal (year) to Head.
     *
     * @throws Exception
     */
    @Test
    public void pipeCalHead() throws Exception {
        String testInput = "cal 2015 | head -n 8";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
        String output = new String(mockOutputStream.toByteArray(), "UTF-8");
        String expectedOutput =
                  "                              2015                              " + System.lineSeparator()
                + "      January               February               March        " + System.lineSeparator()
                + "Su Mo Tu We Th Fr Sa  Su Mo Tu We Th Fr Sa  Su Mo Tu We Th Fr Sa" + System.lineSeparator()
                + "             1  2  3   1  2  3  4  5  6  7   1  2  3  4  5  6  7" + System.lineSeparator()
                + " 4  5  6  7  8  9 10   8  9 10 11 12 13 14   8  9 10 11 12 13 14" + System.lineSeparator()
                + "11 12 13 14 15 16 17  15 16 17 18 19 20 21  15 16 17 18 19 20 21" + System.lineSeparator()
                + "18 19 20 21 22 23 24  22 23 24 25 26 27 28  22 23 24 25 26 27 28" + System.lineSeparator()
                + "25 26 27 28 29 30 31                        29 30 31            " + System.lineSeparator();
        assertEquals(expectedOutput, output);
    }

    /**
     * Pipe output of Cal to Comm with no arguments (error - no second file)
     *
     * @throws Exception
     */
    @Test (expected = PipeCommandException.class)
    public void pipeCalNegative() throws Exception {
        String testInput = "cal december 2014 | comm";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
    }

    /**
     * Invalid call command on the left side of the pipe.
     *
     * @throws Exception
     */
    @Test (expected = PipeCommandException.class)
    public void pipeNegativeTail() throws Exception {
        String testInput = "hat 25 | date";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
    }

    /**
     * Cat throws exception with lack of file before piping into date.
     *
     * @throws Exception
     */
    @Test (expected = PipeCommandException.class)
    public void pipeNegativeDate() throws Exception {
        String testInput = "cal -n | date";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
    }

    /**
     * Invalid piping syntax without space as last argument.
     *
     * @throws Exception
     */
    @Test (expected = PipeCommandException.class)
    public void pipeEchoNegative() throws Exception {
        String testInput = "echo abc |";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
    }

    /**
     * Invalid piping syntax with spaces as last argument.
     *
     * @throws Exception
     */
    @Test (expected = PipeCommandException.class)
    public void pipeDateNegative() throws Exception {
        String testInput = "date|    ";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
    }

    /**
     * Invalid piping syntax with spaces as first argument.
     *
     * @throws Exception
     */
    @Test (expected = PipeCommandException.class)
    public void pipeNegativeBc() throws Exception {
        String testInput = "     |  bc '5+3'   ";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
    }

    /**
     * Invalid piping syntax without space as first argument.
     *
     * @throws Exception
     */
    @Test (expected = PipeCommandException.class)
    public void pipeNegativeCal() throws Exception {
        String testInput = "|cal";
        mockShell.parseAndEvaluate(testInput, mockOutputStream);
    }
}
