package Game.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Logger;

import Game.GameCharacter;
import Game.GameSourceFilePath;
import Game.GUI.ui.GamePauseDisplayLayer;
import Game.Loader.GameElementLoader;
import Game.PLUG.GameStateMethod;
import Game.gameBackground.GameLevelManager;
import Game.state.GameState;
import logic.input.Direction;
import main.Game;

import static base.BaseGameConstant.GAME_WIDTH;
import static base.BaseGameConstant.GAME_HEIGHT;
import static base.BaseGameConstant.TILES_IN_WIDTH;
import static base.BaseGameConstant.TILES_SIZE;

public class GamePlaying extends GameStateBase implements GameStateMethod {

    private GameLevelManager gameLevelManager;
    private GameCharacter player;

    private GamePauseDisplayLayer gamePauseDisplayLayer;

    private boolean paused = false;

    // about the display gaming

    private int xLevelOffset;
    private final int leftBorder = (int) (0.2 * GAME_WIDTH);
    private final int rightBorder = (int) (0.8 * GAME_WIDTH);
    private int levelTileWide;
    private int maxTileOffset; // not display value
    private int maxLevelOffset; // not display pixel

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    private static final Logger LOGGER = Logger.getLogger(GamePlaying.class.getName());

    public GamePlaying(Game game) {
        super(game);
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

        // about the window display number
        this.levelTileWide = gameLevelManager.getGameLevel().getMaxWidth();
        this.maxTileOffset = levelTileWide - TILES_IN_WIDTH;
        this.maxLevelOffset = this.maxTileOffset * TILES_SIZE;
    }

    public GameCharacter getPlayer() {
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
        checkCloseToBorder();
    }

    private void checkCloseToBorder() {
        var playerX = this.player.getHitBox().x;
        var diff = playerX - this.xLevelOffset;

        if (diff > rightBorder) {
            xLevelOffset += diff - rightBorder;
        }

        else if (diff < leftBorder) {
            xLevelOffset += diff - leftBorder;
        }

        if (this.xLevelOffset > maxLevelOffset) {
            this.xLevelOffset = maxLevelOffset;
        }

        else if (this.xLevelOffset < 0) {
            this.xLevelOffset = 0;
        }

        this.player.passOffset(this.xLevelOffset);
        this.gameLevelManager.passOffset(this.xLevelOffset);
    }

    @Override
    public void render(Graphics g) {
        this.gameLevelManager.render(g);
        this.player.render(g);

        if (this.paused) {
            g.setColor(new Color(0, 0, 0, 200));
            g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
            this.gamePauseDisplayLayer.render(g);
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
