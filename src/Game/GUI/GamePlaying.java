package Game.GUI;

import Game.GUI.ui.GameLevelCompletedOverLayer;
import Game.GUI.ui.GameOverDisplayLayer;
import Game.GUI.ui.GamePauseDisplayLayer;
import Game.GameSourceFilePath;
import Game.Loader.GameElementLoader;
import Game.Loader.ImageLoader;
import Game.PLUG.GameStateMethod;
import Game.effects.Rain;
import Game.Player;
import Game.gameBackground.GameEnemyManager;
import Game.gameBackground.GameLevelManager;
import Game.state.GameState;
import logic.input.Direction;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static base.BaseGameConstant.GAME_HEIGHT;
import static base.BaseGameConstant.GAME_WIDTH;
import static base.BaseGameConstant.TILES_IN_WIDTH;
import static base.BaseGameConstant.TILES_SIZE;

public class GamePlaying extends GameStateBase implements GameStateMethod {

    private static final Logger LOGGER = Logger.getLogger(GamePlaying.class.getName());
    private final float leftBorder = 0.2f * (float) GAME_WIDTH;
    private final float rightBorder = 0.8f * (float) GAME_WIDTH;
    private GameLevelManager gameLevelManager;

    // about the display gaming
    private Player player;

    private GamePauseDisplayLayer gamePauseDisplayLayer;
    private boolean paused = false;

    private float xLevelOffset;
    private int levelTileWide;
    private int maxTileOffset; // not display value
    private int maxLevelOffset; // not display pixel
    private BufferedImage playingBackgroundImage;
    // private BufferedImage bigCloudImage, smallCloudImage;
    // private int[] smallCloudPosArrayY;
    // private int bigCloudNumber;

    // city image
    private BufferedImage cityImage2, cityImage3, cityImage4, cityImage5;

    // about the game enemy
    private GameEnemyManager gameEnemyManager;

    // about the game over
    private GameOverDisplayLayer gameOverDisplayLayer;
    private boolean gameOver;
    private boolean playerDying;

    // game level complete
    private GameLevelCompletedOverLayer gameLevelCompletedOverLayer;
    private boolean levelComplete = false;

    // effect
    private Rain rain;
    private boolean drawRaining = true;

    public GamePlaying(Game game) {
        super(game);

        try {
            this.playingBackgroundImage = ImageLoader.loadImage(GameSourceFilePath.PLAYING_BACKGROUND_IMAGE_CITY);

            this.cityImage2 = ImageLoader.loadImage(GameSourceFilePath.CITY_BACKGROUND_2_IMAGE);
            this.cityImage3 = ImageLoader.loadImage(GameSourceFilePath.CITY_BACKGROUND_3_IMAGE);
            this.cityImage4 = ImageLoader.loadImage(GameSourceFilePath.CITY_BACKGROUND_4_IMAGE);
            this.cityImage5 = ImageLoader.loadImage(GameSourceFilePath.CITY_BACKGROUND_5_IMAGE);

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Load image error", e);
        }

    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void initClass() throws IOException {
        LOGGER.info("Playing");

        gameLevelManager = new GameLevelManager(this.game);

        player = GameElementLoader.getTestingGameCharacter(GameSourceFilePath.PLAYER_MAIN_CHARACTER_TEXT_FILE);

        assert player != null;
        player.init(200, 200);
        player.setLevelData(gameLevelManager.getGameLevel().getLevel2D());
        player.setLevel(gameLevelManager.getGameLevel());
        player.setGamePlaying(this);

        gamePauseDisplayLayer = new GamePauseDisplayLayer(this);

        // about the enemy
        gameEnemyManager = new GameEnemyManager(this);

        gameOverDisplayLayer = new GameOverDisplayLayer(this);

        gameLevelCompletedOverLayer = new GameLevelCompletedOverLayer(this);

        // about the window display number
        this.levelTileWide = gameLevelManager.getGameLevel().getMaxWidth();
        this.maxTileOffset = levelTileWide - TILES_IN_WIDTH;
        this.maxLevelOffset = this.maxTileOffset * TILES_SIZE;

        // this.bigCloudNumber = (int) Math.round((double)
        // GameEnvironment.BIG_CLOUD_WIDTH.value
        // / (double) this.gameLevelManager.getGameLevel().getMaxWidth());
        this.rain = new Rain();
    }

    public void loadNextLevel() {
        this.resetAll();
        gameLevelManager.loadNextLevel();
    }

    public Player getPlayer() {
        return player;
    }

    public void windowLostFocus() {
        this.player.stopDirection();
    }

    @Override
    public void update() {
        if (this.paused) {
            this.gamePauseDisplayLayer.update();
            return;
        }

        if (this.playerDying) {
            this.player.update();
            return;
        }

        if (this.levelComplete) {
            this.gameLevelCompletedOverLayer.update();
            return;
        }

        if (this.gameOver) {
            this.gameOverDisplayLayer.update();
            return;
        }

        if (this.drawRaining) {
            rain.passOffset(this.xLevelOffset);
            rain.update();
        }

        this.gameLevelManager.update();
        this.player.update();

        GameEnemyManager.passLevelData(this.gameLevelManager.getGameLevel());
        this.gameEnemyManager.passPlayer(player);
        this.gameEnemyManager.update();

        checkCloseToBorder();
    }

    private void checkCloseToBorder() {
        var playerX = this.player.getHitBox().x;
        var diff = playerX - this.xLevelOffset;

        if (diff > rightBorder) {
            xLevelOffset += diff - rightBorder;
        } else if (diff < leftBorder) {
            xLevelOffset += diff - leftBorder;
        }

        this.xLevelOffset = Math.max(Math.min(xLevelOffset, maxLevelOffset), 0);
    }

    @Override
    public void render(Graphics2D g) {
        this.gameLevelManager.passOffset(this.xLevelOffset);
        this.player.passOffset(this.xLevelOffset);

        GameEnemyManager.passOffset(this.xLevelOffset);

        g.drawImage(this.playingBackgroundImage,
                0, 0,
                GAME_WIDTH, GAME_HEIGHT,
                null);

        drawCityImage(g);

        // drawCloud(g);
        if (this.drawRaining) {
            rain.passOffset(this.xLevelOffset);
            rain.render(g);
        }

        this.gameLevelManager.render(g);
        this.gameEnemyManager.render(g);
        this.player.render(g);

        if (this.paused) {
            this.gamePauseDisplayLayer.render(g);
        } else if (this.gameOver) {
            this.gameOverDisplayLayer.render(g);
        } else if (this.levelComplete) {
            this.gameLevelCompletedOverLayer.render(g);
        }

    }

    private void drawCityImage(Graphics2D g) {
        g.drawImage(this.cityImage2,
                0, 0,
                GAME_WIDTH, GAME_HEIGHT,
                null);

        // g.drawImage(this.cityImage3,
        // -GAME_WIDTH + (int) (xLevelOffset * 0.005), 0,
        // GAME_WIDTH, GAME_HEIGHT,
        // null);
        g.drawImage(this.cityImage3,
                (int) (xLevelOffset * 0.005), 0,
                GAME_WIDTH, GAME_HEIGHT,
                null);

        g.drawImage(this.cityImage4,
                -(int) (xLevelOffset * 0.05), 0,
                GAME_WIDTH, GAME_HEIGHT,
                null);

        g.drawImage(this.cityImage4,
                GAME_WIDTH - (int) (xLevelOffset * 0.05), 0,
                GAME_WIDTH, GAME_HEIGHT,
                null);

        g.drawImage(this.cityImage5,
                -(int) (xLevelOffset * 0.09), 0,
                GAME_WIDTH, GAME_HEIGHT,
                null);

        g.drawImage(this.cityImage5,
                GAME_WIDTH - (int) (xLevelOffset * 0.09), 0,
                GAME_WIDTH, GAME_HEIGHT,
                null);

    }

    public void resetAll() {
        // TODO: reset player , enemy level , etc...
        this.gameOver = false;
        this.paused = false;
        this.playerDying = false;
        this.levelComplete = false;
        player.resetAll();
        gameEnemyManager.resetAll();

    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void setPlayerDying(boolean dead) {
        this.playerDying = true;
    }

    public void setLevelCompleted(boolean completed) {
        this.levelComplete = completed;
        if (this.levelComplete) {
            this.game.getGameAudioPlayer().playLevelComplete();
        }
    }

    public void checkEnemyHit(Player player) {
        this.gameEnemyManager.checkEnemyHit(player);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (this.gameOver) {
            this.gameOverDisplayLayer.mouseClicked(e);
            return;
        }

        if (e.getButton() == MouseEvent.BUTTON1) {
            this.player.setAttacking(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (this.gameOver) {
            this.gameOverDisplayLayer.mousePressed(e);
            return;
        }

        if (this.levelComplete) {
            this.gameLevelCompletedOverLayer.mousePressed(e);
            return;
        }

        if (paused) {
            this.gamePauseDisplayLayer.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (this.gameOver) {
            this.gameOverDisplayLayer.mouseReleased(e);
            return;
        }

        if (this.levelComplete) {
            this.gameLevelCompletedOverLayer.mouseReleased(e);
            return;
        }

        if (paused) {
            this.gamePauseDisplayLayer.mouseReleased(e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (this.gameOver) {
            this.gameOverDisplayLayer.mouseDragged(e);
            return;
        }

        if (paused) {
            this.gamePauseDisplayLayer.mouseDragged(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (this.gameOver) {
            this.gameOverDisplayLayer.mouseMoved(e);
            return;
        }

        if (this.levelComplete) {
            this.gameLevelCompletedOverLayer.mouseMoved(e);
            return;
        }

        if (paused) {
            this.gamePauseDisplayLayer.mouseMoved(e);
        }
    }

    private void keyEventToPlayerMove(KeyEvent e, boolean isMoveIt) {
        switch (e.getKeyCode()) {

            case KeyEvent.VK_A -> this.player.setDirection(Direction.LEFT, isMoveIt);

            case KeyEvent.VK_D -> this.player.setDirection(Direction.RIGHT, isMoveIt);

            case KeyEvent.VK_SPACE -> this.player.setJump(isMoveIt);

            case KeyEvent.VK_BACK_SPACE -> GameState.setState(GameState.MENU);

            case KeyEvent.VK_ESCAPE -> this.paused = (isMoveIt ? !this.paused : this.paused);

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (this.gameOver) {
            this.gameOverDisplayLayer.keyPressed(e);
            return;
        }

        this.keyEventToPlayerMove(e, true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (this.gameOver) {
            this.gameOverDisplayLayer.keyReleased(e);
            return;
        }

        this.keyEventToPlayerMove(e, false);
    }

}
