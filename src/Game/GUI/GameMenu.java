package Game.GUI;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

import Game.GameElementFactory;
import Game.GameSourceFilePath;
import Game.GUI.ui.buttons.GameMenuButton;
import Game.Loader.ImageLoader;
import Game.PLUG.GameStateMethod;
import Game.gameBase.GameCalculator;
import Game.gameBase.GameUnitPair;
import Game.state.GameState;
import Game.state.MouseState;
import main.Game;

import static base.BaseGameConstant.GAME_WIDTH;
import static base.BaseGameConstant.GAME_HEIGHT;
import static base.BaseGameConstant.SCALE;

public class GameMenu extends GameStateBase implements GameStateMethod {
    private static final int MENU_BUTTON_NUMBER = 3;

    private GameMenuButton[] buttons = new GameMenuButton[MENU_BUTTON_NUMBER];
    private BufferedImage backgroundImage, backgroundMenuImage;

    private Point menuWH;
    private Point menuBgPoint;

    private static final float[] xMenuArray = { GAME_WIDTH / 2, GAME_WIDTH / 2, GAME_WIDTH / 2 };
    private static final float[] yMenuArray = { 150 * SCALE, 220 * SCALE, 290 * SCALE };

    public GameMenu(Game game) {
        super(game);
        try {
            this.loadGameMenuButton();
            this.loadGameMenuBackgroundImage();
            this.loadGameMenuSelectBackgroundImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadGameMenuSelectBackgroundImage() throws IOException {
        this.backgroundMenuImage = ImageLoader.loadImage(GameSourceFilePath.MENU_SELECT_BACKGROUND_IMAGE);
    }

    private void loadGameMenuBackgroundImage() throws IOException {
        this.backgroundImage = ImageLoader.loadImage(GameSourceFilePath.MENU_BACKGROUND_IMAGE);

        this.menuWH = GameCalculator
                .calculate(
                        this.backgroundImage.getWidth(), this.backgroundImage.getHeight(),
                        x -> (int) (x * SCALE))
                .toIntPoint();

        this.menuBgPoint = GameUnitPair
                .buildGameUnitPair(GAME_WIDTH / 2 - menuWH.x / 2, 45 * SCALE)
                .toIntPoint();
    }

    private void loadGameMenuButton() throws IOException {
        buttons = GameElementFactory.getAllMenuButtons(xMenuArray, yMenuArray);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(this.backgroundMenuImage, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);

        g.drawImage(backgroundImage,
                this.menuBgPoint.x, this.menuBgPoint.y,
                this.menuWH.x, this.menuWH.y,
                null);

        Arrays.stream(this.buttons).forEach(item -> item.render(g));
    }

    @Override
    public void update() {
        Arrays.stream(this.buttons).forEach(item -> item.update());
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        Arrays.stream(this.buttons)
                .filter(item -> item.isIn(e))
                .findFirst()
                .ifPresent(item -> item.setMouseState(MouseState.PRESS));
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        Arrays.stream(this.buttons)
                .filter(item -> item.isIn(e) && item.getMouseState().equals(MouseState.PRESS))
                .findFirst()
                .ifPresent(item -> item.applyGameState());

        this.resetButtons();
    }

    private void resetButtons() {
        Arrays.stream(this.buttons).forEach(item -> item.resetState());
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
        Arrays.stream(this.buttons).forEach(item -> item.resetState());

        Arrays.stream(this.buttons)
                .filter(item -> item.isIn(e))
                .findFirst()
                .ifPresent(item -> item.setMouseState(MouseState.OVER));

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
