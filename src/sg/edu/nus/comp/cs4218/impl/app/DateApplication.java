package sg.edu.nus.comp.cs4218.impl.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.exception.CatException;

/**
 * The date command prints the current date and time where the pattern is specified 
 * as [week day] [month] [day] [hh:mm:ss] [time zone] [year].
 *
 * <p>
 * <b>Command format:</b> <code>date</code>
 * </p>
 */
public class DateApplication implements Application {

	/**
	 * Runs the date application with the specified arguments.
	 * 
	 * @param stdout
	 *            An OutputStream. The output of the command is written to this
	 *            OutputStream.
	 * 
	 * @throws CatException
	 *             If the file(s) specified do not exist or are unreadable.
	 */
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout)
		 {

			
				//http://stackoverflow.com/questions/1459656/how-to-get-the-current-time-in-yyyy-mm-dd-hhmisec-millisecond-format-in-java
				//Question by : http://stackoverflow.com/users/111988/sunil-kumar-sahoo
				//Answer by : http://stackoverflow.com/users/171675/jayjay
				
				//http://stackoverflow.com/questions/4069028/write-string-to-output-stream
				//Question by : http://stackoverflow.com/users/232666/yart
				//Answer by : http://stackoverflow.com/users/248432/peter-knego
				
				SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
				try {
					stdout.write(sdf.toString().getBytes(Charset.forName("UTF-8")));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				
				
		}
	}
}
