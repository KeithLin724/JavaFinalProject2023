package logic.input;

public enum MoveCommand {
    UP("UP"),
    DOWN("DOWN"),
    LEFT("LEFT"),
    RIGHT("RIGHT"),

    NONE("NONE");

    final String cmd;

    MoveCommand(String cmdString) {
        this.cmd = cmdString;
    }
}
