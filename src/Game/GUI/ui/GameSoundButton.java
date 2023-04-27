package Game.GUI.ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import Game.GameSourceFilePath;
import Game.GUI.UIConstant.PauseLayerButtons;
import Game.Loader.ImageLoader;
import Game.gameBase.GamePoint;
import Game.state.MouseState;

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

        loadImage();
        // this.initBounds();
    }

    private void loadImage() {
        try {
            this.soundImages = ImageLoader.loadSoundButtonImage(GameSourceFilePath.SOUND_BUTTON_IMAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initBounds() {
        this.bounds = new Rectangle((int) this.point.x,
                (int) this.point.y, PauseLayerButtons.SOUND_SIZE.value,
                PauseLayerButtons.SOUND_SIZE.value);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(this.soundImages[!isMul ? 0 : 1][this.mouseState.toDisplayIndex],
                (int) this.point.x, (int) this.point.y,
                PauseLayerButtons.SOUND_SIZE.value,
                PauseLayerButtons.SOUND_SIZE.value,
                null);

    }
}
