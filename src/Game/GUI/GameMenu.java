package Game.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

import Game.GameElementFactory;
import Game.GameSourceFilePath;
import Game.GUI.ui.GameMenuButton;
import Game.Loader.ImageLoader;
import Game.PLUG.GameStateMethod;
import Game.gameBase.GameCalculator;
import Game.gameBase.GamePoint;
import Game.gameBase.GameUnitPair;
import Game.state.GameState;
import Game.state.MouseState;
import main.Game;

import static base.BaseGameConstant.GAME_WIDTH;
// import static base.BaseGameConstant.GAME_HEIGHT;
import static base.BaseGameConstant.SCALE;

public class GameMenu extends GameStateBase implements GameStateMethod {

    private GameMenuButton[] buttons = new GameMenuButton[3];
    private BufferedImage backgroundImage;

    private Point menuWH;
    private Point menuBgPoint;

    public GameMenu(Game game) {
        super(game);
        this.loadGameMenuButton();
        this.loadGameMenuBackgroundImage();
    }

    private void loadGameMenuBackgroundImage() {
        try {
            this.backgroundImage = ImageLoader.loadImage(GameSourceFilePath.MENU_BACKGROUND_IMAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.menuWH = GameCalculator
                .calculate(
                        this.backgroundImage.getWidth(), this.backgroundImage.getHeight(),
                        x -> (int) (x * SCALE))
                .toIntPoint();

        this.menuBgPoint = GameUnitPair
                .buildGameUnitPair(GAME_WIDTH / 2 - menuWH.x / 2, 45 * SCALE)
                .toIntPoint();
    }

    private void loadGameMenuButton() {

        try {
            buttons[0] = GameElementFactory.getGameMenuButton(GAME_WIDTH / 2, 150 * SCALE, 0, GameState.PLAYING);
            buttons[1] = GameElementFactory.getGameMenuButton(GAME_WIDTH / 2, 220 * SCALE, 1, GameState.OPTIONS);
            buttons[2] = GameElementFactory.getGameMenuButton(GAME_WIDTH / 2, 290 * SCALE, 2, GameState.QUITS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(Graphics g) {
        // g.setColor(Color.BLACK);
        // g.drawString("Menu", GAME_WIDTH / 2, GAME_HEIGHT / 2);
        g.drawImage(backgroundImage, this.menuBgPoint.x, this.menuBgPoint.y, this.menuWH.x, this.menuWH.y, null);
        Arrays.stream(this.buttons).forEach(item -> item.render(g));
    }

    @Override
    public void update() {
        // for (var item : this.buttons) {
        // item.update();
        // }

        Arrays.stream(this.buttons).forEach(item -> item.update());
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (var item : this.buttons) {
            if (this.isIn(e, item)) {
                item.setMouseState(MouseState.PRESS);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (var item : this.buttons) {
            if (this.isIn(e, item) && item.getMouseState() == MouseState.PRESS) {
                item.applyGameState();
                break;
            }
        }
        this.resetButtons();
    }

    private void resetButtons() {
        Arrays.stream(this.buttons).forEach(item -> item.resetMouseState());
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
        Arrays.stream(this.buttons).forEach(item -> item.resetMouseState());

        for (var item : this.buttons) {
            if (this.isIn(e, item)) {
                item.setMouseState(MouseState.OVER);
                break;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            GameState.setState(GameState.PLAYING);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
