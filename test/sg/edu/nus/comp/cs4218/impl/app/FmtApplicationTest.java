package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.CatException;

public class FmtApplicationTest {
	
	@Test
	public final void testRun() throws IOException {
		FmtApplication fmt = new FmtApplication();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		String testString = "Hey man";
		ByteArrayInputStream bis = new ByteArrayInputStream(testString.getBytes());
		
		String[] arguements = {"-w 90"};
		try {
			fmt.run(arguements, bis, bos);
		} catch (CatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String ss = new String(bos.toByteArray());
		String[] splitSS = ss.split("\n");
		assertEquals("dd",splitSS[0]);
	}
}
