import java.util.Scanner;

public class Identifier {
    static boolean isIdentifier(char x) {
        if (x >= 'a' && x <= 'z'
                || x >= 'A' && x <= 'Z'
                || x >= '0' && x <= '9'
                || x == '_')
            return true;

        else return false;
    }

    public static void main(String[] args) {
        String x = "a2";
        char[] arr = x.toCharArray();

        int i = 0;
        int len = arr.length;
        boolean flag = false;

        if (isIdentifier(arr[i]) && !(arr[i] >= '0' && arr[i] <= '9')) {
            flag = true;
            for (char c: arr) {
                if(!isIdentifier(c))
                    flag = false;
            }
        }

        if (flag)
            System.out.println("Identifier");
        else
            System.out.println("Not ID");
    }
}
