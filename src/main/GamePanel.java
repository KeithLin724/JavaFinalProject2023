package main;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import logic.input.MouseInputs;

// this class for drawing 

public class GamePanel extends JPanel {
    // private MouseInputs mouseInputs;
    private Game game;

    public GamePanel(Game game) {
        this.game = game;
    }

    // private void setPanelSize() {
    // Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    // }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }

    public Game getGame() {
        return this.game;
    }

}
