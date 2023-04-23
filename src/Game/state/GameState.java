package Game.state;

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
    MENU;

    private static GameState state = MENU;

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
    }
}
