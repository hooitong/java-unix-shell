package sg.edu.nus.comp.cs4218.impl;

import org.junit.Before;
import org.junit.Test;
import sg.edu.nus.comp.cs4218.Environment;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class GlobFinderTest {
    private String mockSinglePattern, mockMultiPattern, mockRootDirectory;
    private GlobFinder singleFinder, nestedFinder;

    @Before
    public void setUp() {
        mockSinglePattern = "*.txt";
        mockMultiPattern = "*/*/*.txt";
        singleFinder = new GlobFinder(mockSinglePattern);
        nestedFinder = new GlobFinder(mockMultiPattern);
        mockRootDirectory = Environment.currentDirectory + "mock-filesystem/";
    }

    @Test
    public void testValidGlobFind() throws Exception {
        Path mockAbsolutePath = Paths.get(mockRootDirectory + "quantum.txt");
        singleFinder.globFind(mockAbsolutePath);
        assert(!singleFinder.getResults().isEmpty());
        assert(singleFinder.getResults().get(0).equals(mockAbsolutePath.toString()));
    }

    @Test
    public void testInvalidGlobFind() throws Exception {
        Path mockAbsolutePath = Paths.get(mockRootDirectory + "tango/Kat.cpp");
        singleFinder.globFind(mockAbsolutePath);
        assert(singleFinder.getResults().isEmpty());
    }

    @Test
    public void testValidNestedGlobFind() throws Exception {
        Path mockAbsolutePath = Paths.get(mockRootDirectory + "21-herb/hola/kappa.txt");
        nestedFinder.globFind(mockAbsolutePath);
        assert(!nestedFinder.getResults().isEmpty());
    }

    @Test
    public void testInvalidNestedGlobFind() throws Exception {
        Path mockRelativePath = Paths.get("apple/vadar.txt");
        nestedFinder.globFind(mockRelativePath);
        assert(nestedFinder.getResults().isEmpty());
    }
}