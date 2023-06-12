package Game.GUI.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import Game.GameElementFactory;
import Game.GameSourceFilePath;
import Game.GUI.GamePlaying;
import Game.GUI.ui.buttons.GameButtonBase;
import Game.GUI.ui.buttons.GameURMButton;

import Game.Loader.ImageLoader;
import Game.PLUG.GameStateMethod;
import Game.PLUG.gameDrawer.GameUpdateInterface;
import Game.audio.GameAudio;
import Game.gameBase.GameCalculator;
import Game.gameBase.GamePoint;
import Game.gameBase.GameUnitPair;
import Game.state.GameState;
import Game.state.MouseState;

import static base.BaseGameConstant.SCALE;
import static base.BaseGameConstant.GAME_WIDTH;
import static base.BaseGameConstant.GAME_HEIGHT;

public class GamePauseDisplayLayer implements GameStateMethod {
    private BufferedImage backgroundImage;

    private final GamePlaying gamePlaying;

    private GameAudioOptions gameAudioOptions;

    private GameUnitPair bgWH;
    private GamePoint bgPoint;

    // private GameSoundButton musicButton, sfxButton;
    private GameURMButton menuB, replayB, unpauseB;
    // private GameVolumeButton volumeButtons;

    private final List<GameButtonBase> allButtons;

    private static final Logger LOGGER = Logger.getLogger(GamePauseDisplayLayer.class.getName());

    public GamePauseDisplayLayer(GamePlaying gamePlaying) {
        this.gamePlaying = gamePlaying;

        try {
            loadBackground();

            this.gameAudioOptions = this.gamePlaying.getGame().getGameAudioOptions();

            createURMButton();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.allButtons = List.of(menuB, replayB, unpauseB);
    }

    private void createURMButton() throws IOException {
        var menuPoint = GamePoint.buildGamePoint(313 * SCALE, 325 * SCALE);
        var replayPoint = GamePoint.buildGamePoint(387 * SCALE, 325 * SCALE);
        var unpausePoint = GamePoint.buildGamePoint(462 * SCALE, 325 * SCALE);

        var btnResult = GameElementFactory.getAllGameURMButton(menuPoint, replayPoint, unpausePoint);

        this.menuB = btnResult[0];
        this.replayB = btnResult[1];
        this.unpauseB = btnResult[2];
    }

    private void loadBackground() throws IOException {
        this.backgroundImage = ImageLoader.loadImage(GameSourceFilePath.PAUSE_BACKGROUND_IMAGE);

        this.bgWH = GameCalculator.calculate(
                this.backgroundImage.getWidth(), this.backgroundImage.getHeight(),
                x -> (int) (x * SCALE));

        this.bgPoint = GamePoint.buildGamePoint(GAME_WIDTH / 2.0F - this.bgWH.x / 2.0F, 25 * SCALE);

    }

    @Override
    public void update() {
        this.allButtons.forEach(GameUpdateInterface::update);

        // audio update
        this.gameAudioOptions.update();
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        // background
        g.drawImage(this.backgroundImage,
                this.bgPoint.getIntX(), this.bgPoint.getIntY(),
                this.bgWH.getIntW(), this.bgWH.getIntH(),
                null);

        this.allButtons.forEach(btn -> btn.render(g));

        // audio render
        this.gameAudioOptions.render(g);

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

        this.gameAudioOptions.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if (this.menuB.isIn(e) && this.menuB.getMouseState().equals(MouseState.PRESS)) {
            this.gamePlaying.setPaused(false);
            this.gamePlaying.getGame().getGameAudioPlayer().playUiEffect(GameAudio.CLICK);
            this.gamePlaying.resetAll();
            this.gamePlaying.setGameState(GameState.MENU);
        }

        else if (this.replayB.isIn(e) && this.replayB.getMouseState().equals(MouseState.PRESS)) {
            LOGGER.info("level replay");
            this.gamePlaying.resetAll();
            this.gamePlaying.getGame().getGameAudioPlayer().playUiEffect(GameAudio.CLICK);
            this.gamePlaying.setGameState(GameState.PLAYING);
        }

        else if (this.unpauseB.isIn(e) && this.unpauseB.getMouseState().equals(MouseState.PRESS)) {
            this.gamePlaying.setPaused(false);
            this.gamePlaying.getGame().getGameAudioPlayer().playUiEffect(GameAudio.CLICK);
        }

        else {
            this.gameAudioOptions.mouseReleased(e);
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
        this.gameAudioOptions.mouseDragged(e);
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

        this.gameAudioOptions.mouseMoved(e);
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
