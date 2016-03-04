package sg.edu.nus.comp.cs4218.impl.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import sg.edu.nus.comp.cs4218.app.Cal;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.CalException;
import sg.edu.nus.comp.cs4218.exception.CatException;

/**
 * The cal command prints the calendar of the month and can be adjusted
 * via arguments to print different formats. This calendar is based
 * on the gregorian calendar and only supports year 1-9999. Months allowed
 * to be pass can be in the form of 1..12, Jan..Dec or January..December.
 *
 * <p>
 * <b>Command format:</b> <code>cat [-m] [[month] [year]]</code>
 * <dl>
 * <dt>FILE</dt>
 * <dd>the name of the file(s). If no files are specified, use stdin.</dd>
 * </dl>
 * </p>
 */
public class CalApplication implements Cal {
	private static final String ERROR_NULL = "Arg given is null.";
	private static final String ERROR_INVALID = "Invalid arguments provided.";
	private static final String ERROR_MONTH = "Not a valid month provided.";
	private static final String ERROR_YEAR = "Not a valid year provided.";
	private static final String ERROR_FLAG = "Invalid flag provided.";

	private static final int WIDTH_YEAR = 64;
	private static final int WIDTH_MONTH = 20;
	private static final String DATE_HEADER = "%s %s";
	private static final int INVALID_DATE = -1;
	private static final int LAST_MONTH = 11;
	private static final String WEEK_MON = "Mo Tu We Th Fr Sa Su";
	private static final String WEEK_SUN = "Su Mo Tu We Th Fr Sa";
	private static final String[] MONTHS_LONG = {"January", "February", "March", "April", "May", "June", "July",
			"August", "September", "October", "November", "December"};
	private static final String[] MONTHS_SHORT = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep",
	"Oct", "Nov", "Dec"};

	/**
	 * Returns the string to print the calendar of the current month
	 *
	 * @param args
	 * @return
     */
	@Override
	public String printCal(String[] args) {
		/* Get current year and month */
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH);
		int year = c.get(Calendar.YEAR);

		int firstDay = (getDayOfDate(1, month, year) + 1) % 7;
		int totalDays = getDaysOfMonth(month, year);

		/* Print header output */
		StringBuilder sb = new StringBuilder();
		sb.append(centerHeader(String.format(DATE_HEADER, MONTHS_LONG[month], year)));
		sb.append(System.lineSeparator());
		sb.append(WEEK_SUN);
		sb.append(System.lineSeparator());

		/* Print spacer to accommodate */
		for (int i = 0; i < firstDay; i++) {
			sb.append("   ");
		}

		/* Print the days by appending into bulider */
		for(int day = 1; day <= totalDays;  day++) {
			sb.append(String.format("%2d", day));
			if((firstDay + day) % 7 == 0 || (day == totalDays)) sb.append(System.lineSeparator());
			else sb.append(" ");
		}

		return sb.toString();
	}

	/**
	 * Returns the string to print the calendar of the current month with Monday
	 * as the first day of the week
	 *
	 * @param args
	 * @return
     */
	@Override
	public String printCalWithMondayFirst(String[] args) {
		/* Get current year and month */
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH);
		int year = c.get(Calendar.YEAR);

		int firstDay = (getDayOfDate(1, month, year) - 1) % 7;
		int totalDays = getDaysOfMonth(month, year);

		/* Print header output */
		StringBuilder sb = new StringBuilder();
		sb.append(centerHeader(String.format(DATE_HEADER, MONTHS_LONG[month], year)));
		sb.append(System.lineSeparator());
		sb.append(WEEK_MON);
		sb.append(System.lineSeparator());

		/* Print spacer to accommodate */
		for (int i = 0; i < firstDay; i++) {
			sb.append("   ");
		}

		/* Print the days by appending into bulider */
		for(int day = 1; day <= totalDays;  day++) {
			sb.append(String.format("%2d", day));

			if((firstDay + day) % 7 == 0 || (day == totalDays)) sb.append(System.lineSeparator());
			else sb.append(" ");
		}

		return sb.toString();
	}

	/**
	 * Returns the string to print the calendar for specified month and year
	 *
	 * @param args
	 * @return
     */
	@Override
	public String printCalForMonthYear(String[] args) {
		int month = parseMonth(args[0]);
		int year = parseYear(args[1]);

		int firstDay = getDayOfDate(1, month, year);
		int totalDays = getDaysOfMonth(month, year);
		/* Print header output */
		StringBuilder sb = new StringBuilder();
		sb.append(centerHeader(String.format(DATE_HEADER, MONTHS_LONG[month], year)));
		sb.append(System.lineSeparator());
		sb.append(WEEK_SUN);
		sb.append(System.lineSeparator());

		/* Print spacer to accommodate */
		for (int i = 0; i < firstDay; i++) {
			sb.append("   ");
		}

		/* Print the days by appending into bulider */
		for(int day = 1; day <= totalDays;  day++) {
			sb.append(String.format("%2d", day));
			if((firstDay + day) % 7 == 0 || (day == totalDays)) sb.append(System.lineSeparator());
			else sb.append(" ");
		}

		return sb.toString();
	}

	/**
	 * Returns the string to print the calendar for specified month and year
	 * with Monday as the first day of the week
	 *
	 * @param args
	 * @return
	 */
	@Override
	public String printCalForMonthYearMondayFirst(String[] args) {
		int month = parseMonth(args[1]);
		int year = parseYear(args[2]);

		int firstDay = (getDayOfDate(1, month, year) - 1) % 7;
		int totalDays = getDaysOfMonth(month, year);

		/* Print header output */
		StringBuilder sb = new StringBuilder();
		sb.append(centerHeader(String.format(DATE_HEADER, MONTHS_LONG[month], year)));
		sb.append(System.lineSeparator());
		sb.append(WEEK_MON);
		sb.append(System.lineSeparator());

		/* Print spacer to accommodate */
		for (int i = 0; i < firstDay; i++) {
			sb.append("   ");
		}

		/* Print the days by appending into bulider */
		for(int day = 1; day <= totalDays;  day++) {
			sb.append(String.format("%2d", day));
			if((firstDay + day) % 7 == 0 || (day == totalDays)) sb.append(System.lineSeparator());
			else sb.append(" ");
		}

		return sb.toString();
	}

	/**
	 * Returns the string to print the calendar for specified year
	 *
	 * @param args
	 * @return
     */
	@Override
	public String printCalForYear(String[] args) {
		int year = parseYear(args[0]);

		/* Print all 12 months of the calendar */
		String[][] yearCalendars = new String[12][];
		for(int i = 0; i < yearCalendars.length; i++) {
			yearCalendars[i] = printCalForMonthYear(new String[]{Integer.toString(i+1), Integer.toString(year)})
					.split(System.lineSeparator());
		}

		/* Combine all the strings together to form yearly calendar */
		StringBuilder sb = new StringBuilder();
		/* Append the year */
		sb.append(centerYear(Integer.toString(year)));
		sb.append(System.lineSeparator());

		for (int i = 0; i < 4; i++) {
			/* Append the months first */
			sb.append(centerHeader(MONTHS_LONG[i*3]) + "  " + centerHeader(MONTHS_LONG[i*3+1]) + "  " + centerHeader(MONTHS_LONG[i*3+2]));
			sb.append(System.lineSeparator());
			sb.append(WEEK_SUN + "  " + WEEK_SUN + "  " + WEEK_SUN);
			sb.append(System.lineSeparator());
			for(int j = 0; j < 6; j++) {
				if(yearCalendars[i*3].length > (j+2)) sb.append(padDaysToWidth(yearCalendars[i*3][j+2]));
				else sb.append(padDaysToWidth(""));
				sb.append("  ");

				if(yearCalendars[i*3+1].length > (j+2)) sb.append(padDaysToWidth(yearCalendars[i*3+1][j+2]));
				else sb.append(padDaysToWidth(""));
				sb.append("  ");

				if(yearCalendars[i*3+2].length > (j+2)) sb.append(padDaysToWidth(yearCalendars[i*3+2][j+2]));
				else sb.append(padDaysToWidth(""));

				sb.append(System.lineSeparator());
			}
		}

		return sb.toString();
	}

	/**
	 * Returns the string to print the calendar for specified year with Monday
	 * as the first day of the week
	 *
	 * @param args
	 * @return
     */
	@Override
	public String printCalForYearMondayFirst(String[] args) {
		int year = parseYear(args[1]);

		/* Print all 12 months of the calendar */
		String[][] yearCalendars = new String[12][];
		for(int i = 0; i < yearCalendars.length; i++) {
			yearCalendars[i] = printCalForMonthYearMondayFirst(new String[]{"", Integer.toString(i+1), Integer.toString(year)})
					.split(System.lineSeparator());
		}

		/* Combine all the strings together to form yearly calendar */
		StringBuilder sb = new StringBuilder();
		/* Append the year */
		sb.append(centerYear(Integer.toString(year)));
		sb.append(System.lineSeparator());

		for (int i = 0; i < 4; i++) {
			/* Append the months first */
			sb.append(centerHeader(MONTHS_LONG[i*3]) + "  " + centerHeader(MONTHS_LONG[i*3+1]) + "  " + centerHeader(MONTHS_LONG[i*3+2]));
			sb.append(System.lineSeparator());
			sb.append(WEEK_MON + "  " + WEEK_MON + "  " + WEEK_MON);
			sb.append(System.lineSeparator());
			for(int j = 0; j < 6; j++) {
				if(yearCalendars[i*3].length > (j+2)) sb.append(padDaysToWidth(yearCalendars[i*3][j+2]));
				else sb.append(padDaysToWidth(""));
				sb.append("  ");

				if(yearCalendars[i*3+1].length > (j+2)) sb.append(padDaysToWidth(yearCalendars[i*3+1][j+2]));
				else sb.append(padDaysToWidth(""));
				sb.append("  ");

				if(yearCalendars[i*3+2].length > (j+2)) sb.append(padDaysToWidth(yearCalendars[i*3+2][j+2]));
				else sb.append(padDaysToWidth(""));

				sb.append(System.lineSeparator());
			}
		}

		return sb.toString();
	}

	/**
	 * Runs the cat application with the specified arguments.
	 *
	 * @param args
	 *            Array of arguments for the application. Each array element is
	 *            the path to a file. If no files are specified stdin is used.
	 * @param stdin
	 *            An InputStream. The input for the command is read from this
	 *            InputStream if no files are specified.
	 * @param stdout
	 *            An OutputStream. The output of the command is written to this
	 *            OutputStream.
	 *
	 * @throws CatException
	 *             If the file(s) specified do not exist or are unreadable.
	 */
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws AbstractApplicationException {
		if (args == null) {
			throw new CalException(ERROR_NULL);
		}

		String output = processArgument(args);
		writeToOutput(output, stdout);
	}


	/**
	 *
	 * @param args
	 * @return
	 * @throws CalException
     */
	private String processArgument(String[] args) throws CalException{
		if (args.length == 0) { /* Print current month */
			return printCal(null);
		} else if (args.length == 1) { /* either -m, year or invalid */
			if("-m".equals(args[0].trim())) return printCalWithMondayFirst(null);
			else if(parseYear(args[0].trim()) != INVALID_DATE) return printCalForYear(args);
			else throw new CalException(ERROR_INVALID);
		} else if (args.length == 2) { /* either -m year, month year or invalid */
			if("-m".equals(args[0].trim()) && parseYear(args[1].trim()) != INVALID_DATE) {
				return printCalForYearMondayFirst(args);
			} else if (parseMonth(args[0].trim()) != INVALID_DATE && parseYear(args[0].trim()) != INVALID_DATE) {
				return printCalForMonthYear(args);
			} else throw new CalException(ERROR_INVALID);
		} else if (args.length == 3) { /* -m month year */
			if("-m".equals(args[0].trim()) && parseMonth(args[1].trim()) != INVALID_DATE &&
					parseYear(args[2].trim()) != INVALID_DATE) {
				return printCalForMonthYearMondayFirst(args);
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
			if(MONTHS_LONG[i].equalsIgnoreCase(month) || MONTHS_SHORT[i].equalsIgnoreCase(month)) {
				return i;
			}
		}

		/* If short and long month cannot be parsed */
		try {
			int parsedMonth = Integer.parseInt(month);
			if(0 < parsedMonth && parsedMonth < 13) return parsedMonth - 1;
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
		if( ++month == 2 && isLeapYear(year)) return 29;
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
		int yearBlock = year - (14 - ++month) / 12;
		int centuryBlock = yearBlock + yearBlock / 4 - yearBlock / 100 + yearBlock / 400;
		int monthBlock = month + 12 * ((14 - month) / 12) - 2;
		return (day + centuryBlock + (31 * monthBlock) / 12) % 7;
	}

	private String centerHeader(String header) {
		return center(header, WIDTH_MONTH);
	}

	private String centerYear(String header) {
		return center(header, WIDTH_YEAR);
	}

	private String center(String header, int length) {
		int headerLength = header.length();
		int missingLength = (length - headerLength) / 2;
		int extraLength = (length - headerLength) % 2;
		StringBuilder padder = new StringBuilder();
		for(int i = 0; i < missingLength; i++) {
			padder.append(" ");
		}
		padder.append(header);
		for(int i = 0; i < (missingLength + extraLength); i++) {
			padder.append(" ");
		}
		return padder.toString();
	}

	private String padDaysToWidth(String content) {
		int headerLength = content.length();
		int missingLength = (WIDTH_MONTH - headerLength);
		StringBuilder padder = new StringBuilder(content);
		for(int i = 0; i < missingLength; i++) {
			padder.append(" ");
		}
		return padder.toString();
	}
}
