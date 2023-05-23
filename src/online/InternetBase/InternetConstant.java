package online.InternetBase;

/**
 * This is a Java enumeration (enum) named `InternetConstant` that defines a set
 * of constants with
 * string values. Each constant represents a possible outcome of an internet
 * operation and has a
 * corresponding string value. The `toString()` method is overridden to return
 * the string value of the
 * constant.
 */
public enum InternetConstant {
    SUCCESS("SUCCESS"),
    FAIL("FAIL"),
    NONE("NONE"),
    ERROR("ERROR"),
    OK("OK");

    private final String command;

    InternetConstant(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return this.command;
    }
}
