package Game.GUI.ui.buttons;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Game.GUI.UIConstant.MenuButtons;
import Game.gameBase.GamePoint;
import Game.state.GameState;
import Game.state.MouseState;

/**
 * MenuButton
 */
public class GameMenuButton extends GameButtonBase {
    public static final int pitchesNumber = 3;
    private static final int xOffsetCenter = MenuButtons.B_WIDTH.value / 2;

    private GameState state;

    private BufferedImage[] images;

    public GameMenuButton(GamePoint point, BufferedImage[] images, GameState state) {
        super(point);
        this.point = point;

        this.images = images;
        this.state = state;

        this.mouseState = MouseState.NONE;
    }

    @Override
    protected void initBounds() {
        this.bounds = new Rectangle(
                (int) this.point.x - xOffsetCenter, (int) this.point.y,
                MenuButtons.B_WIDTH.value,
                MenuButtons.B_HEIGHT.value);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(this.images[this.mouseState.toDisplayIndex],
                (int) this.point.x - xOffsetCenter, (int) this.point.y,
                MenuButtons.B_WIDTH.value, MenuButtons.B_HEIGHT.value, null);
    }

    public void applyGameState() {
        GameState.setState(state);
    }

}