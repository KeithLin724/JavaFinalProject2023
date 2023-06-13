package logic.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Game.state.GameState;
import main.Game;

public class KeyboardInputs implements KeyListener {

    private Game game;

    public KeyboardInputs(Game game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // None
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (GameState.getState()) {
            case MENU -> this.game.getGameMenu().keyPressed(e);

            case PLAYING -> this.game.getGamePlaying().keyPressed(e);

            case OPTIONS -> this.game.getGameOptions().keyPressed(e);

            case CREDITS -> this.game.getGameCredits().keyPressed(e);

            default -> {
                // None:
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (GameState.getState()) {

            case MENU -> this.game.getGameMenu().keyReleased(e);

            case PLAYING -> this.game.getGamePlaying().keyReleased(e);

            case OPTIONS -> this.game.getGameOptions().keyReleased(e);

            case CREDITS -> this.game.getGameCredits().keyReleased(e);

            default -> {
                // None :
            }

        }
    }

}
