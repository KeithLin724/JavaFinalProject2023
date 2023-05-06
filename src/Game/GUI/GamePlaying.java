package Game.GUI;

import Game.GUI.ui.GamePauseDisplayLayer;
import Game.GameSourceFilePath;
import Game.Player;
import Game.Loader.GameElementLoader;
import Game.Loader.ImageLoader;
import Game.PLUG.GameStateMethod;
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
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import static base.BaseGameConstant.*;

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
    private BufferedImage playingBackgroundImage, bigCloudImage, smallCloudImage;
    private int[] smallCloudPosArrayY;
    private int bigCloudNumber;

    // about the game enemy
    private GameEnemyManager gameEnemyManager;

    public GamePlaying(Game game) {
        super(game);

        try {
            this.playingBackgroundImage = ImageLoader.loadImage(GameSourceFilePath.PLAYING_BACKGROUND_IMAGE);
            this.bigCloudImage = ImageLoader.loadImage(GameSourceFilePath.BIG_CLOUD_IMAGE);
            this.smallCloudImage = ImageLoader.loadImage(GameSourceFilePath.SMALL_CLOUD_IMAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Random random = new Random();

        smallCloudPosArrayY = IntStream
                .range(0, 8)
                .map(i -> (int) (90 * SCALE) + random.nextInt((int) (100 * SCALE)))
                .toArray();
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

        gamePauseDisplayLayer = new GamePauseDisplayLayer(this);

        // about the enemy
        gameEnemyManager = new GameEnemyManager(this);

        // about the window display number
        this.levelTileWide = gameLevelManager.getGameLevel().getMaxWidth();
        this.maxTileOffset = levelTileWide - TILES_IN_WIDTH;
        this.maxLevelOffset = this.maxTileOffset * TILES_SIZE;

        this.bigCloudNumber = (int) Math.round((double) GameEnvironment.BIG_CLOUD_WIDTH.value
                / (double) this.gameLevelManager.getGameLevel().getMaxWidth());
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
    public void render(Graphics g) {
        this.gameLevelManager.passOffset(this.xLevelOffset);
        this.player.passOffset(this.xLevelOffset);

        GameEnemyManager.passOffset(this.xLevelOffset);

        g.drawImage(this.playingBackgroundImage,
                0, 0,
                GAME_WIDTH, GAME_HEIGHT,
                null);

        drawCloud(g);

        this.gameLevelManager.render(g);
        this.player.render(g);
        this.gameEnemyManager.render(g);

        if (this.paused) {
            g.setColor(new Color(0, 0, 0, 200));
            g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
            this.gamePauseDisplayLayer.render(g);
        }

    }

    private void drawCloud(Graphics g) {
        for (int i = 0; i < this.bigCloudNumber; i++) {
            g.drawImage(this.bigCloudImage,
                    i * GameEnvironment.BIG_CLOUD_WIDTH.value - (int) (xLevelOffset - 0.1), // slower
                    (int) (204 * SCALE),
                    GameEnvironment.BIG_CLOUD_WIDTH.value,
                    GameEnvironment.BIG_CLOUD_HEIGHT.value,
                    null);
        }

        for (int i = 0; i < this.smallCloudPosArrayY.length; i++) {
            g.drawImage(this.smallCloudImage,
                    GameEnvironment.SMALL_CLOUD_WIDTH.value * 4 * i - (int) (xLevelOffset - 0.7), // faster
                    this.smallCloudPosArrayY[i],
                    GameEnvironment.SMALL_CLOUD_WIDTH.value,
                    GameEnvironment.SMALL_CLOUD_HEIGHT.value,
                    null);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            this.player.setAttacking(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (paused) {
            this.gamePauseDisplayLayer.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
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
        if (paused) {
            this.gamePauseDisplayLayer.mouseDragged(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
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
        this.keyEventToPlayerMove(e, true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.keyEventToPlayerMove(e, false);
    }

}
