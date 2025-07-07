package com.kulebiakin.infohandler.parser.impl;

import com.kulebiakin.infohandler.component.TextComponent;
import com.kulebiakin.infohandler.component.TextComponentType;
import com.kulebiakin.infohandler.component.TextComposite;
import com.kulebiakin.infohandler.parser.TextParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LexemeParserTest {

    private LexemeParser lexemeParser;
    private StubTextParser nextParser;

    static class StubTextParser implements TextParser {
        private String lastParsed;

        @Override
        public void setNext(TextParser nextParser) {
        }

        @Override
        public TextComponent parse(String data) {
            lastParsed = data;
            return new TextComposite(TextComponentType.LEXEME);
        }

        public String getLastParsed() {
            return lastParsed;
        }
    }

    @BeforeEach
    void setUp() {
        lexemeParser = new LexemeParser();
        nextParser = new StubTextParser();
        lexemeParser.setNext(nextParser);
    }

    @Test
    void parsesSingleLexemeCorrectly() {
        TextComponent result = lexemeParser.parse("word");
        Assertions.assertEquals(1, result.getChildren().size());
        Assertions.assertEquals("word", nextParser.getLastParsed());
    }

    @Test
    void parsesMultipleLexemesSeparatedBySpaces() {
        lexemeParser.parse("hello world");
        Assertions.assertEquals("world", nextParser.getLastParsed());
    }

    @Test
    void trimsInputAndIgnoresLeadingAndTrailingSpaces() {
        lexemeParser.parse("   word   ");
        Assertions.assertEquals("word", nextParser.getLastParsed());
    }

    @Test
    void returnsEmptySentenceCompositeForEmptyInput() {
        TextComponent result = lexemeParser.parse("");
        Assertions.assertEquals(0, result.getChildren().size());
    }

    @Test
    void handlesMultipleSpacesBetweenLexemes() {
        lexemeParser.parse("foo    bar");
        Assertions.assertEquals("bar", nextParser.getLastParsed());
    }
}