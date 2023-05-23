package Game.GUI.ui;

import Game.GameElementFactory;
import Game.GUI.ui.buttons.GameSoundButton;
import Game.GUI.ui.buttons.GameVolumeButton;
import Game.GUI.ui.buttons.GameButtonBase;
import Game.PLUG.GameStateMethod;
import Game.PLUG.gameDrawer.GameUpdateInterface;
import Game.audio.GameAudioPlayer;
import Game.gameBase.GamePoint;
import Game.state.MouseState;

import static base.BaseGameConstant.SCALE;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameAudioOptions implements GameStateMethod {
    private static final Logger LOGGER = Logger.getLogger(GameAudioOptions.class.getName());

    private GameSoundButton musicButton, sfxButton;
    private GameVolumeButton volumeButtons;

    private final List<GameButtonBase> allButtons;

    private GameAudioPlayer gameAudioPlayer;

    public GameAudioOptions(GameAudioPlayer gameAudioPlayer) {
        this.gameAudioPlayer = gameAudioPlayer;
        try {
            createVolumeButtons();
            createSoundButton();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "load buttons error", e);
        }

        this.allButtons = List.of(this.musicButton, this.sfxButton, this.volumeButtons);
    }

    private void createSoundButton() throws IOException {
        var musicPoint = GamePoint.buildGamePoint(450 * SCALE, 140 * SCALE);
        var sfxPoint = GamePoint.buildGamePoint(450 * SCALE, 186 * SCALE);

        var btnResult = GameElementFactory.getAllGameSoundButton(musicPoint, sfxPoint);

        this.musicButton = btnResult[0];
        this.sfxButton = btnResult[1];
    }

    private void createVolumeButtons() throws IOException {
        var volumeBtnPoint = GamePoint.buildGamePoint(309 * SCALE, 278 * SCALE);
        this.volumeButtons = GameElementFactory.getAllGameVolumeButton(volumeBtnPoint);

    }

    @Override
    public void update() {
        this.allButtons.forEach(GameUpdateInterface::update);
    }

    @Override
    public void render(Graphics2D g) {
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
            this.gameAudioPlayer.toggleSongMute();
        }

        else if (this.sfxButton.isIn(e) && this.sfxButton.getMouseState().equals(MouseState.PRESS)) {
            this.sfxButton.changeMul();
            this.gameAudioPlayer.toggleEffectMute();
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
            float valueBefore = volumeButtons.getFloatValue();
            this.volumeButtons.changeX(e.getX());
            float valueAfter = volumeButtons.getFloatValue();

            if (valueBefore != valueAfter) {
                this.gameAudioPlayer.setVolume(valueAfter);
            }
        }
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
