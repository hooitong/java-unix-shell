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
import java.util.LinkedList;
import java.util.Stack;

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
	private static final String NEW_LINE = System.lineSeparator();
	private static final String NUMLINES_FLAG = "-n";

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
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws TailException {
		int numLinesToRead = 15;
		int filePosition = -1;
		if (args != null) {
			switch (args.length) {
			case 0:
				break;
			case 1:
				filePosition = 0;
				break;
			case 2:
				if (args[0].equals((NUMLINES_FLAG))) {
					numLinesToRead = checkNumberOfLinesInput(args[1]);
				} else {
					throw new TailException("Incorrect flag used to denote number of lines to print");
				}
				break;
			case 3:
				if (args[0].equals(NUMLINES_FLAG)) {
					numLinesToRead = checkNumberOfLinesInput(args[1]);
					filePosition = 2;
				} else {
					throw new TailException("Incorrect flag used to denote number of lines to print");
				}
				break;
			default:
				throw new TailException("Incorrect number of arguments");
			}
		}

		Stack<String> inputString = null;
		if (filePosition > -1) {
			Path currentDir = Paths.get(Environment.currentDirectory);
			Path filePath = currentDir.resolve(args[filePosition]);
			boolean isFileReadable = false;
			isFileReadable = checkIfFileIsReadable(filePath);
			if (isFileReadable) {
				inputString = readFromFile(filePath);
			} else {
				throw new TailException("File not readable");
			}
		} else {
			inputString = readFromStdin(stdin);
		}

		LinkedList<String> wrappedString = extractTail(inputString, numLinesToRead);
		writeToStdout(stdout, wrappedString);
	}

	/**
	 * Parse the number of lines to print from String to int
	 * 
	 * @param numLinesString
	 *            The number of lines received in String
	 * @return numLines The number of lines received in int
	 * @throws TailException
	 *             If the numLinesString in not an Integer or a negative number.
	 */
	int checkNumberOfLinesInput(String numLinesString) throws TailException {
		int numLines;

		try {
			numLines = Integer.parseInt(numLinesString);
		} catch (NumberFormatException nfe) {
			throw new TailException("Number of lines not a number");
		}
		if (numLines < 0) {
			throw new TailException("Number of lines should be at least 0");
		}
		return numLines;
	}

	/**
	 * Wraps text to specified width without breaking words
	 * 
	 * @param stringToWrap
	 *            String to wrap
	 * @param wrapWidth
	 *            The value of the wrap width
	 * @return wrappedString Wrapped string
	 * @throws TailException
	 *             If the wrap width is too short (i.e. shorter than the longest
	 *             word in the string
	 */
	LinkedList<String> extractTail(Stack<String> textToExtractFrom, int numLines) throws TailException {
		LinkedList<String> extractedText = new LinkedList<String>();
		if (textToExtractFrom.isEmpty() || numLines == 0) {
			return extractedText;
		}
		int count = 0;
		while (count < numLines) {
			if (textToExtractFrom.isEmpty()) {
				break;
			} else {
				extractedText.addFirst(textToExtractFrom.pop());
				++count;
			}
		}

		return extractedText;
	}

	/**
	 * Checks if a file is readable.
	 * 
	 * @param filePath
	 *            The path to the file
	 * @return True if the file is readable.
	 * @throws TailException
	 *             If the file is not readable
	 */
	boolean checkIfFileIsReadable(Path filePath) throws TailException {
		if (Files.exists(filePath) && Files.isReadable(filePath)) {
			return true;
		} else {
			throw new TailException("Could not read file");
		}
	}

	/**
	 * Writes to specified OutputStream
	 * 
	 * @param stdout
	 *            An OutputStream. Write stringToWrite to this OutputStream.
	 * @param linesToWrite
	 *            The lines to write to stdout
	 * @throws TailException
	 *             If stdout is null or there is an error writing to stdout
	 */
	void writeToStdout(OutputStream stdout, LinkedList<String> linesToWrite) throws TailException {
		if (stdout == null) {
			throw new TailException("Null pointer exception - stdout is not defined");
		}

		try {
			while (!linesToWrite.isEmpty()) {
				stdout.write(linesToWrite.removeFirst().getBytes(CHARSET_UTF_8));
				if (!linesToWrite.isEmpty()) {
					stdout.write(NEW_LINE.getBytes(CHARSET_UTF_8));
				}
			}
		} catch (IOException e) {
			throw new TailException("Error writing to stdout");
		}
	}

	/**
	 * Reads from file
	 *
	 * @param filePath
	 *            A Path. Read file from the file path given.
	 * @return textToExtract Stack of read lines
	 * @throws TailException
	 *             If there is an error reading from the file
	 */
	Stack<String> readFromFile(Path filePath) throws TailException {
		Stack<String> textToExtract = new Stack<String>();

		try {
			FileInputStream fileInStream = new FileInputStream(filePath.toString());
			BufferedReader buffReader = new BufferedReader(new InputStreamReader(fileInStream));

			String input = "";

			while ((input = buffReader.readLine()) != null) {
				textToExtract.push(input);
			}

			buffReader.close();
		} catch (IOException e) {
			throw new TailException("Error reading from file");
		}
		return textToExtract;
	}

	/**
	 * Reads from stdin
	 *
	 * @param stdin
	 *            An InputStream. Read input from this InputStream.
	 * @return textToExtract Stack of read lines
	 * @throws TailException
	 *             If stdin is null or there is an error reading from stdin
	 */
	Stack<String> readFromStdin(InputStream stdin) throws TailException {
		Stack<String> textToExtract = new Stack<String>();
		if (stdin == null) {
			throw new TailException("Null pointer exception - stdin is not defined");
		}
		try {
			BufferedReader buffReader = new BufferedReader(new InputStreamReader(stdin));

			String input = "";

			while ((input = buffReader.readLine()) != null) {
				textToExtract.push(input);
			}

			buffReader.close();
		} catch (Exception e) {
			throw new TailException("Error reading from stdin");
		}

		return textToExtract;
	}

}
