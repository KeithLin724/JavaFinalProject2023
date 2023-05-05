package Game.gameBackground;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import Game.GameSourceFilePath;
import Game.GUI.GamePlaying;
import Game.Loader.GameElementLoader;
import Game.Loader.ImageLoader;
import Game.PLUG.gameDrawer.GameAnimatedDrawer;
import Game.role.GameEnemy;

import static Game.gameBackground.GameLevelManager.HEIGHT_BLOCK_NUM;
import static Game.gameBackground.GameLevelManager.WIDTH_BLOCK_NUM;

public class GameEnemyManager implements GameAnimatedDrawer {

    private static final Logger LOGGER = Logger.getLogger(GameEnemyManager.class.getName());

    private GamePlaying gamePlaying;
    private BufferedImage[][] enemyImage;
    private List<GameEnemy> enemyArr = new ArrayList<>();

    public GameEnemyManager(GamePlaying gamePlaying) {
        this.gamePlaying = gamePlaying;

        this.loadEnemyImage();
        this.addEnemies();
    }

    private void loadEnemyImage() {
        try {
            this.enemyImage = ImageLoader.loadCharacterImage(GameSourceFilePath.ENEMIES_FOLDER_PATH, 0, 0);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "load enemy image error", e);
        }
        LOGGER.info("load enemy image success");
    }

    private void addEnemies() {
        try {
            this.enemyArr = GameElementLoader.loadGameEnemyData(
                    GameSourceFilePath.BACKGROUND_LEVEL_1,
                    HEIGHT_BLOCK_NUM,
                    WIDTH_BLOCK_NUM,
                    enemyImage);

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "add enemies error", e);
        }

        LOGGER.info("enemies number : " + this.enemyArr.size());
    }

    public static void passOffset(float xOffset) {
        GameEnemy.passOffset(xOffset);
    }

    public static void passLevelData(GameLevel levelData) {
        GameEnemy.passLevelData(levelData);
    }

    @Override
    public void update() {
        enemyArr.forEach(GameEnemy::update);
        // enemyArr.get(0).update();
    }

    @Override
    public void render(Graphics g) {
        enemyArr.forEach(enemy -> enemy.render(g));
        // enemyArr.get(0).render(g);
    }

}
