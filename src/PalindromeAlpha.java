
public class PalindromeAlpha {
    static boolean isChar(char x) {
        if ((x >= 'a' && x <= 'z') || (x >= 'A' && x <= 'Z'))
            return true;
        else return false;
    }

 public static void main (String[] args) {
     String string = "e#!$#!$xfxt324itxfxe";
     char[] str = string.toCharArray();

     int len = str.length;
     int back = len - 1;
     boolean flag = true;

     for (int i = 0; i < len && flag; i++) {
         if (isChar(str[i])) {
             while (!isChar(str[back])) {
                 back--;
             }

             if (str[i] != str[back])
                 flag = false;
             back--;
         }
     }

     if (flag)
         System.out.println("Palindrome");
     else
         System.out.println("Not Palindrome");
 }
}
