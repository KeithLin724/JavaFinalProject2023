package gameBackground;

import Game.Loader.GameElementLoader;
import Game.gameBase.GamePoint;
import Game.gameBase.GameUnitPair;
import main.Game;

import static gameBackground.GameLevelManager.HEIGHT_BLOCK_NUM;
import static gameBackground.GameLevelManager.WIDTH_BLOCK_NUM;

import java.io.IOException;;

public class GameLevel {
    private int[][] levelData;

    public GameLevel(int[][] levelData) {
        this.levelData = levelData;
    }

    public static GameLevel loadFromFile(String levelDataFileName) throws IOException {
        return new GameLevel(GameElementLoader.loadGameLevelData(levelDataFileName, HEIGHT_BLOCK_NUM, WIDTH_BLOCK_NUM));
    }

    public int getImageIndex(int x, int y) {
        return this.levelData[y][x];
    }

    public int getImageIndex(GameUnitPair point) {
        return this.levelData[(int) point.y][(int) point.x];
    }

    public int[][] getLevel2D() {
        return this.levelData;
    }
}
