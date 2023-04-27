package Game.GUI;

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

public class GamePlaying extends GameStateBase implements GameStateMethod {

    private GameLevelManager gameLevelManager;
    private GameCharacter player;

    private GamePauseDisplayLayer gamePauseDisplayLayer;

    // add
    private boolean paused = true;

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

        gamePauseDisplayLayer = new GamePauseDisplayLayer();
    }

    public GameCharacter getPlayer() {
        return player;
    }

    public void windowLostFocus() {
        this.player.stopDirection();
    }

    @Override
    public void update() {
        this.gameLevelManager.update();
        this.player.update();

    }

    @Override
    public void render(Graphics g) {
        this.gameLevelManager.render(g);
        this.player.render(g);

        this.gamePauseDisplayLayer.render(g);
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
