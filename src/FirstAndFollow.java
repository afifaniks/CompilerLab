import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author: Afif Al Mamun
 * @created_in: 3/23/19
 * @project_name: LabWorks
 **/

public class FirstAndFollow {
    static HashMap<Character, String[]> productions = new HashMap<>();
    static HashMap<Character, String> first = new HashMap<>();
    static Character currentStartSymbol;

    /**
     * Splits production input by '|'
     * @param in: input line
     */
    public static void splitProductions(String in) {

        char start = in.charAt(0);
        String[] products = new String[10];
        int index = 0;

        int len = in.length();
        String end = "";

        for (int i = 2; i < len; i++) {
            if (in.charAt(i) == '|') {
                products[index++] = end;
                end = "";
            } else {
                end += in.charAt(i);
            }
        }

        products[index++] = end;
        productions.put(start, products);
    }

    /**
     * Checks if a symbol is terminal or not
     * @param c: symbol
     * @return true: if terminal
     *          false: if non-terminal
     */
    public static boolean isTerminal(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= '0' && c <= '9') ||
                c == '+' ||
                c == '-' ||
                c == '*' ||
                c == '(' ||
                c == ')' ||
                c == 'ε';
    }

    /**
     * Finds first symbol of a productions and add
     * them to a map
     * @param startSymbol: The start symbol of the production
     */
    public static void findFirst(char startSymbol) {
        String[] prods = productions.get(startSymbol);

        for (String p: prods) {
            if (p == null)
                break;
            if (isTerminal(p.charAt(0))) {
                // If start symbol is existed
                if (first.containsKey(currentStartSymbol)) {
                    String previousFirst = first.get(currentStartSymbol);
                    previousFirst += p.charAt(0);
                    previousFirst += ' ';
                    first.put(currentStartSymbol, previousFirst);
                } else {
                    String firstS = "";
                    firstS += p.charAt(0);
                    firstS += ' ';
                    first.put(currentStartSymbol, firstS);
                }
            } else {
                findFirst(p.charAt(0));
            }

        }
    }

    public static void main(String[] args) {
        Scanner sc = null;

        try {
            sc = new Scanner(new File("cfg.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (sc.hasNextLine()) {
            splitProductions(sc.nextLine());
        }

        for (char start: productions.keySet()) {
            currentStartSymbol = start;
            findFirst(currentStartSymbol);
        }

        // Printing result
        for (char start: first.keySet()) {
            String firstSym = first.get(start);
            HashSet<Character> firstSymbolSet = new HashSet<>();

            // Unique symbols
            for (char c: firstSym.toCharArray()) {
                if (c != ' ')
                    firstSymbolSet.add(c);

            }

            System.out.print(start + " -> ");
            for (char c: firstSymbolSet) {
                System.out.print(c + " ");
            }
            System.out.println("");
        }

    }
}
