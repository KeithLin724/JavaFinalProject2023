package Game.GUI.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import Game.GameElementFactory;
import Game.GameSourceFilePath;
import Game.GUI.GamePlaying;
import Game.GUI.ui.buttons.GameButtonBase;
import Game.GUI.ui.buttons.GameURMButton;
import Game.Loader.ImageLoader;
import Game.PLUG.GameStateMethod;
import Game.PLUG.gameDrawer.GameUpdateInterface;
import Game.gameBase.GameCalculator;
import Game.gameBase.GamePoint;
import Game.gameBase.GameUnitPair;
import Game.state.GameState;
import Game.state.MouseState;

import static base.BaseGameConstant.GAME_WIDTH;
import static base.BaseGameConstant.GAME_HEIGHT;
import static base.BaseGameConstant.SCALE;

/**
 * The GameOverDisplayLayer class displays the game over screen and handles user
 * input for menu and
 * replay buttons.
 */
public class GameOverDisplayLayer implements GameStateMethod {
    private static final Logger LOGGER = Logger.getLogger(GameOverDisplayLayer.class.getName());

    private GamePlaying gamePlaying;
    private BufferedImage deadDisplayImage;
    private GameUnitPair imageWH;
    private GamePoint imagePoint;
    private GameURMButton menuButton, replayButton;

    private final List<GameButtonBase> allButtons;

    public GameOverDisplayLayer(GamePlaying gamePlaying) {
        this.gamePlaying = gamePlaying;

        try {
            this.loadImage();
            this.loadButtons();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "load image error", e);
        }

        this.allButtons = List.of(this.menuButton, this.replayButton);
    }

    private void loadImage() throws IOException {

        this.deadDisplayImage = ImageLoader.loadImage(GameSourceFilePath.DEAD_SCREEN_IMAGE);

        this.imageWH = GameCalculator.calculate(
                this.deadDisplayImage.getWidth(), this.deadDisplayImage.getHeight(),
                x -> (int) (x * SCALE));

        this.imagePoint = GamePoint.buildGamePoint(
                GAME_WIDTH / 2.0f - this.imageWH.getW() / 2.0f,
                100 * SCALE);

    }

    private void loadButtons() throws IOException {
        var menuPoint = GamePoint.buildGamePoint(330 * SCALE, 200 * SCALE);
        var replayPoint = GamePoint.buildGamePoint(440 * SCALE, 200 * SCALE);
        var unpausePoint = GamePoint.buildGamePoint(0 * SCALE, 0 * SCALE);

        var btnResult = GameElementFactory.getAllGameURMButton(menuPoint, replayPoint, unpausePoint);

        this.menuButton = btnResult[0];
        this.replayButton = btnResult[1];
    }

    @Override
    public void update() {
        this.allButtons.forEach(GameUpdateInterface::update);
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        g.drawImage(this.deadDisplayImage,
                this.imagePoint.getIntX(), this.imagePoint.getIntY(),
                this.imageWH.getIntW(),
                this.imageWH.getIntH(),
                null);

        this.menuButton.render(g);
        this.replayButton.render(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
        // gamePlaying.resetAll();
        // gamePlaying.setGameState(GameState.MENU);
        // }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.allButtons
                .stream()
                .filter(item -> item.isIn(e))
                .findFirst()
                .ifPresent(item -> item.setMouseState(MouseState.PRESS));
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if (this.menuButton.isIn(e) && this.menuButton.getMouseState().equals(MouseState.PRESS)) {
            LOGGER.info("go to menu");
            this.gamePlaying.resetAll();
            gamePlaying.setGameState(GameState.MENU);
        }

        else if (this.replayButton.isIn(e) && this.replayButton.getMouseState().equals(MouseState.PRESS)) {
            LOGGER.info("level replay");
            this.gamePlaying.resetAll();
            gamePlaying.setGameState(GameState.PLAYING);
            // gamePlaying.getGame().getGameAudioPlayer().setLevelSong(0);
        }

        this.allButtons.forEach(GameButtonBase::resetState);
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
        // reset
        this.allButtons
                .forEach(GameButtonBase::resetState);

        // check over
        this.allButtons
                .stream()
                .filter(btn -> btn.isIn(e))
                .findFirst()
                .ifPresent(btn -> btn.setMouseState(MouseState.OVER));
    }
}
