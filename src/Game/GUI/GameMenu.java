package Game.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import Game.PLUG.GameStateMethod;
import Game.state.GameState;
import main.Game;

import static base.BaseGameConstant.GAME_WIDTH;
import static base.BaseGameConstant.GAME_HEIGHT;;

public class GameMenu extends GameStateBase implements GameStateMethod {

    public GameMenu(Game game) {
        super(game);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawString("Menu", GAME_WIDTH / 2, GAME_HEIGHT / 2);
    }

    @Override
    public void update() {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

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

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            GameState.setState(GameState.PLAYING);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
