package io.rjnsh.datastructures.stack.expression;

import java.util.Deque;
import java.util.LinkedList;

public class InfixEvaluation {
    public static void main(String... args) {
        String expression = "2*32+ 5*3^2";
        InfixEvaluation infixEvaluator = new InfixEvaluation();
        int result = infixEvaluator.expression(expression.toCharArray());
        System.out.println(result);
    }

    // rules
    // stack to process in reverse order of localised group of operands and operators
    // * stack top and curr op = +, process all higher precedence operators in the stack
    // process all equal
    // 6 - 3 + 3, curr op = +, stack top is -, equal precedence
    // 6 - 3 + 3 * 2 + 3
    private int expression(char[] expression) {
        Deque<Integer> operandStack = new LinkedList<>();
        Deque<Character> operatorStack = new LinkedList<>();
        final int LEN = expression.length;
        int idx = 0;
        while (idx < LEN) {
            char c = expression[idx];
            if (Character.isWhitespace(c)) {
                idx++;
                // skip
            } else if (Character.isDigit(expression[idx])) {
                int num = 0;
                while (idx < LEN && Character.isDigit(expression[idx])) {
                    num = num * 10 + (expression[idx] - '0');
                    idx++;
                }

                operandStack.push(num);
                
            } else if (isOperator(c)) {
                while (!operatorStack.isEmpty() && getPrecedence(operatorStack.peek()) >= getPrecedence(c)) {
                    int result = calculate(operandStack, operatorStack);
                    operandStack.push(result);
                }
                operatorStack.push(c);
                idx++;
            }
        }

        while (!operatorStack.isEmpty()) {
            int result = calculate(operandStack, operatorStack);
            operandStack.push(result);
        }

        return operandStack.peek();
    }

    private boolean isOperator(char c) {
        return c == '/' || c == '^' || c == '*' || c == '+' || c == '-';
    }

    private int getPrecedence(char operator) {
        switch (operator) {
            case '+':
                return 1;
            case '-':
                return 1;
            case '*':
                return 2;
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                return -1;
        }
    }

    private int calculate(Deque<Integer> operands, Deque<Character> operators) {
        int val1 = operands.pop();
        int val2 = operands.pop();
        char operator = operators.pop();
        switch (operator) {
            case '+':
                return val2 + val1;
            case '-':
                return val2 - val1;
            case '*':
                return val1 * val2;
            case '/':
                if (val1 == 0) {
                    throw new RuntimeException("division by zero..!");
                }
                return val2 / val1;
            case '^':
                return (int) Math.pow(val2, val1);
            default:
                return -1;
        }
    }
}