package Inputs;

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
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                game.getTranslator().getPlayer().setUp(true);
                break;
            case KeyEvent.VK_A:
                game.getTranslator().getPlayer().setLeft(true);
                break;
            case KeyEvent.VK_S:
                game.getTranslator().getPlayer().setDown(true);
                break;
            case KeyEvent.VK_D:
                game.getTranslator().getPlayer().setRight(true);
                break;
        }

        // throw new UnsupportedOperationException("Unimplemented method 'keyPressed'");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                game.getTranslator().getPlayer().setUp(false);
                break;
            case KeyEvent.VK_A:
                game.getTranslator().getPlayer().setLeft(false);
                break;
            case KeyEvent.VK_S:
                game.getTranslator().getPlayer().setDown(false);
                break;
            case KeyEvent.VK_D:
                game.getTranslator().getPlayer().setRight(false);
                break;
        }
        // throw new UnsupportedOperationException("Unimplemented method
        // 'keyReleased'");
    }

}
