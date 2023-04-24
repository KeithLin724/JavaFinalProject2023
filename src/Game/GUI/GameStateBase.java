package Game.GUI;

import java.awt.event.MouseEvent;

import Game.GUI.ui.GameMenuButton;
import main.Game;

// this class is saving the game state parameter
public abstract class GameStateBase {
    protected Game game;

    public GameStateBase(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return this.game;
    }

    public boolean isIn(MouseEvent mouseEvent, GameMenuButton btn) {
        return btn.getBounds().contains(mouseEvent.getX(), mouseEvent.getY());
    }

}
