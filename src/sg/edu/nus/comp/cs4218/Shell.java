package sg.edu.nus.comp.cs4218;

import java.io.OutputStream;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;

public interface Shell {
	
	/**
	 * Parses and evaluates user's command line.
	 */	
	public void parseAndEvaluate(String cmdline, OutputStream stdout) throws AbstractApplicationException, ShellException;
	
	/**
	 * Evaluate pipe call with two commands
	 */
	public String pipeTwoCommands(String[] args);

	/**
	 * Evaluate pipe call with more than two commands
	 */
	public String pipeMultipleCommands(String[] args);

	/**
	 * Evaluate pipe call with one part generating an exception
	 */
	public String pipeWithException(String[] args);
}
