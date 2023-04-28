package Game.GUI.ui.buttons;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Game.GUI.UIConstant.VolumeButtons;
import Game.gameBase.GamePoint;

public class GameVolumeButton extends GameButtonBase {

    public static final int pitchesNumber = 3;

    private BufferedImage[] image;
    private BufferedImage slider;

    private int buttonX;
    private final int sliderMin, sliderMax;
    private static final float sliderXOffset = VolumeButtons.VOLUME_WIDTH.value / 2.0F;

    @Override
    protected void initBounds() {
        // this.point.addToX(this.point.x + VolumeButtons.SLIDER_WIDTH.value / 2.0F);
        this.buttonX = (int) (this.point.x + VolumeButtons.SLIDER_WIDTH.value / 2.0F);

        this.bounds = new Rectangle(this.buttonX, (int) this.point.y,
                VolumeButtons.VOLUME_WIDTH.value,
                VolumeButtons.VOLUME_HEIGHT.value);

        this.bounds.x -= sliderXOffset;

    }

    public GameVolumeButton(GamePoint point, BufferedImage[] image, BufferedImage slider) {
        super(point);

        this.image = image;
        this.slider = slider;

        this.sliderMin = (int) (point.x + sliderXOffset);
        this.sliderMax = (int) (point.x + VolumeButtons.SLIDER_WIDTH.value - sliderXOffset);
    }

    public void setImage(BufferedImage[] image) {
        this.image = image;
    }

    public void setSlider(BufferedImage slider) {
        this.slider = slider;
    }

    public void changeX(int x) {
        if (x < this.sliderMin) {
            this.buttonX = this.sliderMin;
            return;
        }

        if (x > this.sliderMax) {
            this.buttonX = this.sliderMax;
            return;
        }

        this.buttonX = x;

        this.bounds.x = x;
        this.bounds.x -= sliderXOffset;

    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(slider,
                (int) this.point.x, (int) this.point.y,
                VolumeButtons.SLIDER_WIDTH.value,
                VolumeButtons.VOLUME_HEIGHT.value, null);

        g.drawImage(this.image[this.mouseState.toDisplayIndex],
                (int) (buttonX - sliderXOffset), (int) this.point.y,
                VolumeButtons.VOLUME_WIDTH.value,
                VolumeButtons.VOLUME_HEIGHT.value, null);
    }

}
