package main;

import java.awt.Graphics;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

    private Game game;

    public GamePanel(Game game) {
        this.game = game;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        game.getTranslator().render(g);
    }

}
