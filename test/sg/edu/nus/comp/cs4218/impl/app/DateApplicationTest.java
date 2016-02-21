package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;

import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.DateException;

public class DateApplicationTest 
{
	@Test
	public final void testRun() throws DateException 
	{	
		DateApplication da = new DateApplication();
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		ByteArrayOutputStream bos = new ByteArrayOutputStream();	
		da.run(null, null, bos);
		String output = new String(bos.toByteArray());
		assertEquals(sdf.toString(),output);
	}
}

//Java Byte Array to String to Byte Array
//http://stackoverflow.com/questions/6684665/java-byte-array-to-string-to-byte-array
//Question by : http://stackoverflow.com/users/843387/0909em
//Answer by : http://stackoverflow.com/users/1313268/coraythan

//Is there an alternative to FileOutputStream type
//http://stackoverflow.com/questions/17595487/is-there-an-alternative-to-fileoutputstream-type-which-does-not-create-a-file
//Question by : http://stackoverflow.com/users/1904663/benvg
//Answer by : http://stackoverflow.com/users/1886855/marco-forberg