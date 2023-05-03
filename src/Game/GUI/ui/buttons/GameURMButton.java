package Game.GUI.ui.buttons;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Game.GUI.UIConstant.URMButtons;
import Game.gameBase.GamePoint;

public class GameURMButton extends GameButtonBase {

    public static final int pitchesNumber = 3;

    private BufferedImage[] image;

    public void setImage(BufferedImage[] image) {
        this.image = image;
    }

    public GameURMButton(GamePoint point, BufferedImage[] image) {
        super(point);

        this.image = image;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(this.image[this.mouseState.toDisplayIndex],
                this.point.getIntX(), this.point.getIntY(),
                URMButtons.URM_SIZE.value, URMButtons.URM_SIZE.value,
                null);

    }

    @Override
    protected void initBounds() {
        this.bounds = new Rectangle(
                this.point.getIntX(), this.point.getIntY(),
                URMButtons.URM_SIZE.value, URMButtons.URM_SIZE.value);
    }

}
