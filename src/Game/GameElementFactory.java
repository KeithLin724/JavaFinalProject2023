package Game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;

import Game.GUI.UIConstant.MenuButtons;
import Game.GUI.UIConstant.PauseLayerButtons;
import Game.GUI.UIConstant.URMButtons;
import Game.GUI.UIConstant.VolumeButtons;
import Game.GUI.ui.buttons.GameMenuButton;
import Game.GUI.ui.buttons.GameSoundButton;
import Game.GUI.ui.buttons.GameURMButton;
import Game.GUI.ui.buttons.GameVolumeButton;
import Game.Loader.ImageLoader;
import Game.gameBase.GamePoint;
import Game.state.GameState;

public class GameElementFactory {
    public GameElementFactory() {

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

    /**
     * This function creates two sound buttons for a game with specified images and
     * positions.
     * 
     * @param musicsPoints A GamePoint object representing the position of the music
     *                     sound button on the
     *                     game screen.
     * @param sfxPoints    The location where the sound effects button will be
     *                     placed on the game screen.
     * @return An array of GameSoundButton objects, containing a music button and a
     *         sound effects button.
     */
    public static GameSoundButton[] getAllGameSoundButton(GamePoint musicsPoints, GamePoint sfxPoints)
            throws IOException {

        var imageOri = ImageLoader.loadImage(GameSourceFilePath.SOUND_BUTTON_IMAGE);

        BufferedImage[][] image = new BufferedImage[GameSoundButton.BUTTON_ROW_NUMBER][GameSoundButton.BUTTON_COLUMN_NUMBER];

        for (int row = 0; row < GameSoundButton.BUTTON_ROW_NUMBER; row++) {
            for (int col = 0; col < GameSoundButton.BUTTON_COLUMN_NUMBER; col++) {

                int size = PauseLayerButtons.SOUND_SIZE_DEFAULT.value;
                image[row][col] = imageOri.getSubimage(col * size, row * size, size, size);

            }
        }

        GameSoundButton musicBtn = new GameSoundButton(musicsPoints);
        musicBtn.setSoundImages(image);

        GameSoundButton sfxBtn = new GameSoundButton(sfxPoints);
        sfxBtn.setSoundImages(image);

        return new GameSoundButton[] { musicBtn, sfxBtn };
    }

    private static final BiFunction<BufferedImage, Integer, BufferedImage[]> getURMImageByRow = (image,
            rowIndex) -> IntStream
                    .range(0, GameURMButton.pitchesNumber)
                    .mapToObj(i -> image.getSubimage(i * URMButtons.URM_SIZE_DEFAULT.value,
                            rowIndex * URMButtons.URM_SIZE_DEFAULT.value,
                            URMButtons.URM_SIZE_DEFAULT.value,
                            URMButtons.URM_SIZE_DEFAULT.value))
                    .toArray(BufferedImage[]::new);

    /**
     * This Java function returns an array of GameURMButton objects with specified
     * GamePoint coordinates
     * and images obtained from a loaded image file.
     * 
     * @param menuPt    The location of the "Menu" button on the game screen.
     * @param replayPt  A GamePoint object representing the coordinates of the
     *                  replay button in the game.
     * @param unpausePt The GamePoint object representing the coordinates of the
     *                  "unpause" button in the
     *                  game.
     * @return An array of GameURMButton objects.
     */
    public static GameURMButton[] getAllGameURMButton(GamePoint menuPt, GamePoint replayPt, GamePoint unpausePt)
            throws IOException {
        var oriImage = ImageLoader.loadImage(GameSourceFilePath.URM_BUTTON_IMAGE);

        ExecutorService executorService = Executors.newCachedThreadPool();

        Future<?>[] futures = IntStream
                .rangeClosed(0, 2)
                .mapToObj(i -> executorService.submit(() -> getURMImageByRow.apply(oriImage, i)))
                .toArray(Future<?>[]::new);

        executorService.shutdown();

        var imageCut = Arrays
                .stream(futures)
                .map(i -> {
                    try {
                        return i.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .toArray(BufferedImage[][]::new);

        return new GameURMButton[] {
                new GameURMButton(menuPt, imageCut[2]),
                new GameURMButton(replayPt, imageCut[1]),
                new GameURMButton(unpausePt, imageCut[0])
        };
    }

    /**
     * The function returns a GameVolumeButton object with images loaded from a file
     * and positioned at a
     * given GamePoint.
     * 
     * @param point The location on the game screen where the volume button will be
     *              placed.
     * @return The method is returning a GameVolumeButton object.
     */
    public static GameVolumeButton getAllGameVolumeButton(GamePoint point) throws IOException {
        var oriImage = ImageLoader.loadImage(GameSourceFilePath.VOLUME_BUTTON_IMAGE);

        var volumeImage = IntStream
                .range(0, GameVolumeButton.pitchesNumber)
                .mapToObj(i -> oriImage.getSubimage(i * VolumeButtons.VOLUME_DEFAULT_WIDTH.value,
                        0,
                        VolumeButtons.VOLUME_DEFAULT_WIDTH.value,
                        VolumeButtons.VOLUME_DEFAULT_HEIGHT.value))
                .toArray(BufferedImage[]::new);

        var sliderImage = oriImage.getSubimage(3 * VolumeButtons.VOLUME_DEFAULT_WIDTH.value, 0,
                VolumeButtons.SLIDER_DEFAULT_WIDTH.value,
                VolumeButtons.VOLUME_DEFAULT_HEIGHT.value);

        return new GameVolumeButton(point, volumeImage, sliderImage);
    }
}
