package main;

import java.awt.Dimension;
import Inputs.KeyboardInputs;
import Inputs.MouseInputs;

public class Game extends MainClass implements Runnable {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Translator translator;
    private Thread gameThread;
    private final int FPS = 120;
    private final int UPS = 200;

    private MouseInputs mouseInputs;
    private KeyboardInputs keyboardInputs;

    public Game() {
        initClasses();
        gamePanel = new GamePanel(this);
        gamePanel.addKeyListener(keyboardInputs);
        gamePanel.addMouseListener(mouseInputs);
        gamePanel.addMouseMotionListener(mouseInputs);
        gamePanel.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();
        // Start Game-Loop
        gameWindow = new GameWindow(gamePanel);
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * It initializes the classes that are used in the program
     */
    private void initClasses() {
        mouseInputs = new MouseInputs(this);
        keyboardInputs = new KeyboardInputs(this);
        translator = new Translator();
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
                System.out.println("FPS: " + frames + "| UPS: " + updates);
                frames = 0;
                updates = 0;
            }

        }
    }

    public Translator getTranslator() {
        return translator;
    }
}
