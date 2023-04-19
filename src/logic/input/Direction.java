package logic.input;

public enum Direction {
    UP("UP"),
    DOWN("DOWN"),
    LEFT("LEFT"),
    RIGHT("RIGHT"),

    NONE("NONE");

    final String cmd;

    Direction(String cmdString) {
        this.cmd = cmdString;
    }

    public boolean isMoving() {
        return !this.equals(NONE);
    }
}
