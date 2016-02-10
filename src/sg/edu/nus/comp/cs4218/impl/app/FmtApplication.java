package sg.edu.nus.comp.cs4218.impl.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
public class FmtApplication implements Application {

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
	public void run(String[] args, InputStream stdin, OutputStream stdout)
			throws CatException {

		if (args == null || args.length == 0) {
			if (stdin == null || stdout == null) {
				throw new CatException("Null Pointer Exception");
			}
			try {
				int intCount;
				while ((intCount = stdin.read()) != -1) {
					stdout.write(intCount);
				}
			} catch (Exception exIO) {
				throw new CatException("Exception Caught");
			}
		} else {
			
			int wrapValue = 80;
			Path filePath = null;
			
			if(args[0].startsWith("-w"))
			{
				wrapValue = Integer.parseInt(args[0].substring(3));
				
				if(args.length>1)
				{
					Path currentDir = Paths.get(Environment.currentDirectory);
					filePath = currentDir.resolve(args[1]);
					checkIfFileIsReadable(filePath);
				}
			}
			else
			{
				Path currentDir = Paths.get(Environment.currentDirectory);
				filePath = currentDir.resolve(args[1]);
				checkIfFileIsReadable(filePath);
			}
			
			String strToWrap = "";
			if(filePath!=null)
			{
				//http://stackoverflow.com/questions/6684665/java-byte-array-to-string-to-byte-array
				//Question by : http://stackoverflow.com/users/843387/0909em
				//Answer by : http://stackoverflow.com/users/320700/yanick-rochon
				
						try {
							byte[] byteFileArray = Files
									.readAllBytes(filePath);
							strToWrap = new String(byteFileArray);
							
						} catch (IOException e) {
							throw new CatException(
									"Could not write to output stream");
						}
			}
			else
			{
				//http://www.mkyong.com/java/how-to-get-the-standard-input-in-java/
				BufferedReader br = new BufferedReader(new InputStreamReader(stdin));
				String input;
				try {
					while((input=br.readLine())!=null){
						strToWrap.concat(input);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//stdout.write(byteFileArray);

				
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
			throw new CatException("This is a directory");
		}
		if (Files.exists(filePath) && Files.isReadable(filePath)) {
			return true;
		} else {
			throw new CatException("Could not read file");
		}
	}
}
