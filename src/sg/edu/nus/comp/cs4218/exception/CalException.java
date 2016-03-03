package sg.edu.nus.comp.cs4218.exception;

public class CalException extends AbstractApplicationException {
    private static final long serialVersionUID = -183372846620152792L;

    public CalException(String message) {
        super("cal: " + message);
    }

    public CalException(Exception e) {
        super("cal: " + e.getMessage());
    }
}
