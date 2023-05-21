package logic.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import Game.state.GameState;
import main.Game;

public class MouseInputs implements MouseListener, MouseMotionListener {
    private Game game;

    public MouseInputs(Game game) {
        this.game = game;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        switch (GameState.getState()) {
            case MENU -> {
                // this.game.getGameMenu().mouseDragged(e);
            }
            case PLAYING -> this.game.getGamePlaying().mouseDragged(e);

            case OPTIONS -> this.game.getGameOptions().mouseDragged(e);

            default -> {

            }

        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // game.getTranslator().getPlayer();
        switch (GameState.getState()) {
            case MENU -> this.game.getGameMenu().mouseMoved(e);

            case PLAYING -> this.game.getGamePlaying().mouseMoved(e);

            case OPTIONS -> this.game.getGameOptions().mouseMoved(e);

            case QUITS -> System.exit(0);

            default -> throw new IllegalArgumentException("Unexpected value: " + GameState.getState());

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        switch (GameState.getState()) {
            case MENU -> this.game.getGameMenu().mouseClicked(e);

            case PLAYING -> this.game.getGamePlaying().mouseClicked(e);

            case OPTIONS -> this.game.getGameOptions().mouseClicked(e);

            case QUITS -> System.exit(0);

            default -> throw new IllegalArgumentException("Unexpected value: " + GameState.getState());

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (GameState.getState()) {
            case MENU -> this.game.getGameMenu().mousePressed(e);

            case PLAYING -> this.game.getGamePlaying().mousePressed(e);

            case OPTIONS -> this.game.getGameOptions().mousePressed(e);

            case QUITS -> System.exit(0);

            default -> throw new IllegalArgumentException("Unexpected value: " + GameState.getState());

        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameState.getState()) {
            case MENU -> this.game.getGameMenu().mouseReleased(e);

            case PLAYING -> this.game.getGamePlaying().mouseReleased(e);

            case OPTIONS -> this.game.getGameOptions().mouseReleased(e);

            case QUITS -> System.exit(0);

            default -> throw new IllegalArgumentException("Unexpected value: " + GameState.getState());

        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
