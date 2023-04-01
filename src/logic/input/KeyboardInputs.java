package logic.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.Game;

public class KeyboardInputs implements KeyListener {

    private Game game;

    public KeyboardInputs(Game game) {
        this.game = game;
    }

    private MoveCommand keyEventToMoveCommand(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> {
                return MoveCommand.UP;
            }
            case KeyEvent.VK_A -> {
                return MoveCommand.LEFT;
            }
            case KeyEvent.VK_S -> {
                return MoveCommand.DOWN;
            }
            case KeyEvent.VK_D -> {
                return MoveCommand.RIGHT;
            }
        }
        return MoveCommand.NONE;
    }

    private void updatePlayerMoveState(MoveCommand moveCmd, boolean move) {
        game.getTranslator().setPlayMove(moveCmd, move);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // None
    }

    @Override
    public void keyPressed(KeyEvent e) {
        var state = this.keyEventToMoveCommand(e);
        this.updatePlayerMoveState(state, true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        var state = this.keyEventToMoveCommand(e);
        this.updatePlayerMoveState(state, false);
    }

}
