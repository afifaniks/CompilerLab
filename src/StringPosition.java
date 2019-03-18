/**
 * @author: Afif Al Mamun
 * @created_in: 3/18/19
 * @project_name: LabWorks
 **/
import java.util.Scanner;


public class StringPosition {
    // Checks if a character is numeric value or not
    private static boolean isNumeric(char x) {
        return x >= '0' && x <= '9' ? true : false;
    }

    /**
     * Compares String str with base String
     * and choose its position according to the rule...
     *
     * A string is considered to be small if it has
     * a smaller length, big otherwise.
     *
     * String str is considered to be small if both strings
     * have same length and current character of String has
     * lower ASCII value than the base String, big otherwise.
     *
     * String str is considered to be small if both strings
     * have same length and there is an integer value in the
     * string which is smaller than of the base String's
     * corresponding integer value. Example: "XY23ABV" < "XY50ABV"(base)
     *
     * If the base String has no numeric value in the same position
     * where there is a numeric value in String str, then it is
     * considered to be small.
     * @param base: base String with which the other one is compared
     * @param str: The string that is going to be compared with base
     * @return: 0 - If str is smaller than base
     *          1 - If str is bigger than base
     */
    private static int compare(String base, String str) {
        if (base.length() < str.length())
            return 1;
        else if (base.length() > str.length())
            return 0;

        int j = 0; // str index

        for (int i = 0; i < base.length(); i++) {
            if (isNumeric(base.charAt(i)) && !isNumeric(str.charAt(j))) {
                return 0;
            }

            else if (!isNumeric(base.charAt(i)) && isNumeric(str.charAt(j))) {
                return 1;
            }

            else if(isNumeric(base.charAt(i)) && isNumeric(str.charAt(j))) {
                String baseNum = "";
                String strNum = "";

                while (i < base.length() && isNumeric(base.charAt(i))) {
                    baseNum += base.charAt(i);
                    i++;
                }

                while (j < str.length() && isNumeric(str.charAt(j))) {
                    strNum += str.charAt(j);
                    j++;
                }

                j--;

                if (Integer.parseInt(baseNum) < Integer.parseInt(strNum))
                    return 1;
                else if (Integer.parseInt(baseNum) > Integer.parseInt(strNum))
                    return 0;
            } else {
                if(base.charAt(i) < str.charAt(j))
                    return 1;
                else if(base.charAt(i) > str.charAt(j))
                    return 0;
            }

            j++;
        }

        return 1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        String[] in = new String[n + 1];

        int index = 0;
        while (index < in.length) {
            String x = sc.next();
            in[index++] = x;
        }

        for(int i = 1; i < in.length; i++) {
            int res = compare(in[0], in[i]);

            if (res == 0)
                System.out.println("-");
            else
                System.out.println("+");
        }
    }
}
