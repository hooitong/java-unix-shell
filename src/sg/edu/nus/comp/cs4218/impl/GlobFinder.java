package sg.edu.nus.comp.cs4218.impl;

import java.io.File;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class GlobFinder extends SimpleFileVisitor<Path> {
	private final PathMatcher matcher;
	private final List<String> filePaths;

	public GlobFinder(String pattern, String absoluteRoot) {
		filePaths = new ArrayList<>();
		String globPattern = separatorsToSystem("glob:" + absoluteRoot + '/' + pattern);
		matcher = FileSystems.getDefault().getPathMatcher(globPattern);
	}

	void globFind(Path path) {
		if (path != null && matcher.matches(path)) {
			filePaths.add(path.toString());
		}
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
		globFind(file);
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
		globFind(dir);
		return FileVisitResult.CONTINUE;
	}

	public List<String> getResults() {
		return filePaths;
	}

	private String separatorsToSystem(String path) {
		if (path == null) {
			return null;
		}
		/* If Windows System */
		if (File.separatorChar == '\\') {
			return path.replace('/', File.separatorChar).replace("\\", "\\\\");
		} else { /* If Unix System */
			return path.replace('\\', File.separatorChar);
		}
	}
}
