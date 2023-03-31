package main;

import java.awt.Graphics;
import entities.Player;
import logic.input.MoveCommandConstant;

public class Translator extends Online {

    private Player player;

    public Translator() {
        player = new Player(200, 200);
    }

    public void updateLogic() {
        player.update();
    }

    public void render(Graphics g) {
        player.render(g);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayMove(String moveCmd, boolean move) {
        switch (moveCmd) {
            case MoveCommandConstant.UP -> {
                this.player.setUp(move);
            }
            case MoveCommandConstant.LEFT -> {
                this.player.setLeft(move);
            }
            case MoveCommandConstant.DOWN -> {
                this.player.setDown(move);
            }
            case MoveCommandConstant.RIGHT -> {
                this.player.setRight(move);
            }
        }
    }

}
