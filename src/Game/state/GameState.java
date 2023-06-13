package Game.state;

import java.util.logging.Logger;

/**
 * This is a Java enum called `GameState` that represents the different states
 * of a game. It has two
 * possible values: `PLAYING` and `MENU`. It also has two static methods:
 * `getState()` and
 * `setState(GameState inState)`. `getState()` returns the current state of the
 * game, which is stored
 * in the static variable `state`. `setState(GameState inState)` sets the
 * current state of the game to
 * the value passed in as the `inState` parameter.
 */
public enum GameState {
    PLAYING,
    MENU,
    OPTIONS,
    CREDITS,
    QUITS;

    private static GameState state = MENU;
    private static final Logger LOGGER = Logger.getLogger(GameState.class.getName());

    public static final GameState[] MENU_GAME_STATES_CHOOSE = { PLAYING, OPTIONS, QUITS, CREDITS };

    /**
     * The function returns the current state of the game.
     * 
     * @return The method is returning an object of the class GameState. The
     *         specific object being returned
     *         is determined by the value of the variable "state".
     */
    public static GameState getState() {
        return state;
    }

    /**
     * This function sets the state of the game to the input state.
     * 
     * @param inState The parameter "inState" is of type GameState, which is an
     *                enumeration representing
     *                the different states of a game. This method sets the current
     *                state of the game to the value passed
     *                in as the "inState" parameter.
     */
    public static void setState(GameState inState) {
        GameState.state = inState;
        LOGGER.info("Game State IN: " + GameState.state);
    }
}
