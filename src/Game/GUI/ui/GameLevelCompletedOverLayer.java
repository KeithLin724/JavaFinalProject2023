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
import Game.audio.GameAudio;
import Game.gameBase.GameCalculator;
import Game.gameBase.GamePoint;
import Game.gameBase.GameUnitPair;
import Game.state.GameState;
import Game.state.MouseState;

import static base.BaseGameConstant.SCALE;
import static base.BaseGameConstant.GAME_WIDTH;
import static base.BaseGameConstant.GAME_HEIGHT;

public class GameLevelCompletedOverLayer implements GameStateMethod {

    private static final Logger LOGGER = Logger.getLogger(GameLevelCompletedOverLayer.class.getName());

    private GamePlaying gamePlaying;

    private GameURMButton menuButton, nextButton;

    private BufferedImage image;
    private GamePoint point;
    private GameUnitPair bgWH;

    private final List<GameButtonBase> allButtons;

    public GameLevelCompletedOverLayer(GamePlaying gamePlaying) {
        this.gamePlaying = gamePlaying;

        try {
            initImage();
            initButtons();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "init class error", e);
        }

        allButtons = List.of(menuButton, nextButton);
    }

    private void initButtons() throws IOException {
        var menuPoint = GamePoint.buildGamePoint(330 * SCALE, 200 * SCALE);
        var replayPoint = GamePoint.buildGamePoint(0 * SCALE, 0 * SCALE);
        var nextPoint = GamePoint.buildGamePoint(445 * SCALE, 200 * SCALE);

        var btnResult = GameElementFactory.getAllGameURMButton(menuPoint, replayPoint, nextPoint);

        this.menuButton = btnResult[0];
        this.nextButton = btnResult[2];

    }

    private void initImage() throws IOException {

        this.image = ImageLoader.loadImage(GameSourceFilePath.LEVEL_COMPLETED_IMAGE);

        this.bgWH = GameCalculator.calculate(
                this.image.getWidth(), this.image.getHeight(),
                (x) -> (int) (x * SCALE));

        this.point = GamePoint.buildGamePoint(GAME_WIDTH / 2f - this.bgWH.getW() / 2f, 75 * SCALE);

    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        g.drawImage(image,
                this.point.getIntX(), this.point.getIntY(),
                this.bgWH.getIntW(), this.bgWH.getIntH(),
                null);

        this.allButtons.forEach(btn -> btn.render(g));
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

            this.gamePlaying.getGame().getGameAudioPlayer().playUiEffect(GameAudio.CLICK);
        }

        // next
        else if (this.nextButton.isIn(e) && this.nextButton.getMouseState().equals(MouseState.PRESS)) {

            LOGGER.info("level next");

            gamePlaying.loadNextLevel();

            this.gamePlaying.getGame().getGameAudioPlayer().playUiEffect(GameAudio.CLICK);
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

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
