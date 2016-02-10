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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.app.Sort;
import sg.edu.nus.comp.cs4218.exception.SortException;

public class SortApplication implements Sort, Application{
	
	public static final int MAX_LENGTH = 2;
	public static final int ONE = 1;
	public static final int ZERO = 0;
	private static final String CHARSET_UTF_8 = "UTF-8";
	
	/**
	 * Returns an ordered list of lines containing only simple letters
	 */
	@Override
	public List<String> sortStringsSimple(String[] toSort){
		List<String> al = new ArrayList<String>();
		for (int i = 0; i < toSort.length; i++) {
			String currentLine = toSort[i];
			if(checkForSimpleInLine(currentLine)){
				al.add(currentLine);
			}
		}
		Collections.sort(al);
		return al;
	}

	/**
	 * Returns an ordered list of lines containing only capital letters
	 */
	@Override
	public List<String> sortStringsCapital(String[] toSort){
		List<String> al = new ArrayList<String>();
		for (int i = 0; i < toSort.length; i++) {
			String currentLine = toSort[i];
			if(checkForCapitalInLine(currentLine)){
				al.add(currentLine);
			}
		}
		Collections.sort(al);
		return al;
	}

	/**
	 * Returns an ordered list of lines containing only numbers in natural order
	 */
	@Override
	public List<String> sortNumbers(String[] toSort){
		List<String> al = new ArrayList<String>();
		for (int i = 0; i < toSort.length; i++) {
			String currentLine = toSort[i];
			if(checkForNumberInLine(currentLine)){
				al.add(currentLine);
			}
		}
		Collections.sort(al);
		return al;
	}

	/**
	 * Returns an ordered list of lines containing only numbers in increasing order
	 */
	public List<Integer> sortNumbersOrder(String[] toSort){
		List<Integer> al = new ArrayList<Integer>();
		for (int i = 0; i < toSort.length; i++) {
			String currentLine = toSort[i];
			if(checkForNumberInLine(currentLine)){
				al.add(Integer.parseInt(currentLine));
			}
		}
		Collections.sort(al);
		return al;
	}

	/**
	 * Returns an ordered list of lines containing only special characters
	 */
	@Override
	public List<String> sortSpecialChars(String[] toSort){
		List<String> al = new ArrayList<String>();
		for (int i = 0; i < toSort.length; i++) {
			String currentLine = toSort[i];
			if(checkForSpecialInLine(currentLine)){
				al.add(currentLine);
			}
		}
		Collections.sort(al);
		return al;
	}

	/**
	 * Returns an ordered list of lines containing simple and capital letters
	 */
	public List<String> sortSimpleCapital(String[] toSort){
		List<String> al = new ArrayList<String>();
		for (int i = 0; i < toSort.length; i++) {
			String currentLine = toSort[i];
			if(checkForSimpleInLine(currentLine) || checkForCapitalInLine(currentLine)){
				al.add(currentLine);
			}
		}
		Collections.sort(al);
		return al;
	}

	/**
	 * Returns an ordered list of lines containing simple letters and numbers
	 */
	@Override
	public List<String> sortSimpleNumbers(String[] toSort){
		List<String> al = new ArrayList<String>();
		for (int i = 0; i < toSort.length; i++) {
			String currentLine = toSort[i];
			if(checkForSimpleInLine(currentLine) || checkForNumberInLine(currentLine)){
				al.add(currentLine);
			}
		}
		Collections.sort(al);
		return al;
	}

	/**
	 * Returns an ordered list of lines containing simple letters and special
	 * characters
	 */
	@Override
	public List<String> sortSimpleSpecialChars(String[] toSort){
		List<String> al = new ArrayList<String>();
		for (int i = 0; i < toSort.length; i++) {
			String currentLine = toSort[i];
			if(checkForSimpleInLine(currentLine) || checkForSpecialInLine(currentLine)){
				al.add(currentLine);
			}
		}
		Collections.sort(al);
		return al;
	}


	/**
	 * Returns an ordered list of lines containing capital letters and numbers
	 */
	@Override
	public List<String> sortCapitalNumbers(String[] toSort){
		List<String> al = new ArrayList<String>();
		for (int i = 0; i < toSort.length; i++) {
			String currentLine = toSort[i];
			if(checkForNumberInLine(currentLine) || checkForCapitalInLine(currentLine)){
				al.add(currentLine);
			}
		}
		return al;
	}

	/**
	 * Returns an ordered list of lines containing capital letters and special
	 * character
	 */
	@Override
	public List<String> sortCapitalSpecialChars(String[] toSort){
		List<String> al = new ArrayList<String>();
		for (int i = 0; i < toSort.length; i++) {
			String currentLine = toSort[i];
			if(checkForCapitalInLine(currentLine) || checkForSpecialInLine(currentLine)){
				al.add(currentLine);
			}
		}
		Collections.sort(al);
		return al;
	}


	/**
	 * Returns an ordered list of lines containing numbers and special
	 * characters
	 */
	@Override
	public List<String> sortNumbersSpecialChars(String[] toSort){
		List<String> al = new ArrayList<String>();
		for (int i = 0; i < toSort.length; i++) {
			String currentLine = toSort[i];
			if(checkForSpecialInLine(currentLine) || checkForNumberInLine(currentLine)){
				al.add(currentLine);
			}
		}
		Collections.sort(al);
		return al;
	}


	/**
	 * Returns an ordered list of lines containing simple and capital letters
	 * and numbers
	 */
	@Override
	public List<String> sortSimpleCapitalNumber(String[] toSort){
		List<String> al = new ArrayList<String>();
		for (int i = 0; i < toSort.length; i++) {
			String currentLine = toSort[i];
			if(checkForSimpleInLine(currentLine) || checkForCapitalInLine(currentLine) 
					|| checkForNumberInLine(currentLine)){
				al.add(currentLine);
			}
		}
		return al;
	}

	/**
	 * Returns an ordered list of lines containing simple and capital letters
	 * and special characters
	 */
	@Override
	public List<String> sortSimpleCapitalSpecialChars(String[] toSort){
		List<String> al = new ArrayList<String>();
		for (int i = 0; i < toSort.length; i++) {
			String currentLine = toSort[i];
			if(checkForSimpleInLine(currentLine) || checkForCapitalInLine(currentLine) 
					|| checkForSpecialInLine(currentLine)){
				al.add(currentLine);
			}
		}
		Collections.sort(al);
		return al;
	}


	/**
	 * Returns an ordered list of lines containing simple letters, numbers and
	 * special characters
	 */
	@Override
	public List<String> sortSimpleNumbersSpecialChars(String[] toSort){
		List<String> al = new ArrayList<String>();
		for (int i = 0; i < toSort.length; i++) {
			String currentLine = toSort[i];
			if(checkForSimpleInLine(currentLine) || checkForNumberInLine(currentLine) 
					|| checkForSpecialInLine(currentLine)){
				al.add(currentLine);
			}
		}
		Collections.sort(al);
		return al;
	}


	/**
	 * Returns an ordered list of lines containing capital letters, numbers and
	 * special characters
	 */
	@Override
	public List<String> sortCapitalNumbersSpecialChars(String[] toSort){
		List<String> al = new ArrayList<String>();
		for (int i = 0; i < toSort.length; i++) {
			String currentLine = toSort[i];
			if(checkForCapitalInLine(currentLine) || checkForNumberInLine(currentLine) 
					|| checkForSpecialInLine(currentLine)){
				al.add(currentLine);
			}
		}
		Collections.sort(al);
		return al;
	}

	/**
	 * Returns an ordered list of lines containing simple and capital letters,
	 * numbers and special characters
	 */
	@Override
	public List<String> sortAll(String[] toSort){
		List<String> al = new ArrayList<String>();
		for (int i = 0; i < toSort.length; i++) {
			String currentLine = toSort[i];
			if(checkForSimpleInLine(currentLine) || checkForCapitalInLine(currentLine) 
					|| checkForNumberInLine(currentLine) || checkForSpecialInLine(currentLine)){
				al.add(currentLine);
			}
		}

		return al;
	};

	/**
	 * Returns True if the input character is a lowercase letter
	 */
	private Boolean checkForSimpleInLine(String currentLine) {
		int count = 0;
		Boolean result = false;
		for (int j = 0; j < currentLine.length(); j++) {
			int currentChar = currentLine.indexOf(j);
			if(Character.isLetter(currentChar) && Character.isLowerCase(currentChar)){
				count++;
			}
		}
		if(count == currentLine.length()){
			result = true;
		}
		return result;
	}

	/**
	 * Returns True if the input character is an Uppercase letter
	 */
	private Boolean checkForCapitalInLine(String currentLine) {
		int count = 0;
		Boolean result = false;
		for (int j = 0; j < currentLine.length(); j++) {
			char currentChar = (char) currentLine.indexOf(j);
			if(Character.isLetter(currentChar) && Character.isUpperCase(currentChar)){
				count++;
			}
		}
		if(count == currentLine.length()){
			result = true;
		}
		return result;
	}

	/**
	 * Returns True if the input character is a Number
	 */
	private Boolean checkForNumberInLine(String currentLine) {
		int count = 0;
		Boolean result = false;
		for (int j = 0; j < currentLine.length(); j++) {
			int currentChar = currentLine.indexOf(j);
			if(Character.isDigit(currentChar)){
				count++;
			}
		}
		if(count == currentLine.length()){
			result = true;
		}
		return result;
	}

	/**
	 * Returns True if the input character is a Special character
	 */
	private Boolean checkForSpecialInLine(String currentLine) {
		int count = 0;
		Boolean result = false;
		for (int j = 0; j < currentLine.length(); j++) {
			char currentChar = (char) currentLine.indexOf(j);
			if(!Character.isDigit(currentChar) && !Character.isLetter(currentChar)){
				count++;
			}
		}
		if(count == currentLine.length()){
			result = true;
		}
		return result;
	}

	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws SortException{
		Path currentDir = Paths.get(Environment.currentDirectory);
		int filePosition = ZERO;
		String[] toSort = null;


		if (args == null || args.length == ZERO) {
			toSort = readFromStdinAndWriteToStringArray(stdin);
		} else if (args.length == ONE){		
			filePosition = ZERO;
			if(isNumberCommandFormat(args)){
				toSort = readFromStdinAndWriteToStringArray(stdin);
			}else{
				toSort = getFileContents(args, currentDir, filePosition);	
			}
		}else if (args.length == MAX_LENGTH){
			catchMissingNumberCommandFormatException(args);
			filePosition = ONE;
			toSort = getFileContents(args, currentDir, filePosition);

		}else{
			throw new SortException("Arguments cannot be greater than 2");
		}
		Arrays.sort(toSort);
		stdoutSortedArray(stdout, toSort);
	}

	private void stdoutSortedArray(OutputStream stdout, String[] toSort)
			throws SortException {
		for (int i = 0; i < toSort.length; i++) {
			try {
				stdout.write(toSort[i].getBytes(CHARSET_UTF_8));
			} catch (IOException e) {
				throw new SortException(
						"Could not write to output stream");
			}
		}
	}

	/**
	 * Read from stdin and write to a string array
	 * 
	 * @param stdin
	 *            An input Stream. Reading from stdin and not a file
	 * @throws SortException
	 *             If stdin. I/O exceptions caught when
	 *             reading and writing from input and output streams.
	 */

	private String[] readFromStdinAndWriteToStringArray(InputStream stdin) throws SortException {
		List<String> al = new ArrayList<String>();
		if (stdin == null) {
			throw new SortException("Null Pointer Exception");
		}
		BufferedReader buffReader = new BufferedReader(new InputStreamReader(
				stdin));
		String input = "";
		try {
			while((input=buffReader.readLine())!=null){
				al.add(input);
			}
		} catch (Exception e) {
			throw new SortException("Exception caught");
		}
		return al.toArray(new String[al.size()]);
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
	private String[] getFileContents(String[] args, Path currentDir,
			int filePosition) throws SortException {
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
	private void catchMissingNumberCommandFormatException(String[] args) throws SortException {
		if(!isNumberCommandFormat(args)){
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
	private boolean isNumberCommandFormat(String[] args) {
		return args[0].equals("-n");
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
	 *             Exceptions caught when reading and
	 *             writing from input file.
	 */
	String[] readFromFileAndWriteToStringArray(Path filePath) throws SortException {
		List<String> al = new ArrayList<String>();
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
			throw new SortException("IOException");
		}
		return al.toArray(new String[al.size()]);
	}

	
	
	
	


}



