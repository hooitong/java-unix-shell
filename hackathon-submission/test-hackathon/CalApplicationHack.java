import static org.junit.Assert.assertEquals;

import org.junit.Test;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

import java.io.ByteArrayOutputStream;

public class CalApplicationHack {
    ShellImpl mockShell;
    ByteArrayOutputStream mockOutput;

    public CalApplicationHack() {
        mockShell = new ShellImpl();
        mockOutput = new ByteArrayOutputStream();
    }

    /**
     * When input cal 2015, returns an integer 6. Further inspection reveals an ArrayIndexOutOfBoundsException : 6
     * was thrown.
     *
     * This bug is due to not "print the calendar for each month in the specified year in a grid 3 wide
     * and 4 down"  Ref. spec Page 12, Section cal
     *
     * 	Failing code at sg.edu.nus.comp.cs4218.impl.app.CalApplication.printFormatYear(CalApplication.java:372)
     */
    @Test
    public void testCalYearSpecific() throws Exception {
        String cmdline = "cal 2015";
        mockShell.parseAndEvaluate(cmdline, mockOutput);
        // Output copy pasted from UNIX. Feel free to edit to your own calendar format.
        String expectedOutput = "                            2015" + System.lineSeparator()
                + "      January               February               March" + System.lineSeparator()
                + "Su Mo Tu We Th Fr Sa  Su Mo Tu We Th Fr Sa  Su Mo Tu We Th Fr Sa" + System.lineSeparator()
                + "             1  2  3   1  2  3  4  5  6  7   1  2  3  4  5  6  7" + System.lineSeparator()
                + " 4  5  6  7  8  9 10   8  9 10 11 12 13 14   8  9 10 11 12 13 14" + System.lineSeparator()
                + "11 12 13 14 15 16 17  15 16 17 18 19 20 21  15 16 17 18 19 20 21" + System.lineSeparator()
                + "18 19 20 21 22 23 24  22 23 24 25 26 27 28  22 23 24 25 26 27 28" + System.lineSeparator()
                + "25 26 27 28 29 30 31                        29 30 31" + System.lineSeparator()
                + System.lineSeparator()
                + System.lineSeparator()
                + "       April                  May                   June" + System.lineSeparator()
                + "Su Mo Tu We Th Fr Sa  Su Mo Tu We Th Fr Sa  Su Mo Tu We Th Fr Sa" + System.lineSeparator()
                + "          1  2  3  4                  1  2      1  2  3  4  5  6" + System.lineSeparator()
                + " 5  6  7  8  9 10 11   3  4  5  6  7  8  9   7  8  9 10 11 12 13" + System.lineSeparator()
                + "12 13 14 15 16 17 18  10 11 12 13 14 15 16  14 15 16 17 18 19 20" + System.lineSeparator()
                + "19 20 21 22 23 24 25  17 18 19 20 21 22 23  21 22 23 24 25 26 27" + System.lineSeparator()
                + "26 27 28 29 30        24 25 26 27 28 29 30  28 29 30" + System.lineSeparator()
                + "                      31" + System.lineSeparator()
                + System.lineSeparator()
                + "        July                 August              September" + System.lineSeparator()
                + "Su Mo Tu We Th Fr Sa  Su Mo Tu We Th Fr Sa  Su Mo Tu We Th Fr Sa" + System.lineSeparator()
                + "          1  2  3  4                     1         1  2  3  4  5" + System.lineSeparator()
                + " 5  6  7  8  9 10 11   2  3  4  5  6  7  8   6  7  8  9 10 11 12" + System.lineSeparator()
                + "12 13 14 15 16 17 18   9 10 11 12 13 14 15  13 14 15 16 17 18 19" + System.lineSeparator()
                + "19 20 21 22 23 24 25  16 17 18 19 20 21 22  20 21 22 23 24 25 26" + System.lineSeparator()
                + "26 27 28 29 30 31     23 24 25 26 27 28 29  27 28 29 30" + System.lineSeparator()
                + "                      30 31" + System.lineSeparator()
                + System.lineSeparator()
                + "      October               November              December" + System.lineSeparator()
                + "Su Mo Tu We Th Fr Sa  Su Mo Tu We Th Fr Sa  Su Mo Tu We Th Fr Sa" + System.lineSeparator()
                + "             1  2  3   1  2  3  4  5  6  7         1  2  3  4  5" + System.lineSeparator()
                + " 4  5  6  7  8  9 10   8  9 10 11 12 13 14   6  7  8  9 10 11 12" + System.lineSeparator()
                + "11 12 13 14 15 16 17  15 16 17 18 19 20 21  13 14 15 16 17 18 19" + System.lineSeparator()
                + "18 19 20 21 22 23 24  22 23 24 25 26 27 28  20 21 22 23 24 25 26" + System.lineSeparator()
                + "25 26 27 28 29 30 31  29 30                 27 28 29 30 31";
        assertEquals(expectedOutput, new String(mockOutput.toByteArray()));
    }
}
