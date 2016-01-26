package sg.edu.nus.comp.cs4218.app;

import sg.edu.nus.comp.cs4218.Application;

public interface Bc extends Application {

	/**
	 * Returns resultant string with expression of the form <number>, where
	 * number can by any natural number or floating point number, evaluated
	 */
	public String number(String[] args);

	/**
	 * Returns resultant string with expression of the form -<expression>
	 * evaluated
	 */
	public String negate(String[] args);

	/**
	 * Returns resultant string with expression of the form <expression> +
	 * <expression> evaluated
	 */
	public String add(String[] args);

	/**
	 * Returns resultant string with expression of the form <expression> -
	 * <expression> evaluated
	 */
	public String subtract(String[] args);

	/**
	 * Returns resultant string with expression of the form <expression> *
	 * <expression> evaluated
	 */
	public String multiply(String[] args);

	/**
	 * Returns resultant string with expression of the form <expression> /
	 * <expression> evaluated
	 */
	public String divide(String[] args);

	/**
	 * Returns resultant string with expression of the form <expression> ^
	 * <expression> evaluated
	 */
	public String pow(String[] args);

	/**
	 * Returns resultant string with expression of the form (<expression>)
	 * evaluated
	 */
	public String bracket(String[] args);

	/**
	 * Returns resultant string with expression of the form <expression> >
	 * <expression> evaluated
	 */
	public String greaterThan(String[] args);

	/**
	 * Returns resultant string with expression of the form <expression> >=
	 * <expression> evaluated
	 */
	public String greaterThanOrEqual(String[] args);

	/**
	 * Returns resultant string with expression of the form <expression> <
	 * <expression> evaluated
	 */
	public String lessThan(String[] args);

	/**
	 * Returns resultant string with expression of the form <expression> <=
	 * <expression> evaluated
	 */
	public String lessThanOrEqual(String[] args);

	/**
	 * Returns resultant string with expression of the form <expression> ==
	 * <expression> evaluated
	 */
	public String equalEqual(String[] args);

	/**
	 * Returns resultant string with expression of the form <expression> !=
	 * <expression> evaluated
	 */
	public String notEqual(String[] args);

	/**
	 * Returns resultant string with expression of the form <expression> &&
	 * <expression> evaluated
	 */
	public String and(String[] args);

	/**
	 * Returns resultant string with expression of the form <expression> ||
	 * <expression> evaluated
	 */
	public String or(String[] args);

	/**
	 * Returns resultant string with expression of the form !<expression>
	 * evaluated
	 */
	public String not(String[] args);
}
