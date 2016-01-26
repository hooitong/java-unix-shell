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

	/**
	 * Evaluate globbing with no files or directories
	 */
	public String globNoPaths(String[] args);

	/**
	 * Evaluate globbing with one file
	 */
	public String globOneFile(String[] args);

	/**
	 * Evaluate globbing with files and directories one level down
	 */
	public String globFilesDirectories(String[] args);

	/**
	 * Evaluate globbing with files and directories multiple levels down
	 */
	public String globMultiLevel(String[] args);
}
