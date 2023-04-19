package logic.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.Game;

public class KeyboardInputs implements KeyListener {

    private Game game;

    public KeyboardInputs(Game game) {
        this.game = game;
    }

    private Direction keyEventToMoveCommand(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> {
                return Direction.UP;
            }
            case KeyEvent.VK_A -> {
                return Direction.LEFT;
            }
            case KeyEvent.VK_S -> {
                return Direction.DOWN;
            }
            case KeyEvent.VK_D -> {
                return Direction.RIGHT;
            }
        }
        return Direction.NONE;
    }

    private void updatePlayerMoveState(Direction moveCmd) {
        game.getTranslator().setPlayMove(moveCmd);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // None
    }

    @Override
    public void keyPressed(KeyEvent e) {
        var state = this.keyEventToMoveCommand(e);
        this.updatePlayerMoveState(state);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.updatePlayerMoveState(Direction.NONE);
    }

}
