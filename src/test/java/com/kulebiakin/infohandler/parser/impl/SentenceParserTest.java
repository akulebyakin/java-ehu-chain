package com.kulebiakin.infohandler.parser.impl;

import com.kulebiakin.infohandler.component.TextComponent;
import com.kulebiakin.infohandler.component.TextComponentType;
import com.kulebiakin.infohandler.component.TextComposite;
import com.kulebiakin.infohandler.parser.TextParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SentenceParserTest {

    private SentenceParser sentenceParser;
    private StubTextParser nextParser;

    static class StubTextParser implements TextParser {
        private String lastParsed;

        @Override
        public void setNext(TextParser nextParser) {
        }

        @Override
        public TextComponent parse(String data) {
            lastParsed = data;
            return new TextComposite(TextComponentType.SENTENCE);
        }

        public String getLastParsed() {
            return lastParsed;
        }
    }

    @BeforeEach
    void setUp() {
        sentenceParser = new SentenceParser();
        nextParser = new StubTextParser();
        sentenceParser.setNext(nextParser);
    }

    @Test
    void parsesSingleSentenceCorrectly() {
        TextComponent result = sentenceParser.parse("Hello world!");
        Assertions.assertEquals(1, result.getChildren().size());
        Assertions.assertEquals("Hello world!", nextParser.getLastParsed());
    }

    @Test
    void parsesMultipleSentencesWithDifferentTerminators() {
        TextComponent result = sentenceParser.parse("Hi! How are you? Fine.");
        Assertions.assertEquals(3, result.getChildren().size());
        Assertions.assertEquals("Fine.", nextParser.getLastParsed());
    }

    @Test
    void parsesSentencesWithEllipsis() {
        TextComponent result = sentenceParser.parse("Wait… What happened?");
        Assertions.assertEquals(2, result.getChildren().size());
        Assertions.assertEquals("What happened?", nextParser.getLastParsed());
    }

    @Test
    void trimsWhitespaceAroundSentences() {
        TextComponent result = sentenceParser.parse("   Hello!   ");
        Assertions.assertEquals(1, result.getChildren().size());
        Assertions.assertEquals("Hello!", nextParser.getLastParsed());
    }

    @Test
    void returnsEmptyParagraphCompositeForEmptyInput() {
        TextComponent result = sentenceParser.parse("");
        Assertions.assertEquals(0, result.getChildren().size());
    }

    @Test
    void ignoresTextWithoutSentenceTerminators() {
        TextComponent result = sentenceParser.parse("This is not a sentence");
        Assertions.assertEquals(0, result.getChildren().size());
    }

    @Test
    void handlesMultipleSentenceTerminatorsTogether() {
        TextComponent result = sentenceParser.parse("Wow!! Really??");
        Assertions.assertEquals(2, result.getChildren().size());
        Assertions.assertEquals("Really??", nextParser.getLastParsed());
    }
}
