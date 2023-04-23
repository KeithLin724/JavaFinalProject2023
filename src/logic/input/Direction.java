package logic.input;

public enum Direction {
    UP("UP", 0),
    DOWN("DOWN", 1),
    LEFT("LEFT", 2),
    RIGHT("RIGHT", 3),

    NONE("NONE", -1);

    public final String cmd;

    public final int index;

    Direction(String cmdString, int index) {
        this.cmd = cmdString;
        this.index = index;
    }

    public boolean isMoving() {
        return !this.equals(NONE);
    }

}
