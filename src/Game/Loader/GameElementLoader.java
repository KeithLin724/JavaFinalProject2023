package Game.Loader;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.BiFunction;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import Game.DataPass;
import Game.Player;
import Game.DataPass.AniData;
import Game.DataPass.GamePlayerSpeedData;
import Game.DataPass.ImageScaleData;
import Game.audio.GameAudio;
import Game.audio.GameAudioPlayer;
import Game.builder.GameCharacterBuilder;
import Game.role.GameEnemy;
import base.loader.BaseLoader;
import base.loader.FileNameFormatter;

import static base.BaseGameConstant.TILES_SIZE;

import static Game.GameSourceFilePath.BACKGROUND_SKIN_FOLDER_PATH;

// Factory 
public final class GameElementLoader {

    private static final Logger LOGGER = Logger.getLogger(GameElementLoader.class.getName());

    public Player gameCharacter() {
        return null;
    }

    public static Player getTestingGameCharacter() {
        return new GameCharacterBuilder()
                .setAniData(new AniData(0, 0, 35))
                .setImageScale(new ImageScaleData(0, 0, 10))
                .setGamePlayerSpeedData(new GamePlayerSpeedData(5.0f))
                .build();
    }

    public static Player getTestingGameCharacter(String fileName) {
        try {
            return loadCharacterByPath(fileName);

        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static BufferedImage[] loadBackgroundSkinImageFromTextList(String fileName) throws IOException {
        var fileNameList = BaseLoader.loadTextFile(fileName);
        List<BufferedImage> loadResult = new ArrayList<>();

        for (var imageFileName : fileNameList) {
            loadResult.add(ImageLoader.loadImage(BACKGROUND_SKIN_FOLDER_PATH, imageFileName));
        }

        return loadResult.toArray(BufferedImage[]::new);
    }

    /**
     * This function loads a game character from a file path and returns it.
     * 
     * @param fileName The name of the file that contains the data needed to load a
     *                 GameCharacter object.
     * @return The method is returning a GameCharacter object.
     */
    public static Player loadCharacterByPath(String fileName)
            throws IOException, URISyntaxException, NoSuchMethodException, SecurityException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        GameCharacterBuilder gameCharacterBuilder = new GameCharacterBuilder();
        var fileData = BaseLoader.loadTextFile(fileName);

        String path = fileData.get(0);
        String stateFrame = fileData.get(1);

        // state data
        var stateData = DataPass.stringDataToIntArray(stateFrame);
        gameCharacterBuilder.setAnimationImage(path, stateData[0], stateData[1]);

        // ani data
        String aniData = fileData.get(2);
        var aniDataArray = DataPass.stringDataToIntArray(aniData);
        gameCharacterBuilder.setAniData(DataPass.build(aniDataArray, AniData.class));

        // img data
        String imgData = fileData.get(3);
        var imgDataArr = DataPass.stringDataToIntArray(imgData);
        gameCharacterBuilder.setImageScale(DataPass.build(imgDataArr, ImageScaleData.class));

        // player speed
        float playerSpeed = Float.parseFloat(fileData.get(4));
        gameCharacterBuilder.setGamePlayerSpeedData(new GamePlayerSpeedData(playerSpeed));

        return gameCharacterBuilder.build();
    }

    // this is a function for load the pixel where to put
    // for the GameMapLevelManager
    /**
     * This function loads game level data from an image file and returns it as a 2D
     * integer array.
     * 
     * @param fileName         The name of the file containing the game level data.
     * @param checkHeightBlock The height of each block in pixels that is checked in
     *                         the level image.
     * @param checkWidthBlock  The width of each block in pixels that is being
     *                         checked in the level
     *                         image.
     * @return The method is returning a 2D integer array called `levelData`.
     */
    public static int[][] loadGameLevelData(String fileName, int checkHeightBlock, int checkWidthBlock)
            throws IOException {

        BufferedImage levelImage = ImageLoader.loadImage(fileName);
        int[][] levelData = new int[levelImage.getHeight()][levelImage.getWidth()];

        for (int row = 0; row < levelImage.getHeight(); row++) {
            for (int col = 0; col < levelImage.getWidth(); col++) {
                Color color = new Color(levelImage.getRGB(col, row));

                int value = color.getRed();

                if (value >= checkHeightBlock * checkWidthBlock) {
                    value = 0;
                }

                levelData[row][col] = value;
            }
        }

        return levelData;
    }

    /**
     * This function loads game enemy data from a specified file and creates enemy
     * objects based on the
     * level data in the file.
     * 
     * @param gameLevelFileName The file name of the image file that contains the
     *                          level data for the game
     *                          enemies.
     * @param checkHeightBlock  The height of the block to check for enemy data in
     *                          the game level file.
     * @param checkWidthBlock   The width of the block being checked in the game
     *                          level.
     * @param enemyImage        A 2D array of BufferedImages representing the
     *                          different images for each type of
     *                          enemy in the game.
     * @param gameAudioPlayer   An object of the GameAudioPlayer class, which is
     *                          responsible for playing
     *                          audio in the game.
     * @return An ArrayList of GameEnemy objects.
     */
    public static ArrayList<GameEnemy> loadGameEnemyData(
            String gameLevelFileName,
            int checkHeightBlock, int checkWidthBlock,
            BufferedImage[][] enemyImage,
            GameAudioPlayer gameAudioPlayer)

            throws IOException {

        BufferedImage levelImage = ImageLoader.loadImage(gameLevelFileName);
        ArrayList<GameEnemy> enemies = new ArrayList<>();

        for (int row = 0; row < levelImage.getHeight(); row++) {
            for (int col = 0; col < levelImage.getWidth(); col++) {
                Color color = new Color(levelImage.getRGB(col, row));

                int value = color.getGreen();

                if (value == GameEnemy.levelDataID) {
                    enemies.add(new GameEnemy(col * TILES_SIZE, row * TILES_SIZE, enemyImage, gameAudioPlayer));
                }

            }
        }

        return enemies;

    }

    /**
     * A lambda expression that defines a function that takes in two parameters, a
     * String `folderPath` and
     * a `GameAudio` object, and returns an `Optional` of `Clip`. The function loads
     * an audio clip from a
     * file path generated using the `folderPath` and `gameAudio.fileName`
     * parameters, using the
     * `BaseLoader.loadClip` method. If the clip cannot be loaded, the function logs
     * an error message using
     * the `LOGGER` object and returns an empty `Optional`. Otherwise, it returns an
     * `Optional` containing
     * the loaded `Clip`. This lambda expression is used in the `loadClip` method to
     * asynchronously load
     * multiple audio clips.
     */
    private static final BiFunction<String, GameAudio, Optional<Clip>> loadClipLambda = (folderPath, gameAudio) -> {
        Clip clip = null;
        String fileName = FileNameFormatter.of(folderPath + gameAudio.fileName, FileNameFormatter.SOUND);
        try {
            clip = BaseLoader.loadClip(fileName);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            LOGGER.log(Level.SEVERE, "load clip error FileName: " + fileName, e);

        }
        return Optional.ofNullable(clip);
    };

    /**
     * The function loads audio clips from a specified folder path using a list of
     * GameAudio objects and
     * returns an array of Clip objects.
     * 
     * @param folderPath  A string representing the path to the folder containing
     *                    the audio files to be
     *                    loaded as Clips.
     * @param listOfAudio An array of GameAudio objects that represent the audio
     *                    files to be loaded as Clip
     *                    objects.
     * @return The method is returning an array of Clip objects.
     */
    public static Clip[] loadClip(String folderPath, GameAudio[] listOfAudio) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        Future<?>[] futures = Arrays.stream(listOfAudio)
                .map((audio) -> executorService.submit(
                        () -> loadClipLambda.apply(folderPath, audio).orElse(null)))
                .toArray(Future<?>[]::new);

        executorService.shutdown();

        return Arrays.stream(futures)
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        LOGGER.log(Level.SEVERE, "load list clip error", e);
                    }
                    return null;
                }).toArray(Clip[]::new);
    }
}
