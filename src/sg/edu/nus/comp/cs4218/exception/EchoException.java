package sg.edu.nus.comp.cs4218.exception;

public class EchoException extends AbstractApplicationException {
	
	private static final long serialVersionUID = 7499486452467089104L;

	public EchoException(String message) {
		super("echo: " + message);
	}
	
	/**
	 * Overloaded constructor to wrap any exception into a HeadException.
	 * 
	 * @param excep
	 */
	public EchoException(Exception excep) {
		super("echo: " + excep.getMessage());
	}
	
}