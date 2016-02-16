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

	@Test
	public void testSortNumbers() {
		String[] inputArr = new String[] { "5", "100", "1", "10", "33" };
		List<String> al = sortApplication.sortNumbers(inputArr, false);
		assertEquals(al.get(0), "1");
		assertEquals(al.get(1), "10");
		assertEquals(al.get(2), "100");
		assertEquals(al.get(3), "33");
		assertEquals(al.get(4), "5");
	}

	@Test
	public void testSortNumbersWithNumFlag() {
		String[] inputArr = new String[] { "5", "100", "1", "10", "33" };
		List<String> al = sortApplication.sortNumbers(inputArr, true);
		System.out.println(al);
		assertEquals("1", al.get(0));
		assertEquals("5", al.get(1));
		assertEquals("10", al.get(2));
		assertEquals("33", al.get(3));
		assertEquals("100", al.get(4));
	}

	@Test
	public void testSortSpecialChars() {
		String[] inputArr = new String[] { "@$#", "&*(", "^%&", "!^&^" };
		List<String> al = sortApplication.sortSpecialChars(inputArr);
		assertEquals("!^&^", al.get(0));
		assertEquals("&*(", al.get(1));
		assertEquals("@$#", al.get(2));
		assertEquals("^%&", al.get(3));
	}

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

	@Test
	public void testSortSimpleNumbers() {
		String[] inputArr = new String[] { "1oranges", "100apples", "22pears",
				"january2016", "feb16" };
		List<String> al = sortApplication.sortSimpleNumbers(inputArr);
		assertEquals(al.get(0), "100apples");
		assertEquals(al.get(1), "1oranges");
		assertEquals(al.get(2), "22pears");
		assertEquals(al.get(3), "feb16");
		assertEquals(al.get(4), "january2016");
	}

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

	@Test
	public void testSortNumbersSpecialChars() {
		String[] inputArr = new String[] { "100 @", "1 &(*", "22#%!", "33 *&@",
				"#%356" };
		List<String> al = sortApplication.sortNumbersSpecialChars(inputArr,
				false);
		assertEquals("#%356", al.get(0));
		assertEquals("1 &(*", al.get(1));
		assertEquals("100 @", al.get(2));
		assertEquals("22#%!", al.get(3));
		assertEquals("33 *&@", al.get(4));
	}

	@Test
	public void testSortNumbersSpecialCharsWithNumFlag() {
		// String[] inputArr = new String[] {"100 @", "22#%!", "33 *&@"};
		// String[] inputArr = new String[] {"100 @", "22#%!", "33 *&@", "68!"
		// };
		String[] inputArr = new String[] { "100 @", "50 @", "1 &(*", "22#%!",
				"33 *&@", "#%356", "68!" };

		List<String> al = sortApplication.sortNumbersSpecialChars(inputArr,
				true);
		System.out.println(al);
		assertEquals("#%356", al.get(0));
		assertEquals("1 &(*", al.get(1));
		assertEquals("22#%!", al.get(2));
		assertEquals("33 *&@", al.get(3));
		assertEquals("50 @", al.get(4));
		assertEquals("68!", al.get(5));
	}

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

	@Test
	public void testSortSimpleNumbersSpecialChars() {
		String[] inputArr = new String[] { "&23jan", "100 pea*s", "jer100 *",
				"20 m!n", "chief) 24" };
		List<String> al = sortApplication.sortSimpleNumbersSpecialChars(
				inputArr, false);
		assertEquals("&23jan", al.get(0));
		assertEquals("100 pea*s", al.get(1));
		assertEquals("20 m!n", al.get(2));
		assertEquals("chief) 24", al.get(3));
		assertEquals("jer100 *", al.get(4));
	}

	@Test
	public void testSortSimpleNumbersSpecialCharsWithNumFlag() {
		String[] inputArr = new String[] { "&23jan", "100 pea*s", "jer100 *",
				"20 m!n", "chief) 24" };
		List<String> al = sortApplication.sortSimpleNumbersSpecialChars(
				inputArr, true);
		assertEquals("&23jan", al.get(0));
		assertEquals("20 m!n", al.get(1));
		assertEquals("100 pea*s", al.get(2));
		assertEquals("chief) 24", al.get(3));
		assertEquals("jer100 *", al.get(4));
	}

	@Test
	public void testSortCapitalNumbersSpecialChars() {
		String[] inputArr = new String[] { "AP1#E", "APP1E#", "6P3@R", "6PE@R",
				"&P3AL", "100 AP1#E", "30 AP1#E" };
		List<String> al = sortApplication.sortCapitalNumbersSpecialChars(
				inputArr, false);
		assertEquals("&P3AL", al.get(0));
		assertEquals("100 AP1#E", al.get(1));
		assertEquals("30 AP1#E", al.get(2));
		assertEquals("6P3@R", al.get(3));
		assertEquals("6PE@R", al.get(4));
		assertEquals("AP1#E", al.get(5));
		assertEquals("APP1E#", al.get(6));
	}

	@Test
	public void testSortCapitalNumbersSpecialCharsWithNumFlag() {
		String[] inputArr = new String[] { "AP1#E", "APP1E#", "6P3@R", "6PE@R",
				"&P3AL", "100 AP1#E", "30 AP1#E" };
		List<String> al = sortApplication.sortCapitalNumbersSpecialChars(
				inputArr, true);
		assertEquals("&P3AL", al.get(0));
		assertEquals("6P3@R", al.get(1));
		assertEquals("6PE@R", al.get(2));
		assertEquals("30 AP1#E", al.get(3));
		assertEquals("100 AP1#E", al.get(4));
		assertEquals("AP1#E", al.get(5));
		assertEquals("APP1E#", al.get(6));

	}

	@Test
	public void testSortAll() {
		String[] inputArr = new String[] { "100 B*anana", "20 B*anana",
				"#B@n@n0", "P3@r", "p3@R" };
		List<String> al = sortApplication.sortAll(inputArr, false);
		assertEquals("#B@n@n0", al.get(0));
		assertEquals("100 B*anana", al.get(1));
		assertEquals("20 B*anana", al.get(2));
		assertEquals("P3@r", al.get(3));
		assertEquals("p3@R", al.get(4));
	}

	@Test
	public void testSortAllNumFlag() {
		String[] inputArr = new String[] { "100 B*anana", "20 B*anana",
				"#B@n@n0", "P3@r", "p3@R" };
		List<String> al = sortApplication.sortAll(inputArr, true);
		assertEquals("#B@n@n0", al.get(0));
		assertEquals("20 B*anana", al.get(1));
		assertEquals("100 B*anana", al.get(2));
		assertEquals("P3@r", al.get(3));
		assertEquals("p3@R", al.get(4));
	}

}
