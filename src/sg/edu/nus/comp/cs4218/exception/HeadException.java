package sg.edu.nus.comp.cs4218.exception;

public class HeadException extends AbstractApplicationException {

	private static final long serialVersionUID = -3608957686975123140L;

	public HeadException(String message) {
		super("head: " + message);
	}
	
	/**
	 * Overloaded constructor to wrap any exception into a HeadException.
	 * 
	 * @param excep
	 */
	public HeadException(Exception excep) {
		super("head: " + excep.getMessage());
	}
	
	/**
	 * Original exception stack is ignore, but print current message instead.
	 * 
	 * @param excep
	 */
	public HeadException(Exception excep, String message) {
		super("head: " + message);
	}
}