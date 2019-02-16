import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.TreeMap;

public class ExpressionEvaluation {
    /**
     * This field will contain operators '+', '-', '*', '/', '%'.
     */
    static Stack<Character> operator = new Stack<>();
    /**
     * expressionToken field is going to contain the whole expression
     * as different tokens of string. For example: for a-b*c
     * the list will contain "a", "-", "b", "*", "c".
     * Upon inputting values for different variables they are going to be
     * replaced by the actual values. Assume a = 3, b = 4, c = 4;
     * Then the list will be: "3", "-", "4", "*", "4".
     */
    static ArrayList<String> expressionToken = new ArrayList<>();
    /**
     * This field will contain operands or the values of variables.
     */
    static Stack<String> operand = new Stack<>();
    /**
     * This map will be used to map values of variables and replace the variables
     * in expressionToken list with their corresponding values.
     */
    static TreeMap<String, Double> values= new TreeMap<>();

    /**
     * Checks if a character is operator or not.
     * @param c: A character.
     * @return: An integer containing symbolizing the precedence of an operator
     * and -1 if not a character.
     */
    static int isOperator(char c) {
        if (c == '+')
            return 1;
        else if (c == '-')
            return 2;
        else if (c == '*')
            return 3;
        else if (c == '/')
            return 4;
        else if (c == '%')
            return 5;
        else
            return -1;
    }

    /**
     * Performs mathematical operation.
     * @param x: Operand 1
     * @param y: Operand 2
     * @param operator: Operator
     * @return: The result of the operation
     */

    static double calculate(double x, double y, char operator) {
        if (operator == '+')
            return x + y;
        else if (operator == '-')
            return x - y;
        else if (operator == '*')
            return x * y;
        else if (operator == '/')
            return x / y;
        else if (operator == '%')
            return x % y;

        return 0.0; // Should never reach
    }

    public static void main(String[] args) {
        System.out.print("Input expression: ");

        Scanner scanner = new Scanner(System.in);
        String expression = scanner.nextLine();

        char[] expressionArr = expression.toCharArray();
        String oper = "";

        // Parsing expression into tokens
        for (int i = 0; i < expressionArr.length; i++) {
            if (isOperator(expressionArr[i]) != -1) {
                expressionToken.add(oper);
                values.put(oper, 0.0); // Inputting in map
                oper = "";
                expressionToken.add(String.valueOf(expressionArr[i]));
            }
            else
                oper += expressionArr[i];
        }

        expressionToken.add(oper);
        values.put(oper, 0.0);


        // Prompting for values of variables
        for (String key: values.keySet()) {
            System.out.print("Enter value for " + key + ": ");
            values.put(key, scanner.nextDouble());
        }

        // Replacing operand tokens with their inputted values
        for (int i = 0; i < expressionToken.size(); i++) {
            String tok = expressionToken.get(i);
            if (isOperator(tok.charAt(0)) == -1) {
                expressionToken.set(i, String.valueOf(values.get(tok)));
            }
        }

        // Infix evaluation for every token in the expressionToken list
        for (String k: expressionToken) {
            if (isOperator(k.charAt(0)) == -1) {
                operand.push(k);

            } else {
                if (operator.isEmpty()) {
                    operator.push(k.charAt(0));
                } else {
                    int precedenceOfStackTop = isOperator(operator.peek());
                    int precedenceOfOperator = isOperator(k.charAt(0));

                    if (precedenceOfStackTop < precedenceOfOperator) {
                        operator.push(k.charAt(0));
                    } else {
                        while ((!operator.isEmpty()) && (precedenceOfStackTop > precedenceOfOperator)) {
                            double val1 = Double.valueOf(operand.pop());
                            double val2 = Double.valueOf(operand.pop());

                            double res = calculate(val2, val1, operator.pop());
                            operand.push(String.valueOf(res));

                            if (!operator.isEmpty())
                                precedenceOfStackTop = isOperator(operator.peek());
                        }

                        operator.push(k.charAt(0));
                    }
                }
            }
        }

        while (!operator.empty()) {
            double val1 = Double.valueOf(operand.pop());
            double val2 = Double.valueOf(operand.pop());

            double res = calculate(val2, val1, operator.pop());
            operand.push(String.valueOf(res));
        }

        // Displaying result
        System.out.println("Result: " + operand.peek());


    }
}
