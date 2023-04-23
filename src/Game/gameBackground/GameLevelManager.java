package Game.gameBackground;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import Game.GameSourceFilePath;
import Game.Loader.ImageLoader;
import Game.PLUG.gameDrawer.GameRenderInterface;
import Game.PLUG.gameDrawer.GameUpdateInterface;
import main.Game;

import static base.BaseGameConstant.TILES_SIZE;

// https://www.youtube.com/watch?v=et5JeT-ESKk&list=PL4rzdwizLaxYmltJQRjq18a9gsSyEQQ-0&index=9

public class GameLevelManager implements GameRenderInterface, GameUpdateInterface {

    public static final int HEIGHT_BLOCK_NUM = 4;
    public static final int WIDTH_BLOCK_NUM = 12;
    public static final int PIXEL = 32;

    private Game game;
    // about save the pixel block
    private BufferedImage[] bgMapImage;

    // about display the block where to display
    private GameLevel gameLevel1; // level 1

    public GameLevelManager(Game game) throws IOException {
        this.game = game;
        this.bgMapImage = ImageLoader.loadBackgroundImage(GameSourceFilePath.BACKGROUND_IMAGE_PATH,
                HEIGHT_BLOCK_NUM,
                WIDTH_BLOCK_NUM,
                PIXEL);

        this.gameLevel1 = GameLevel.loadFromFile(GameSourceFilePath.BACKGROUND_LEVEL_1);
    }

    @Override
    public void render(Graphics g) {
        // g.drawImage(bgMapImage[2], 0, 0, null);

        // for test
        for (int row = 0; row < Game.TILES_IN_HEIGHT; row++) {
            for (int col = 0; col < Game.TILES_IN_WIDTH; col++) {
                int index = gameLevel1.getImageIndex(col, row);

                g.drawImage(this.bgMapImage[index],
                        TILES_SIZE * col, TILES_SIZE * row,
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
}
