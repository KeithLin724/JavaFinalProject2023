package Game.GUI.ui.buttons;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Game.GUI.UIConstant.PauseLayerButtons;
import Game.gameBase.GamePoint;

public class GameSoundButton extends GameButtonBase {

    private BufferedImage[][] soundImages;

    public static final int BUTTON_ROW_NUMBER = 2;
    public static final int BUTTON_COLUMN_NUMBER = 3;

    private boolean isMul;

    public boolean isMul() {
        return isMul;
    }

    public void setMul(boolean isMut) {
        this.isMul = isMut;
    }

    public void changeMul() {
        this.isMul = !this.isMul;
    }

    public GameSoundButton(GamePoint point) {
        super(point);

        // loadImage();
    }

    public void setSoundImages(BufferedImage[][] soundImages) {
        this.soundImages = soundImages;
    }

    @Override
    protected void initBounds() {
        this.bounds = new Rectangle(this.point.getIntX(), this.point.getIntY(),
                PauseLayerButtons.SOUND_SIZE.value,
                PauseLayerButtons.SOUND_SIZE.value);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(this.soundImages[!isMul ? 0 : 1][this.mouseState.toDisplayIndex],
                this.point.getIntX(), this.point.getIntY(),
                PauseLayerButtons.SOUND_SIZE.value,
                PauseLayerButtons.SOUND_SIZE.value,
                null);
    }
}
