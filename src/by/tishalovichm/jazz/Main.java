package by.tishalovichm.jazz;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Logger logger = Logger.getLogger("");
        logger.setLevel(Level.WARNING);

        PackagesScanner packagesScanner = new PackagesScanner();
        List<Class<?>> classes = packagesScanner.scan("by");

        logger.warning(classes.toString());
    }
}
