package sg.edu.nus.comp.cs4218.app;

import sg.edu.nus.comp.cs4218.Application;

public interface Cal extends Application {

	/**
	 * Returns the string to print the calendar of the current month
	 */
	public String printCal(String[] args);

	/**
	 * Returns the string to print the calendar of the current month with Monday
	 * as the first day of the week
	 */
	public String printCalWithMondayFirst(String[] args);

	/**
	 * Returns the string to print the calendar for specified month and year
	 */
	public String printCalForMonthYear(String[] args);

	/**
	 * Returns the string to print the calendar for specified year
	 */
	public String printCalForYear(String[] args);

	/**
	 * Returns the string to print the calendar for specified month and year
	 * with Monday as the first day of the week
	 */
	public String printCalForMonthYearMondayFirst(String[] args);

	/**
	 * Returns the string to print the calendar for specified year with Monday
	 * as the first day of the week
	 */
	public String printCalForYearMondayFirst(String[] args);
}
