package main;

import java.awt.Graphics;

import logic.input.MoveCommand;
import oldVersion.entities.Online;
import oldVersion.entities.Player;

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

    public void setPlayMove(MoveCommand moveCmd, boolean move) {
        switch (moveCmd) {
            case UP -> {
                this.player.setUp(move);
            }
            case LEFT -> {
                this.player.setLeft(move);
            }
            case DOWN -> {
                this.player.setDown(move);
            }
            case RIGHT -> {
                this.player.setRight(move);
            }
            case NONE -> throw new UnsupportedOperationException("Unimplemented case: " + moveCmd);
            default -> throw new IllegalArgumentException("Unexpected value: " + moveCmd);
        }
    }

}
