package com.kulebiakin.infohandler.parser.impl;

import com.kulebiakin.infohandler.component.TextComponent;
import com.kulebiakin.infohandler.component.TextComponentType;
import com.kulebiakin.infohandler.component.TextComposite;
import com.kulebiakin.infohandler.parser.TextParser;

public class LexemeParser implements TextParser {

    private static final String LEXEME_DELIMITER = "\\s+";

    private TextParser next;

    @Override
    public void setNext(TextParser nextParser) {
        this.next = nextParser;
    }

    @Override
    public TextComponent parse(String data) {
        TextComposite sentenceComposite = new TextComposite(TextComponentType.SENTENCE);

        String[] lexemes = data.trim().split(LEXEME_DELIMITER);
        for (String lexeme : lexemes) {
            TextComponent lexemeComponent = next.parse(lexeme);
            sentenceComposite.add(lexemeComponent);
        }

        return sentenceComposite;
    }
}
