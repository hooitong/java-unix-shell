package sg.edu.nus.comp.cs4218.exception;

public class ShellException extends Exception {

	private static final long serialVersionUID = -4439395674558704575L;

	public ShellException(String message) {
		super("shell: " + message);
	}

	/**
	 * Overloaded constructor to wrap any exception into a ShellException.
	 * 
	 * @param excep
	 */
	public ShellException(Exception excep) {
		super("shell: " + excep.getMessage());
	}
}