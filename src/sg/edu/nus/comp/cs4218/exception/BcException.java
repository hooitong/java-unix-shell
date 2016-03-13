package sg.edu.nus.comp.cs4218.exception;

public class BcException extends AbstractApplicationException {

	private static final long serialVersionUID = 1359222356518059216L;

	public BcException(String message) {
		super("bc: " + message);
	}

	public BcException(String message, Exception exception) {
		super("bc: " + message);
	}
}
