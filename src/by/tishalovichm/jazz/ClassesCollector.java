package by.tishalovichm.jazz;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class ClassesCollector extends SimpleFileVisitor<Path> {

    private final List<Path> classes = new ArrayList<>();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (file.toString().endsWith(".class")) {
            classes.add(file);
        }

        return FileVisitResult.CONTINUE;
    }

    public List<Path> getClasses() {
        return classes;
    }

}
