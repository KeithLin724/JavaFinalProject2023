package Game.Loader;

import java.awt.image.BufferedImage;
import java.io.IOException;

// import Game.gameConstant.PlayerConstants;
import Game.gameConstant.PlayerState;
import base.loader.BaseLoader;

public class ImageLoader {

    /**
     * It loads an image from a folder in the resources folder
     * 
     * @param folderName The folder name where the image is located.
     * @param fileName   The name of the file you want to load.
     * @return A BufferedImage object.
     */
    public static BufferedImage loadImage(String folderName, String fileName) throws IOException {
        return BaseLoader.loadImage(ImageLoader.class, ImageNamePath.imagePath(folderName, fileName));
    }

    /**
     * This function loads an image file and returns it as a BufferedImage object in
     * Java.
     * 
     * @param fileName The fileName parameter is a String that represents the name
     *                 of the image file that
     *                 needs to be loaded.
     * @return A BufferedImage object is being returned.
     */
    public static BufferedImage loadImage(String fileName) throws IOException {
        return BaseLoader.loadImage(ImageLoader.class, fileName);
    }

    /**
     * It loads a character's animations from a folder
     * 
     * @param characterState The number of states the character has.
     * @param frameNumber    The number of frames in the animation
     * @param folderName     The name of the folder where the images are stored.
     * @return A 2D array of BufferedImages.
     */
    public static BufferedImage[][] loadCharacter(String folderName, int characterState, int frameNumber)
            throws IOException {

        BufferedImage[][] animations = new BufferedImage[characterState][frameNumber];

        for (int i = 0; i < PlayerState.IDLE.getAnimationFrameNumbs(); i++) {
            animations[PlayerState.IDLE.num][i] = ImageLoader.loadImage(folderName, "IDLE_" + i);
        }

        for (int i = 0; i < PlayerState.ATTACKING.getAnimationFrameNumbs(); i++) {
            animations[PlayerState.ATTACKING.num][i] = ImageLoader.loadImage(folderName, "ATTACK_" + i);
        }

        for (int i = 0; i < PlayerState.MOVING.getAnimationFrameNumbs(); i++) {
            animations[PlayerState.MOVING.num][i] = ImageLoader.loadImage(folderName, "MOVE_" + i);
        }

        return animations;
    }

    /**
     * This function loads an image file and splits it into smaller blocks of a
     * specified size.
     * 
     * @param fileName       The name of the image file to be loaded as the
     *                       background.
     * @param heightBlockNum The number of blocks (or tiles) in the vertical
     *                       direction of the background
     *                       image.
     * @param widthBlockNum  The number of blocks (or sections) horizontally in the
     *                       image.
     * @param pixel          The size of each block in pixels.
     * @return The method is returning an array of BufferedImages, which represents
     *         the loaded background
     *         image divided into smaller blocks.
     */
    public static BufferedImage[] loadBackground(String fileName, int heightBlockNum, int widthBlockNum, int pixel)
            throws IOException {

        BufferedImage oriImage = loadImage(fileName);
        BufferedImage[] mapBlock = new BufferedImage[heightBlockNum * widthBlockNum];

        for (int row = 0; row < heightBlockNum; row++) {
            for (int col = 0; col < widthBlockNum; col++) {

                int index = row * widthBlockNum + col;
                mapBlock[index] = oriImage.getSubimage(col * pixel, row * pixel, pixel, pixel);
            }
        }

        return mapBlock;
    }
}
