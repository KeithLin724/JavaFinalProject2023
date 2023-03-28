package base.loader;

import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

// this class is a base class for loading different file or image
public class BaseLoader {

    public static InputStream loadFile(Object obj, String fileName) {
        return obj.getClass().getResourceAsStream(fileName);
    }

    public static BufferedImage coverToImage(InputStream inputStream) throws IOException {
        return ImageIO.read(inputStream);
    }

}
