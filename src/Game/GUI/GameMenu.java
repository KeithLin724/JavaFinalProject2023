package Game.GUI;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

import Game.GUI.ui.buttons.GameButtonBase;
import Game.GameElementFactory;
import Game.GameSourceFilePath;
import Game.GUI.ui.buttons.GameMenuButton;
import Game.Loader.ImageLoader;
import Game.PLUG.GameStateMethod;
import Game.gameBase.GameCalculator;
import Game.gameBase.GamePoint;
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

    private GameUnitPair menuWH;
    private GamePoint menuBgPoint;

    private static final float[] xMenuArray = { GAME_WIDTH / 2.0F, GAME_WIDTH / 2.0F, GAME_WIDTH / 2.0F };
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
        this.backgroundMenuImage = ImageLoader.loadImage(GameSourceFilePath.MENU_SELECT_BACKGROUND_IMAGE_CITY);
    }

    private void loadGameMenuBackgroundImage() throws IOException {
        this.backgroundImage = ImageLoader.loadImage(GameSourceFilePath.MENU_BACKGROUND_IMAGE);

        this.menuWH = GameCalculator.calculate(
                this.backgroundImage.getWidth(), this.backgroundImage.getHeight(),
                x -> (int) (x * SCALE));

        this.menuBgPoint = GamePoint.buildGamePoint(GAME_WIDTH / 2.0F - menuWH.x / 2.0F, 45 * SCALE);
    }

    private void loadGameMenuButton() throws IOException {
        buttons = GameElementFactory.getAllMenuButtons(xMenuArray, yMenuArray);
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(this.backgroundMenuImage, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);

        g.drawImage(backgroundImage,
                this.menuBgPoint.getIntX(), this.menuBgPoint.getIntY(),
                this.menuWH.getIntW(), this.menuWH.getIntH(),
                null);

        Arrays.stream(this.buttons).forEach(item -> item.render(g));
    }

    @Override
    public void update() {
        Arrays.stream(this.buttons).forEach(GameMenuButton::update);
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
                .ifPresent(GameMenuButton::applyGameState);

        this.resetButtons();
    }

    private void resetButtons() {
        Arrays.stream(this.buttons).forEach(GameButtonBase::resetState);
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
        Arrays.stream(this.buttons).forEach(GameButtonBase::resetState);

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
