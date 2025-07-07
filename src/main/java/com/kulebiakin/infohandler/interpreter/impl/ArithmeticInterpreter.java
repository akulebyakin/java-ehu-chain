package com.kulebiakin.infohandler.interpreter.impl;

import com.kulebiakin.infohandler.interpreter.ExpressionInterpreter;

import java.util.Stack;

public class ArithmeticInterpreter implements ExpressionInterpreter {

    private static final String EXPRESSION_REGEX = ".*[\\d+\\-*/()]+.*";

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

        private String toPostfix(String expr) {
            StringBuilder result = new StringBuilder();
            Stack<Character> stack = new Stack<>();

            for (char ch : expr.replaceAll("\\s+", "").toCharArray()) {
                if (Character.isDigit(ch)) {
                    result.append(ch);
                } else if (ch == '(') {
                    stack.push(ch);
                } else if (ch == ')') {
                    while (!stack.isEmpty() && stack.peek() != '(') {
                        result.append(' ').append(stack.pop());
                    }
                    stack.pop();
                } else if (isOperator(ch)) {
                    result.append(' ');
                    while (!stack.isEmpty() && precedence(ch) <= precedence(stack.peek())) {
                        result.append(stack.pop()).append(' ');
                    }
                    stack.push(ch);
                }
            }

            while (!stack.isEmpty()) {
                result.append(' ').append(stack.pop());
            }

            return result.toString();
        }

        private double evalPostfix(String postfix) {
            Stack<Double> stack = new Stack<>();
            for (String token : postfix.trim().split("\\s+")) {
                if (token.matches("-?\\d+(\\.\\d+)?")) {
                    stack.push(Double.parseDouble(token));
                } else if (token.length() == 1 && isOperator(token.charAt(0))) {
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
            return (ch == '+' || ch == '-') ? 1 : 2;
        }
    }
}
