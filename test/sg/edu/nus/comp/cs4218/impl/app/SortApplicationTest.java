package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.SortException;

public class SortApplicationTest {
	private static final String NEW_LINE = System.lineSeparator();
	private static SortApplication sortApplication;
	private static String[] args, inputArr1, inputArr2, inputArr3, inputArr4, inputArr5, inputArr6, inputArr7,
			inputArr8, inputArr9, inputArr10;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		sortApplication = new SortApplication();
		args = new String[] { "examples/sort.txt" };
		inputArr1 = new String[] { "100 B*anana", "20 B*anana", "#B@n@n0", "P3@r", "p3@R" };
		inputArr2 = new String[] { "AP1#E", "APP1E#", "6P3@R", "6PE@R", "&P3AL", "100 AP1#E", "30 AP1#E" };
		inputArr3 = new String[] { "&23jan", "100 pea*s", "jer100 *", "20 m!n", "chief) 24" };
		inputArr4 = new String[] { "Appl^", "APpl^", "!peAR", "!Pear", "!peAr" };
		inputArr5 = new String[] { "100 @", "50 @", "1 &(*", "22#%!", "33 *&@", "#%356", "68!" };
		inputArr6 = new String[] { "1oranges", "100apples", "22pears", "january2016", "feb16" };
		inputArr7 = new String[] { "5", "100", "1", "10", "33" };
		inputArr8 = new String[] { "FE8", "APP1E", "APP13", "25DA", "F3B" };
		inputArr9 = new String[] { "ap1E", "aPp1e", "6p3aR", "6Pear", "p3Ar" };
		inputArr10 = new String[] { "8FE", "1APP1E", "100APP13", "25DAA", "30F3B" };
	}

	/**
	 * Sorts strings that consists of lowercase letters
	 */
	@Test
	public void testSortStringsSimple() {
		String[] inputArr = new String[] { "nicholas", "jerry", "zackary", "arthur", "benny" };
		List<String> arrayList = sortApplication.sortStringsSimple(inputArr);
		assertEquals("arthur", arrayList.get(0));
		assertEquals("benny", arrayList.get(1));
		assertEquals("jerry", arrayList.get(2));
		assertEquals("nicholas", arrayList.get(3));
		assertEquals("zackary", arrayList.get(4));
	}

	/**
	 * Sorts strings that consists of uppercase letters
	 */
	@Test
	public void testSortStringsCapital() {
		String[] inputArr = new String[] { "NICHOLAS", "JERRY", "ZACKARY", "ARTHUR", "BENNY" };
		List<String> arrayList = sortApplication.sortStringsCapital(inputArr);
		assertEquals("ARTHUR", arrayList.get(0));
		assertEquals("BENNY", arrayList.get(1));
		assertEquals("JERRY", arrayList.get(2));
		assertEquals("NICHOLAS", arrayList.get(3));
		assertEquals("ZACKARY", arrayList.get(4));
	}

	/**
	 * Sorts strings that consists of digits
	 * 
	 */
	@Test
	public void testSortNumbers() {
		List<String> arrayList = sortApplication.sortNumbers(inputArr7);
		assertEquals("1", arrayList.get(0));
		assertEquals("10", arrayList.get(1));
		assertEquals("100", arrayList.get(2));
		assertEquals("33", arrayList.get(3));
		assertEquals("5", arrayList.get(4));
	}

	/**
	 * Sorts strings that consists of digits in ascending order
	 */
	@Test
	public void testSortNumbersWithNumFlag() {
		List<String> arrayList = sortApplication.sortNumbersWithNumFlagOn(inputArr7);
		assertEquals("1", arrayList.get(0));
		assertEquals("5", arrayList.get(1));
		assertEquals("10", arrayList.get(2));
		assertEquals("33", arrayList.get(3));
		assertEquals("100", arrayList.get(4));
	}

	/**
	 * Sorts strings that consists of special characters
	 */
	@Test
	public void testSortSpecialChars() {
		String[] inputArr = new String[] { "@$#", "&*(", "^%&", "!^&^" };
		List<String> arrayList = sortApplication.sortSpecialChars(inputArr);
		assertEquals("!^&^", arrayList.get(0));
		assertEquals("&*(", arrayList.get(1));
		assertEquals("@$#", arrayList.get(2));
		assertEquals("^%&", arrayList.get(3));
	}

	/**
	 * Sorts strings that consists of a combination of lowercase and capital
	 * characters
	 */
	@Test
	public void testSortSimpleCapital() {
		String[] inputArr = new String[] { "arCholas", "Benny", "Zackary", "arThur", "arPhur" };
		List<String> arrayList = sortApplication.sortSimpleCapital(inputArr);
		assertEquals("Benny", arrayList.get(0));
		assertEquals("Zackary", arrayList.get(1));
		assertEquals("arCholas", arrayList.get(2));
		assertEquals("arPhur", arrayList.get(3));
		assertEquals("arThur", arrayList.get(4));

	}

	/**
	 * Sorts strings that consists of a combination of lowercase and digit
	 * characters
	 */
	@Test
	public void testSortSimpleNumbers() {
		List<String> arrayList = sortApplication.sortSimpleNumbers(inputArr6);
		assertEquals("100apples", arrayList.get(0));
		assertEquals("1oranges", arrayList.get(1));
		assertEquals("22pears", arrayList.get(2));
		assertEquals("feb16", arrayList.get(3));
		assertEquals("january2016", arrayList.get(4));
	}

	/**
	 * Sorts strings that consists of a combination of lowercase and digit
	 * characters
	 */
	@Test
	public void testSortSimpleNumbersWithNumFlagOn() {
		List<String> arrayList = sortApplication.sortSimpleNumbersWithNumFlagOn(inputArr6);
		assertEquals("1oranges", arrayList.get(0));
		assertEquals("22pears", arrayList.get(1));
		assertEquals("100apples", arrayList.get(2));
		assertEquals("feb16", arrayList.get(3));
		assertEquals("january2016", arrayList.get(4));
	}

	/**
	 * Sorts strings that consists of a combination of lowercase and special
	 * characters
	 */
	@Test
	public void testSortSimpleSpecialChars() {
		String[] inputArr = new String[] { "*oranges", "a^pples", "a!pples", "feb*", "feb%" };
		List<String> arrayList = sortApplication.sortSimpleSpecialChars(inputArr);
		assertEquals("*oranges", arrayList.get(0));
		assertEquals("a!pples", arrayList.get(1));
		assertEquals("a^pples", arrayList.get(2));
		assertEquals("feb%", arrayList.get(3));
		assertEquals("feb*", arrayList.get(4));
	}

	/**
	 * Sorts strings that consists of a combination of capital and digit
	 * characters
	 */
	@Test
	public void testSortCapitalNumbers() {
		List<String> arrayList = sortApplication.sortCapitalNumbers(inputArr8);
		assertEquals("25DA", arrayList.get(0));
		assertEquals("APP13", arrayList.get(1));
		assertEquals("APP1E", arrayList.get(2));
		assertEquals("F3B", arrayList.get(3));
		assertEquals("FE8", arrayList.get(4));
	}

	/**
	 * Sorts strings that consists of a combination of capital and digit
	 * characters
	 */
	@Test
	public void testSortCapitalNumbersWithNumFlagOn() {
		List<String> arrayList = sortApplication.sortCapitalNumberswithNumFlagOn(inputArr10);
		assertEquals("1APP1E", arrayList.get(0));
		assertEquals("8FE", arrayList.get(1));
		assertEquals("25DAA", arrayList.get(2));
		assertEquals("30F3B", arrayList.get(3));
		assertEquals("100APP13", arrayList.get(4));
	}

	/**
	 * Sorts strings that consists of a combination of capital and special
	 * characters
	 */
	@Test
	public void testSortCapitalSpecialChars() {
		String[] input = new String[] { "F**", "!F*", ")JD", "$GA", "*DE" };
		List<String> arrayList = sortApplication.sortCapitalSpecialChars(input);
		assertEquals("!F*", arrayList.get(0));
		assertEquals("$GA", arrayList.get(1));
		assertEquals(")JD", arrayList.get(2));
		assertEquals("*DE", arrayList.get(3));
		assertEquals("F**", arrayList.get(4));
	}

	/**
	 * Sorts strings that consists of a combination of special and digit
	 * characters
	 */
	@Test
	public void testSortNumbersSpecialChars() {
		List<String> arrayList = sortApplication.sortNumbersSpecialChars(inputArr5);
		assertEquals("#%356", arrayList.get(0));
		assertEquals("1 &(*", arrayList.get(1));
		assertEquals("100 @", arrayList.get(2));
		assertEquals("22#%!", arrayList.get(3));
		assertEquals("33 *&@", arrayList.get(4));
	}

	/**
	 * Sorts strings that consists of a combination of special and digit
	 * characters in asc order
	 */
	@Test
	public void testSortNumbersSpecialCharsWithNumFlagOn() {
		List<String> arrayList = sortApplication.sortNumbersSpecialCharsWithNumFlagOn(inputArr5);
		assertEquals("#%356", arrayList.get(0));
		assertEquals("1 &(*", arrayList.get(1));
		assertEquals("22#%!", arrayList.get(2));
		assertEquals("33 *&@", arrayList.get(3));
		assertEquals("50 @", arrayList.get(4));
		assertEquals("68!", arrayList.get(5));
	}

	/**
	 * Sorts strings that consists of a combination of lowercase, uppercase and
	 * digit characters
	 */
	@Test
	public void testSortSimpleCapitalNumber() {
		List<String> arrayList = sortApplication.sortSimpleCapitalNumber(inputArr9);
		assertEquals("6Pear", arrayList.get(0));
		assertEquals("6p3aR", arrayList.get(1));
		assertEquals("aPp1e", arrayList.get(2));
		assertEquals("ap1E", arrayList.get(3));
		assertEquals("p3Ar", arrayList.get(4));
	}

	/**
	 * Sorts strings that consists of a combination of lowercase, uppercase and
	 * digit characters in asc order
	 */
	@Test
	public void testSortSimpleCapitalNumberWithNumFlagOn() {
		String[] input = new String[] { "ap1EE", "100aPp1e", "66p3aR", "1Pear", "p3Arr" };
		List<String> arrayList = sortApplication.sortSimpleCapitalNumberWithNumFlagOn(input);
		assertEquals("1Pear", arrayList.get(0));
		assertEquals("66p3aR", arrayList.get(1));
		assertEquals("100aPp1e", arrayList.get(2));
		assertEquals("ap1EE", arrayList.get(3));
		assertEquals("p3Arr", arrayList.get(4));
	}

	/**
	 * Sorts strings that consists of a combination of lowercase, uppercase and
	 * special characters in asc order
	 */
	@Test
	public void testSortSimpleCapitalSpecialChars() {
		List<String> arrayList = sortApplication.sortSimpleCapitalSpecialChars(inputArr4);
		assertEquals("!Pear", arrayList.get(0));
		assertEquals("!peAR", arrayList.get(1));
		assertEquals("!peAr", arrayList.get(2));
		assertEquals("APpl^", arrayList.get(3));
		assertEquals("Appl^", arrayList.get(4));
	}

	/**
	 * Sorts strings that consists of a combination of lowercase, number and
	 * special characters
	 */
	@Test
	public void testSortSimpleNumbersSpecialChars() {
		List<String> arrayList = sortApplication.sortSimpleNumbersSpecialChars(inputArr3);
		assertEquals("&23jan", arrayList.get(0));
		assertEquals("100 pea*s", arrayList.get(1));
		assertEquals("20 m!n", arrayList.get(2));
		assertEquals("chief) 24", arrayList.get(3));
		assertEquals("jer100 *", arrayList.get(4));
	}

	/**
	 * Sorts strings that consists of a combination of lowercase, number and
	 * special characters in asc order
	 */
	@Test
	public void testSortSimpleNumbersSpecialCharsWithNumFlagOn() {
		List<String> arrayList = sortApplication.sortSimpleNumbersSpecialCharsWithNumFlagOn(inputArr3);
		assertEquals("&23jan", arrayList.get(0));
		assertEquals("20 m!n", arrayList.get(1));
		assertEquals("100 pea*s", arrayList.get(2));
		assertEquals("chief) 24", arrayList.get(3));
		assertEquals("jer100 *", arrayList.get(4));
	}

	/**
	 * Sorts strings that consists of a combination of uppercase, number and
	 * special characters
	 */
	@Test
	public void testSortCapitalNumbersSpecialChars() {
		List<String> arrayList = sortApplication.sortCapitalNumbersSpecialChars(inputArr2);
		assertEquals("&P3AL", arrayList.get(0));
		assertEquals("100 AP1#E", arrayList.get(1));
		assertEquals("30 AP1#E", arrayList.get(2));
		assertEquals("6P3@R", arrayList.get(3));
		assertEquals("6PE@R", arrayList.get(4));
		assertEquals("AP1#E", arrayList.get(5));
		assertEquals("APP1E#", arrayList.get(6));
	}

	/**
	 * Sorts strings that consists of a combination of uppercase, number and
	 * special characters in asc order
	 */
	@Test
	public void testSortCapitalNumbersSpecialCharsWithNumFlag() {
		List<String> arrayList = sortApplication.sortCapitalNumbersSpecialCharsWithNumFlagOn(inputArr2);
		assertEquals("&P3AL", arrayList.get(0));
		assertEquals("6P3@R", arrayList.get(1));
		assertEquals("6PE@R", arrayList.get(2));
		assertEquals("30 AP1#E", arrayList.get(3));
		assertEquals("100 AP1#E", arrayList.get(4));
		assertEquals("AP1#E", arrayList.get(5));
		assertEquals("APP1E#", arrayList.get(6));

	}

	/**
	 * Sorts strings that consists of a combination of lowecase, uppercase,
	 * number and special characters
	 */
	@Test
	public void testSortAll() {
		List<String> arrayList = sortApplication.sortAll(inputArr1);
		assertEquals("#B@n@n0", arrayList.get(0));
		assertEquals("100 B*anana", arrayList.get(1));
		assertEquals("20 B*anana", arrayList.get(2));
		assertEquals("P3@r", arrayList.get(3));
		assertEquals("p3@R", arrayList.get(4));
	}

	/**
	 * Sorts strings that consists of a combination of lowecase, uppercase,
	 * number and special characters in asc order
	 */
	@Test
	public void testSortAllNumFlagOn() {
		List<String> arrayList = sortApplication.sortAllWithNumFlagOn(inputArr1);
		assertEquals("#B@n@n0", arrayList.get(0));
		assertEquals("20 B*anana", arrayList.get(1));
		assertEquals("100 B*anana", arrayList.get(2));
		assertEquals("P3@r", arrayList.get(3));
		assertEquals("p3@R", arrayList.get(4));
	}

	/**
	 * Test using only arguments only
	 * 
	 * @throws SortException
	 */
	@Test
	public void testRunArgsOnly() throws SortException {
		String[] argsArr = new String[] { "-n", "examples/sort.txt" };
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		sortApplication.run(argsArr, null, stdout);
		String expectedResult = "arthur" + NEW_LINE + "benny" + NEW_LINE + "jerry" + NEW_LINE + "nicholas" + NEW_LINE
				+ "zackary" + NEW_LINE;
		assertEquals(expectedResult, stdout.toString());
	}

	/**
	 * Test using only arguments only with num flag on
	 * 
	 * @throws SortException
	 */
	@Test
	public void testRunArgsOnlyWithNumFlag() throws SortException {
		String[] argsArr = new String[] { "-n", "examples/numbersort.txt" };
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		sortApplication.run(argsArr, null, stdout);
		String expectedResult = "9" + NEW_LINE + "11" + NEW_LINE + "23" + NEW_LINE + "65" + NEW_LINE + "1000"
				+ NEW_LINE;
		assertEquals(expectedResult, stdout.toString());
	}

	/**
	 * Test using only stdin
	 * 
	 * @throws SortException
	 */
	@Test
	public void testRunStdinMissingArgs() throws SortException {
		String[] argsArr = new String[] {};
		String contentStr = "8" + NEW_LINE + "68" + NEW_LINE + "105";
		String expected = "105" + NEW_LINE + "68" + NEW_LINE + "8" + NEW_LINE;
		InputStream inputStream = new java.io.ByteArrayInputStream(contentStr.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		sortApplication.run(argsArr, inputStream, stdout);
		assertEquals(expected, stdout.toString());
	}

	/**
	 * test using both args and stdin
	 * 
	 * @throws SortException
	 */
	@Test
	public void testRunArgsStdin() throws SortException {
		String[] argsArr = new String[] { "-n" };
		String contentStr = "112" + NEW_LINE + "68" + NEW_LINE + "681";
		String expected = "68" + NEW_LINE + "112" + NEW_LINE + "681" + NEW_LINE;
		InputStream inputStream = new java.io.ByteArrayInputStream(contentStr.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		sortApplication.run(argsArr, inputStream, stdout);
		assertEquals(expected, stdout.toString());
	}

	/**
	 * Test without both args and stdin
	 * 
	 * @throws SortException
	 */
	@Test(expected = SortException.class)
	public void testRunMissingArgsStdin() throws SortException {
		String[] argsArr = new String[] {};
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		sortApplication.run(argsArr, null, stdout);
	}

	/**
	 * Test with args (numflag) but missing stdin
	 * 
	 * @throws SortException
	 */
	@Test(expected = SortException.class)
	public void testRunArgsMissingStdin() throws SortException {
		String[] argsArr = new String[] { "-n" };
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		sortApplication.run(argsArr, null, stdout);
	}

	/**
	 * Test with invalid args (numflag) but missing stdin
	 * 
	 * @throws SortException
	 */
	@Test(expected = SortException.class)
	public void testRunInvalidArgsMissingStdin() throws SortException {
		String[] argsArr = new String[] { "-j" };
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		sortApplication.run(argsArr, null, stdout);
	}

}
