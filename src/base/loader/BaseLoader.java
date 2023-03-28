package base.loader;

import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

// this class is a base class for loading different file or image
public class BaseLoader {

    /**
     * It returns an InputStream for the file specified by the fileName parameter
     * 
     * @param obj      The object that is calling the method.
     * @param fileName The name of the file to load.
     * @return The InputStream of the file.
     */
    public static InputStream loadFile(Object obj, String fileName) {
        return obj.getClass().getResourceAsStream(fileName);
    }

    /**
     * Load a file from the classpath.
     * 
     * @param cls      The class that is calling the method.
     * @param fileName The name of the file to load.
     * @return An InputStream object.
     */
    public static <T> InputStream loadFile(Class<T> cls, String fileName) {
        return cls.getResourceAsStream(fileName);
    }

    /**
     * Convert the cover image to a BufferedImage.
     * 
     * @param inputStream The input stream of the image file.
     * @return A BufferedImage object.
     */
    public static BufferedImage coverToImage(InputStream inputStream) throws IOException {
        return ImageIO.read(inputStream);
    }

    /**
     * "Loads an image from a file and returns it as a BufferedImage."
     * 
     * The first line of the function is the function header. It tells us the
     * function's name, the type of
     * data it returns, and the type of data it takes as input
     * 
     * @param cls      The class that the file is in.
     * @param fileName The name of the file to load.
     * @return A BufferedImage object.
     */
    public static <T> BufferedImage loadImage(Class<T> cls, String fileName) throws IOException {
        return coverToImage(loadFile(cls, fileName));
    }

}
