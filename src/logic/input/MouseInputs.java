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

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // game.getTranslator().getPlayer();

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // if (e.getButton() == MouseEvent.BUTTON1) {
        // game.getTranslator().getPlayer().setAttacking(true);
        // }

        switch (GameState.getState()) {
            case MENU -> {
                this.game.getGameMenu().mouseClicked(e);
            }
            case PLAYING -> {
                this.game.getGamePlaying().mouseClicked(e);
            }

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
