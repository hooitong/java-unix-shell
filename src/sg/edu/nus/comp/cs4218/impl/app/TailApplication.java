package sg.edu.nus.comp.cs4218.impl.app;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.TailException;

/**
 * Print last N lines of the file (or input stream). If there are less than N
 * lines, print existing lines without rising an exception.
 * 
 * <p>
 * <b>Command format:</b> <code>tail [OPTIONS] [FILE]</code>
 * <dl>
 * <dt>OPTIONS</dt>
 * <dd>&quot;-n 15&quot; means printing 15 lines. Print last 10 lines if not
 * specified.</dd>
 * <dt>FILE</dt>
 * <dd>name of the file. If not specified, use stdin.</dd>
 * </dl>
 * </p>
 */
public class TailApplication implements Application {

	private static final String CHARSET_UTF_8 = "UTF-8";

	/**
	 * Runs the tail application with the specified arguments.
	 * 
	 * @param args
	 *            Array of arguments for the application. Only one file could be
	 *            specified. If file is specified, the input should be read from
	 *            the file, else, input is read from stdin. If a flag,-n, is
	 *            specified, it should be accompanied by a number to indicate
	 *            the number of lines. If flag is not specified, the last 10
	 *            lines would be printed.
	 * 
	 * @param stdin
	 *            An InputStream. The input for the command is read from this
	 *            InputStream if no files are specified.
	 * @param stdout
	 *            An OutputStream. The output of the command is written to this
	 *            OutputStream.
	 * 
	 * @throws TailException
	 *             If the file specified do not exist, are unreadable, or an I/O
	 *             exception occurs. If the args array do not matches the
	 *             criteria.
	 */
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout)
			throws TailException {
		
		int numLinesToRead;
		int filePosition = -1;
		switch(args.length)
		{
		case 0:
			numLinesToRead = 15;
		case 1:
			if(args[0].startsWith("-n"))
			{
				numLinesToRead = checkNumberOfLinesInput(args[0]);
			}
			else
			{
				filePosition = 0;
				numLinesToRead = 15;
			}
			break;
		case 2:
			if(args[0].startsWith(("-n")))
			{
				numLinesToRead = checkNumberOfLinesInput(args[0]);
				filePosition = 1;
			}
			else
			{
				throw new TailException("Incorrect flag used to denote number of lines to print");
			}
			break;
		default:
			throw new TailException("Incorrect number of arguments");
		}
		
		if(filePosition>-1)
		{
			Path currentDir = Paths.get(Environment.currentDirectory);
			Path filePath = currentDir.resolve(args[filePosition]);
			boolean isFileReadable = false;
			isFileReadable = checkIfFileIsReadable(filePath);
			if (isFileReadable) 
			{
				try 
				{
					readFromFileAndWriteToStdout(stdout, numLinesToRead, filePath);
				} 
				catch (Exception e) 
				{
					throw new TailException("Exception Caught");
				}
			}
			else
			{
				throw new TailException("File not readable");
			}
		}
		else
		{
			readFromStdinAndWriteToStdout(stdout, numLinesToRead, stdin);
		}
	}

	/**
	 * Parse the number of lines to print from String to int
	 * 
	 * @param numLinesString
	 *            The number of lines received in String
	 * @return numLines 
	 * 		The number of lines received in int
	 * @throws TailException
	 *             If the numLinesString in not an Integer or a negative number.
	 */
	int checkNumberOfLinesInput(String numLinesString) throws TailException {
		int numLines;

		try {
			numLines = Integer.parseInt(numLinesString.substring(3, numLinesString.length()));
		} catch (NumberFormatException nfe) {
			throw new TailException("Invalid command, not a number.");
		}

		

		return numLines;
	}

	/**
	 * Read from stdin and output last number of lines specified to stdout
	 * 
	 * @param stdout
	 *            An Output Stream. The output is written to this stream
	 * @param numLinesRequired
	 *            The number of lines required to output as received in the tail
	 *            command or 10 if not specified in the command
	 * @param stdin
	 *            An input Stream. Reading from stdin and not a file
	 * @throws TailException
	 *             If stdin or stdout is null. Other exceptions caught when
	 *             reading and writing from input and output streams.
	 */
	void readFromStdinAndWriteToStdout(OutputStream stdout,
			int numLinesRequired, InputStream stdin) throws TailException {

		if (stdin == null || stdout == null) {
			throw new TailException("Null Pointer Exception");
		}

		BufferedReader buffReader = new BufferedReader(new InputStreamReader(
				stdin));
		Queue<String> inputArray = new LinkedList<String>();
		String input = "";
		int intCount = 0;

		try {
			if (numLinesRequired == 0) {
				stdout.write("".getBytes(CHARSET_UTF_8));
			} else {
				while ((input = buffReader.readLine()) != null) {
					if (intCount == numLinesRequired) {
						break;
					}
					intCount++;
					inputArray.add(input);
				}
			}
		} catch (Exception e) {
			throw new TailException("Exception caught");
		}

		while (!inputArray.isEmpty()) {
			try {
				if (inputArray.peek().equals("")) {
					inputArray.poll();
					stdout.write(System.lineSeparator().getBytes(CHARSET_UTF_8));
				} else if (inputArray.size() == 1) {
					stdout.write(inputArray.poll().getBytes(CHARSET_UTF_8));
				} else {
					stdout.write(inputArray.poll().getBytes(CHARSET_UTF_8));
					stdout.write(System.lineSeparator().getBytes(CHARSET_UTF_8));
				}

			} catch (Exception e) {
				throw new TailException("Exception caught");
			}
		}
	}

	/**
	 * Read from file and output last number of lines specified to stdout
	 * 
	 * @param stdout
	 *            An Output Stream. The output is written to this stream
	 * @param numLinesRequired
	 *            The number of lines required to output as received in the tail
	 *            command or 10 if not specified in the command
	 * @param filePath
	 *            A Path. Read file from the file path given
	 * @throws TailException
	 *             If stdout is null. Other exceptions caught when
	 *             reading and writing from input and output streams.
	 */
	void readFromFileAndWriteToStdout(OutputStream stdout,
			int numLinesRequired, Path filePath) throws TailException {

		String encoding = CHARSET_UTF_8;

		if (stdout == null) {
			throw new TailException("Stdout is null");
		}
		try {
			if (numLinesRequired == 0) {
				stdout.write("".getBytes(encoding));
			} else {
				FileInputStream fileInStream = new FileInputStream(
						filePath.toString());
				BufferedReader buffReader = new BufferedReader(
						new InputStreamReader(fileInStream));

				Queue<String> inputArray = new LinkedList<String>();
				String input = "";
				int intCount = 0;
				while ((input = buffReader.readLine()) != null) {
					if (intCount == numLinesRequired) {
						break;
					}
					inputArray.add(input);
					intCount++;
				}
				buffReader.close();
				while (!inputArray.isEmpty()) {
					if (inputArray.peek().equals("")) {
						inputArray.poll();
						stdout.write(System.lineSeparator().getBytes(encoding));
					} else if (inputArray.size() == 1) {
						stdout.write(inputArray.poll().getBytes(encoding));
					} else {
						stdout.write(inputArray.poll().getBytes(encoding));
						stdout.write(System.lineSeparator().getBytes(encoding));
					}
				}
			}
		} catch (Exception e) {
			throw new TailException("Exception caught");
		}
	}

	/**
	 * Checks if a file is readable.
	 * @param filePath
	 * 		The path to the file
	 * @return
	 * 		True if the file is readable.
	 * @throws TailException
	 * 		If the file is not readable
	 */
	boolean checkIfFileIsReadable(Path filePath) throws TailException {

		
		if (Files.exists(filePath) && Files.isReadable(filePath)) {
			return true;
		} else {
			throw new TailException("Could not read file");
		}
	}
}
