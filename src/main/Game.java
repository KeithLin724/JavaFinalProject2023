package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.IOException;
import java.util.logging.Logger;

import GUI.Test.TranslatorTester;
import Game.PLUG.gameDrawer.GameRenderInterface;
import Game.gameBackground.GameLevelManager;
import Game.state.GameState;
import base.BaseGameConstant;
import logic.input.KeyboardInputs;
import logic.input.MouseInputs;

public class Game extends BaseGameConstant implements Runnable, GameRenderInterface {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    // private Translator translator;
    private TranslatorTester translator;
    private Thread gameThread;

    private static final double FPS = 120;
    private static final double UPS = 200;

    private MouseInputs mouseInputs;
    private KeyboardInputs keyboardInputs;

    // private GameMapLevelManager gameMapLevelManager;

    private static Logger LOGGER = Logger.getLogger(Game.class.getName());

    // construct
    public Game() {
        try {
            this.initClasses();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.gamePanelSetting();
    }

    private void initClasses() throws IOException {
        mouseInputs = new MouseInputs(this);
        keyboardInputs = new KeyboardInputs(this);

        translator = new TranslatorTester(this);
    }

    private void gamePanelSetting() {
        gamePanel = new GamePanel(this);
        gamePanel.addKeyListener(keyboardInputs);
        gamePanel.addMouseListener(mouseInputs);
        gamePanel.addMouseMotionListener(mouseInputs);

        gamePanel.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();
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
                translator.update();
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

    public TranslatorTester getTranslator() {
        return translator;
    }

    @Override
    public void render(Graphics g) {
        this.translator.render(g);
    }

    public void windowLostFocus() {
        translator.stopPlayerMoving();
    }
}
