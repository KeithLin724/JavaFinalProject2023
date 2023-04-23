package main;

import java.awt.Graphics;
import java.io.IOException;
import java.util.logging.Logger;

import Game.GUI.GameMenu;
import Game.GUI.GamePlaying;
import Game.PLUG.gameDrawer.GameAnimatedDrawer;
import Game.state.GameState;
import base.BaseGameConstant;

public class Game extends BaseGameConstant implements Runnable, GameAnimatedDrawer {
    private GameWindow gameWindow;
    private GamePanel gamePanel;

    private GameMenu gameMenu;
    private GamePlaying gamePlaying;

    private Thread gameThread;

    private static final double FPS = 120;
    private static final double UPS = 200;

    private static Logger LOGGER = Logger.getLogger(Game.class.getName());

    // construct
    public Game() {
        try {
            this.initClasses();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initClasses() throws IOException {
        // translator = new TranslatorTester(this);

        gamePanel = new GamePanel(this);
        gamePanel.init();

        this.gameMenu = new GameMenu(this);
        this.gamePlaying = new GamePlaying(this);

        this.gamePlaying.initClass();
    }

    // Start Game-loop
    public void runGame() {
        gameWindow = new GameWindow();
        gameWindow.init(gamePanel);

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / FPS;
        double timePerUpdate = 1000000000.0 / UPS;

        long lastFrame = System.nanoTime();
        long currentFrame = System.nanoTime();
        long lastCheck = System.currentTimeMillis();

        int frames = 0;
        int updates = 0;

        double deltaF = 0;
        double deltaU = 0;

        while (true) {

            currentFrame = System.nanoTime();
            deltaF += (currentFrame - lastFrame) / timePerFrame;
            deltaU += (currentFrame - lastFrame) / timePerUpdate;
            lastFrame = currentFrame;

            if (deltaU >= 1) {
                this.update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

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
            case MENU -> {
                this.gameMenu.update();
            }
            case PLAYING -> {
                this.gamePlaying.update();
            }

        }
    }

    @Override
    public void render(Graphics g) {
        switch (GameState.getState()) {
            case MENU -> {
                this.gameMenu.render(g);
            }
            case PLAYING -> {
                this.gamePlaying.render(g);
            }

        }
    }

    public void windowLostFocus() {
        if (GameState.getState() == GameState.PLAYING) {
            this.gamePlaying.windowLostFocus();
        }
    }

}
