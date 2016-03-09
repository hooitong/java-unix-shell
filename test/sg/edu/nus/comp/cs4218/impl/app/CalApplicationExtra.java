package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.CalException;

public class CalApplicationExtra {
	private CalApplication calApp;
	private OutputStream outStream;

	@Before
	public void setUp() throws Exception {
		calApp = new CalApplication();
		outStream = new ByteArrayOutputStream();
	}

	@After
	public void tearDown() throws Exception {
		outStream.close();
	}
	@Test
	public void testRun() throws CalException {
		String[] args = {"1", "2010"};
		try {
			calApp.run(args, null, outStream);
			String expected = "January 2010\n"
					+ "Su Mo Tu We Th Fr Sa\n"
					+ "                1  2\n"
					+ " 3  4  5  6  7  8  9\n"
					+ "10 11 12 13 14 15 16\n"
					+ "17 18 19 20 21 22 23\n"
					+ "24 25 26 27 28 29 30\n"
					+ "31\n";
			assertEquals(expected, outStream.toString());
		} catch (CalException e) {
			throw e;
		}
	}

	@Test
	public void testRunInvalidArguments1() {
		try {
			String[] args = {"abc"};
			calApp.run(args, null, outStream);

			fail("CalApplication should fail");
		} catch (CalException e) {
			assertEquals("cal: Invalid arguments", e.getMessage());
		} catch (Exception e) {
			fail("CalApplication should throw CalException");
		}
	}

	@Test
	public void testRunInvalidArguments2() {
		try {
			String[] args = {"-1"};
			calApp.run(args, null, outStream);

			fail("CalApplication should fail");
		} catch (CalException e) {
			assertEquals("cal: Year should not be <= 0", e.getMessage());
		} catch (Exception e) {
			fail("CalApplication should throw CalException");
		}
	}

	@Test
	public void testRunInvalidArguments3() {
		try {
			String[] args = {"-1", "2010"};
			calApp.run(args, null, outStream);

			fail("CalApplication should fail");
		} catch (CalException e) {
			assertEquals("cal: Month should be between 1 - 12", e.getMessage());
		} catch (Exception e) {
			fail("CalApplication should throw CalException");
		}
	}

	@Test
	public void testRunInvalidArguments4() {
		try {
			String[] args = {"13", "2010"};
			calApp.run(args, null, outStream);

			fail("CalApplication should fail");
		} catch (CalException e) {
			assertEquals("cal: Month should be between 1 - 12", e.getMessage());
		} catch (Exception e) {
			fail("CalApplication should throw CalException");
		}
	}

	@Test
	public void testRunInvalidArguments5() {
		try {
			String[] args = {"12", "2010", "xyz"};
			calApp.run(args, null, outStream);

			fail("CalApplication should fail");
		} catch (CalException e) {
			assertEquals("cal: Invalid arguments", e.getMessage());
		} catch (Exception e) {
			fail("CalApplication should throw CalException");
		}
	}

	@Test
	public void testPrintCal() {
		String[] args = {};
		String result = calApp.printCal(args);
		GregorianCalendar gregCal = new GregorianCalendar();
		String expected = formatCalendarMonthly(getCalendarMonthlyTable(gregCal.get(Calendar.MONTH), gregCal.get(Calendar.YEAR), false));

		assertEquals(expected, result);
	}

	@Test
	public void testPrintCalWithMondayFirst() {
		String[] args = {};
		String result = calApp.printCalWithMondayFirst(args);
		GregorianCalendar gregCal = new GregorianCalendar();
		String expected = formatCalendarMonthly(getCalendarMonthlyTable(gregCal.get(Calendar.MONTH), gregCal.get(Calendar.YEAR), true));

		assertEquals(expected, result);
	}

	@Test
	public void testPrintCalForMonthYear1() {
		String[] args = {"1", "2010"};
		String result = calApp.printCalForMonthYear(args);
		String expected = "January 2010\n"
				+ "Su Mo Tu We Th Fr Sa\n"
				+ "                1  2\n"
				+ " 3  4  5  6  7  8  9\n"
				+ "10 11 12 13 14 15 16\n"
				+ "17 18 19 20 21 22 23\n"
				+ "24 25 26 27 28 29 30\n"
				+ "31\n";

		assertEquals(expected, result);
	}

	@Test
	public void testPrintCalForMonthYear2() {
		String[] args = {"7", "2010"};
		String result = calApp.printCalForMonthYear(args);
		String expected = "July 2010\n"
				+ "Su Mo Tu We Th Fr Sa\n"
				+ "             1  2  3\n"
				+ " 4  5  6  7  8  9 10\n"
				+ "11 12 13 14 15 16 17\n"
				+ "18 19 20 21 22 23 24\n"
				+ "25 26 27 28 29 30 31\n";

		assertEquals(expected, result);
	}

	@Test
	public void testPrintCalForMonthYear3() {
		String[] args = {"12", "2010"};
		String result = calApp.printCalForMonthYear(args);
		String expected = "December 2010\n"
				+ "Su Mo Tu We Th Fr Sa\n"
				+ "          1  2  3  4\n"
				+ " 5  6  7  8  9 10 11\n"
				+ "12 13 14 15 16 17 18\n"
				+ "19 20 21 22 23 24 25\n"
				+ "26 27 28 29 30 31\n";

		assertEquals(expected, result);
	}

	@Test
	public void testPrintCalForMonthYear4() {
		String[] args = {"1", "2020"};
		String result = calApp.printCalForMonthYear(args);
		String expected = "January 2020\n"
				+ "Su Mo Tu We Th Fr Sa\n"
				+ "          1  2  3  4\n"
				+ " 5  6  7  8  9 10 11\n"
				+ "12 13 14 15 16 17 18\n"
				+ "19 20 21 22 23 24 25\n"
				+ "26 27 28 29 30 31\n";

		assertEquals(expected, result);
	}

	@Test
	public void testPrintCalForMonthYear5() {
		String[] args = {"7", "2020"};
		String result = calApp.printCalForMonthYear(args);
		String expected = "July 2020\n"
				+ "Su Mo Tu We Th Fr Sa\n"
				+ "          1  2  3  4\n"
				+ " 5  6  7  8  9 10 11\n"
				+ "12 13 14 15 16 17 18\n"
				+ "19 20 21 22 23 24 25\n"
				+ "26 27 28 29 30 31\n";

		assertEquals(expected, result);
	}

	@Test
	public void testPrintCalForMonthYear6() {
		String[] args = {"12", "2020"};
		String result = calApp.printCalForMonthYear(args);
		String expected = "December 2020\n"
				+ "Su Mo Tu We Th Fr Sa\n"
				+ "       1  2  3  4  5\n"
				+ " 6  7  8  9 10 11 12\n"
				+ "13 14 15 16 17 18 19\n"
				+ "20 21 22 23 24 25 26\n"
				+ "27 28 29 30 31\n";

		assertEquals(expected, result);
	}

	@Test
	public void testPrintCalForMonthYearMondayFirst1() {
		String[] args = {"1", "2010"};
		String result = calApp.printCalForMonthYearMondayFirst(args);
		String expected = "January 2010\n"
				+ "Mo Tu We Th Fr Sa Su\n"
				+ "             1  2  3\n"
				+ " 4  5  6  7  8  9 10\n"
				+ "11 12 13 14 15 16 17\n"
				+ "18 19 20 21 22 23 24\n"
				+ "25 26 27 28 29 30 31\n";

		assertEquals(expected, result);
	}

	@Test
	public void testPrintCalForMonthYearMondayFirst2() {
		String[] args = {"7", "2010"};
		String result = calApp.printCalForMonthYearMondayFirst(args);
		String expected = "July 2010\n"
				+ "Mo Tu We Th Fr Sa Su\n"
				+ "          1  2  3  4\n"
				+ " 5  6  7  8  9 10 11\n"
				+ "12 13 14 15 16 17 18\n"
				+ "19 20 21 22 23 24 25\n"
				+ "26 27 28 29 30 31\n";

		assertEquals(expected, result);
	}

	@Test
	public void testPrintCalForMonthYearMondayFirst3() {
		String[] args = {"12", "2010"};
		String result = calApp.printCalForMonthYearMondayFirst(args);
		String expected = "December 2010\n"
				+ "Mo Tu We Th Fr Sa Su\n"
				+ "       1  2  3  4  5\n"
				+ " 6  7  8  9 10 11 12\n"
				+ "13 14 15 16 17 18 19\n"
				+ "20 21 22 23 24 25 26\n"
				+ "27 28 29 30 31\n";

		assertEquals(expected, result);
	}

	@Test
	public void testPrintCalForMonthYearMondayFirst4() {
		String[] args = {"1", "2020"};
		String result = calApp.printCalForMonthYearMondayFirst(args);
		String expected = "January 2020\n"
				+ "Mo Tu We Th Fr Sa Su\n"
				+ "       1  2  3  4  5\n"
				+ " 6  7  8  9 10 11 12\n"
				+ "13 14 15 16 17 18 19\n"
				+ "20 21 22 23 24 25 26\n"
				+ "27 28 29 30 31\n";

		assertEquals(expected, result);
	}

	@Test
	public void testPrintCalForMonthYearMondayFirst5() {
		String[] args = {"7", "2020"};
		String result = calApp.printCalForMonthYearMondayFirst(args);
		String expected = "July 2020\n"
				+ "Mo Tu We Th Fr Sa Su\n"
				+ "       1  2  3  4  5\n"
				+ " 6  7  8  9 10 11 12\n"
				+ "13 14 15 16 17 18 19\n"
				+ "20 21 22 23 24 25 26\n"
				+ "27 28 29 30 31\n";

		assertEquals(expected, result);
	}

	@Test
	public void testPrintCalForMonthYearMondayFirst6() {
		String[] args = {"12", "2020"};
		String result = calApp.printCalForMonthYearMondayFirst(args);
		String expected = "December 2020\n"
				+ "Mo Tu We Th Fr Sa Su\n"
				+ "    1  2  3  4  5  6\n"
				+ " 7  8  9 10 11 12 13\n"
				+ "14 15 16 17 18 19 20\n"
				+ "21 22 23 24 25 26 27\n"
				+ "28 29 30 31\n";

		assertEquals(expected, result);
	}

	@Test
	public void testPrintCalForYear() {
		String[] args = {"2010"};
		String result = calApp.printCalForYear(args);
		String expected = "January 2010           February 2010          March 2010          \n"
				+ "Su Mo Tu We Th Fr Sa   Su Mo Tu We Th Fr Sa   Su Mo Tu We Th Fr Sa\n"
				+ "                1  2       1  2  3  4  5  6       1  2  3  4  5  6\n"
				+ " 3  4  5  6  7  8  9    7  8  9 10 11 12 13    7  8  9 10 11 12 13\n"
				+ "10 11 12 13 14 15 16   14 15 16 17 18 19 20   14 15 16 17 18 19 20\n"
				+ "17 18 19 20 21 22 23   21 22 23 24 25 26 27   21 22 23 24 25 26 27\n"
				+ "24 25 26 27 28 29 30   28                     28 29 30 31         \n"
				+ "\n"
				+ "April 2010             May 2010               June 2010           \n"
				+ "Su Mo Tu We Th Fr Sa   Su Mo Tu We Th Fr Sa   Su Mo Tu We Th Fr Sa\n"
				+ "             1  2  3                      1          1  2  3  4  5\n"
				+ " 4  5  6  7  8  9 10    2  3  4  5  6  7  8    6  7  8  9 10 11 12\n"
				+ "11 12 13 14 15 16 17    9 10 11 12 13 14 15   13 14 15 16 17 18 19\n"
				+ "18 19 20 21 22 23 24   16 17 18 19 20 21 22   20 21 22 23 24 25 26\n"
				+ "25 26 27 28 29 30      23 24 25 26 27 28 29   27 28 29 30         \n"
				+ "\n"
				+ "July 2010              August 2010            September 2010      \n"
				+ "Su Mo Tu We Th Fr Sa   Su Mo Tu We Th Fr Sa   Su Mo Tu We Th Fr Sa\n"
				+ "             1  2  3    1  2  3  4  5  6  7             1  2  3  4\n"
				+ " 4  5  6  7  8  9 10    8  9 10 11 12 13 14    5  6  7  8  9 10 11\n"
				+ "11 12 13 14 15 16 17   15 16 17 18 19 20 21   12 13 14 15 16 17 18\n"
				+ "18 19 20 21 22 23 24   22 23 24 25 26 27 28   19 20 21 22 23 24 25\n"
				+ "25 26 27 28 29 30 31   29 30 31               26 27 28 29 30      \n"
				+ "\n"
				+ "October 2010           November 2010          December 2010       \n"
				+ "Su Mo Tu We Th Fr Sa   Su Mo Tu We Th Fr Sa   Su Mo Tu We Th Fr Sa\n"
				+ "                1  2       1  2  3  4  5  6             1  2  3  4\n"
				+ " 3  4  5  6  7  8  9    7  8  9 10 11 12 13    5  6  7  8  9 10 11\n"
				+ "10 11 12 13 14 15 16   14 15 16 17 18 19 20   12 13 14 15 16 17 18\n"
				+ "17 18 19 20 21 22 23   21 22 23 24 25 26 27   19 20 21 22 23 24 25\n"
				+ "24 25 26 27 28 29 30   28 29 30               26 27 28 29 30 31   \n";

		assertEquals(expected, result);
	}

	@Test
	public void testPrintCalForYearMondayFirst() {
		String[] args = {"2010"};
		String result = calApp.printCalForYearMondayFirst(args);
		String expected = "January 2010           February 2010          March 2010          \n"
				+ "Mo Tu We Th Fr Sa Su   Mo Tu We Th Fr Sa Su   Mo Tu We Th Fr Sa Su\n"
				+ "             1  2  3    1  2  3  4  5  6  7    1  2  3  4  5  6  7\n"
				+ " 4  5  6  7  8  9 10    8  9 10 11 12 13 14    8  9 10 11 12 13 14\n"
				+ "11 12 13 14 15 16 17   15 16 17 18 19 20 21   15 16 17 18 19 20 21\n"
				+ "18 19 20 21 22 23 24   22 23 24 25 26 27 28   22 23 24 25 26 27 28\n"
				+ "25 26 27 28 29 30 31                          29 30 31            \n"
				+ "\n"
				+ "April 2010             May 2010               June 2010           \n"
				+ "Mo Tu We Th Fr Sa Su   Mo Tu We Th Fr Sa Su   Mo Tu We Th Fr Sa Su\n"
				+ "          1  2  3  4                   1  2       1  2  3  4  5  6\n"
				+ " 5  6  7  8  9 10 11    3  4  5  6  7  8  9    7  8  9 10 11 12 13\n"
				+ "12 13 14 15 16 17 18   10 11 12 13 14 15 16   14 15 16 17 18 19 20\n"
				+ "19 20 21 22 23 24 25   17 18 19 20 21 22 23   21 22 23 24 25 26 27\n"
				+ "26 27 28 29 30         24 25 26 27 28 29 30   28 29 30            \n"
				+ "\n"
				+ "July 2010              August 2010            September 2010      \n"
				+ "Mo Tu We Th Fr Sa Su   Mo Tu We Th Fr Sa Su   Mo Tu We Th Fr Sa Su\n"
				+ "          1  2  3  4                      1          1  2  3  4  5\n"
				+ " 5  6  7  8  9 10 11    2  3  4  5  6  7  8    6  7  8  9 10 11 12\n"
				+ "12 13 14 15 16 17 18    9 10 11 12 13 14 15   13 14 15 16 17 18 19\n"
				+ "19 20 21 22 23 24 25   16 17 18 19 20 21 22   20 21 22 23 24 25 26\n"
				+ "26 27 28 29 30 31      23 24 25 26 27 28 29   27 28 29 30         \n"
				+ "\n"
				+ "October 2010           November 2010          December 2010       \n"
				+ "Mo Tu We Th Fr Sa Su   Mo Tu We Th Fr Sa Su   Mo Tu We Th Fr Sa Su\n"
				+ "             1  2  3    1  2  3  4  5  6  7          1  2  3  4  5\n"
				+ " 4  5  6  7  8  9 10    8  9 10 11 12 13 14    6  7  8  9 10 11 12\n"
				+ "11 12 13 14 15 16 17   15 16 17 18 19 20 21   13 14 15 16 17 18 19\n"
				+ "18 19 20 21 22 23 24   22 23 24 25 26 27 28   20 21 22 23 24 25 26\n"
				+ "25 26 27 28 29 30 31   29 30                  27 28 29 30 31      \n";
		assertEquals(expected, result);
	}

	private final static String[] DAYS = {
		"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"
	};

	private final static String[] MONTHS = {
		"January", "February", "March",
		"April", "May", "June",
		"July", "August", "September",
		"October", "November", "December"
	};

	private final static int DAYS_OF_MONTH[] = {
		31, 28, 31, 30, /* jan feb mar apr */
		31, 30, 31, 31, /* may jun jul aug */
		30, 31, 30, 31 /* sep oct nov dec */
	};

	private String formatCalendarMonthly(ArrayList<ArrayList<String>> calendarTable){
		String calendarString = "";
		for (int i = 0; i < calendarTable.size(); i++) {
			for (int j = 0; j < calendarTable.get(i).size(); j++) {
				String date = calendarTable.get(i).get(j);
				String dateFormatted = "";
				if (date.length() < 2){
					dateFormatted += " ";
				}
				dateFormatted += date;
				calendarString = calendarString.concat(dateFormatted);
				if (j < calendarTable.get(i).size()-1) {
					calendarString += " ";
				}
			}
			calendarString += "\n";
		}

		return calendarString;
	}

	private ArrayList<ArrayList<String>> getCalendarMonthlyTable(int currentMonth, int currentYear, boolean startsWithMonday) {
		ArrayList<String> calendarRow = new ArrayList<String>();
		ArrayList<ArrayList<String>> calendarTable = new ArrayList<ArrayList<String>>();

		calendarRow.add(String.valueOf(MONTHS[currentMonth]));
		calendarRow.add(String.valueOf(currentYear));
		calendarTable.add(calendarRow);

		calendarRow = new ArrayList<String>();
		if (!startsWithMonday){
			calendarRow.add(DAYS[0]);
		}
		for (int i = 1; i < DAYS.length; i++) {
			calendarRow.add(DAYS[i]);
		}
		if (startsWithMonday) {
			calendarRow.add(DAYS[0]);
		}
		calendarTable.add(calendarRow);

		GregorianCalendar calendar = new GregorianCalendar(currentYear, currentMonth, 1);
		int leadGap = startsWithMonday ? calendar.get(Calendar.DAY_OF_WEEK)-2 : calendar.get(Calendar.DAY_OF_WEEK)-1;
		int daysInMonth = DAYS_OF_MONTH[currentMonth];
		if (calendar.isLeapYear(currentYear) && currentMonth == 1)
			++daysInMonth;

		calendarRow = new ArrayList<String>();
		for (int i = 0; i < leadGap; i++) {
			calendarRow.add("  ");
		}

		for (int i = 1; i <= daysInMonth; i++) {
			calendarRow.add(String.valueOf(i));
			if ((leadGap + i) % 7 == 0){
				calendarTable.add(calendarRow);
				calendarRow = new ArrayList<String>();
			}
		}
		calendarTable.add(calendarRow);

		return calendarTable;
	}
}
