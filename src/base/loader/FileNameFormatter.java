package base.loader;

/**
 * This is a Java enum called `FileNameFormatter` that defines two file types:
 * TEXT and IMAGE. Each
 * file type has a corresponding file extension (".txt" and ".png",
 * respectively) stored in the
 * fileType` field. The constructor initializes the `fileType` field for each
 * enum value.
 */
public enum FileNameFormatter {
    TEXT(".txt"),
    IMAGE(".png");

    private final String fileType;

    FileNameFormatter(String fileType) {
        this.fileType = fileType;
    }

    /**
     * The function returns the file type as a string.
     * 
     * @return The method `type()` is returning the value of the instance variable
     *         `fileType`.
     */
    public String type() {
        return this.fileType;
    }

    /**
     * The function takes a file name and a file name formatter type and returns a
     * formatted string with
     * the file type appended to the file name.
     * 
     * @param fileName A string representing the name of a file.
     * @param type     FileNameFormatter is an enum type that contains a fileType
     *                 field. It is used to format
     *                 the file name by appending the fileType to the end of the
     *                 fileName.
     * @return A string that concatenates the given fileName and the fileType of the
     *         given
     *         FileNameFormatter object.
     */
    public static String of(String fileName, FileNameFormatter type) {
        return String.format("%s%s", fileName, type.fileType);
    }
}
