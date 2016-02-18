package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.CatException;

public class FmtApplicationTest {
	
	@Before
	public void setUp() {
		//mockRootDirectory = Environment.currentDirectory + "mock-filesystem/";
	}
	
	@Test
	public final void testRun() throws IOException {
		FmtApplication fmt = new FmtApplication();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		String testString = "Hey man";
		ByteArrayInputStream bis = new ByteArrayInputStream(testString.getBytes());
		
		String[] arguements = {"sample.txt"};
		try {
			fmt.run(arguements, bis, bos);
		} catch (CatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String ss = new String(bos.toByteArray());
		String[] splitSS = ss.split("\n");
		String[] split2 = splitSS[0].split(" ");
		assertEquals("terminated.",split2[split2.length-1]);
	}
}
