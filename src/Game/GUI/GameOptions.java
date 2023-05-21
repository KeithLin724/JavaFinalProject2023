package Game.GUI;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import Game.GameElementFactory;
import Game.GameSourceFilePath;
import Game.GUI.ui.GameAudioOptions;
import Game.GUI.ui.buttons.GameURMButton;
import Game.Loader.ImageLoader;
import Game.PLUG.GameStateMethod;
import Game.gameBase.GameCalculator;
import Game.gameBase.GamePoint;
import Game.gameBase.GameUnitPair;
import Game.state.GameState;
import Game.state.MouseState;
import main.Game;

import static base.BaseGameConstant.GAME_WIDTH;
import static base.BaseGameConstant.GAME_HEIGHT;
import static base.BaseGameConstant.SCALE;

public class GameOptions extends GameStateBase implements GameStateMethod {
    private static final Logger LOGGER = Logger.getLogger(GameOptions.class.getName());

    private GameAudioOptions gameAudioOptions;
    private BufferedImage backgroundImage, optionsBackgroundImage;

    private GamePoint optionsPoints;
    private GameUnitPair optionsWH;

    private GameURMButton menuBtn;

    public GameOptions(Game game) {
        super(game);

        try {
            loadImage();
            loadButtons();
        } catch (IOException e) {
            // e.printStackTrace();
            LOGGER.log(Level.SEVERE, "load image error", e);
        }

        this.gameAudioOptions = game.getGameAudioOptions();
    }

    private void loadImage() throws IOException {
        this.backgroundImage = ImageLoader.loadImage(GameSourceFilePath.MENU_SELECT_BACKGROUND_IMAGE_CITY);
        this.optionsBackgroundImage = ImageLoader.loadImage(GameSourceFilePath.OPTIONS_IMAGE);

        this.optionsWH = GameCalculator.calculate(this.optionsBackgroundImage.getWidth(),
                this.optionsBackgroundImage.getHeight(),
                x -> (int) (x * SCALE));

        this.optionsPoints = GamePoint.buildGamePoint(GAME_WIDTH / 2.0f - this.optionsWH.getW() / 2.0f, 33 * SCALE);
    }

    private void loadButtons() throws IOException {
        var menuPoint = GamePoint.buildGamePoint(385 * SCALE, 325 * SCALE);
        var replayPoint = GamePoint.buildGamePoint(0 * SCALE, 0 * SCALE);
        var unpausePoint = GamePoint.buildGamePoint(0 * SCALE, 0 * SCALE);

        var btnResult = GameElementFactory.getAllGameURMButton(menuPoint, replayPoint, unpausePoint);

        this.menuBtn = btnResult[0];

    }

    @Override
    public void update() {
        this.menuBtn.update();
        this.gameAudioOptions.update();
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(backgroundImage,
                0, 0,
                GAME_WIDTH, GAME_HEIGHT,
                null);

        g.drawImage(this.optionsBackgroundImage,
                this.optionsPoints.getIntX(), this.optionsPoints.getIntY(),
                this.optionsWH.getIntW(), this.optionsWH.getIntH(),
                null);

        this.menuBtn.render(g);
        this.gameAudioOptions.render(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.gameAudioOptions.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (this.menuBtn.isIn(e)) {
            this.menuBtn.setMouseState(MouseState.PRESS);
            return;
        }

        this.gameAudioOptions.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (this.menuBtn.isIn(e) && this.menuBtn.getMouseState().equals(MouseState.PRESS)) {
            GameState.setState(GameState.MENU);
        }

        else {
            this.gameAudioOptions.mouseReleased(e);
        }

        this.menuBtn.resetState();

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.gameAudioOptions.mouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.menuBtn.resetState();

        if (this.menuBtn.isIn(e)) {
            this.menuBtn.setMouseState(MouseState.OVER);
            return;
        }

        this.gameAudioOptions.mouseMoved(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            GameState.setState(GameState.MENU);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
