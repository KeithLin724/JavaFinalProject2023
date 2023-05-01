package Game.gameBackground;

import Game.Loader.GameElementLoader;
import Game.gameBase.GameUnitPair;

import static Game.gameBackground.GameLevelManager.HEIGHT_BLOCK_NUM;
import static Game.gameBackground.GameLevelManager.WIDTH_BLOCK_NUM;

import java.io.IOException;
import java.util.Arrays;

public class GameLevel {
    private int[][] levelData;

    public GameLevel(int[][] levelData) {
        this.levelData = levelData;
        // Arrays.stream(this.levelData).forEach(line ->
        // System.out.println(Arrays.toString(line)));
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

    public int getMaxWidth() {
        return this.levelData[0].length;
    }
}
