package sg.edu.nus.comp.cs4218.impl.cmd;

import sg.edu.nus.comp.cs4218.Command;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

import java.io.InputStream;
import java.io.OutputStream;

public class SequenceCommand implements Command {
    public static final String EXP_SYNTAX = "Invalid syntax encountered.";

    static final String SEQUENCE_SYMBOL = ";";

    Command firstCommand, secondCommand;

    String cmdline;
    String errorMsg;

    public SequenceCommand(String cmdline) {
        this.cmdline = cmdline.trim();
        errorMsg = "";
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
        String[] commands = cmdline.trim().split(SEQUENCE_SYMBOL, 2);
        if(commands.length < 2 || commands[1].isEmpty()) throw new ShellException(EXP_SYNTAX);
        firstCommand = ShellImpl.parse(commands[0]);
        secondCommand = ShellImpl.parse(commands[1]);
    }

    /**
     * Terminates current execution of the command (unused for now)
     */
    @Override
    public void terminate() {
        // TODO Auto-generated method stub
    }
}
