package Game.GUI;

import java.awt.event.MouseEvent;

import Game.GUI.ui.buttons.GameMenuButton;
import Game.audio.GameAudio;
import Game.state.GameState;
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

    public void setGameState(GameState state) {
        switch (state) {
            case MENU -> game.getGameAudioPlayer().playSong(GameAudio.MENU_1);
            case OPTIONS -> {
            }
            case PLAYING -> game.getGameAudioPlayer().setLevelSong(0);

            case QUITS -> {
            }
            default -> {
                // None
            }
        }
        GameState.setState(state);
    }

}
