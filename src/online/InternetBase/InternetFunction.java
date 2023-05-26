package online.InternetBase;

import java.util.ArrayList;
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

    /**
     * The function takes a list of strings, capitalizes the first element, and
     * joins the elements with a
     * forward slash.
     * 
     * @param cmdList cmdList is a List of Strings that contains the command and its
     *                arguments that need to
     *                be joined together. The first element of the list is the
     *                command itself, and the rest of the
     *                elements are its arguments.
     * @return The method is returning a string that is the concatenation of all the
     *         elements in the input
     *         list `cmdList`, separated by a forward slash ("/"). The first element
     *         of the list is converted to
     *         uppercase before joining the elements.
     */
    public static String commandJoin(List<String> cmdList) {
        List<String> copyCmdList = new ArrayList<>(cmdList);
        copyCmdList.set(0, copyCmdList.get(0).toUpperCase());
        return String.join("/", cmdList);
    }
}
