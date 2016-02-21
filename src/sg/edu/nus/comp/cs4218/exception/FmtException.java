package sg.edu.nus.comp.cs4218.exception;

public class FmtException extends AbstractApplicationException 
{
	private static final long serialVersionUID = 4372874496473057307L;

	public FmtException(String message) 
	{
		super("tail: " + message);
	}
}