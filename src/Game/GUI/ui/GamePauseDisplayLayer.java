package Game.GUI.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import Game.GameElementFactory;
import Game.GameSourceFilePath;
import Game.GUI.GamePlaying;
import Game.GUI.ui.buttons.GameButtonBase;
import Game.GUI.ui.buttons.GameSoundButton;
import Game.GUI.ui.buttons.GameURMButton;
import Game.GUI.ui.buttons.GameVolumeButton;

import Game.Loader.ImageLoader;
import Game.PLUG.GameStateMethod;
import Game.PLUG.gameDrawer.GameUpdateInterface;
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

    private GameUnitPair bgWH;
    private GamePoint bgPoint;

    private GameSoundButton musicButton, sfxButton;
    private GameURMButton menuB, replayB, unpauseB;
    private GameVolumeButton volumeButtons;

    private final List<GameButtonBase> allButtons;

    public GamePauseDisplayLayer(GamePlaying gamePlaying) {
        this.gamePlaying = gamePlaying;

        try {
            loadBackground();
            createSoundButton();
            createURMButton();
            createVolumeButtons();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.allButtons = List.of(musicButton, sfxButton, menuB, replayB, unpauseB, volumeButtons);
    }

    private void createVolumeButtons() throws IOException {
        var volumeBtnPoint = GamePoint.buildGamePoint(309 * SCALE, 278 * SCALE);

        this.volumeButtons = GameElementFactory.getAllGameVolumeButton(volumeBtnPoint);

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

    private void createSoundButton() throws IOException {
        var musicPoint = GamePoint.buildGamePoint(450 * SCALE, 140 * SCALE);
        var sfxPoint = GamePoint.buildGamePoint(450 * SCALE, 186 * SCALE);

        var btnResult = GameElementFactory.getAllGameSoundButton(musicPoint, sfxPoint);

        this.musicButton = btnResult[0];
        this.sfxButton = btnResult[1];

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
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        // background
        g.drawImage(this.backgroundImage,
                this.bgPoint.getIntX(), this.bgPoint.getIntY(),
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
        if (this.musicButton.isIn(e) && this.musicButton.getMouseState().equals(MouseState.PRESS)) {
            this.musicButton.changeMul();
        }

        else if (this.sfxButton.isIn(e) && this.sfxButton.getMouseState().equals(MouseState.PRESS)) {
            this.sfxButton.changeMul();
        }

        else if (this.menuB.isIn(e) && this.menuB.getMouseState().equals(MouseState.PRESS)) {
            GameState.setState(GameState.MENU);
            this.gamePlaying.setPaused(false);
        }

        else if (this.replayB.isIn(e) && this.replayB.getMouseState().equals(MouseState.PRESS)) {
            System.out.println("level replay");
        }

        else if (this.unpauseB.isIn(e) && this.unpauseB.getMouseState().equals(MouseState.PRESS)) {
            this.gamePlaying.setPaused(false);
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
        if (this.volumeButtons.getMouseState().equals(MouseState.PRESS)) {
            this.volumeButtons.changeX(e.getX());
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // reset
        this.allButtons.forEach(GameButtonBase::resetState);

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
