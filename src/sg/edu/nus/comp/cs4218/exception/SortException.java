package sg.edu.nus.comp.cs4218.exception;

import java.io.IOException;

public class SortException extends AbstractApplicationException {

	private static final long serialVersionUID = -3608957686975123140L;

	public SortException(String message) {
		super("sort: " + message);
	}

	public SortException(String message, IOException e) {
		super("sort: " + message);
	}

	public SortException(String message, Exception e) {
		super("sort: " + message);
	}
}