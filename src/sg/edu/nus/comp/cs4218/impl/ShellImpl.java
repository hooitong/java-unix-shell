package sg.edu.nus.comp.cs4218.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.Command;
import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.Shell;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.app.BcApplication;
import sg.edu.nus.comp.cs4218.impl.app.CalApplication;
import sg.edu.nus.comp.cs4218.impl.app.CatApplication;
import sg.edu.nus.comp.cs4218.impl.app.CommApplication;
import sg.edu.nus.comp.cs4218.impl.app.DateApplication;
import sg.edu.nus.comp.cs4218.impl.app.EchoApplication;
import sg.edu.nus.comp.cs4218.impl.app.FmtApplication;
import sg.edu.nus.comp.cs4218.impl.app.HeadApplication;
import sg.edu.nus.comp.cs4218.impl.app.SortApplication;
import sg.edu.nus.comp.cs4218.impl.app.TailApplication;
import sg.edu.nus.comp.cs4218.impl.cmd.CallCommand;
import sg.edu.nus.comp.cs4218.impl.cmd.PipeCommand;
import sg.edu.nus.comp.cs4218.impl.cmd.SequenceCommand;

/**
 * A Shell is a command interpreter and forms the backbone of the entire
 * program. Its responsibility is to interpret commands that the user type and
 * to run programs that the user specify in her command lines.
 * 
 * <p>
 * <b>Command format:</b>
 * <code>&lt;Pipe&gt; | &lt;Sequence&gt; | &lt;Call&gt;</code>
 * </p>
 */

public class ShellImpl implements Shell {

	public static final String EXP_INVALID_APP = "Invalid app.";
	public static final String EXP_SYNTAX = "Invalid syntax encountered.";
	public static final String EXP_REDIR_PIPE = "File output redirection and "
			+ "pipe operator cannot be used side by side.";
	public static final String EXP_SAME_REDIR = "Input redirection file same " + "as output redirection file.";
	public static final String EXP_STDOUT = "Error writing to stdout.";
	public static final String EXP_NOT_SUPPORTED = " not supported yet";
	public static final String NEW_LINE = System.lineSeparator();
	public static final int ZERO = 0;
	public static final int ONE = 1;
	public static final int TWO = 2;
	public static final String PIPE = "|";

	/**
	 * Searches for and processes the commands enclosed by back quotes for
	 * command substitution.If no back quotes are found, the argsArray from the
	 * input is returned unchanged. If back quotes are found, the back quotes
	 * and its enclosed commands substituted with the output from processing the
	 * commands enclosed in the back quotes.
	 * 
	 * @param argsArray
	 *            String array of the individual commands.
	 * 
	 * @return String array with the back quotes command processed.
	 * 
	 * @throws AbstractApplicationException
	 *             If an exception happens while processing the content in the
	 *             back quotes.
	 * @throws ShellException
	 *             If an exception happens while processing the content in the
	 *             back quotes.
	 */
	public static String[] processBQ(String... argsArray) throws AbstractApplicationException, ShellException {
		// echo "this is space `echo "nbsp"`"
		// echo "this is space `echo "nbsp"` and `echo "2nd space"`"
		// Back quoted: any char except \n,`
		String[] resultArr = new String[argsArray.length];
		System.arraycopy(argsArray, 0, resultArr, 0, argsArray.length);
		String patternBQ = "`([^\\n`]*)`";
		Pattern patternBQp = Pattern.compile(patternBQ);

		for (int i = 0; i < argsArray.length; i++) {
			Matcher matcherBQ = patternBQp.matcher(argsArray[i]);
			if (matcherBQ.find()) {// found backquoted
				String bqStr = matcherBQ.group(1);
				// cmdVector.add(bqStr.trim());
				// process back quote
				// System.out.println("backquote" + bqStr);
				OutputStream bqOutputStream = new ByteArrayOutputStream();
				ShellImpl shell = new ShellImpl();
				// shell.parseAndEvaluate(bqStr, bqOutputStream);

				ByteArrayOutputStream outByte = (ByteArrayOutputStream) bqOutputStream;
				byte[] byteArray = outByte.toByteArray();
				String bqResult = new String(byteArray).replace("\n", "").replace("\r", "");

				// replace substring of back quote with result
				String replacedStr = argsArray[i].replace("`" + bqStr + "`", bqResult);
				resultArr[i] = replacedStr;
			}
		}
		return resultArr;
	}

	/**
	 * Static method to run the application as specified by the application
	 * command keyword and arguments.
	 * 
	 * @param app
	 *            String containing the keyword that specifies what application
	 *            to run.
	 * @param argsArray
	 *            String array containing the arguments to pass to the
	 *            applications for running.
	 * @param inputStream
	 *            InputputStream for the application to get arguments from, if
	 *            needed.
	 * @param outputStream
	 *            OutputStream for the application to print its output to.
	 * 
	 * @throws AbstractApplicationException
	 *             If an exception happens while running any of the
	 *             application(s).
	 * @throws ShellException
	 *             If an unsupported or invalid application command is detected.
	 */
	public static void runApp(String app, String[] argsArray, InputStream inputStream, OutputStream outputStream)
			throws AbstractApplicationException, ShellException {
		Application absApp = null;
		if (("cat").equals(app)) {// cat [FILE]...
			absApp = new CatApplication();
		} else if (("echo").equals(app)) {// echo [args]...
			absApp = new EchoApplication();
		} else if (("head").equals(app)) {// head [OPTIONS] [FILE]
			absApp = new HeadApplication();
		} else if (("tail").equals(app)) {// tail [OPTIONS] [FILE]
			absApp = new TailApplication();
		} else if (("date").equals(app)) {// tail [OPTIONS] [FILE]
			absApp = new DateApplication();
		} else if (("fmt").equals(app)) {// tail [OPTIONS] [FILE]
			absApp = new FmtApplication();
		} else if (("sort").equals(app)) {// tail [OPTIONS] [FILE]
			absApp = new SortApplication();
		} else if (("comm").equals(app)) {// tail [OPTIONS] [FILE]
			absApp = new CommApplication();
		} else if (("bc").equals(app)) {// tail [OPTIONS] [FILE]
			absApp = new BcApplication();
		} else if (("cal").equals(app)) {// tail [OPTIONS] [FILE]
			absApp = new CalApplication();
		} else { // invalid command
			throw new ShellException(app + ": " + EXP_INVALID_APP);
		}
		absApp.run(argsArray, inputStream, outputStream);
	}

	/**
	 * Static method to creates an inputStream based on the file name or file
	 * path.
	 * 
	 * @param inputStreamS
	 *            String of file name or file path
	 * 
	 * @return InputStream of file opened
	 * 
	 * @throws ShellException
	 *             If file is not found.
	 */
	public static InputStream openInputRedir(String inputStreamS) throws ShellException {
		File inputFile = new File(inputStreamS);
		FileInputStream fInputStream = null;
		try {
			fInputStream = new FileInputStream(inputFile);
		} catch (FileNotFoundException e) {
			throw new ShellException(e);
		}
		return fInputStream;
	}

	/**
	 * Static method to creates an outputStream based on the file name or file
	 * path.
	 * 
	 * @param outputStreamS
	 *            String of file name or file path.
	 * 
	 * @return OutputStream of file opened.
	 * 
	 * @throws ShellException
	 *             If file destination cannot be opened or inaccessible.
	 */
	public static OutputStream openOutputRedir(String outputStreamS) throws ShellException {
		File outputFile = new File(outputStreamS);
		FileOutputStream fOutputStream = null;
		try {
			fOutputStream = new FileOutputStream(outputFile);
		} catch (FileNotFoundException e) {
			throw new ShellException(e);
		}
		return fOutputStream;
	}

	/**
	 * Static method to close an inputStream.
	 * 
	 * @param inputStream
	 *            InputStream to be closed.
	 * 
	 * @throws ShellException
	 *             If inputStream cannot be closed successfully.
	 */
	public static void closeInputStream(InputStream inputStream) throws ShellException {
		if (inputStream != System.in && inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
				throw new ShellException(e);
			}
		}
	}

	/**
	 * Static method to close an outputStream. If outputStream provided is
	 * System.out, it will be ignored.
	 * 
	 * @param outputStream
	 *            OutputStream to be closed.
	 * 
	 * @throws ShellException
	 *             If outputStream cannot be closed successfully.
	 */
	public static void closeOutputStream(OutputStream outputStream) throws ShellException {
		if (outputStream != System.out && outputStream != null) {
			try {
				outputStream.close();
			} catch (IOException e) {
				throw new ShellException(e);
			}
		}
	}

	/**
	 * Static method to write output of an outputStream to another outputStream,
	 * usually System.out.
	 * 
	 * @param outputStream
	 *            Source outputStream to get stream from.
	 * @param stdout
	 *            Destination outputStream to write stream to.
	 * @throws ShellException
	 *             If exception is thrown during writing.
	 */
	public static void writeToStdout(OutputStream outputStream, OutputStream stdout) throws ShellException {
		if (outputStream instanceof FileOutputStream) {
			return;
		}
		try {
			stdout.write(((ByteArrayOutputStream) outputStream).toByteArray());
		} catch (IOException e) {
			throw new ShellException(e);
		}
	}

	/**
	 * Static method to pipe data from an outputStream to an inputStream, for
	 * the evaluation of the Pipe Commands.
	 * 
	 * @param outputStream
	 *            Source outputStream to get stream from.
	 * 
	 * @return InputStream with data piped from the outputStream.
	 * 
	 * @throws ShellException
	 *             If exception is thrown during piping.
	 */
	public static InputStream outputStreamToInputStream(OutputStream outputStream) throws ShellException {
		return new ByteArrayInputStream(((ByteArrayOutputStream) outputStream).toByteArray());
	}

	/**
	 * Main method for the Shell Interpreter program.
	 * 
	 * @param args
	 *            List of strings arguments, unused.
	 */

	public static void main(String... args) {
		ShellImpl shell = new ShellImpl();

		BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
		String readLine = null;
		String currentDir;

		while (true) {
			try {
				currentDir = Environment.currentDirectory;
				System.out.print(currentDir + ">");
				readLine = bReader.readLine();
				if (readLine == null) {
					break;
				}
				if (("").equals(readLine)) {
					continue;
				}
				shell.parseAndEvaluate(readLine, System.out);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * Method to parse the given string command from the user.
	 * 
	 * @param cmdline
	 *            The string to parse and execute
	 * @param stdout
	 * @throws AbstractApplicationException
	 * @throws ShellException
	 */

	@Override
	public void parseAndEvaluate(String cmdline, OutputStream stdout)
			throws AbstractApplicationException, ShellException {
		Command parentCommand = parse(cmdline);
		parentCommand.evaluate(null, stdout);
	}

	/**
	 * Attempt to pass using grammar syntax and return parent command.
	 * 
	 * @param cmdline
	 *            The string to parse into a command
	 * @return parent command
	 * @throws ShellException
	 */
	public static Command parse(String cmdline) throws ShellException {
		int commandIndex = 0;
		Command[] possibleCommands = new Command[3];
		possibleCommands[0] = new CallCommand(cmdline);
		possibleCommands[1] = new SequenceCommand(cmdline);
		possibleCommands[2] = new PipeCommand(cmdline);

		while (true) {
			try {
				possibleCommands[commandIndex].parse();
				return possibleCommands[commandIndex];
			} catch (ShellException e) {
				if (++commandIndex == possibleCommands.length) {
					throw e;
				} else if (e.getMessage().contains(SequenceCommand.MISSING_ARG)) {
					throw e;
				}

			}
		}
	}

	/**
	 * Evaluate pipe call with two commands
	 * 
	 * @param args
	 * @return string the string could return the exception message or the
	 *         actual result
	 */
	@Override
	public String pipeTwoCommands(String... args) {
		return pipeCaller(args);
	}

	/**
	 * Evaluate pipe call with more than two commands
	 * 
	 * @param args
	 * @return string the string could return the exception message or the
	 *         actual result
	 */
	@Override
	public String pipeMultipleCommands(String... args) {
		return pipeCaller(args);
	}

	/**
	 * Since the interface cannot be modified to throw an exception due to
	 * project requirements, a string of the error message is returned instead
	 * when an exception occurs during the execution of one of the commands.
	 * 
	 * @param args
	 * 
	 * @return string the string could return an exception message ( "pipe:
	 *         exception detected for one of the call commands") or the actual
	 *         result
	 */
	@Override
	public String pipeWithException(String... args) {
		return pipeCaller(args);

	}

	/**
	 * This methods calls all starts the parsing and evaluate pipe command
	 * 
	 * @param args
	 * @return string the string could return the exception message or the
	 *         actual result
	 */
	private String pipeCaller(String... args) {
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		String result = "";
		String joinedStr = String.join(" ", args);
		PipeCommand pipeCmd = new PipeCommand(joinedStr);
		try {
			pipeCmd.parse();
			pipeCmd.evaluate(null, stdout);
			result = stdout.toString();
		} catch (AbstractApplicationException | ShellException e) {
			result = e.getMessage();
		}
		return result;
	}

	/**
	 * Evaluates globbing with no files or directories.
	 *
	 * @param args
	 *            arguments to be evaluated
	 * @return empty string object since if it does not match any file or
	 *         directory
	 */
	@Override
	public String globNoPaths(String[] args) {
		return "";
	}

	/**
	 * Evaluate globbing with one file.
	 *
	 * @param args
	 *            arguments to be evaluated
	 * @return string where arguments matches a single file
	 */
	@Override
	public String globOneFile(String[] args) {
		String tempResult = "";
		for (String arg : args) {
			tempResult += arg + NEW_LINE;
		}
		return tempResult;
	}

	/**
	 * Evaluate globbing with files and directories one level down.
	 *
	 * @param args
	 *            arguments to be evaluated
	 * @return string that contains the evaluated arguments existing in the same
	 *         directory.
	 */
	@Override
	public String globFilesDirectories(String[] args) {
		return globHelper(args);
	}

	/**
	 * Evaluate globbing with files and directories multiple levels down.
	 *
	 * @param args
	 *            arguments to be evaluated
	 * @return string that contains the evaluated arguments existing in multiple
	 *         levels down.
	 */
	@Override
	public String globMultiLevel(String[] args) {
		return globHelper(args);
	}

	private String globHelper(String[] args) {
		CallCommand helper = new CallCommand();
		String tempResult = "";
		try {
			String[] globResult = helper.evaluateGlob(args);
			for (String result : globResult) {
				tempResult += result + NEW_LINE;
			}
		} catch (ShellException e) {
			return e.getMessage();
		}

		return tempResult;
	}
}