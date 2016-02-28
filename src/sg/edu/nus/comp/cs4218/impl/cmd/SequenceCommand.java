package sg.edu.nus.comp.cs4218.impl.cmd;

import sg.edu.nus.comp.cs4218.Command;
import sg.edu.nus.comp.cs4218.Shell;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

import java.io.InputStream;
import java.io.OutputStream;

public class SequenceCommand implements Command {
	public static final String EXP_SYNTAX = "Invalid syntax encountered.";
	public static final String MISSING_ARG = "Missing arg for pipe command.";

	static final String SEQUENCE_DOUBLE = ";(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)";
	static final String SEQUENCE_SINGLE = ";(?=([^']*'[^']*')*[^']*$)";

	Command firstCommand, secondCommand;

	String cmdline;

	public SequenceCommand(String cmdline) {
		this.cmdline = cmdline.trim();
	}

	/**
	 * Evaluates command using data provided through stdin stream. Write result
	 * to stdout stream.
	 */
	public void evaluate(InputStream stdin, OutputStream stdout) throws AbstractApplicationException, ShellException {
		firstCommand.evaluate(stdin, stdout);
		secondCommand.evaluate(stdin, stdout);
	}

	/**
	 * Finds the first semicolon and parses both sides of the command line.
	 *
	 * @throws ShellException
	 */
	public void parse() throws ShellException {
		String[] doubleCommands = cmdline.trim().split(SEQUENCE_DOUBLE, 2);
		String[] singleCommands = cmdline.trim().split(SEQUENCE_SINGLE, 2);
		String[] validCommands;

		/* Setup boolean parameters and conditions for parsing */
		boolean dblNotFound = doubleCommands.length < 2;
		boolean sinNotFound = singleCommands.length < 2;
		boolean dblArgMissing = doubleCommands.length == 2 && doubleCommands[1].isEmpty();
		boolean sinArgMissing = singleCommands.length == 2 && singleCommands[1].isEmpty();

		if(dblNotFound || sinNotFound) {
			throw new ShellException(EXP_SYNTAX);
		} else if (dblArgMissing && sinArgMissing) {
			throw new ShellException(MISSING_ARG);
		} else if (dblArgMissing || sinArgMissing) {
			boolean singleValid = singleCommands[0].length() > doubleCommands[0].length();
			validCommands = singleValid ? singleCommands : doubleCommands;
		} else {
			validCommands = singleCommands;
		}

		firstCommand = ShellImpl.parse(validCommands[0]);
		secondCommand = ShellImpl.parse(validCommands[1]);
	}

	/**
	 * Terminates current execution of the command (unused for now)
	 */
	@Override
	public void terminate() {
		// TODO Auto-generated method stub
	}
}
