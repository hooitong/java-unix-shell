package sg.edu.nus.comp.cs4218.exception;

public class CommException extends AbstractApplicationException {

	private static final long serialVersionUID = -3608957686975123140L;

	public CommException(String message) {
		super("Comm: " + message);
	}
	
	public CommException(String message, Exception exception) {
		super("Comm: " + message);
	}
}