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

    private void updatePlayerMoveState(Direction moveCmd, boolean isMoving) {
        game.getTranslator().setPlayMove(moveCmd, isMoving);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // None
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            game.getTranslator().setPlayerJump(true);
            return;
        }

        var state = this.keyEventToMoveCommand(e);
        // System.out.println(state);
        this.updatePlayerMoveState(state, true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            game.getTranslator().setPlayerJump(false);
            return;
        }

        var state = this.keyEventToMoveCommand(e);
        this.updatePlayerMoveState(state, false);
    }

}
