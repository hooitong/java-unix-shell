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

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.app.Comm;
import sg.edu.nus.comp.cs4218.exception.CommException;
import sg.edu.nus.comp.cs4218.exception.SortException;
import sg.edu.nus.comp.cs4218.misc.LineComparison;

public class CommApplication implements Comm {

	private static final int ZERO = 0;
	private static final int ONE = 1;
	private static final int TWO = 2;
	private static final int COL_ZERO = 0;
	private static final int COL_ONE = 1;
	private static final int COL_TWO = 2;
	private static final String CHARSET_UTF_8 = "UTF-8";
	private static final String NEW_LINE = System.lineSeparator();
	private static final String TAB_LINE = "\t";

	/**
	 * Returns string to print comparisons when there are no matches in both
	 * files
	 * 
	 * @throws CommException
	 */
	@Override
	public String commNoMatches(String... args) {
		String[] firstColArr = commOnlyFirst(args).split(NEW_LINE);
		String[] middleColArr = commOnlySecond(args).split(NEW_LINE);
		StringBuilder stringBuilder = new StringBuilder("");
		for (int i = 0; i < firstColArr.length - 1; i++) {
			stringBuilder.append(firstColArr[i]).append(middleColArr[i]).append(NEW_LINE);
		}
		stringBuilder.append(firstColArr[firstColArr.length - 1]).append(middleColArr[firstColArr.length - 1]);

		return stringBuilder.toString();
	}

	/**
	 * Returns string to print comparisons when there are only lines in the
	 * first file to match
	 * 
	 * @throws CommException
	 */
	@Override
	public String commOnlyFirst(String... args) {
		StringBuilder stringBuilder = new StringBuilder("");

		ArrayList<ArrayList<String>> mainList;
		try {
			mainList = getContentFromStdinOrFile(args);
			ArrayList<String> strList1 = mainList.get(ZERO);
			ArrayList<String> strList2 = mainList.get(ONE);
			String[] temp = strList1.toArray(new String[strList1.size()]);
			LineComparison lineCompare = new LineComparison(strList1, strList2);
			ArrayList<String> firstColAl = lineCompare.compareLines().get(COL_ZERO);
			for (int i = 0; i < firstColAl.size() - 1; i++) {
				stringBuilder.append(firstColAl.get(i)).append(NEW_LINE);
			}
			stringBuilder.append(firstColAl.get(firstColAl.size() - 1));
		} catch (CommException e) {
			e.printStackTrace();
		}
		return stringBuilder.toString();
	}

	/**
	 * Returns string to print comparisons when there are only lines in the
	 * second file to match
	 * 
	 * @throws CommException
	 */
	@Override
	public String commOnlySecond(String... args) {
		StringBuilder stringBuilder = new StringBuilder("");
		ArrayList<ArrayList<String>> mainList;
		try {
			mainList = getContentFromStdinOrFile(args);
			ArrayList<String> strList1 = mainList.get(ZERO);
			ArrayList<String> strList2 = mainList.get(ONE);
			LineComparison lineCompare = new LineComparison(strList1, strList2);
			ArrayList<String> middleColAl = lineCompare.compareLines().get(COL_ONE);

			for (int i = 0; i < middleColAl.size() - 1; i++) {
				stringBuilder.append(middleColAl.get(i)).append(NEW_LINE);
			}
			stringBuilder.append(middleColAl.get(middleColAl.size() - 1));

		} catch (CommException e) {
			e.printStackTrace();
		}

		return stringBuilder.toString();
	}

	/**
	 * Returns string to print comparisons when some of the lines match
	 * 
	 * @throws CommException
	 */
	@Override
	public String commBothMathches(String... args) {
		String[] firstTwoCols = commNoMatches(args).split(NEW_LINE);
		String[] lastCol = commAllMatches(args).split(NEW_LINE);
		StringBuilder stringBuilder = new StringBuilder("");
		for (int i = 0; i < firstTwoCols.length - 1; i++) {
			stringBuilder.append(firstTwoCols[i]).append(lastCol[i]).append(NEW_LINE);
		}
		stringBuilder.append(firstTwoCols[firstTwoCols.length - 1]).append(lastCol[firstTwoCols.length - 1]);
		return stringBuilder.toString();
	}

	/**
	 * Returns string to print comparisons when there are all matches in both
	 * files
	 * 
	 * @throws CommException
	 */
	@Override
	public String commAllMatches(String... args) {
		ArrayList<ArrayList<String>> mainList;
		StringBuilder stringBuilder = new StringBuilder("");
		try {
			mainList = getContentFromStdinOrFile(args);
			ArrayList<String> strList1 = mainList.get(ZERO);
			ArrayList<String> strList2 = mainList.get(ONE);
			LineComparison lineCompare = new LineComparison(strList1, strList2);
			ArrayList<String> lastColAl = lineCompare.compareLines().get(COL_TWO);

			for (int i = 0; i < lastColAl.size() - 1; i++) {
				stringBuilder.append(lastColAl.get(i)).append(NEW_LINE);
			}
			stringBuilder.append(lastColAl.get(lastColAl.size() - 1));
		} catch (CommException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return stringBuilder.toString();
	}

	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws CommException {

		StringBuilder stringBuilder = new StringBuilder("");
		ArrayList<ArrayList<String>> mainList = getContentFromStdinOrFile(args, stdin);
		ArrayList<String> strList1 = mainList.get(ZERO);
		ArrayList<String> strList2 = mainList.get(ONE);
		LineComparison lineCompare = new LineComparison(strList1, strList2);
		ArrayList<ArrayList<String>> resultAl = lineCompare.compareLines();
		ArrayList<String> firstColAl = resultAl.get(ZERO);
		ArrayList<String> secondColAl = resultAl.get(ONE);
		ArrayList<String> thirdColAl = resultAl.get(TWO);

		for (int i = 0; i < firstColAl.size() - 1; i++) {
			stringBuilder.append(firstColAl.get(i)).append(secondColAl.get(i)).append(thirdColAl.get(i))
					.append(NEW_LINE);
		}
		stringBuilder.append(firstColAl.get(firstColAl.size() - 1)).append(secondColAl.get(secondColAl.size() - 1))
				.append(thirdColAl.get(thirdColAl.size() - 1));
		stdoutString(stdout, stringBuilder.toString());
	}

	/**
	 * This method returns ArrayList<ArrayList<String>> containing contents of
	 * both files in the arguments
	 * 
	 * @param args
	 *            initial arguments
	 * @param stdin
	 *            inputstream to be read for contents
	 * @return ArrayList<ArrayList<String>> index zero returns an arraylist of
	 *         string from file1 index one returns an arraylist of string from
	 *         file2
	 * @throws CommException
	 */
	private ArrayList<ArrayList<String>> getContentFromStdinOrFile(String[] args, InputStream stdin)
			throws CommException {
		Path currentDir = Paths.get(Environment.currentDirectory);
		int file1Position = ZERO;
		int file2Position = ZERO;
		ArrayList<ArrayList<String>> mainList = new ArrayList<ArrayList<String>>();
		ArrayList<String> strList1, strList2;

		if (args == null || args.length == ZERO) {
			throw new CommException("Length of arguments 0 or null");
		} else if (args.length == ONE) {
			file2Position = ZERO;
			strList1 = readFromStdinAndWriteToStringList(stdin);
			strList2 = getFileContents(args, currentDir, file2Position);
		} else if (args.length == TWO) {
			file1Position = ZERO;
			file2Position = ONE;
			strList1 = getFileContents(args, currentDir, file1Position);
			strList2 = getFileContents(args, currentDir, file2Position);
		} else {
			throw new CommException("Length of arguments cannot be greater than 2");
		}
		mainList.add(strList1);
		mainList.add(strList2);
		return mainList;
	}

	/**
	 * This method returns ArrayList<ArrayList<String>> containing contents of
	 * both files in the arguments
	 * 
	 * @param args
	 *            initial arguments
	 * @return index zero returns an arraylist of string from file1, index one
	 *         returns an arraylist of string from file2
	 * @throws CommException
	 */
	private ArrayList<ArrayList<String>> getContentFromStdinOrFile(String... args) throws CommException {
		Path currentDir = Paths.get(Environment.currentDirectory);
		int file1Position = ZERO;
		int file2Position = ZERO;
		ArrayList<ArrayList<String>> mainList = new ArrayList<ArrayList<String>>();
		ArrayList<String> strList1 = new ArrayList<String>();
		ArrayList<String> strList2 = new ArrayList<String>();

		if (args == null || args.length == ZERO) {
			throw new CommException("Length of arguments 0 or null");
		} else if (args.length == ONE) {
			throw new CommException(
					"stdin can only be tested using run method as interface's parameters are not allowed to be changed due to project requirements");
		} else if (args.length == TWO) {
			file1Position = ZERO;
			file2Position = ONE;
			strList1 = getFileContents(args, currentDir, file1Position);
			strList2 = getFileContents(args, currentDir, file2Position);
		} else {
			throw new CommException("Legnth of arguments cannot be greater than 2");
		}
		mainList.add(strList1);
		mainList.add(strList2);
		return mainList;
	}

	/**
	 * Read from file and return a string array
	 * 
	 * @param filePath
	 *            A Path. Read file from the file path given
	 * @throws CommException
	 *             Exceptions caught when reading and writing from input file.
	 */
	ArrayList<String> readFromFileAndWriteToStringList(Path filePath) throws CommException {
		ArrayList<String> resultList = new ArrayList<String>();
		try {
			FileInputStream fileInStream = new FileInputStream(filePath.toString());
			BufferedReader buffReader = new BufferedReader(new InputStreamReader(fileInStream));

			String input = "";
			while ((input = buffReader.readLine()) != null) {
				resultList.add(input);
			}
			buffReader.close();

		} catch (IOException e) {
			throw new CommException("IOException", e);
		}
		return resultList;
	}

	/**
	 * Checks if a file is readable.
	 * 
	 * @param filePath
	 *            The path to the file
	 * @return True if the file is readable.
	 * @throws CommException
	 *             If the file is not readable
	 */
	void catchIfFileIsReadableException(Path filePath) throws CommException {
		if (!Files.exists(filePath) && !Files.isReadable(filePath)) {
			throw new CommException("Could not read file");
		}
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
	 * @throws CommException
	 *             If the 'n' flag is missing in the command format
	 */
	private ArrayList<String> getFileContents(String[] args, Path currentDir, int filePosition) throws CommException {
		Path filePath = currentDir.resolve(args[filePosition]);
		catchIfFileIsReadableException(filePath);
		return readFromFileAndWriteToStringList(filePath);
	}

	/**
	 * Read from stdin and write to a string array
	 * 
	 * @param stdin
	 *            An input Stream. Reading from stdin and not a file
	 * @throws CommException
	 *             If stdin. I/O exceptions caught when reading and writing from
	 *             input and output streams.
	 */

	private ArrayList<String> readFromStdinAndWriteToStringList(InputStream stdin) throws CommException {
		ArrayList<String> arrayList = new ArrayList<String>();
		if (stdin == null) {
			throw new CommException("Null Pointer Exception");
		}
		BufferedReader buffReader = new BufferedReader(new InputStreamReader(stdin));
		String input = "";
		try {
			while ((input = buffReader.readLine()) != null) {
				arrayList.add(input);
			}
		} catch (Exception e) {
			throw new CommException("Exception caught", e);
		}
		return arrayList;
	}

	private void stdoutString(OutputStream stdout, String resultStr) throws CommException {
		if (stdout == null) {
			throw new CommException("stdout is not present");
		}
		try {
			stdout.write(resultStr.getBytes(CHARSET_UTF_8));
		} catch (IOException e) {
			throw new CommException("Could not write to output stream", e);
		}

	}

}