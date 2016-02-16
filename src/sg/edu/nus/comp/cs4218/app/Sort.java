package sg.edu.nus.comp.cs4218.app;

import java.util.List;

import sg.edu.nus.comp.cs4218.Application;

public interface Sort extends Application {

	/**
	 * Returns an ordered list of lines containing only simple letters
	 */
	public List<String> sortStringsSimple(String[] toSort);

	/**
	 * Returns an ordered list of lines containing only capital letters
	 */
	public List<String> sortStringsCapital(String[] toSort);

	/**
	 * Returns an ordered list of lines containing only numbers
	 */
	public List<String> sortNumbers(String[] toSort, boolean numFlag);

	/**
	 * Returns an ordered list of lines containing only special characters
	 */
	public List<String> sortSpecialChars(String[] toSort);

	/**
	 * Returns an ordered list of lines containing simple and capital letters
	 */
	public List<String> sortSimpleCapital(String[] toSort);

	/**
	 * Returns an ordered list of lines containing simple letters and numbers
	 */
	public List<String> sortSimpleNumbers(String[] toSort);

	/**
	 * Returns an ordered list of lines containing simple letters and special
	 * characters
	 */
	public List<String> sortSimpleSpecialChars(String[] toSort);

	/**
	 * Returns an ordered list of lines containing capital letters and numbers
	 */
	public List<String> sortCapitalNumbers(String[] toSort);

	/**
	 * Returns an ordered list of lines containing capital letters and special
	 * character
	 */
	public List<String> sortCapitalSpecialChars(String[] toSort);

	/**
	 * Returns an ordered list of lines containing numbers and special
	 * characters
	 */
	public List<String> sortNumbersSpecialChars(String[] toSort, boolean numFlag);

	/**
	 * Returns an ordered list of lines containing simple and capital letters
	 * and numbers
	 */
	public List<String> sortSimpleCapitalNumber(String[] toSort);

	/**
	 * Returns an ordered list of lines containing simple and capital letters
	 * and special characters
	 */
	public List<String> sortSimpleCapitalSpecialChars(String[] toSort);

	/**
	 * Returns an ordered list of lines containing simple letters, numbers and
	 * special characters
	 */
	public List<String> sortSimpleNumbersSpecialChars(String[] toSort, boolean numFlag);

	/**
	 * Returns an ordered list of lines containing capital letters, numbers and
	 * special characters
	 */
	public List<String> sortCapitalNumbersSpecialChars(String[] toSort, boolean numFlag);

	/**
	 * Returns an ordered list of lines containing simple and capital letters,
	 * numbers and special characters
	 */
	public List<String> sortAll(String[] toSort, boolean numFlag);
}
