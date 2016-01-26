package sg.edu.nus.comp.cs4218.impl.app;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.HeadException;

/**
 * Print first N lines of the file (or input stream). If there are less than N
 * lines, print existing lines without rising an exception.
 * 
 * <p>
 * <b>Command format:</b> <code>head [OPTIONS] [FILE]</code>
 * <dl>
 * <dt>OPTIONS</dt>
 * <dd>&quot;-n 15&quot; means printing 15 lines. Print first 10 lines if not
 * specified.</dd>
 * <dt>FILE</dt>
 * <dd>name of the file. If not specified, use stdin.</dd>
 * </dl>
 * </p>
 */
public class HeadApplication implements Application {

	/**
	 * Runs the head application with the specified arguments.
	 * 
	 * @param args
	 *            Array of arguments for the application. Only one file could be
	 *            specified. If file is specified, the input should be read from
	 *            the file, else, input is read from stdin. If a flag,-n, is
	 *            specified, it should be accompanied by a number to indicate
	 *            the number of lines. If flag is not specified, the first 10
	 *            lines would be printed.
	 * @param stdin
	 *            An InputStream. The input for the command is read from this
	 *            InputStream if no files are specified.
	 * @param stdout
	 *            An OutputStream. The output of the command is written to this
	 *            OutputStream.
	 * 
	 * @throws HeadException
	 *             If the file specified do not exist, are unreadable, or an I/O
	 *             exception occurs. If the args array do not matches the
	 *             criteria.
	 */
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout)
			throws HeadException {

		if (args == null || args.length == 0 || args.length == 2) {
			int numLinesToRead = 0;

			if (args == null || args.length == 0) {
				numLinesToRead = 15;
			} else {
				if (args.length == 2 && args[0].equals("--n")) {
					numLinesToRead = checkNumberOfLinesInput(args[1]);
				} else {
					throw new HeadException(
							"Invalid Head Command for reading from stdin");
				}
			}

			readFromStdinAndWriteToStdout(stdout, numLinesToRead, stdin);

		} else {
			int numLines;

			if (args.length == 3 || args.length == 1) {
				if (args.length == 3 && args[0].equals("--n")) {
					numLines = checkNumberOfLinesInput(args[1]);
				} else if (args.length == 1) {
					numLines = 15;
				} else {
					throw new HeadException("Incorrect flag used");
				}
			} else {
				throw new HeadException("Invalid Head Command");
			}

			// check file
			Path currentDir = Paths.get(Environment.currentDirectory);
			int filePosition = 0;
			if (args.length == 3) {
				filePosition = 2;
			}
			Path filePath = currentDir.resolve(args[filePosition]);
			boolean isFileReadable = false;
			isFileReadable = checkIfFileIsReadable(filePath);

			if (isFileReadable) {
				readFromFileAndWriteToStdout(stdout, numLines, filePath);
			}
		}
	}

	/**
	 * Read from stdin and output first N number of lines specified to stdout
	 * 
	 * @param stdout
	 *            An Output Stream. The output is written to this stream
	 * @param numLinesToRead
	 *            The number of lines required to output as received in the head
	 *            command or 10 if not specified in the command
	 * @param stdin
	 *            An input Stream. Reading from stdin and not a file
	 * @throws HeadException
	 *             If stdin or stdout is null. I/O exceptions caught when
	 *             reading and writing from input and output streams.
	 */
	void readFromStdinAndWriteToStdout(OutputStream stdout, int numLinesToRead,
			InputStream stdin) throws HeadException {

		if (stdin == null || stdout == null) {
			throw new HeadException("Null Pointer Exception");
		}

		BufferedReader buffReader = new BufferedReader(new InputStreamReader(
				stdin));

		int numRead = 0;

		while (numLinesToRead != numRead) {
			try {
				String inputString = buffReader.readLine();
				stdout.write(inputString.getBytes("UTF-8"));
				if (numLinesToRead == numRead) {
					break;
				}
				stdout.write(System.lineSeparator().getBytes("UTF-8"));
				numRead++;
			} catch (IOException e) {
				throw new HeadException("IO Exception");
			}
		}
	}

	/**
	 * Parse the number of lines to print from String to int
	 * 
	 * @param numLinesString
	 *            The number of lines received in String
	 * @return numLines The number of lines received in int
	 * @throws HeadException
	 *             If the numLinesString in not an Integer or a negative number.
	 */
	int checkNumberOfLinesInput(String numLinesString) throws HeadException {
		int numLines;
		try {
			numLines = Integer.parseInt(numLinesString);
		} catch (NumberFormatException nfe) {
			throw new HeadException("Invalid command, not a number.");
		}

		return numLines;
	}

	/**
	 * Read from file and output last number of lines specified to stdout
	 * 
	 * @param stdout
	 *            An Output Stream. The output is written to this stream
	 * @param numLinesRequired
	 *            The number of lines required to output as received in the head
	 *            command or 10 if not specified in the command
	 * @param filePath
	 *            A Path. Read file from the file path given
	 * @throws HeadException
	 *             If stdout is null. Other exceptions caught when reading and
	 *             writing from input and output streams.
	 */
	void readFromFileAndWriteToStdout(OutputStream stdout,
			int numLinesRequired, Path filePath) throws HeadException {

		String encoding = "UTF-8";

		if (stdout == null) {
			throw new HeadException("Stdout is null");
		}

		try {
			FileInputStream fileInStream = new FileInputStream(
					filePath.toString());
			BufferedReader buffReader = new BufferedReader(
					new InputStreamReader(fileInStream));

			int numLinesWrote = 0;
			String input = "";
			while ((input = buffReader.readLine()) != null) {
				if (numLinesWrote == numLinesRequired) {
					break;
				}
				stdout.write(input.getBytes(encoding));
				stdout.write(System.lineSeparator().getBytes(encoding));
				numLinesWrote++;
			}
			buffReader.close();

		} catch (IOException e) {
			throw new HeadException("IOException");
		}

	}

	/**
	 * Checks if a file is readable.
	 * 
	 * @param filePath
	 *            The path to the file
	 * @return True if the file is readable.
	 * @throws HeadException
	 *             If the file is not readable
	 */
	boolean checkIfFileIsReadable(Path filePath) throws HeadException {
		
		if (Files.exists(filePath) && Files.isReadable(filePath)) {
			return true;
		} else {
			throw new HeadException("Could not read file");
		}
	}
}
