package Game.GUI;

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

}
