package Game.GUI;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import Game.GameSourceFilePath;
import Game.Player;
import Game.Loader.GameElementLoader;
import Game.Loader.ImageLoader;
import Game.PLUG.GameStateMethod;
import Game.gameBackground.GameLevelManager;
import logic.input.Direction;
import main.Game;
import online.GameClient;
import online.GameMainOnlinePlayer;
import online.GameOnlinePlayer;
import online.InternetBase.InternetFunction;

import static base.BaseGameConstant.GAME_WIDTH;
import static base.BaseGameConstant.GAME_HEIGHT;

public class GameOnline extends GameStateBase implements GameStateMethod {
    private BufferedImage bgImage;

    public static final Logger LOGGER = Logger.getLogger(GameOnline.class.getName());

    private static final int port = 7000;
    private GameLevelManager gameLevelManager;

    // test
    private String IP = "127.0.0.70";

    // private Player player;
    private GameMainOnlinePlayer player;
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

        // run listen the server
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(this::listenToServer);
        executorService.shutdown();

    }

    private void commandProcess(String commandStr) {

        String[] command = InternetFunction.commandSplit(commandStr);
        System.out.println(commandStr);

        // TODO:
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

        player = (GameMainOnlinePlayer) GameElementLoader
                .getTestingGameCharacter(GameSourceFilePath.PLAYER_MAIN_CHARACTER_TEXT_FILE);

        assert player != null;
        player.init(200, 200);
        player.setLevelData(gameLevelManager.getGameLevel().getLevel2D());
        player.setLevel(gameLevelManager.getGameLevel());

        // setting online
        this.player
                .setGameClient(client)
                .setGameOnline(this);
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

        // gameLevel
        gameLevelManager.render(g);

        // player render
        this.player.render(g);
        this.gameOnlinePlayer.render(g);
    }

    private void listenToServer() {
        LOGGER.info("link to server...");
        String recvStr;

        try {
            while ((recvStr = this.client.recvMessagePlug()) != null) {
                // String[] command = InternetFunction.commandSplit(recvStr);
                commandProcess(recvStr);
            }
        } catch (IOException e) {
            client.close();
        }
        client.close();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            this.player.setAttacking(true);
        }
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

    private void keyEventToPlayerMove(KeyEvent e, boolean isMoveIt) {
        switch (e.getKeyCode()) {

            case KeyEvent.VK_A -> this.player.setDirection(Direction.LEFT, isMoveIt);

            case KeyEvent.VK_D -> this.player.setDirection(Direction.RIGHT, isMoveIt);

            case KeyEvent.VK_SPACE -> this.player.setJump(isMoveIt);

            // case KeyEvent.VK_BACK_SPACE -> GameState.setState(GameState.MENU);

            // case KeyEvent.VK_ESCAPE -> this.paused = (isMoveIt ? !this.paused :
            // this.paused);

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // if (this.gameOver) {
        // this.gameOverDisplayLayer.keyPressed(e);
        // return;
        // }

        this.keyEventToPlayerMove(e, true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // if (this.gameOver) {
        // this.gameOverDisplayLayer.keyReleased(e);
        // return;
        // }

        this.keyEventToPlayerMove(e, false);
    }
}
