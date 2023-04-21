package main;

import java.awt.Graphics;
import javax.swing.JPanel;

// this class for drawing 

public class GamePanel extends JPanel {

    private Game game;

    public GamePanel(Game game) {
        this.game = game;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }

    public Game getGame() {
        return this.game;
    }

}
