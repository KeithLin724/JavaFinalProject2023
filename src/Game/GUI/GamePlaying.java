package Game.GUI;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Logger;

import Game.GameCharacter;
import Game.GameSourceFilePath;
import Game.Loader.GameElementLoader;
import Game.PLUG.GameStateMethod;
import Game.gameBackground.GameLevelManager;
import Game.state.GameState;
import logic.input.Direction;
import main.Game;

public class GamePlaying extends GameStateBase implements GameStateMethod {

    private GameLevelManager gameMapLevelManager;
    private GameCharacter player;

    private static final Logger LOGGER = Logger.getLogger(GamePlaying.class.getName());

    public GamePlaying(Game game) {
        super(game);
    }

    public void initClass() throws IOException {
        LOGGER.info("Testing");

        gameMapLevelManager = new GameLevelManager(this.game);

        player = GameElementLoader.getTestingGameCharacter(GameSourceFilePath.PLAYER_MAIN_CHARACTER_TEXT_FILE);
        player.init(200, 200);
        player.setLevelData(gameMapLevelManager.getGameLevel().getLevel2D());
        player.setLevel(gameMapLevelManager.getGameLevel());
    }

    public GameCharacter getPlayer() {
        return player;
    }

    public void windowLostFocus() {
        this.player.stopDirection();
    }

    @Override
    public void update() {
        this.gameMapLevelManager.update();
        this.player.update();
    }

    @Override
    public void render(Graphics g) {
        this.gameMapLevelManager.render(g);
        this.player.render(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1 -> {
                this.player.setAttacking(true);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

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

    }

    private void keyEventToPlayerMove(KeyEvent e, boolean isMoveIt) {
        switch (e.getKeyCode()) {

            case KeyEvent.VK_A -> {
                this.player.setDirection(Direction.LEFT, isMoveIt);
            }

            case KeyEvent.VK_D -> {
                this.player.setDirection(Direction.RIGHT, isMoveIt);
            }

            case KeyEvent.VK_SPACE -> {
                this.player.setJump(isMoveIt);
            }

            case KeyEvent.VK_BACK_SPACE -> {
                GameState.setState(GameState.MENU);
            }

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
