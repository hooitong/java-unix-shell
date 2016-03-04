package sg.edu.nus.comp.cs4218.exception;

public class DateException extends AbstractApplicationException {
	private static final long serialVersionUID = -4071979116776975963L;

	public DateException(String message) {
		super("date: " + message);
	}

	public DateException(Exception excep) {
		super("date: " + excep.getMessage());
	}
}