package logic.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> {
                game.getTranslator().getPlayer().setUp(true);
            }
            case KeyEvent.VK_A -> {
                game.getTranslator().getPlayer().setLeft(true);
            }
            case KeyEvent.VK_S -> {
                game.getTranslator().getPlayer().setDown(true);
            }
            case KeyEvent.VK_D -> {
                game.getTranslator().getPlayer().setRight(true);
            }
        }

        // throw new UnsupportedOperationException("Unimplemented method 'keyPressed'");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> {
                game.getTranslator().getPlayer().setUp(false);
            }
            case KeyEvent.VK_A -> {
                game.getTranslator().getPlayer().setLeft(false);
            }
            case KeyEvent.VK_S -> {
                game.getTranslator().getPlayer().setDown(false);
            }
            case KeyEvent.VK_D -> {
                game.getTranslator().getPlayer().setRight(false);
            }
        }
        // throw new UnsupportedOperationException("Unimplemented method
        // 'keyReleased'");
    }

}
