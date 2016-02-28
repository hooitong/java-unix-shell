package sg.edu.nus.comp.cs4218.exception;

public class CatException extends AbstractApplicationException {
	
	private static final long serialVersionUID = 2333796686823942499L;

	public CatException(String message) {
		super("cat: " + message);
	}
	
	/**
	 * Overloaded constructor to wrap any exception into a CatException.
	 * 
	 * @param excep
	 */
	public CatException(Exception excep) {
		super("cat: " + excep.getMessage());
	}
	
	
	/**
	 * Ignore exception but print message instead.
	 * 
	 * @param excep
	 */
	public CatException(Exception excep, String message) {
		super("cat: " + message);
	}
	
}