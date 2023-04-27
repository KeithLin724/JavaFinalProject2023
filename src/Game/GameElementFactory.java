package Game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.IntStream;

import Game.GUI.UIConstant.MenuButtons;
import Game.GUI.ui.GameMenuButton;
import Game.Loader.ImageLoader;
import Game.gameBase.GamePoint;
import Game.state.GameState;

public class GameElementFactory {
    public GameElementFactory() {

    }

    // using file to load the game character data
    public GameCharacter gameCharacterFactory() {
        return new GameCharacter();
    }

    /**
     * This function returns an array of sub-images from a given image using
     * multi-threading.
     * 
     * @param image               A BufferedImage object that contains the image
     *                            from which the subimages will be
     *                            extracted.
     * @param selectImageRowIndex The index of the row in the image that contains
     *                            the button images for the
     *                            selected state.
     * @return The method is returning an array of BufferedImages. The number of
     *         BufferedImages in the
     *         array is determined by the constant value "pitchesNumber" in the
     *         GameMenuButton class. Each
     *         BufferedImage is a subimage of the input image, created using the
     *         "getSubimage" method. The subimage
     *         is selected based on the selectImageRowIndex parameter passed to the
     *         method. The method uses
     *         multithreading
     */
    private static BufferedImage[] getGameMenuButton(BufferedImage image, int selectImageRowIndex) {
        Function<Integer, BufferedImage> bufferedImageBuilder = i -> image.getSubimage(
                i * MenuButtons.B_WIDTH_DEFAULT.value,
                selectImageRowIndex * MenuButtons.B_HEIGHT_DEFAULT.value,
                MenuButtons.B_WIDTH_DEFAULT.value,
                MenuButtons.B_HEIGHT_DEFAULT.value);

        ExecutorService executorService = Executors.newCachedThreadPool();

        Future<?>[] futures = IntStream
                .range(0, GameMenuButton.pitchesNumber)
                .mapToObj(i -> executorService.submit(() -> bufferedImageBuilder.apply(i)))
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
     * This function returns an array of GameMenuButtons created using a cached
     * thread pool and a future
     * object.
     * 
     * @param xPos An array of floats representing the x-coordinates of the menu
     *             buttons.
     * @param yPos The yPos parameter is an array of floats representing the
     *             y-coordinates of the menu
     *             buttons.
     * @return The method is returning an array of GameMenuButton objects.
     */
    public static GameMenuButton[] getAllMenuButtons(float[] xPos, float[] yPos) throws IOException {

        var oriImage = ImageLoader.loadImage(GameSourceFilePath.MENU_BUTTON_IMAGE_1);

        Function<Integer, GameMenuButton> gameMenuBuilder = i -> new GameMenuButton(
                new GamePoint(xPos[i], yPos[i]),
                getGameMenuButton(oriImage, i),
                GameState.MENU_GAME_STATES_CHOOSE[i]);

        ExecutorService executorService = Executors.newCachedThreadPool();

        Future<?>[] futures = IntStream
                .range(0, GameState.MENU_GAME_STATES_CHOOSE.length)
                .mapToObj(i -> executorService.submit(() -> gameMenuBuilder.apply(i)))
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
                .toArray(GameMenuButton[]::new);
    }
}
