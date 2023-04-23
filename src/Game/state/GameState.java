package Game.state;

public enum GameState {
    PLAYING,
    MENU;

    private static GameState state = MENU;

    public static GameState getState() {
        return state;
    }

    public static void setState(GameState inState) {
        GameState.state = inState;
    }
}
