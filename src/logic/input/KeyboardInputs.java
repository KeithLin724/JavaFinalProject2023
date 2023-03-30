package logic.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.Game;

public class KeyboardInputs implements KeyListener {

    private Game game;

    public KeyboardInputs(Game game) {
        this.game = game;
    }

    private String keyEventToMoveCommand(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> {
                return MoveCommandConstant.UP;
            }
            case KeyEvent.VK_A -> {
                return MoveCommandConstant.LEFT;
            }
            case KeyEvent.VK_S -> {
                return MoveCommandConstant.DOWN;
            }
            case KeyEvent.VK_D -> {
                return MoveCommandConstant.RIGHT;
            }
        }
        return MoveCommandConstant.NONE;
    }

    private void updatePlayerMoveState(String moveCmd, boolean move) {
        game.getTranslator().setPlayMove(moveCmd, move);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // None
    }

    @Override
    public void keyPressed(KeyEvent e) {
        String state = this.keyEventToMoveCommand(e);
        this.updatePlayerMoveState(state, true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        String state = this.keyEventToMoveCommand(e);
        this.updatePlayerMoveState(state, false);
    }

}
