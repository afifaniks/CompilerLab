import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeMap;

public class KeywordFinder {
    static String[] keywords = {
            "int",
            "float",
            "char",
            "double",
            "printf",
            "if",
            "else",
            "scanf",
            "for",
            "while",
            "struct",
            "switch",
            "case",
            "break",
            "continue",
            "return"
    };

    static TreeMap<String, Boolean> map = new TreeMap<>();

    static void addToMap (String x) {
        for (String k: keywords) {
            if (k.equals(x)) {
                map.put(k, true);
            }
        }
    }

    public static void main(String[] args) {
        File f = new File("source.c");
        Scanner scanner = null;

        try {
            scanner = new Scanner(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String str = "";

        while (scanner.hasNextLine()) {
            str = scanner.nextLine();

            String[] line = str.split(" ");
            int len = line.length;

            for (int i = 0; i < len; i++) {
                addToMap(line[i]);
            }
        }

        for (String s: map.keySet()) {
            System.out.println(s);
        }

    }
}
