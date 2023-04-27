package Game.GUI.ui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import Game.GameSourceFilePath;
import Game.Loader.ImageLoader;
import Game.PLUG.GameStateMethod;
import Game.gameBase.GameCalculator;
import Game.gameBase.GamePoint;
import Game.gameBase.GameUnitPair;
import Game.state.MouseState;

import static base.BaseGameConstant.SCALE;
import static base.BaseGameConstant.GAME_WIDTH;

public class GamePauseDisplayLayer implements GameStateMethod {
    private BufferedImage backgroundImage;

    private GameUnitPair bgWH;
    private GamePoint bgPoint;

    private GameSoundButton musicButton, sfxButton;

    public GamePauseDisplayLayer() {
        try {
            loadBackground();
            createSoundButton();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (this.musicButton.isIn(e)) {
            this.musicButton.setMouseState(MouseState.PRESS);

        } else if (this.sfxButton.isIn(e)) {
            this.sfxButton.setMouseState(MouseState.PRESS);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (this.musicButton.isIn(e) && this.musicButton.mouseState.equals(MouseState.PRESS)) {
            this.musicButton.changeMul();
            this.musicButton.resetState();

        } else if (this.sfxButton.isIn(e) && this.sfxButton.mouseState.equals(MouseState.PRESS)) {
            this.sfxButton.changeMul();
            this.sfxButton.resetState();
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
        this.musicButton.resetState();
        this.sfxButton.resetState();

        if (this.musicButton.isIn(e)) {
            this.musicButton.setMouseState(MouseState.OVER);

        } else if (this.sfxButton.isIn(e)) {
            this.sfxButton.setMouseState(MouseState.OVER);
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
