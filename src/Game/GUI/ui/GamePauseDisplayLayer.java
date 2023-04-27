package Game.GUI.ui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import Game.GameSourceFilePath;
import Game.GUI.GamePlaying;
import Game.GUI.UIConstant.VolumeButtons;
import Game.Loader.ImageLoader;
import Game.PLUG.GameStateMethod;
import Game.gameBase.GameCalculator;
import Game.gameBase.GamePoint;
import Game.gameBase.GameUnitPair;
import Game.state.GameState;
import Game.state.MouseState;

import static base.BaseGameConstant.SCALE;
import static base.BaseGameConstant.GAME_WIDTH;

public class GamePauseDisplayLayer implements GameStateMethod {
    private BufferedImage backgroundImage;

    private GamePlaying gamePlaying;

    private GameUnitPair bgWH;
    private GamePoint bgPoint;

    private GameSoundButton musicButton, sfxButton;
    private GameURMButton menuB, replayB, unpauseB;
    private GameVolumeButton volumeButtons;

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
    }

    private void createVolumeButtons() throws IOException {
        var volumeBtnPoint = GamePoint.buildGamePoint(309 * SCALE, 278 * SCALE);
        this.volumeButtons = new GameVolumeButton(volumeBtnPoint,
                ImageLoader.loadVolumeButtonImages(),
                ImageLoader.loadVolumeSliderImages());

    }

    private void createURMButton() throws IOException {
        var menuPoint = GamePoint.buildGamePoint(313 * SCALE, 325 * SCALE);
        var replayPoint = GamePoint.buildGamePoint(387 * SCALE, 325 * SCALE);
        var unpausePoint = GamePoint.buildGamePoint(462 * SCALE, 325 * SCALE);

        this.menuB = new GameURMButton(menuPoint, ImageLoader.loadURMButtonImage(2));
        this.replayB = new GameURMButton(replayPoint, ImageLoader.loadURMButtonImage(1));
        this.unpauseB = new GameURMButton(unpausePoint, ImageLoader.loadURMButtonImage(0));
    }

    private void createSoundButton() {
        var musicPoint = GamePoint.buildGamePoint(450 * SCALE, 140 * SCALE);
        var sfxPoint = GamePoint.buildGamePoint(450 * SCALE, 186 * SCALE);

        this.musicButton = new GameSoundButton(musicPoint);
        this.sfxButton = new GameSoundButton(sfxPoint);

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
        this.musicButton.update();
        this.sfxButton.update();

        this.menuB.update();
        this.replayB.update();
        this.unpauseB.update();

        this.volumeButtons.update();
    }

    @Override
    public void render(Graphics g) {

        // background
        g.drawImage(this.backgroundImage,
                (int) this.bgPoint.x, (int) this.bgPoint.y,
                (int) this.bgWH.x, (int) this.bgWH.y,
                null);

        this.musicButton.render(g);
        this.sfxButton.render(g);

        // URM button
        this.menuB.render(g);
        this.replayB.render(g);
        this.unpauseB.render(g);

        // volume button
        this.volumeButtons.render(g);

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (this.musicButton.isIn(e)) {
            this.musicButton.setMouseState(MouseState.PRESS);
        }

        else if (this.sfxButton.isIn(e)) {
            this.sfxButton.setMouseState(MouseState.PRESS);
        }

        else if (this.menuB.isIn(e)) {
            this.menuB.setMouseState(MouseState.PRESS);
        }

        else if (this.replayB.isIn(e)) {
            this.replayB.setMouseState(MouseState.PRESS);
        }

        else if (this.unpauseB.isIn(e)) {
            this.unpauseB.setMouseState(MouseState.PRESS);
        }

        else if (this.volumeButtons.isIn(e)) {
            this.volumeButtons.setMouseState(MouseState.PRESS);
        }
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

        // sound
        this.musicButton.resetState();
        this.sfxButton.resetState();

        // URM
        this.menuB.resetState();
        this.replayB.resetState();
        this.unpauseB.resetState();

        // volume buttons
        this.volumeButtons.resetState();

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
        this.musicButton.resetState();
        this.sfxButton.resetState();

        this.menuB.resetState();
        this.replayB.resetState();
        this.unpauseB.resetState();

        this.volumeButtons.resetState();

        if (this.musicButton.isIn(e)) {
            this.musicButton.setMouseState(MouseState.OVER);
        }

        else if (this.sfxButton.isIn(e)) {
            this.sfxButton.setMouseState(MouseState.OVER);
        }

        else if (this.menuB.isIn(e)) {
            this.menuB.setMouseState(MouseState.OVER);
        }

        else if (this.replayB.isIn(e)) {
            this.replayB.setMouseState(MouseState.OVER);
        }

        else if (this.unpauseB.isIn(e)) {
            this.unpauseB.setMouseState(MouseState.OVER);
        }

        else if (this.volumeButtons.isIn(e)) {
            this.volumeButtons.setMouseState(MouseState.OVER);
        }
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
