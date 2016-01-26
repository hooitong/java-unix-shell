package sg.edu.nus.comp.cs4218.app;

import sg.edu.nus.comp.cs4218.Application;

public interface Comm extends Application {

	/**
	 * Returns string to print comparisons when there are no matches in both
	 * files
	 */
	public String commNoMatches(String[] args);

	/**
	 * Returns string to print comparisons when there are only lines in the
	 * first file to match
	 */
	public String commOnlyFirst(String[] args);

	/**
	 * Returns string to print comparisons when there are only lines in the
	 * second file to match
	 */
	public String commOnlySecond(String[] args);

	/**
	 * Returns string to print comparisons when some of the lines match
	 */
	public String commBothMathches(String[] args);

	/**
	 * Returns string to print comparisons when there are all matches in both
	 * files
	 */
	public String commAllMatches(String[] args);
}
