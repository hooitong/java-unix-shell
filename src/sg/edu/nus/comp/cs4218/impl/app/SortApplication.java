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
import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.app.Sort;
import sg.edu.nus.comp.cs4218.exception.SortException;
import sg.edu.nus.comp.cs4218.misc.MergeSort;
import sg.edu.nus.comp.cs4218.misc.SortHelper;

public class SortApplication implements Sort {

	private static final int MAX_LENGTH = 2;
	private static final int ONE = 1;
	private static final int ZERO = 0;
	private static final int TWO = 2;
	private static final int THREE = 3;
	private static final int FOUR = 4;
	private static final String CHARSET_UTF_8 = "UTF-8";

	/**
	 * Returns an ordered list of lines containing only simple letters
	 */
	@Override
	public List<String> sortStringsSimple(String... toSort) {
		List<String> extractedList = new ArrayList<String>(SortHelper.separateBasedOnType(ONE, toSort));
		return SortHelper.sortHelper(extractedList);
	}

	/**
	 * Returns an ordered list of lines containing only capital letters
	 */
	@Override
	public List<String> sortStringsCapital(String... toSort) {
		List<String> extractedList = new ArrayList<String>(SortHelper.separateBasedOnType(ONE, toSort));
		return SortHelper.sortHelper(extractedList);
	}

	/**
	 * Returns an ordered list of lines containing only numbers in natural order
	 */
	@Override
	public List<String> sortNumbers(String... toSort) {
		List<String> extractedList = new ArrayList<String>(SortHelper.separateBasedOnType(ONE, toSort));
		return SortHelper.sortHelper(extractedList);
	}

	/**
	 * Returns an ordered list of lines containing only numbers in asc order
	 */
	@Override
	public List<String> sortNumbersWithNumFlagOn(String... toSort) {
		List<String> extractedList = new ArrayList<String>(SortHelper.separateBasedOnType(ONE, toSort));
		return SortHelper.sortHelperWithNumFlag(extractedList);
	}

	/**
	 * Returns an ordered list of lines containing only special characters
	 */
	@Override
	public List<String> sortSpecialChars(String... toSort) {
		List<String> extractedList = new ArrayList<String>(SortHelper.separateBasedOnType(ONE, toSort));
		return SortHelper.sortHelper(extractedList);
	}

	/**
	 * Returns an ordered list of lines containing simple and capital letters
	 */
	public List<String> sortSimpleCapital(String... toSort) {
		List<String> extractedList = new ArrayList<String>(SortHelper.separateBasedOnType(TWO, toSort));
		return SortHelper.sortHelper(extractedList);
	}

	/**
	 * Returns an ordered list of lines containing simple letters and numbers
	 */
	@Override
	public List<String> sortSimpleNumbers(String... toSort) {
		List<String> extractedList = new ArrayList<String>(SortHelper.separateBasedOnType(TWO, toSort));
		return SortHelper.sortHelper(extractedList);
	}

	/**
	 * Returns an ordered list of lines containing simple letters and numbers
	 * with numbers in asc order
	 */
	@Override
	public List<String> sortSimpleNumbersWithNumFlagOn(String... toSort) {
		List<String> extractedList = new ArrayList<String>(SortHelper.separateBasedOnType(TWO, toSort));
		return SortHelper.sortHelperWithNumFlag(extractedList);
	}

	/**
	 * Returns an ordered list of lines containing simple letters and special
	 * characters
	 */
	@Override
	public List<String> sortSimpleSpecialChars(String... toSort) {
		List<String> extractedList = new ArrayList<String>(SortHelper.separateBasedOnType(TWO, toSort));
		return SortHelper.sortHelper(extractedList);
	}

	/**
	 * Returns an ordered list of lines containing capital letters and numbers
	 */
	@Override
	public List<String> sortCapitalNumbers(String... toSort) {
		List<String> extractedList = new ArrayList<String>(SortHelper.separateBasedOnType(TWO, toSort));
		return SortHelper.sortHelper(extractedList);
	}

	/**
	 * Returns an ordered list of lines containing capital letters and numbers
	 * in ascending order
	 */
	@Override
	public List<String> sortCapitalNumberswithNumFlagOn(String... toSort) {
		List<String> extractedList = new ArrayList<String>(SortHelper.separateBasedOnType(TWO, toSort));
		return SortHelper.sortHelperWithNumFlag(extractedList);
	}

	/**
	 * Returns an ordered list of lines containing capital letters and special
	 * character
	 */
	@Override
	public List<String> sortCapitalSpecialChars(String... toSort) {
		List<String> extractedList = new ArrayList<String>(SortHelper.separateBasedOnType(TWO, toSort));
		return SortHelper.sortHelper(extractedList);
	}

	/**
	 * Returns an ordered list of lines containing numbers and special
	 * characters
	 */
	@Override
	public List<String> sortNumbersSpecialChars(String... toSort) {
		List<String> extractedList = new ArrayList<String>(SortHelper.separateBasedOnType(TWO, toSort));
		return SortHelper.sortHelper(extractedList);
	}

	/**
	 * Returns an ordered list of lines containing numbers and special
	 * characters
	 */
	@Override
	public List<String> sortNumbersSpecialCharsWithNumFlagOn(String... toSort) {
		List<String> extractedList = new ArrayList<String>(SortHelper.separateBasedOnType(TWO, toSort));
		return SortHelper.sortHelperWithNumFlag(extractedList);
	}

	/**
	 * Returns an ordered list of lines containing simple and capital letters
	 * and numbers
	 */
	@Override
	public List<String> sortSimpleCapitalNumber(String... toSort) {
		List<String> extractedList = new ArrayList<String>(SortHelper.separateBasedOnType(THREE, toSort));
		return SortHelper.sortHelper(extractedList);
	}

	/**
	 * Returns an ordered list of lines containing simple and capital letters
	 * and numbers
	 */
	@Override
	public List<String> sortSimpleCapitalNumberWithNumFlagOn(String... toSort) {
		List<String> extractedList = new ArrayList<String>(SortHelper.separateBasedOnType(THREE, toSort));
		return SortHelper.sortHelperWithNumFlag(extractedList);
	}

	/**
	 * Returns an ordered list of lines containing simple and capital letters
	 * and special characters
	 */
	@Override
	public List<String> sortSimpleCapitalSpecialChars(String... toSort) {
		List<String> extractedList = new ArrayList<String>(SortHelper.separateBasedOnType(THREE, toSort));
		return SortHelper.sortHelper(extractedList);
	}

	/**
	 * Returns an ordered list of lines containing simple letters, numbers and
	 * special characters
	 */
	@Override
	public List<String> sortSimpleNumbersSpecialChars(String... toSort) {
		List<String> extractedList = new ArrayList<String>(SortHelper.separateBasedOnType(THREE, toSort));
		return SortHelper.sortHelper(extractedList);
	}

	/**
	 * Returns an ordered list of lines containing simple letters, numbers and
	 * special characters in asc order
	 */
	@Override
	public List<String> sortSimpleNumbersSpecialCharsWithNumFlagOn(String... toSort) {
		List<String> extractedList = new ArrayList<String>(SortHelper.separateBasedOnType(THREE, toSort));
		return SortHelper.sortHelperWithNumFlag(extractedList);
	}

	/**
	 * Returns an ordered list of lines containing capital letters, numbers and
	 * special characters
	 */
	@Override
	public List<String> sortCapitalNumbersSpecialChars(String... toSort) {
		List<String> extractedList = new ArrayList<String>(SortHelper.separateBasedOnType(THREE, toSort));
		return SortHelper.sortHelper(extractedList);
	}

	/**
	 * Returns an ordered list of lines containing capital letters, numbers and
	 * special characters
	 */
	@Override
	public List<String> sortCapitalNumbersSpecialCharsWithNumFlagOn(String... toSort) {
		List<String> extractedList = new ArrayList<String>(SortHelper.separateBasedOnType(THREE, toSort));
		return SortHelper.sortHelperWithNumFlag(extractedList);
	}

	/**
	 * Returns an ordered list of lines containing simple and capital letters,
	 * numbers and special characters
	 */
	@Override
	public List<String> sortAll(String... toSort) {
		List<String> extractedList = new ArrayList<String>(SortHelper.separateBasedOnType(FOUR, toSort));
		return SortHelper.sortHelper(extractedList);
	}

	/**
	 * Returns an ordered list of lines containing simple and capital letters,
	 * numbers and special characters in asc order
	 */
	@Override
	public List<String> sortAllWithNumFlagOn(String... toSort) {
		List<String> extractedList = new ArrayList<String>(SortHelper.separateBasedOnType(FOUR, toSort));
		return SortHelper.sortHelperWithNumFlag(extractedList);
	}

	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws SortException {
		Path currentDir = Paths.get(Environment.currentDirectory);
		int filePosition = ZERO;
		String[] toSort = null;
		boolean numFlag = false;

		if (args == null || args.length == ZERO) {
			toSort = readFromStdinAndWriteToStringArray(stdin);
		} else if (args.length == ONE) {
			filePosition = ZERO;
			if (isNumberCommandFormat(args)) {
				numFlag = true;
				toSort = readFromStdinAndWriteToStringArray(stdin);
			} else {
				toSort = getFileContents(args, currentDir, filePosition);
			}
		} else if (args.length == MAX_LENGTH) {
			catchMissingNumberCommandFormatException(args);
			numFlag = true;
			filePosition = ONE;
			toSort = getFileContents(args, currentDir, filePosition);

		} else {
			throw new SortException("Arguments cannot be greater than 2");
		}
		MergeSort mergeSort = new MergeSort(numFlag);
		mergeSort.mergeSort(toSort, ZERO, toSort.length - 1);
		stdoutSortedArray(stdout, toSort);
	}

	/**
	 * write the sorted array to the outstream
	 * 
	 * @param outputstream
	 *            stream to write out
	 * @param string
	 *            array sorted array
	 * @throws SortException
	 */
	private void stdoutSortedArray(OutputStream stdout, String... toSort) throws SortException {
		for (int i = 0; i < toSort.length; i++) {
			try {
				stdout.write(toSort[i].getBytes(CHARSET_UTF_8));
				stdout.write(System.lineSeparator().getBytes("UTF-8"));
			} catch (IOException e) {
				throw new SortException("Could not write to output stream", e);
			}
		}
	}

	/**
	 * Read from stdin and write to a string array
	 * 
	 * @param stdin
	 *            An input Stream. Reading from stdin and not a file
	 * @throws SortException
	 *             If stdin. I/O exceptions caught when reading and writing from
	 *             input and output streams.
	 */

	private String[] readFromStdinAndWriteToStringArray(InputStream stdin) throws SortException {
		List<String> resultList = new ArrayList<String>();
		if (stdin == null) {
			throw new SortException("Null Pointer Exception");
		}
		BufferedReader buffReader = new BufferedReader(new InputStreamReader(stdin));
		String input = "";
		try {
			while ((input = buffReader.readLine()) != null) {
				resultList.add(input);
			}
		} catch (Exception e) {
			throw new SortException("Exception caught", e);
		}
		return resultList.toArray(new String[resultList.size()]);
	}

	/**
	 * Catch the missing 'n' flag when the argument length is of 2
	 * 
	 * @param args
	 *            arguments present in the command
	 * @param currentDir
	 *            path where the source file resides
	 * @param filePosition
	 *            position of the filename in the args
	 * @return void
	 * @throws SortException
	 *             If the 'n' flag is missing in the command format
	 */
	private String[] getFileContents(String[] args, Path currentDir, int filePosition) throws SortException {
		Path filePath = currentDir.resolve(args[filePosition]);
		catchIfFileIsReadableException(filePath);
		return readFromFileAndWriteToStringArray(filePath);
	}

	/**
	 * Catch the missing 'n' flag when the argument length is of 2
	 * 
	 * @param args
	 *            arguments present in the command
	 * @return void
	 * @throws SortException
	 *             If the 'n' flag is missing in the command format
	 */
	private void catchMissingNumberCommandFormatException(String... args) throws SortException {
		if (!isNumberCommandFormat(args)) {
			throw new SortException("only -n command is allowed");
		}
	}

	/**
	 * Checks if the '-n' flag is present in the command format
	 * 
	 * @param args
	 *            arguments present in the command
	 * @return True if the 'n' flag is present.
	 * @throws SortException
	 *             If the file is not readable
	 */
	private boolean isNumberCommandFormat(String... args) {
		return args[ZERO].equals("-n");
	}

	/**
	 * Checks if a file is readable.
	 * 
	 * @param filePath
	 *            The path to the file
	 * @return True if the file is readable.
	 * @throws SortException
	 *             If the file is not readable
	 */
	void catchIfFileIsReadableException(Path filePath) throws SortException {
		if (!Files.exists(filePath) && !Files.isReadable(filePath)) {
			throw new SortException("Could not read file");
		}
	}

	/**
	 * Read from file and return a string array
	 * 
	 * @param filePath
	 *            A Path. Read file from the file path given
	 * @throws SortException
	 *             Exceptions caught when reading and writing from input file.
	 */
	String[] readFromFileAndWriteToStringArray(Path filePath) throws SortException {
		List<String> arrayList = new ArrayList<String>();
		try {
			FileInputStream fileInStream = new FileInputStream(filePath.toString());
			BufferedReader buffReader = new BufferedReader(new InputStreamReader(fileInStream));

			String input = "";
			while ((input = buffReader.readLine()) != null) {
				arrayList.add(input);
			}
			buffReader.close();

		} catch (IOException e) {
			throw new SortException("IOException", e);
		}
		return arrayList.toArray(new String[arrayList.size()]);
	}
}
