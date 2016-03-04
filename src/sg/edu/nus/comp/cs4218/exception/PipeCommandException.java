package sg.edu.nus.comp.cs4218.exception;

public class PipeCommandException extends AbstractApplicationException {

	private static final long serialVersionUID = 2333796686823942499L;

	public PipeCommandException(String message) {
		super("pipe: " + message);
	}

	public PipeCommandException(String message, Exception exception) {
		super("pipe: " + message);
	}
}