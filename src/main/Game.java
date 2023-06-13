package main;

import Game.GameSourceFilePath;
import Game.GUI.GameCredits;
import Game.GUI.GameMenu;
import Game.GUI.GameOptions;
import Game.GUI.GamePlaying;
import Game.GUI.ui.GameAudioOptions;
import Game.PLUG.gameDrawer.GameAnimatedDrawer;
import Game.audio.GameAudioPlayer;
import Game.state.GameState;
import base.BaseGameConstant;

import java.awt.*;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * @author USER
 */
public class Game extends BaseGameConstant implements Runnable, GameAnimatedDrawer {
    private GameWindow gameWindow;
    private GamePanel gamePanel;

    private GameMenu gameMenu;
    private GamePlaying gamePlaying;

    private GameAudioOptions gameAudioOptions;
    private GameAudioPlayer gameAudioPlayer;

    private GameOptions gameOptions;
    private GameCredits gameCredits;

    private Thread gameThread;

    private static final double FPS = 120;
    private static final double UPS = 200;

    private int updates = 0, frames = 0;

    private static final Logger LOGGER = Logger.getLogger(Game.class.getName());

    // construct
    public Game() {
        try {
            this.initClasses();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initClasses() throws IOException {
        gamePanel = new GamePanel(this);
        gamePanel.init();

        this.gameAudioPlayer = new GameAudioPlayer(GameSourceFilePath.AUDIO_FOLDER_PATH);
        this.gameAudioOptions = new GameAudioOptions(this.gameAudioPlayer);

        this.gameOptions = new GameOptions(this);

        this.gameMenu = new GameMenu(this);
        this.gamePlaying = new GamePlaying(this);
        this.gameCredits = new GameCredits(this);

        this.gamePlaying.initClass();
    }

    // Start Game-loop
    public void runGame() {
        gameWindow = new GameWindow();
        gameWindow.init(gamePanel);

        gameThread = new Thread(this);
        gameThread.start();
    }

    public GameWindow getGameWindow() {
        return this.gameWindow;
    }

    /**
     * This function updates the game logic at a fixed rate of UPS (updates per
     * second) using a time-based
     * approach.
     */
    public void gameLogicUpdateThread() {
        double timePerUpdate = 1000000000.0 / UPS;
        long currentFrame = System.nanoTime();
        double deltaU = 0;
        long lastFrame = System.nanoTime();

        while (true) {
            currentFrame = System.nanoTime();
            deltaU += (currentFrame - lastFrame) / timePerUpdate;
            lastFrame = currentFrame;

            if (deltaU >= 1) {
                this.update();
                updates++;
                deltaU--;
            }
        }
    }

    /**
     * This function renders a game panel at a specified frame rate using a
     * time-based approach.
     */
    public void gameRenderThread() {
        double timePerFrame = 1000000000.0 / FPS;
        long currentFrame;
        double deltaF = 0;
        long lastFrame = System.nanoTime();

        while (true) {
            currentFrame = System.nanoTime();
            deltaF += (currentFrame - lastFrame) / timePerFrame;
            lastFrame = currentFrame;

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }
        }
    }

    @Override
    public void run() {

        // double timePerFrame = 1000000000.0 / FPS;
        // double timePerUpdate = 1000000000.0 / UPS;

        // long lastFrame = System.nanoTime();
        // long currentFrame = System.nanoTime();
        long lastCheck = System.currentTimeMillis();

        // int frames = 0;
        // int updates = 0;

        // double deltaF = 0;
        // double deltaU = 0;

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.execute(this::gameLogicUpdateThread);
        executorService.execute(this::gameRenderThread);

        executorService.shutdown();

        while (true) {

            // currentFrame = System.nanoTime();
            // deltaF += (currentFrame - lastFrame) / timePerFrame;
            // deltaU += (currentFrame - lastFrame) / timePerUpdate;
            // lastFrame = currentFrame;

            // if (deltaU >= 1) {
            // this.update();
            // updates++;
            // deltaU--;
            // }

            // if (deltaF >= 1) {
            // gamePanel.repaint();
            // frames++;
            // deltaF--;
            // }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                LOGGER.info(String.format("FPS: %s| UPS: %s", frames, updates));
                frames = 0;
                updates = 0;
            }

        }
    }

    public GameMenu getGameMenu() {
        return this.gameMenu;
    }

    public GamePlaying getGamePlaying() {
        return this.gamePlaying;
    }

    @Override
    public void update() {
        switch (GameState.getState()) {
            case MENU -> this.gameMenu.update();

            case PLAYING -> this.gamePlaying.update();

            case OPTIONS -> this.gameOptions.update();

            case CREDITS -> this.gameCredits.update();

            default -> {
                // None
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        switch (GameState.getState()) {
            case MENU -> this.gameMenu.render(g);

            case PLAYING -> this.gamePlaying.render(g);

            case OPTIONS -> this.gameOptions.render(g);

            case CREDITS -> this.gameCredits.render(g);

            default -> {
                // None
            }

        }
    }

    public void windowLostFocus() {
        if (GameState.getState() == GameState.PLAYING) {
            this.gamePlaying.windowLostFocus();
        }
    }

    public GameOptions getGameOptions() {
        return this.gameOptions;
    }

    public GameAudioOptions getGameAudioOptions() {
        return this.gameAudioOptions;
    }

    public GameAudioPlayer getGameAudioPlayer() {
        return this.gameAudioPlayer;
    }

    public GameCredits getGameCredits() {
        return this.gameCredits;
    }

}
