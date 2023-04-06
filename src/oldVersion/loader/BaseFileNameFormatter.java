package oldVersion.loader;

import java.util.EnumMap;

// How to use 
/*
 * String test = "test";
 * System.out.println(BaseFileNameFormatter.of(test, FileNameType.IMAGE));
 * >> test.png
*/

/*
 * add type to FileNameType enum class 
 * and add of method in here to return a file name 
 */

public class BaseFileNameFormatter {
    public static EnumMap<FileNameType, String> enumMap = new EnumMap<>(FileNameType.class) {
        {
            put(FileNameType.IMAGE, ".png");
            put(FileNameType.TEXT, ".txt");
        }
    };

    /**
     * It takes a file name and a file type and returns a string that is the file
     * name with the file type
     * extension appended to it
     * 
     * @param fileName The name of the file
     * @param type     FileNameType
     * @return The file name and the file extension.
     */
    public static String of(String fileName, FileNameType type) {
        return String.format("%s%s", fileName, enumMap.get(type));
    }

}
