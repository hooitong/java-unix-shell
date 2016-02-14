package sg.edu.nus.comp.cs4218.impl;

import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class GlobFinder extends SimpleFileVisitor<Path>{
    private final PathMatcher matcher;
    private List<String> filePaths;

    public GlobFinder(String pattern) {
        filePaths = new ArrayList<>();
        matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
    }

    void globFind(Path file) {
        Path name = file.getFileName();
        if(name != null && matcher.matches(name)) {
            filePaths.add(file.toString());
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
}
