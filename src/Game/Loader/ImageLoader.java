package Game.Loader;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;

import Game.GUI.UIConstant.Buttons;
import Game.state.PlayerState;
import base.loader.BaseLoader;

import static Game.GUI.ui.GameMenuButton.pitchesNumber;

public class ImageLoader {

    /**
     * It loads an image from a folder in the resources folder
     * 
     * @param folderName The folder name where the image is located.
     * @param fileName   The name of the file you want to load.
     * @return A BufferedImage object.
     */
    public static BufferedImage loadImage(String folderName, String fileName) throws IOException {
        return BaseLoader.loadImage(ImageNamePath.imagePath(folderName, fileName));
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
        return BaseLoader.loadImage(fileName);
    }

    /**
     * This is a lambda expression that defines a function that takes in a String
     * and an Integer as
     * parameters and returns a BufferedImage. It is used in the
     * `loadCharacterImageByState` method to load
     * a set of character images based on the player's state. The lambda expression
     * takes the `fileName`
     * parameter and appends the integer `i` to it to create the full file name for
     * each frame of the
     * animation. It then calls the `loadImage` method to load the image and returns
     * it as a BufferedImage.
     * If an IOException occurs during the loading process, it prints the stack
     * trace and returns null.
     */
    private static BiFunction<String, Integer, BufferedImage> loadImageLambda = (fileName, i) -> {
        try {
            return ImageLoader.loadImage(ImageNamePath.imagePath(fileName + i));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    };

    /**
     * This function loads a set of character images based on the player's state and
     * returns them as an
     * array of BufferedImages.
     * 
     * @param folderName  The name of the folder where the character images are
     *                    stored.
     * @param playerState PlayerState is an enum that represents the different
     *                    states a player character
     *                    can be in, such as standing, walking, jumping, etc. It
     *                    contains information about the number of
     *                    frames in the animation and the name of the image file for
     *                    each frame.
     * @return The method is returning an array of BufferedImages.
     */
    private static BufferedImage[] loadCharacterImageByState(String folderName, PlayerState playerState)
            throws IOException {

        return IntStream.range(0, playerState.frameNumber)
                .mapToObj(i -> loadImageLambda.apply(folderName + playerState.imageString, i))
                .toArray(BufferedImage[]::new);
    }

    /**
     * It loads a character's animations from a folder
     * 
     * @param characterState The number of states the character has.
     * @param frameNumber    The number of frames in the animation
     * @param folderName     The name of the folder where the images are stored.
     * @return A 2D array of BufferedImages.
     */
    public static BufferedImage[][] loadCharacterImage(String folderName, int characterState, int frameNumber)
            throws IOException {

        BufferedImage[][] animations = new BufferedImage[characterState][]; // frameNumber

        animations[PlayerState.IDLE.num] = loadCharacterImageByState(folderName, PlayerState.IDLE);
        animations[PlayerState.JUMP.num] = loadCharacterImageByState(folderName, PlayerState.JUMP);
        animations[PlayerState.FALLING.num] = loadCharacterImageByState(folderName, PlayerState.FALLING);
        animations[PlayerState.ATTACKING.num] = loadCharacterImageByState(folderName, PlayerState.ATTACKING);
        animations[PlayerState.MOVING.num] = loadCharacterImageByState(folderName, PlayerState.MOVING);

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
    public static BufferedImage[] loadBackgroundImage(String fileName, int heightBlockNum, int widthBlockNum, int pixel)
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

    public static BufferedImage[] loadMenuButtonImage(String fileName, int selectImageRowIndex) throws IOException {
        BufferedImage imageOri = loadImage(fileName);

        return IntStream
                .range(0, pitchesNumber)
                .mapToObj(i -> imageOri.getSubimage(i * Buttons.B_WIDTH_DEFAULT.value,
                        selectImageRowIndex * Buttons.B_HEIGHT_DEFAULT.value,
                        Buttons.B_WIDTH_DEFAULT.value,
                        Buttons.B_HEIGHT_DEFAULT.value))
                .toArray(BufferedImage[]::new);
    }
}
