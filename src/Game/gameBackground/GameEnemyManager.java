package Game.gameBackground;

import Game.GUI.GamePlaying;
import Game.GameSourceFilePath;
import Game.Loader.GameElementLoader;
import Game.Loader.ImageLoader;
import Game.PLUG.gameDrawer.GameAnimatedDrawer;
import Game.audio.GameAudioPlayer;
import Game.Player;
import Game.role.GameEnemy;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Game.gameBackground.GameLevelManager.HEIGHT_BLOCK_NUM;
import static Game.gameBackground.GameLevelManager.WIDTH_BLOCK_NUM;

public class GameEnemyManager implements GameAnimatedDrawer {

    private static final Logger LOGGER = Logger.getLogger(GameEnemyManager.class.getName());

    private GamePlaying gamePlaying;
    private BufferedImage[][] enemyImage;
    private List<GameEnemy> enemyArr = new ArrayList<>();
    private Player player;

    private GameAudioPlayer gameAudioPlayer;

    public GameEnemyManager(GamePlaying gamePlaying) {
        this.gamePlaying = gamePlaying;
        this.gameAudioPlayer = this.gamePlaying.getGame().getGameAudioPlayer();

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
                    enemyImage,
                    gameAudioPlayer);

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

    public void passPlayer(Player player) {
        this.player = player;
    }

    public void checkEnemyHit(Player player) {
        Rectangle2D.Float playerAttackBox = player.getAttackBox();
        for (var enemyItem : this.enemyArr) {

            boolean check = enemyItem.getCurrentHealth() > 0
                    && enemyItem.isActive()
                    && playerAttackBox.intersects(enemyItem.getHitBox());

            if (check) {
                enemyItem.getHurt(10);
                return;
            }
        }
    }

    @Override
    public void update() {
        enemyArr.forEach((item) -> {
            if (!item.isActive()) {
                return;
            }
            item.passPlayer(this.player);
            item.update();
        });
        // enemyArr.get(0).update();
    }

    @Override
    public void render(Graphics2D g) {
        enemyArr.forEach(enemy -> {
            if (!enemy.isActive()) {
                return;
            }
            enemy.render(g);
        });
        // enemyArr.get(0).render(g);
    }

    public void resetAll() {
        this.enemyArr.forEach(GameEnemy::resetAll);
    }

}
