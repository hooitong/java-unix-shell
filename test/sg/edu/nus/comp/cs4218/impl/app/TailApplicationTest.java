/**
 * 
 */
package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.CatException;
import sg.edu.nus.comp.cs4218.exception.TailException;

/**
 * @author Yusuf
 *
 */
public class TailApplicationTest {

	@Test
	public final void testRun() throws IOException {
		TailApplication tail = new TailApplication();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		String testString = "Hey man";
		ByteArrayInputStream bis = new ByteArrayInputStream(testString.getBytes());
		
		String[] arguements = {"-n 1","sample.txt"};
		try {
			tail.run(arguements, bis, bos);
		} catch (TailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String ss = new String(bos.toByteArray());
		
		assertEquals("Indulgence announcing uncommonly met she ",ss);
	}

}
