package base.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

/**
 * this class is a base class for loading different file or image
 */
public class BaseLoader {

    private static final Logger LOGGER = Logger.getLogger(BaseLoader.class.getName());

    /**
     * The function takes a path as input and returns its absolute path as a string.
     * 
     * @param path The path parameter is a string representing a file or directory
     *             path, which may be
     *             relative or absolute.
     * @return The method `pathToAbsolutePath` returns a `String` that represents
     *         the absolute path of the
     *         file or directory specified by the input `path`.
     */

    private static String pathToAbsolutePath(String path) {
        return new File(path).getAbsolutePath();
    }

    /**
     * This function loads a file from a given file name and returns an input
     * stream.
     * 
     * @param fileName The name of the file to be loaded.
     * @return The method is returning an InputStream object.
     */
    public static InputStream loadFile(String fileName) throws FileNotFoundException {
        String absPath = pathToAbsolutePath(fileName);
        LOGGER.info("loading" + absPath);
        return new FileInputStream(absPath);
    }

    /**
     * Convert the cover image to a BufferedImage.
     * 
     * @param inputStream The input stream of the image file.
     * @return A BufferedImage object.
     */
    public static BufferedImage convertToImage(InputStream inputStream) throws IOException {
        return ImageIO.read(inputStream);
    }

    /**
     * This function loads an image file and converts it into a BufferedImage
     * object.
     * 
     * @param fileName A String representing the name or path of the image file to
     *                 be loaded.
     * @return A BufferedImage object is being returned.
     */
    public static BufferedImage loadImage(String fileName) throws IOException {
        return convertToImage(loadFile(fileName));
    }

    /**
     * It returns the URI of a file in the same directory as the class file of the
     * class passed as the
     * first argument
     * 
     * @param cls      The class that is calling the method.
     * @param fileName The name of the file to be read.
     * @return A URI object.
     */
    public static <T> URI getURI(Class<T> cls, String fileName) throws URISyntaxException {
        return cls.getResource(fileName).toURI();
    }

    /**
     * This function loads the contents of a text file into a list of strings in
     * Java.
     * 
     * @param fileName The name of the text file that needs to be loaded.
     * @return A `List` of `String` objects is being returned. The method
     *         `loadTextFile` reads all the
     *         lines from a text file specified by the `fileName` parameter and
     *         returns them as a list of strings.
     */
    public static List<String> loadTextFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(pathToAbsolutePath(fileName)));
    }

}
