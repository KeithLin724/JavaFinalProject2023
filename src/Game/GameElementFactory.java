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

import Game.GUI.UIConstant.Buttons;
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

    public static GameMenuButton getGameMenuButton(float xPos, float yPos, int index, GameState state)
            throws IOException {
        return new GameMenuButton(new GamePoint(xPos, yPos),
                ImageLoader.loadMenuButtonImage(GameSourceFilePath.MENU_BUTTON_IMAGE_1, index),
                state);
    }

    private static BufferedImage[] getGameMenuButton(BufferedImage image, int selectImageRowIndex) {
        Function<Integer, BufferedImage> bufferedImageBuilder = i -> image.getSubimage(
                i * Buttons.B_WIDTH_DEFAULT.value,
                selectImageRowIndex * Buttons.B_HEIGHT_DEFAULT.value,
                Buttons.B_WIDTH_DEFAULT.value,
                Buttons.B_HEIGHT_DEFAULT.value);

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

    public static GameMenuButton[] getAllMenuButtons(float[] xPos, float[] yPos) throws IOException {

        var oriImage = ImageLoader.loadImage(GameSourceFilePath.MENU_BUTTON_IMAGE_1);

        Function<Integer, GameMenuButton> gameMenuBuilder = i -> new GameMenuButton(
                new GamePoint(xPos[i], yPos[i]),
                getGameMenuButton(oriImage, i),
                GameState.ALL_GAME_STATES[i]);

        ExecutorService executorService = Executors.newCachedThreadPool();

        Future<?>[] futures = IntStream
                .range(0, GameState.ALL_GAME_STATES.length)
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
