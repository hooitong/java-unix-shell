package sg.edu.nus.comp.cs4218.app;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.exception.CommException;

public interface Comm extends Application {

	/**
	 * Returns string to print comparisons when there are no matches in both
	 * files
	 * 
	 * @throws CommException
	 */
	public String commNoMatches(String... args);

	/**
	 * Returns string to print comparisons when there are only lines in the
	 * first file to match
	 * 
	 * @throws CommException
	 */
	public String commOnlyFirst(String... args);

	/**
	 * Returns string to print comparisons when there are only lines in the
	 * second file to match
	 * 
	 * @throws CommException
	 */
	public String commOnlySecond(String... args);

	/**
	 * Returns string to print comparisons when some of the lines match
	 * 
	 * @throws CommException
	 */
	public String commBothMathches(String... args);

	/**
	 * Returns string to print comparisons when there are all matches in both
	 * files
	 * 
	 * @throws CommException
	 */
	public String commAllMatches(String... args);
}
