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
import sg.edu.nus.comp.cs4218.exception.FmtException;

/**
 * The fmt command wraps the given text at the specified maximum width without
 * breaking a line in between words. 
 * 
 * <p>
 * <b>Command format:</b> <code>fmt [OPTIONS] [FILE]</code>
 * <dl>
 * <dt>OPTIONS</dt>
 * <dd> “​­w 50​” means print the given text where each line has at most 50 
 * characters.      Default value is 80.</dd>
 * <dt>FILE</dt>
 * <dd>the name of the file(s). If no files are specified, use stdin.</dd>
 * </dl>
 * </p>
 */
public class FmtApplication implements Application {
	private static final String CHARSET_UTF_8 = "UTF-8";
	private static final String NEW_LINE = System.lineSeparator();
	private static final String WIDTH_FLAG = "-w";

	/**
	 * Runs the fmt application with the specified arguments.
	 * 
	 * @param args
	 *            Array of arguments for the application. Each array element is
	 *            the path to a file. If no files are specified stdin is used.
	 * @param stdin
	 *            An InputStream. The input for the command is read from this
	 *            InputStream if no files are specified.
	 * @param stdout
	 *            An OutputStream. The output of the command is written to this
	 *            OutputStream.
	 * 
	 * @throws FmtException
	 *             If the file(s) specified do not exist or are unreadable.
	 */
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws FmtException {
		int wrapWidth = 80;
		int filePosition = -1;
		switch (args.length) {
		case 0:
			break;
		case 1:
			filePosition = 0;
			break;
		case 2:
			if (args[0].equals((WIDTH_FLAG))) {
				wrapWidth = checkWrapWidth(args[1]);
			} else {
				throw new FmtException("Incorrect flag used to denote number of lines to print");
			}
			break;
		case 3:
			if (args[0].equals(WIDTH_FLAG)) {
				wrapWidth = checkWrapWidth(args[1]);
				filePosition = 2;
			} else {
				throw new FmtException("Incorrect flag used to denote number of lines to print");
			}
			break;
		default:
			throw new FmtException("Incorrect number of arguments");
		}

		String inputString = "";
		if (filePosition > -1) {
			Path currentDir = Paths.get(Environment.currentDirectory);
			Path filePath = currentDir.resolve(args[filePosition]);
			boolean isFileReadable = false;
			isFileReadable = checkIfFileIsReadable(filePath);
			if (isFileReadable) {
				inputString = readFromFile(filePath);
			} else {
				throw new FmtException("File not readable");
			}
		} else {
			inputString = readFromStdin(stdin);
		}

		String wrappedString = wrapText(inputString, wrapWidth);
		writeToStdout(stdout, wrappedString);
	}

	/**
	 * Wraps text to specified width without breaking words
	 * 
	 * @param stringToWrap
	 *            String to wrap
	 * @param wrapWidth
	 *            The value of the wrap width
	 * @return wrappedString Wrapped string
	 * @throws FmtException
	 *             If the wrap width is too short (i.e. shorter than the longest
	 *             word in the string
	 */
	String wrapText(String stringToWrap, int wrapWidth) throws FmtException {
		if (stringToWrap.isEmpty()) {
			return "";
		}
		String stringToWrapM = stringToWrap.replaceAll("(\\r|\\n)", " ");
		String stringToWrapMod = stringToWrapM.replaceAll(" +", " ");
		String[] strArray = stringToWrapMod.split(" ");
		String wrappedString = "";
		int count = 0;
		String tempLine = "";
		while (count < strArray.length) {
			String trimmedString = strArray[count].replace(String.valueOf((char) 160), " ").trim();
			
			if (tempLine.length() == 0) {
				if (wrapWidth < trimmedString.length()) {
					wrappedString = wrappedString.concat(NEW_LINE + trimmedString);
					++count;
				} else {
					tempLine = tempLine.concat(trimmedString);
					++count;
				}
			} else {
				if ((tempLine.length() + strArray[count].replace(String.valueOf((char) 160), " ").trim().length() + 1) < wrapWidth) {
					tempLine = tempLine.concat(" " + strArray[count].replace(String.valueOf((char) 160), " ").trim());
					++count;
				} else {
					if (wrappedString.isEmpty()) {
						wrappedString = wrappedString.concat(tempLine);
					} else {
						wrappedString = wrappedString.concat(NEW_LINE + tempLine);
					}
					tempLine = "";
				}
			}
		}
		if (wrappedString.isEmpty()) {
			wrappedString = wrappedString.concat(tempLine);
		} else {
			wrappedString = wrappedString.concat(NEW_LINE + tempLine);
		}
		
		return wrappedString;
	}

	/**
	 * Parses the wrap width from String to int
	 * 
	 * @param wrapWidthString
	 *            The width to wrap received in String
	 * @return wrapWidth The width to wrap received in int
	 * @throws FmtException
	 *             If the wrapWidthString in not an integer or a negative
	 *             number.
	 */
	int checkWrapWidth(String wrapWidthString) throws FmtException {
		int wrapWidth;

		try {
			wrapWidth = Integer.parseInt(wrapWidthString);
		} catch (NumberFormatException e) {
			throw new FmtException(e, "Wrap width not a number");

		}
		if (wrapWidth < 1) {
			throw new FmtException("Wrap width should be at least 1");
		}
		return wrapWidth;
	}

	/**
	 * Wraps text to specified width without breaking words
	 * 
	 * @param stdout
	 *            An OutputStream. Write stringToWrite to this OutputStream.
	 * @param stringToWrite
	 *            The string to write to stdout
	 * @throws FmtException
	 *             If stdout is null or there is an error writing to stdout
	 */
	void writeToStdout(OutputStream stdout, String stringToWrite) throws FmtException {
		if (stdout == null) {
			throw new FmtException("Null pointer exception - stdout is not defined");
		}

		try {
			stdout.write(stringToWrite.getBytes(CHARSET_UTF_8));
		} catch (IOException e) {
			throw new FmtException(e);
		}
	}

	/**
	 * Reads from file
	 *
	 * @param filePath
	 *            A Path. Read file from the file path given.
	 * @return Concatenated string read from file
	 * @throws FmtException
	 *             If there is an error reading from the file
	 */
	String readFromFile(Path filePath) throws FmtException {
		String concatString = "";

		try {
			FileInputStream fileInStream = new FileInputStream(filePath.toString());
			BufferedReader buffReader = new BufferedReader(new InputStreamReader(fileInStream));

			String input = "";

			while ((input = buffReader.readLine()) != null) {
				concatString = concatString.concat(input+" ");
			}

			buffReader.close();
		} catch (IOException e) {
			throw new FmtException(e);
		}

		return concatString.trim();
	}

	/**
	 * Reads from stdin
	 *
	 * @param stdin
	 *            An InputStream. Read input from this InputStream.
	 * @return Concatenated string read from stdin
	 * @throws FmtException
	 *             If stdin is null or there is an error reading from stdin
	 */
	String readFromStdin(InputStream stdin) throws FmtException {
		String concatString = "";

		try {
			if (stdin == null) {
				throw new FmtException("Null pointer exception - stdin is not defined");
			}

			BufferedReader buffReader = new BufferedReader(new InputStreamReader(stdin));

			String input = "";

			while ((input = buffReader.readLine()) != null) {
				concatString = concatString.concat(input+" ");
			}

			buffReader.close();
		} catch (Exception e) {
			throw new FmtException(e);
		}
		return concatString.trim();
	}

	/**
	 * Checks if a file is readable
	 * 
	 * @param filePath
	 *            The path to the file
	 * @return True if the file is readable
	 * @throws FmtException
	 *             If the file is not readable
	 */
	boolean checkIfFileIsReadable(Path filePath) throws FmtException {
		if (Files.isDirectory(filePath)) {
			throw new FmtException("This is a directory");
		}
		if (Files.isReadable(filePath)) {
			return true;
		} else {
			throw new FmtException("Could not read file");
		}
	}
}

// References

// http://stackoverflow.com/questions/6684665/java-byte-array-to-string-to-byte-array
// Question by : http://stackoverflow.com/users/843387/0909em
// Answer by : http://stackoverflow.com/users/320700/yanick-rochon

// http://stackoverflow.com/questions/593671/remove-end-of-line-characters-from-java-string

// http://www.mkyong.com/java/how-to-get-the-standard-input-in-java/

// Check emtpy string
// http://stackoverflow.com/questions/3598770/java-check-whether-a-string-is-not-null-and-not-empty
// Question by : http://stackoverflow.com/users/405398/xyz
// Answer by : http://stackoverflow.com/users/422597/colin-hebert
