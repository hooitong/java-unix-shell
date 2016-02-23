package sg.edu.nus.comp.cs4218.impl.app;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

public class CalApplicationTest {
    private final String MONTHS[] = {"January", "February", "March", "April",
            "May", "June", "July", "August", "September",
            "October", "November", "December"};
    private final String START_SUN = "Su Mo Tu We Th Fr Sa";
    private final String START_MON = "Mo Tu We Th Fr Sa Su";
    private final String ERROR_INVALID_YEAR = "cal: not a valid year %s";
    private final String ERROR_INVALID_MONTH = "cal: %s is neither a month number (1..12) nor a name";

    private CalApplication testCal;
    private int currentMonth, currentYear;

    @Before
    public void setUp() {
        testCal = new CalApplication();
        currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        currentYear = Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * Tests whether the calendar app can print the calendar of the current month correctly.
     *
     * @throws Exception
     */
    @Test
    public void testPrintCal() throws Exception {
        String[] arguments = new String[0];
        String[] lineOutputs = testCal.printCal(arguments).split(System.getProperty("line.separator"));
        int numberOfDays = new GregorianCalendar(currentYear, currentMonth, 1).getActualMaximum(Calendar.DAY_OF_MONTH);
        assert(lineOutputs[0].trim().equals(MONTHS[currentMonth] + " " + currentYear));
        assert(lineOutputs[1].trim().equals(START_SUN));
        assert(lineOutputs[2].trim().startsWith("1"));
        assert(lineOutputs[lineOutputs.length - 1].trim().endsWith(Integer.toString(numberOfDays)));
    }

    /**
     * Tests whether the calendar app can print the calendar of the current month
     * with monday as the first day of the week.
     *
     * @throws Exception
     */
    @Test
    public void testPrintCalWithMondayFirst() throws Exception {
        String[] arguments = new String[0];
        String[] lineOutputs = testCal.printCal(arguments).split(System.getProperty("line.separator"));
        int numberOfDays = new GregorianCalendar(currentYear, currentMonth, 1).getActualMaximum(Calendar.DAY_OF_MONTH);
        assert(lineOutputs[0].trim().equals(MONTHS[currentMonth] + " " + currentYear));
        assert(lineOutputs[1].trim().equals(START_MON));
        assert(lineOutputs[2].trim().startsWith("1"));
        assert(lineOutputs[lineOutputs.length - 1].trim().endsWith(Integer.toString(numberOfDays)));
    }

    /**
     * Tests whether the calendar app can print the calendar of the given
     * month and year.
     *
     * @throws Exception
     */
    @Test
    public void testPrintCalForMonthYear() throws Exception {
        String mockMonth = "June"; String mockYear = "1998";
        String[] arguments = {mockMonth, mockYear};
        String[] lineOutputs = testCal.printCal(arguments).split(System.getProperty("line.separator"));
        assert(lineOutputs[0].trim().equals(MONTHS[currentMonth] + " " + currentYear));
        assert(lineOutputs[1].trim().equals(START_SUN));
        assert(lineOutputs[2].trim().startsWith("1"));
        assert(lineOutputs[lineOutputs.length - 1].trim().endsWith("30"));
    }

    /**
     * Tests whether the calendar app can print the correct 3x4 grid of
     * the year with all the included months required.
     *
     * @throws Exception
     */
    @Test
    public void testPrintCalForYearGrid() throws Exception {
        String mockYear = "2007";
        String[] arguments = {mockYear};
        String rawOutput = testCal.printCal(arguments);
        assert(rawOutput.contains(mockYear));
        for(String month : MONTHS) {
            assert(rawOutput.contains(month));
        }
        assert(rawOutput.contains(START_SUN));
        assert(!rawOutput.contains(START_MON));
    }

    /**
     * Tests whether the calendar app can print the calendar of the given
     * month and year with monday as the first day of the week.
     *
     * @throws Exception
     */
    @Test
    public void testPrintCalForMonthYearMondayFirst() throws Exception {
        String mockMonth = "March"; String mockYear = "2016";
        String[] arguments = {mockMonth, mockYear};
        String[] lineOutputs = testCal.printCal(arguments).split(System.getProperty("line.separator"));
        assert(lineOutputs[0].trim().equals(MONTHS[currentMonth] + " " + currentYear));
        assert(lineOutputs[1].trim().equals(START_MON));
        assert(lineOutputs[2].trim().startsWith("1"));
        assert(lineOutputs[lineOutputs.length - 1].trim().endsWith("31"));
    }

    /**
     * Tests whether the calendar app can print the calendar of the given
     * year with monday as the first day of the week.
     *
     * @throws Exception
     */
    @Test
    public void testPrintCalForYearMondayFirst() throws Exception {
        String mockYear = "2011";
        String[] arguments = {mockYear};
        String rawOutput = testCal.printCal(arguments);
        assert(rawOutput.contains(mockYear));
        for(String month : MONTHS) {
            assert(rawOutput.contains(month));
        }
        assert(rawOutput.contains(START_MON));
        assert(!rawOutput.contains(START_SUN));
    }

    /**
     * Tests whether the calendar app can respond with an error message
     * when given an invalid year.
     *
     * @throws Exception
     */
    @Test
    public void testInvalidRunWithMonthOnly() throws Exception {
        String mockMonth = "March";
        String[] arguments = {mockMonth};
        String rawOutput = testCal.printCalForMonthYear(arguments);
        assert(String.format(ERROR_INVALID_YEAR, mockMonth).equals(rawOutput));
    }

    /**
     * Tests whether the calendar app can respond with an error message
     * when given an invalid month but valid year.
     *
     * @throws Exception
     */
    @Test
    public void testInvalidRunWithMonth() throws Exception {
        String mockMonth = "Test";
        String mockYear = "2011";
        String[] arguments = {mockMonth, mockYear};
        String rawOutput = testCal.printCalForMonthYear(arguments);
        assert(String.format(ERROR_INVALID_MONTH, mockMonth).equals(rawOutput));
    }
}