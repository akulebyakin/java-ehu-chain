package com.kulebiakin.infohandler.interpreter.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArithmeticInterpreterTest {

    private ArithmeticInterpreter interpreter;

    @BeforeEach
    void setUp() {
        interpreter = new ArithmeticInterpreter();
    }

    @Test
    void returnsTrueForValidArithmeticExpression() {
        Assertions.assertTrue(interpreter.isArithmeticExpression("2+2"));
        Assertions.assertTrue(interpreter.isArithmeticExpression("(3*4)-5"));
        Assertions.assertTrue(interpreter.isArithmeticExpression(" 7 / 2 "));
    }

    @Test
    void returnsFalseForNonArithmeticExpression() {
        Assertions.assertFalse(interpreter.isArithmeticExpression("hello world"));
        Assertions.assertFalse(interpreter.isArithmeticExpression("abc+def"));
        Assertions.assertFalse(interpreter.isArithmeticExpression(""));
    }

    @Test
    void interpretsSimpleAdditionCorrectly() {
        Assertions.assertEquals("4.0", interpreter.interpret("2+2"));
    }

    @Test
    void interpretsExpressionWithSpacesCorrectly() {
        Assertions.assertEquals("7.0", interpreter.interpret(" 3 + 4 "));
    }

    @Test
    void interpretsExpressionWithParenthesesCorrectly() {
        Assertions.assertEquals("14.0", interpreter.interpret("2*(3+4)"));
    }

    @Test
    void interpretsExpressionWithAllOperatorsCorrectly() {
        Assertions.assertEquals("3.0", interpreter.interpret("10-2*3.5+0.0"));
    }

    @Test
    void returnsOriginalExpressionOnInvalidInput() {
        Assertions.assertEquals("abc", interpreter.interpret("abc"));
        Assertions.assertEquals("", interpreter.interpret(""));
    }

    @Test
    void interpretsDivisionByZeroAsInfinity() {
        Assertions.assertEquals("Infinity", interpreter.interpret("1/0"));
    }

    @Test
    void interpretsNegativeNumbersCorrectly() {
        Assertions.assertEquals("-1.0", interpreter.interpret("2-3"));
    }

    @Test
    void interpretsComplexNestedParentheses() {
        Assertions.assertEquals("17.0", interpreter.interpret("((2+3)*(4-1))+2"));
    }
}
