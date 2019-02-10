import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Afif Al Mamun
 * @date 2/5/2019
 * @project_name LabWorks
 */

public class LexicalAnalyzer {
    static String[] keywords = {
            "#include",
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

    static char[] parentheses = {'[', '{', '(', '<', '>', ')', '}', ']'};
    static char[] operators = {'+', '-', '*', '/', '%'};
//    static String[] dataTypes = {"int", "double", "float", "char"};

    static int[] operatorFlag = new int[operators.length];
    static int[] parenthFlag = new int[parentheses.length];
    static int[] keywordFlag;
    static int keyWordNumber = 0;

    static String[] stringSplitter(String string) {
        String[] res = new String[50];
        int len = string.length();
        int ctr = 0;
        int i = 0;

        while (i < len) {
            String token = "";

            while (i < len) {
                if (string.charAt(i) == ' ') {
                    i++;
                    break;
                }

                else if (string.charAt(i) == ',' ||
                        string.charAt(i) == ';' ||
                        string.charAt(i) == '=') {
                    // If it satisfies then we save the current token
                    // and save the punctuation mark as a seperate token
                    res[ctr++] = token;
                    token = "";
                }

                token += string.charAt(i++);
            }

            if (!token.equals("")) { // If token is not empty
                res[ctr++] = token;
            }
        }



        return res;
    }

    static int keyWordIdentifier(String line) {

        for (int i = 0; i < keyWordNumber; i++) {
            if (line.equals(keywords[i]))
                return i;
        }

        return -1;
    }

    static boolean isDataType(String line) {
        if (line.equals("int") ||
                line.equals("char") ||
                line.equals("double") ||
                line.equals("float"))
            return true;
        else return false;
    }

    static void parenthAndOperatorIdentifier(String line) {
        for (char c: line.toCharArray()) {
            for (int i = 0; i < parentheses.length; i++) {
                if (c == parentheses[i] && parenthFlag[i] ==0) {
                    parenthFlag[i] = 1;
                }
            }

            for (int i = 0; i < operators.length; i++) {
                if (c == operators[i] && operatorFlag[i] == 0)
                    operatorFlag[i] = 1;
            }
        }
    }

    static boolean constantIdentifier(String line) {
        char[] arr = line.toCharArray();

        for (char c: arr) {
            if (!(c >= '0' && c <= '9'))
                return false;
        }

        return true;
    }

    public static void main(String[] args) {
        int posIdentifier = 0;
        int posConstants = 0;
        int posVariables = 0;

        String[] keywordsFound = new String[50];
        String[] constants = new String[300];
        String[] variables = new String[300];

        keyWordNumber = keywords.length;

        keywordFlag = new int[keyWordNumber];

        File file = new File("source.c");
        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String[] line = stringSplitter(scanner.nextLine());

                for (String x: line) {
                    if (x == null)
                        break;

                    int f = keyWordIdentifier(x);

                    if (f != -1) {
                        if (keywordFlag[f] == 0) {
                            keywordsFound[posIdentifier++] = x;
                            keywordFlag[f] = 1;
                        }
                    }

                    else if (constantIdentifier(x))
                        constants[posConstants++] = x;

                    parenthAndOperatorIdentifier(x);
                }

                int len = line.length;


                for (int i = 0; i < len; i++) {
                    if (line[i] != null && isDataType(line[i])) {
                        i++;
                        variables[posVariables++] = line[i];
                        while (line[i] != null && !line[i].equals(";")) {
                            if (line[i].equals("="))
                                variables[posVariables++] = line[i - 1];
                            else if(line[i].equals(",") && !constantIdentifier(line[i - 1]))
                                variables[posVariables++] = line[i + 1];
                            i++;
                        }
                    }
                }
            }


            // Print Results
            int i = 0;

            System.out.print("Keywords: ");
            while (keywordsFound[i] != null) {
                System.out.print(keywordsFound[i++] + " ");
            }

            System.out.print("\nConstants: ");

            i = 0;

            while (constants[i] != null) {
                if (!constants[i].equals("")) {
                    System.out.print(constants[i] + " ");
                }
                i++;
            }

            System.out.print("\nParentheses: ");

            for (i = 0; i < parenthFlag.length; i++)
                if (parenthFlag[i] == 1)
                    System.out.print(parentheses[i] + " ");

            System.out.print("\nOperators: ");

            for (i = 0; i < operatorFlag.length; i++)
                if (operatorFlag[i] == 1)
                    System.out.print(operators[i] + " ");

            System.out.print("\nIdentifiers: ");

            for (i = 0; i < variables.length && variables[i] != null; i++)
                if (!variables[i].equals(""))
                    System.out.print(variables[i] + " ");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
