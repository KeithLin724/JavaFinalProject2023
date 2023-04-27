package Game.GUI.ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Game.GUI.UIConstant.MenuButtons;
import Game.PLUG.gameDrawer.GameAnimatedDrawer;
import Game.gameBase.GamePoint;
import Game.state.GameState;
import Game.state.MouseState;

/**
 * MenuButton
 */
public class GameMenuButton implements GameAnimatedDrawer {
    public static final int pitchesNumber = 3;
    private static final int xOffsetCenter = MenuButtons.B_WIDTH.value / 2;

    private GamePoint point;

    private GameState state;

    private BufferedImage[] images;

    private MouseState mouseState;

    private Rectangle bounds;

    public GameMenuButton(GamePoint point, BufferedImage[] images, GameState state) {
        this.point = point;
        // this.imageRow = imageRow;
        this.images = images;
        this.state = state;

        this.mouseState = MouseState.NONE;

        this.initBounds();
    }

    private void initBounds() {
        this.bounds = new Rectangle(
                (int) this.point.x - xOffsetCenter, (int) this.point.y,
                MenuButtons.B_WIDTH.value,
                MenuButtons.B_HEIGHT.value);
    }

    public void setMouseState(MouseState mouseState) {
        this.mouseState = mouseState;
    }

    public MouseState getMouseState() {
        return this.mouseState;
    }

    @Override
    public void update() {
        // this.displayIndex = this.mouseState.toDisplayIndex;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(this.images[this.mouseState.toDisplayIndex],
                (int) this.point.x - xOffsetCenter, (int) this.point.y,
                MenuButtons.B_WIDTH.value, MenuButtons.B_HEIGHT.value, null);
    }

    public Rectangle getBounds() {
        return this.bounds;
    }

    public void applyGameState() {
        GameState.setState(state);
    }

    public void resetMouseState() {
        this.mouseState = MouseState.NONE;
    }

}