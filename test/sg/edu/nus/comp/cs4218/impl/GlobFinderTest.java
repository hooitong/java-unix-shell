package sg.edu.nus.comp.cs4218.impl;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;

public class GlobFinderTest {
	private String mockRootDirectory;
	private GlobFinder singleFinder, nestedFinder;

	@Before
	public void setUp() {
		String mockSinglePattern = "*.txt";
		String mockMultiPattern = "*/*/*.txt";
		singleFinder = new GlobFinder(mockSinglePattern);
		nestedFinder = new GlobFinder(mockMultiPattern);
		mockRootDirectory = Environment.currentDirectory + "mock-filesystem/";
	}

	/**
	 * TODO: Need to test for unquoted asterisks
	 */

    /**
     * Test a simple glob pattern (non-nested) on a valid file that should be matched.
     *
     * @throws Exception
     */
	@Test
	public void testValidGlobFind() throws Exception {
		Path mockAbsolutePath = Paths.get(mockRootDirectory + "quantum.txt");
		singleFinder.globFind(mockAbsolutePath);
		assert (!singleFinder.getResults().isEmpty());
		assert (singleFinder.getResults().get(0).equals(mockAbsolutePath.toString()));
	}

    /**
     *  Test a simple glob pattern (non-nested) on a invalid file that should not be matched.
     *
     * @throws Exception
     */
	@Test
	public void testInvalidGlobFind() throws Exception {
		Path mockAbsolutePath = Paths.get(mockRootDirectory + "tango/Kat.cpp");
		singleFinder.globFind(mockAbsolutePath);
		assert (singleFinder.getResults().isEmpty());
	}

    /**
     *  Test a glob pattern (nested) on a valid file that should be matched.
     *
     * @throws Exception
     */
	@Test
	public void testValidNestedGlobFind() throws Exception {
		Path mockAbsolutePath = Paths.get(mockRootDirectory + "21-herb/hola/kappa.txt");
		nestedFinder.globFind(mockAbsolutePath);
		assert (!nestedFinder.getResults().isEmpty());
	}

    /**
     *  Test a glob pattern (nested) on a invalid file that should not be matched.
     *
     * @throws Exception
     */
	@Test
	public void testInvalidNestedGlobFind() throws Exception {
		Path mockRelativePath = Paths.get("apple/vadar.txt");
		nestedFinder.globFind(mockRelativePath);
		assert (nestedFinder.getResults().isEmpty());
	}
}