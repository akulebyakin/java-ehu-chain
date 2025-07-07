package com.kulebiakin.infohandler.interpreter.impl;

import com.kulebiakin.infohandler.interpreter.ExpressionInterpreter;

import java.util.Stack;

public class ArithmeticInterpreter implements ExpressionInterpreter {

    private static final String EXPRESSION_REGEX = "^[\\d\\s()+\\-*/.]+$";

    @Override
    public boolean isArithmeticExpression(String input) {
        return input.matches(EXPRESSION_REGEX);
    }

    @Override
    public String interpret(String expression) {
        try {
            double result = evaluateExpression(expression);
            return String.valueOf(result);
        } catch (Exception e) {
            return expression;
        }
    }

    private double evaluateExpression(String expr) {
        return new RPNCalculator().evaluate(expr);
    }

    private static class RPNCalculator {

        double evaluate(String expr) {
            String postfix = toPostfix(expr);
            return evalPostfix(postfix);
        }

        private String toPostfix(String expression) {
            StringBuilder output = new StringBuilder();
            Stack<Character> stack = new Stack<>();
            StringBuilder numberBuffer = new StringBuilder();

            for (int i = 0; i < expression.length(); i++) {
                char ch = expression.charAt(i);

                if (Character.isDigit(ch) || ch == '.') {
                    numberBuffer.append(ch);
                } else {
                    if (!numberBuffer.isEmpty()) {
                        output.append(numberBuffer).append(" ");
                        numberBuffer.setLength(0);
                    }

                    if (ch == '(') {
                        stack.push(ch);
                    } else if (ch == ')') {
                        while (!stack.isEmpty() && stack.peek() != '(') {
                            output.append(stack.pop()).append(" ");
                        }
                        stack.pop();
                    } else if (isOperator(ch)) {
                        if (ch == '-' && (i == 0 || expression.charAt(i - 1) == '(')) {
                            output.append("0 "); // e.g., -3 -> 0 3 -
                        }

                        while (!stack.isEmpty() && precedence(ch) <= precedence(stack.peek())) {
                            output.append(stack.pop()).append(" ");
                        }
                        stack.push(ch);
                    }
                }
            }

            if (!numberBuffer.isEmpty()) {
                output.append(numberBuffer).append(" ");
            }

            while (!stack.isEmpty()) {
                output.append(stack.pop()).append(" ");
            }

            return output.toString().trim();
        }

        private double evalPostfix(String postfix) {
            Stack<Double> stack = new Stack<>();
            for (String token : postfix.split("\\s+")) {
                if (token.matches("-?\\d+(\\.\\d+)?")) {
                    stack.push(Double.parseDouble(token));
                } else if (isOperator(token.charAt(0)) && token.length() == 1) {
                    double b = stack.pop();
                    double a = stack.pop();
                    switch (token.charAt(0)) {
                        case '+':
                            stack.push(a + b);
                            break;
                        case '-':
                            stack.push(a - b);
                            break;
                        case '*':
                            stack.push(a * b);
                            break;
                        case '/':
                            stack.push(a / b);
                            break;
                    }
                }
            }
            return stack.pop();
        }

        private boolean isOperator(char ch) {
            return ch == '+' || ch == '-' || ch == '*' || ch == '/';
        }

        private int precedence(char ch) {
            switch (ch) {
                case '+':
                case '-':
                    return 1;
                case '*':
                case '/':
                    return 2;
            }
            return -1;
        }
    }
}
