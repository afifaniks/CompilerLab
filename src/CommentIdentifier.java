import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CommentIdentifier {
    static int flag = 0; /* flag will count the number of characters
                            in a comment so that we don't need to re-iterate
                            over those characters and save time */
    static int counter = 0; // Total number of comments

    static String sameLine(char[] line, int pos) {
        flag = 0;
        String comment = "";
        while (line[pos] != '\n') {
            comment += line[pos++];
            flag++;
        }

        return comment;
    }

    static String multiLine(char[] line, int pos) {
        flag = 0;
        String comment = "";
        int len = line.length;

        while(pos < len) {
            if (line[pos] == '*' && line[pos + 1] == '/')
                break;
            comment += line[pos++];
            flag++;
        }

        return comment;
    }

    static void printer(String comment) {
        System.out.println("Comment " + (++counter) + ":\n" +
                comment +"\n");
    }

    public static void main(String[] args) {
        File file = new File("test.txt");

        try {
            Scanner sc = new Scanner(file);
            String source = "";

            while (sc.hasNextLine()) {
                source += sc.nextLine()+"\n";
            }

            int len = source.length();
            int i = 0;

            char[] x = source.toCharArray();

            while (i < len) {
                if (x[i] == '/' && x[i + 1] == '/') {
                    String comment = sameLine(x, i+=2);
                    i += flag;

                    printer(comment);
                }

                else if (x[i] == '/' && x[i + 1] == '*') {
                    String comment = multiLine(x, i+=2);
                    i += flag;

                    printer(comment);
                }
                else
                    i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

