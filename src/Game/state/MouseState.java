package Game.state;

public enum MouseState {
    OVER(1),
    PRESS(2),
    NONE(0);

    public final int toDisplayIndex;

    MouseState(int toDisplayIndex) {
        this.toDisplayIndex = toDisplayIndex;
    }

}
