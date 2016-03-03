package sg.edu.nus.comp.cs4218.impl.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import sg.edu.nus.comp.cs4218.app.Cal;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.CalException;

public class CalApplication implements Cal {
	private static final String ERROR_NULL = "Arg given is null.";
	private static final String ERROR_INVALID = "Invalid arguments provided.";
	private static final String ERROR_MONTH = "Not a valid month provided.";
	private static final String ERROR_YEAR = "Not a valid year provided.";
	private static final String ERROR_FLAG = "Invalid flag provided.";

	private static final int INVALID_DATE = -1;
	private static final int LAST_MONTH = 11;
	private static final String WEEK_MON = "Mo Tu We Th Fr Sa Su";
	private static final String WEEK_SUN = "Su Mo Tu We Th Fr Sa Su";
	private static final String[] MONTHS_LONG = {"January", "February", "March", "April", "May", "June", "July",
			"August", "September", "October", "November", "December"};
	private static final String[] MONTHS_SHORT = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep",
	"Oct", "Nov", "Dec"};

	@Override
	public String printCal(String[] args) {
		return null;
	}

	@Override
	public String printCalWithMondayFirst(String[] args) {
		return null;
	}

	@Override
	public String printCalForMonthYear(String[] args) {
		return null;
	}

	@Override
	public String printCalForYear(String[] args) {
		return null;
	}

	@Override
	public String printCalForMonthYearMondayFirst(String[] args) {
		return null;
	}

	@Override
	public String printCalForYearMondayFirst(String[] args) {
		return null;
	}

	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws AbstractApplicationException {
		if (args == null) {
			throw new CalException(ERROR_NULL);
		}

		String output = processArgument(args);
		writeToOutput(output, stdout);
	}

	private String processArgument(String[] args) throws CalException{
		if (args.length == 0) { /* Print current month */
			printCal(null);
		} else if (args.length == 1) { /* either -m, year or invalid */
			if("-m".equals(args[0].trim())) printCalWithMondayFirst(null);
			else if(parseYear(args[0].trim()) != INVALID_DATE) printCalForYear(args);
			else throw new CalException(ERROR_INVALID);
		} else if (args.length == 2) { /* either -m year, month year or invalid */
			if("-m".equals(args[0].trim()) && parseYear(args[1].trim()) != INVALID_DATE) {
				printCalForYearMondayFirst(args);
			} else if (parseMonth(args[0].trim()) != INVALID_DATE && parseYear(args[0].trim()) != INVALID_DATE) {
				printCalForMonthYear(args);
			} else throw new CalException(ERROR_INVALID);
		} else if (args.length == 3) { /* -m month year */
			if("-m".equals(args[0].trim()) && parseMonth(args[1].trim()) != INVALID_DATE &&
					parseYear(args[2].trim()) != INVALID_DATE) {
				printCalForMonthYearMondayFirst(args);
			} else {
				throw new CalException(ERROR_INVALID);
			}
		} else { /* Invalid number of args - not supported */
			throw new CalException(ERROR_INVALID);
		}
	}

	private void writeToOutput(String output, OutputStream stdout) throws CalException {
		try {
			stdout.write(output.getBytes());
		} catch (IOException e) {
			throw new CalException(e);
		}
	}

	/**
	 * Parses the given string into the actual year. Supports years of 1..9999.
	 * @param year ranging from 1..9999
	 * @return integer value of the year or error value if cannot be parsed
     */
	private int parseYear(String year) {
		try{
			int parsedYear = Integer.parseInt(year);
			if(0 < parsedYear && parsedYear < 10000) return parsedYear;
			else return INVALID_DATE;
		} catch (NumberFormatException e) {
			return INVALID_DATE;
		}
	}

	/**
	 * Parses the given string in the form of either 1..12, Jan..Dec, January..December
	 * into the integer form.
	 * @param month 1..12 | Jan..Dec | January..December
	 * @return integer value of the month 0..11 or error value if cannot be parsed
     */
	private int parseMonth(String month) {
		/* Match against both long and short form of the months */
		for(int i = 0; i <= LAST_MONTH; i++) {
			if(MONTHS_LONG[0].equalsIgnoreCase(month) || MONTHS_SHORT[0].equalsIgnoreCase(month)) {
				return i;
			}
		}

		/* If short and long month cannot be parsed */
		try {
			int parsedMonth = Integer.parseInt(month);
			if(0 < parsedMonth && parsedMonth < 12) return parsedMonth - 1;
			else return INVALID_DATE;
		} catch (NumberFormatException e) {
			return INVALID_DATE;
		}
	}

	/**
	 * Returns the number of days in the given month.
	 *
	 * @param month
	 * @param year to check for leap year
	 * @return the number of days in the month
     */
	private int getDaysOfMonth(int month, int year) {
		if(isLeapYear(year) && month == 2) return 29;
		return (int)(28 + (month + Math.floor(month/8)) % 2 + 2 % month + 2 * Math.floor(1/month));
	}

	/**
	 * Returns a boolean whether the year given is a leap year.
	 * @param year
	 * @return true if it is a leap year
     */
	private boolean isLeapYear(int year) {
		return (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0));
	}

	/**
	 * Return the day of the week the date falls on according to Gregorian calendar.
	 *
	 * @param day day of the date
	 * @param month month of the date
	 * @param year year of the date
     * @return integer value where 0 represents sunday, 1 represents monday, etc
     */
	private int getDayOfDate(int day, int month, int year){
		int yearBlock = year - (14 - month) / 12;
		int centuryBlock = year + year / 4 - year / 100 + year / 400;
		int monthBlock = month + 12 * ((14 - month) / 12) - 2;
		return (day + centuryBlock + (31 * month) / 12) % 7;
	}
}
