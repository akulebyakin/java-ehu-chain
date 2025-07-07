package com.kulebiakin.infohandler.interpreter;

public interface ExpressionInterpreter {

    boolean isArithmeticExpression(String input);

    String interpret(String expression);
}
