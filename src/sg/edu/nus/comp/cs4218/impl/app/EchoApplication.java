package sg.edu.nus.comp.cs4218.impl.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.exception.EchoException;

/**
 * The echo command writes its arguments separated by spaces and terminates by a
 * newline on the standard output.
 * 
 * <p>
 * <b>Command format:</b> <code>echo [ARG]...</code>
 * </p>
 */
public class EchoApplication implements Application {

	/**
	 * Runs the echo application with the specified arguments.
	 * 
	 * @param args
	 *            Array of arguments for the application.
	 * @param stdin
	 *            An InputStream, not used.
	 * @param stdout
	 *            An OutputStream. Elements of args will be output to stdout,
	 *            separated by a space character.
	 * 
	 * @throws EchoException
	 *             If an I/O exception occurs.
	 */

	public void run(String[] args, InputStream stdin, OutputStream stdout) throws EchoException {
		if (args == null) {
			throw new EchoException("Null arguments");
		}
		if (stdout == null) {
			throw new EchoException("OutputStream not provided");
		}
		if (args.length == 0) {
			try {
				writeIfArgsIsEmpty(stdout);
			} catch (IOException i) {
				throw new EchoException(i);
			}
		} else {
			try {
				writeWhenArgsIsNonEmpty(args, stdout);
			} catch (IOException i) {
				throw new EchoException(i);
			}
		}

	}

	private void writeWhenArgsIsNonEmpty(String[] args, OutputStream stdout) throws IOException {
		byte[] line;
		String temp;
		String[] cleanList = createList(args);

		for (int i = 0; i < cleanList.length - 1; i++) {

			temp = cleanList[i] + " ";
			line = temp.getBytes();
			stdout.write(line);
		}
		if (cleanList.length >= 1) {
			stdout.write(cleanList[cleanList.length - 1].getBytes());
			stdout.write(System.lineSeparator().getBytes());
		}

	}

	private void writeIfArgsIsEmpty(OutputStream stdout) throws IOException {
		stdout.write(System.lineSeparator().getBytes());
		stdout.write(System.lineSeparator().getBytes());
	}

	/*
	 * Create a clean list of words to echo by removing spaces and empty
	 * argument
	 */
	private String[] createList(String... args) {
		int index = 0;
		String[] cleanList = new String[args.length];
		cleanList[index] = "\n";

		for (int i = 0; i < args.length; i++) {
			if (args[i] == null) {
				continue;
			}
			String temp = args[i].trim();
			if (temp.isEmpty()) {
				continue;
			}
			cleanList[index] = temp;
			index++;

		}

		return cleanList;
	}

}
