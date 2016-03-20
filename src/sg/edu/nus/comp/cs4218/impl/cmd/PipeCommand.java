package sg.edu.nus.comp.cs4218.impl.cmd;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import sg.edu.nus.comp.cs4218.Command;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.PipeCommandException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class PipeCommand implements Command {

	static final String WHITESPACE = "\\s+";
	static final char PIPE = '|';
	static final int ZERO = 0;
	static final int ONE = 0;
	String app;
	String cmdline, inputStreamS, outputStreamS;
	ArrayList<String> argsList;
	ArrayList<CallCommand> cmdList = new ArrayList<CallCommand>();
	Boolean error;
	String errorMsg;

	public PipeCommand(String cmdLine) {
		this.argsList = new ArrayList<String>();
		this.cmdline = cmdLine;
		separateIntoIndividualCommands(cmdLine);
	}

	/**
	 * this method uses the pipe as a delimiter and breaks a line of string into
	 * their various call commands
	 * 
	 * @param cmdLine
	 *            a string of commands
	 */
	private void separateIntoIndividualCommands(String cmdLine) {
		StringBuilder stringBuilder = new StringBuilder("");
		for (int i = 0; i < cmdLine.length(); i++) {
			if (cmdLine.charAt(i) == PIPE) {
				this.argsList.add(stringBuilder.toString().trim());
				stringBuilder.setLength(ZERO);
			} else {
				stringBuilder.append(cmdLine.charAt(i));
			}
			if (i == cmdline.length() - 1 && !cmdLine.equals(stringBuilder.toString())) {
				this.argsList.add(stringBuilder.toString().trim());
			}
		}
	}

	/**
	 * Evaluates command using data provided through stdin stream. Write result
	 * to stdout stream.
	 */
	public void evaluate(InputStream stdin, OutputStream stdout) throws AbstractApplicationException, ShellException {
		ByteArrayOutputStream outgoingPipe = new ByteArrayOutputStream();
		for (int i = 0; i < this.cmdList.size(); i++) {
			if (i == 0) {
				try {
					cmdList.get(i).evaluate(stdin, outgoingPipe);
				} catch (AbstractApplicationException | ShellException e) {
					throw new PipeCommandException("exception detected for one of the call commands", e);
				}
			} else if (i < this.cmdList.size() - 1) {
				InputStream incomingPipe = new ByteArrayInputStream(outgoingPipe.toByteArray());
				outgoingPipe.reset();
				try {
					cmdList.get(i).evaluate(incomingPipe, outgoingPipe);
				} catch (AbstractApplicationException | ShellException e) {
					throw new PipeCommandException("exception detected for one of the call commands", e);
				}

			} else {
				InputStream incomingPipe = new ByteArrayInputStream(outgoingPipe.toByteArray());
				try {
					cmdList.get(i).evaluate(incomingPipe, stdout);
				} catch (AbstractApplicationException | ShellException e) {
					throw new PipeCommandException("exception detected for one of the call commands", e);
				}

			}
		}
	}

	/**
	 * Terminates current execution of the command (unused for now)
	 */
	@Override
	public void terminate() {
		// unused
	}

	public void parse() throws ShellException {
		for (int i = 0; i < this.argsList.size(); i++) {
			Command command = ShellImpl.parse(this.argsList.get(i));
			cmdList.add((CallCommand) command);
		}
	}
}
