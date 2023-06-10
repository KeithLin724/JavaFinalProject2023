package Game.gameBackground;

import Game.GameSourceFilePath;
import Game.Loader.ImageLoader;
import Game.PLUG.gameDrawer.GameAnimatedDrawer;
import Game.PLUG.gameDrawer.GameRenderOffsetPass;
import Game.state.GameState;
import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Logger;

import static base.BaseGameConstant.TILES_SIZE;

// https://www.youtube.com/watch?v=et5JeT-ESKk&list=PL4rzdwizLaxYmltJQRjq18a9gsSyEQQ-0&index=9

public class GameLevelManager implements GameAnimatedDrawer, GameRenderOffsetPass {
    private static final Logger LOGGER = Logger.getLogger(GameLevelManager.class.getName());
    // new version // 12 * 10

    public static final int HEIGHT_BLOCK_NUM = 10; // 4
    public static final int WIDTH_BLOCK_NUM = 12;

    public static final int MAX_NUMBER = HEIGHT_BLOCK_NUM * WIDTH_BLOCK_NUM;

    public static final int PIXEL = 32;

    private Game game;
    // about save the pixel block
    private final BufferedImage[] bgMapImage;

    // about display the block where to display
    private GameLevel gameLevel1; // level 1
    private float drawXOffset;

    private int levelIndex = 0;

    public GameLevelManager(Game game) throws IOException {
        this.game = game;
        this.bgMapImage = ImageLoader.loadBackgroundImage(GameSourceFilePath.BACKGROUND_IMAGE_PATH_CITY,
                HEIGHT_BLOCK_NUM,
                WIDTH_BLOCK_NUM,
                PIXEL);
        // System.out.println(this.bgMapImage.length);

        this.gameLevel1 = GameLevel.loadFromFile(GameSourceFilePath.BACKGROUND_LEVEL_2);
    }

    public void setGameLevelFromFile(String fileName) throws IOException {
        this.gameLevel1 = GameLevel.loadFromFile(fileName);
    }

    @Override
    public void passOffset(float offset) {
        this.drawXOffset = offset;
    }

    @Override
    public void render(Graphics2D g) {

        for (int row = 0; row < Game.TILES_IN_HEIGHT; row++) {
            for (int col = 0; col < this.gameLevel1.getMaxWidth(); col++) {
                int index = gameLevel1.getImageIndex(col, row);

                g.drawImage(this.bgMapImage[index],
                        (int) (TILES_SIZE * col - drawXOffset), TILES_SIZE * row,
                        TILES_SIZE, TILES_SIZE, null);
            }
        }

    }

    public GameLevel getGameLevel() {
        return this.gameLevel1;
    }

    @Override
    public void update() {

    }

    public void loadNextLevel() {
        levelIndex++;
        if (levelIndex >= 1) {
            levelIndex = 0;
            LOGGER.info("No more Level To Play");
            game.getGamePlaying().setGameState(GameState.MENU);
            return;
        }
        game.getGameAudioPlayer().setLevelSong(0);
    }

}
