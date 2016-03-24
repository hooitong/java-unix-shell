package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.BcException;
import sg.edu.nus.comp.cs4218.impl.app.BcApplication;

public class BcApplicationTestBug {
	private BcApplication bcApp;
	private ByteArrayInputStream inStream;
	private ByteArrayOutputStream outStream;

	public static final String APP_EXCEPTION = "bc: ";
	public static final String SHOULDNOT_FAIL = "Should not throw exception";

	@Before
	public void setUp() throws Exception {
		bcApp = new BcApplication();
		inStream = new ByteArrayInputStream(new byte[1]);
		outStream = new ByteArrayOutputStream();
	}
	
	/**This bug fails to return 1. Note only 0 statnds for false. Any another value except 0 represents true. this error can also be found in Or operation
	 * @throws BcException
	 * @throws IOException
	 */
	@Test
	public void testAndNegativeIntegers() throws BcException, IOException {
		String[] params = { "-1 && -1" };
		bcApp.run(params, inStream, outStream);
		assertEquals("1", outStream.toString());
	}
	
	/**This bug fails to throw an error but instead returned 50
	 * @throws BcException
	 * @throws IOException
	 */
	@Test
	public void testBracketsWithoutOperators() throws BcException, IOException {
		String[] params = { "((5)0)" };
		bcApp.run(params, inStream, outStream);
		fail("Should have thrown exception but did not!");

	}
	
	/**This bug fails to account for a operator precedence with unary minus, multiply, division
	 * @throws BcException
	 * @throws IOException
	 */
	@Test
	public void testUnaryMinusExpression() throws BcException, IOException {
		String[] params = { "5/-1*-2" };
		bcApp.run(params, inStream, outStream);
		assertEquals("10", outStream.toString());

	}
}
