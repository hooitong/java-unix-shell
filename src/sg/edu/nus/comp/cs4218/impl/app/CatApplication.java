package sg.edu.nus.comp.cs4218.impl.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.CatException;

/**
 * The cat command concatenates the content of given files and prints on the
 * standard output.
 * 
 * <p>
 * <b>Command format:</b> <code>cat [FILE]...</code>
 * <dl>
 * <dt>FILE</dt>
 * <dd>the name of the file(s). If no files are specified, use stdin.</dd>
 * </dl>
 * </p>
 */
public class CatApplication implements Application {

	/**
	 * Runs the cat application with the specified arguments.
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
	 * @throws CatException
	 *             If the file(s) specified do not exist or are unreadable.
	 */
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws CatException {
		throwExceptionIfNoOutputStream(stdout);
		throwExceptionIfNoInput(args, stdin);
		if (fileIsSpecified(args)) {
			readFromArgs(args, stdout);
		} else {
			readFromStdin(stdin, stdout);
		}

	}

	private void readFromArgs(String[] args, OutputStream stdout) throws CatException {
		ArrayList<Path> filePaths = new ArrayList<Path>();
		resolveArguments(args, filePaths);
		processFiles(filePaths, stdout);
	}

	private void resolveArguments(String[] args, ArrayList<Path> filePaths) throws CatException {
		boolean isFileReadable = false;
		int numOfFiles = args.length;
		Path filePath;
		Path currentDir = Paths.get(Environment.currentDirectory);

		for (int i = 0; i < numOfFiles; i++) {
			try {
				String temp = args[i].trim();
				if (temp.isEmpty()) {
					continue;
				} else {
					filePath = currentDir.resolve(args[i]);
					isFileReadable = checkIfFileIsReadable(filePath);
					if (isFileReadable) {
						filePaths.add(filePath);
					}
				}
			} catch (NullPointerException npe) {
				throw new CatException(npe, "NullPointerException");
			}
		}
	}

	private void processFiles(ArrayList<Path> filePaths, OutputStream stdout) throws CatException {
		// file could be read. perform cat command
		if (filePaths.isEmpty()) {
			throw new CatException("Invalid filepath");
		} else {

			for (int j = 0; j < filePaths.size() - 1; j++) {
				try {
					byte[] byteFileArray = Files.readAllBytes(filePaths.get(j));
					if (byteFileArray.length <= 0) { // Empty file
						continue;
					}
					stdout.write(byteFileArray);
					byte[] newLine = System.lineSeparator().getBytes("UTF-8");
					stdout.write(newLine);
					stdout.flush();
				} catch (IOException e) {
					throw new CatException(e);
				}
			}
			if (!filePaths.isEmpty()) {
				try {
					byte[] byteFileArray = Files.readAllBytes(filePaths.get(filePaths.size() - 1));

					stdout.write(byteFileArray);
					stdout.flush();
				} catch (IOException e1) {
					throw new CatException("IOException: " + e1);
				}
			}
		}
	}

	/*
	 * Note: when reading from inputStream, no new line is added at the end of
	 * the file
	 */
	private void readFromStdin(InputStream stdin, OutputStream stdout) throws CatException {
		try {
			int intCount;
			while ((intCount = stdin.read()) != -1) {
				stdout.write(intCount);
			}
			stdout.flush();

		} catch (IOException io) {
			throw new CatException(io);
		}
	}

	/**
	 * Checks if a file is readable.
	 * 
	 * @param filePath
	 *            The path to the file
	 * @return True if the file is readable.
	 * @throws CatException
	 *             If the file is not readable
	 */
	boolean checkIfFileIsReadable(Path filePath) throws CatException {
		if (Files.isDirectory(filePath)) {
			throw new CatException( "This is a directory");
		}
		if (Files.exists(filePath) && Files.isReadable(filePath)) {
			return true;
		} else {
			throw new CatException("Could not read file");
		}
	}

	private void throwExceptionIfNoOutputStream(OutputStream stdout) throws CatException {
		if (stdout == null) {
			throw new CatException("No output stream.");
		}
	}

	private void throwExceptionIfNoInput(String[] args, InputStream stdin) throws CatException {
		if (args == null && stdin == null) {
			throw new CatException("No input");
		} else if (args != null && args.length == 0 && stdin == null) {
			throw new CatException("No input");
		}
	}

	private boolean fileIsSpecified(String... args) {
		return (args != null && args.length > 0);
	}
}
