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

    public int index() {
        switch (this) {
            case UP -> {
                return 0;
            }
            case DOWN -> {
                return 1;
            }
            case LEFT -> {
                return 2;
            }
            case RIGHT -> {
                return 3;
            }
            case NONE -> {
                return -1;
            }

            default -> {
                return -1;
            }

        }
    }
}
