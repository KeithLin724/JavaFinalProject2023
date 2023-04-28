package Game.Loader;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

import Game.state.PlayerState;
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
    private static final BiFunction<String, Integer, BufferedImage> loadImageLambda = (fileName, i) -> {
        try {
            return ImageLoader.loadImage(ImageNamePath.imagePath(fileName + i));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    };

    /**
     * The function loads character images based on their state using
     * multithreading.
     * 
     * @param folderName  The name of the folder where the character images are
     *                    stored.
     * @param playerState PlayerState is an enum that represents the different
     *                    states a player character
     *                    can be in, such as standing, walking, jumping, etc. It
     *                    contains information about the number of
     *                    frames in the animation and the file name of the image for
     *                    each frame.
     * @return The method is returning an array of BufferedImages, which are loaded
     *         from image files based
     *         on the provided folder name and player state. The images are loaded
     *         using a lambda function and a
     *         thread pool to improve performance.
     */
    private static BufferedImage[] loadCharacterImageByState(String folderName, PlayerState playerState) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        Future<?>[] futures = IntStream
                .range(0, playerState.frameNumber)
                .mapToObj(i -> executorService
                        .submit(() -> loadImageLambda.apply(folderName + playerState.imageString, i)))
                .toArray(Future<?>[]::new);

        executorService.shutdown();

        return Arrays.stream(futures)
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
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

        ExecutorService executorService = Executors.newCachedThreadPool();

        Future<?>[] futures = Arrays.stream(PlayerState.ALL_PLAYER_STATES)
                .map(state -> executorService
                        .submit(() -> animations[state.saveArrayIndex] = loadCharacterImageByState(folderName, state)))
                .toArray(Future<?>[]::new);

        executorService.shutdown();

        try {
            // Wait for all tasks to complete
            for (var future : futures) {
                future.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
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

}
