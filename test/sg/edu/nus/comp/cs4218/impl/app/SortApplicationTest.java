package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SortApplicationTest {
	private static SortApplication sortApplication;
	private static String[] args;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		sortApplication = new SortApplication();
		args = new String[] { "examples/sort.txt" };
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Sorts strings that consists of lowercase letters
	 */
	@Test
	public void testSortStringsSimple() {
		String[] inputArr = new String[] { "nicholas", "jerry", "zackary",
				"arthur", "benny" };
		List<String> al = sortApplication.sortStringsSimple(inputArr);
		assertEquals("arthur", al.get(0));
		assertEquals("benny", al.get(1));
		assertEquals("jerry", al.get(2));
		assertEquals("nicholas", al.get(3));
		assertEquals("zackary", al.get(4));
	}

	/**
	 * Sorts strings that consists of uppercase letters
	 */
	@Test
	public void testSortStringsCapital() {
		String[] inputArr = new String[] { "NICHOLAS", "JERRY", "ZACKARY",
				"ARTHUR", "BENNY" };
		List<String> al = sortApplication.sortStringsCapital(inputArr);
		assertEquals("ARTHUR", al.get(0));
		assertEquals("BENNY", al.get(1));
		assertEquals("JERRY", al.get(2));
		assertEquals("NICHOLAS", al.get(3));
		assertEquals("ZACKARY", al.get(4));
	}

	/**
	 * Sorts strings that consists of digits
	 * 
	 */
	@Test
	public void testSortNumbers() {
		String[] inputArr = new String[] { "5", "100", "1", "10", "33" };
		List<String> al = sortApplication.sortNumbers(inputArr);
		assertEquals("1", al.get(0));
		assertEquals("10", al.get(1));
		assertEquals("100", al.get(2));
		assertEquals("33", al.get(3));
		assertEquals("5", al.get(4));
	}

	/**
	 * Sorts strings that consists of digits in ascending order
	 */
	@Test
	public void testSortNumbersWithNumFlag() {
		String[] inputArr = new String[] { "5", "100", "1", "10", "33" };
		List<String> al = sortApplication.sortNumbersWithNumFlagOn(inputArr);
		assertEquals("1", al.get(0));
		assertEquals("5", al.get(1));
		assertEquals("10", al.get(2));
		assertEquals("33", al.get(3));
		assertEquals("100", al.get(4));
	}

	/**
	 * Sorts strings that consists of special characters
	 */
	@Test
	public void testSortSpecialChars() {
		String[] inputArr = new String[] { "@$#", "&*(", "^%&", "!^&^" };
		List<String> al = sortApplication.sortSpecialChars(inputArr);
		assertEquals("!^&^", al.get(0));
		assertEquals("&*(", al.get(1));
		assertEquals("@$#", al.get(2));
		assertEquals("^%&", al.get(3));
	}

	/**
	 * Sorts strings that consists of a combination of lowercase and capital
	 * characters
	 */
	@Test
	public void testSortSimpleCapital() {
		String[] inputArr = new String[] { "arCholas", "Benny", "Zackary",
				"arThur", "arPhur" };
		List<String> al = sortApplication.sortSimpleCapital(inputArr);
		assertEquals("Benny", al.get(0));
		assertEquals("Zackary", al.get(1));
		assertEquals("arCholas", al.get(2));
		assertEquals("arPhur", al.get(3));
		assertEquals("arThur", al.get(4));

	}

	/**
	 * Sorts strings that consists of a combination of lowercase and digit
	 * characters
	 */
	@Test
	public void testSortSimpleNumbers() {
		String[] inputArr = new String[] { "1oranges", "100apples", "22pears",
				"january2016", "feb16" };
		List<String> al = sortApplication.sortSimpleNumbers(inputArr);
		assertEquals("100apples", al.get(0));
		assertEquals("1oranges", al.get(1));
		assertEquals("22pears", al.get(2));
		assertEquals("feb16", al.get(3));
		assertEquals("january2016", al.get(4));
	}

	/**
	 * Sorts strings that consists of a combination of lowercase and digit
	 * characters
	 */
	@Test
	public void testSortSimpleNumbersWithNumFlagOn() {
		String[] inputArr = new String[] { "1oranges", "100apples", "22pears",
				"january2016", "feb16" };
		List<String> al = sortApplication
				.sortSimpleNumbersWithNumFlagOn(inputArr);
		assertEquals("1oranges", al.get(0));
		assertEquals("22pears", al.get(1));
		assertEquals("100apples", al.get(2));
		assertEquals("feb16", al.get(3));
		assertEquals("january2016", al.get(4));
	}

	/**
	 * Sorts strings that consists of a combination of lowercase and special
	 * characters
	 */
	@Test
	public void testSortSimpleSpecialChars() {
		String[] inputArr = new String[] { "*oranges", "a^pples", "a!pples",
				"feb*", "feb%" };
		List<String> al = sortApplication.sortSimpleSpecialChars(inputArr);
		assertEquals("*oranges", al.get(0));
		assertEquals("a!pples", al.get(1));
		assertEquals("a^pples", al.get(2));
		assertEquals("feb%", al.get(3));
		assertEquals("feb*", al.get(4));
	}

	/**
	 * Sorts strings that consists of a combination of capital and digit
	 * characters
	 */
	@Test
	public void testSortCapitalNumbers() {
		String[] inputArr = new String[] { "FE8", "APP1E", "APP13", "25DA",
				"F3B" };
		List<String> al = sortApplication.sortCapitalNumbers(inputArr);
		assertEquals("25DA", al.get(0));
		assertEquals("APP13", al.get(1));
		assertEquals("APP1E", al.get(2));
		assertEquals("F3B", al.get(3));
		assertEquals("FE8", al.get(4));
	}

	/**
	 * Sorts strings that consists of a combination of capital and digit
	 * characters
	 */
	@Test
	public void testSortCapitalNumbersWithNumFlagOn() {
		String[] inputArr = new String[] { "8FE", "1APP1E", "100APP13", "25DA",
				"30F3B" };
		List<String> al = sortApplication
				.sortCapitalNumberswithNumFlagOn(inputArr);
		assertEquals("1APP1E", al.get(0));
		assertEquals("8FE", al.get(1));
		assertEquals("25DA", al.get(2));
		assertEquals("30F3B", al.get(3));
		assertEquals("100APP13", al.get(4));
	}

	/**
	 * Sorts strings that consists of a combination of capital and special
	 * characters
	 */
	@Test
	public void testSortCapitalSpecialChars() {
		String[] inputArr = new String[] { "F**", "!F*", ")JD", "$GA", "*DE" };
		List<String> al = sortApplication.sortCapitalSpecialChars(inputArr);
		assertEquals("!F*", al.get(0));
		assertEquals("$GA", al.get(1));
		assertEquals(")JD", al.get(2));
		assertEquals("*DE", al.get(3));
		assertEquals("F**", al.get(4));
	}

	/**
	 * Sorts strings that consists of a combination of special and digit
	 * characters
	 */
	@Test
	public void testSortNumbersSpecialChars() {
		String[] inputArr = new String[] { "100 @", "1 &(*", "22#%!", "33 *&@",
				"#%356" };
		List<String> al = sortApplication.sortNumbersSpecialChars(inputArr);
		assertEquals("#%356", al.get(0));
		assertEquals("1 &(*", al.get(1));
		assertEquals("100 @", al.get(2));
		assertEquals("22#%!", al.get(3));
		assertEquals("33 *&@", al.get(4));
	}

	/**
	 * Sorts strings that consists of a combination of special and digit
	 * characters in asc order
	 */
	@Test
	public void testSortNumbersSpecialCharsWithNumFlagOn() {
		String[] inputArr = new String[] { "100 @", "50 @", "1 &(*", "22#%!",
				"33 *&@", "#%356", "68!" };
		List<String> al = sortApplication
				.sortNumbersSpecialCharsWithNumFlagOn(inputArr);
		assertEquals("#%356", al.get(0));
		assertEquals("1 &(*", al.get(1));
		assertEquals("22#%!", al.get(2));
		assertEquals("33 *&@", al.get(3));
		assertEquals("50 @", al.get(4));
		assertEquals("68!", al.get(5));
	}

	/**
	 * Sorts strings that consists of a combination of lowercase, uppercase and
	 * digit characters
	 */
	@Test
	public void testSortSimpleCapitalNumber() {
		String[] inputArr = new String[] { "ap1E", "aPp1e", "6p3aR", "6Pear",
				"p3Ar" };
		List<String> al = sortApplication.sortSimpleCapitalNumber(inputArr);
		assertEquals("6Pear", al.get(0));
		assertEquals("6p3aR", al.get(1));
		assertEquals("aPp1e", al.get(2));
		assertEquals("ap1E", al.get(3));
		assertEquals("p3Ar", al.get(4));
	}

	/**
	 * Sorts strings that consists of a combination of lowercase, uppercase and
	 * digit characters in asc order
	 */
	@Test
	public void testSortSimpleCapitalNumberWithNumFlagOn() {
		String[] inputArr = new String[] { "ap1E", "100aPp1e", "66p3aR",
				"1Pear", "p3Ar" };
		List<String> al = sortApplication
				.sortSimpleCapitalNumberWithNumFlagOn(inputArr);
		assertEquals("1Pear", al.get(0));
		assertEquals("66p3aR", al.get(1));
		assertEquals("100aPp1e", al.get(2));
		assertEquals("ap1E", al.get(3));
		assertEquals("p3Ar", al.get(4));
	}

	/**
	 * Sorts strings that consists of a combination of lowercase, uppercase and
	 * special characters in asc order
	 */
	@Test
	public void testSortSimpleCapitalSpecialChars() {
		String[] inputArr = new String[] { "Appl^", "APpl^", "!peAR", "!Pear",
				"!peAr" };
		List<String> al = sortApplication
				.sortSimpleCapitalSpecialChars(inputArr);
		assertEquals("!Pear", al.get(0));
		assertEquals("!peAR", al.get(1));
		assertEquals("!peAr", al.get(2));
		assertEquals("APpl^", al.get(3));
		assertEquals("Appl^", al.get(4));
	}

	/**
	 * Sorts strings that consists of a combination of lowercase, number and
	 * special characters
	 */
	@Test
	public void testSortSimpleNumbersSpecialChars() {
		String[] inputArr = new String[] { "&23jan", "100 pea*s", "jer100 *",
				"20 m!n", "chief) 24" };
		List<String> al = sortApplication
				.sortSimpleNumbersSpecialChars(inputArr);
		assertEquals("&23jan", al.get(0));
		assertEquals("100 pea*s", al.get(1));
		assertEquals("20 m!n", al.get(2));
		assertEquals("chief) 24", al.get(3));
		assertEquals("jer100 *", al.get(4));
	}

	/**
	 * Sorts strings that consists of a combination of lowercase, number and
	 * special characters in asc order
	 */
	@Test
	public void testSortSimpleNumbersSpecialCharsWithNumFlagOn() {
		String[] inputArr = new String[] { "&23jan", "100 pea*s", "jer100 *",
				"20 m!n", "chief) 24" };
		List<String> al = sortApplication
				.sortSimpleNumbersSpecialCharsWithNumFlagOn(inputArr);
		assertEquals("&23jan", al.get(0));
		assertEquals("20 m!n", al.get(1));
		assertEquals("100 pea*s", al.get(2));
		assertEquals("chief) 24", al.get(3));
		assertEquals("jer100 *", al.get(4));
	}

	/**
	 * Sorts strings that consists of a combination of uppercase, number and
	 * special characters
	 */
	@Test
	public void testSortCapitalNumbersSpecialChars() {
		String[] inputArr = new String[] { "AP1#E", "APP1E#", "6P3@R", "6PE@R",
				"&P3AL", "100 AP1#E", "30 AP1#E" };
		List<String> al = sortApplication
				.sortCapitalNumbersSpecialChars(inputArr);
		assertEquals("&P3AL", al.get(0));
		assertEquals("100 AP1#E", al.get(1));
		assertEquals("30 AP1#E", al.get(2));
		assertEquals("6P3@R", al.get(3));
		assertEquals("6PE@R", al.get(4));
		assertEquals("AP1#E", al.get(5));
		assertEquals("APP1E#", al.get(6));
	}

	/**
	 * Sorts strings that consists of a combination of uppercase, number and
	 * special characters in asc order
	 */
	@Test
	public void testSortCapitalNumbersSpecialCharsWithNumFlag() {
		String[] inputArr = new String[] { "AP1#E", "APP1E#", "6P3@R", "6PE@R",
				"&P3AL", "100 AP1#E", "30 AP1#E" };
		List<String> al = sortApplication
				.sortCapitalNumbersSpecialCharsWithNumFlagOn(inputArr);
		assertEquals("&P3AL", al.get(0));
		assertEquals("6P3@R", al.get(1));
		assertEquals("6PE@R", al.get(2));
		assertEquals("30 AP1#E", al.get(3));
		assertEquals("100 AP1#E", al.get(4));
		assertEquals("AP1#E", al.get(5));
		assertEquals("APP1E#", al.get(6));

	}

	/**
	 * Sorts strings that consists of a combination of lowecase, uppercase,
	 * number and special characters
	 */
	@Test
	public void testSortAll() {
		String[] inputArr = new String[] { "100 B*anana", "20 B*anana",
				"#B@n@n0", "P3@r", "p3@R" };
		List<String> al = sortApplication.sortAll(inputArr);
		assertEquals("#B@n@n0", al.get(0));
		assertEquals("100 B*anana", al.get(1));
		assertEquals("20 B*anana", al.get(2));
		assertEquals("P3@r", al.get(3));
		assertEquals("p3@R", al.get(4));
	}

	/**
	 * Sorts strings that consists of a combination of lowecase, uppercase,
	 * number and special characters in asc order
	 */
	@Test
	public void testSortAllNumFlagOn() {
		String[] inputArr = new String[] { "100 B*anana", "20 B*anana",
				"#B@n@n0", "P3@r", "p3@R" };
		List<String> al = sortApplication.sortAllWithNumFlagOn(inputArr);
		assertEquals("#B@n@n0", al.get(0));
		assertEquals("20 B*anana", al.get(1));
		assertEquals("100 B*anana", al.get(2));
		assertEquals("P3@r", al.get(3));
		assertEquals("p3@R", al.get(4));
	}

}
