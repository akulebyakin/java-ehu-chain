package com.kulebiakin.infohandler.parser.impl;

import com.kulebiakin.infohandler.component.TextComponent;
import com.kulebiakin.infohandler.component.TextComponentType;
import com.kulebiakin.infohandler.component.TextComposite;
import com.kulebiakin.infohandler.component.TextSymbol;
import com.kulebiakin.infohandler.interpreter.ExpressionInterpreter;
import com.kulebiakin.infohandler.interpreter.impl.ArithmeticInterpreter;
import com.kulebiakin.infohandler.parser.TextParser;

public class WordParser implements TextParser {

    private final ExpressionInterpreter interpreter = new ArithmeticInterpreter();

    @Override
    public void setNext(TextParser nextParser) {
    }

    @Override
    public TextComponent parse(String data) {
        String interpreted = interpreter.isArithmeticExpression(data)
            ? interpreter.interpret(data)
            : data;

        TextComposite lexemeComposite = new TextComposite(TextComponentType.LEXEME);

        for (char c : interpreted.toCharArray()) {
            lexemeComposite.add(new TextSymbol(c));
        }

        return lexemeComposite;
    }
}
