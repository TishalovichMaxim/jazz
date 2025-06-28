package by.tishalovichm.jazz;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PackagesScanner {

    private static final Logger logger = Logger.getLogger(PackagesScanner.class.getName());

    private String convertClassFileToClassFullName(String workDir, Path classPath) {
        char separatorChar = File.separatorChar;
        return classPath.toString()
            .substring(workDir.length() + 1, classPath.toString().length() - ".class".length())
            .replace(separatorChar, '.');
    }

    public List<Class<?>> scan(String prefix) throws IOException, ClassNotFoundException {
        List<String> classesNames = new ArrayList<>();

        String workingDir = System.getProperty("user.dir");
        logger.log(Level.INFO, "Working dir: {0}", workingDir);
        String classPathStr = System.getProperty("java.class.path");
        logger.log(Level.INFO, "Class path: {0}", classPathStr);

        String[] classPathParts = classPathStr.split(";");
        for (String classPathPart : classPathParts) {
            ClassesCollector classesCollector = new ClassesCollector();

            if (classPathPart.endsWith("*")) {
                continue;
            }

            Path classesLocation = Path.of(workingDir, classPathPart);
            Files.walkFileTree(classesLocation, classesCollector);

            List<String> classes = classesCollector.getClasses()
                    .stream()
                    .map(p -> convertClassFileToClassFullName(classesLocation.toString(), p))
                    .toList();

            logger.log(Level.INFO, "In location '{0}', found classes: {1}",
                new Object[] {classesLocation, classes});

            classesNames.addAll(classes);
        }

        List<Class<?>> classes = new ArrayList<>();
        for (String className : classesNames) {
            if (className.startsWith(prefix)) {
                classes.add(Class.forName(className));
            }
        }

        return classes;
    }

}
