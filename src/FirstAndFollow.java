import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author: Afif Al Mamun
 * @created_in: 3/23/19
 * @project_name: LabWorks
 **/

public class FirstAndFollow {
    static private LinkedHashMap<Character, String[]> productions = new LinkedHashMap<>();
    static private LinkedHashMap<Character, String> first = new LinkedHashMap<>();
    static private LinkedHashMap<Character, String> follow = new LinkedHashMap<>();
    static private Character currentStartSymbol;
    static private Character currentNonTerminal;
    private static boolean startingNonTerminal = true;

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

    public static void findFollow(char nonTerminal) {

        if (startingNonTerminal) {
            startingNonTerminal = false;

            follow.put(currentNonTerminal, "$");
        }

        for (char c: productions.keySet()) {
            // Getting productions
            String[] prods = productions.get(c);

            for (String s: prods) {
                if (s == null)
                    break;

                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == nonTerminal && i < s.length() - 1) {
                        if (isTerminal(s.charAt(i + 1))) {
                            if (follow.containsKey(currentNonTerminal)) {
                                String follows = follow.get(currentNonTerminal);
                                follows += s.charAt(i + 1);
                                follows += ' ';
                                follow.put(currentNonTerminal, follows);
                            } else {
                                String follows = "";
                                follows += s.charAt(i + 1);
                                follows += ' ';
                                follow.put(currentNonTerminal, follows);
                            }
                        } else {
                            String firstOfNext = first.get(s.charAt(i + 1));
                            // TODO Add case for epsilon

                            if (follow.containsKey(currentNonTerminal)) {
                                String follows = follow.get(currentNonTerminal);
                                follows += firstOfNext;
                                follows += ' ';
                                follow.put(currentNonTerminal, follows);
                            } else {
                                String follows = "";
                                follows += firstOfNext;
                                follows += ' ';
                                follow.put(currentNonTerminal, follows);
                            }
                        }
                    }
                }
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

        // Finding follow
        for (char c: productions.keySet()) {
            currentNonTerminal = c;
            findFollow(c);
        }

        // Printing result
        System.out.println("First");

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

        System.out.println("\nFollows");

        for (char start: follow.keySet()) {
            String followSym = follow.get(start);
            HashSet<Character> followSymbolSet = new HashSet<>();

            // Unique symbols
            for (char c: followSym.toCharArray()) {
                if (c != ' ')
                    followSymbolSet.add(c);
            }

            System.out.print(start + " -> ");
            for (char c: followSymbolSet) {
                System.out.print(c + " ");
            }

            System.out.println("");
        }

    }
}
