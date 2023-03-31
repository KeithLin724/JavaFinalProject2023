package main;

import java.awt.Dimension;
import java.util.logging.Logger;

import base.BaseGameConstant;
import logic.input.KeyboardInputs;
import logic.input.MouseInputs;

public class Game extends BaseGameConstant implements Runnable {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Translator translator;
    private Thread gameThread;
    private final int FPS = 120;
    private final int UPS = 200;

    private MouseInputs mouseInputs;
    private KeyboardInputs keyboardInputs;

    private static Logger LOGGER = Logger.getLogger(Game.class.getName());

    // construct
    public Game() {
        this.initClasses();
        this.gamePanelSetting();
    }

    private void initClasses() {
        mouseInputs = new MouseInputs(this);
        keyboardInputs = new KeyboardInputs(this);
        translator = new Translator();
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
        gameWindow = new GameWindow(gamePanel);
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
                translator.updateLogic();
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

    public Translator getTranslator() {
        return translator;
    }
}
