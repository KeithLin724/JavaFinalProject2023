package Game.GUI;

import java.awt.event.MouseEvent;

import Game.GUI.ui.buttons.GameMenuButton;
import Game.audio.GameAudio;
import Game.state.GameState;
import main.Game;

// this class is saving the game state parameter
/**
 * The abstract class GameStateBase contains methods for getting the game
 * object, checking if a mouse
 * event occurred within the bounds of a game menu button, and setting the game
 * state and playing
 * different audio tracks based on the state.
 */
public abstract class GameStateBase {
    protected Game game;

    public GameStateBase(Game game) {
        this.game = game;
    }

    /**
     * The function returns the game object.
     * 
     * @return The method is returning an object of type `Game`. The specific `Game`
     *         object being returned
     *         is the one referenced by the `this.game` instance variable.
     */
    public Game getGame() {
        return this.game;
    }

    /**
     * This function checks if a mouse event occurred within the bounds of a game
     * menu button.
     * 
     * @param mouseEvent A MouseEvent is an event that occurs when the user
     *                   interacts with the mouse, such
     *                   as clicking or moving it. It contains information about the
     *                   event, such as the location of the mouse
     *                   cursor and the type of event that occurred.
     * @param btn        btn is an object of the class GameMenuButton. It is being
     *                   used to get the bounds of the
     *                   button using the getBounds() method. The bounds of the
     *                   button are then used to check if the mouse
     *                   event occurred within the button's area.
     * @return The method `isIn` returns a boolean value, which indicates whether
     *         the given `MouseEvent` is
     *         within the bounds of the specified `GameMenuButton` or not.
     */
    public boolean isIn(MouseEvent mouseEvent, GameMenuButton btn) {
        return btn.getBounds().contains(mouseEvent.getX(), mouseEvent.getY());
    }

    /**
     * This function sets the game state and plays different audio tracks based on
     * the state.
     * 
     * @param state The parameter "state" is of type GameState, which is an enum
     *              representing the different
     *              states of the game (such as MENU, OPTIONS, PLAYING, and QUITS).
     *              The method setGameState() takes in a
     *              GameState parameter and performs different actions based on the
     *              value of the parameter.
     */
    public void setGameState(GameState state) {
        switch (state) {
            case MENU -> game.getGameAudioPlayer().playSong(GameAudio.MENU_1);

            case OPTIONS -> {
            }
            case PLAYING -> game.getGameAudioPlayer().setLevelSong(0);

            case CREDITS -> game.getGameAudioPlayer().playSong(GameAudio.CREDITS);

            case QUITS -> {

            }
            default -> {
                // None
            }
        }
        GameState.setState(state);
    }

}
