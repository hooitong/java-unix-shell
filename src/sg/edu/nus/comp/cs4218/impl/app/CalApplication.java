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
 * The cal command prints the calendar of the month and can be adjusted via
 * arguments to print different formats. This calendar is based on the gregorian
 * calendar and only supports year 1-9999. Months allowed to be pass can be in
 * the form of 1..12, Jan..Dec or January..December.
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
	private static final String ERROR_FLAG = "Invalid flag provided.";
	private static final String ERROR_OUT = "Invalid / lack of output stream to write";

	private static final int WIDTH_YEAR = 64;
	private static final int WIDTH_MONTH = 20;
	private static final int INVALID_DATE = -1;
	private static final int FIRST_MONTH = 0;
	private static final int LAST_MONTH = 11;
	private static final int TOTAL_MONTHS = 12;

	private static final String MON_FLAG = "-m";
	private static final String WEEK_MON = "Mo Tu We Th Fr Sa Su";
	private static final String WEEK_SUN = "Su Mo Tu We Th Fr Sa";
	private static final String[] MONTHS_LONG = { "January", "February", "March", "April", "May", "June", "July",
			"August", "September", "October", "November", "December" };
	private static final String[] MONTHS_SHORT = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct",
			"Nov", "Dec" };

	/**
	 * Returns the string to print the calendar of the current month.
	 *
	 * @param args
	 *            no arguments expected
	 * @return current month calendar in String
	 */
	@Override
	public String printCal(String[] args) {
		/* Get current year and month */
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);

		/* Pass into monthly printer for calendar output */
		return monthPrint(false, month, year);
	}

	/**
	 * Returns the string to print the calendar of the current month with Monday
	 * as the first day of the week.
	 *
	 * @param args
	 *            "-m" flag expected
	 * @return current month calendar (monday format) in String
	 */
	@Override
	public String printCalWithMondayFirst(String[] args) {
		if (!validateMonFlag(args)) {
			return ERROR_FLAG;
		}

		/* Get current year and month */
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);

		/* Pass into monthly printer for calendar output */
		return monthPrint(true, month, year);
	}

	/**
	 * Returns the string to print the calendar for specified month and year.
	 *
	 * @param args
	 *            month and year as two String arguments
	 * @return specified month calendar in String
	 */
	@Override
	public String printCalForMonthYear(String[] args) {
		/* Get the month and year from the arguments */
		int month = parseMonth(args[0]);
		int year = parseYear(args[1]);

		/* Pass into monthly printer for calendar output */
		return monthPrint(false, month, year);
	}

	/**
	 * Returns the string to print the calendar for specified month and year
	 * with Monday as the first day of the week.
	 *
	 * @param args
	 *            "-m", month and year as three String arguments
	 * @return specified month calendar (monday format) in String
	 */
	@Override
	public String printCalForMonthYearMondayFirst(String[] args) {
		if (!validateMonFlag(args)) {
			return ERROR_FLAG;
		}

		/* Get the month and year from the arguments */
		int month = parseMonth(args[1]);
		int year = parseYear(args[2]);

		/* Pass into monthly printer for calendar output */
		return monthPrint(true, month, year);
	}

	/**
	 * Returns the string to print the calendar for specified year.
	 *
	 * @param args
	 *            year as first String argument
	 * @return specified year calendar in String
	 */
	@Override
	public String printCalForYear(String[] args) {
		/* Get the year from the arguments */
		int year = parseYear(args[0]);

		/* Pass into yearly printer for calendar output */
		return yearPrint(false, year);
	}

	/**
	 * Returns the string to print the calendar for specified year with Monday
	 * as the first day of the week.
	 *
	 * @param args
	 *            "-m", year as two String argument
	 * @return specified year calendar in String
	 */
	@Override
	public String printCalForYearMondayFirst(String[] args) {
		if (!validateMonFlag(args)) {
			return ERROR_FLAG;
		}

		/* Get the year from the arguments */
		int year = parseYear(args[1]);

		/* Pass into yearly printer for calendar output */
		return yearPrint(true, year);
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

		if (stdout == null) {
			throw new CalException(ERROR_OUT);
		}

		String output = processArgument(args);
		writeToOutput(output, stdout);
	}

	/**
	 * Process the given arguments and print the appropriate calendar based on
	 * them.
	 *
	 * @param args
	 *            arguments to process
	 * @return the requested calendar
	 * @throws CalException
	 *             if there is an error in the given arguments
	 */
	private String processArgument(String[] args) throws CalException {
		if (args.length == 0) { /* Print current month */
			return printCal(args);
		} else if (args.length == 1) { /* either -m, year or invalid */
			if (validateMonFlag(args)) {
				return printCalWithMondayFirst(args);
			} else if (parseYear(args[0].trim()) == INVALID_DATE) {
				throw new CalException(ERROR_INVALID);
			} else {
				return printCalForYear(args);
			}
		} else if (args.length == 2) { /*
										 * either -m year, month year or invalid
										 */
			if (validateMonFlag(args) && parseYear(args[1].trim()) != INVALID_DATE) {
				return printCalForYearMondayFirst(args);
			} else if (parseMonth(args[0].trim()) == INVALID_DATE || parseYear(args[1].trim()) == INVALID_DATE) {
				throw new CalException(ERROR_INVALID);
			} else {
				return printCalForMonthYear(args);
			}
		} else if (args.length == 3) { /* -m month year */
			if (validateMonFlag(args) && parseMonth(args[1].trim()) != INVALID_DATE
					&& parseYear(args[2].trim()) != INVALID_DATE) {
				return printCalForMonthYearMondayFirst(args);
			} else {
				throw new CalException(ERROR_INVALID);
			}
		} else { /* Invalid number of args - not supported */
			throw new CalException(ERROR_INVALID);
		}
	}

	/**
	 * Write a String output into a output stream.
	 *
	 * @param output
	 *            output to write
	 * @param stdout
	 *            output stream to write into
	 * @throws CalException
	 */
	private void writeToOutput(String output, OutputStream stdout) throws CalException {
		try {
			stdout.write(output.getBytes());
		} catch (IOException e) {
			throw new CalException(e);
		}
	}

	/**
	 * Parses the given string into the actual year. Supports years of 1..9999.
	 *
	 * @param year
	 *            ranging from 1..9999
	 * @return integer value of the year or error value if cannot be parsed
	 */
	private int parseYear(String year) {
		try {
			int parsedYear = Integer.parseInt(year);
			if (0 < parsedYear && parsedYear < 10000) {
				return parsedYear;
			} else {
				return INVALID_DATE;
			}
		} catch (NumberFormatException e) {
			return INVALID_DATE;
		}
	}

	/**
	 * Parses the given string in the form of either 1..12, Jan..Dec,
	 * January..December into the integer form.
	 *
	 * @param month
	 *            1..12 | Jan..Dec | January..December
	 * @return integer value of the month 0..11 or error value if cannot be
	 *         parsed
	 */
	private int parseMonth(String month) {
		/* Match against both long and short form of the months */
		for (int i = 0; i <= LAST_MONTH; i++) {
			if (MONTHS_LONG[i].equalsIgnoreCase(month) || MONTHS_SHORT[i].equalsIgnoreCase(month)) {
				return i;
			}
		}

		/* If short and long month cannot be parsed */
		try {
			int parsedMonth = Integer.parseInt(month);
			if (0 < parsedMonth && parsedMonth < 13) {
				return parsedMonth - 1;
			} else {
				return INVALID_DATE;
			}
		} catch (NumberFormatException e) {
			return INVALID_DATE;
		}
	}

	/**
	 * Returns the number of days in the given month.
	 *
	 * @param month
	 * @param year
	 *            to check for leap year
	 * @return the number of days in the month
	 */
	private int getDaysOfMonth(int month, int year) {
		int literalMonth = month + 1;
		if (literalMonth == 2 && isLeapYear(year)) {
			return 29;
		}
		return (int) (28 + (literalMonth + Math.floor(literalMonth / 8)) % 2 + 2 % literalMonth
				+ 2 * Math.floor(1 / literalMonth));
	}

	/**
	 * Returns a boolean whether the year given is a leap year.
	 * 
	 * @param year
	 * @return true if it is a leap year
	 */
	private boolean isLeapYear(int year) {
		return (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0));
	}

	/**
	 * Return the day of the week the date falls on according to Gregorian
	 * calendar.
	 *
	 * @param day
	 *            day of the date
	 * @param month
	 *            month of the date
	 * @param year
	 *            year of the date
	 * @return integer value where 0 represents sunday, 1 represents monday, etc
	 */
	private int getDayOfDate(int day, int month, int year) {
		int literalMonth = month + 1;
		int yearBlock = year - (14 - literalMonth) / 12;
		int centuryBlock = yearBlock + yearBlock / 4 - yearBlock / 100 + yearBlock / 400;
		int monthBlock = literalMonth + 12 * ((14 - literalMonth) / 12) - 2;
		return (day + centuryBlock + (31 * monthBlock) / 12) % 7;
	}

	/**
	 * Center a string with a fixed length of WIDTH_MONTH for monthly calendars.
	 *
	 * @param header
	 *            header to center
	 * @return centered header of length WIDTH_MONTH
	 */
	private String centerHeader(String header) {
		return center(header, WIDTH_MONTH);
	}

	/**
	 * Center a string with a fixed length of WIDTH_YEAR for yearly calendars.
	 *
	 * @param header
	 *            header to center
	 * @return centered header of length WIDTH_YEAR
	 */
	private String centerYear(String header) {
		return center(header, WIDTH_YEAR);
	}

	/**
	 * Center the given String based on the fixed length provided.
	 *
	 * @param header
	 *            the String to center
	 * @param length
	 *            the length of the padded string
	 * @return the centered string of length provided
	 */
	private String center(String header, int length) {
		int headerLength = header.length();
		int missingLength = (length - headerLength) / 2;
		int extraLength = (length - headerLength) % 2;
		StringBuilder padder = new StringBuilder();
		for (int i = 0; i < missingLength; i++) {
			padder.append(' ');
		}
		padder.append(header);
		for (int i = 0; i < (missingLength + extraLength); i++) {
			padder.append(' ');
		}
		return padder.toString();
	}

	/**
	 * Pad the string to the fixed length for the monthly calendar.
	 *
	 * @param content
	 *            the string to pad
	 * @return the padded string of fixed length
	 */
	private String padDaysToWidth(String content) {
		int headerLength = content.length();
		int missingLength = (WIDTH_MONTH - headerLength);
		StringBuilder padder = new StringBuilder(content);
		for (int i = 0; i < missingLength; i++) {
			padder.append(' ');
		}
		return padder.toString();
	}

	/**
	 * Pretty print the yearly calendar when given the year and the format of
	 * the calendar.
	 *
	 * @param isMon
	 *            flag
	 * @param month
	 *            a valid month 0..11
	 * @param year
	 *            a valid year 1..9999
	 * @return the year calendar of the specified year
	 */
	private String monthPrint(boolean isMon, int month, int year) {
		int firstDay = isMon ? (getDayOfDate(1, month, year) - 1) % 7 : getDayOfDate(1, month, year);
		int totalDays = getDaysOfMonth(month, year);

		/* Print header output */
		StringBuilder builder = new StringBuilder();
		builder.append(centerHeader(MONTHS_LONG[month] + " " + year)).append(System.lineSeparator())
				.append(isMon ? WEEK_MON : WEEK_SUN).append(System.lineSeparator());

		/* Print spacer to accommodate */
		for (int i = 0; i < firstDay; i++) {
			builder.append("   ");
		}

		/* Print the days by appending into bulider */
		for (int day = 1; day <= totalDays; day++) {
			builder.append(String.format("%2d", day));
			if ((firstDay + day) % 7 == 0 || (day == totalDays)) {
				builder.append(System.lineSeparator());
			} else {
				builder.append(' ');
			}
		}

		return builder.toString();
	}

	/**
	 * Pretty print the yearly calendar when given the year and the format of
	 * the calendar.
	 *
	 * @param isMon
	 *            flag
	 * @param year
	 *            a valid year 1..9999
	 * @return the year calendar of the specified year
	 */
	private String yearPrint(boolean isMon, int year) {
		/* Print all 12 months of the calendar */
		String[][] yearCalendars = new String[TOTAL_MONTHS][];
		for (int i = FIRST_MONTH; i < yearCalendars.length; i++) {
			String[] arguments = new String[] { Integer.toString(i + 1), Integer.toString(year) };
			yearCalendars[i] = printCalForMonthYear(arguments).split(System.lineSeparator());
		}

		/* Combine all the strings together to form yearly calendar */
		StringBuilder builder = new StringBuilder();

		/* Append the year */
		builder.append(centerYear(Integer.toString(year))).append(System.lineSeparator());

		/* Select the correct format based on flag isMon */
		String weekFormat = isMon ? WEEK_MON : WEEK_SUN;

		/* Merge all 12 calendars with 3 calendar in a single row */
		for (int i = 0; i < 4; i++) {
			/* Append the months first */
			builder.append(centerHeader(MONTHS_LONG[i * 3]) + "  ");
			builder.append(centerHeader(MONTHS_LONG[i * 3 + 1]) + "  ");
			builder.append(centerHeader(MONTHS_LONG[i * 3 + 2]));
			builder.append(System.lineSeparator());

			/* Append the format of the week for 3 calendars */
			builder.append(weekFormat + "  " + weekFormat + "  " + weekFormat);
			builder.append(System.lineSeparator());

			/* Merge all the 3 calendars together */
			for (int j = 2; j < 8; j++) {
				if (yearCalendars[i * 3].length > j) {
					builder.append(padDaysToWidth(yearCalendars[i * 3][j]));
				} else {
					builder.append(padDaysToWidth(""));
				}
				builder.append("  ");

				if (yearCalendars[i * 3 + 1].length > j) {
					builder.append(padDaysToWidth(yearCalendars[i * 3 + 1][j]));
				} else {
					builder.append(padDaysToWidth(""));
				}
				builder.append("  ");

				if (yearCalendars[i * 3 + 2].length > j) {
					builder.append(padDaysToWidth(yearCalendars[i * 3 + 2][j]));
				} else {
					builder.append(padDaysToWidth(""));
				}

				builder.append(System.lineSeparator());
			}
		}

		return builder.toString();
	}

	/**
	 * Defensive method to ensure that "-m" flag is valid.
	 *
	 * @param args
	 *            String array
	 * @return true if the flag in the first argument is valid
	 */
	private boolean validateMonFlag(String[] args) {
		return args.length > 0 && MON_FLAG.equalsIgnoreCase(args[0]);
	}
}
