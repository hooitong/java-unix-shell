package sg.edu.nus.comp.cs4218.exception;

public class SortException extends AbstractApplicationException {

	private static final long serialVersionUID = -3608957686975123140L;

	public SortException(String message) {
		super("sort: " + message);
	}
}