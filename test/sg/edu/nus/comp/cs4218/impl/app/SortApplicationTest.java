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

	/**
	 * Sorts strings that consists of lowercase letters
	 */
	@Test
	public void testSortStringsSimple() {
		String[] inputArr = new String[] { "nicholas", "jerry", "zackary",
				"arthur", "benny" };
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
		String[] inputArr = new String[] { "NICHOLAS", "JERRY", "ZACKARY",
				"ARTHUR", "BENNY" };
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
		String[] inputArr = new String[] { "5", "100", "1", "10", "33" };
		List<String> arrayList = sortApplication.sortNumbers(inputArr);
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
		String[] inputArr = new String[] { "5", "100", "1", "10", "33" };
		List<String> arrayList = sortApplication.sortNumbersWithNumFlagOn(inputArr);
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
		String[] inputArr = new String[] { "arCholas", "Benny", "Zackary",
				"arThur", "arPhur" };
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
		String[] inputArr = new String[] { "1oranges", "100apples", "22pears",
				"january2016", "feb16" };
		List<String> arrayList = sortApplication.sortSimpleNumbers(inputArr);
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
		String[] inputArr = new String[] { "1oranges", "100apples", "22pears",
				"january2016", "feb16" };
		List<String> arrayList = sortApplication
				.sortSimpleNumbersWithNumFlagOn(inputArr);
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
		String[] inputArr = new String[] { "*oranges", "a^pples", "a!pples",
				"feb*", "feb%" };
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
		String[] inputArr = new String[] { "FE8", "APP1E", "APP13", "25DA",
				"F3B" };
		List<String> arrayList = sortApplication.sortCapitalNumbers(inputArr);
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
		String[] inputArr = new String[] { "8FE", "1APP1E", "100APP13", "25DA",
				"30F3B" };
		List<String> arrayList = sortApplication
				.sortCapitalNumberswithNumFlagOn(inputArr);
		assertEquals("1APP1E", arrayList.get(0));
		assertEquals("8FE", arrayList.get(1));
		assertEquals("25DA", arrayList.get(2));
		assertEquals("30F3B", arrayList.get(3));
		assertEquals("100APP13", arrayList.get(4));
	}

	/**
	 * Sorts strings that consists of a combination of capital and special
	 * characters
	 */
	@Test
	public void testSortCapitalSpecialChars() {
		String[] inputArr = new String[] { "F**", "!F*", ")JD", "$GA", "*DE" };
		List<String> arrayList = sortApplication.sortCapitalSpecialChars(inputArr);
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
		String[] inputArr = new String[] { "100 @", "1 &(*", "22#%!", "33 *&@",
				"#%356" };
		List<String> arrayList = sortApplication.sortNumbersSpecialChars(inputArr);
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
		String[] inputArr = new String[] { "100 @", "50 @", "1 &(*", "22#%!",
				"33 *&@", "#%356", "68!" };
		List<String> arrayList = sortApplication
				.sortNumbersSpecialCharsWithNumFlagOn(inputArr);
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
		String[] inputArr = new String[] { "ap1E", "aPp1e", "6p3aR", "6Pear",
				"p3Ar" };
		List<String> arrayList = sortApplication.sortSimpleCapitalNumber(inputArr);
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
		String[] inputArr = new String[] { "ap1E", "100aPp1e", "66p3aR",
				"1Pear", "p3Ar" };
		List<String> arrayList = sortApplication
				.sortSimpleCapitalNumberWithNumFlagOn(inputArr);
		assertEquals("1Pear", arrayList.get(0));
		assertEquals("66p3aR", arrayList.get(1));
		assertEquals("100aPp1e", arrayList.get(2));
		assertEquals("ap1E", arrayList.get(3));
		assertEquals("p3Ar", arrayList.get(4));
	}

	/**
	 * Sorts strings that consists of a combination of lowercase, uppercase and
	 * special characters in asc order
	 */
	@Test
	public void testSortSimpleCapitalSpecialChars() {
		String[] inputArr = new String[] { "Appl^", "APpl^", "!peAR", "!Pear",
				"!peAr" };
		List<String> arrayList = sortApplication
				.sortSimpleCapitalSpecialChars(inputArr);
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
		String[] inputArr = new String[] { "&23jan", "100 pea*s", "jer100 *",
				"20 m!n", "chief) 24" };
		List<String> arrayList = sortApplication
				.sortSimpleNumbersSpecialChars(inputArr);
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
		String[] inputArr = new String[] { "&23jan", "100 pea*s", "jer100 *",
				"20 m!n", "chief) 24" };
		List<String> arrayList = sortApplication
				.sortSimpleNumbersSpecialCharsWithNumFlagOn(inputArr);
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
		String[] inputArr = new String[] { "AP1#E", "APP1E#", "6P3@R", "6PE@R",
				"&P3AL", "100 AP1#E", "30 AP1#E" };
		List<String> arrayList = sortApplication
				.sortCapitalNumbersSpecialChars(inputArr);
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
		String[] inputArr = new String[] { "AP1#E", "APP1E#", "6P3@R", "6PE@R",
				"&P3AL", "100 AP1#E", "30 AP1#E" };
		List<String> arrayList = sortApplication
				.sortCapitalNumbersSpecialCharsWithNumFlagOn(inputArr);
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
		String[] inputArr = new String[] { "100 B*anana", "20 B*anana",
				"#B@n@n0", "P3@r", "p3@R" };
		List<String> arrayList = sortApplication.sortAll(inputArr);
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
		String[] inputArr = new String[] { "100 B*anana", "20 B*anana",
				"#B@n@n0", "P3@r", "p3@R" };
		List<String> arrayList = sortApplication.sortAllWithNumFlagOn(inputArr);
		assertEquals("#B@n@n0", arrayList.get(0));
		assertEquals("20 B*anana", arrayList.get(1));
		assertEquals("100 B*anana", arrayList.get(2));
		assertEquals("P3@r", arrayList.get(3));
		assertEquals("p3@R", arrayList.get(4));
	}

}
