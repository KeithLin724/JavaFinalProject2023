package Game.GUI.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Game.GUI.GamePlaying;
import Game.PLUG.gameDrawer.GameAnimatedDrawer;
import Game.state.GameState;

import static base.BaseGameConstant.GAME_WIDTH;
import static base.BaseGameConstant.GAME_HEIGHT;

public class GameOverDisplayLayer implements GameAnimatedDrawer, KeyListener {
    private GamePlaying gamePlaying;

    public GameOverDisplayLayer(GamePlaying gamePlaying) {
        this.gamePlaying = gamePlaying;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        g.setColor(Color.white);
        g.drawString("Game Over", GAME_WIDTH / 2, 150);
        g.drawString("Press esc to enter Main Menu", GAME_WIDTH / 2, 300);

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            gamePlaying.resetAll();
            GameState.setState(GameState.MENU);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
