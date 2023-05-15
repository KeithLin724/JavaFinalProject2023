package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import logic.input.KeyboardInputs;
import logic.input.MouseInputs;

import static base.BaseGameConstant.GAME_WIDTH;
import static base.BaseGameConstant.GAME_HEIGHT;;

// this class for drawing 

public class GamePanel extends JPanel {
    // private MouseInputs mouseInputs;
    private Game game;

    private MouseInputs mouseInputs;
    private KeyboardInputs keyboardInputs;

    public GamePanel(Game game) {
        this.game = game;
        this.mouseInputs = new MouseInputs(game);
        this.keyboardInputs = new KeyboardInputs(game);
    }

    public void init() {
        // add Listener
        this.addKeyListener(keyboardInputs);
        this.addMouseListener(mouseInputs);
        this.addMouseMotionListener(mouseInputs);

        // set size
        this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));

        // setting the window
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render((Graphics2D) g);
    }

    public Game getGame() {
        return this.game;
    }

}
