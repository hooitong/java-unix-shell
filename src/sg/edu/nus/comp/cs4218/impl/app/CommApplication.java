package sg.edu.nus.comp.cs4218.impl.app;

import sg.edu.nus.comp.cs4218.app.Comm;
import sg.edu.nus.comp.cs4218.misc.MergeSort;
import sg.edu.nus.comp.cs4218.exception.CommException;
import sg.edu.nus.comp.cs4218.exception.CommException;
import sg.edu.nus.comp.cs4218.exception.SortException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.app.Comm;
import sg.edu.nus.comp.cs4218.Environment;

public class CommApplication implements Comm {

	private static final int ZERO = 0;
	private static final int ONE = 1;
	private static final int TWO = 2;
	private static final int MAX_LENGTH_ONE = 1;
	private static final int COL_ZERO = 0;
	private static final int COL_ONE = 1;
	private static final int COL_TWO = 2;
	private static final String CHARSET_UTF_8 = "UTF-8";
	private static final String NEW_LINE = System.lineSeparator();
	private static final String TAB_LINE = "\t";

	public CommApplication() {
	}

	/**
	 * Returns string to print comparisons when there are no matches in both
	 * files
	 * 
	 * @throws CommException
	 */
	@Override
	public String commNoMatches(String[] args, InputStream stdin)
			throws CommException {
		String[] firstColArr = commOnlyFirst(args, stdin).split(NEW_LINE);
		String[] middleColArr = commOnlySecond(args, stdin).split(NEW_LINE);
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < firstColArr.length - 1; i++) {
			sb.append(firstColArr[i]).append(middleColArr[i]).append(NEW_LINE);
		}
		sb.append(firstColArr[firstColArr.length - 1]).append(
				middleColArr[firstColArr.length - 1]);

		return sb.toString();
	}

	/**
	 * Returns string to print comparisons when there are only lines in the
	 * first file to match
	 * 
	 * @throws CommException
	 */
	@Override
	public String commOnlyFirst(String[] args, InputStream stdin)
			throws CommException {
		ArrayList<ArrayList<String>> mainList = getContentFromStdinOrFile(args,
				stdin);
		ArrayList<String> strList1 = mainList.get(ZERO);
		ArrayList<String> strList2 = mainList.get(ONE);
		String[] temp = strList1.toArray(new String[strList1.size()]);
		ArrayList<String> firstColAl = linesComparison(strList1, strList2).get(
				COL_ZERO);
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < firstColAl.size() - 1; i++) {
			sb.append(firstColAl.get(i)).append(NEW_LINE);
		}
		sb.append(firstColAl.get(firstColAl.size() - 1));

		return sb.toString();
	}

	/**
	 * Returns string to print comparisons when there are only lines in the
	 * second file to match
	 * 
	 * @throws CommException
	 */
	@Override
	public String commOnlySecond(String[] args, InputStream stdin)
			throws CommException {
		ArrayList<ArrayList<String>> mainList = getContentFromStdinOrFile(args,
				stdin);
		ArrayList<String> strList1 = mainList.get(ZERO);
		ArrayList<String> strList2 = mainList.get(ONE);
		ArrayList<String> middleColAl = linesComparison(strList1, strList2)
				.get(COL_ONE);
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < middleColAl.size() - 1; i++) {
			sb.append(middleColAl.get(i)).append(NEW_LINE);
		}
		sb.append(middleColAl.get(middleColAl.size() - 1));
		return sb.toString();
	}

	/**
	 * Returns string to print comparisons when some of the lines match
	 * 
	 * @throws CommException
	 */
	@Override
	public String commBothMathches(String[] args, InputStream stdin)
			throws CommException {
		String[] firstTwoCols = commNoMatches(args, stdin).split(
				NEW_LINE);
		String[] lastCol = commAllMatches(args, stdin).split(NEW_LINE);
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < firstTwoCols.length - 1; i++) {
			sb.append(firstTwoCols[i]).append(lastCol[i]).append(NEW_LINE);
		}
		sb.append(firstTwoCols[firstTwoCols.length - 1]).append(
				lastCol[firstTwoCols.length - 1]);
		return sb.toString();
	}

	/**
	 * Returns string to print comparisons when there are all matches in both
	 * files
	 * 
	 * @throws CommException
	 */
	@Override
	public String commAllMatches(String[] args, InputStream stdin)
			throws CommException {
		ArrayList<ArrayList<String>> mainList = getContentFromStdinOrFile(args,
				stdin);
		ArrayList<String> strList1 = mainList.get(ZERO);
		ArrayList<String> strList2 = mainList.get(ONE);
		ArrayList<String> lastColAl = linesComparison(strList1, strList2).get(
				COL_TWO);
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < lastColAl.size() - 1; i++) {
			sb.append(lastColAl.get(i)).append(NEW_LINE);
		}
		sb.append(lastColAl.get(lastColAl.size() - 1));
		return sb.toString();
	}

	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout)
			throws CommException {

		String resultStr = commBothMathches(args, stdin);
		stdoutString(stdout, resultStr);
	}

	private ArrayList<ArrayList<String>> getContentFromStdinOrFile(
			String[] args, InputStream stdin) throws CommException {
		Path currentDir = Paths.get(Environment.currentDirectory);
		int file1Position = ZERO;
		int file2Position = ZERO;
		ArrayList<ArrayList<String>> mainList = new ArrayList<ArrayList<String>>();
		ArrayList<String> strList1 = new ArrayList<String>();
		ArrayList<String> strList2 = new ArrayList<String>();

		if (args.length == ONE) {
			file2Position = ZERO;
			strList1 = readFromStdinAndWriteToStringList(stdin);
			strList2 = getFileContents(args, currentDir, file2Position);
		} else if (args.length == TWO) {
			file1Position = ZERO;
			file2Position = ONE;
			strList1 = getFileContents(args, currentDir, file1Position);
			strList2 = getFileContents(args, currentDir, file2Position);
		} else {
			throw new CommException(
					"Legnth of arguments cannot be greater than 1");
		}
		mainList.add(strList1);
		mainList.add(strList2);
		return mainList;
	}

	private ArrayList<ArrayList<String>> getContentFromStdinOrFile(String[] args)
			throws CommException {
		Path currentDir = Paths.get(Environment.currentDirectory);
		int file1Position = ZERO;
		int file2Position = ZERO;
		ArrayList<ArrayList<String>> mainList = new ArrayList<ArrayList<String>>();
		ArrayList<String> strList1 = new ArrayList<String>();
		ArrayList<String> strList2 = new ArrayList<String>();
		mainList.add(strList1);
		mainList.add(strList2);

		if (args == null || args.length == ZERO) {
			file1Position = ONE;
			// strList1 = readFromStdinAndWriteToStringList(stdin);
			strList2 = getFileContents(args, currentDir, file2Position);
		} else if (args.length == TWO) {
			file1Position = ZERO;
			file2Position = ONE;
			strList1 = getFileContents(args, currentDir, file1Position);
			strList2 = getFileContents(args, currentDir, file2Position);
		} else {
			throw new CommException(
					"Legnth of arguments cannot be greater than 1");
		}
		return mainList;
	}

	public ArrayList<ArrayList<String>> linesComparison(
			ArrayList<String> strList1, ArrayList<String> strList2) {
		int indexOfLineFromFile1 = 0;
		int indexOfLineFromFile2 = 0;
		ArrayList<ArrayList<String>> mainAl = new ArrayList<ArrayList<String>>();
		mainAl.add(new ArrayList<String>());
		mainAl.add(new ArrayList<String>());
		mainAl.add(new ArrayList<String>());
		while (true) {
			int result = 0;
			String lineA = "";
			String lineB = "";

			lineA = getLineFromList(strList1, indexOfLineFromFile1);
			lineB = getLineFromList(strList2, indexOfLineFromFile2);

			if (lineA.length() == 0 && lineB.length() == 0) {
				break;
			} else if (lineA.length() == 0 && lineB.length() != 0) {
				// sb.append(TAB_LINE + lineB + TAB_LINE + NEW_LINE);
				mainAl.get(COL_ZERO).add(TAB_LINE);
				mainAl.get(COL_ONE).add(lineB);
				mainAl.get(COL_TWO).add(TAB_LINE);
				indexOfLineFromFile2++;
			} else if (lineA.length() != 0 && lineB.length() == 0) {
				// sb.append(lineA + TAB_LINE + TAB_LINE + NEW_LINE);
				mainAl.get(COL_ZERO).add(lineA);
				mainAl.get(COL_ONE).add(TAB_LINE);
				mainAl.get(COL_TWO).add(TAB_LINE);
				indexOfLineFromFile1++;
			} else {// compare lines that are not empty
				result = lineA.toString().compareTo(lineB.toString());
				if (result == ZERO) {
					// sb.append(TAB_LINE + TAB_LINE + lineA + NEW_LINE);
					mainAl.get(COL_ZERO).add(TAB_LINE);
					mainAl.get(COL_ONE).add(TAB_LINE);
					mainAl.get(COL_TWO).add(lineA);
					indexOfLineFromFile1++;
					indexOfLineFromFile2++;

				} else if (result < ZERO) {// output to first col
					// sb.append(lineA + TAB_LINE + TAB_LINE + NEW_LINE);
					mainAl.get(COL_ZERO).add(lineA);
					mainAl.get(COL_ONE).add(TAB_LINE);
					mainAl.get(COL_TWO).add(TAB_LINE);
					indexOfLineFromFile1++;
				} else {// output to second column
					// sb.append(TAB_LINE + lineB + TAB_LINE + NEW_LINE);
					mainAl.get(COL_ZERO).add(TAB_LINE);
					mainAl.get(COL_ONE).add(lineB);
					mainAl.get(COL_TWO).add(TAB_LINE);
					indexOfLineFromFile2++;
				}
			}
		}
		return mainAl;
	}

	private String getLineFromList(ArrayList<String> strList1,
			int indexOfLineFromFile1) {
		String currentLine = "";
		if (strList1.size() != 0 && indexOfLineFromFile1 < strList1.size()) {
			currentLine = strList1.get(indexOfLineFromFile1);
		}
		return currentLine;
	}

	/**
	 * Read from file and return a string array
	 * 
	 * @param filePath
	 *            A Path. Read file from the file path given
	 * @throws CommException
	 *             Exceptions caught when reading and writing from input file.
	 */
	ArrayList<String> readFromFileAndWriteToStringList(Path filePath)
			throws CommException {
		ArrayList<String> al = new ArrayList<String>();
		try {
			FileInputStream fileInStream = new FileInputStream(
					filePath.toString());
			BufferedReader buffReader = new BufferedReader(
					new InputStreamReader(fileInStream));

			String input = "";
			while ((input = buffReader.readLine()) != null) {
				al.add(input);
			}
			buffReader.close();

		} catch (IOException e) {
			throw new CommException("IOException");
		}
		return al;
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
	private ArrayList<String> getFileContents(String[] args, Path currentDir,
			int filePosition) throws CommException {
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

	private ArrayList<String> readFromStdinAndWriteToStringList(
			InputStream stdin) throws CommException {
		ArrayList<String> al = new ArrayList<String>();
		if (stdin == null) {
			throw new CommException("Null Pointer Exception");
		}
		BufferedReader buffReader = new BufferedReader(new InputStreamReader(
				stdin));
		String input = "";
		try {
			while ((input = buffReader.readLine()) != null) {
				al.add(input);
			}
		} catch (Exception e) {
			throw new CommException("Exception caught");
		}
		return al;
	}

	private void stdoutString(OutputStream stdout, String resultStr)
			throws CommException {
		try {
			stdout.write(resultStr.getBytes(CHARSET_UTF_8));
		} catch (IOException e) {
			throw new CommException("Could not write to output stream");
		}

	}

}