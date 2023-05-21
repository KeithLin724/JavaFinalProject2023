package Game.GUI;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import Game.GameSourceFilePath;
import Game.Player;
import Game.Loader.GameElementLoader;
import Game.Loader.ImageLoader;
import Game.PLUG.GameStateMethod;
import Game.gameBackground.GameLevelManager;
import main.Game;
import online.GameClient;
import online.GameOnlinePlayer;

import static base.BaseGameConstant.GAME_WIDTH;
import static base.BaseGameConstant.GAME_HEIGHT;

public class GameOnline extends GameStateBase implements GameStateMethod {
    private BufferedImage bgImage;

    public static final Logger LOGGER = Logger.getLogger(GameOnline.class.getName());

    private static final int port = 7000;
    private GameLevelManager gameLevelManager;

    // test
    private String IP = "127.0.0.70";

    private Player player;
    private GameOnlinePlayer gameOnlinePlayer;
    private String roomPlay;

    private String userName;
    private GameClient client;

    public GameOnline(Game game) {
        super(game);

        try {
            loadImage();

            initClient();
            initClass();

            client = new GameClient(IP, port, userName);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "client open error", e);
        }
    }

    private void loadImage() throws IOException {
        this.bgImage = ImageLoader.loadImage(GameSourceFilePath.MENU_SELECT_BACKGROUND_IMAGE_CITY);
    }

    // about the IP , user name , and the Room
    private void initClient() {
        this.IP = JOptionPane.showInputDialog(this.game.getGameWindow(), "Input Server IP");
        this.userName = JOptionPane.showInputDialog(this.game.getGameWindow(), "Input UserName");
        this.roomPlay = JOptionPane.showInputDialog(this.game.getGameWindow(), "Play Room Name");
    }

    // setting player pos
    //
    private void initClass() throws IOException {
        this.gameLevelManager = new GameLevelManager(this.game);
        this.gameLevelManager.setGameLevelFromFile(GameSourceFilePath.BACKGROUND_LEVEL_ONLINE);
        player = GameElementLoader.getTestingGameCharacter(GameSourceFilePath.PLAYER_MAIN_CHARACTER_TEXT_FILE);

        assert player != null;

    }

    // for send and recv server message
    @Override
    public void update() {
        this.gameLevelManager.update();
        this.player.update();

    }

    // render thing
    @Override
    public void render(Graphics2D g) {
        // background
        g.drawImage(bgImage,
                0, 0,
                GAME_WIDTH, GAME_HEIGHT,
                null);

        gameLevelManager.render(g);

        this.player.render(g);
        this.gameOnlinePlayer.render(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

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
