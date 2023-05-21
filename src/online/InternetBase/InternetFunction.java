package online.InternetBase;

import java.util.List;

final public class InternetFunction {

    /**
     * It takes a string, splits it into an array of strings, and then capitalizes
     * the first element of
     * the array
     * 
     * @param cmdIn The command that the user has entered.
     * @return A string array.
     */
    public static String[] commandSplit(String cmdIn) {
        String[] res = cmdIn.split("/");
        if (res.length > 1) {
            res[0] = res[0].toUpperCase();
        }
        return res;
    }

    /**
     * It takes an array of strings, capitalizes the first string, and joins the
     * array into a string
     * with "/" as the delimiter
     * 
     * @param cmdArr The array of strings that you want to join.
     * @return The first element of the array is being converted to uppercase, and
     *         then the array is
     *         being joined with "/" as the delimiter.
     */
    public static String commandJoin(String[] cmdArr) {
        cmdArr[0] = cmdArr[0].toUpperCase();
        return String.join("/", cmdArr);
    }

    public static String commandJoin(List<String> cmdList) {
        cmdList.set(0, cmdList.get(0).toUpperCase());
        return String.join("/", cmdList);
    }
}
